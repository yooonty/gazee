package com.multi.gazee.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.multi.gazee.customerService.PageVO;
import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.report.ReportDAO;
import com.multi.gazee.report.ReportVO;
import com.multi.gazee.reportImg.ReportImgDAO;
import com.multi.gazee.reportImg.ReportImgVO;

@Service
public class ReportServiceImpl implements ReportService{

	@Autowired
	ReportDAO dao;
	@Autowired
	ReportImgDAO dao2;
	@Autowired
	MemberDAO memberDao;
	
	public void reportWrite(ReportVO bag, HttpSession session) {
		bag.setReportWriter((String)session.getAttribute("id"));
		dao.reportRegister(bag);
		int reportId = bag.getReportId();
		session.setAttribute("reportId", reportId);
	}
	
	public void reportDelete(ReportVO bag) {
		dao.reportDelete(bag);
	}
	
	public void reportUpdate(ReportVO bag) {
		dao.reportUpdate(bag);
	}
	
	public String reportList(PageVO vo, Model model, int mode) {
		vo.setStartEnd(vo.getPage());
		List<ReportVO> list = dao.list(vo);
		List<String> list2 = new ArrayList<String>();
		for (ReportVO reportVO : list) {
			String reportWriterId=reportVO.getReportWriter();
			MemberVO bag2= memberDao.selectOne(reportWriterId);
			String nickname=bag2.getNickname();
			System.out.println(nickname);
			list2.add(nickname);
		}
		int count = dao.count();
		int pages = count / 10 +1;	
		model.addAttribute("list", list);
		model.addAttribute("nickName", list2);
		model.addAttribute("count", count);
		model.addAttribute("pages", pages);
		
		if(mode==2) {
			return "report/reportList2";
		}
		else 
			return "report/reportList";
	}
	
	public String reportCategory(PageVO vo, Model model, String category1, int mode) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("category1", category1);
		List<ReportVO> reportCategory = dao.category(map);
		List<String> list2 = new ArrayList<String>();
		for (ReportVO reportVO : reportCategory) {
			String reportWriterId=reportVO.getReportWriter();
			MemberVO bag2= memberDao.selectOne(reportWriterId);
			String nickname=bag2.getNickname();
			list2.add(nickname);
		}
		int count = dao.countCategory(category1);
		int pages1 = count / 10 +1;
		model.addAttribute("category", reportCategory);
		model.addAttribute("nickName", list2);
		model.addAttribute("count", count);
		model.addAttribute("pages1", pages1);
		model.addAttribute("categoryValue",category1);
		if(mode==2) {
			return "report/reportCategory2";
		}
		else 
			return "report/reportCategory";
	}
	
	public String reportSearch(PageVO vo, String search1, Model model, int mode) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("search1", search1);
		List<ReportVO> reportSearch = dao.search(map); 
		List<String> list2 = new ArrayList<String>();
		for (ReportVO reportVO : reportSearch) {
			String reportWriterId=reportVO.getReportWriter();
			MemberVO bag2= memberDao.selectOne(reportWriterId);
			String nickname=bag2.getNickname();
			list2.add(nickname);
		}
		int count = dao.countSearch(search1);
		int pages1 = count / 10 +1;
		model.addAttribute("search", reportSearch);
		model.addAttribute("count", count);
		model.addAttribute("pages1", pages1);
		model.addAttribute("nickName", list2);
		model.addAttribute("searchValue",search1);
		if(mode==2) {
			return "report/reportSearch2";
		}
		else 
			return "report/reportSearch";
	}
	
	public void reportOne(int id, Model model) {
		ReportVO bag = dao.one(id);
		List<ReportImgVO> reportImgList= dao2.reportImgList(id);
		model.addAttribute("reportImgList", reportImgList);
		model.addAttribute("bag",bag);
		model.addAttribute("reportWriter",bag.getReportWriter());
	}
	
	public String goToReportWrite() {
		return "report/reportWrite";
	}
	
	public String goToReportUpdate(Model model, int id) {
		System.out.println(id);
		ReportVO bag = dao.one(id);
		model.addAttribute("bag",bag);
		return "report/reportUpdate";
	}
	
	public void checkTemporaryReport(Model model, ReportVO bag) {
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
