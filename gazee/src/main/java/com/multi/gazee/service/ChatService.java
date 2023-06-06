package com.multi.gazee.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.multi.gazee.chat.ChatMessageVO;
import com.multi.gazee.chat.ChatOutputMessageVO;
import com.multi.gazee.chat.ChatVO;

public interface ChatService {

	int chatRoomCreate(ChatVO chatVO, HttpSession session) throws Exception;
	
	int chatRoomCheck(ChatVO chatVO) throws Exception;
	
	ArrayList<Integer> myChatRoomIds(String memberId) throws Exception;
	
	void chatList(String memberId, Model model);
	
	void chatRoomEntry(int roomId, Model model);
	
	void roomLeave(int roomId);
	
	void chatPaymentModal(int roomId, Model model);
	
	void dealDirectDateUpdate(ChatVO chatVO);
	
	void saveSubscribedRoomIds(HttpSession session, List<String> roomIds);
	
	List<Integer> getSubscribedRoomIds(HttpSession session);
	
	List<Integer> addChatRoomIdToSession(HttpSession session, String roomId);
	
	List<ChatOutputMessageVO> chatMessageList(int roomId);
	
	ChatMessageVO chatLastMessage(int roomId);
	
	int lastMessageTimeUpdate(int roomId);
	
	ChatOutputMessageVO chatMessage(ChatMessageVO message, String roomId);
	
	ChatVO chatSelectOne(int roomId);
	
	List<ChatVO> unreadMessageCheck(String memberId);
}
