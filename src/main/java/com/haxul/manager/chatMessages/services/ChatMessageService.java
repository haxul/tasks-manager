package com.haxul.manager.chatMessages.services;

import com.haxul.manager.chatMessages.repositories.ChatMessageRepository;
import com.haxul.manager.users.security.AuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final AuthFilter authFilter;

    public void saveMessage(String from, String to, String message) {
        chatMessageRepository.save(from, to, message, String.valueOf(System.currentTimeMillis()));
    }

    public String verifyMessage(String token) {
        try {
            return authFilter.verifyToken(token);
        }catch (Exception e) {
            return null;
        }
    }
}
