package com.iot.kiwigateway.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.ReactiveSessionRepository;
import org.springframework.session.Session;
import reactor.core.publisher.Mono;

/**
 * 这是一个“间谍”配置
 * 它会拦截系统中的 ReactiveSessionRepository (Redis版)
 * 并打印出它的一举一动。
 * @author wan
 */
@Configuration
public class SessionSpyConfig implements BeanPostProcessor {

    @Override
    @SuppressWarnings("unchecked")
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 拦截 ReactiveSessionRepository
        if (bean instanceof ReactiveSessionRepository) {
            System.err.println(">>> [间谍生效] 已捕获 SessionRepository: " + bean.getClass().getName());
            // 使用通配符或 Session 接口
            return new SpyReactiveSessionRepository((ReactiveSessionRepository<Session>) bean);
        }
        return bean;
    }

    /**
     * 内部包装类，用于打印日志
     */
    static class SpyReactiveSessionRepository implements ReactiveSessionRepository<Session> {
        private final ReactiveSessionRepository<Session> delegate;

        public SpyReactiveSessionRepository(ReactiveSessionRepository<Session> delegate) {
            this.delegate = delegate;
        }

        @Override
        public Mono<Session> createSession() {
            return delegate.createSession()
                    .doOnSuccess(s -> System.err.println(">>> [间谍] 创建新 Session: " + s.getId()));
        }

        @Override
        public Mono<Void> save(Session session) {
            return delegate.save(session)
                    .doOnError(e -> System.err.println(">>> [间谍] 保存 Redis 失败: " + e.getMessage()));
        }

        @Override
        public Mono<Session> findById(String id) {
            System.err.println(">>> [间谍] 正在去 Redis 查找 ID: " + id);

            return delegate.findById(id)
                    .doOnNext(s -> {
                        System.err.println(">>> [间谍] Redis 找到了! ID=" + s.getId());
                        System.err.println(">>> [间谍] 包含属性: " + s.getAttributeNames());
                    })
                    .doOnSuccess(s -> {
                        if (s == null) {
                            System.err.println(">>> [间谍] Redis 返回了空 (NULL)! Key 可能不存在或已过期。");
                        }
                    })
                    .doOnError(e -> {
                        System.err.println(">>> [间谍] Redis 读取报错 (反序列化失败?): ");
                        e.printStackTrace();
                    });
        }

        @Override
        public Mono<Void> deleteById(String id) {
            return delegate.deleteById(id)
                    .doOnSuccess(v -> System.err.println(">>> [间谍] 从 Redis 删除 Session: " + id));
        }
    }
}