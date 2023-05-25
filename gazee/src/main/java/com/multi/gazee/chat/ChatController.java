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
import com.multi.gazee.order.OrderDAO;
import com.multi.gazee.order.OrderVO;
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
	
	@Autowired
	OrderDAO orderDao;
	
	/* 채팅방 생성 */
	@SuppressWarnings("unchecked")
	@RequestMapping("chat/chatRoomCreate")
	@ResponseBody
	public int chatRoomCreate(ChatVO bag, HttpSession session) {
		String userId = (String)session.getAttribute("id");
		int result = chatDao.create(bag); //새로운 채팅방 생성 후
		if (result != 0) {
			List<Integer> roomIds = (List<Integer>)session.getAttribute(userId); //내 세션을 가져옴
			roomIds.add(result);
			session.setAttribute(userId, roomIds); //내 세션에 다시 저장
			return result;
		} else {
			return 0;
		}
	}
	
	/* 채팅방이 기존에 존재했는지 확인 */
	@RequestMapping("chat/chatRoomCheck")
	@ResponseBody
	public int chatRoomCheck(ChatVO bag) {
		ChatVO bag2 = chatDao.chatRoomSearch(bag);
		if (bag2 != null) {
			int roomId = bag2.getRoomId();
			return roomId;
		} else {
			return 0;
		}
	}
	
	/* 내가 보유한 채팅방 roomId 전부 다 select */
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
	
	/* 내 채팅 목록 select */
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
				//mongoDB의 Timestamp는 Bson타입이라 1000L를 곱해줘야한다.
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
	
	/* 해당 채팅방 입장 */
	@RequestMapping("chat/chatRoomEntry")
	public void chatRoomEntry(int roomId, Model model, HttpSession session) {
		ChatVO bag = chatDao.chatRoomOne(roomId); //채팅방 정보 가져오기
		MemberVO sellerVO = memberDao.searchOne(bag.getSellerId()); //판매자 닉네임
		MemberVO buyerVO = memberDao.searchOne(bag.getBuyerId()); //구매자 닉네임
		ProductVO productVO = productDao.productone(bag.getProductId()); //해당 채팅방에서의 판매물품 정보 가져오기
		OrderVO orderVO = orderDao.orderCheck(bag.getProductId()); //해당 상품이 이미 결제가 되었는지 여부 체크
		DecimalFormat decFormat = new DecimalFormat("###,###");
		String priceDec = decFormat.format(productVO.getPrice());
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
		if (orderVO != null) { //결제 후
			if (orderVO.getCompleteStatus() == 1) {
				model.addAttribute("order", "done"); //거래 완료
			} else {
				model.addAttribute("order", "yet"); //거래 전
			}
		} else { //결제 전
			model.addAttribute("order", "null");
		}
		model.addAttribute("priceDec", priceDec);
		model.addAttribute("dealType", bag.getDealType());
		model.addAttribute("sellerId", bag.getSellerId());
		model.addAttribute("sellerNickname", sellerVO.getNickname());
		model.addAttribute("buyerNickname", buyerVO.getNickname());
		model.addAttribute("bag", bag);
		model.addAttribute("bag2", productVO);
	}
	
	/* 채팅방 나가기 */
	@RequestMapping("chat/roomLeave")
	@ResponseBody
	public void roomLeave(int roomId) {
		chatDao.roomLeave(roomId);
	}
	
	/* 채팅방 내 결제시스템 */
	@RequestMapping("chat/chatPaymentModal")
	public void chatPaymentModal(int roomId, Model model) {
		ChatVO bag = chatDao.chatRoomOne(roomId);
		ProductVO bag2 = productDao.productone(bag.getProductId());
		OrderVO orderVO = orderDao.orderCheck(bag.getProductId());
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
		int balance = 50000;
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
		if (orderVO != null) { //결제 후
			model.addAttribute("order", "done");
		} else { //결제 전
			model.addAttribute("order", "null");
		}
		model.addAttribute("bag", bag);
		model.addAttribute("bag2", bag2);
		model.addAttribute("amount", amount);
		model.addAttribute("dealType", bag.getDealType());
		model.addAttribute("priceDec", priceDec);
		model.addAttribute("balanceDec", balanceDec);
	}
	
	/* 직거래 시간 업데이트 */
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
	
	/* 로그인시 내가 보유한 roomIds에 대해 세션 추가 */
	@PostMapping(value = "chat/saveSubscribedRoomIds", consumes = "application/json")
    @ResponseBody
    public void saveSubscribedRoomIds(HttpSession session, @RequestBody List<String> roomIds) {
		List<Integer> roomIds2 = new LinkedList<Integer>();
		String userId = (String)session.getAttribute("id");
	    for (String roomId : roomIds) {
	        roomIds2.add(Integer.parseInt(roomId));
	    }
		session.setAttribute(userId, roomIds2);
    }
	
	/* 로그인 제외 모든 페이지에서의 roomIds 세션 가져오기 */
	@SuppressWarnings("unchecked")
	@GetMapping("chat/getSubscribedRoomIds")
	@ResponseBody
	public List<Integer> getSubscribedRoomIds(HttpSession session) {
		String userId = (String)session.getAttribute("id");
		List<Integer> roomIds = (List<Integer>)session.getAttribute(userId);
		return roomIds;
	}
}
