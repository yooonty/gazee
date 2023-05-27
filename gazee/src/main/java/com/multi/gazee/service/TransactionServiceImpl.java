package com.multi.gazee.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.gazee.charge.ChargeVO;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.order.OrderVO;
import com.multi.gazee.set.SetVO;
import com.multi.gazee.transactionHistory.TransactionHistoryDAO;
import com.multi.gazee.transactionHistory.TransactionHistoryVO;
import com.multi.gazee.withdraw.WithdrawVO;

@Service
public class TransactionServiceImpl implements TransactionService{

	@Autowired
	TransactionHistoryDAO historyDAO;
	
	@Override
	public String makeIdentifier(String transactionType, MemberVO memberVO, Timestamp transactionTime) {
		String identifier = transactionType + String.format("%010d", memberVO.getNo()) + memberVO.getId().substring(0, 2) 
				+ new SimpleDateFormat("yyMMddHHmmss").format(transactionTime);
		return identifier;
	}

	@Override
	public Timestamp getTransactionTime() {
		ZoneId zoneId = ZoneId.of("Asia/Seoul");

		// 표준 시간대에 맞는 현재 시간 생성
		ZonedDateTime zonedDateTime = LocalDateTime.now().atZone(zoneId);
		LocalDateTime currentDateTime = zonedDateTime.toLocalDateTime();

		// 표준 시간대에 맞는 Timestamp 생성
		Timestamp transactionTime = Timestamp.valueOf(currentDateTime);
		return transactionTime;
	}
	
	@Override
	public int chargeToTransactionHistory(ChargeVO chargeVO, String id) {
		TransactionHistoryVO transactionHistoryVO = new TransactionHistoryVO();
		
		transactionHistoryVO.setTransactionId(chargeVO.getTransactionId());
		transactionHistoryVO.setMemberId(chargeVO.getMemberId());
		transactionHistoryVO.setTransactionTime(chargeVO.getTransactionTime());
		transactionHistoryVO.setAmount(chargeVO.getAmount());
		transactionHistoryVO.setBalance(historyDAO.select(id)+chargeVO.getAmount());
		return historyDAO.insert(transactionHistoryVO);
//		return transactionHistoryVO;
	}
	
	@Override
	public int withdrawToTransactionHistory(WithdrawVO withdrawVO, int balance) {
		TransactionHistoryVO transactionHistoryVO = new TransactionHistoryVO();

		transactionHistoryVO.setTransactionId(withdrawVO.getTransactionId());
		transactionHistoryVO.setMemberId(withdrawVO.getMemberId());
		transactionHistoryVO.setTransactionTime(withdrawVO.getTransactionTime());
		transactionHistoryVO.setAmount(withdrawVO.getTotalAmount());
		transactionHistoryVO.setBalance(balance-withdrawVO.getTotalAmount());
		return historyDAO.insert(transactionHistoryVO);
	};
	
	@Override
	public int orderToTransactionHistory(OrderVO orderVO, int paid_amount, int balance) {
		TransactionHistoryVO transactionHistoryVO = new TransactionHistoryVO();

		transactionHistoryVO.setTransactionId(orderVO.getTransactionId());
		transactionHistoryVO.setMemberId(orderVO.getBuyerId());
		transactionHistoryVO.setTransactionTime(orderVO.getPaymentTime());
		transactionHistoryVO.setAmount(paid_amount);
		transactionHistoryVO.setBalance(balance-paid_amount);
		return historyDAO.insert(transactionHistoryVO);
	};
	
	@Override
	public int setToTransactionHistory(SetVO setVO, String id) {
		TransactionHistoryVO transactionHistoryVO = new TransactionHistoryVO();

		transactionHistoryVO.setTransactionId(setVO.getTransactionId());
		transactionHistoryVO.setMemberId(setVO.getSellerId());
		transactionHistoryVO.setTransactionTime(setVO.getTransactionTime());
		transactionHistoryVO.setAmount(setVO.getAmount());
		transactionHistoryVO.setBalance(historyDAO.select(id)+setVO.getAmount());
		return historyDAO.insert(transactionHistoryVO);
	};
	

}