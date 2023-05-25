package com.multi.gazee.charge;

import java.sql.Timestamp;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.multi.gazee.member.MemberVO;
import com.multi.gazee.service.TransactionService;
import com.multi.gazee.transactionHistory.TransactionHistoryDAO;
import com.multi.gazee.transactionHistory.TransactionHistoryVO;

@Component
public class ChargeDAO {
	
	@Autowired
	SqlSessionTemplate my;
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	TransactionHistoryDAO historyDAO;
	
	public void charge(ChargeVO charge, MemberVO member) {
		//1. 거래 시간 가져오기 
		Timestamp transactionTime = transactionService.getTransactionTime();
		charge.setTransactionTime(transactionTime);
		//2. 거래 일련번호 생성
		String identifier = transactionService.makeIdentifier("c", member, transactionTime);
		charge.setTransactionId(identifier);
		//3. Charge 테아블 넣기
		my.insert("charge.insert", charge);
		//4. TransactionHistory 테이블 넣기
		transactionService.chargeToTransactionHistory(charge, member.getId());
	}
	
}