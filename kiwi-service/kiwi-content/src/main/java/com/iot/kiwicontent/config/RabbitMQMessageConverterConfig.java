package com.iot.kiwicontent.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 配置类
 * @author wan
 */
@Configuration
public class RabbitMQMessageConverterConfig {

    /**
     * 配置消息转换器为 Jackson2JsonMessageConverter
     */
    @Bean
    public MessageConverter messageConverter(){
        // 将消息体转换为 JSON 格式
        return new Jackson2JsonMessageConverter();
    }
}
