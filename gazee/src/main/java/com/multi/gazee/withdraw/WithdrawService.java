package com.multi.gazee.withdraw;

import org.springframework.ui.Model;

public interface WithdrawService {
    String searchWithdraw(String searchType, String searchIndex, Model model) throws Exception;
}
