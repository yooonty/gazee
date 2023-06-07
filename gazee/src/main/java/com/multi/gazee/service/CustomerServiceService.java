package com.multi.gazee.service;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.multi.gazee.admin.paging.AdminPageVO;
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
	
	/* ADMIN CS 상세 */
    String csOne(int id, Model model) throws Exception;
    
    
    /* ADMIN CS 목록 */
    String getCsList(AdminPageVO pageVo, int pageNumber, Model model) throws Exception;
    
    
    /* ADMIN CS 답변 등록 */
    String csReply(int csId, String replyContent) throws Exception;
}

