package com.iot.kiwiuser.service.impl;

import com.iot.common.result.Result;
import com.iot.kiwiuser.model.dto.UserLoginDTO;
import com.iot.kiwiuser.model.dto.UserRegisterDTO;
import com.iot.kiwiuser.model.pojo.User;
import com.iot.kiwiuser.repository.UserRepository;
import com.iot.kiwiuser.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户认证服务实现类
 * @author wan
 */
@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 注册
     * @param registerDTO 注册信息
     * @param avatarFile 头像文件
     * @return 注册结果
     */
    @Override
    public Result<Object> register(UserRegisterDTO registerDTO, MultipartFile avatarFile) {
        // 查找是否已存在该用户，用户名或邮箱均不能重复
        String username = registerDTO.getUsername();
        String email = registerDTO.getEmail();
        boolean exists = userRepository.existsByUsernameOrEmail(username, email);
        if (exists) {
            return Result.fail().message("用户名或邮箱已存在");
        }

        String passwordHash = passwordEncoder.encode(registerDTO.getPassword());
        User user = User.builder()
                .username(username)
                .email(email)
                .passwordHash(passwordHash)
                .build();
        userRepository.save(user);
        return Result.success().message("注册成功");
    }

    @Override
    public Result<Object> login(UserLoginDTO loginDTO) {
        return null;
    }
}
