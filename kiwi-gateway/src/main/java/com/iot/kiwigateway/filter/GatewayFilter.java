package com.iot.kiwigateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.common.constant.HttpHeader;
import com.iot.common.constant.SessionConstant;
import com.iot.common.result.Result;
import com.iot.common.result.ResultCodeEnum;
import com.iot.kiwigateway.model.constant.URIConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;

/**
 * 全局过滤器：验证Session
 * 职责是：“这个Session有效吗？有效就放行”。
 * @author wan
 */
@Slf4j
@Component
@SuppressWarnings("deprecation")
@RequiredArgsConstructor
public class GatewayFilter implements GlobalFilter, Ordered {

    private final ObjectMapper objectMapper;

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    /**
     * 跳过验证的接口: 不需要token验证
     */
    private static final Set<String> SKIP_AUTH = Set.of(
            URIConstant.USER_LOGIN,
            URIConstant.USER_REGISTER
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // 不需要token验证
        if (allowedPath(path)) {
            return chain.filter(exchange);
        }

        // Session验证
        // 注意：这里使用的是 WebFlux 的 getSession()，
        // Spring Session 会根据 Cookie 中的 SESSIONID 自动去 Redis 查找
        return exchange.getSession().flatMap(session -> {
            Object sessionAttribute = session.getAttribute(SessionConstant.LOGIN_USER);
            if (sessionAttribute == null) {
                // 未登录
                return returnErrorResponse(exchange, HttpStatus.UNAUTHORIZED,
                        Result.result(ResultCodeEnum.UNAUTHORIZED).message("未登录"));
            }
            // 已登录,放入 Header 传递给下游微服务
            Map<String, Object> sessionAttributeMap = (Map<String, Object>) sessionAttribute;
            String id = (String) sessionAttributeMap.get(SessionConstant.ATTRIBUTE_ID);

            ServerHttpRequest successRequest = exchange.getRequest().mutate()
                    .header(HttpHeader.USER_ID, id)
                    .build();

            return chain.filter(exchange.mutate().request(successRequest).build());
        });
    }

    // 返回错误信息
    private <T> Mono<Void> returnErrorResponse(ServerWebExchange exchange, HttpStatus status, Result<T> result) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsString(result).getBytes();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }

    /**
     * 判断请求路径是否需要过滤
     *
     * @param requestUri 请求路径
     * @return true: 不需要过滤，允许通过
     */
    private boolean allowedPath(String requestUri) {
        for (String allowedPath : SKIP_AUTH) {
            if (PATH_MATCHER.match(allowedPath, requestUri)) {
                // 匹配成功
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
