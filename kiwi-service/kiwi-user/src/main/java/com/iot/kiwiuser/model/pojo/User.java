package com.iot.kiwiuser.model.pojo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * @author wan
 */
@Data
@Builder
@Document(collection = "users")
public class User {

    // 对应 MongoDB 的 _Id
    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String email;

    // 存储BCrypt加密后的字符串
    @Field(name = "password_hash")
    private String passwordHash;

    @Field(name = "created_at")
    private LocalDateTime createdAt;

    // 嵌入文档
    private UserProfile profile;

    @Field(name = "social_stats")
    private UserStats socialStats;
}