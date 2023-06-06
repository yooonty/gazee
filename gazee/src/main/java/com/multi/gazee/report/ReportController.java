package com.multi.gazee.report;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.gazee.customerService.PageVO;
import com.multi.gazee.reportImg.ReportImgDAO;
import com.multi.gazee.service.ReportService;

@Controller
public class ReportController {
	
	@Autowired
	ReportService service;
	
	@RequestMapping("report/reportWrite")
	public void reportWrite(ReportVO bag,  HttpSession session) {
		service.reportWrite(bag, session);
	}
	
	@RequestMapping("report/reportDelete")
	public void reportDelete(ReportVO bag) {
		service.reportDelete(bag);
	}
	
	@RequestMapping("report/reportUpdate")
	public void reportUpdate(ReportVO bag) {
		service.reportUpdate(bag);
	}

	@RequestMapping("report/reportList")
	public String reportList(PageVO vo, Model model, int mode) throws Exception {
		return service.reportList(vo, model, mode);
	}

	@RequestMapping("report/reportCategory")
	public String reportCategory(PageVO vo, Model model, String category1, int mode) throws Exception {
		return service.reportCategory(vo, model, category1, mode);
	}

	@RequestMapping("report/reportSearch")
	public String reportSearch(PageVO vo, String search1, Model model, int mode) throws Exception {
		return service.reportSearch(vo, search1, model, mode);
		
	}
	
	@RequestMapping("report/reportOne")
	public void reportOne(int id, Model model) {
		service.reportOne(id, model);
	}

	@RequestMapping("report/goToReportWrite")
	public String goToReportWrite() throws Exception{
		return service.goToReportWrite();
	}

	@RequestMapping("report/goToReportUpdate")
	public String goToReportUpdate(Model model, int id) throws Exception{
		return service.goToReportUpdate(model, id);
	}

	@RequestMapping("report/checkTemporaryReport")
	public void checkTemporaryReport(HttpSession session, Model model, ReportVO bag) {
		service.checkTemporaryReport(model, bag);
	}

	
	
}
