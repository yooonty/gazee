package com.multi.gazee.transactionHistory;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionHistoryDAO {
    @Autowired
    SqlSessionTemplate my;
    public List<TransactionHistoryVO> listTransactionHistory() {
        List<TransactionHistoryVO> transactionHistoryVo = my.selectList("history.listTransaction");
        return transactionHistoryVo;
    }
    
    public int getBalance(String id){
        int balance = my.selectOne("history.select", id);
        return balance;
    }
}
