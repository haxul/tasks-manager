package com.haxul.manager.users.controllers;

import com.google.gson.Gson;
import com.haxul.manager.users.dto.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(Message message) throws Exception {
        Gson gson = new Gson();
        return gson.toJson(new Message("hello"));
    }

}
