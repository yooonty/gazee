package com.multi.gazee.withdraw;

import java.util.List;
import java.util.Map;

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
	
    /* ALL */
    public List<WithdrawVO> listWithdraw() {
        List<WithdrawVO> withdrawList = my.selectList("withdraw.listWithdraw");
        return withdrawList;
    }
    
    /* ONE */
    public WithdrawVO oneWithdrawById(String id) {
        return my.selectOne("withdraw.oneWithdrawById", id);
    }
    
    /* 총 수수료 합 (수입) */
    public int sumCommission() {
        Integer totalCommission = my.selectOne("withdraw.sumCommission");
        if (totalCommission == null) {
            return 0;
        } else {
            return totalCommission;
        }
    }
    
    /* 출금내역 내 검색*/
    public List<WithdrawVO> search(Map parameterMap) {
        List<WithdrawVO> search = my.selectList("withdraw.search", parameterMap);
        return search;
    }

}
