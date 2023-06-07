package com.multi.gazee.service;

import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.order.OrderDAO;
import com.multi.gazee.product.ProductDAO;
import com.multi.gazee.set.SetDAO;
import com.multi.gazee.set.SetVO;
import com.multi.gazee.transactionHistory.TransactionHistoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class SetServiceImpl implements SetService {
    
    @Autowired
    ProductDAO Pdao;
    @Autowired
    MemberDAO Mdao;
    @Autowired
    SetDAO Sdao;
    @Autowired
    OrderDAO Odao;
    @Autowired
    TransactionHistoryDAO Tdao;
    @Autowired
    TransactionService transactionService;
    
    @Override
    public String settlement(int productId, String sellerId, String orderTransactionId) throws Exception{
        int price = Pdao.priceByOrderNo(productId);
        MemberVO memberVo = Mdao.selectOne(sellerId);
        SetVO setVo = new SetVO();
        //1. 거래 시간 가져오기
        Timestamp transactionTime = transactionService.getTransactionTime();
        //2. 거래 일련번호 생성
        String identifier = transactionService.makeIdentifier("s", memberVo, transactionTime);
        
        setVo.setTransactionId(identifier);
        setVo.setOrderTransactionId(orderTransactionId);
        setVo.setSellerId(sellerId);
        setVo.setTransactionTime(transactionTime);
        setVo.setAmount(price);
    
        //3. Set 테이블 넣기
        Sdao.insert(setVo);
        
        //4. tb_order 테이블 set_status UPDATE
        Odao.updateSetStatus(productId);
    
        //4. TransactionHistory 테이블 넣기
        String setMember = memberVo.getId();
        transactionService.setToTransactionHistory(setVo, setMember);
        
        return "정산이 완료되었습니다.";
    }
}
