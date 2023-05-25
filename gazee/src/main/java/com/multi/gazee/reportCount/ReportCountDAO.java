package com.multi.gazee.reportCount;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportCountDAO {
    @Autowired
    SqlSessionTemplate my;
    
    public List<ReportCountVO> all(){
        List<ReportCountVO> countList = my.selectList("reportCount.all");
        return countList;
    }
    
    public List<ReportCountVO> listCount(String id) {
        List<ReportCountVO> countList = my.selectList("reportCount.listCount", id);
        return countList;
    }
    
    public ReportCountVO one(String id) {
        ReportCountVO bag = my.selectOne("reportCount.one", id);
        return bag;
    }
  
}
