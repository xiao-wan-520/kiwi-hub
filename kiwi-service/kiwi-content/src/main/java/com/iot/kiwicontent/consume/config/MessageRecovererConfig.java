package com.iot.kiwicontent.consume.config;

import com.iot.kiwicontent.model.constant.RabbitConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.context.annotation.Bean;

/**
 * 消息恢复配置类
 * @author wan
 */
public class MessageRecovererConfig {
    // 定义接收失败消息的直连交换机
    @Bean
    public Exchange errorMessageExchange() {
        return ExchangeBuilder
                .directExchange(RabbitConstant.ARTICLE_RECOVERY_EXCHANGE)
                .durable(true)
                .build();
    }

    // 定义失败消息队列（持久化）
    @Bean
    public Queue errorQueue() {
        return QueueBuilder
                .durable(RabbitConstant.ARTICLE_RECOVERY_QUEUE)
                .build();
    }

    // 绑定交换机与队列，指定路由键为 "error"
    @Bean
    public Binding errorBinding(Exchange errorExchange, Queue errorQueue) {
        return BindingBuilder.bind(errorQueue)
                .to(errorExchange)
                .with(RabbitConstant.ARTICLE_RECOVERY_ROUTING_KEY)
                .noargs();
    }

    @Bean
    public MessageRecoverer republishMessageRecoverer(RabbitTemplate rabbitTemplate) {
        // 将失败消息投递到 error.direct 交换机，路由键为 error
        return new RepublishMessageRecoverer(rabbitTemplate,
                RabbitConstant.ARTICLE_RECOVERY_EXCHANGE,
                RabbitConstant.ARTICLE_RECOVERY_ROUTING_KEY);
    }
}
