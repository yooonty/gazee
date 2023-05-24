package com.multi.gazee.admin.admin;

import com.multi.gazee.charge.ChargeVO;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.set.SetVO;
import com.multi.gazee.transactionHistory.TransactionHistoryVO;
import com.multi.gazee.withdraw.WithdrawVO;
import com.multi.gazee.order.OrderVO;
import com.multi.gazee.product.ProductVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminDAOImpl implements AdminDAO {
    @Autowired
    SqlSessionTemplate my;
    
    @Override
    public MemberVO readAdmin() throws Exception {
        return my.selectOne("admin.readAdmin");
    }
    
    @Override
    public MemberVO checkAdmin(String email) throws Exception {
        return my.selectOne("admin.readAdmin", email);
    }
    
    @Override
    public void updatePw(MemberVO vo) throws Exception {
        int result = my.update("admin.updatePw", vo);
    }
    
    @Override
    public List<TransactionHistoryVO> listTransactionHistory() {
        List<TransactionHistoryVO> transactionHistoryVo = my.selectList("admin.listTransaction");
        return transactionHistoryVo;
    }
    
    @Override
    public List<WithdrawVO> listWithdraw() {
        List<WithdrawVO> withdrawList = my.selectList("admin.listWithdraw");
        return withdrawList;
    }
    
    @Override
    public List<ChargeVO> listCharge() {
        List<ChargeVO> chargeList = my.selectList("admin.listCharge");
        return chargeList;
    }
    
    @Override
    public List<WithdrawVO> listWithdrawNeedConfirm() {
        List<WithdrawVO> withdrawNeedConfirmList = my.selectList("admin.listWithdrawNeedConfirm");
        return withdrawNeedConfirmList;
    }
    
    @Override
    public List<WithdrawVO> listWithdrawComplete() {
        List<WithdrawVO> withdrawCompleteList = my.selectList("admin.listWithdrawComplete");
        return withdrawCompleteList;
    }
    
    @Override
    public WithdrawVO oneWithdrawById(String id) {
        return my.selectOne("admin.oneWithdrawById", id);
    }
    
    @Override
    public int withdrawComplete(WithdrawVO bag) {
        return my.update("admin.withdrawComplete", bag);
    }
    
    @Override
    public int sumCommission() {
        int totalCommission = my.selectOne("admin.sumCommission");
        return totalCommission;
    }
    
    @Override
    public List<SetVO> listSet() {
        List<SetVO> setList = my.selectList("admin.listSet");
        return setList;
    }
    
    @Override
    public List<ProductVO> listProduct() {
        List<ProductVO> productList = my.selectList("product.all");
        return productList;
    }
    
    @Override
    public List<ProductVO> listProductToday() {
        List<ProductVO> productTodayList = my.selectList("admin.listProductToday");
        return productTodayList;
    }
    
    @Override
    public List<OrderVO> listOrder() {
        List<OrderVO> orderList = my.selectList("admin.listOrder");
        return orderList;
    }
    
    @Override
    public List<OrderVO> listOrderNeedToSet() {
        List<OrderVO> orderNeedToSetList = my.selectList("admin.listOrderNeedToSet");
        return orderNeedToSetList;
    }
    
    @Override
    public List<OrderVO> listOrderFinished() {
        List<OrderVO> orderFinishedList = my.selectList("admin.listOrderFinished");
        return orderFinishedList;
    }
    
    @Override
    public int sumTotalTrading() {
        int totalTrading = my.selectOne("admin.sumTotalTrading");
        return totalTrading;
    }
    
    @Override
    public ProductVO productOneById(int id) {
        ProductVO productOneById = my.selectOne("admin.productOneById", id);
        return productOneById;
    }
    
    @Override
    public List<MemberVO> newMemberThisWeek() {
        List<MemberVO> newMemberThisWeekList = my.selectList("admin.newMemberThisWeek");
        return newMemberThisWeekList;
    }
    
    @Override
    public List<MemberVO> memberOfPastThirtyDays() {
        List<MemberVO> memberOfPastThirtyDaysList = my.selectList("admin.memberOfPastThirtyDays");
        return memberOfPastThirtyDaysList;
    }
    
    @Override
    public List<MemberVO> listBankAccount(String id) {
        List<MemberVO> bankAccountList = my.selectList("admin.listBankAccount", id);
        return bankAccountList;
    }
    
    @Override
    public List<MemberVO> suspendedList() {
        List<MemberVO> suspendedList = my.selectList("admin.suspended");
        return suspendedList;
    }
    
    @Override
    public MemberVO oneById(String id) {
        MemberVO oneById = my.selectOne("admin.oneById", id);
        return oneById;
    }
    
    @Override
    public int getBalance(String id){
        int balance = my.selectOne("admin.balance", id);
        return balance;
    }
}

