package com.iot.kiwicontent.model.constant;

/**
 * RabbitMQ 常量
 * @author wan
 */
public class RabbitConstant {

    public static final String ARTICLE_INTERACTION_EXCHANGE = "kiwi-article-interaction-exchange";
    // 观看数相关
    public static final String ARTICLE_VIEW_QUEUE = "kiwi-article-view-queue";
    public static final String ARTICLE_VIEW_ROUTING_KEY = "article.view";

    // 点赞数相关
    public static final String ARTICLE_LIKE_QUEUE = "kiwi-article-like-queue";
    public static final String ARTICLE_LIKE_ROUTING_KEY = "article.like";

    // 评论数相关
    public static final String ARTICLE_COMMENT_QUEUE = "kiwi-article-comment-queue";
    public static final String ARTICLE_COMMENT_ROUTING_KEY = "article.comment";

    public static final String ARTICLE_RECOVERY_EXCHANGE = "kiwi-article-recovery-exchange";
    public static final String ARTICLE_RECOVERY_QUEUE = "kiwi-article-recovery-queue";
    public static final String ARTICLE_RECOVERY_ROUTING_KEY = "recovery.error";

    // 用户服务相关
    public static final String ARTICLE_USER_EXCHANGE = "kiwi-article-user-exchange";
    public static final String USER_ARTICLE_KEY = "user.article";
}
