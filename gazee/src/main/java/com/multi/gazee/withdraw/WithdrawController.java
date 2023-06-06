package com.multi.gazee.withdraw;

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
import com.multi.gazee.transactionHistory.TransactionHistoryDAO;

@Controller
public class WithdrawController {
	@Autowired
	WithdrawDAO withdrawDAO;
	
	@Autowired
	MemberDAO memberDAO;
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	TransactionHistoryDAO transactionHistoryDAO;
	
	@RequestMapping(value="pay/withdraw", method = {RequestMethod.POST})
	public void payWithdraw(WithdrawVO withdraw, HttpSession session, HttpServletResponse response) {
		int withdrawResult = 0;
		int transactionResult = 0;
		//1. 멤버 객체 가져오기
		String memberId = String.valueOf(session.getAttribute("id"));
		MemberVO member = memberDAO.selectOne(memberId);
		//2. 잔액 가져오기
		int balance = transactionHistoryDAO.select(memberId);
	
		if(balance>=withdraw.getTotalAmount() && member.getAccount().equals(member.getAccount()) && member.getBank().equals(withdraw.getBank())) {
			withdraw.setMemberId(memberId);
			//3. 거래 시간 가져오기 
			Timestamp transactionTime = transactionService.getTransactionTime();
			withdraw.setTransactionTime(transactionTime);
			//4. 거래 일련번호 생성
			String identifier = transactionService.makeIdentifier("w", member, transactionTime);
			withdraw.setTransactionId(identifier);
			withdrawResult = withdrawDAO.insert(withdraw);
		}else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		if(withdrawResult==1) {
			transactionResult = transactionService.withdrawToTransactionHistory(withdraw, balance);
		}
		if(transactionResult==1) {
			response.setStatus(HttpServletResponse.SC_OK);
		}else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
