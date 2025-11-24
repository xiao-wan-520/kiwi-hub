package com.iot.kiwiuser.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

/**
 * @author wan
 */
@Data
public class UserRegisterDTO {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "邮箱不能为空")
    @Pattern(regexp = "^[\\w.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)+$", message = "邮箱格式不正确")
    private String email;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,12}$", message = "密码需包含字母和数字，长度6-12位")
    private String password;


    // 个性化设置，可不传
    private String bio;
    private List<String> tags;

}
