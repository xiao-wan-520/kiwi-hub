package com.iot.kiwigateway.filter;

import com.iot.kiwigateway.model.constant.URIConstant;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * 登出时，显式通知网关：这个 Session 不要了
 * Spring Session 的“自动续期机制” 和 Redis 的“广播通知机制” 会发生 “竞态冲突” (Race Condition)
 * @author wan
 */
@Component
public class LogoutConsistencyFilter implements GlobalFilter, Ordered {

    private static final Set<String> SESSION_INVALIDATE_URIS = Set.of(
            URIConstant.USER_LOGOUT,
            URIConstant.USER_DELETE
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.defer(() -> {
            ServerHttpRequest request = exchange.getRequest();

            // 1. 判断是否是注销接口 (根据你的实际路径修改)
            // 2. 判断下游是否处理成功 (状态码 200)
            boolean isLogoutRequest = SESSION_INVALIDATE_URIS.contains(request.getURI().getPath());
            boolean isSuccess = exchange.getResponse().getStatusCode().is2xxSuccessful();

            if (isLogoutRequest && isSuccess) {
                // 3. 显式通知网关：这个 Session 不要了！
                // 调用 invalidate 后，网关最后会执行 delete 而不是 save
                return exchange.getSession().flatMap(WebSession::invalidate);
            }
            return Mono.empty();
        }));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
