package com.iot.kiwigateway.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.server.session.SpringSessionWebSessionStore;
import org.springframework.web.server.session.DefaultWebSessionManager;
import org.springframework.web.server.session.WebSessionManager;

/**
 * 监测 Spring Session 的存储方式
 * @author wan
 */
@Configuration
public class SessionDebugConfig {

    @Bean
    public ApplicationRunner inspectSessionStorage(WebSessionManager webSessionManager) {
        return args -> {
            System.err.println("=================================================");
            System.err.println(">>> [Session存储诊断] 当前使用的管理器: " + webSessionManager.getClass().getName());

            if (webSessionManager instanceof DefaultWebSessionManager defaultManager) {
                // 反射获取内部的 sessionStore，因为它是 private 的
                java.lang.reflect.Field field = DefaultWebSessionManager.class.getDeclaredField("sessionStore");
                field.setAccessible(true);
                Object store = field.get(defaultManager);

                System.err.println(">>> [Session存储诊断] 内部 Store 类型: " + store.getClass().getName());

                if (store.getClass().getName().contains("InMemory")) {
                    System.err.println(">>> 警告: 当前正在使用【内存】存储！Redis 未生效！");
                } else if (store instanceof SpringSessionWebSessionStore) {
                    System.err.println(">>> 成功: 当前正在使用【Spring Session (Redis)】存储！");
                }
            } else {
                System.err.println(">>> [Session存储诊断] 使用了自定义管理器，情况未知。");
            }
            System.err.println("=================================================");
        };
    }
}
