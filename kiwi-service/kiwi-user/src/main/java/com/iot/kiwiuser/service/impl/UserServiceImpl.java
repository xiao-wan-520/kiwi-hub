package com.iot.kiwiuser.service.impl;

import com.iot.common.result.Result;
import com.iot.kiwiuser.model.pojo.User;
import com.iot.kiwiuser.model.pojo.UserRelation;
import com.iot.kiwiuser.model.vo.UserDetailVO;
import com.iot.kiwiuser.repository.UserRelationRepository;
import com.iot.kiwiuser.repository.UserRepository;
import com.iot.kiwiuser.service.UserService;
import com.mongodb.DuplicateKeyException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 * @author wan
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRelationRepository userRelationRepository;

    /**
     * 获取当前用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public UserDetailVO getCurrentUserDetail(String userId) {
        User user = userRepository.findById(userId)
                .orElseGet(() -> User.builder().build());
        UserDetailVO userDetailVO = new UserDetailVO();
        BeanUtils.copyProperties(user, userDetailVO);
        return userDetailVO;
    }

    /**
     * 关注用户
     * @param userId 用户ID
     * @param followUserId 关注用户ID
     * @return 是否成功
     */
    @Override
    public Result<Object> follow(String userId, String followUserId) {
        boolean exists = userRepository.existsById(followUserId);
        if (!exists) {
            return Result.fail().message("关注的用户不存在");
        }
        // 保存关注关系
        UserRelation userRelation = new UserRelation(userId, followUserId);
        try {
            // 效率高且线程安全 TODO RabbitMQ推送消息+更新 users 表的统计字段
            userRelationRepository.save(userRelation);
        } catch (DuplicateKeyException e) {
            // 捕获唯一索引冲突的异常
            return Result.fail().message("已关注该用户");
        }
        return Result.success().message("关注成功");
    }

    /**
     * 取消关注用户
     * @param userId 用户ID
     * @param followUserId 取消关注用户ID
     * @return 是否成功
     */
    @Override
    public Result<Object> unfollow(String userId, String followUserId) {
        // 直接删除，数据库自动处理不存在的情况
        userRelationRepository.deleteByFollowerIdAndFollowingId(userId, followUserId);
        return Result.success().message("取消关注成功");
    }
}
