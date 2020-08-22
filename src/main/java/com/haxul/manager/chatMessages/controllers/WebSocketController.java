package com.haxul.manager.chatMessages.controllers;

import com.haxul.manager.chatMessages.dto.InputSocketMessage;
import com.haxul.manager.chatMessages.dto.OutputSocketMessage;
import com.haxul.manager.chatMessages.services.ChatMessageService;
import com.haxul.manager.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.LinkedList;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserService userService;
    private final ChatMessageService chatMessageService;


    @EventListener(SessionConnectEvent.class)
    public void handleWebsocketConnectListener(SessionConnectEvent event) {
        MessageHeaders headers = event.getMessage().getHeaders();
        Map<String, LinkedList<String>> nativeHeaders = (Map<String, LinkedList<String>>) headers.get("nativeHeaders");
        String username = nativeHeaders.get("username").getFirst();
        if (username != null) chatMessageService.updateIsOnlineOfUser(true, username);
    }
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) chatMessageService.updateIsOnlineOfUser(false, username);
    }


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


    @MessageMapping("/user/addHeader/{to}")
    public void addHeader(@DestinationVariable String to , SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", to);
    }
}
