package com.multi.gazee.customerService;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class CustomerServiceDAO {
    @Autowired
    SqlSessionTemplate my;
    
    public int replyRegister(CustomerServiceVO bag) {
        int result = my.insert("cs.reply", bag);
        return result;
    }
   
    public CustomerServiceVO one(int no) {
        CustomerServiceVO bag = my.selectOne("cs.one",no);
        my.update("cs.viewUpdate", no);
        return bag;
    }
    
    public List<CustomerServiceVO> nonPagedNeedReply() {
        List<CustomerServiceVO> nonPagedNeedReplyList = my.selectList("cs.nonPagedNeedReply");
        return nonPagedNeedReplyList;
    }
    
    public List<CustomerServiceVO> nonPagedList(){
        List<CustomerServiceVO> list = my.selectList("cs.nonPagedList");
        return list;
    }
}
