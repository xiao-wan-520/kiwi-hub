package com.iot.kiwicontent.model.pojo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String authorId;
    private String title;
    private String content;

    // 内容类型:content, html, markdown
    private String contentType;
    private List<String> ossUrls;
    private List<String> tags;

    private LocalDateTime createdAt;

    @Indexed
    private LocalDateTime updatedAt;

    // 嵌入文档
    private ArticleStats stats;
}
