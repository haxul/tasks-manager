package com.haxul.manager.users.controllers;

import com.haxul.manager.users.dto.*;
import com.haxul.manager.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody UserCreateRequest request) {
        var user = userService.createUser(request.getUsername(), request.getPassword());
        return new UserResponse(user.getUsername(), user.getCreated());
    }

    @PostMapping(path = "/login")
    public LoginResponse loginUser(@Valid @RequestBody LoginRequest request) {
        var token =  userService.loginUser(request.getUsername(), request.getPassword());
        return new LoginResponse(token, request.getUsername());
    }

}
