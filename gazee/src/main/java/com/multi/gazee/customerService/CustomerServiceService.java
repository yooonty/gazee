package com.multi.gazee.customerService;

import org.springframework.ui.Model;

public interface CustomerServiceService {
    String csOne(int id, Model model) throws Exception;
    
    String getCsList(Model model) throws Exception;
    
    String csReply(int csId, String replyContent) throws Exception;
}
