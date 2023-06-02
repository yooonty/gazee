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
    
    public int replyRegister(ReportVO bag) {
        int result = my.insert("report.reply", bag);
        return result;
    }
    
    public List<ReportVO> nonPagedNeedReply() {
        List<ReportVO> nonPagedNeedReplyList = my.selectList("report.nonPagedNeedReply");
        return nonPagedNeedReplyList;
    }
    
    public List<ReportVO> pagedNeedReply(PageVO pageVo) {
        List<ReportVO> pagedReportNeedReplyList = my.selectList("report.pagedNeedReply", pageVo);
        return pagedReportNeedReplyList;
    }
    
    public int countPagedNeedReply() {
        return my.selectOne("report.countPagedNeedReply");
    }
    
    public void delete(ReportVO bag) {
        my.delete("report.del", bag);
    }
    
    public void update(ReportVO bag) {
        my.delete("report.up", bag);
    }
    
    public ReportVO one(int no) {
        ReportVO bag = my.selectOne("report.one",no);
        my.update("report.viewUpdate", no);
        return bag;
    }
    
    public List<ReportVO> nonPagedList(){
        List<ReportVO> list = my.selectList("report.nonPagedList");
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
    
    public List<ReportVO> pagedList(PageVO pageVo){
        List<ReportVO> list = my.selectList("report.pagedList", pageVo);
        return list;
    }
    
}
