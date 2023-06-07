package com.multi.gazee.order;

import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.transactionHistory.TransactionService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Component
public class OrderDAO {

	@Autowired
	SqlSessionTemplate my;
	
	@Autowired
    TransactionService transactionService;
	
	@Autowired
	MemberDAO memberDao;
	
	/* 주문 완료 Insert */
	public int orderComplete(OrderVO orderVO, MemberVO memberVO, int paid_amount, int balance) {
		Timestamp transactionTime = transactionService.getTransactionTime();
		String transactionId = transactionService.makeIdentifier("o", memberVO, transactionTime);
		orderVO.setTransactionId(transactionId);
		orderVO.setPaymentTime(transactionService.getTransactionTime());
		int result = my.insert("order.insert", orderVO);
		transactionService.orderToTransactionHistory(orderVO, paid_amount, balance);
		return result;
	}
	
	/* 주문 상태 확인 */
	public OrderVO orderCheck(int productId) {
		OrderVO orderVO = my.selectOne("order.check", productId);
		if (orderVO != null) {
			MemberVO sellerVO = memberDao.one(orderVO.getSellerId());
			MemberVO buyerVO = memberDao.one(orderVO.getBuyerId());
			orderVO.setSellerId(sellerVO.getNickname());
			orderVO.setBuyerId(buyerVO.getNickname());
		}
		return orderVO;
	}
	
	public OrderVO getOrderInfo(int no) {
		OrderVO orderVO = my.selectOne("order.getOrderInfo", no);
		return orderVO;
	}
	
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
        Integer totalTrading = my.selectOne("order.sumTotalTrading");
        if (totalTrading == null) {
            return 0;
        } else {
            return totalTrading;
        }
    }
    
    /* 거래관리 내 검색 */
    public List<OrderVO> search(Map parameterMap) {
        List<OrderVO> search = my.selectList("order.search", parameterMap);
        return search;
    }
}
