package com.multi.gazee.transactionHistory;

import com.multi.gazee.report.ReportVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class TransactionHistoryDAO {
    @Autowired
    SqlSessionTemplate my;
    
    public int insert(TransactionHistoryVO bag) {
        int result = my.insert("history.insert", bag);
        return result;
    }
    
    public TransactionHistoryVO one(String id) {
        TransactionHistoryVO bag = my.selectOne("history.one",id);
        return bag;
    }
    
    public int select(String id) {
        int result = my.selectOne("history.select", id);
        return result;
    }
    
    public List<TransactionHistoryVO> listTransactionHistory() {
        List<TransactionHistoryVO> transactionHistoryVo = my.selectList("history.all");
        return transactionHistoryVo;
    }
    
    public int checkBalance(String id) {
        int balance;
        
        try {
            balance = my.selectOne("history.select", id);
        } catch (Exception e) {
            balance = 0;
        }
        
        return balance;
    }
}
