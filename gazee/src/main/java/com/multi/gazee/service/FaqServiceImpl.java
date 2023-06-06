package com.multi.gazee.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.multi.gazee.customerService.PageVO;
import com.multi.gazee.faq.FaqDAO;
import com.multi.gazee.faq.FaqVO;

@Service
public class FaqServiceImpl implements FaqService{

	
	@Autowired
	FaqDAO dao;
	
	public String faqList(PageVO vo, Model model, int mode) {
		vo.setStartEnd(vo.getPage());
		List<FaqVO> list = dao.list(vo);
		int count = dao.count();
		int pages = count / 10 +1;
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("pages", pages);
		
		if(mode==2) {
			return "faq/faqlist2";
		}
		else {
			return "faq/faqlist";
			}
	}
	
	public String faqCategory(PageVO vo, Model model, String category1, int mode) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("category1", category1);
		List<FaqVO> faqCategory = dao.category(map);
		int count = dao.countCategory(category1);
		int pages1 = count / 10 +1;
		model.addAttribute("category", faqCategory);
		model.addAttribute("count", count);
		model.addAttribute("pages1", pages1);
		model.addAttribute("categoryValue",category1);
		if(mode==2) {
			return "faq/faqCategory2";
		}
		else {
			return "faq/faqCategory";
			}
	}
	
	public void faqOne(int id, Model model) {
		FaqVO bag = dao.one(id);
		model.addAttribute("bag",bag);
	}
	
	public String faqSearch(PageVO vo, String search1, Model model, int mode) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("search1", search1);
		List<FaqVO> faqSearch = dao.search(map); 
		int count = dao.countSearch(search1);
		int pages1 = count / 10 +1;
		model.addAttribute("search", faqSearch);
		model.addAttribute("count", count);
		model.addAttribute("pages1", pages1);
		model.addAttribute("searchValue",search1);
		if(mode==2) {
			return "faq/faqSearch2";
		}
		else {
			return "faq/faqSearch";
			}
	}
}
