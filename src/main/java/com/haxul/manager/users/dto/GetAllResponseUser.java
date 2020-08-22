package com.haxul.manager.users.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class GetAllResponseUser {
    private String username;
    private Boolean isBanned;
    private Boolean isDeleted;
    private Timestamp created;
    private Boolean isOnline;
}
