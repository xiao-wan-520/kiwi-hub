package com.iot.kiwiuser.repository;

import com.iot.kiwiuser.model.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 用户数据访问接口
 * @author wan
 */
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * 判断是否存在用户名或邮箱匹配的用户
     * @param username 用户名
     * @param email 邮箱
     * @return 如果存在，返回 true；否则返回 false
     */
    boolean existsByUsernameOrEmail(String username, String email);
}
