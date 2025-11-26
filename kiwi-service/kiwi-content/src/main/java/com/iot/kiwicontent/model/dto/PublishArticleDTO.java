package com.iot.kiwicontent.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 发布文章DTO
 * @author wan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishArticleDTO {
    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "内容不能为空")
    private String content;

    // 内容类型:content, html, markdown
    private String contentType;
    private List<String> ossUrls;
    private List<String> tags;
}
