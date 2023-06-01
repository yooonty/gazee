package com.multi.gazee.member;

import org.springframework.ui.Model;

public interface MemberService {
    String getMemberList(Model model) throws Exception;
    
    String getMemberThisWeekList(Model model) throws Exception;
    
    String getMemberThisMonthList(Model model) throws Exception;
    
    String getMemberSuspendedList(Model model) throws Exception;
    
    String searchMember(String searchType, String searchIndex, Model model) throws Exception;
    
    String executeSuspension(String memberId, String period) throws Exception;
    
    String releaseSuspension(String memberId, String penaltyType) throws Exception;
}
