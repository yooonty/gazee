package com.multi.gazee.service;

import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.order.OrderVO;
import com.multi.gazee.transactionHistory.TransactionHistoryDAO;
import com.multi.gazee.transactionHistory.TransactionHistoryVO;
import com.multi.gazee.withdraw.WithdrawDAO;
import com.multi.gazee.withdraw.WithdrawVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WithdrawServiceImpl implements WithdrawService {
    
    @Autowired
    WithdrawDAO Wdao;
    @Autowired
    MemberDAO Mdao;
    @Autowired
    TransactionHistoryDAO Tdao;
    
    @Override
    public String getWithdrawList(Model model) {
        List<WithdrawVO> withdrawList = Wdao.listWithdraw();
        model.addAttribute("withdrawList", withdrawList);
        
        return "admin/adminMoneyWithdrawList";
    }
    
    @Override
    public String searchWithdraw(String searchType, String searchIndex, Model model) throws Exception {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("searchType", searchType);
        parameterMap.put("searchIndex", searchIndex);
        
        List<WithdrawVO> oneWhereList = Wdao.search(parameterMap);
        model.addAttribute("searchList", oneWhereList);
        
        return "admin/adminMoneyWithdrawSearch";
    }
    
    @Override
    public String searchBalance(String memberId, Model model) throws Exception {
        int balance = Tdao.select(memberId);
        model.addAttribute("memberId", memberId);
        model.addAttribute("balance", balance);
        
        return "admin/adminMoneyBalanceSearch";
    }
    
    @Override
    public String getBalanceList(Model model) {
        List<MemberVO> memberList = Mdao.listExceptAdmin();
        List<Integer> balanceList = new ArrayList<>();
        for (MemberVO vo : memberList) {
            String id = vo.getId();
            Integer balance = Tdao.select(id);
            balanceList.add(balance);
        }
    
        model.addAttribute("memberList", memberList);
        model.addAttribute("balanceList", balanceList);
        return "admin/adminMoneyBalanceList";
    }
}