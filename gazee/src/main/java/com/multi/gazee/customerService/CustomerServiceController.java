package com.multi.gazee.customerService;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.gazee.customerServiceImg.CustomerServiceImgDAO;
import com.multi.gazee.service.CustomerServiceService;



@Controller
public class CustomerServiceController {

	@Autowired
	CustomerServiceService service;
	
	@Autowired
	CustomerServiceImgDAO dao2;
	
	@Autowired
	CustomerServiceDAO dao;
	
	@RequestMapping("cs/csWrite")
	public void csWrite(CustomerServiceVO bag, HttpSession session) {
		service.csWrite(bag, session);
	}

	@RequestMapping("cs/csDelete")
	public void csDelete(CustomerServiceVO bag) {
		service.csDelete(bag);
	}

	@RequestMapping("cs/csUpdate")
	public void csUpdate(CustomerServiceVO bag) {
		service.csUpdate(bag);
	}

	@RequestMapping("cs/csList")
	public String list(PageVO vo, Model model, int mode) throws Exception {
		return service.csList(vo, model, mode);
	}

	@RequestMapping("cs/csCategory")
	public String category(PageVO vo, Model model, String category1, int mode) throws Exception{
		return service.csCategory(vo, model, category1, mode);
	}

	@RequestMapping("cs/csSearch")
	public String search(PageVO vo, String search1, Model model, int mode) throws Exception {
		return service.search(vo, search1, model, mode);
	}

	@RequestMapping("cs/csOne")
	public void one(int id, Model model) {
		service.one(id, model);		
		}

	@RequestMapping("cs/goToCsWrite")
	public String goToCsWrite() throws Exception{
		return service.goToCsWrite();
	}
	
	@RequestMapping("cs/goToCsUpdate")
	public String goToCsUpdate(Model model, int id) throws Exception{
		return service.goToCsUpdate(model, id);
	}
	
	@RequestMapping("cs/checkTemporaryCs")
	public void checkTemporaryCs(HttpSession session, Model model, CustomerServiceVO bag) {
		service.checkTemporaryCs(model, bag);
	}
	
}
