package com.iot.kiwiuser.repository;

import com.iot.kiwiuser.model.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

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

    /**
     * 查询 username 和 email 匹配的用户，并只返回 password 字段。
     * 如果查询不到，返回 null。
     */
    @Query(
            value = "{ 'username' : ?0, 'email' : ?1 }",
            fields = "{'password_hash' : 1}"
    )
    Optional<User> findPasswordByUsernameAndEmail(String username, String email);
}
