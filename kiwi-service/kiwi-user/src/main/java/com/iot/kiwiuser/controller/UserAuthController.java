package com.iot.kiwiuser.controller;

import com.iot.common.result.Result;
import com.iot.kiwiuser.model.dto.UserLoginDTO;
import com.iot.kiwiuser.model.dto.UserRegisterDTO;
import com.iot.kiwiuser.service.UserAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户认证相关接口
 * @author wan
 */
@Validated
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserAuthService userAuthService;

    /**
     * 注册
     * @param registerDTO 注册信息
     * @param avatarFile 头像文件
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<Object> register(@ModelAttribute @Validated UserRegisterDTO registerDTO,
                                   @RequestPart(value = "avatarFile", required = false) MultipartFile avatarFile) {
        // 处理注册逻辑
        return userAuthService.register(registerDTO, avatarFile);
    }

    /**
     * 登录
     * @param loginDTO 登录信息
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<Object> login(@RequestBody @Validated UserLoginDTO loginDTO) {
        // 处理登录逻辑
        return userAuthService.login(loginDTO);
    }

    /**
     * 登出
     * @param request 请求对象
     * @return 登出结果
     */
    @PostMapping("/logout")
    public Result<Object> logout(HttpServletRequest request) {
        // 处理登出逻辑
        return Result.success();
    }
}
