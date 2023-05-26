package com.multi.gazee.admin.login;

import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AdminLoginService {
    
    String editAdminPw(String currentPw, String newPw, String newPwCheck, Model model) throws Exception;
    
    String login(String idToCheck, String pwToCheck, final HttpServletRequest request, HttpServletResponse response, Model model) throws Exception;
    
    //아이디 비교
    int checkId(String idToCheck) throws Exception;
    
    //암호 평문-해쉬값 비교
    int checkPass(String plainPassword) throws Exception;
    
    String checkCookie(HttpServletRequest request, Model model) throws Exception;
    
    String invalidateSession(HttpServletRequest request) throws Exception;
}
