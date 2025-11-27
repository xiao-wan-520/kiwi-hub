package com.iot.kiwicontent.model.pojo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wan
 */
@Data
@Builder
@Document(collection = "articles")
public class Article {

    @Id
    private String id;

    // 引用 User ID
    @Field("author_id")
    private String authorId;
    private String title;
    private String content;

    // 内容类型:content, html, markdown
    @Field("content_type")
    private String contentType;

    @Field("oss_urls")
    private List<String> ossUrls;
    private List<String> tags;

    @Field("created_at")
    private LocalDateTime createdAt;

    @Indexed
    @Field("updated_at")
    private LocalDateTime updatedAt;

    // 嵌入文档
    private ArticleStats stats;
}
