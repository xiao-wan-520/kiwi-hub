package com.iot.kiwiuser.controller;

import com.iot.common.constant.HttpHeader;
import com.iot.common.result.PageResult;
import com.iot.common.result.Result;
import com.iot.kiwiuser.model.dto.UserProfileDTO;
import com.iot.kiwiuser.model.vo.UserCardVO;
import com.iot.kiwiuser.model.vo.UserDetailVO;
import com.iot.kiwiuser.service.UserService;
import lombok.RequiredArgsConstructor;
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

    /**
     * 获取当前用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    @GetMapping("/me")
    public Result<UserDetailVO> getCurrentUser(@RequestHeader(HttpHeader.USER_ID) String userId) {
        // 处理获取当前用户信息逻辑
        return Result.success(userService.getCurrentUserDetail(userId));
    }

    /**
     * 更新当前用户信息
     * @param userId 用户ID
     * @param profileDTO 用户信息
     * @return 更新结果
     */
    @PutMapping("/me/profile")
    public Result<Object> updateProfile(@RequestHeader(HttpHeader.USER_ID) String userId,
                                        @ModelAttribute UserProfileDTO profileDTO) {
        userService.updateProfile(userId, profileDTO);
        // 处理更新个人信息逻辑
        return Result.success();
    }

    /**
     * 关注用户
     * @param userId 用户ID
     * @param followUserId 要关注的用户ID
     * @return 关注结果
     */
    @PostMapping("/follow")
    public Result<Object> follow(@RequestHeader(HttpHeader.USER_ID) String userId,
                                 @RequestParam String followUserId) {
        // 处理关注逻辑
        return userService.follow(userId, followUserId);
    }

    /**
     * 取消关注用户
     * @param userId 用户ID
     * @param followUserId 要取消关注的用户ID
     * @return 取消关注结果
     */
    @DeleteMapping("/follow")
    public Result<Object> unfollow(@RequestHeader(HttpHeader.USER_ID) String userId,
                                 @RequestParam String followUserId) {
        // 处理取消关注逻辑
        return userService.unfollow(userId, followUserId);
    }

    /**
     * 获取关注列表
     * @param userId 用户ID
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return 关注列表
     */
    @GetMapping("/following")
    public Result<PageResult<UserCardVO>> getFollowingList(@RequestHeader(HttpHeader.USER_ID) String userId,
                                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        // 处理获取关注列表逻辑
        return Result.success(userService.getFollowingList(userId, pageNum, pageSize));
    }

    /**
     * 获取粉丝列表
     * @param userId 用户ID
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return 粉丝列表
     */
    @GetMapping("/followers")
    public Result<PageResult<UserCardVO>> getFollowersList(@RequestHeader(HttpHeader.USER_ID) String userId,
                                                           @RequestParam(defaultValue = "1") Integer pageNum,
                                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        // 处理获取粉丝列表逻辑
        return Result.success(userService.getFollowersList(userId, pageNum, pageSize));
    }
}
