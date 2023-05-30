package com.multi.gazee.customerService;

import com.multi.gazee.order.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class CustomerServiceServiceImpl implements CustomerServiceService {
    
    @Autowired
    CustomerServiceDAO CSdao;
    
    public String csOne(int id, Model model) {
        CustomerServiceVO csOne = CSdao.one(id);
        model.addAttribute("csOne", csOne);
        return "../admin/adminCsOne";
    }
    
    public String getCsList(Model model) {
        List<CustomerServiceVO> csList = CSdao.nonPagedList();
        model.addAttribute("csList", csList);
        return "../admin/adminCsList";
    }
    
    public String csReply(int csId, String replyContent) {
        CustomerServiceVO vo = CSdao.one(csId);
        vo.setCsReply(replyContent);
        CSdao.replyRegister(vo);
        return "../admin/adminCs";
    }
}
