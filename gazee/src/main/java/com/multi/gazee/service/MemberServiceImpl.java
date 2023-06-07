package com.multi.gazee.service;

import com.multi.gazee.admin.paging.AdminPageVO;
import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.report.ReportDAO;
import com.multi.gazee.report.ReportVO;
import com.multi.gazee.reportCount.ReportCountDAO;
import com.multi.gazee.reportCount.ReportCountVO;
import com.multi.gazee.scheduler.SevenDaysScheduler;
import com.multi.gazee.scheduler.ThirtyDaysScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {
    
    @Autowired
    MemberDAO Mdao;
    @Autowired
    ReportDAO Rdao;
    @Autowired
    ReportCountDAO RCdao;
    @Autowired
    SevenDaysScheduler sevenDaysScheduler;
    @Autowired
    ThirtyDaysScheduler thirtyDaysScheduler;
    
    @Override
    public String getMemberList(AdminPageVO pageVo, Model model) {
        List<MemberVO> memberList = Mdao.list();
        List<MemberVO> memberExceptAdminList = Mdao.listExceptAdmin();
        List<MemberVO> newMemberThisWeekList = Mdao.newMemberThisWeek();
        List<MemberVO> memberOfPastThirtyDaysList = Mdao.memberOfPastThirtyDays();
        List<MemberVO> suspendedList = Mdao.suspendedList();
        List<ReportVO> nonPagedReportList = Rdao.nonPagedList();
        List<Integer> reportCountList = new ArrayList<>();
    
        for (MemberVO member : memberExceptAdminList) {
            String memberId = member.getId();
            ReportCountVO RCvo = RCdao.adminOne(memberId);
            int reportCount = 0;
            
            if (RCvo != null) {
                reportCount = RCvo.getCount();
            }
            reportCountList.add(reportCount);
        }
        model.addAttribute("memberList", memberList);
        model.addAttribute("memberListToShow", memberExceptAdminList);
        model.addAttribute("newMemberThisWeekList", newMemberThisWeekList);
        model.addAttribute("memberOfPastThirtyDaysList", memberOfPastThirtyDaysList);
        model.addAttribute("suspendedList", suspendedList);
        model.addAttribute("reportList", nonPagedReportList);
        model.addAttribute("countList", reportCountList);
    
        /* 페이징 */
        List<MemberVO> pagedMemberList = Mdao.pagedList(pageVo);
        int listSize = pagedMemberList.size();
        int pages = listSize / 10 +1;
    
        model.addAttribute("pagedMemberList", pagedMemberList);
        model.addAttribute("listSize", listSize);
        model.addAttribute("pages", pages);
        
        return "admin/adminMemberList";
    }
    
    @Override
    public String getMemberThisWeekList(Model model) {
        List<MemberVO> memberList = Mdao.list();
        List<MemberVO> memberExceptAdminList = Mdao.listExceptAdmin();
        List<MemberVO> newMemberThisWeekList = Mdao.newMemberThisWeek();
        List<MemberVO> memberOfPastThirtyDaysList = Mdao.memberOfPastThirtyDays();
        List<MemberVO> suspendedList = Mdao.suspendedList();
        List<ReportVO> nonPagedReportList = Rdao.nonPagedList();
        List<Integer> countList = new ArrayList<>();
        
        model.addAttribute("memberList", memberList);
        model.addAttribute("memberListToShow", memberExceptAdminList);
        model.addAttribute("newMemberThisWeekList", newMemberThisWeekList);
        model.addAttribute("memberOfPastThirtyDaysList", memberOfPastThirtyDaysList);
        model.addAttribute("suspendedList", suspendedList);
        
        return "admin/adminMemberThisWeekList";
    }
    
    @Override
    public String getMemberThisMonthList(Model model) {
        List<MemberVO> memberList = Mdao.list();
        List<MemberVO> memberExceptAdminList = Mdao.listExceptAdmin();
        List<MemberVO> newMemberThisWeekList = Mdao.newMemberThisWeek();
        List<MemberVO> memberOfPastThirtyDaysList = Mdao.memberOfPastThirtyDays();
        List<MemberVO> suspendedList = Mdao.suspendedList();
        
        model.addAttribute("memberList", memberList);
        model.addAttribute("memberListToShow", memberExceptAdminList);
        model.addAttribute("newMemberThisWeekList", newMemberThisWeekList);
        model.addAttribute("memberOfPastThirtyDaysList", memberOfPastThirtyDaysList);
        model.addAttribute("suspendedList", suspendedList);
        
        return "admin/adminMemberThisMonthList";
    }
    
    @Override
    public String getMemberSuspendedList(Model model) {
        List<MemberVO> memberList = Mdao.list();
        List<MemberVO> memberExceptAdminList = Mdao.listExceptAdmin();
        List<MemberVO> newMemberThisWeekList = Mdao.newMemberThisWeek();
        List<MemberVO> memberOfPastThirtyDaysList = Mdao.memberOfPastThirtyDays();
        List<MemberVO> suspendedList = Mdao.suspendedList();
        
        model.addAttribute("memberList", memberList);
        model.addAttribute("memberListToShow", memberExceptAdminList);
        model.addAttribute("newMemberThisWeekList", newMemberThisWeekList);
        model.addAttribute("memberOfPastThirtyDaysList", memberOfPastThirtyDaysList);
        model.addAttribute("suspendedList", suspendedList);
        
        return "admin/adminMemberSuspendedList";
    }
    
    @Override
    public String searchMember(String searchType, String searchIndex, Model model) throws Exception {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("searchType", searchType);
        parameterMap.put("searchIndex", searchIndex);
        List<MemberVO> oneWhereList = Mdao.adminSearch(parameterMap);
        model.addAttribute("searchList", oneWhereList);
        
        return "admin/adminMemberSearch";
    }
    
    @Override
    public String executeSuspension(String memberId, String period) throws Exception {
        if (period.equals("seven")) {
            Mdao.executeSuspension(memberId);
            sevenDaysScheduler.setMemberId(memberId);
            LocalDateTime releaseDate = LocalDateTime.now().plusDays(7);
            return "7일 제재가 적용되었습니다.\n해제 일시는" + releaseDate + "입니다.";
        } else if (period.equals("thirty")) {
            Mdao.executeSuspension(memberId);
            thirtyDaysScheduler.setMemberId(memberId);;
            LocalDateTime releaseDate = LocalDateTime.now().plusDays(30);
            return "30일 정지가 적용되었습니다.\n해제 일시는" + releaseDate + "입니다.";
        } else {
            Mdao.executeSuspension(memberId);
            return "영구 정지가 적용되었습니다.";
        }
    }
    
    @Override
    public String releaseSuspension(String reporteeId, String penaltyType) throws Exception {
        MemberVO vo = Mdao.adminOne(reporteeId);
        String currentStatus = vo.getStatus();
        if (currentStatus.equals("정상")) {
            return "해당 회원은 제재 상태가 아닙니다.";
        } else {
            Mdao.releaseSuspension(reporteeId);
            return "제재가 해제되었습니다.";
        }
    }
    
    @Override
    public String adminDeleteMember(int no) throws Exception {
        Mdao.adminDeleteMember(no);
        return "회원 삭제가 완료되었습니다.";
    }
}