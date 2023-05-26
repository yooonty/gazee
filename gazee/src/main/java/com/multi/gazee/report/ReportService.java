package com.multi.gazee.report;

import org.springframework.ui.Model;

public interface ReportService {
    String reportOne(int id, Model model) throws Exception;
    
    String reportReply(int reportId, String replyContent, Model model) throws Exception;
    
    String penalty(String reporteeId, String penaltyType);
}
