package com.multi.gazee.report;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.multi.gazee.customerService.PageVO;

@Component
public class ReportDAO {

	@Autowired
	SqlSessionTemplate my;
	
	public int reportRegister(ReportVO bag) {
		int result = my.insert("report.register", bag);
		return result;
	}
	
	public void reportDelete(ReportVO bag) {
		my.delete("report.del", bag);
	}
	
	public void reportUpdate(ReportVO bag) {
		my.update("report.up", bag);
	}
	
	public ReportVO one(int no) {
		ReportVO bag = my.selectOne("report.one",no);
		my.update("report.viewUpdate", no);
		return bag;
	}
	
	public List<ReportVO> list(PageVO vo){
		List<ReportVO> list = my.selectList("report.all", vo);
		return list;
	}
	
	public List<ReportVO> category(HashMap<String, Object> map){
		List<ReportVO> category = my.selectList("report.category", map);
		return category;
	}
	
	
	public List<ReportVO> search(HashMap<String, Object> map){
		List<ReportVO> search = my.selectList("report.searchAll", map);
		System.out.println(search.size());
		return search;
	}
	
	
	public int count() {
		return my.selectOne("report.count");
	}
	
	public int countCategory(String category1) {
		return my.selectOne("report.countCategory", category1);
	}  
	
	public int countSearch(String search1) {
		return my.selectOne("report.countSearch", search1);
	}  
	
	public ReportVO checkTemporaryReport(ReportVO report) {
		ReportVO bag= my.selectOne("report.checkTemporaryReport",report);
		return bag;
	}
}

