package com.multi.gazee.reportCount;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportCountDAO {
	@Autowired
	SqlSessionTemplate my;
	
	public ReportCountVO reportCount(String memberId) {
		ReportCountVO bag = my.selectOne("reportCount.reportCount",memberId);
		return bag;
	}
	
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
    public ReportCountVO adminOne(String id) {
        ReportCountVO bag = my.selectOne("reportCount.reportCount", id);
        return bag;
    }
}
