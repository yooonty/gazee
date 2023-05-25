package com.multi.gazee.customerService;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CustomerServiceController {

	@Autowired
	CustomerServiceDAO dao;
	
	@RequestMapping("customerService/cs/csWrite")
	public void csWrite(CustomerServiceVO bag, HttpSession session) {
		bag.setCsWriter((String)session.getAttribute("id"));
		dao.csRegister(bag);
		
	}
	
	@RequestMapping("customerService/cs/csDelete")
	public void csDelete(CustomerServiceVO bag) {
		dao.csDelete(bag);
	}
	
	@RequestMapping("customerService/cs/csUpdate")
	public void csUpdate(CustomerServiceVO bag) {
		dao.csUpdate(bag);
		System.out.println(bag);
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
		model.addAttribute("csWriter",bag.getCsWriter());		
		}
	
	@RequestMapping("customerService/cs/goToCsWrite")
	public String goToCsWrite() {
		return "customerService/cs/csWrite";
	}
	
	@RequestMapping("customerService/cs/goToCsUpdate")
	public String goToCsUpdate(Model model, int id) {
		CustomerServiceVO bag = dao.one(id);
		model.addAttribute("bag",bag);
		return "customerService/cs/csUpdate";
	}
	
	@RequestMapping("customerService/cs/checkTemporaryCs")
	public void checkTemporaryCs(HttpSession session, Model model, CustomerServiceVO bag) {
		CustomerServiceVO bag2 =dao.checkTemporaryCs(bag);
		
		if(bag2 !=null) {//임시저장 존재
			model.addAttribute("result",1);
			model.addAttribute("bag",bag2);
			model.addAttribute("csId",bag2.getCsId());
		} else { //임시저장 없음 바로 글쓰기
			model.addAttribute("result",0);
			bag2= new CustomerServiceVO();
			bag2.setCsId(0);
			model.addAttribute("bag",bag2);
		}
	}
	
	
	
}
