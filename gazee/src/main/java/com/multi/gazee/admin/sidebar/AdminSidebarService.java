package com.multi.gazee.admin.sidebar;

import com.multi.gazee.admin.paging.AdminPageVO;
import org.springframework.ui.Model;

public interface AdminSidebarService {
    String loadDashboard(Model model);
    String loadMember(AdminPageVO pageVo, int pageNumber, Model model) throws Exception;
    String loadProduct(AdminPageVO pageVo, int pageNumber, Model model) throws Exception;
    String loadOrder(Model model) throws Exception;
    String loadMoney(Model model) throws Exception;
    String loadCs(AdminPageVO pageVo, int pageNumber, Model model) throws Exception;
    String loadReport(AdminPageVO pageVo, int pageNumber, Model model);
    String loadInfo(Model model) throws Exception;
    String loadInfoEdit(Model model) throws Exception;
}
