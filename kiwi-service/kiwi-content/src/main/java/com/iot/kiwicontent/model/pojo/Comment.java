package com.iot.kiwicontent.model.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * @author wan
 */
@Data
@CompoundIndex(name = "article_id_author_id_index", def = "{'article_id': 1, 'author_id': 1}")
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    @Field("article_id")
    private String articleId;

    @Field("author_id")
    private String authorId;
    private String content;

    // 用于实现盖楼回复结构
    @Field("parent_id")
    private String parentId;

    // 可选：用于快速定位评论串的根节点
    @Field("root_id")
    private String rootId;

    @Field("created_at")
    private LocalDateTime createdAt;
}
