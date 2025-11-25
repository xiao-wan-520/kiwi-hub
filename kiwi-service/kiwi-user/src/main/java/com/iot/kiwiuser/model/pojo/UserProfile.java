package com.iot.kiwiuser.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private String avatarUrl;
    // 简介
    private String bio;
    private List<String> tags;
}
