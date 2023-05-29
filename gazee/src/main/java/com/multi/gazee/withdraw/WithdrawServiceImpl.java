package com.multi.gazee.withdraw;

import com.multi.gazee.order.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WithdrawServiceImpl implements WithdrawService {
    
    @Autowired
    WithdrawDAO Wdao;
    
    public String searchWithdraw(String searchType, String searchIndex, Model model) throws Exception {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("searchType", searchType);
        parameterMap.put("searchIndex", searchIndex);
        System.out.println("searchType : " + searchType + ", searchIndex : " + searchIndex);
        
        List<WithdrawVO> oneWhereList = Wdao.oneWhere(parameterMap);
        System.out.println("WithdrawVO : " + oneWhereList);
        model.addAttribute("searchList", oneWhereList);
        
        return "../admin/adminMoneyWithdrawSearch";
    }
    
    public String getWithdrawList(Model model) {
        List<WithdrawVO> withdrawList = Wdao.listWithdraw();
        model.addAttribute("withdrawList", withdrawList);
        return "../admin/adminMoneyWithdrawList";
    }
}

