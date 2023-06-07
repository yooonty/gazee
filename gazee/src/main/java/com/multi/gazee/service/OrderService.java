package com.multi.gazee.service;

import org.springframework.ui.Model;

import com.multi.gazee.order.OrderVO;

public interface OrderService {
	
	int orderComplete(OrderVO orderVO);
	
	OrderVO orderCheck(int productId);
	
	OrderVO getOrderInfo(int no);
	
	 /* ADMIN 거래 목록 */
    String getOrderList(Model model) throws Exception;
   
    /* ADMIN 진행 중 거래 목록 */
    String getloadOrderInProgressList(Model model) throws Exception;
    
    /* ADMIN 종료 된 거래 목록 */
    String getOrderFinishedList(Model model) throws Exception;
    
    /* ADMIN 거래 검색 */
    String searchOrder(String searchType, String searchIndex, Model model) throws Exception;
}
