package com.multi.gazee.faq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.gazee.customerService.PageVO;
import com.multi.gazee.service.FaqService;



@Controller
public class FaqController {

	@Autowired
	FaqDAO dao;
	
	@Autowired
	FaqService service;

	@RequestMapping("faq/faqlist")
	public String faqList(PageVO vo, Model model, int mode) throws Exception {
		return service.faqList(vo, model, mode);
	}

	@RequestMapping("faq/faqCategory")
	public String faqCategory(PageVO vo, Model model, String category1, int mode) throws Exception {
		
		return service.faqCategory(vo, model, category1, mode);
	}
	
	@RequestMapping("faq/faqOne")
	public void faqOne(int id, Model model) {
		service.faqOne(id, model);
	}
	
	@RequestMapping("faq/faqSearch")
	public String faqSearch(PageVO vo, String search1, Model model, int mode) throws Exception {
		return service.faqSearch(vo, search1, model, mode);
		
	}

	
}
