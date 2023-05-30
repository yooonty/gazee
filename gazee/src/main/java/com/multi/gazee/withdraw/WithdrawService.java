package com.multi.gazee.withdraw;

import org.springframework.ui.Model;

public interface WithdrawService {
    String searchWithdraw(String searchType, String searchIndex, Model model) throws Exception;
    String searchBalance(String memberId, Model model) throws Exception;
    String getBalanceList(Model model) throws Exception;
    String getWithdrawList(Model model) throws Exception;
}
