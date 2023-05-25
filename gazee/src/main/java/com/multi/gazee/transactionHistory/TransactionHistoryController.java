package com.multi.gazee.transactionHistory;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TransactionHistoryController {
	@Autowired
	TransactionHistoryDAO dao;
	
	@RequestMapping("pay/record")
	@ResponseBody
	public String checkBalance(HttpSession session) {
		session.setAttribute("id", "testuser");
		String balance = String.valueOf(dao.select(String.valueOf(session.getAttribute("id"))));
		return balance;
	}
}
