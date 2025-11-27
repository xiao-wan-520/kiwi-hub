package com.iot.kiwiuser.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author wan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @Field("avatar_url")
    private String avatarUrl;
    // 简介
    private String bio;
    private List<String> tags;
}
