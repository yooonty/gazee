package com.multi.gazee.order;

import com.multi.gazee.transactionHistory.TransactionHistoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    OrderDAO Odao;
    @Autowired
    TransactionHistoryDAO Tdao;
    
    public String getOrderList(Model model) {
        List<OrderVO> orderList = Odao.listOrder();
        model.addAttribute("orderList", orderList);
        return "../admin/adminOrderList";
    }
    
    public String settlement(String sellerId, Model model) {
        int balance = Tdao.checkBalance(sellerId);
        List<OrderVO> orderList = Odao.listOrder();
        model.addAttribute("orderList", orderList);
        return "정산이 완료되었습니다.";
    }
}
