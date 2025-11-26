package com.iot.kiwicontent.model.constant;

/**
 * RabbitMQ 常量
 * @author wan
 */
public class RabbitConstant {
    public static final String ARTICLE_INTERACTION_EXCHANGE = "kiwi-article-interaction-exchange";
    public static final String ARTICLE_INTERACTION_QUEUE = "kiwi-article-interaction-queue";
    public static final String ARTICLE_INTERACTION_ROUTING_KEY = "article.interaction";

    public static final String ARTICLE_RECOVERY_EXCHANGE = "kiwi-article-recovery-exchange";
    public static final String ARTICLE_RECOVERY_QUEUE = "kiwi-article-recovery-queue";
    public static final String ARTICLE_RECOVERY_ROUTING_KEY = "recovery.error";
}
