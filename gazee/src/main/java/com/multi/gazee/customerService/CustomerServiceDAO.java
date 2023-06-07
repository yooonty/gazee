package com.multi.gazee.customerService;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.multi.gazee.admin.paging.AdminPageVO;



@Component
public class CustomerServiceDAO {

	@Autowired
	SqlSessionTemplate my;
	
	public int csRegister(CustomerServiceVO bag) {
		int result = my.insert("cs.register", bag);
		return result;
	}
	
	public void csDelete(CustomerServiceVO bag) {
		my.delete("cs.del", bag);
	}
	
	public void csUpdate(CustomerServiceVO bag) {
		my.update("cs.up", bag);
	}
	
	public CustomerServiceVO one(int no) {
		CustomerServiceVO bag = my.selectOne("cs.one",no);
		my.update("cs.viewUpdate", no);
		return bag;
	}
	
	public List<CustomerServiceVO> list(PageVO vo){
		List<CustomerServiceVO> list = my.selectList("cs.all", vo);
		return list;
	}
	
	public List<CustomerServiceVO> category(HashMap<String, Object> map){
		List<CustomerServiceVO> category = my.selectList("cs.category", map);
		return category;
	}
	
	
	public List<CustomerServiceVO> search(HashMap<String, Object> map){
		List<CustomerServiceVO> search = my.selectList("cs.searchAll", map);
		return search;
	}
	
	
	public int count() {
		return my.selectOne("cs.count");
	}
	
	public int countCategory(String category1) {
		return my.selectOne("cs.countCategory", category1);
	}  
	
	public int countSearch(String search1) {
		return my.selectOne("cs.countSearch", search1);
	}  
	
	public CustomerServiceVO checkTemporaryCs(CustomerServiceVO cs) {
		CustomerServiceVO bag= my.selectOne("cs.checkTemporaryCs",cs);
		return bag;
	}
	
	/* 문의 답변 등록 */
    public int replyRegister(CustomerServiceVO bag) {
        int result = my.insert("cs.reply", bag);
        return result;
    }
   
    /* 문의 One + 조회수 증가 */
    public CustomerServiceVO adminOne(int no) {
        CustomerServiceVO bag = my.selectOne("cs.one",no);
        my.update("cs.viewUpdate", no);
        return bag;
    }
    
    public List<CustomerServiceVO> nonPagedNeedReply() {
        List<CustomerServiceVO> nonPagedNeedReplyList = my.selectList("cs.nonPagedNeedReply");
        return nonPagedNeedReplyList;
    }
    
    public List<CustomerServiceVO> pagedNeedReply(AdminPageVO pageVo) {
        List<CustomerServiceVO> pagedCsNeedReplyList = my.selectList("cs.pagedNeedReply", pageVo);
        return pagedCsNeedReplyList;
    }
    
    public int countPagedNeedReply() {
        return my.selectOne("cs.countPagedNeedReply");
    }
    
    public List<CustomerServiceVO> nonPagedList(){
        List<CustomerServiceVO> list = my.selectList("cs.nonPagedList");
        return list;
    }
    
    public List<CustomerServiceVO> pagedList(AdminPageVO pageVo){
        List<CustomerServiceVO> list = my.selectList("cs.pagedList", pageVo);
        return list;
    }
	
}
