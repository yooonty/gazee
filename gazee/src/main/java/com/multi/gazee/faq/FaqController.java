package com.multi.gazee.faq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.gazee.customerService.PageVO;



@Controller
public class FaqController {

	@Autowired
	FaqDAO dao;

	@RequestMapping("customerService/faq/faqlist")
	public String list(PageVO vo, Model model, int mode) {
		vo.setStartEnd(vo.getPage());
		System.out.println("faqcontrollerVO"+vo);
		List<FaqVO> list = dao.list(vo);
		System.out.println("faqlist"+list);
		int count = dao.count();
		int pages = count / 10 +1;
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("pages", pages);
		
		if(mode==2) {
			return "customerService/faq/faqlist2";
		}
		else {
			return "customerService/faq/faqlist";
			}
	}


	@RequestMapping("customerService/faq/faqCategory")
	public String category(PageVO vo, Model model, String category1, int mode) {
		
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("category1", category1);
		System.out.println(map);
		List<FaqVO> faqCategory = dao.category(map);
		System.out.println(faqCategory);
		int count = dao.countCategory(category1);
		System.out.println(count);
		int pages1 = count / 10 +1;
		System.out.println(pages1);
		model.addAttribute("category", faqCategory);
		model.addAttribute("count", count);
		model.addAttribute("pages1", pages1);
		model.addAttribute("categoryValue",category1);
		if(mode==2) {
			return "customerService/faq/faqCategory2";
		}
		else {
			return "customerService/faq/faqCategory";
			}
	}

	
	@RequestMapping("customerService/faq/faqOne")
	public void one(int id, Model model) {
		FaqVO bag = dao.one(id);
		model.addAttribute("bag",bag);
	}
	
	@RequestMapping("customerService/faq/faqSearch")
	public String search(PageVO vo, String search1, Model model, int mode) {
		vo.setStartEnd(vo.getPage());
		System.out.println(vo);
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
			return "customerService/faq/faqSearch2";
		}
		else {
			return "customerService/faq/faqSearch";
			}
		
	}
}
