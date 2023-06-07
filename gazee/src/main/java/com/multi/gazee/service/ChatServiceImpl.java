package com.multi.gazee.service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.bson.BsonTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.multi.gazee.chat.ChatDAO;
import com.multi.gazee.chat.ChatMessageDAO;
import com.multi.gazee.chat.ChatMessageVO;
import com.multi.gazee.chat.ChatOutputMessageVO;
import com.multi.gazee.chat.ChatVO;
import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.order.OrderDAO;
import com.multi.gazee.order.OrderVO;
import com.multi.gazee.product.ProductDAO;
import com.multi.gazee.product.ProductVO;
import com.multi.gazee.productImage.ProductImageDAO;
import com.multi.gazee.productImage.ProductImageVO;
import com.multi.gazee.transactionHistory.TransactionHistoryDAO;

@Service
public class ChatServiceImpl implements ChatService {
	
	@Autowired
	ChatDAO chatDao;
	
	@Autowired
	ChatMessageDAO messageDao;
	
	@Autowired
	MemberDAO memberDao;
	
	@Autowired
	ProductDAO productDao;
	
	@Autowired
	ProductImageDAO productImageDao;
	
	@Autowired
	OrderDAO orderDao;
	
	@Autowired
	ChatMessageDAO chatMessageDao;
	
	@Autowired
	TransactionHistoryDAO historyDao;
	
	@Autowired
	SimpMessagingTemplate messagingTemplate;
	
	/* 채팅방 생성 */
	public int chatRoomCreate(ChatVO chatVO, HttpSession session) {
		int result = chatDao.create(chatVO); //새로운 채팅방 생성 후
		if (result != 0) {
			@SuppressWarnings("unchecked")
			List<Integer> roomIds = (List<Integer>)session.getAttribute("subscribedRoomIds"); //내 세션을 가져옴
			roomIds.add(result);
			session.setAttribute("subscribedRoomIds", roomIds); //내 세션에 다시 저장
			ChatVO chatVO2 = chatDao.chatRoomOne(result);
			String sellerId = chatVO2.getSellerId(); //판매자 정보를 가져와서
			messagingTemplate.convertAndSend("/topic/" + sellerId, result); //판매자에게 새로운 채팅방 정보 전송
			return result;
		} else {
			return 0;
		}
	}
	
	/* 기존 채팅방이 존재했는지 확인 */
	public int chatRoomCheck(ChatVO chatVO) {
		ChatVO chatVO2 = chatDao.chatRoomSearch(chatVO);
		if (chatVO2 != null) {
			int roomId = chatVO2.getRoomId();
			return roomId;
		} else {
			return 0;
		}
	}
	
