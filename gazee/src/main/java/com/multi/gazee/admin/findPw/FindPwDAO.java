package com.multi.gazee.admin.findPw;

import com.multi.gazee.member.MemberVO;

public interface FindPwDAO {
    
    MemberVO readAdmin() throws Exception;
    
    MemberVO checkAdmin(String email) throws Exception;
    
    void updatePw(MemberVO vo) throws Exception;
  
}

