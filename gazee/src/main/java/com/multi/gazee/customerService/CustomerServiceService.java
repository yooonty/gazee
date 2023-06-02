package com.multi.gazee.customerService;

import com.multi.gazee.admin.paging.PageVO;
import org.springframework.ui.Model;

public interface CustomerServiceService {
    String csOne(int id, Model model) throws Exception;
    
    String getCsList(PageVO pageVo, int pageNumber, Model model) throws Exception;
    
    String csReply(int csId, String replyContent) throws Exception;
}
