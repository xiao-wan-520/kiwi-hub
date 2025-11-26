package com.iot.kiwiuser.controller;

import com.iot.common.constant.HttpHeader;
import com.iot.common.constant.SessionConstant;
import com.iot.common.result.Result;
import com.iot.kiwiuser.model.dto.UserLoginDTO;
import com.iot.kiwiuser.model.dto.UserRegisterDTO;
import com.iot.kiwiuser.service.UserAuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

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
    public Result<Object> login(@RequestBody @Validated UserLoginDTO loginDTO, HttpSession session) {
        // 处理登录逻辑
        Result<Object> result = userAuthService.login(loginDTO);
        if (result.isSuccess()) {
            Map<String, Object> user = new HashMap<>();
            user.put(SessionConstant.ATTRIBUTE_USERNAME, loginDTO.getUsername());
            user.put(SessionConstant.ATTRIBUTE_ID, loginDTO.getId());
            session.setAttribute(SessionConstant.LOGIN_USER, user);
        }
        return result;
    }

    /**
     * 登出
     * @param session HttpSession
     * @param id 用户 ID
     * @return 登出结果
     */
    @PostMapping("/logout")
    public Result<Object> logout(HttpSession session, @RequestHeader(HttpHeader.USER_ID) String id) {
        System.out.println(id);
        // 处理登出逻辑
        // 销毁 Session，Redis 中对应的数据也会被删除
        session.invalidate();
        return Result.success().message("登出成功");
    }

    /**
     * 注销
     * @param session HttpSession
     * @param userId 用户 ID
     */
    @PostMapping("/delete")
    public Result<Object> delete(HttpSession session, @RequestHeader(HttpHeader.USER_ID) String userId) {
        userAuthService.delete(userId);
        session.invalidate();
        return Result.success().message("注销成功");
    }
}
