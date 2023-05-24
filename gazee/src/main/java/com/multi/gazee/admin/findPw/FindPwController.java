package com.multi.gazee.admin.findPw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletResponse;

@Controller
public class FindPwController {
    @Autowired
    public FindPwService service;
    
    @RequestMapping(value = "findPwForm.do")
    public String findPwForm() throws Exception{
        return "../admin/findPwForm";
    }
    
    @RequestMapping(value = "findPw.do", method = RequestMethod.POST)
    public void findPw(@ModelAttribute("email") String email, HttpServletResponse response) throws Exception{
        System.out.println("ModelAttribute : " + email);
        service.findPw(response, email);
    }
    
}
