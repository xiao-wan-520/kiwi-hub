package com.iot.kiwiuser.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户列表
 * @author wan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCardVO {
    private String id;
    private String username;
    private String avatarUrl;
    private String email;
    // 关注时间
    private LocalDateTime followTime;

    /**
     * 获取邮箱（脱敏处理）
     * @return 邮箱
     */
    public String getEmail() {
        String[] emailParts = email.split("@");
        String username = emailParts[0];
        String domain = emailParts[1];

        // 用户名长度≤3位时，保留全部+****；否则保留前3位+****
        String maskedUsername = username.length() <= 3
                ? username + "****"
                : username.substring(0, 3) + "****";

        return maskedUsername + "@" + domain;
    }

}
