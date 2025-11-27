package com.iot.kiwicontent.model.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author wan
 */
@Data
public class ArticleStats {
    @Field("view_count")
    private int viewCount = 0;
    @Field("like_count")
    private int likeCount = 0;
    @Field("comment_count")
    private int commentCount = 0;
}
