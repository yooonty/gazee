package com.multi.gazee.service;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.multi.gazee.customerService.CustomerServiceVO;
import com.multi.gazee.customerService.PageVO;

public interface CustomerServiceService {
	
	String csList(PageVO vo, Model model, int mode) throws Exception;
	
	void csDelete(CustomerServiceVO bag); 
	
	void csWrite(CustomerServiceVO bag, HttpSession session);
	
	void csUpdate(CustomerServiceVO bag);
	
	String csCategory(PageVO vo, Model model, String category1, int mode) throws Exception;

	String search(PageVO vo, String search1, Model model, int mode) throws Exception;

	void one(int id, Model model);
	
	String goToCsWrite() throws Exception;
	
	String goToCsUpdate(Model model, int id) throws Exception;
	
	void checkTemporaryCs(Model model, CustomerServiceVO bag);
}

