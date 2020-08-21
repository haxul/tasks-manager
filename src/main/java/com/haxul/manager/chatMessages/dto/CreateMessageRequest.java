package com.haxul.manager.chatMessages.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateMessageRequest {

    @NotBlank
    private String to;
    @NotBlank
    private String message;
}
