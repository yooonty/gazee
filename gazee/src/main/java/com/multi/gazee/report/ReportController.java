package com.multi.gazee.report;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.gazee.customerService.PageVO;

@Controller
public class ReportController {

	@Autowired
	ReportDAO dao;
	
	@Autowired
	ReportService service;
	
	@RequestMapping("customerService/report/reportWrite")
	public void reportWrite(ReportVO bag,  HttpSession session) {
		service.reportWrite(bag, session);
	}
	
	@RequestMapping("customerService/report/reportDelete")
	public void reportDelete(ReportVO bag) {
		service.reportDelete(bag);
	}
	
	@RequestMapping("customerService/report/reportUpdate")
	public void reportUpdate(ReportVO bag) {
		service.reportUpdate(bag);
	}

	@RequestMapping("customerService/report/reportList")
	public String reportList(PageVO vo, Model model, int mode) throws Exception {
		return reportList(vo, model, mode);
	}

	@RequestMapping("customerService/report/reportCategory")
	public String reportCategory(PageVO vo, Model model, String category1, int mode) throws Exception {
		return service.reportCategory(vo, model, category1, mode);
	}

	@RequestMapping("customerService/report/reportSearch")
	public String reportSearch(PageVO vo, String search1, Model model, int mode) throws Exception {
		return service.reportSearch(vo, search1, model, mode);
		
	}
	
	@RequestMapping("customerService/report/reportOne")
	public void reportOne(int id, Model model) {
		service.reportOne(id, model);
	}

	@RequestMapping("customerService/report/goToReportWrite")
	public String goToReportWrite() throws Exception{
		return service.goToReportWrite();
	}

	@RequestMapping("customerService/report/goToReportUpdate")
	public String goToReportUpdate(Model model, int id) throws Exception{
		return service.goToReportUpdate(model, id);
	}

	@RequestMapping("customerService/report/checkTemporaryReport")
	public void checkTemporaryReport(HttpSession session, Model model, ReportVO bag) {
		service.checkTemporaryReport(model, bag);
	}

	
	
}
