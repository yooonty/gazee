package com.multi.gazee.order;

import java.sql.Timestamp;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.service.TransactionService;

@Component
public class OrderDAO {

	@Autowired
	SqlSessionTemplate my;
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	MemberDAO memberDao;
	
	/* 주문 완료 Insert */
	public int orderComplete(OrderVO orderVO, MemberVO memberVO, int paid_amount, int balance) {
		Timestamp transactionTime = transactionService.getTransactionTime();
		String transactionId = transactionService.makeIdentifier("o", memberVO, transactionTime);
		orderVO.setTransactionId(transactionId);
		orderVO.setPaymentTime(transactionService.getTransactionTime());
		int result = my.insert("order.insert", orderVO);
		transactionService.orderToTransactionHistory(orderVO, paid_amount, balance);
		return result;
	}
	
	/* 주문 상태 확인 */
	public OrderVO orderCheck(int productId) {
		OrderVO orderVO = my.selectOne("order.check", productId);
		if (orderVO != null) {
			MemberVO sellerVO = memberDao.selectOne(orderVO.getSellerId());
			MemberVO buyerVO = memberDao.selectOne(orderVO.getBuyerId());
			orderVO.setSellerId(sellerVO.getNickname());
			orderVO.setBuyerId(buyerVO.getNickname());
		}
		return orderVO;
	}
	
	public OrderVO getOrderInfo(int no) {
		OrderVO orderVO = my.selectOne("order.getOrderInfo", no);
		return orderVO;
	}
}
