package com.multi.gazee.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.multi.gazee.chat.ChatDAO;
import com.multi.gazee.chat.ChatVO;
import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.order.OrderDAO;
import com.multi.gazee.order.OrderVO;
import com.multi.gazee.product.ProductDAO;
import com.multi.gazee.product.ProductVO;
import com.multi.gazee.transactionHistory.TransactionHistoryDAO;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	OrderDAO orderDao;
	
	@Autowired
	ChatDAO chatDao;
	
	@Autowired
	MemberDAO memberDao;
	
	@Autowired
	ProductDAO productDao;
	
	@Autowired
	TransactionHistoryDAO historyDao;
	
	/* 주문 완료 */
	public int orderComplete(OrderVO orderVO) {
		ChatVO chatBag = chatDao.chatRoomOne(orderVO.getRoomId());
		MemberVO memberVO = memberDao.selectOne(chatBag.getBuyerId());
		ProductVO productVO = productDao.productOne(chatBag.getProductId());
		
		orderVO.setDealType(chatBag.getDealType());
		orderVO.setProductId(chatBag.getProductId());
		orderVO.setSellerId(chatBag.getSellerId());
		orderVO.setBuyerId(chatBag.getBuyerId());
		
		int paid_amount = productVO.getPrice();
		int balance = historyDao.select(memberVO.getId());
		
		int result = orderDao.orderComplete(orderVO, memberVO, paid_amount, balance);
		
		return result;
	}
	
	public OrderVO orderCheck(int productId) {
		OrderVO orderVO = orderDao.orderCheck(productId);
		return orderVO;
	}
	
	public OrderVO getOrderInfo(int no) {
		OrderVO orderVO = orderDao.getOrderInfo(no);
		return orderVO;
	}
	
	@Override
    public String getOrderList(Model model) {
        List<OrderVO> orderList = orderDao.listOrder();
        model.addAttribute("orderList", orderList);
        return "admin/adminOrderList";
    }
    
    @Override
    public String getloadOrderInProgressList(Model model) {
        List<OrderVO> orderInProgressList = orderDao.listOrderInProgress();
        model.addAttribute("orderInProgressList", orderInProgressList);
        return "admin/adminOrderInProgressList";
    }
    
    @Override
    public String getOrderFinishedList(Model model) {
        List<OrderVO> orderFinishedList = orderDao.listOrderFinished();
        model.addAttribute("orderFinishedList", orderFinishedList);
        return "admin/adminOrderFinishedList";
    }
    
    @Override
    public String searchOrder(String searchType, String searchIndex, Model model) throws Exception {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("searchType", searchType);
        parameterMap.put("searchIndex", searchIndex);
        
        List<OrderVO> oneWhereList = orderDao.search(parameterMap);
        model.addAttribute("searchList", oneWhereList);
        
        return "admin/adminOrderSearch";
    }
	
}
