package com.multi.gazee.withdraw;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.multi.gazee.transactionHistory.TransactionHistoryDAO;

@Component
public class WithdrawDAO {
	@Autowired
	SqlSessionTemplate my;
	
	@Autowired
	TransactionHistoryDAO transactionHistoryDAO;
	
	public int insert(WithdrawVO withdraw) {
		return my.insert("withdraw.insert", withdraw);
		}
}
