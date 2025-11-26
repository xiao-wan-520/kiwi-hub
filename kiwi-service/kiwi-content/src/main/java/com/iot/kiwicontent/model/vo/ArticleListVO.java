package com.iot.kiwicontent.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 作者简单获取列表信息
 * @author wan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListVO {
    private String id;
    private String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String contentType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> tags;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedAt;
}
