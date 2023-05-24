package com.multi.gazee.admin.findPw;

import com.multi.gazee.member.MemberVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindPwDAOImpl implements FindPwDAO {
    @Autowired
    SqlSessionTemplate my;
    
    @Override
    public MemberVO readAdmin() throws Exception {
        return my.selectOne("findPw.readAdmin");
    }
    
    @Override
    public MemberVO checkAdmin(String email) throws Exception {
        return my.selectOne("findPw.readAdmin", email);
    }
    
    @Override
    public void updatePw(MemberVO vo) throws Exception {
        int result = my.update("findPw.updatePw", vo);
    }
}

