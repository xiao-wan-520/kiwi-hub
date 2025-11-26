package com.iot.kiwiuser.rabbitmq.consume.config;

import com.iot.kiwiuser.model.constant.RabbitConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用户消息队列配置类
 * @author wan
 */
@Configuration
public class UserRabbitConfig {
    @Bean
    public Exchange userRwlationExchange() {
        return ExchangeBuilder
                .directExchange(RabbitConstant.USER_RELATION_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    public Queue userRelationQueue() {
        return QueueBuilder
                .durable(RabbitConstant.USER_RELATION_QUEUE)
                .build();
    }


    // 绑定 RoutingKey
    @Bean
    public Binding userRelationBinding(Queue userFollowQueue, Exchange userFollowExchange) {
        return BindingBuilder.bind(userFollowQueue)
                .to(userFollowExchange)
                .with(RabbitConstant.USER_RELATION_ROUTING_KEY)
                .noargs();
    }
}
