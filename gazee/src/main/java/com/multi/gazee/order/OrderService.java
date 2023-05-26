package com.multi.gazee.order;

import org.springframework.ui.Model;

public interface OrderService {
    String getOrderList(Model model) throws Exception;
    
    String settlement(String sellerId, Model model) throws Exception;
}
