package com.haxul.manager.users.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserCreateRequest {
    @NotBlank
    @Size(min = 3, max = 150)
    private String username;

    @NotBlank
    @Size(min = 3, max = 40)
    private String password;
}
