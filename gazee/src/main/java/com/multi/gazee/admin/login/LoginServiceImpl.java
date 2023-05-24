package com.multi.gazee.admin.login;

import com.multi.gazee.admin.admin.AdminDAOImpl;
import com.multi.gazee.member.MemberVO;
import org.mindrot.jbcrypt.BCrypt;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    AdminDAOImpl dao = new AdminDAOImpl();
    @Autowired
    SqlSessionTemplate my;
    @Override
    public int checkId(String idToCheck) throws Exception {
        MemberVO vo = dao.readAdmin();
        
        if (idToCheck.equals(vo.getId())){
            return 1;
        } else {
            return 0;
        }
    }
    
    public int checkPass(String plainPassword, String hasedPassword) throws Exception {
        MemberVO vo = dao.readAdmin();
        hasedPassword = vo.getPw();
        
        if (BCrypt.checkpw(plainPassword, hasedPassword)) { // doSomething(); }
            System.out.println("로그인 성공");
            return 1;
        } else {
            System.out.println("로그인 실패");
            return 0;
        }
    }
    
}
