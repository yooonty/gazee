package com.multi.gazee.member;

import com.multi.gazee.report.ReportDAO;
import com.multi.gazee.report.ReportVO;
import com.multi.gazee.reportCount.ReportCountDAO;
import com.multi.gazee.reportCount.ReportCountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    
    @Autowired
    MemberDAO Mdao;
    @Autowired
    ReportDAO Rdao;
    @Autowired
    ReportCountDAO RCdao;
    
    public String getMemberList(Model model) {
        List<MemberVO> memberList = Mdao.list();
        List<MemberVO> memberExceptAdminList = Mdao.listExceptAdmin();
        List<MemberVO> newMemberThisWeekList = Mdao.newMemberThisWeek();
        List<MemberVO> memberOfPastThirtyDaysList = Mdao.memberOfPastThirtyDays();
        List<MemberVO> suspendedList = Mdao.suspendedList();
        List<ReportVO> nonPagedReportList = Rdao.nonPagedList();
        List<Integer> countList = new ArrayList<>();
        
        for (MemberVO member : memberExceptAdminList) {
            String memberId = member.getId();
            ReportCountVO RCvo = RCdao.one(memberId);
            int count = 0;
            
            if (RCvo != null) {
                count = RCvo.getCount();
            }
            countList.add(count);
        }
        model.addAttribute("memberList", memberList);
        model.addAttribute("memberExceptAdminList", memberExceptAdminList);
        model.addAttribute("newMemberThisWeekList", newMemberThisWeekList);
        model.addAttribute("memberOfPastThirtyDaysList", memberOfPastThirtyDaysList);
        model.addAttribute("suspendedList", suspendedList);
        model.addAttribute("reportList", nonPagedReportList);
        model.addAttribute("countList", countList);
        
        return "../admin/adminMemberList";
    }
    
    public String getMemberThisWeekList(Model model) {
        List<MemberVO> memberList = Mdao.list();
        List<MemberVO> memberExceptAdminList = Mdao.listExceptAdmin();
        List<MemberVO> newMemberThisWeekList = Mdao.newMemberThisWeek();
        List<MemberVO> memberOfPastThirtyDaysList = Mdao.memberOfPastThirtyDays();
        List<MemberVO> suspendedList = Mdao.suspendedList();
        List<ReportVO> nonPagedReportList = Rdao.nonPagedList();
        List<Integer> countList = new ArrayList<>();
        
        model.addAttribute("memberList", memberList);
        model.addAttribute("memberExceptAdminList", memberExceptAdminList);
        model.addAttribute("newMemberThisWeekList", newMemberThisWeekList);
        model.addAttribute("memberOfPastThirtyDaysList", memberOfPastThirtyDaysList);
        model.addAttribute("suspendedList", suspendedList);
        
        return "../admin/adminMemberThisWeekList";
    }
    
    public String getMemberThisMonthList(Model model) {
        List<MemberVO> memberList = Mdao.list();
        List<MemberVO> memberExceptAdminList = Mdao.listExceptAdmin();
        List<MemberVO> newMemberThisWeekList = Mdao.newMemberThisWeek();
        List<MemberVO> memberOfPastThirtyDaysList = Mdao.memberOfPastThirtyDays();
        List<MemberVO> suspendedList = Mdao.suspendedList();
        
        model.addAttribute("memberList", memberList);
        model.addAttribute("memberExceptAdminList", memberExceptAdminList);
        model.addAttribute("newMemberThisWeekList", newMemberThisWeekList);
        model.addAttribute("memberOfPastThirtyDaysList", memberOfPastThirtyDaysList);
        model.addAttribute("suspendedList", suspendedList);
        
        return "../admin/adminMemberThisMonthList";
    }
    
    public String getMemberSuspendedList(Model model) {
        List<MemberVO> memberList = Mdao.list();
        List<MemberVO> memberExceptAdminList = Mdao.listExceptAdmin();
        List<MemberVO> newMemberThisWeekList = Mdao.newMemberThisWeek();
        List<MemberVO> memberOfPastThirtyDaysList = Mdao.memberOfPastThirtyDays();
        List<MemberVO> suspendedList = Mdao.suspendedList();
        
        model.addAttribute("memberList", memberList);
        model.addAttribute("memberExceptAdminList", memberExceptAdminList);
        model.addAttribute("newMemberThisWeekList", newMemberThisWeekList);
        model.addAttribute("memberOfPastThirtyDaysList", memberOfPastThirtyDaysList);
        model.addAttribute("suspendedList", suspendedList);
        
        return "../admin/adminMemberSuspendedList";
    }
}

