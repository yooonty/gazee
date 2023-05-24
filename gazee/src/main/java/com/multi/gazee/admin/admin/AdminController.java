package com.multi.gazee.admin.admin;

import com.multi.gazee.admin.login.LoginService;
import com.multi.gazee.brcypt.BcryptServiceImpl;
import com.multi.gazee.charge.ChargeVO;
import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.order.OrderVO;
import com.multi.gazee.product.ProductDAO;
import com.multi.gazee.product.ProductVO;
import com.multi.gazee.customerService.ReportDAO;
import com.multi.gazee.customerService.ReportVO;
import com.multi.gazee.set.SetVO;
import com.multi.gazee.transactionHistory.TransactionHistoryVO;
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
    ReportDAO Rdao;
    @Autowired
    AdminDAO Adao;
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
        List<OrderVO> orderNeedToSetList = Adao.listOrderNeedToSet();
        List<OrderVO> orderList = Adao.listOrder();
        int sum = Adao.sumCommission();
        
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
        MemberVO memberVo = Adao.readAdmin();
        model.addAttribute("adminOne", memberVo);
        return "../admin/adminInfo";
    }
    
    @RequestMapping(value = "infoEdit.do")
    public String loadInfoEdit(Model model) throws Exception {
        MemberVO memberVo = Adao.readAdmin();
        model.addAttribute("adminOne", memberVo);
        return "../admin/adminInfoEdit";
    }
    
    @RequestMapping(value = "adminPwEdit.do")
    public @ResponseBody String loadInfoEdit(@ModelAttribute("currentPw") String currentPw, @ModelAttribute("newPw") String newPw, @ModelAttribute("newPwCheck") String newPwCheck, @ModelAttribute("resultMsg") String resultMsg, Model model) throws Exception {
        MemberVO adminVo = Adao.readAdmin();
        String plainPassword = currentPw;
        String hasedPassword = "";
        int pwCheck = loginService.checkPass(plainPassword, hasedPassword);
        if (pwCheck == 1) {
            if (newPw.equals(newPwCheck)) {
                String hasedNewPw = bcry.encrypt(newPw);
                adminVo.setPw(hasedNewPw);
                Adao.updatePw(adminVo);
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
        List<MemberVO> newMemberThisWeekList = Adao.newMemberThisWeek();
        List<MemberVO> memberOfPastThirtyDaysList = Adao.memberOfPastThirtyDays();
        List<MemberVO> suspendedList = Adao.suspendedList();
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
        List<MemberVO> newMemberThisWeekList = Adao.newMemberThisWeek();
        List<MemberVO> memberOfPastThirtyDaysList = Adao.memberOfPastThirtyDays();
        List<MemberVO> suspendedList = Adao.suspendedList();
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
        List<OrderVO> orderList = Adao.listOrder();
        List<OrderVO> orderNeedToSetList = Adao.listOrderNeedToSet();
        List<OrderVO> orderFinishedList = Adao.listOrderFinished();
        int orderInProgress = orderList.size() - orderFinishedList.size();
        int sum = Adao.sumCommission();
        List<SetVO> setList = Adao.listSet();
        
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
        List<OrderVO> orderList = Adao.listOrder();
        model.addAttribute("orderList", orderList);
        return "../admin/adminOrderList";
    }
    
    @RequestMapping(value = "chargeList.do")
    public String loadChargeList(Model model) throws Exception {
        List<ChargeVO> chargeList = Adao.listCharge();
        model.addAttribute("chargeList", chargeList);
        return "../admin/adminChargeList";
    }
    
    @RequestMapping(value = "set.do")
    public String set(@ModelAttribute("productId") int productId, @ModelAttribute("sellerId") String sellerId, Model model) throws Exception {
        int balance = Adao.getBalance(sellerId);
        List<OrderVO> orderList = Adao.listOrder();
        model.addAttribute("orderList", orderList);
        return "정산이 완료되었습니다.";
    }
    
    @RequestMapping(value = "product.do")
    public String loadProduct(Model model) throws Exception {
        List<ProductVO> productList = Pdao.list();
        List<ProductVO> productTodayList = Adao.listProductToday();
        List<OrderVO> orderList = Adao.listOrder();
        List<OrderVO> orderFinishedList = Adao.listOrderFinished();
        int sum = Adao.sumTotalTrading();
        
        model.addAttribute("productList", productList);
        model.addAttribute("productTodayList", productTodayList);
        model.addAttribute("orderList", orderList);
        model.addAttribute("orderFinishedList", orderFinishedList);
        model.addAttribute("sum", sum);
        return "../admin/adminProduct";
    }
    
    @RequestMapping(value = "pay.do")
    public String loadPay(Model model) throws Exception {
        List<TransactionHistoryVO> transactionList = Adao.listTransactionHistory();
        List<WithdrawVO> withdrawList = Adao.listWithdraw();
        List<WithdrawVO> withdrawNeedConfirmList = Adao.listWithdrawNeedConfirm();
        List<WithdrawVO> withdrawCompleteList = Adao.listWithdrawComplete();
        List<MemberVO> bankAccountList = new ArrayList<>(); // 사용자 계좌 목록 담을 리스트
        // withdrawList에서 user 값을 하나씩 꺼내서 Adao.listBankAccount()의 파라미터로 사용
        int sum = Adao.sumCommission();
        
        for (WithdrawVO withdraw : withdrawList) {
            String user = withdraw.getMemberId();
            List<MemberVO> userBankAccounts = Adao.listBankAccount(user);
            bankAccountList.addAll(userBankAccounts);
        }
        
        List<MemberVO> memberList = Mdao.list();
        
        model.addAttribute("seedHistoryList", transactionList);
        model.addAttribute("withdrawList", withdrawList);
        model.addAttribute("withdrawNeedConfirmList", withdrawNeedConfirmList);
        model.addAttribute("withdrawCompleteList", withdrawCompleteList);
        model.addAttribute("memberList", memberList);
        model.addAttribute("bankAccountList", bankAccountList); // 사용자 계좌 목록
        model.addAttribute("sum", sum);
        
        return "../admin/adminPay";
    }
    
    @RequestMapping(value = "approve_withdraw.do")
    public String approveWithdraw(@ModelAttribute("id") String id, @ModelAttribute("requested") int requested) throws Exception {
        WithdrawVO vo = Adao.oneWithdrawById(id);
        
        return "출금이 승인되었습니다.";
    }
    
    @RequestMapping(value = "cs.do")
    public String loadCs(Model model) throws Exception {
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
