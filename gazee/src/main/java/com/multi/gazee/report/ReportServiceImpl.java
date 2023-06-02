package com.multi.gazee.report;

import com.multi.gazee.admin.paging.PageVO;
import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.reportCount.ReportCountDAO;
import com.multi.gazee.reportCount.ReportCountVO;
import com.multi.gazee.withdraw.WithdrawVO;
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
    
    public String getReportList(PageVO pageVo, int pageNumber, Model model) {
        List<ReportVO> reportList = Rdao.nonPagedList();
    
        /* 페이징 */
        pageVo.setPage(pageNumber);
        pageVo.setStartEnd(pageVo.getPage());
        List<ReportVO> pagedReportList = Rdao.pagedList(pageVo);
        int currentPage = pageVo.getPage();
        int count = Mdao.count();
        int pages = (int) (count / 10.0 + 1);
    
        model.addAttribute("pagedReportList", pagedReportList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pages", pages);
        model.addAttribute("count", count);
        model.addAttribute("reportList", reportList);
        return "../admin/adminReportList";
    }
    
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
}
