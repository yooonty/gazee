package com.multi.gazee.member;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.multi.gazee.admin.paging.AdminPageVO;
import com.multi.gazee.order.OrderVO;
import com.multi.gazee.productLikes.ProductLikesVO;

@Component
public class MemberDAO { // CRUD
	
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
	
	public MemberVO nicknameCheck(String id) {
		MemberVO vo = my.selectOne("member.nickOne", id);
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
	public String joinEmail(String email) {
		makeRandomNumber();
		String setFrom = ".com"; // email-config에 설정한 자신의 이메일 주소를 입력
		String toMail = email;
		String title = "회원 가입 인증 이메일 입니다."; // 이메일 제목
		String content = "홈페이지를 방문해주셔서 감사합니다." + // html 형식으로 작성 !
				"<br><br>" + "인증 번호는 " + authNumber + "입니다." + "<br>" + "해당 인증번호를 인증번호 확인란에 기입하여 주세요."; // 이메일 내용 삽입
		mailSend(setFrom, toMail, title, content);
		return Integer.toString(authNumber);
	}
	
	// 이메일 전송 메소드
	public void mailSend(String setFrom, String toMail, String title, String content) {
		MimeMessage message = mailSender.createMimeMessage();
		// true 매개값을 전달하면 multipart 형식의 메세지 전달이 가능.문자 인코딩 설정도 가능하다.
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			// true 전달 > html 형식으로 전송 , 작성하지 않으면 단순 텍스트로 전달.
			helper.setText(content, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
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
}
