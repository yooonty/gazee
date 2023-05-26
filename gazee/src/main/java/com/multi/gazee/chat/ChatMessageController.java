package com.multi.gazee.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.multi.gazee.service.ChatService;

@Controller
public class ChatMessageController {

	@Autowired
	ChatService ChatService;
	
	/* 채팅메세지 내역 */
	@RequestMapping("chat/chatMessageList")
	@ResponseBody
	public List<ChatOutputMessageVO> list(int roomId) {
		return ChatService.chatMessageList(roomId);
	}
	
	/* 마지막 채팅 메세지 */
	@RequestMapping("chat/chatLastMessage")
	@ResponseBody
	public ChatMessageVO chatLastMessage(int roomId) {
		return ChatService.chatLastMessage(roomId);
	}
	
	/* 마지막 채팅 시간 업데이트 */
	@RequestMapping("chat/lastMessageTimeUpdate")
	@ResponseBody
	public int lastMessageTimeUpdate(int roomId) {
		return ChatService.lastMessageTimeUpdate(roomId);
	}

}
