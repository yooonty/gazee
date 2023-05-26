package com.multi.gazee.customerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class CustomerServiceServiceImpl implements CustomerServiceService {
    
    @Autowired
    CustomerServiceDAO CSdao;
    
    public String csOne(int id, Model model) {
        CustomerServiceVO csOne = CSdao.one(id);
        model.addAttribute("csOne", csOne);
        return "../admin/adminCsOne";
    }
    
    public String csReply(int csId, String replyContent) {
        CustomerServiceVO vo = CSdao.one(csId);
        vo.setCsReply(replyContent);
        CSdao.replyRegister(vo);
        return "../admin/adminCs";
    }
}
