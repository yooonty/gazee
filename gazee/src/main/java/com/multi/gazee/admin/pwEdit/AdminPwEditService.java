package com.multi.gazee.admin.pwEdit;

import com.multi.gazee.brcypt.BcryptServiceImpl;
import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

public interface AdminPwEditService {
    @Autowired
    BcryptServiceImpl bcry = new BcryptServiceImpl();
    
    @Autowired
    MemberDAO Mdao = new MemberDAO();
    
   String editPw(String newPw, String newPwCheck, Model model, int pwCheck) throws Exception;
    }
