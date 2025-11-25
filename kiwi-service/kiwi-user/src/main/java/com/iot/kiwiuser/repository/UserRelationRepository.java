package com.iot.kiwiuser.repository;

import com.iot.kiwiuser.model.pojo.UserRelation;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 用户关系存储接口
 * @author wan
 */
public interface UserRelationRepository extends MongoRepository<UserRelation, String> {

    /**
     * 删除指定用户关系
     * @param followerId 关注者 ID
     * @param followingId 被关注者 ID
     */
    void deleteByFollowerIdAndFollowingId(String followerId, String followingId);
}
