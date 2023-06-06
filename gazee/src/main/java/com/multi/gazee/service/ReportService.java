package com.multi.gazee.service;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.multi.gazee.customerService.PageVO;
import com.multi.gazee.report.ReportVO;

public interface ReportService {

	void reportWrite(ReportVO bag, HttpSession session);
	
	void reportDelete(ReportVO bag);
	
	void reportUpdate(ReportVO bag);
	
	String reportList(PageVO vo, Model model, int mode) throws Exception;
	
	String reportCategory(PageVO vo, Model model, String category1, int mode) throws Exception;
	
	String reportSearch(PageVO vo, String search1, Model model, int mode) throws Exception;
	
	void reportOne(int id, Model model);
	
	String goToReportWrite() throws Exception;
	
	String goToReportUpdate(Model model, int id) throws Exception;
	
	void checkTemporaryReport(Model model, ReportVO bag);
}
