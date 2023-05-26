package com.multi.gazee.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import com.multi.gazee.service.ChatService;

@RestController
public class WebSocketController {
	
	@Autowired
	ChatService ChatService;
	
	@MessageMapping("/chat/{roomId}")
	@SendTo("/topic/{roomId}")
	public ChatOutputMessageVO chatMessage(ChatMessageVO message, @DestinationVariable String roomId) {
		return ChatService.chatMessage(message, roomId);
	}

}
