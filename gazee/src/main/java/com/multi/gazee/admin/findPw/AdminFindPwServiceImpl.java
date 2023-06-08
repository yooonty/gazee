package com.multi.gazee.admin.findPw;

import com.multi.gazee.admin.brcypt.BcryptServiceImpl;
import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Properties;

@PropertySource("classpath:application.properties")
@Service
public class AdminFindPwServiceImpl implements AdminFindPwService {
    @Autowired
    MemberDAO dao = new MemberDAO();
    @Autowired
    BcryptServiceImpl bcry = new BcryptServiceImpl();
    
    @Value("${mailSender.username}")
    private String hostSmtpId;
    @Value("${mailSender.password}")
    private String hostSmtpPwd;
    
    
    String tempPw = "";
    
    //비밀번호찾기
    @Override
    public void findPw(HttpServletResponse response, String email) throws Exception {
        
        response.setContentType("text/html; charset=utf-8");
        MemberVO memberVo = dao.readAdmin();
        MemberVO vo = dao.checkAdmin(email);
        PrintWriter out = response.getWriter();
        
        // 입력한 이메일로 가입된 계정이 없으면
        if (vo == null) {
            out.print("회원으로 등록되지 않은 이메일입니다.");
            out.close();
        }
        // 입력한 이메일이 관리자 계정의 이메일이 아니라면
        else if (!memberVo.getEmail().equals(email)) {
            out.print("관리자로 등록되지 않은 이메일입니다.");
            out.close();
        } else {
            out.print("이메일로 임시 비밀번호를 발송하였습니다.");
            out.close();
            
            // 관리자 계정의 이메일 확인되면 임시 비밀번호 생성
            String pw = "";
            for (int i = 0; i < 12; i++) {
                pw += (char) ((Math.random() * 26) + 97);
            }
    
            tempPw = pw;
            
            //생성한 임시 비밀번호 암호화
            String bcryPw = bcry.encrypt(pw);
            
            vo.setPw(bcryPw);
            // 비밀번호 변경
            dao.updatePw(vo);
            // 비밀번호 변경 메일 발송
            sendEmail(vo, "findPw");
        }
    }
    
    //비밀번호 찾기 이메일발송
    @Override
    public void sendEmail(MemberVO vo, String div) throws Exception {
        // Mail Server 설정
        String charSet = "utf-8";
        String hostSMTP = "smtp.gmail.com";
        String hostSMTPid = hostSmtpId;
        String hostSMTPpwd = hostSmtpPwd;
    
        // 보내는 사람 EMail, 제목, 내용
        String fromEmail = "qlscmgns@gmail.comm";
        String fromName = "가지가지 관리자";
        String subject = "";
        String msg = "";
        
        if (div.equals("findPw")) {
            subject = "가지가지 임시 비밀번호 입니다.";
            msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
            msg += "<h3 style='color: blue;'>";
            msg += vo.getName() + "님의 임시 비밀번호 입니다. 비밀번호를 변경하여 사용하세요.</h3>";
            msg += "<p>임시 비밀번호 : ";
            msg += tempPw + "</p></div>";
        }
        
        // 받는 사람 E-Mail 주소
        String mail = vo.getEmail();
        try {
            HtmlEmail email = new HtmlEmail();
            email.setAuthentication(hostSMTPid, hostSMTPpwd);
            email.setDebug(true);
            email.setCharset(charSet);
            email.setSSLOnConnect(true);
            email.setHostName(hostSMTP);
            email.setSslSmtpPort("465");
            
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
            
            email.setTLS(true);
            email.addTo(mail, charSet);
            email.setFrom(fromEmail, fromName, charSet);
            email.setSubject(subject);
            email.setHtmlMsg(msg);
            email.send();
            
        } catch (Exception e) {
            System.out.println("메일발송 실패 : " + e);
        }
    }
}