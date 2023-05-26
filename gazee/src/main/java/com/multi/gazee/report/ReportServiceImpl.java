package com.multi.gazee.report;

import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.reportCount.ReportCountDAO;
import com.multi.gazee.reportCount.ReportCountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    
    @Autowired
    ReportDAO Rdao;
    @Autowired
    ReportCountDAO RCdao;
    @Autowired
    MemberDAO Mdao;
    
    public String reportOne(int id, Model model) {
        ReportVO reportOne = Rdao.one(id);
        String reportee = reportOne.getReportee();
        ReportCountVO countOne = RCdao.one(reportee);
        MemberVO reporteeInfo = Mdao.one(reportee);
        model.addAttribute("reportOne", reportOne);
        model.addAttribute("countOne", countOne);
        model.addAttribute("reporteeInfo", reporteeInfo);
        return "../admin/adminReportOne";
    }
    
    public String reportReply(int reportId, String replyContent, Model model) {
        ReportVO vo = Rdao.one(reportId);
        vo.setReportReply(replyContent);
        Rdao.replyRegister(vo);
        List<ReportVO> nonPagedReportList = Rdao.nonPagedList();
        model.addAttribute("reportList", nonPagedReportList);
        return "../admin/adminReport";
    }
    
    public String penalty(String reporteeId, String penaltyType) {
        MemberVO vo = Mdao.one(reporteeId);
        String currentStatus = vo.getStatus();
        if (penaltyType.equals("release")) {
            if (currentStatus.equals("정상")) {
                return "해당 회원은 제재 상태가 아닙니다.";
            } else {
                vo.setStatus("정상");
                Mdao.changeStatus(vo);
                return "제재가 해제되었습니다.";
            }
        } else {
            vo.setStatus("정지");
            Mdao.changeStatus(vo);
            return "제재가 적용되었습니다.";
        }
    }
}
