package com.example.LiveChat.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;


// This is a rest controller for chat messages, which handles the network calls related to chats
public class ChatController {
    // Springboot will handle the dependency injection for us.
    private SimpMessagingTemplate simpMessagingTemplate;

    // Endpoint for public chats
    @MessageMapping("/message") // Receive endpoint
    @SendTo("chatroom/public") // Send endpoint
    public ChatMessage receivePublicMessage(@Payload ChatMessage message) {
        return message;
    }

    @MessageMapping("/private-message")
    public ChatMessage receivePrivateMessage(@Payload ChatMessage message) {
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message); // user/UserName/private

        return message;
    }
}
