package com.haxul.manager.users.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Builder
public class User {
    private String username;
    private String password;
    @Builder.Default
    private Boolean isDeleted = false;
    @Builder.Default
    private Boolean isBanned = false;
    @Builder.Default
    private Timestamp created = new Timestamp(System.currentTimeMillis());
}
