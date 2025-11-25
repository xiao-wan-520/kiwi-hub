package com.iot.kiwiuser.model.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * 用户关系模型
 * @author wan
 */
@Data
@Document(collection = "user_relations")
@CompoundIndexes({
        // 索引1：确保 A 不能重复关注 B
        @CompoundIndex(name = "unique_relation", def = "{'followerId': 1, 'followingId': 1}", unique = true),

        // 索引2：用于“查询我的关注列表”，并按时间倒序
        @CompoundIndex(name = "idx_follower_time", def = "{'followerId': 1, 'createdAt': -1}"),

        // 索引3：用于“查询我的粉丝列表”，并按时间倒序
        @CompoundIndex(name = "idx_following_time", def = "{'followingId': 1, 'createdAt': -1}")
})
public class UserRelation {

    @Id
    private String id;

    // 发起关注的用户 ID (粉丝)
    @Field("follower_id")
    private String followerId;

    // 被关注的用户 ID (目标)
    @Field("following_id")
    private String followingId;

    @Field("created_at")
    private LocalDateTime createdAt;

    // 构造函数方便快速创建
    public UserRelation(String followerId, String followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
        this.createdAt = LocalDateTime.now();
    }
}
