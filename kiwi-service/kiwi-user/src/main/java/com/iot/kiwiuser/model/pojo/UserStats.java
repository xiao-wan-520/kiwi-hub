package com.iot.kiwiuser.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStats {
    // 文章数
    private int articleCount = 0;
    // 关注数
    private int followingCount = 0;
    // 粉丝数
    private int followerCount = 0;
}
