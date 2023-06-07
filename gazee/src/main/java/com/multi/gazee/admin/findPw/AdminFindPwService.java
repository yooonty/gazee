package com.multi.gazee.admin.findPw;

import com.multi.gazee.member.MemberVO;
import javax.servlet.http.HttpServletResponse;

public interface AdminFindPwService {
    
    /* 비밀번호찾기 */
    void findPw(HttpServletResponse response, String email) throws Exception;
    
    /* 이메일발송 */
    void sendEmail(MemberVO vo, String div) throws Exception;
}