package com.haxul.manager.chatMessages.controllers;

import com.haxul.manager.users.dto.Message;
import com.haxul.manager.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserService userService;

    @MessageMapping("/user/{to}/chat")
    public void sendMessage(@DestinationVariable String to, Message message) {
        //var user = userService.findUserByUsername(to);
        //if (user != null)
        simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
    }
}
