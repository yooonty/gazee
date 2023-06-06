package com.multi.gazee.order;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OrderDAO {
    @Autowired
    SqlSessionTemplate my;
    
    /* ALL */
    public List<OrderVO> listOrder() {
        List<OrderVO> orderList = my.selectList("order.listOrder");
        return orderList;
    }
    
    /* 최근 거래 목록 */
    public List<OrderVO> recentOrder() {
        List<OrderVO> orderList = my.selectList("order.recent");
        return orderList;
    }
    
    /* 정산이 필요한 거래 목록 */
    public List<OrderVO> listOrderNeedToSet() {
        List<OrderVO> orderNeedToSetList = my.selectList("order.listOrderNeedToSet");
        return orderNeedToSetList;
    }
    
    /* 정산 상태 변경 */
    public void updateSetStatus(int productId) {
        my.selectOne("order.updateSetStatus", productId);
    }
    
    /* 진행 중인 거래 목록 */
    public List<OrderVO> listOrderInProgress() {
        List<OrderVO> orderInProgressList = my.selectList("order.listOrderInProgress");
        return orderInProgressList;
    }
    
    /* 종료 된 거래 목록 */
    public List<OrderVO> listOrderFinished() {
        List<OrderVO> orderFinishedList = my.selectList("order.listOrderFinished");
        return orderFinishedList;
    }
    
    /* 완료 된 총 거래 금액 */
    public int sumTotalTrading() {
        int totalTrading = my.selectOne("order.sumTotalTrading");
        return totalTrading;
    }
    
    /* 거래관리 내 검색 */
    public List<OrderVO> search(Map parameterMap) {
        List<OrderVO> search = my.selectList("order.search", parameterMap);
        return search;
    }
    
}
