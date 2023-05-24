package com.multi.gazee.admin.admin;

import com.multi.gazee.charge.ChargeVO;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.set.SetVO;
import com.multi.gazee.transactionHistory.TransactionHistoryVO;
import com.multi.gazee.withdraw.WithdrawVO;
import com.multi.gazee.order.OrderVO;
import com.multi.gazee.product.ProductVO;

import java.util.List;

public interface AdminDAO {
    MemberVO readAdmin() throws Exception;
    
    MemberVO checkAdmin(String email) throws Exception;
    
    void updatePw(MemberVO vo) throws Exception;
    
    List<TransactionHistoryVO> listTransactionHistory() throws Exception;
    
    List<WithdrawVO> listWithdraw() throws Exception;
    
    List<ChargeVO> listCharge() throws Exception;
    
    WithdrawVO oneWithdrawById(String id) throws Exception;
    
    int sumCommission() throws Exception;
    
    List<SetVO> listSet() throws Exception;
    
    List<ProductVO> listProduct() throws Exception;
    
    List<ProductVO> listProductToday() throws Exception;
    
    List<OrderVO> listOrder() throws Exception;
    
    List<OrderVO> listOrderNeedToSet() throws Exception;
    
    List<OrderVO> listOrderFinished() throws Exception;
    
    int sumTotalTrading() throws Exception;
    
    ProductVO productOneById(int id) throws Exception;
    
    List<MemberVO> newMemberThisWeek() throws Exception;
    
    List<MemberVO> memberOfPastThirtyDays() throws Exception;
    
    List<MemberVO> listBankAccount(String id) throws Exception;
    
    List<MemberVO> suspendedList() throws Exception;
    
    MemberVO oneById(String id) throws Exception;
    
    int getBalance(String id) throws Exception;
}

