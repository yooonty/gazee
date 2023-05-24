package com.multi.gazee.admin.admin;

import com.multi.gazee.admin.login.LoginService;
import com.multi.gazee.brcypt.BcryptServiceImpl;
import com.multi.gazee.charge.ChargeDAO;
import com.multi.gazee.charge.ChargeVO;
import com.multi.gazee.customerService.CustomerServiceDAO;
import com.multi.gazee.customerService.CustomerServiceVO;
import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.order.OrderDAO;
import com.multi.gazee.order.OrderVO;
import com.multi.gazee.product.ProductDAO;
import com.multi.gazee.product.ProductVO;
import com.multi.gazee.report.ReportDAO;
import com.multi.gazee.report.ReportVO;
import com.multi.gazee.set.SetDAO;
import com.multi.gazee.set.SetVO;
import com.multi.gazee.transactionHistory.TransactionHistoryDAO;
import com.multi.gazee.transactionHistory.TransactionHistoryVO;
import com.multi.gazee.withdraw.WithdrawDAO;
import com.multi.gazee.withdraw.WithdrawVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    MemberDAO Mdao;
    
    @Autowired
    ProductDAO Pdao;
    
    @Autowired
    CustomerServiceDAO CSdao;
    
    @Autowired
    ChargeDAO Cdao;
    
    @Autowired
    ReportDAO Rdao;
    
    @Autowired
    OrderDAO Odao;
    
    @Autowired
    WithdrawDAO Wdao;
    
    @Autowired
    SetDAO Sdao;
    
    @Autowired
    TransactionHistoryDAO Tdao;
    
    @Autowired
    BcryptServiceImpl bcry = new BcryptServiceImpl();
    
    @Autowired
    public LoginService loginService;
    
    @RequestMapping(value = "gazee.do")
    public String redirectGazee() throws Exception {
        return "gazee";
    }
    
    @RequestMapping(value = "main.do")
    public String goToMain() throws Exception {
        return "../admin/adminSidebar";
    }
    
    @RequestMapping(value = "dashboard.do")
    public String loadDashboard(Model model) throws Exception {
        
        List<MemberVO> memberList = Mdao.list();
        List<ProductVO> productList = Pdao.list();
        List<ReportVO> nonPagedReportList = Rdao.nonPagedList();
        List<OrderVO> orderNeedToSetList = Odao.listOrderNeedToSet();
        List<OrderVO> orderList = Odao.listOrder();
        int sum = Wdao.sumCommission();
        model.addAttribute("memberList", memberList);
        model.addAttribute("productList", productList);
        model.addAttribute("reportList", nonPagedReportList);
        model.addAttribute("orderNeedToSetList", orderNeedToSetList);
        model.addAttribute("orderList", orderList);
        model.addAttribute("sum", sum);
        return "../admin/adminDashboard";
    }
    
    @RequestMapping(value = "info.do")
    public String loadInfo(Model model) throws Exception {
        MemberVO memberVo = Mdao.readAdmin();
        model.addAttribute("adminOne", memberVo);
        return "../admin/adminInfo";
    }
    
    @RequestMapping(value = "infoEdit.do")
    public String loadInfoEdit(Model model) throws Exception {
        MemberVO memberVo = Mdao.readAdmin();
        model.addAttribute("adminOne", memberVo);
        return "../admin/adminInfoEdit";
    }
    
    @RequestMapping(value = "adminPwEdit.do")
    public @ResponseBody String loadInfoEdit(@ModelAttribute("currentPw") String currentPw, @ModelAttribute("newPw") String newPw, @ModelAttribute("newPwCheck") String newPwCheck, @ModelAttribute("resultMsg") String resultMsg, Model model) throws Exception {
        MemberVO adminVo = Mdao.readAdmin();
        String plainPassword = currentPw;
        String hasedPassword = "";
        int pwCheck = loginService.checkPass(plainPassword, hasedPassword);
        if (pwCheck == 1) {
            if (newPw.equals(newPwCheck)) {
                String hasedNewPw = bcry.encrypt(newPw);
                adminVo.setPw(hasedNewPw);
                Mdao.updatePw(adminVo);
                model.addAttribute("adminOne", adminVo);
                return "SUCCESS";
            } else {
                model.addAttribute("adminOne", adminVo);
                return "MISMATCH";
            }
        } else {
            model.addAttribute("adminOne", adminVo);
            return "INCORRECT";
        }
    }
    
    @RequestMapping(value = "member.do")
    public String loadMember(Model model) throws Exception {
        
        List<MemberVO> memberList = Mdao.list();
        List<MemberVO> newMemberThisWeekList = Mdao.newMemberThisWeek();
        List<MemberVO> memberOfPastThirtyDaysList = Mdao.memberOfPastThirtyDays();
        List<MemberVO> suspendedList = Mdao.suspendedList();
        List<ReportVO> nonPagedReportList = Rdao.nonPagedList();
        
        model.addAttribute("memberList", memberList);
        model.addAttribute("newMemberThisWeekList", newMemberThisWeekList);
        model.addAttribute("memberOfPastThirtyDaysList", memberOfPastThirtyDaysList);
        model.addAttribute("suspendedList", suspendedList);
        model.addAttribute("reportList", nonPagedReportList);
        return "../admin/adminMember";
    }
    
    
    @RequestMapping(value = "memberList.do")
    public String loadMemberList(Model model) throws Exception {
        
        List<MemberVO> memberList = Mdao.list();
        List<MemberVO> newMemberThisWeekList = Mdao.newMemberThisWeek();
        List<MemberVO> memberOfPastThirtyDaysList = Mdao.memberOfPastThirtyDays();
        List<MemberVO> suspendedList = Mdao.suspendedList();
        List<ReportVO> nonPagedReportList = Rdao.nonPagedList();
        
        model.addAttribute("memberList", memberList);
        model.addAttribute("newMemberThisWeekList", newMemberThisWeekList);
        model.addAttribute("memberOfPastThirtyDaysList", memberOfPastThirtyDaysList);
        model.addAttribute("suspendedList", suspendedList);
        model.addAttribute("reportList", nonPagedReportList);
        
        return "../admin/adminMemberList";
    }
    
    @RequestMapping(value = "order.do")
    public String loadOrder(Model model) throws Exception {
        List<ProductVO> productList = Pdao.list();
        List<OrderVO> orderList = Odao.listOrder();
        List<OrderVO> orderNeedToSetList = Odao.listOrderNeedToSet();
        List<OrderVO> orderFinishedList = Odao.listOrderFinished();
        int orderInProgress = orderList.size() - orderFinishedList.size();
        int sum = Wdao.sumCommission();
        List<SetVO> setList = Sdao.listSet();
        
        model.addAttribute("productList", productList);
        model.addAttribute("orderList", orderList);
        model.addAttribute("orderNeedToSetList", orderNeedToSetList);
        model.addAttribute("orderFinishedList", orderFinishedList);
        model.addAttribute("orderInProgress", orderInProgress);
        model.addAttribute("sum", sum);
        model.addAttribute("setList", setList);
        return "../admin/adminOrder";
    }
    
    @RequestMapping(value = "orderList.do")
    public String loadOrderList(Model model) throws Exception {
        List<OrderVO> orderList = Odao.listOrder();
        model.addAttribute("orderList", orderList);
        return "../admin/adminOrderList";
    }
    
    @RequestMapping(value = "chargeList.do")
    public String loadChargeList(Model model) throws Exception {
        List<ChargeVO> chargeList = Cdao.listCharge();
        model.addAttribute("chargeList", chargeList);
        return "../admin/adminChargeList";
    }
    
    @RequestMapping(value = "set.do")
    public String set(@ModelAttribute("productId") int productId, @ModelAttribute("sellerId") String sellerId, Model model) throws Exception {
        int balance = Tdao.getBalance(sellerId);
        List<OrderVO> orderList = Odao.listOrder();
        model.addAttribute("orderList", orderList);
        return "정산이 완료되었습니다.";
    }
    
    @RequestMapping(value = "product.do")
    public String loadProduct(Model model) throws Exception {
        List<ProductVO> productList = Pdao.list();
        List<ProductVO> productTodayList = Pdao.listProductToday();
        List<OrderVO> orderList = Odao.listOrder();
        List<OrderVO> orderFinishedList = Odao.listOrderFinished();
        int sum = Odao.sumTotalTrading();
        
        model.addAttribute("productList", productList);
        model.addAttribute("productTodayList", productTodayList);
        model.addAttribute("orderList", orderList);
        model.addAttribute("orderFinishedList", orderFinishedList);
        model.addAttribute("sum", sum);
        return "../admin/adminProduct";
    }
    
    @RequestMapping(value = "pay.do")
    public String loadPay(Model model) throws Exception {
        List<TransactionHistoryVO> transactionList = Tdao.listTransactionHistory();
        List<WithdrawVO> withdrawList = Wdao.listWithdraw();
        List<MemberVO> bankAccountList = new ArrayList<>(); // 사용자 계좌 목록 담을 리스트
        // withdrawList에서 user 값을 하나씩 꺼내서 Adao.listBankAccount()의 파라미터로 사용
        List<MemberVO> memberList = Mdao.list();
        int sum = Wdao.sumCommission();
        
        for (WithdrawVO withdraw : withdrawList) {
            String user = withdraw.getMemberId();
            List<MemberVO> userBankAccounts = Mdao.listBankAccount(user);
            bankAccountList.addAll(userBankAccounts);
        }
        
        model.addAttribute("seedHistoryList", transactionList);
        model.addAttribute("withdrawList", withdrawList);
        model.addAttribute("memberList", memberList);
        model.addAttribute("bankAccountList", bankAccountList); // 사용자 계좌 목록
        model.addAttribute("sum", sum);
        
        return "../admin/adminPay";
    }
    
    @RequestMapping(value = "cs.do")
    public String loadCs(Model model) throws Exception {
        List<CustomerServiceVO> csList = CSdao.nonPagedList();
        model.addAttribute("csList", csList);
        return "../admin/adminCs";
    }
    
    @RequestMapping(value = "cs_one.do")
    public String cs_one(@ModelAttribute("csId") int id, Model model) throws Exception {
        CustomerServiceVO csOne = CSdao.one(id);
        model.addAttribute("csOne", csOne);
        return "../admin/adminCsOne";
    }
    
    @RequestMapping(value = "report.do")
    public String loadReport(Model model) throws Exception {
        List<ReportVO> nonPagedReportList = Rdao.nonPagedList();
        model.addAttribute("reportList", nonPagedReportList);
        return "../admin/adminReport";
    }
    
    @RequestMapping(value = "report_one.do")
    public String report_one(@ModelAttribute("reportId") int id, Model model) throws Exception {
        ReportVO reportOne = Rdao.one(id);
        model.addAttribute("reportOne", reportOne);
        return "../admin/adminReportOne";
    }
    
    @RequestMapping(value = "replyRegisterComplete.do")
    public String replyRegister(@ModelAttribute("reportId") int reportId, @ModelAttribute("replyContent") String replyContent, Model model) throws Exception {
        ReportVO vo = Rdao.one(reportId);
        vo.setReportReply(replyContent);
        Rdao.replyRegister(vo);
        List<ReportVO> nonPagedReportList = Rdao.nonPagedList();
        model.addAttribute("reportList", nonPagedReportList);
        return "../admin/adminReport";
    }
}
