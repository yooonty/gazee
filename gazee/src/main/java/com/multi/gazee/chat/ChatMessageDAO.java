package com.multi.gazee.chat;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Component
@Repository
public class ChatMessageDAO {
	
	@Autowired
	SqlSessionTemplate my;
	
	public void insert(ChatMessageVO chatMessageVO) {
		my.insert("chat.chatSave", chatMessageVO);
	}
	
	public ArrayList<ChatOutputMessageVO> list(int roomId) {
		List<ChatMessageVO> list = my.selectList("chat.getChatLogs", roomId);
		ArrayList<ChatOutputMessageVO> arrList = new ArrayList<ChatOutputMessageVO>();
		for (ChatMessageVO x : list) {
			ChatOutputMessageVO chatOutputMessageVO = new ChatOutputMessageVO();
			chatOutputMessageVO.setRoomId(String.valueOf(x.getRoomId()));
			chatOutputMessageVO.setSender(x.getSender());
			chatOutputMessageVO.setContent(x.getContent());
			Timestamp date = x.getTime();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.HOUR_OF_DAY, 9);
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			String time = format.format(calendar.getTime());
			chatOutputMessageVO.setTime(time);
			arrList.add(chatOutputMessageVO);
		}
		Collections.reverse(arrList);
		return arrList;
	}
	
	public ChatMessageVO lastMessageList(int roomId) {
		ChatMessageVO chatMessageVO = my.selectOne("chat.lastMessage", roomId);
		return chatMessageVO;
	}
}