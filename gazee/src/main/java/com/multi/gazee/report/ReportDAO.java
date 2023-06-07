package com.multi.gazee.report;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.multi.gazee.admin.paging.AdminPageVO;
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
	
	/* 신고글 답변 작성 */
    public int replyRegister(ReportVO bag) {
        int result = my.insert("report.reply", bag);
        return result;
    }
    
    /* 답변이 필요한 신고글 목록 (non-페이징) */
    public List<ReportVO> nonPagedNeedReply() {
        List<ReportVO> nonPagedNeedReplyList = my.selectList("report.nonPagedNeedReply");
        return nonPagedNeedReplyList;
    }
    
    /* 답변이 필요한 신고글 목록 (페이징) */
    public List<ReportVO> pagedNeedReply(AdminPageVO pageVo) {
        List<ReportVO> pagedReportNeedReplyList = my.selectList("report.pagedNeedReply", pageVo);
        return pagedReportNeedReplyList;
    }
    
    /* 답변이 필요한 신고글 목록 갯수 카운트 */
    public int countPagedNeedReply() {
        return my.selectOne("report.countPagedNeedReply");
    }
    
    /* ONE + 조회수 상승 */
    public ReportVO adminOne(int no) {
        ReportVO bag = my.selectOne("report.one",no);
        my.update("report.viewUpdate", no);
        return bag;
    }
    
    /* 신고글 전체 목록 (non-페이징) */
    public List<ReportVO> nonPagedList(){
        List<ReportVO> list = my.selectList("report.nonPagedList");
        return list;
    }
    
    /* 신고글 전체 목록 (페이징) */
    public List<ReportVO> pagedList(AdminPageVO pageVo){
        List<ReportVO> list = my.selectList("report.pagedList", pageVo);
        return list;
    }
    
    /* Admin 신고관리 내 검색 */
    public List<ReportVO> adminSearch(HashMap<String, Object> map){
        List<ReportVO> search = my.selectList("report.searchAll", map);
        return search;
    }
    
    /* 전체 신고글 갯수 COUNT */
    public int adminCount() {
        return my.selectOne("report.count");
    }
}

