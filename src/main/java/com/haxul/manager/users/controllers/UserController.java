package com.haxul.manager.users.controllers;

import com.haxul.manager.users.dto.*;
import com.haxul.manager.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper mapper;

    @PostMapping(path = "/signup")
    public UserResponse createUser(@Valid @RequestBody UserCreateRequest request) {
        var user = userService.createUser(request.getUsername(), request.getPassword());
        return new UserResponse(user.getUsername(), user.getCreated());
    }

    @PostMapping(path = "/login")
    public LoginResponse loginUser(@Valid @RequestBody LoginRequest request) {
        var token =  userService.loginUser(request.getUsername(), request.getPassword());
        return new LoginResponse(token, request.getUsername());
    }

    @GetMapping
    public List<GetAllResponseUser> findAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(user -> mapper.map(user, GetAllResponseUser.class))
                .collect(Collectors.toList());
    }



}
