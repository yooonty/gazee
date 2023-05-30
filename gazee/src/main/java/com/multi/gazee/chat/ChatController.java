package com.multi.gazee.chat;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.multi.gazee.service.ChatService;

@Controller
public class ChatController {
	
	@Autowired
	ChatService ChatService;
	
	/* 채팅방 생성 */
	@RequestMapping("chat/chatRoomCreate")
	@ResponseBody
	public int chatRoomCreate(ChatVO chatVO, HttpSession session) throws Exception {
		return ChatService.chatRoomCreate(chatVO, session);
	}
	
	/* 채팅방이 기존에 존재했는지 확인 */
	@RequestMapping("chat/chatRoomCheck")
	@ResponseBody
	public int chatRoomCheck(ChatVO chatVO) throws Exception {
		return ChatService.chatRoomCheck(chatVO);
	}

	/* 내가 보유한 채팅방 roomId 전부 다 select */
	@RequestMapping("chat/myChatRoomIds")
	@ResponseBody
	public ArrayList<Integer> myChatRoomIds(String memberId) throws Exception {
		return ChatService.myChatRoomIds(memberId);
	}

	/* 내 채팅 목록 select */
	@RequestMapping("chat/chatList")
	public void chatList(String memberId, Model model) {
		ChatService.chatList(memberId, model);
	}
	
	/* 해당 채팅방 입장 */
	@RequestMapping("chat/chatRoomEntry")
	public void chatRoomEntry(int roomId, Model model, HttpSession session) {
		ChatService.chatRoomEntry(roomId, model);
	}

	/* 채팅방 나가기 */
	@RequestMapping("chat/roomLeave")
	@ResponseBody
	public void roomLeave(int roomId) {
		ChatService.roomLeave(roomId);
	}

	/* 채팅방 내 결제시스템 */
	@RequestMapping("chat/chatPaymentModal")
	public void chatPaymentModal(int roomId, Model model) {
		ChatService.chatPaymentModal(roomId, model);
	}

	/* 직거래 시간 업데이트 */
	@RequestMapping("chat/dealDirectDateUpdate")
	@ResponseBody
	public void dealDirectDateUpdate(ChatVO chatVO) {
		ChatService.dealDirectDateUpdate(chatVO);
	}

	/* 로그인시 내가 보유한 roomIds에 대해 세션 추가 */
	@PostMapping(value = "chat/saveSubscribedRoomIds", consumes = "application/json")
    @ResponseBody
    public void saveSubscribedRoomIds(HttpSession session, @RequestBody List<String> roomIds) {
		ChatService.saveSubscribedRoomIds(session, roomIds);
    }

	/* 새로운 채팅방 세션 업데이트 */
	@PostMapping("chat/addChatRoomIdToSession")
	@ResponseBody
	public List<Integer> addChatRoomIdToSession(HttpSession session, @RequestParam("roomId")String roomId) {
		return ChatService.addChatRoomIdToSession(session, roomId);
	}
	
	/* 로그인 제외 모든 페이지에서의 roomIds 세션 가져오기 */
	@GetMapping("chat/getSubscribedRoomIds")
	@ResponseBody
	public List<Integer> getSubscribedRoomIds(HttpSession session) {
		return ChatService.getSubscribedRoomIds(session);
	}
	
	@RequestMapping("chat/chatSelectOne")
	@ResponseBody
	public ChatVO chatSelectOne(int roomId) {
		return ChatService.chatSelectOne(roomId);
	}
	
	@RequestMapping("chat/unreadMessageCheck")
	@ResponseBody
	public List<ChatVO> unreadMessageCheck(String memberId) {
		return ChatService.unreadMessageCheck(memberId);
	}
}
