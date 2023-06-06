package com.multi.gazee.admin.login;

import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AdminLoginService {
    
    /* 관리자 계정 PW 변경 */
    String editAdminPw(String currentPw, String newPw, String newPwCheck, Model model) throws Exception;
    
    /* 관리자 로그인 */
    String login(String idToCheck, String pwToCheck, final HttpServletRequest request, HttpServletResponse response, Model model) throws Exception;
    
    /* 관리자 ID 비교 */
    int checkId(String idToCheck) throws Exception;
    
    /* 암호 평문-해쉬값 비교 */
    int checkPass(String plainPassword) throws Exception;
    
    /* 관리자 쿠키 체크 */
    String checkCookie(HttpServletRequest request, Model model) throws Exception;
    
    /* 관리자 세션 체크 */
    String invalidateSession(HttpServletRequest request) throws Exception;
}