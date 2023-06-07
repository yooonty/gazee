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
    
    /* Admin 계정 정보 One */
    public MemberVO readAdmin() throws Exception {
        return my.selectOne("member.readAdmin");
    }
    
    /* email로 Admin 체크 */
    public MemberVO checkAdmin(String email) throws Exception {
        return my.selectOne("member.checkAdmin", email);
    }
    
    /* Admin 암호 UPDATE */
    public void updatePw(MemberVO vo) throws Exception {
        int result = my.update("member.updatePw", vo);
    }
    
    /* 전체 회원 목록 (non-페이징) */
    public List<MemberVO> list() {
        List<MemberVO> memberList = my.selectList("member.all");
        return memberList;
    }
    
    /* 전체 회원 목록 (페이징) */
    public List<MemberVO> pagedList(PageVO pageVo) {
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
    public MemberVO one(String id) {
        MemberVO oneById = my.selectOne("member.one", id);
        return oneById;
    }
    
    /* 회원관리 내 검색 */
    public List<MemberVO> search(Map parameterMap) {
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