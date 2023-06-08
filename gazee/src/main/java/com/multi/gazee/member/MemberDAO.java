package com.multi.gazee.member;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import static java.lang.System.out;
import org.apache.commons.mail.HtmlEmail;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.multi.gazee.admin.paging.AdminPageVO;
import com.multi.gazee.order.OrderVO;
import com.multi.gazee.productLikes.ProductLikesVO;

@PropertySource("classpath:application.properties")
@Component
public class MemberDAO { // CRUD
	
	
	@Value("${mailSender.username}")
	private String hostSmtpId;
	@Value("${mailSender.password}")
	private String hostSmtpPwd;
	
	@Autowired
	SqlSessionTemplate my;
	
	@Autowired
	private JavaMailSenderImpl mailSender;
	private int authNumber;
	
	public MemberVO login(String id) {
		MemberVO result = my.selectOne("member.login", id);
		return result;
	}
	
	public MemberVO logincheck(String id) {
		MemberVO result = my.selectOne("member.logincheck", id);
		return result;
	}
	
	public void insert(MemberVO bag) {
		if (bag.getGender().equals("female")) {
			bag.setGender("여성");
		} else {
			bag.setGender("남성");
		}
		System.out.println(bag);
		my.insert("member.create", bag);
	}
	
	public MemberVO idCheck(String id) {
		MemberVO vo = my.selectOne("member.searchOne", id);
		return vo;
	}
	
	public MemberVO nicknameCheck(String nickname) {
		MemberVO vo = my.selectOne("member.searchOneByNickname", nickname);
		return vo;
	}
	
	public void nick(MemberVO bag) {
		my.update("member.nick", bag);
	}
	
	public MemberVO selectOne(String id) {
		MemberVO bag = my.selectOne("member.searchOne", id);
		return bag;
	}
	
	public MemberVO emailCheck(String email) {
		MemberVO vo = my.selectOne("member.emailCheck", email);
		return vo;
	}
	
	public void makeRandomNumber() {
		// 난수의 범위 111111 ~ 999999 (6자리 난수)
		Random r = new Random();
		int checkNum = r.nextInt(888888) + 111111;
		System.out.println("인증번호 : " + checkNum);
		authNumber = checkNum;
	}
	
	// 이메일 보낼 양식!
	public String joinEmail(String email) throws IOException {
	   makeRandomNumber();
	   String setFrom = "qlscmgns@gmail.com"; // email-config에 설정한 자신의 이메일 주소를 입력
	   String toMail = email;
	   int content = authNumber;
	   mailSend(setFrom, toMail, content, "findPw");
	   return Integer.toString(authNumber);
	}

	// 이메일 전송 메소드
	public void mailSend(String setFrom, String toMail, int content, String div) {
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
	      subject = "가지가지 인증번호 입니다.";
	      msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
	      msg += "<h3 style='color: black;'>";
	      msg += "가지가지 인증번호입니다.</h3>";
	      msg += "<p>인증번호 : ";
	      msg += content + "</p><br></div>";
	   }
	   
