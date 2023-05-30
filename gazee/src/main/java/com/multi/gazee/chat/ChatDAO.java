package com.multi.gazee.chat;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.multi.gazee.product.ProductDAO;
import com.multi.gazee.product.ProductVO;

@Component
@Repository
public class ChatDAO {
	
	@Autowired
	SqlSessionTemplate my;
	
	@Autowired
	ProductDAO dao;

	public int create(ChatVO chatVO) {
		ProductVO bag2 = dao.productone(chatVO.getProductId());
		chatVO.setSellerId(bag2.getMemberId());
		int result = my.insert("chat.create", chatVO);
		if (result > 0) {
			int roomId = chatVO.getRoomId();
			return roomId;
		} else {
			return 0;
		}
	}
	
	public ChatVO chatRoomSearch(ChatVO chatVO) {
		ChatVO result = my.selectOne("chat.chatRoomSearch", chatVO);
		return result;
	}
	
	public List<ChatVO> chatList(String memberId) {
		List<ChatVO> list = my.selectList("chat.chatList", memberId);
		return list;
	}
	
	public ChatVO chatRoomOne(int roomId) {
		ChatVO result = my.selectOne("chat.chatRoomOne", roomId);
		return result;
	}
	
	public int lastMessageTimeUpdate(ChatVO chatVO) {
		int result = my.update("chat.lastMessageTimeUpdate", chatVO);
		return result;
	}
	
	public int roomLeave(int roomId) {
		int result = my.delete("chat.roomLeave", roomId);
		return result;
	}
	
	public int dealDirectDateUpdate(ChatVO chatVO) {
		int result = my.update("chat.dealDateUpdate", chatVO);
		return result;
	}
	
	public List<ChatVO> unreadMessageCheck(String memberId) {
		List<ChatVO> list = my.selectList("chat.unreadMessage", memberId);
		return list;
	}
}
