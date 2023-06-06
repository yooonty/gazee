package com.multi.gazee.withdraw;

import com.multi.gazee.order.OrderVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class WithdrawDAO {
    @Autowired
    SqlSessionTemplate my;
    
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
        int totalCommission = my.selectOne("withdraw.sumCommission");
        return totalCommission;
    }
    
    /* 출금내역 내 검색*/
    public List<WithdrawVO> search(Map parameterMap) {
        List<WithdrawVO> search = my.selectList("withdraw.search", parameterMap);
        return search;
    }
}
