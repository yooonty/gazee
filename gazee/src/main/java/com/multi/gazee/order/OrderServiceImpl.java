package com.multi.gazee.order;

import com.multi.gazee.transactionHistory.TransactionHistoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    OrderDAO Odao;
    
    @Override
    public String getOrderList(Model model) {
        List<OrderVO> orderList = Odao.listOrder();
        model.addAttribute("orderList", orderList);
        return "../admin/adminOrderList";
    }
    
    @Override
    public String getloadOrderInProgressList(Model model) {
        List<OrderVO> orderInProgressList = Odao.listOrderInProgress();
        model.addAttribute("orderInProgressList", orderInProgressList);
        return "../admin/adminOrderInProgressList";
    }
    
    @Override
    public String getOrderFinishedList(Model model) {
        List<OrderVO> orderFinishedList = Odao.listOrderFinished();
        model.addAttribute("orderFinishedList", orderFinishedList);
        return "../admin/adminOrderFinishedList";
    }
    
    @Override
    public String searchOrder(String searchType, String searchIndex, Model model) throws Exception {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("searchType", searchType);
        parameterMap.put("searchIndex", searchIndex);
        
        List<OrderVO> oneWhereList = Odao.search(parameterMap);
        model.addAttribute("searchList", oneWhereList);
        
        return "../admin/adminOrderSearch";
    }
    
}