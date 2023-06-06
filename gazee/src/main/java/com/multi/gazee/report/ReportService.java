package com.multi.gazee.report;

import com.multi.gazee.admin.paging.PageVO;
import org.springframework.ui.Model;

public interface ReportService {
    
    /* ADMIN 신고 목록 */
    String getReportList(PageVO pageVo, int pageNumber, Model model) throws Exception;
    
    /* ADMIN 신고 상세 */
    String reportOne(int id, Model model) throws Exception;
    
    /* ADMIN 신고 답변 */
    String reportReply(int reportId, String replyContent, Model model) throws Exception;
}