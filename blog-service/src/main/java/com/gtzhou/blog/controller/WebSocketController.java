package com.gtzhou.blog.controller;

import com.gtzhou.blog.domain.ClientMessage;
import com.gtzhou.blog.domain.ServerMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/webSocket/sendMsg")
    @SendTo("/topic/subscribeTest")
    public ServerMessage sendDemo(ClientMessage msg) {
        System.out.println(msg.getName());
        return new ServerMessage("recevied meg is " + msg.getName());
    }
}
