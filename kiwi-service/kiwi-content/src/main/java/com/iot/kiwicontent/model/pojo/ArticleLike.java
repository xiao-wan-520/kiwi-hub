package com.iot.kiwicontent.model.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * 点赞表
 * @author wan
 */
@Data
@Document(collection = "article_likes")
@CompoundIndexes({
        // 核心索引：确保同一个用户对同一篇文章只能点赞一次
        @CompoundIndex(name = "idx_user_article_unique", def = "{'userId': 1, 'articleId': 1}", unique = true),

        // 辅助索引：用于查询“我最近赞过的文章”
        @CompoundIndex(name = "idx_user_time", def = "{'userId': 1, 'createTime': -1}")
})
public class ArticleLike {

    @Id
    private String id;

    @Field("user_id")
    private String userId;

    @Field("article_id")
    private String articleId;

    // 冗余字段：方便快速统计“作者总获赞数”
    @Field("author_id")
    private String authorId;

    @Field("create_time")
    private LocalDateTime createTime;

    // 构造函数
    public ArticleLike(String userId, String articleId, String authorId) {
        this.userId = userId;
        this.articleId = articleId;
        this.authorId = authorId;
        this.createTime = LocalDateTime.now();
    }
}
