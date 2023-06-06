package com.multi.gazee.member;

import com.multi.gazee.admin.paging.PageVO;
import org.springframework.ui.Model;

public interface MemberService {
    
    /* ADMIN 회원 목록 */
    String getMemberList(PageVO pageVo, Model model) throws Exception;
    
    /* ADMIN 이번 주 가입 회원 목록 */
    String getMemberThisWeekList(Model model) throws Exception;
    
    /* ADMIN 30일 내 가입 회원 목록 */
    String getMemberThisMonthList(Model model) throws Exception;
    
    /* ADMIN 제재 중인 회원 목록 */
    String getMemberSuspendedList(Model model) throws Exception;
    
    /* ADMIN 회원 검색 */
    String searchMember(String searchType, String searchIndex, Model model) throws Exception;
    
    /* ADMIN 제재 실행 */
    String executeSuspension(String memberId, String period) throws Exception;
    
    /* ADMIN 제재 해제 */
    String releaseSuspension(String memberId, String penaltyType) throws Exception;
}