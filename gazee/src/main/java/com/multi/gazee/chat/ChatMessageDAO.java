package com.multi.gazee.chat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	MongoTemplate mongo;
	
	@Autowired
	SqlSessionTemplate my;
	
	public void insert(ChatMessageVO chatMessageVO) {
		mongo.insert(chatMessageVO, "chat_logs");
	}
	
	public ArrayList<ChatOutputMessageVO> list(int roomId) {
		Query query = new Query(new Criteria("roomId").is(roomId)).limit(20);
		query.with(new Sort(Sort.Direction.DESC, "date"));
		List<ChatMessageVO> list = mongo.find(query, ChatMessageVO.class, "chat_logs");
		ArrayList<ChatOutputMessageVO> arrList = new ArrayList<ChatOutputMessageVO>();
		for (ChatMessageVO x : list) {
			ChatOutputMessageVO chatOutputMessageVO = new ChatOutputMessageVO();
			chatOutputMessageVO.setRoomId(String.valueOf(x.getRoomId()));
			chatOutputMessageVO.setSender(x.getSender());
			chatOutputMessageVO.setContent(x.getContent());
			//mongoDB의 timestamp는 BSON 타입이라 Date로 변환 후 String을 따로 저장하는게 낫다.
			Date date = new Date(x.getDate().getTime() * 1000L);
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			String time = format.format(date);
			chatOutputMessageVO.setTime(time);
			arrList.add(chatOutputMessageVO);
		}
		Collections.reverse(arrList);
		return arrList;
	}
	
	public ChatMessageVO lastMessageList(int roomId) {
		Query query = new Query(new Criteria("roomId").is(roomId)).limit(1);
		query.with(new Sort(Sort.Direction.DESC, "date"));
		ChatMessageVO chatMessageVO = mongo.findOne(query, ChatMessageVO.class, "chat_logs");
		return chatMessageVO;
	}
}