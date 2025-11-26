package com.iot.kiwiuser.model.constant;

/**
 * RabbitMQ 常量
 * @author wan
 */
public class RabbitConstant {
    public static final String USER_RELATION_EXCHANGE = "kiwi-user-relation-exchange";
    public static final String USER_RELATION_QUEUE = "kiwi-user-relation-queue";
    public static final String USER_RELATION_ROUTING_KEY = "user.relation";

    public static final String USER_RECOVERY_EXCHANGE = "kiwi-user-recovery-exchange";
    public static final String USER_RECOVERY_QUEUE = "kiwi-user-recovery-queue";
    public static final String USER_RECOVERY_ROUTING_KEY = "recovery.error";
}
