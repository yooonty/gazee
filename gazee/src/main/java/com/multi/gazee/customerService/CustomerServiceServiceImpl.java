package com.multi.gazee.customerService;

import com.multi.gazee.admin.paging.PageVO;
import com.multi.gazee.order.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class CustomerServiceServiceImpl implements CustomerServiceService {
    
    @Autowired
    CustomerServiceDAO CSdao;
    
    @Override
    public String csOne(int id, Model model) {
        CustomerServiceVO csOne = CSdao.one(id);
        model.addAttribute("csOne", csOne);
        return "../admin/adminCsOne";
    }
    
    @Override
    public String getCsList(PageVO pageVo, int pageNumber, Model model) {
        List<CustomerServiceVO> csList = CSdao.nonPagedList();
        
        /* 페이징 */
        pageVo.setPage(pageNumber);
        pageVo.setStartEnd(pageVo.getPage());
        List<CustomerServiceVO> pagedList = CSdao.pagedList(pageVo);
        int currentPage = pageVo.getPage();
        int count = CSdao.count();
        int pages = (int) (count / 10.0 + 1);
    
        model.addAttribute("pagedList", pagedList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pages", pages);
        model.addAttribute("count", count);
        model.addAttribute("csList", csList);
        return "../admin/adminCsList";
    }
    
    @Override
    public String csReply(int csId, String replyContent) {
        CustomerServiceVO vo = CSdao.one(csId);
        vo.setCsReply(replyContent);
        CSdao.replyRegister(vo);
        return "../admin/adminCs";
    }
}