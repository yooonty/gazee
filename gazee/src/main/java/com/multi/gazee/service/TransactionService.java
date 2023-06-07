package com.multi.gazee.service;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.multi.gazee.charge.ChargeVO;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.order.OrderVO;
import com.multi.gazee.set.SetVO;
import com.multi.gazee.withdraw.WithdrawVO;
import com.multi.gazee.transactionHistory.TransactionHistoryVO;

public interface TransactionService {
	String makeIdentifier(String transactionType, MemberVO memberVO, Timestamp transactionTime);
	
	Timestamp getTransactionTime();
	
	int chargeToTransactionHistory(ChargeVO chargeVO, String id);

	int withdrawToTransactionHistory(WithdrawVO woithdrawVO, int balance);
	
	int orderToTransactionHistory(OrderVO orderVO, int paid_amount, int balance);
	
	int setToTransactionHistory(SetVO setVO, String id);
	
	List<TransactionRecordVO> changeFormat(List<TransactionHistoryVO> transactionRecord, HttpSession session);
	
	boolean checkRefundAvailable(ChargeVO charge, HttpSession session);
	
	boolean checkCanceled(ChargeVO charge);

	boolean checkUser(ChargeVO charge, HttpSession session);

	boolean checkTransactionTime(ChargeVO charge);
	
	String timestampToString(Timestamp timestamp);
}