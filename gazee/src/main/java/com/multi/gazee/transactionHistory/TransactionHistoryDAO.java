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
    
    /* INSERT */
    public int insert(TransactionHistoryVO bag) {
        int result = my.insert("history.insert", bag);
        return result;
    }
    
    /* ONE */
    public TransactionHistoryVO one(String id) {
        TransactionHistoryVO bag = my.selectOne("history.one",id);
        return bag;
    }
    
    /* 잔액 조회 */
    public int getBalance(String id) {
        int balance;
        try {
            balance = my.selectOne("history.getBalance", id);
        } catch (Exception e) {
            balance = 0;
        }
        return balance;
    }
    
    /* ALL */
    public List<TransactionHistoryVO> listTransactionHistory() {
        List<TransactionHistoryVO> transactionHistoryVo = my.selectList("history.all");
        return transactionHistoryVo;
    }
}