	   // 받는 사람 E-Mail 주소
	   String mail = toMail;
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
	      email.addTo(mail);
	      email.setFrom(fromEmail, fromName, charSet);
	      email.setSubject(subject);
	      email.setHtmlMsg(msg);
	      email.send();
	   } catch (Exception e) {
	      out.println("메일발송 실패 : " + e);
	   }
	}
	
	public void leave(MemberVO bag) {
		my.update("member.leave", bag);
	}
	
	public void updatePw(MemberVO bag) {
		my.update("member.updatePw", bag);
	}
	
	public String getId(String email) {
		MemberVO memberVO = my.selectOne("member.emailCheck", email);
		String id = memberVO.getId();
		return id;
	}
	
	public MemberVO findPw(String id) {
		MemberVO vo = my.selectOne("member.searchOne", id);
		return vo;
	}
	
	public void updateuser(MemberVO bag) {
		my.update("member.updateuser", bag);
	}
	
	public void profileImg(MemberVO bag) {
		my.update("member.profileImg", bag);
	}
	
	public void trackingNo(OrderVO bag) {
		if (bag.getDeliveryCom().equals("HanJin")) {
			bag.setDeliveryCom("한진택배");
		} else if (bag.getDeliveryCom().equals("CJ")) {
			bag.setDeliveryCom("CJ대한통운");
		} else {
			bag.setDeliveryCom("로젠택배");
		}
		my.update("member.trackingNo", bag);
	}
	
	public void buyerCon(MemberVO bag) {
		my.update("member.buyerCon", bag);
	}
	
	public void logoutTimeUpdate(String id) {
		my.update("member.logoutTimeUpdate", id);
	}
	
	public List<OrderVO> purchsList(String id) {
		List<OrderVO> list = my.selectList("member.purchsList", id);
		System.out.println("DAO" + list.size());
		return list;
	}

	public List<OrderVO> sellList(String id) {
		List<OrderVO> list = my.selectList("member.sellList", id);
		System.out.println("DAO" + list.size());
		return list;
	}

	public List<ProductLikesVO> basketList(String id) {
		List<ProductLikesVO> list = my.selectList("member.basketList", id);
		System.out.println("DAO" + list.size());
		return list;
	}
	
	
	// admin
	
	
	/* Admin 계정 정보 One */
	public MemberVO readAdmin() throws Exception {
		return my.selectOne("member.readAdmin");
	}
	
	/* email로 Admin 체크 */
	public MemberVO checkAdmin(String email) throws Exception {
		return my.selectOne("member.checkAdmin", email);
	}
	
	/* Admin 암호 UPDATE */
	public void adminUpdatePw(MemberVO vo) throws Exception {
		int result = my.update("member.updatePwByEmail", vo);
	}
	
	/* 전체 회원 목록 (non-페이징) */
	public List<MemberVO> list() {
		List<MemberVO> memberList = my.selectList("member.all");
		return memberList;
	}
	
	/* 전체 회원 목록 (페이징) */
	public List<MemberVO> pagedList(AdminPageVO pageVo) {
		List<MemberVO> pagedMemberList = my.selectList("member.pagedAll", pageVo);
		return pagedMemberList;
	}
	
	/* 전체 회원 수*/
	public int count() {
		return my.selectOne("member.count");
	}
	
	/* Admin 제외 회원 List */
	public List<MemberVO> listExceptAdmin() {
		List<MemberVO> memberList = my.selectList("member.allExceptAdmin");
		return memberList;
	}
	
	/* 이번 주 가입 회원 */
	public List<MemberVO> newMemberThisWeek() {
		List<MemberVO> newMemberThisWeekList = my.selectList("member.newMemberThisWeek");
		return newMemberThisWeekList;
	}
	
	/* 지난 30일 가입 회원 */
	public List<MemberVO> memberOfPastThirtyDays() {
		List<MemberVO> memberOfPastThirtyDaysList = my.selectList("member.memberOfPastThirtyDays");
		return memberOfPastThirtyDaysList;
	}
	
	/* 회원 별 계좌 READ */
	public List<MemberVO> listBankAccount(String id) {
		List<MemberVO> bankAccountList = my.selectList("member.listBankAccount", id);
		return bankAccountList;
	}
	
	/* 제재 중인 회원 목록 */
	public List<MemberVO> suspendedList() {
		List<MemberVO> suspendedList = my.selectList("member.suspended");
		return suspendedList;
	}
	
	/* 제재가 필요한 회원 목록 (제재 횟수가 3/5/7) */
	public List<MemberVO> needPenaltyList() {
		List<MemberVO> needPenaltyList = my.selectList("member.needPenaltyList");
		return needPenaltyList;
	}
	
	/* One (by ID) */
	public MemberVO adminOne(String id) {
		MemberVO oneById = my.selectOne("member.searchOne", id);
		return oneById;
	}
	
	/* 회원관리 내 검색 */
	public List<MemberVO> adminSearch(Map parameterMap) {
		List<MemberVO> search = my.selectList("member.search", parameterMap);
		return search;
	}
	
	/* 제재 실행 */
	public void executeSuspension(String memberId) {
		my.update("member.executeSuspension", memberId);
	}
	
	/* 제재 해제 */
	public void releaseSuspension(String memberId) {
		my.update("member.releaseSuspension", memberId);
	}
	
	/* ADMIN 회원 삭제 */
	public void adminDeleteMember(int no) {
		my.update("member.adminDeleteMember", no);
	}
	
	public MemberVO findByNickname(String nickname) {
		MemberVO vo = my.selectOne("member.searchOneByNickname", nickname);
		return vo;
	}
}
