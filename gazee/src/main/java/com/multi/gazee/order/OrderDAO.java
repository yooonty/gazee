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
    
    public List<OrderVO> listOrder() {
        List<OrderVO> orderList = my.selectList("order.listOrder");
        return orderList;
    }
    
    public List<OrderVO> listOrderNeedToSet() {
        List<OrderVO> orderNeedToSetList = my.selectList("order.listOrderNeedToSet");
        return orderNeedToSetList;
    }
    
    public void updateSetStatus(int productId) {
        my.selectOne("order.updateSetStatus", productId);
    }
    
    public List<OrderVO> listOrderFinished() {
        List<OrderVO> orderFinishedList = my.selectList("order.listOrderFinished");
        return orderFinishedList;
    }
    
    public int sumTotalTrading() {
        int totalTrading = my.selectOne("order.sumTotalTrading");
        return totalTrading;
    }
    
    public List<OrderVO> oneWhere(Map parameterMap) {
        List<OrderVO> oneWhere = my.selectList("order.oneWhere", parameterMap);
        return oneWhere;
    }
    
}
