package com.iot.kiwiuser.model.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author wan
 */
@Data
public class UserProfile {
    private String avatarUrl;
    // 简介
    private String bio;
    private List<String> tags;
}
