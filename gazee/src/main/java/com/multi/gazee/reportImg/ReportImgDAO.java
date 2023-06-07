package com.multi.gazee.reportImg;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.multi.gazee.customerServiceImg.CustomerServiceImgVO;

@Component
public class ReportImgDAO {

	@Autowired
	SqlSessionTemplate my;
	
	public int reportImgUpload(ReportImgVO bag) {
		int result = my.insert("reportImg.reportImgUpload", bag);
		System.out.println(bag);
		return result;
	}
	
	public List<ReportImgVO> reportImgList(int reportId){
		List<ReportImgVO> reportImgList = my.selectList("reportImg.reportImgList",reportId);
		return reportImgList;
	}
	
	public void reportImgDelete(int reportId) {
		my.delete("reportImg.reportImgDel", reportId);
	}
}
