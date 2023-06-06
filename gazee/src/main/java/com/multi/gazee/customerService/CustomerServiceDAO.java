package com.multi.gazee.customerService;

import com.multi.gazee.admin.paging.PageVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class CustomerServiceDAO {
    @Autowired
    SqlSessionTemplate my;
    
    
    /* 문의 답변 등록 */
    public int replyRegister(CustomerServiceVO bag) {
        int result = my.insert("cs.reply", bag);
        return result;
    }
   
    /* 문의 One + 조회수 증가 */
    public CustomerServiceVO one(int no) {
        CustomerServiceVO bag = my.selectOne("cs.one",no);
        my.update("cs.viewUpdate", no);
        return bag;
    }
    
    public List<CustomerServiceVO> nonPagedNeedReply() {
        List<CustomerServiceVO> nonPagedNeedReplyList = my.selectList("cs.nonPagedNeedReply");
        return nonPagedNeedReplyList;
    }
    
    public List<CustomerServiceVO> pagedNeedReply(PageVO pageVo) {
        List<CustomerServiceVO> pagedCsNeedReplyList = my.selectList("cs.pagedNeedReply", pageVo);
        return pagedCsNeedReplyList;
    }
    
    public int countPagedNeedReply() {
        return my.selectOne("cs.countPagedNeedReply");
    }
    
    public int count() {
        return my.selectOne("cs.count");
    }
    
    
    public List<CustomerServiceVO> nonPagedList(){
        List<CustomerServiceVO> list = my.selectList("cs.nonPagedList");
        return list;
    }
    
    public List<CustomerServiceVO> pagedList(PageVO pageVo){
        List<CustomerServiceVO> list = my.selectList("cs.pagedList", pageVo);
        return list;
    }
}