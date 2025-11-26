package com.iot.kiwicontent.consume.config;

import com.iot.kiwicontent.model.constant.RabbitConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文章互动消息队列配置类
 * @author wan
 */
@Configuration
public class ArticleRabbitConfig {
    @Bean
    public Exchange articleInteractionExchange() {
        return ExchangeBuilder
                .directExchange(RabbitConstant.ARTICLE_INTERACTION_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    public Queue articleInteractionQueue() {
        return QueueBuilder
                .durable(RabbitConstant.ARTICLE_INTERACTION_QUEUE)
                .build();
    }


    // 绑定 RoutingKey
    @Bean
    public Binding articleInteractionBinding(Queue articleInteractionQueue, Exchange articleInteractionExchange) {
        return BindingBuilder.bind(articleInteractionQueue)
                .to(articleInteractionExchange)
                .with(RabbitConstant.ARTICLE_INTERACTION_ROUTING_KEY)
                .noargs();
    }

}
