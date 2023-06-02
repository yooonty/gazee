package com.multi.gazee.member;

import com.multi.gazee.admin.paging.PageVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

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
	
	public List<MemberVO> pagedList(PageVO pageVo) {
		List<MemberVO> pagedMemberList = my.selectList("member.pagedAll", pageVo);
		return pagedMemberList;
	}
	
	public int count() {
		return my.selectOne("member.count");
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
	
	public MemberVO one(String id) {
		MemberVO oneById = my.selectOne("member.one", id);
		return oneById;
	}
	
	public List<MemberVO> oneWhere(Map parameterMap) {
		List<MemberVO> oneWhere = my.selectList("member.oneWhere", parameterMap);
		return oneWhere;
	}
	
	public void executeSuspension(String memberId) {
		my.update("member.executeSuspension", memberId);
	}
	
	public void releaseSuspension(String memberId) {
		my.update("member.releaseSuspension", memberId);
	}
}

