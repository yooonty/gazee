package com.multi.gazee.admin.login;

import com.multi.gazee.admin.brcypt.BcryptService;
import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.WebUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {
    @Autowired
    MemberDAO dao = new MemberDAO();
    @Autowired
    SqlSessionTemplate my;
    @Autowired
    BcryptService bcry;
    
    public String editAdminPw(String currentPw, String newPw, String newPwCheck, Model model) throws Exception {
        MemberVO vo = dao.readAdmin();
        int pwCheck = checkPass(currentPw);
        
        if (pwCheck == 1) {
            if (newPw.equals(newPwCheck)) {
                String hasedNewPw = bcry.encrypt(newPw);
                vo.setPw(hasedNewPw);
                dao.updatePw(vo);
                model.addAttribute("adminOne", vo);
                return "SUCCESS";
            } else {
                model.addAttribute("adminOne", vo);
                return "MISMATCH";
            }
        } else {
            model.addAttribute("adminOne", vo);
            return "INCORRECT";
        }
    }
    
    public String login(String idToCheck, String pwToCheck, final HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        MemberVO adminVo = dao.readAdmin();
        int idCheck = checkId(idToCheck);
        
        if (idCheck == 1) {
            String plainPassword = pwToCheck;
            int pwCheck = checkPass(plainPassword);
            
            if (pwCheck == 1) {
                // 세션 저장 (세션 ID, 사용자 정보)
                // 세션은 브라우저 당 1개 생성(시크릿 모드도 동일, 같은 브라우저에서 새탭 or 새창 띄워도 로그인 유지) / 쿠키는 시크릿 모드시 없어짐
                request.getSession().setAttribute("username", adminVo.getId());
                
                //로그인 시간 저장
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss");
                Date loginTime = new Date();
                String formattedTime = dateFormat.format(loginTime);
                request.getSession().setAttribute("login_time", formattedTime);
                
                // 쿠키 전달 (세션 ID)
                response.addCookie(new Cookie("AUTH", request.getSession().getId()) {{
                    setMaxAge(60 * 60); // 자동 로그인 60분 유지
                    setPath("/");
                }});
                
                // 화면에 표시할 ID 세팅
                model.addAttribute("username", adminVo.getId());
                return "admin/adminSidebar";
                
            } else {
                model.addAttribute("msg", "ID 또는 비밀번호를 확인하세요.");
            }
        } else {
            model.addAttribute("msg", "회원으로 등록되지 않은 ID입니다.");
        }
        
        return "admin/adminLogin";
    }
    
    @Override
    public int checkId(String idToCheck) throws Exception {
        MemberVO vo = dao.readAdmin();
        
        if (idToCheck.equals(vo.getId())){
            return 1;
        } else {
            return 0;
        }
    }
    
    public int checkPass(String plainPassword) throws Exception {
        MemberVO vo = dao.readAdmin();
        String hasedPassword = vo.getPw();
        
        if (BCrypt.checkpw(plainPassword, hasedPassword)) { // doSomething(); }
            return 1; // 로그인 성공
        } else {
            return 0; // 로그인 실패
        }
    }
    
    public String checkCookie(HttpServletRequest request, Model model) {
        // 쿠키 만료시 Cookie 값이 null이 된다. (유효 시간 동안은 개발자 모드 진입 후(F12) 쿠키 보면 AUTH 라는 이름으로 세션 ID가 들어가 있음)
        Cookie auth = WebUtils.getCookie(request, "AUTH");
        
        // 로그인 정보가 있을시
        if (!ObjectUtils.isEmpty(auth) && StringUtils.equalsIgnoreCase(auth.getValue(), request.getSession().getId())) {
            String userId = (String) request.getSession().getAttribute("username");
            if (StringUtils.isNotEmpty(userId)) {
                model.addAttribute("username", userId);
                return "admin/adminSidebar";
            }
        }
        
        // 로그인 만료 or 비 로그인 상태
        return "admin/adminLogin";
    }
    
    public String invalidateSession(HttpServletRequest request) {
        // 세션 저장소 세션 제거
        request.getSession().invalidate();
        return "redirect:/admin";
    }
}
