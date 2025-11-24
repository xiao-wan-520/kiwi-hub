package com.iot.kiwiuser.controller;

import com.iot.common.result.Result;
import com.iot.kiwiuser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息及关系相关接口
 * @author wan
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public Result<Object> getCurrentUser() {
        // 处理获取当前用户信息逻辑
        return Result.success();
    }

    @PutMapping("/me/profile")
    public Result<Object> updateProfile(@RequestBody Object profileDTO) {
        // 处理更新个人信息逻辑
        return Result.success();
    }

    @PostMapping("/{userId}/follow")
    public Result<Object> follow(@PathVariable Long userId) {
        // 处理关注逻辑
        return Result.success();
    }

    @DeleteMapping("/{userId}/follow")
    public Result<Void> unfollow(@PathVariable Long userId) {
        // 处理取消关注逻辑
        return Result.success();
    }

    @GetMapping("/{userId}/following")
    public Result<Page<Object>> getFollowingList(@PathVariable Long userId,
                                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        // 处理获取关注列表逻辑
        return null;
    }

    @GetMapping("/{userId}/followers")
    public Result<Page<Object>> getFollowersList(@PathVariable Long userId,
                                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        // 处理获取粉丝列表逻辑
        return null;
    }
}
