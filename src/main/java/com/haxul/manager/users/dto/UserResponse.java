package com.haxul.manager.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
public class UserResponse {
    private String username;
    private Timestamp created;
}
