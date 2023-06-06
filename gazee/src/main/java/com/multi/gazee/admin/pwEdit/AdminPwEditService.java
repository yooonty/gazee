package com.multi.gazee.admin.pwEdit;

import com.multi.gazee.admin.brcypt.BcryptServiceImpl;
import com.multi.gazee.member.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

public interface AdminPwEditService {
    @Autowired
    BcryptServiceImpl bcry = new BcryptServiceImpl();
    
    @Autowired
    MemberDAO Mdao = new MemberDAO();
    
    /* 관리자 비밀번호 변경 */
   String editPw(String newPw, String newPwCheck, Model model, int pwCheck) throws Exception;
    }
