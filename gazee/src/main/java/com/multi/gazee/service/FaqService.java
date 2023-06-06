package com.multi.gazee.service;

import org.springframework.ui.Model;

import com.multi.gazee.customerService.PageVO;

public interface FaqService {
	
	String faqList(PageVO vo, Model model, int mode) throws Exception;
	
	String faqCategory(PageVO vo, Model model, String category1, int mode) throws Exception;
	
	void faqOne(int id, Model model);
	
	String faqSearch(PageVO vo, String search1, Model model, int mode) throws Exception;
}