	/* 내가 보유한 채팅방 roomId 전부 다 select */
	public ArrayList<Integer> myChatRoomIds(String memberId) {
		List<ChatVO> list = chatDao.chatList(memberId);
		ArrayList<Integer> roomIds = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			roomIds.add(list.get(i).getRoomId());
		}
		return roomIds;
	}
	
	/* 내 채팅 목록 select */
	public void chatList(String memberId, Model model) {
		List<ChatVO> list = chatDao.chatList(memberId);
		List<String> nickname = new LinkedList<String>();
		List<String> lastMessage = new LinkedList<String>();
		List<String> lastMessageTime = new LinkedList<String>();
		List<String> profile = new LinkedList<String>();
		for (int i = 0; i < list.size(); i++) {
			MemberVO sellerVO = memberDao.selectOne(list.get(i).getSellerId());
			MemberVO buyerVO = memberDao.selectOne(list.get(i).getBuyerId());
			if (list.get(i).getBuyerId().equals(memberId)) {
				nickname.add(sellerVO.getNickname());
				profile.add(sellerVO.getProfileImg());
			} else {
				nickname.add(buyerVO.getNickname());
				profile.add(buyerVO.getProfileImg());
			}
			int roomId = list.get(i).getRoomId();
			ChatMessageVO chatMessageVO = messageDao.lastMessageList(roomId);
			if (chatMessageVO != null) {
				//mongoDB의 Timestamp는 Bson타입이라 1000L를 곱해줘야한다.
				Date date = new Date(chatMessageVO.getDate().getTime() * 1000L);
				SimpleDateFormat format = new SimpleDateFormat("HH:mm");
				String time = format.format(date);
				lastMessage.add(chatMessageVO.getContent());
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
		model.addAttribute("profile", profile);
	}
	
	/* 해당 채팅방 입장 */
	public void chatRoomEntry(int roomId, Model model) {
		ChatVO chatVO = chatDao.chatRoomOne(roomId); //채팅방 정보 가져오기
		MemberVO sellerVO = memberDao.selectOne(chatVO.getSellerId()); //판매자 닉네임
		MemberVO buyerVO = memberDao.selectOne(chatVO.getBuyerId()); //구매자 닉네임
		ProductVO productVO = productDao.productOne(chatVO.getProductId()); //해당 채팅방에서의 판매물품 정보 가져오기
		OrderVO orderVO = orderDao.orderCheck(chatVO.getProductId()); //해당 상품이 이미 결제가 되었는지 여부 체크
		ProductImageVO productImageVO = productImageDao.productImageThumbnail(chatVO.getProductId());
		if (productImageVO != null) {
			String thumbnail = productImageVO.getProductImageName();
			String thumbnailAddr = "http://awswlqccbpkd17595311.cdn.ntruss.com/"+thumbnail+"?type=f&w=60&h=60";
			model.addAttribute("thumbnailAddr", thumbnailAddr);
		} else {
			model.addAttribute("thumbnailAddr", "");
		}
		DecimalFormat decFormat = new DecimalFormat("###,###");
		String priceDec = decFormat.format(productVO.getPrice());
		if (chatVO.getDealDirectDate() != null) {
			Timestamp time = chatVO.getDealDirectDate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(time);
			calendar.add(Calendar.HOUR_OF_DAY, -9);
			Timestamp updatedTime = new Timestamp(calendar.getTimeInMillis());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			String dealDirectDate = format.format(updatedTime);
			model.addAttribute("dealDirectDate", dealDirectDate);
		}
		if (orderVO != null && orderVO.getCanceled() == 0) { //결제 후
			if (orderVO.getCompleteStatus() == 1) {
				model.addAttribute("order", "done"); //거래 완료
			} else {
				model.addAttribute("order", "yet"); //거래 전
			}
		} else { //결제 전
			model.addAttribute("order", "null");
		}
		model.addAttribute("priceDec", priceDec);
		model.addAttribute("dealType", chatVO.getDealType());
		model.addAttribute("sellerId", chatVO.getSellerId());
		model.addAttribute("sellerNickname", sellerVO.getNickname());
		model.addAttribute("buyerNickname", buyerVO.getNickname());
		model.addAttribute("chatVO", chatVO);
		model.addAttribute("productVO", productVO);
	}
	
	/* 채팅방 나가기 */
	public void roomLeave(int roomId) {
		chatDao.roomLeave(roomId);
	}
	
	/* 채팅방 내 결제시스템 */
	public void chatPaymentModal(int roomId, Model model) {
		ChatVO chatVO = chatDao.chatRoomOne(roomId);
		ProductVO productVO = productDao.productOne(chatVO.getProductId());
		OrderVO orderVO = orderDao.orderCheck(chatVO.getProductId());
		ProductImageVO productImageVO = productImageDao.productImageThumbnail(chatVO.getProductId());
		if (productImageVO != null) {
			String thumbnail = productImageVO.getProductImageName();
			String thumbnailAddr = "http://awswlqccbpkd17595311.cdn.ntruss.com/"+thumbnail+"?type=f&w=320&h=360";
			model.addAttribute("thumbnailAddr", thumbnailAddr);
		} else {
			model.addAttribute("thumbnailAddr", "");
		}
		if (chatVO.getDealDirectDate() != null) {
			Timestamp time = chatVO.getDealDirectDate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(time);
			calendar.add(Calendar.HOUR_OF_DAY, -9);
			Timestamp updatedTime = new Timestamp(calendar.getTimeInMillis());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String dealDirectDate = format.format(updatedTime);
			model.addAttribute("dealDirectDate", dealDirectDate);
		}
		DecimalFormat decFormat = new DecimalFormat("###,###");
		int balance = historyDao.select(chatVO.getBuyerId());
		int amount = balance - productVO.getPrice();
		int lackAmount = productVO.getPrice() - balance;
		String priceDec = decFormat.format(productVO.getPrice());
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
		model.addAttribute("chatVO", chatVO);
		model.addAttribute("productVO", productVO);
		model.addAttribute("amount", amount);
		model.addAttribute("dealType", chatVO.getDealType());
		model.addAttribute("priceDec", priceDec);
		model.addAttribute("balanceDec", balanceDec);
	}
	
	/* 직거래 시간 업데이트 */
	public void dealDirectDateUpdate(ChatVO chatVO) {
		Timestamp time = chatVO.getDealDirectDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.HOUR_OF_DAY, 18);
		Timestamp updatedTime = new Timestamp(calendar.getTimeInMillis());
		chatVO.setDealDirectDate(updatedTime);
		chatDao.dealDirectDateUpdate(chatVO);
	}
	
	/* 로그인시 내가 보유한 roomIds에 대해 세션 추가 */
	public void saveSubscribedRoomIds(HttpSession session, List<String> roomIds) {
		List<Integer> roomIds2 = new LinkedList<Integer>();
	    for (String roomId : roomIds) {
	        roomIds2.add(Integer.parseInt(roomId));
	    }
		session.setAttribute("subscribedRoomIds", roomIds2);
	}
	
	/* 로그인 제외 모든 페이지에서의 roomIds 세션 가져오기 */
	public List<Integer> getSubscribedRoomIds(HttpSession session) {
		@SuppressWarnings("unchecked")
		List<Integer> roomIds = (List<Integer>)session.getAttribute("subscribedRoomIds");
		return roomIds;
	}
	
	/* 새로운 채팅방 세션 업데이트 */
	public List<Integer> addChatRoomIdToSession(HttpSession session, String roomId) {
		@SuppressWarnings("unchecked")
		List<Integer> roomIds = (List<Integer>)session.getAttribute("subscribedRoomIds");
		roomIds.add(Integer.parseInt(roomId));
		session.setAttribute("subscribedRoomIds", roomIds);
		return roomIds;
	}
	
	/* 채팅메세지 내역 */
	public List<ChatOutputMessageVO> chatMessageList(int roomId) {
		List<ChatOutputMessageVO> arrList = chatMessageDao.list(roomId);
		return arrList;
	}
	
	/* 마지막 채팅 메세지 */
	public ChatMessageVO chatLastMessage(int roomId) {
		ChatMessageVO chatMessageVO = chatMessageDao.lastMessageList(roomId);
		return chatMessageVO;
	}
	
	/* 마지막 채팅 시간 업데이트 */
	public int lastMessageTimeUpdate(int roomId) {
		ChatMessageVO chatMessageVO = chatMessageDao.lastMessageList(roomId);
		if (chatMessageVO != null) {
			ChatVO chatVO = new ChatVO();
			Date date = new Date(chatMessageVO.getDate().getTime() * 1000L);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String timestamp = format.format(date);
			try {
				Date date2 = format.parse(timestamp);
				Timestamp timestamp2 = new Timestamp(date2.getTime());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(timestamp2);
				calendar.add(Calendar.HOUR_OF_DAY, 9);
				Timestamp updatedTime = new Timestamp(calendar.getTimeInMillis());
				chatVO.setRoomId(roomId);
				chatVO.setLastMessageDate(updatedTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			int result = chatDao.lastMessageTimeUpdate(chatVO);
			return result;
		} else {
			return 0;
		}
	}
	
	/* 메세지 전송 output */
	public ChatOutputMessageVO chatMessage(ChatMessageVO message, String roomId) {
		BsonTimestamp timestamp = new BsonTimestamp((int) (System.currentTimeMillis() / 1000), 0);
		message.setDate(timestamp);
		message.setRoomId(Integer.parseInt(roomId));
		chatMessageDao.insert(message);
		ChatOutputMessageVO output = new ChatOutputMessageVO();
		output.setRoomId(roomId);
		output.setSender(message.getSender());
		output.setContent(message.getContent());
		SimpleDateFormat simple2 = new SimpleDateFormat("HH:mm");
		output.setTime(simple2.format(new Timestamp(System.currentTimeMillis())));
		return output;
	}
	
	/* 채팅방 selectOne */
	public ChatVO chatSelectOne(int roomId) {
		ChatVO chatVO = chatDao.chatRoomOne(roomId);
		return chatVO;
	}
	
	/* 안읽은 메세지 체크 */
	public List<ChatVO> unreadMessageCheck(String memberId) {
		List<ChatVO> list = chatDao.unreadMessageCheck(memberId);
		return list;
	}
}
