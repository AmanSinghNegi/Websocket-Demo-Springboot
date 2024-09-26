package com.example.demo;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;


@Controller
public class ChatController {

	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/public")
	public ChatDTO sendMessage(@Payload ChatDTO chat) {
		return chat;
	}
	
	@MessageMapping("chat.addUser")
	@SendTo("/topic/public")
	public ChatDTO addUser(@Payload ChatDTO chat,
			SimpMessageHeaderAccessor headerAccessor) {
		//Add user name in web socket session
		
		headerAccessor.getSessionAttributes().put("username", chat.getSender());
		return ChatDTO.builder()
		        .type(MessageType.JOIN) // Ensure this is set to JOIN
		        .sender(chat.getSender())
		        .build();
	}
}

