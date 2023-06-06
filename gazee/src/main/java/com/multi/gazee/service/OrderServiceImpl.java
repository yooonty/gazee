package com.multi.gazee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.gazee.chat.ChatDAO;
import com.multi.gazee.chat.ChatVO;
import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.order.OrderDAO;
import com.multi.gazee.order.OrderVO;
import com.multi.gazee.product.ProductDAO;
import com.multi.gazee.product.ProductVO;
import com.multi.gazee.transactionHistory.TransactionHistoryDAO;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	OrderDAO orderDao;
	
	@Autowired
	ChatDAO chatDao;
	
	@Autowired
	MemberDAO memberDao;
	
	@Autowired
	ProductDAO productDao;
	
	@Autowired
	TransactionHistoryDAO historyDao;
	
	/* 주문 완료 */
	public int orderComplete(OrderVO orderVO) {
		ChatVO chatBag = chatDao.chatRoomOne(orderVO.getRoomId());
		MemberVO memberVO = memberDao.selectOne(chatBag.getBuyerId());
		ProductVO productVO = productDao.productOne(chatBag.getProductId());
		
		orderVO.setDealType(chatBag.getDealType());
		orderVO.setProductId(chatBag.getProductId());
		orderVO.setSellerId(chatBag.getSellerId());
		orderVO.setBuyerId(chatBag.getBuyerId());
		
		int paid_amount = productVO.getPrice();
		int balance = historyDao.select(memberVO.getId());
		
		int result = orderDao.orderComplete(orderVO, memberVO, paid_amount, balance);
		
		return result;
	}
	
	public OrderVO orderCheck(int productId) {
		OrderVO orderVO = orderDao.orderCheck(productId);
		return orderVO;
	}
	
	public OrderVO getOrderInfo(int no) {
		OrderVO orderVO = orderDao.getOrderInfo(no);
		return orderVO;
	}
	
}
