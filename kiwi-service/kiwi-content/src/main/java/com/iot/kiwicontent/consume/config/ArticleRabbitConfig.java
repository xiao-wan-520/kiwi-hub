package com.iot.kiwicontent.consume.config;

import com.iot.kiwicontent.model.constant.RabbitConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文章互动消息队列配置类
 * 观看数、点赞数、评论数消息队列配置类
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
    public Queue articleViewQueue() {
        return QueueBuilder
                .durable(RabbitConstant.ARTICLE_VIEW_QUEUE)
                .build();
    }


    // 绑定 RoutingKey
    @Bean
    public Binding articleViewBinding(Queue articleViewQueue, Exchange articleInteractionExchange) {
        return BindingBuilder.bind(articleViewQueue)
                .to(articleInteractionExchange)
                .with(RabbitConstant.ARTICLE_VIEW_ROUTING_KEY)
                .noargs();
    }

    @Bean
    public Queue articleLikeQueue() {
        return QueueBuilder
                .durable(RabbitConstant.ARTICLE_LIKE_QUEUE)
                .build();
    }


    // 绑定 RoutingKey
    @Bean
    public Binding articleLikeBinding(Queue articleLikeQueue, Exchange articleInteractionExchange) {
        return BindingBuilder.bind(articleLikeQueue)
                .to(articleInteractionExchange)
                .with(RabbitConstant.ARTICLE_LIKE_ROUTING_KEY)
                .noargs();
    }

    @Bean
    public Queue articleCommentQueue() {
        return QueueBuilder
                .durable(RabbitConstant.ARTICLE_COMMENT_QUEUE)
                .build();
    }


    // 绑定 RoutingKey
    @Bean
    public Binding articleCommentBinding(Queue articleCommentQueue, Exchange articleInteractionExchange) {
        return BindingBuilder.bind(articleCommentQueue)
                .to(articleInteractionExchange)
                .with(RabbitConstant.ARTICLE_COMMENT_ROUTING_KEY)
                .noargs();
    }

}
