package com.multi.gazee.service;

import org.springframework.ui.Model;

public interface WithdrawService {
    
    /* ADMIN  출금 목록 */
    String getWithdrawList(Model model) throws Exception;
    
    /* ADMIN 출금 검색 */
    String searchWithdraw(String searchType, String searchIndex, Model model) throws Exception;
    
    /* ADMIN 잔액 READ */
    String searchBalance(String memberId, Model model) throws Exception;
    
    /* ADMIN 회원별 잔액 목록 */
    String getBalanceList(Model model) throws Exception;
}