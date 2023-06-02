package com.multi.gazee.admin;

import com.multi.gazee.admin.excel.ExcelService;
import com.multi.gazee.admin.findPw.AdminFindPwServiceImpl;
import com.multi.gazee.admin.login.AdminLoginService;
import com.multi.gazee.admin.paging.PageVO;
import com.multi.gazee.admin.pwEdit.AdminPwEditServiceImpl;
import com.multi.gazee.admin.sidebar.AdminSidebarService;
import com.multi.gazee.brcypt.BcryptService;
import com.multi.gazee.charge.ChargeService;
import com.multi.gazee.customerService.CustomerServiceService;
import com.multi.gazee.member.MemberService;
import com.multi.gazee.order.OrderService;
import com.multi.gazee.product.ProductService;
import com.multi.gazee.report.ReportService;
import com.multi.gazee.set.SetService;
import com.multi.gazee.withdraw.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AdminController {
    
    @Autowired
    AdminFindPwServiceImpl adminFindPwService;
    @Autowired
    AdminPwEditServiceImpl adminPwEditService;
    @Autowired
    AdminSidebarService adminSidebarService;
    @Autowired
    MemberService memberService;
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;
    @Autowired
    ChargeService chargeService;
    @Autowired
    WithdrawService withdrawService;
    @Autowired
    CustomerServiceService customerServiceService;
    @Autowired
    ReportService reportService;
    @Autowired
    SetService setService;
    @Autowired
    BcryptService bcryptService;
    @Autowired
    AdminLoginService adminLoginService;
    @Autowired
    ExcelService excelService;
    
    /* Admin Login */
    
    @PostMapping(value = "/admin_main")
    public String adminLogin(@RequestParam("user_id") String idToCheck, @RequestParam("user_pwd") String pwToCheck, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        return adminLoginService.login(idToCheck, pwToCheck, request, response, model);
    }
    
    @RequestMapping(value = "logout.do")
    public String adminLogout(HttpServletRequest request) throws Exception {
        return adminLoginService.invalidateSession(request);
    }
    
    @RequestMapping(value = "/admin")
    public String checkCookie(HttpServletRequest request, Model model) throws Exception {
        return adminLoginService.checkCookie(request, model);
    }
    
    /* Reset Admin Password */
    
    @RequestMapping(value = "findPwForm.do")
    public String findPwForm() throws Exception {
        return "../admin/findPwForm";
    }
    
    @RequestMapping(value = "findPw.do", method = RequestMethod.POST)
    public void findPw(@ModelAttribute("email") String email, HttpServletResponse response) throws Exception {
        adminFindPwService.findPw(response, email);
    }
    
    /* Edit Admin Password */
    
    @RequestMapping(value = "infoEdit.do")
    public String loadInfoEdit(Model model) throws Exception {
        return adminSidebarService.loadInfoEdit(model);
    }
    
    @RequestMapping(value = "adminPwEdit.do")
    public @ResponseBody String editAdminPw(@ModelAttribute("currentPw") String currentPw, @ModelAttribute("newPw") String newPw, @ModelAttribute("newPwCheck") String newPwCheck, @ModelAttribute("resultMsg") String resultMsg, Model model) throws Exception {
        return adminLoginService.editAdminPw(currentPw, newPw, newPwCheck, model);
    }
    
    /* Admin Sidebar */
    
    @RequestMapping(value = "main.do")
    public String goToMain() {
        return "../admin/adminSidebar";
    }
    
    @RequestMapping(value = "gazee.do")
    public String redirectGazee() {
        return "gazee";
    }
    
    @RequestMapping(value = "dashboard.do")
    public String loadDashboard(Model model) throws Exception {
        return adminSidebarService.loadDashboard(model);
    }
    
    @RequestMapping(value = "info.do")
    public String loadInfo(Model model) throws Exception {
        return adminSidebarService.loadInfo(model);
    }
    
    @RequestMapping(value = "member.do")
    public String loadMember(PageVO pageVo, @RequestParam("pageNumber") int pageNumber, Model model) throws Exception {
        return adminSidebarService.loadMember(pageVo, pageNumber, model);
    }
    
    @RequestMapping(value = "order.do")
    public String load(Model model) throws Exception {
        return adminSidebarService.loadOrder(model);
    }
    
    @RequestMapping(value = "product.do")
    public String loadProduct(PageVO pageVo, @RequestParam("pageNumber") int pageNumber, Model model) throws Exception {
        return adminSidebarService.loadProduct(pageVo, pageNumber, model);
    }
    
    @RequestMapping(value = "money.do")
    public String loadMoney(Model model) throws Exception {
        return adminSidebarService.loadMoney(model);
    }
    
    @RequestMapping(value = "cs.do")
    public String loadCs(PageVO pageVo, @RequestParam("pageNumber") int pageNumber, Model model) throws Exception {
        return adminSidebarService.loadCs(pageVo, pageNumber, model);
    }
    
    @RequestMapping(value = "report.do")
    public String loadReport(PageVO pageVo, @RequestParam("pageNumber") int pageNumber, Model model) throws Exception {
        return adminSidebarService.loadReport(pageVo, pageNumber, model);
    }
    
    /* Member */
    
    @RequestMapping(value = "memberList.do")
    public String loadMemberList(PageVO pageVo, Model model) throws Exception {
        return memberService.getMemberList(pageVo, model);
    }
    
    @RequestMapping(value = "memberThisWeekList.do")
    public String loadMemberThisWeek(Model model) throws Exception {
        return memberService.getMemberThisWeekList(model);
    }
    
    @RequestMapping(value = "memberThisMonthList.do")
    public String loadMemberThisMonth(Model model) throws Exception {
        return memberService.getMemberThisMonthList(model);
    }
    
    @RequestMapping(value = "memberSuspendedList.do")
    public String loadSuspendedMember(Model model) throws Exception {
        return memberService.getMemberSuspendedList(model);
    }
    
    @RequestMapping(value = "searchMember.do")
    public String searchMember(@RequestParam("search_type") String searchType, @RequestParam("search_index") String searchIndex, Model model) throws Exception {
        return memberService.searchMember(searchType, searchIndex, model);
    }
    
    /* Execute suspension */
    
    @RequestMapping(value = "executeSuspension.do", produces = "application/text; charset=utf8")
    @ResponseBody
    public String executeSuspension(@ModelAttribute("reporteeId") String memberId, @ModelAttribute("penaltyType") String period) throws Exception {
        return memberService.executeSuspension(memberId, period);
    }
    
    /* Release from suspension */
    
    @RequestMapping(value = "releaseSuspension.do", produces = "application/text; charset=utf8")
    @ResponseBody
    public String releaseSuspension(@ModelAttribute("reporteeId") String memberId, @ModelAttribute("penaltyType") String penaltyType) throws Exception {
        return memberService.releaseSuspension(memberId, penaltyType);
    }
    
    /* Product */
    
    @RequestMapping(value = "searchProduct.do")
    public String searchProduct(@RequestParam("search_type") String searchType, @RequestParam("search_index") String searchIndex, Model model) throws Exception {
        return productService.searchProduct(searchType, searchIndex, model);
    }
    
    /* Order */
    
    @RequestMapping(value = "orderList.do")
    public String loadOrderList(Model model) throws Exception {
        return orderService.getOrderList(model);
    }
    
    @RequestMapping(value = "loadOrderInProgress.do")
    public String loadOrderInProgressList(Model model) throws Exception {
        return orderService.getloadOrderInProgressList(model);
    }
    
    @RequestMapping(value = "loadOrderFinished.do")
    public String loadOrderFinishedList(Model model) throws Exception {
        return orderService.getOrderFinishedList(model);
    }
    
    @RequestMapping(value = "searchOrder.do")
    public String searchOrder(@RequestParam("search_type") String searchType, @RequestParam("search_index") String searchIndex, Model model) throws Exception {
        return orderService.searchOrder(searchType, searchIndex, model);
    }
    
    /* Set */
    
    @RequestMapping(value = "set.do", produces = "application/text; charset=utf8")
    @ResponseBody
    public String set(@ModelAttribute("productId") int productId, @ModelAttribute("sellerId") String sellerId, @ModelAttribute("orderTransactionId") String orderTransactionId) throws Exception {
        return setService.settlement(productId, sellerId, orderTransactionId);
    }
    
    /* Money */
    
    @RequestMapping(value = "searchWithdraw.do")
    public String searchWithdraw(@RequestParam("search_type") String searchType, @RequestParam("search_index") String searchIndex, Model model) throws Exception {
        return withdrawService.searchWithdraw(searchType, searchIndex, model);
    }
    
    @RequestMapping(value = "searchBalance.do")
    public String searchBalacne(@RequestParam("search_index") String memberId, Model model) throws Exception {
        return withdrawService.searchBalance(memberId, model);
    }
    
    @RequestMapping(value = "balanceList.do")
    public String loadBalanceList(Model model) throws Exception {
        return withdrawService.getBalanceList(model);
    }
    
    @RequestMapping(value = "chargeList.do")
    public String loadChargeList(Model model) throws Exception {
        return chargeService.getChargeList(model);
    }
    
    @RequestMapping(value = "withdrawList.do")
    public String loadWithdrawList(Model model) throws Exception {
        return withdrawService.getWithdrawList(model);
    }
    
    /* CS */
    
    @RequestMapping(value = "csList.do")
    public String loadCsList(PageVO pageVo, @RequestParam("pageNumber") int pageNumber, Model model) throws Exception {
        return customerServiceService.getCsList(pageVo, pageNumber, model);
    }
    
    @RequestMapping(value = "cs_one.do")
    public String csOne(@ModelAttribute("csId") int id, Model model) throws Exception {
        return customerServiceService.csOne(id, model);
    }
    
    @RequestMapping(value = "csReplyRegisterComplete.do")
    public String csReplyRegister(@ModelAttribute("csId") int csId, @ModelAttribute("replyContent") String replyContent) throws Exception {
        return customerServiceService.csReply(csId, replyContent);
    }
    
    /* Report */
    
    @RequestMapping(value = "reportList.do")
    public String loadReportList(PageVO pageVo, @RequestParam("pageNumber") int pageNumber, Model model) throws Exception {
        return reportService.getReportList(pageVo, pageNumber, model);
    }
    
    @RequestMapping(value = "report_one.do")
    public String report_one(@ModelAttribute("reportId") int id, Model model) throws Exception {
        return reportService.reportOne(id, model);
    }
    
    @RequestMapping(value = "reportReplyRegisterComplete.do")
    public String replyRegister(@ModelAttribute("reportId") int reportId, @ModelAttribute("replyContent") String replyContent, Model model) throws Exception {
        return reportService.reportReply(reportId, replyContent, model);
    }
    
    /* Export XLSX */
    @GetMapping("excelMember.do")
    public ResponseEntity<InputStreamResource> excelMember(HttpServletResponse response) throws Exception {
        return excelService.memberExcel();
    }
    
    @GetMapping("excelProduct.do")
    public ResponseEntity<InputStreamResource> excelProduct(HttpServletResponse response) throws Exception {
        return excelService.productExcel();
    }
    
    @GetMapping("excelOrder.do")
    public ResponseEntity<InputStreamResource> excelOrder(HttpServletResponse response) throws Exception {
        return excelService.orderExcel();
    }
    
    @GetMapping("excelWithdraw.do")
    public ResponseEntity<InputStreamResource> excelWithdraw(HttpServletResponse response) throws Exception {
        return excelService.withdrawExcel();
    }
    
    @GetMapping("excelCharge.do")
    public ResponseEntity<InputStreamResource> excelCharge(HttpServletResponse response) throws Exception {
        return excelService.chargeExcel();
    }
}


