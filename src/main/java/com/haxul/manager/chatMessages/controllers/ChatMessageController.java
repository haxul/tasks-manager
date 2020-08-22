package com.haxul.manager.chatMessages.controllers;

import com.haxul.manager.chatMessages.dto.Message;
import com.haxul.manager.chatMessages.services.ChatMessageService;
import com.haxul.manager.users.security.SecurityContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @GetMapping(path = "/talk-with/{friend}")
    public List<Message> getUsernameChatWith(@PathVariable String friend) {
        var initiator = SecurityContextHolder.getUsername();
        return chatMessageService.findChatWithFriend(initiator, friend);
    }
}
