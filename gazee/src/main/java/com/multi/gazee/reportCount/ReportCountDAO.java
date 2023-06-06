package com.multi.gazee.reportCount;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ReportCountDAO {
    @Autowired
    SqlSessionTemplate my;
    
    /* ALL */
    public List<ReportCountVO> all(){
        List<ReportCountVO> countList = my.selectList("reportCount.all");
        return countList;
    }
    
    /* 회원 별 누적 제재 횟수 LIST */
    public List<ReportCountVO> listCount(String id) {
        List<ReportCountVO> countList = my.selectList("reportCount.listCount", id);
        return countList;
    }
    
    /* ONE */
    public ReportCountVO one(String id) {
        ReportCountVO bag = my.selectOne("reportCount.one", id);
        return bag;
    }
  
}