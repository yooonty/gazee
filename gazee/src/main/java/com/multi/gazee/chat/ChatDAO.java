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

	public int create(ChatVO bag) {
		ProductVO bag2 = dao.productone(bag.getProductId());
		bag.setSellerId(bag2.getMemberId());
		int result = my.insert("chat.create", bag);
		if (result > 0) {
			int roomId = bag.getRoomId();
			return roomId;
		} else {
			return 0;
		}
	}
	
	public ChatVO chatRoomSearch(ChatVO bag) {
		ChatVO bag2 = my.selectOne("chat.chatRoomSearch", bag);
		return bag2;
	}
	
	public List<ChatVO> chatList(String memberId) {
		List<ChatVO> list = my.selectList("chat.chatList", memberId);
		return list;
	}
	
	public ChatVO chatRoomOne(int roomId) {
		ChatVO bag = my.selectOne("chat.chatRoomOne", roomId);
		return bag;
	}
	
	public int lastMessageTimeUpdate(ChatVO bag) {
		int result = my.update("chat.lastMessageTimeUpdate", bag);
		return result;
	}
	
	public int roomLeave(int roomId) {
		int result = my.delete("chat.roomLeave", roomId);
		return result;
	}
	
	public int dealDirectDateUpdate(ChatVO bag) {
		int result = my.update("chat.dealDateUpdate", bag);
		return result;
	}
	
}
