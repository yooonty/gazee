package com.multi.gazee.customerService;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CustomerServiceController {

	@Autowired
	CustomerServiceService service;
	
	@Autowired
	CustomerServiceDAO dao;
	
	@RequestMapping("customerService/cs/csWrite")
	public void csWrite(CustomerServiceVO bag, HttpSession session) {
		service.csWrite(bag, session);
	}

	@RequestMapping("customerService/cs/csDelete")
	public void csDelete(CustomerServiceVO bag) {
		service.csDelete(bag);
	}

	@RequestMapping("customerService/cs/csUpdate")
	public void csUpdate(CustomerServiceVO bag) {
		service.csUpdate(bag);
	}

	@RequestMapping("customerService/cs/csList")
	public String list(PageVO vo, Model model, int mode) throws Exception {
		return service.csList(vo, model, mode);
	}

	@RequestMapping("customerService/cs/csCategory")
	public String category(PageVO vo, Model model, String category1, int mode) throws Exception{
		return service.csCategory(vo, model, category1, mode);
	}

	@RequestMapping("customerService/cs/csSearch")
	public String search(PageVO vo, String search1, Model model, int mode) throws Exception {
		return service.search(vo, search1, model, mode);
	}

	@RequestMapping("customerService/cs/csOne")
	public void one(int id, Model model) {
		service.one(id, model);		
		}

	@RequestMapping("customerService/cs/goToCsWrite")
	public String goToCsWrite() throws Exception{
		return service.goToCsWrite();
	}
	
	@RequestMapping("customerService/cs/goToCsUpdate")
	public String goToCsUpdate(Model model, int id) throws Exception{
		return service.goToCsUpdate(model, id);
	}
	
	@RequestMapping("customerService/cs/checkTemporaryCs")
	public void checkTemporaryCs(HttpSession session, Model model, CustomerServiceVO bag) {
		service.checkTemporaryCs(model, bag);
	}
}
