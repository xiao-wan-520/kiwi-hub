package com.iot.kiwiuser.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iot.kiwiuser.model.pojo.UserProfile;
import com.iot.kiwiuser.model.pojo.UserStats;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户详情视图对象
 * @author wan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailVO {
    private String username;
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdAt;

    private UserProfile profile;
    private UserStats socialStats;
}
