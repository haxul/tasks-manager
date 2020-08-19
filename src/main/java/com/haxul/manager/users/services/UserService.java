package com.haxul.manager.users.services;

import com.haxul.manager.users.dto.User;
import com.haxul.manager.users.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Value("${password.salt}")
    private String salt;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User createUser(String username, String password) {
        String hash = bCryptPasswordEncoder.encode(password + salt);
        return userRepository.createUser(username, hash);
    }
}
