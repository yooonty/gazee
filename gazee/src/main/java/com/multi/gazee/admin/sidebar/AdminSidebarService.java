package com.multi.gazee.admin.sidebar;

import com.multi.gazee.admin.paging.PageVO;
import org.springframework.ui.Model;

public interface AdminSidebarService {
    String loadDashboard(Model model);
    String loadMember(PageVO pageVo, int pageNumber, Model model) throws Exception;
    String loadProduct(PageVO pageVo, int pageNumber, Model model) throws Exception;
    String loadOrder(Model model) throws Exception;
    String loadMoney(Model model) throws Exception;
    String loadCs(PageVO pageVo, int pageNumber, Model model) throws Exception;
    String loadReport(PageVO pageVo, int pageNumber, Model model);
    String loadInfo(Model model) throws Exception;
    String loadInfoEdit(Model model) throws Exception;
}
