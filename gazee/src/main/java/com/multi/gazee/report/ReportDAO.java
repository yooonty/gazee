package com.multi.gazee.report;

import com.multi.gazee.admin.paging.PageVO;
import com.multi.gazee.customerService.CustomerServiceVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;

@Component
public class ReportDAO {
    @Autowired
    SqlSessionTemplate my;
    
    public int reportRegister(ReportVO bag) {
        int result = my.insert("report.register", bag);
        return result;
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
    public List<ReportVO> pagedNeedReply(PageVO pageVo) {
        List<ReportVO> pagedReportNeedReplyList = my.selectList("report.pagedNeedReply", pageVo);
        return pagedReportNeedReplyList;
    }
    
    /* 답변이 필요한 신고글 목록 갯수 카운트 */
    public int countPagedNeedReply() {
        return my.selectOne("report.countPagedNeedReply");
    }
    
    public void delete(ReportVO bag) {
        my.delete("report.del", bag);
    }
    
    public void update(ReportVO bag) {
        my.delete("report.up", bag);
    }
    
    /* ONE + 조회수 상승 */
    public ReportVO one(int no) {
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
    public List<ReportVO> pagedList(PageVO pageVo){
        List<ReportVO> list = my.selectList("report.pagedList", pageVo);
        return list;
    }
    
    public List<ReportVO> category(HashMap<String, Object> map){
        List<ReportVO> category = my.selectList("report.category", map);
        return category;
    }
    
    /* Admin 신고관리 내 검색 */
    public List<ReportVO> search(HashMap<String, Object> map){
        List<ReportVO> search = my.selectList("report.searchAll", map);
        return search;
    }
    
    /* 전체 신고글 갯수 COUNT */
    public int count() {
        return my.selectOne("report.count");
    }
    
    public int countCategory(String category1) {
        return my.selectOne("report.countCategory", category1);
    }
    
    public int countSearch(String search1) {
        return my.selectOne("report.countSearch", search1);
    }
}