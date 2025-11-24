package com.iot.kiwicontent.model.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author wan
 */
@Data
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    private String articleId;
    private String authorId;
    private String content;

    // 用于实现盖楼回复结构
    private String parentId;

    // 可选：用于快速定位评论串的根节点
    private String rootId;

    private LocalDateTime createdAt;
}
