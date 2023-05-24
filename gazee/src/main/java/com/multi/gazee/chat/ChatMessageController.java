package com.multi.gazee.chat;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChatMessageController {

	@Autowired
	ChatMessageDAO chatMessageDao;
	
	@Autowired
	ChatDAO chatDao;
	
	@RequestMapping("chat/chatMessageList")
	@ResponseBody
	public List<ChatOutputMessageVO> list(int roomId) {
		List<ChatOutputMessageVO> arrList = chatMessageDao.list(roomId);
		return arrList;
	}
	
	@RequestMapping("chat/chatLastMessage")
	@ResponseBody
	public ChatMessageVO lastMeesage(int roomId) {
		ChatMessageVO bag = chatMessageDao.lastMessageList(roomId);
		return bag;
	}
	
	@RequestMapping("chat/lastMessageTimeUpdate")
	@ResponseBody
	public int lastMessageTimeUpdate(int roomId) {
		ChatMessageVO bag = chatMessageDao.lastMessageList(roomId);
		if (bag != null) {
			ChatVO bag2 = new ChatVO();
			Date date = new Date(bag.getDate().getTime() * 1000L);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String timestamp = format.format(date);
			try {
				Date date2 = format.parse(timestamp);
				Timestamp timestamp2 = new Timestamp(date2.getTime());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(timestamp2);
				calendar.add(Calendar.HOUR_OF_DAY, 9);
				Timestamp updatedTime = new Timestamp(calendar.getTimeInMillis());
				bag2.setRoomId(roomId);
				bag2.setLastMessageDate(updatedTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			int result = chatDao.lastMessageTimeUpdate(bag2);
			return result;
		} else {
			return 0;
		}
	}
}
