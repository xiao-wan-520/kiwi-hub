package com.iot.kiwigateway.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.iot.kiwigateway.config.properties.CookieProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.session.CookieWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 配置 Spring Session 的序列化器为 GenericJackson2JsonRedisSerializer
 * 和 User 微服务中的一致
 * @author wan
 */
@Configuration
@RequiredArgsConstructor
public class GatewaySessionConfig {

    private final CookieProperties cookieProperties;

    /**
     * 配置 Spring Session 的序列化器为 GenericJackson2JsonRedisSerializer
     * 和 User 微服务中的一致
     * @return RedisSerializer
     */
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer(OBJECT_MAPPER);
    }

    private static final ObjectMapper OBJECT_MAPPER;


    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 支持多态类型处理
        OBJECT_MAPPER.activateDefaultTyping(
                // 使用宽松的多态类型验证器
                LaissezFaireSubTypeValidator.instance,
                // 激活默认类型信息
                ObjectMapper.DefaultTyping.NON_FINAL,
                // 类型信息包含方式为属性
                JsonTypeInfo.As.PROPERTY
        );
        // 设置可见性，确保 private 字段也能被序列化
        OBJECT_MAPPER.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        OBJECT_MAPPER.registerModule(javaTimeModule);
    }

    /**
     * WebFlux 下自定义 Session ID 解析器
     * 作用：统一 Cookie 的名称、路径、以及编码格式（Base64）
     * 自定义解析器：强制让 WebFlux 支持 Base64 (servlet 默认支持，WebFlux 不支持)
     */
    @Bean
    public WebSessionIdResolver webSessionIdResolver() {
        // 使用匿名内部类重写 resolveSessionIds 和 setSessionId
        CookieWebSessionIdResolver resolver = new CookieWebSessionIdResolver() {

            // 【读 Cookie】：收到浏览器发来的 Base64，解密成 UUID 再去 Redis 查
            @Override
            public List<String> resolveSessionIds(ServerWebExchange exchange) {
                return super.resolveSessionIds(exchange).stream()
                        .map(this::base64Decode) // 解密
                        .collect(Collectors.toList());
            }

            // 【写 Cookie】：生成了新 UUID，加密成 Base64 再发给浏览器
            @Override
            public void setSessionId(ServerWebExchange exchange, String id) {
                super.setSessionId(exchange, base64Encode(id)); // 加密
            }

            // 辅助方法：解密
            private String base64Decode(String value) {
                try {
                    return new String(Base64.getDecoder().decode(value));
                } catch (Exception e) {
                    // 如果解密失败（比如已经是明文），这就直接返回原值，兼容性处理
                    return value;
                }
            }

            // 辅助方法：加密
            private String base64Encode(String value) {
                return Base64.getEncoder().encodeToString(value.getBytes());
            }
        };

        // 1. 设置 Cookie 名字 (和你 User 服务一致)
        resolver.setCookieName(cookieProperties.getName());

        // 2. 设置路径
        resolver.addCookieInitializer(builder -> builder.path(cookieProperties.getPath()));

        return resolver;
    }

}
