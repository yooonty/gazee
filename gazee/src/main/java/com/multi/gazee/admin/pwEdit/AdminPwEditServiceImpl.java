package com.multi.gazee.admin.pwEdit;

import com.multi.gazee.admin.brcypt.BcryptServiceImpl;
import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class AdminPwEditServiceImpl implements AdminPwEditService {
    @Autowired
    BcryptServiceImpl bcry = new BcryptServiceImpl();
    
    @Autowired
    MemberDAO Mdao = new MemberDAO();
    
    public String editPw(String newPw, String newPwCheck, Model model, int pwCheck) throws Exception {
        MemberVO adminVo = Mdao.readAdmin();
        if (pwCheck == 1) {
            if (newPw.equals(newPwCheck)) {
                String hasedNewPw = bcry.encrypt(newPw);
                adminVo.setPw(hasedNewPw);
                Mdao.updatePw(adminVo);
                model.addAttribute("adminOne", adminVo);
                return "SUCCESS";
            } else {
                model.addAttribute("adminOne", adminVo);
                return "MISMATCH";
            }
        } else {
            model.addAttribute("adminOne", adminVo);
            return "INCORRECT";
        }
    }
}
