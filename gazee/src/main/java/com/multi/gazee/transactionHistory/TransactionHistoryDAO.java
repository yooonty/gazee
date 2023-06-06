package com.multi.gazee.transactionHistory;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionHistoryDAO {
	
	@Autowired
	SqlSessionTemplate my;
	
    
	public int select(String id) {

		int balance;

		try {
			balance = my.selectOne("history.select", id);
		} catch (Exception e) {
			balance = 0;
		}
		
		return balance;
	}
	
	public int insert(TransactionHistoryVO historyVO) {
		return my.insert("history.insert", historyVO);
	}
	
	public List<TransactionHistoryVO> selectList(HashMap<String, String> selectCondition) {
		return my.selectList("history.selectList", selectCondition);
	}

}
