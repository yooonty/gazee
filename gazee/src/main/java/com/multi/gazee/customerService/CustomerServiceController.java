package com.multi.gazee.customerService;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CustomerServiceController {

	@Autowired
	CustomerServiceDAO dao;
	
	@RequestMapping("customerService/cs/csWrite")
	public void csWrite(CustomerServiceVO bag) {
		System.out.println(bag);
		dao.csRegister(bag);
	}
	
	@RequestMapping("customerService/cs/csList")
	public String list(PageVO vo, Model model, int mode) {
		vo.setStartEnd(vo.getPage());
		List<CustomerServiceVO> list = dao.list(vo);
		int count = dao.count();
		int pages = count / 10 +1;		
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("pages", pages);
		
		if(mode==2) {
			return "customerService/cs/csList2";
		}
		else 
			return "customerService/cs/csList";
	}
	
	@RequestMapping("customerService/cs/csCategory")
	public String category(PageVO vo, Model model, String category1, int mode) {
		
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("category1", category1);
		List<CustomerServiceVO> qnaCategory = dao.category(map);
		int count = dao.countCategory(category1);
		int pages1 = count / 10 +1;
		model.addAttribute("category", qnaCategory);
		model.addAttribute("count", count);
		model.addAttribute("pages1", pages1);
		model.addAttribute("categoryValue",category1);
		if(mode==2) {
			return "customerService/cs/csCategory2";
		}
		else 
			return "customerService/cs/csCategory";
	}
	
	@RequestMapping("customerService/cs/csSearch")
	public String search(PageVO vo, String search1, Model model, int mode) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("search1", search1);
		List<CustomerServiceVO> qnaSearch = dao.search(map); 
		int count = dao.countSearch(search1);
		int pages1 = count / 10 +1;
		model.addAttribute("search", qnaSearch);
		model.addAttribute("count", count);
		model.addAttribute("pages1", pages1);
		model.addAttribute("searchValue",search1);
		if(mode==2) {
			return "customerService/cs/csSearch2";
		}
		else 
			return "customerService/cs/csSearch";
		
	}
	
	@RequestMapping("customerService/cs/csOne")
	public void one(int id, Model model) {
		CustomerServiceVO bag = dao.one(id);
		model.addAttribute("bag",bag);
	}
	
	
	@RequestMapping("customerService/cs/goToCsWrite")
	public String goToCsWrite() {
		return "customerService/cs/csWrite";
	}
}
