package com.multi.gazee.service;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.multi.gazee.admin.paging.AdminPageVO;
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
	
	 /* ADMIN 신고 목록 */
    String getReportList(AdminPageVO pageVo, int pageNumber, Model model) throws Exception;
    
    /* ADMIN 신고 상세 */
    String adminReportOne(int id, Model model) throws Exception;
    
    /* ADMIN 신고 답변 */
    String reportReply(int reportId, String replyContent, Model model) throws Exception;
}
