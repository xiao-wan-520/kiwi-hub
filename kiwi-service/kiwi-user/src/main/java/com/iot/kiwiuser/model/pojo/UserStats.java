package com.iot.kiwiuser.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author wan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStats {
    // 文章数
    @Field("article_count")
    private int articleCount = 0;
    // 关注数
    @Field("following_count")
    private int followingCount = 0;
    // 粉丝数
    @Field("follower_count")
    private int followerCount = 0;
}
