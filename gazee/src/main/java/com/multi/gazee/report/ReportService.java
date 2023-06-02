package com.multi.gazee.report;

import com.multi.gazee.admin.paging.PageVO;
import org.springframework.ui.Model;

public interface ReportService {
    String getReportList(PageVO pageVo, int pageNumber, Model model) throws Exception;
    
    String reportOne(int id, Model model) throws Exception;
    
    String reportReply(int reportId, String replyContent, Model model) throws Exception;
}
