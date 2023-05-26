package com.multi.gazee.charge;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.service.TransactionService;

@Controller
public class ChargeController {
	
	@Autowired
	ChargeDAO chargeDAO;
	
	@Autowired
	MemberDAO memberDAO;
	
	@Autowired
	TransactionService transactionService;
	
	@RequestMapping(value="pay/charge", method = {RequestMethod.POST})
	public void payCharge(ChargeVO charge, HttpSession session, HttpServletResponse response) {
		int transactionResult = 0;
		int chargeResult = 0;
		//1. 멤버 객체 가져오기
		String memberId = String.valueOf(session.getAttribute("id"));
		MemberVO member = memberDAO.selectOne(memberId);
		charge.setMemberId(memberId);
		//2. 거래 시간 가져오기 
		Timestamp transactionTime = transactionService.getTransactionTime();
		charge.setTransactionTime(transactionTime);
		//3. 거래 일련번호 생성
		String identifier = transactionService.makeIdentifier("c", member, transactionTime);
		charge.setTransactionId(identifier);
		//4. 충전 테이블 삽입
		chargeResult = chargeDAO.insert(charge);
		//5. TransactionHistory 테이블 삽입
		if (chargeResult==1) {
			transactionResult = transactionService.chargeToTransactionHistory(charge, member.getId());
		}
		//6. 응답 코드 추가
		if (transactionResult==1) {
			response.setStatus(HttpServletResponse.SC_OK);
		}else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
