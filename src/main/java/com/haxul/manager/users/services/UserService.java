package com.haxul.manager.users.services;

import com.haxul.manager.users.dto.User;
import com.haxul.manager.users.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(String username, String password) {

        return userRepository.createUser(username, password);
    }
}
