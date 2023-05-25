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
	
	@RequestMapping("customerService/report/reportWrite")
	public void reportWrite(ReportVO bag,  HttpSession session) {
		bag.setReportWriter((String)session.getAttribute("id"));
		dao.reportRegister(bag);
	}
	
	@RequestMapping("customerService/report/reportDelete")
	public void reportDelete(ReportVO bag) {
		dao.reportDelete(bag);
		System.out.println(bag);
	}
	
	@RequestMapping("customerService/report/reportUpdate")
	public void csUpdate(ReportVO bag) {
		dao.reportUpdate(bag);
		System.out.println(bag);
	}
	
	
	@RequestMapping("customerService/report/reportList")
	public String list(PageVO vo, Model model, int mode) {
		vo.setStartEnd(vo.getPage());
		List<ReportVO> list = dao.list(vo);
		int count = dao.count();
		int pages = count / 10 +1;		
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("pages", pages);
		
		if(mode==2) {
			return "customerService/report/reportList2";
		}
		else 
			return "customerService/report/reportList";
	}
	
	@RequestMapping("customerService/report/reportCategory")
	public String category(PageVO vo, Model model, String category1, int mode) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("category1", category1);
		List<ReportVO> reportCategory = dao.category(map);
		int count = dao.countCategory(category1);
		int pages1 = count / 10 +1;
		model.addAttribute("category", reportCategory);
		model.addAttribute("count", count);
		model.addAttribute("pages1", pages1);
		model.addAttribute("categoryValue",category1);
		if(mode==2) {
			return "customerService/report/reportCategory2";
		}
		else 
			return "customerService/report/reportCategory";
	}
	
	@RequestMapping("customerService/report/reportSearch")
	public String search(PageVO vo, String search1, Model model, int mode) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("search1", search1);
		List<ReportVO> reportSearch = dao.search(map); 
		int count = dao.countSearch(search1);
		int pages1 = count / 10 +1;
		model.addAttribute("search", reportSearch);
		model.addAttribute("count", count);
		model.addAttribute("pages1", pages1);
		model.addAttribute("searchValue",search1);
		if(mode==2) {
			return "customerService/report/reportSearch2";
		}
		else 
			return "customerService/report/reportSearch";
		
	}
	
	@RequestMapping("customerService/report/reportOne")
	public void one(int id, Model model) {
		ReportVO bag = dao.one(id);
		model.addAttribute("bag",bag);
		model.addAttribute("reportWriter",bag.getReportWriter());
	}
	
	
	@RequestMapping("customerService/report/goToReportWrite")
	public String goToReportWrite() {
		return "customerService/report/reportWrite";
	}
	
	@RequestMapping("customerService/report/goToReportUpdate")
	public String goToReportUpdate(Model model, int id) {
		System.out.println(id);
		ReportVO bag = dao.one(id);
		model.addAttribute("bag",bag);
		return "customerService/report/reportUpdate";
	}
	
	@RequestMapping("customerService/report/checkTemporaryReport")
	public void checkTemporaryReport(HttpSession session, Model model, ReportVO bag) {
		ReportVO bag2 =dao.checkTemporaryReport(bag);
		
		if(bag2 !=null) {//임시저장 존재
			model.addAttribute("result",1);
			model.addAttribute("bag",bag2);
			model.addAttribute("reportId",bag2.getReportId());
		} else { //임시저장 없음 바로 글쓰기
			model.addAttribute("result",0);
			bag2= new ReportVO();
			bag2.setReportId(0);
			model.addAttribute("bag",bag2);
		}
	}
	
}
