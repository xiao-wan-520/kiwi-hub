package com.iot.kiwiuser.service;

import com.iot.common.result.Result;
import com.iot.kiwiuser.model.vo.UserDetailVO;

/**
 * 用户服务接口
 * @author wan
 */
public interface UserService {
    /**
     * 获取当前用户详情
     * @param email 用户邮箱
     * @return 用户详情
     */
    UserDetailVO getCurrentUserDetail(String email);

    /**
     * 关注用户
     * @param userId 用户ID
     * @param followUserId 关注的用户ID
     * @return 响应结果
     */
    Result<Object> follow(String userId, String followUserId);

    /**
     * 取消关注用户
     * @param userId 用户ID
     * @param followUserId 取消关注的用户ID
     * @return 响应结果
     */
    Result<Object> unfollow(String userId, String followUserId);
}
