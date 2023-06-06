package com.multi.gazee.service;

import com.multi.gazee.order.OrderVO;

public interface OrderService {
	
	int orderComplete(OrderVO orderVO);
	
	OrderVO orderCheck(int productId);
	
	OrderVO getOrderInfo(int no);
}
