package com.example.LiveChat.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.Date;


// This is a rest controller for chat messages, which handles the network calls related to chats
@Controller
public class ChatController {
    // Springboot will handle the dependency injection for us.

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    // Endpoint for public chats
    @MessageMapping("/message") // Receive endpoint
    @SendTo("/chatroom/public") // Send endpoint
    public ChatMessage receivePublicMessage(@Payload ChatMessage message) {
        return message;
    }

    @MessageMapping("/private-message")
    public ChatMessage receivePrivateMessage(@Payload ChatMessage message) {
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message); // user/UserName/private

        System.out.println(message.getMessage());
        return message;
    }
}
