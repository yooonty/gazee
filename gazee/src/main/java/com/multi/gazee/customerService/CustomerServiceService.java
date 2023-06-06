package com.multi.gazee.customerService;

import com.multi.gazee.admin.paging.PageVO;
import org.springframework.ui.Model;

public interface CustomerServiceService {
    
    /* ADMIN CS 상세 */
    String csOne(int id, Model model) throws Exception;
    
    
    /* ADMIN CS 목록 */
    String getCsList(PageVO pageVo, int pageNumber, Model model) throws Exception;
    
    
    /* ADMIN CS 답변 등록 */
    String csReply(int csId, String replyContent) throws Exception;
}