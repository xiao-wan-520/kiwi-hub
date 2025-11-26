package com.iot.kiwiuser.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户个人资料DTO
 * @author wan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
    private String bio;
    private List<String> tags;
}
