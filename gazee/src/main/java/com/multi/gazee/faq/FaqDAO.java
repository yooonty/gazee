package com.multi.gazee.faq;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.multi.gazee.customerService.PageVO;


@Component
public class FaqDAO {
	
	@Autowired
	SqlSessionTemplate my;
	
	public void insert(FaqVO bag) {
		my.insert("faq.create",bag);
	}
	
	public void delete(FaqVO bag) {
		my.delete("faq.del", bag);
	}
	
	public void update(FaqVO bag) {
		my.delete("faq.up", bag);
	}
	
	public FaqVO one(int no) {
		FaqVO bag = my.selectOne("faq.one",no);
		my.update("faq.viewUpdate", no);
		return bag;
	}
	
	public List<FaqVO> list(PageVO vo){
		List<FaqVO> list = my.selectList("faq.faqAll", vo);
		return list;
	}
	
	
	public List<FaqVO> category(HashMap<String, Object> map){
		List<FaqVO> category = my.selectList("faq.category", map);
		return category;
	}
	
	
	public List<FaqVO> search(HashMap<String, Object> map){
		List<FaqVO> search = my.selectList("faq.searchAll", map);
		return search;
	}
	
	
	public int count() {
		return my.selectOne("faq.count");
	}  
	
	public int countCategory(String category1) {
		return my.selectOne("faq.countCategory", category1);
	}  
	
	public int countSearch(String search1) {
		return my.selectOne("faq.countSearch", search1);
	}  
	
}
