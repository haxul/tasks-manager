package com.haxul.manager.chatMessages.dto;

import lombok.Data;

@Data
public class InputSocketMessage {
    private String from;
    private String text;
    private String token;
}
