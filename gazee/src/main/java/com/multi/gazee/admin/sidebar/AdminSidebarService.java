package com.multi.gazee.admin.sidebar;

import org.springframework.ui.Model;

public interface AdminSidebarService {
    String loadDashboard(Model model);
    String loadInfoEdit(Model model) throws Exception;
    String loadInfo(Model model) throws Exception;
    String loadOrder(Model model) throws Exception;
    String loadProduct(Model model) throws Exception;
    String loadMoney(Model model) throws Exception;
    String loadCs(Model model) throws Exception;
    String loadMember(Model model) throws Exception;
    String loadReport(Model model);
}
