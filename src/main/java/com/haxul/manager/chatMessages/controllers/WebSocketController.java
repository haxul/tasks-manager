package com.haxul.manager.chatMessages.controllers;

import com.haxul.manager.chatMessages.dto.InputSocketMessage;
import com.haxul.manager.chatMessages.dto.OutputSocketMessage;
import com.haxul.manager.chatMessages.services.ChatMessageService;
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
    private final ChatMessageService chatMessageService;

    @MessageMapping("/user/{to}/chat")
    public void sendMessage(@DestinationVariable String to, InputSocketMessage message) {
        var verifiedUsername = chatMessageService.verifyMessage(message.getToken());
        if (verifiedUsername == null) {
            var response = new OutputSocketMessage();
            response.setText("Forbidden");
            response.setFrom("Server");
            simpMessagingTemplate.convertAndSend("/topic/messages/" + message.getFrom(), response);
        }

        var receiver = userService.findUserByUsername(to);
        if (receiver != null) {
            var response = new OutputSocketMessage();
            response.setFrom(message.getFrom());
            response.setText(message.getText());
            chatMessageService.saveMessage(message.getFrom(), to, message.getText());
            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, response);
        }
    }
}
