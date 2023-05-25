package com.multi.gazee.member;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class MemberDAO {
	@Autowired
	SqlSessionTemplate my;
	
	
	public MemberVO readAdmin() throws Exception {
		return my.selectOne("member.readAdmin");
	}
	
	public MemberVO checkAdmin(String email) throws Exception {
		return my.selectOne("member.readAdmin", email);
	}
	
	public void updatePw(MemberVO vo) throws Exception {
		int result = my.update("member.updatePw", vo);
	}
	
	public List<MemberVO> list() {
		List<MemberVO> memberList = my.selectList("member.all");
		return memberList;
	}
	
	/* Admin 제외 회원 List */
	public List<MemberVO> listExceptAdmin() {
		List<MemberVO> memberList = my.selectList("member.allExceptAdmin");
		return memberList;
	}
	
	public List<MemberVO> newMemberThisWeek() {
		List<MemberVO> newMemberThisWeekList = my.selectList("member.newMemberThisWeek");
		return newMemberThisWeekList;
	}
	
	public List<MemberVO> memberOfPastThirtyDays() {
		List<MemberVO> memberOfPastThirtyDaysList = my.selectList("member.memberOfPastThirtyDays");
		return memberOfPastThirtyDaysList;
	}
	
	public List<MemberVO> listBankAccount(String id) {
		List<MemberVO> bankAccountList = my.selectList("member.listBankAccount", id);
		return bankAccountList;
	}
	
	public List<MemberVO> suspendedList() {
		List<MemberVO> suspendedList = my.selectList("member.suspended");
		return suspendedList;
	}
	
	public List<MemberVO> needPenaltyList() {
		List<MemberVO> needPenaltyList = my.selectList("member.needPenaltyList");
		return needPenaltyList;
	}
	
	public MemberVO oneById(String id) {
		MemberVO oneById = my.selectOne("member.oneById", id);
		return oneById;
	}
}

