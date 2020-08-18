package com.haxul.manager.users.errors;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class ErrorMessage {
    private String status;
    private String reason;
}
