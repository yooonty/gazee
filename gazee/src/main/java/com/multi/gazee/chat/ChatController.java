package com.multi.gazee.chat;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.product.ProductDAO;
import com.multi.gazee.product.ProductVO;

@Controller
public class ChatController {
	
	@Autowired
	ChatDAO chatDao;
	
	@Autowired
	ChatMessageDAO messageDao;
	
	@Autowired
	MemberDAO memberDao;
	
	@Autowired
	ProductDAO productDao;

	@RequestMapping("chat/chatRoomCreate")
	@ResponseBody
	public int chatRoomCreate(ChatVO bag) {
		int result = chatDao.create(bag);
		if (result != 0) {
			return result;
		} else {
			return 0;
		}
	}
	
	@RequestMapping("chat/chatRoomCheck")
	@ResponseBody
	public int chatRoomCheck(ChatVO bag) {
		ChatVO bag2 = chatDao.chatRoomSearch(bag);
		if (bag2 != null) {
			return bag2.getRoomId();
		} else {
			return 0;
		}
	}
	
	@RequestMapping("chat/myChatRoomIds")
	@ResponseBody
	public ArrayList<Integer> myChatRoomIds(String memberId) {
		List<ChatVO> list = chatDao.chatList(memberId);
		ArrayList<Integer> roomIds = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			roomIds.add(list.get(i).getRoomId());
		}
		return roomIds;
	}
	
	@RequestMapping("chat/chatList")
	public void chatList(String memberId, Model model) {
		List<ChatVO> list = chatDao.chatList(memberId);
		List<String> nickname = new LinkedList<String>();
		List<String> lastMessage = new LinkedList<String>();
		List<String> lastMessageTime = new LinkedList<String>();
		for (int i = 0; i < list.size(); i++) {
			MemberVO sellerVO = memberDao.searchOne(list.get(i).getSellerId());
			MemberVO buyerVO = memberDao.searchOne(list.get(i).getBuyerId());
			if (list.get(i).getBuyerId().equals(memberId)) {
				nickname.add(sellerVO.getNickname());
			} else {
				nickname.add(buyerVO.getNickname());
			}
			int roomId = list.get(i).getRoomId();
			ChatMessageVO bag = messageDao.lastMessageList(roomId);
			if (bag != null) {
				Date date = new Date(bag.getDate().getTime() * 1000L);
				SimpleDateFormat format = new SimpleDateFormat("HH:mm");
				String time = format.format(date);
				lastMessage.add(bag.getContent());
				lastMessageTime.add(time);
			} else {
				lastMessage.add("채팅을 시작해보세요!");
				lastMessageTime.add(" ");
			}
		}
		model.addAttribute("list", list);
		model.addAttribute("nickname", nickname);
		model.addAttribute("lastMessage", lastMessage);
		model.addAttribute("lastMessageTime", lastMessageTime);
	}
	
	@RequestMapping("chat/chatRoomEntry")
	public void chatRoomEntry(int roomId, Model model, HttpSession session) {
		ChatVO bag = chatDao.chatRoomOne(roomId);
		MemberVO sellerVO = memberDao.searchOne(bag.getSellerId());
		MemberVO buyerVO = memberDao.searchOne(bag.getBuyerId());
		ProductVO bag2 = productDao.productone(bag.getProductId());
		DecimalFormat decFormat = new DecimalFormat("###,###");
		String priceDec = decFormat.format(bag2.getPrice());
		if (bag.getDealDirectDate() != null) {
			Timestamp time = bag.getDealDirectDate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(time);
			calendar.add(Calendar.HOUR_OF_DAY, -9);
			Timestamp updatedTime = new Timestamp(calendar.getTimeInMillis());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			String dealDirectDate = format.format(updatedTime);
			model.addAttribute("dealDirectDate", dealDirectDate);
		}
		model.addAttribute("priceDec", priceDec);
		model.addAttribute("dealType", bag.getDealType());
		model.addAttribute("sellerId", bag.getSellerId());
		model.addAttribute("sellerNickname", sellerVO.getNickname());
		model.addAttribute("buyerNickname", buyerVO.getNickname());
		model.addAttribute("bag", bag);
		model.addAttribute("bag2", bag2);
	}
	
	@RequestMapping("chat/roomLeave")
	@ResponseBody
	public void roomLeave(int roomId) {
		chatDao.roomLeave(roomId);
	}
	
	@RequestMapping("chat/chatPaymentModal")
	public void chatPaymentModal(int roomId, Model model) {
		ChatVO bag = chatDao.chatRoomOne(roomId);
		ProductVO bag2 = productDao.productone(bag.getProductId());
		if (bag.getDealDirectDate() != null) {
			Timestamp time = bag.getDealDirectDate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(time);
			calendar.add(Calendar.HOUR_OF_DAY, -9);
			Timestamp updatedTime = new Timestamp(calendar.getTimeInMillis());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String dealDirectDate = format.format(updatedTime);
			model.addAttribute("dealDirectDate", dealDirectDate);
		}
		DecimalFormat decFormat = new DecimalFormat("###,###");
		int balance = 1000;
		int amount = balance - bag2.getPrice();
		int lackAmount = bag2.getPrice() - balance;
		String priceDec = decFormat.format(bag2.getPrice());
		String balanceDec = decFormat.format(balance);
		if (amount >= 0) {
			String amountDec = decFormat.format(amount);
			model.addAttribute("amountDec", amountDec);
		} else {
			String amountDec = decFormat.format(lackAmount);
			model.addAttribute("amountDec", amountDec);
		}
		model.addAttribute("bag", bag);
		model.addAttribute("bag2", bag2);
		model.addAttribute("amount", amount);
		model.addAttribute("dealType", bag.getDealType());
		model.addAttribute("priceDec", priceDec);
		model.addAttribute("balanceDec", balanceDec);
	}
	
	@RequestMapping("chat/dealDirectDateUpdate")
	@ResponseBody
	public void dealDirectDateUpdate(ChatVO bag) {
		Timestamp time = bag.getDealDirectDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.HOUR_OF_DAY, 18);
		Timestamp updatedTime = new Timestamp(calendar.getTimeInMillis());
		bag.setDealDirectDate(updatedTime);
		chatDao.dealDirectDateUpdate(bag);
	}
	
	@PostMapping(value = "chat/saveSubscribedRoomIds", consumes = "application/json")
    @ResponseBody
    public void saveSubscribedRoomIds(HttpSession session, @RequestBody List<String> roomIds) {
		List<Integer> roomIds2 = new LinkedList<Integer>();
	    for (String roomId : roomIds) {
	        roomIds2.add(Integer.parseInt(roomId));
	    }
		session.setAttribute("subscribedRoomIds", roomIds2);
    }
	
	@SuppressWarnings("unchecked")
	@GetMapping("chat/getSubscribedRoomIds")
	@ResponseBody
	public List<Integer> getSubscribedRoomIds(HttpSession session) {
		List<Integer> roomIds = (List<Integer>)session.getAttribute("subscribedRoomIds");
		return roomIds;
	}
}
