package com.multi.gazee.reportCount;

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
}
