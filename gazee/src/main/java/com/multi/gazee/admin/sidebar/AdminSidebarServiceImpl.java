package com.multi.gazee.admin.sidebar;

import com.multi.gazee.admin.paging.AdminPageVO;
import com.multi.gazee.charge.ChargeDAO;
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
import com.multi.gazee.reportCount.ReportCountDAO;
import com.multi.gazee.reportCount.ReportCountVO;
import com.multi.gazee.set.SetDAO;
import com.multi.gazee.set.SetVO;
import com.multi.gazee.transactionHistory.TransactionHistoryDAO;
import com.multi.gazee.transactionHistory.TransactionHistoryVO;
import com.multi.gazee.withdraw.WithdrawDAO;
import com.multi.gazee.withdraw.WithdrawVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminSidebarServiceImpl implements AdminSidebarService {
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
    ReportCountDAO RCdao;
    @Autowired
    OrderDAO Odao;
    @Autowired
    WithdrawDAO Wdao;
    @Autowired
    SetDAO Sdao;
    @Autowired
    TransactionHistoryDAO Tdao;
    
    public String loadDashboard(Model model) {
        List<MemberVO> memberList = Mdao.list();
        List<ProductVO> productList = Pdao.list();
        List<OrderVO> orderList = Odao.listOrder();
        List<ReportVO> nonPagedReportList = Rdao.nonPagedList();
        List<OrderVO> orderNeedToSetList = Odao.listOrderNeedToSet();
        List<CustomerServiceVO> nonPagedNeedCsReplyList = CSdao.nonPagedNeedReply();
        List<ReportVO> nonPagedNeedReportReplyList = Rdao.nonPagedNeedReply();
        List<SetVO> setList = Sdao.listSet();
        int sumCommission = Wdao.sumCommission();
        int sumTotalTrading = Odao.sumTotalTrading();
        
        model.addAttribute("memberList", memberList);
        model.addAttribute("productList", productList);
        model.addAttribute("orderList", orderList);
        model.addAttribute("reportList", nonPagedReportList);
        model.addAttribute("orderNeedToSetList", orderNeedToSetList);
        model.addAttribute("nonPagedNeedCsReplyList", nonPagedNeedCsReplyList);
        model.addAttribute("nonPagedNeedReportReplyList", nonPagedNeedReportReplyList);
        model.addAttribute("setList", setList);
        model.addAttribute("sum", sumCommission);
        model.addAttribute("total", sumTotalTrading);
        return "admin/adminDashboard";
    }
    
    public String loadMember(AdminPageVO pageVo, int pageNumber, Model model) throws Exception {
        List<MemberVO> memberList = Mdao.list();
        List<MemberVO> newMemberThisWeekList = Mdao.newMemberThisWeek();
        List<MemberVO> memberOfPastThirtyDaysList = Mdao.memberOfPastThirtyDays();
        List<MemberVO> suspendedList = Mdao.suspendedList();
        List<ReportVO> nonPagedReportList = Rdao.nonPagedList();
        List<MemberVO> memberExceptAdminList = Mdao.listExceptAdmin();
    
        /* 페이징 */
        pageVo.setPage(pageNumber);
        pageVo.setStartEnd(pageVo.getPage());
        List<MemberVO> pagedMemberList = Mdao.pagedList(pageVo);
        int currentPage = pageVo.getPage();
        int count = Mdao.count();
        int pages = (int) (count / 10.0 + 1);
        
        List<Integer> balanceList = new ArrayList<>(); // 회원 별 잔액을 담을 리스트
        List<Integer> sellingProductQtyList = new ArrayList<>(); // 회원 별 판매갯수를 담을 리스트
        for (MemberVO vo : pagedMemberList) {
            String id = vo.getId();
            Integer balance = Tdao.select(id);
            int qty = Pdao.productOneById(id).size();
            balanceList.add(balance);
            sellingProductQtyList.add(qty);
        }
    
        model.addAttribute("memberListToShow", pagedMemberList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pages", pages);
        model.addAttribute("count", count);
        
        model.addAttribute("memberList", memberList);
        model.addAttribute("newMemberThisWeekList", newMemberThisWeekList);
        model.addAttribute("memberOfPastThirtyDaysList", memberOfPastThirtyDaysList);
        model.addAttribute("suspendedList", suspendedList);
        model.addAttribute("reportList", nonPagedReportList);
        model.addAttribute("memberExceptAdminList", memberExceptAdminList);
        model.addAttribute("balanceList", balanceList);
        model.addAttribute("sellingProductQtyList", sellingProductQtyList);
        
        return "admin/adminMember";
    }
    
    
    public String loadProduct(AdminPageVO pageVo, int pageNumber, Model model) {
        List<ProductVO> productList = Pdao.list();
        List<ProductVO> productTodayList = Pdao.listProductToday();
        List<OrderVO> orderList = Odao.listOrder();
        List<OrderVO> recentOrderList = Odao.recentOrder();
        List<OrderVO> orderFinishedList = Odao.listOrderFinished();
        int sum = Odao.sumTotalTrading();
        
        /* 페이징 */
        pageVo.setPage(pageNumber);
        pageVo.setStartEnd(pageVo.getPage());
        List<ProductVO> pagedProductList = Pdao.pagedList(pageVo);
        int currentPage = pageVo.getPage();
        int count = Pdao.count();
        int pages = (int) (count / 10.0 + 1);
    
        model.addAttribute("pagedProductList", pagedProductList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pages", pages);
        model.addAttribute("count", count);
        
        model.addAttribute("productList", productList);
        model.addAttribute("recentOrderList", recentOrderList);
        model.addAttribute("productTodayList", productTodayList);
        model.addAttribute("orderList", orderList);
        model.addAttribute("orderFinishedList", orderFinishedList);
        model.addAttribute("sum", sum);
        return "admin/adminProduct";
    }
    
    public String loadOrder(Model model) {
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
        
        return "admin/adminOrder";
    }
    
    public String loadMoney(Model model) {
        List<TransactionHistoryVO> transactionList = Tdao.listTransactionHistory();
        List<WithdrawVO> withdrawList = Wdao.listWithdraw();
        List<MemberVO> bankAccountList = new ArrayList<>(); // 사용자 계좌 목록 담을 리스트
        List<MemberVO> memberList = Mdao.listExceptAdmin();
        int sum = Wdao.sumCommission();
        List<Integer> balanceList = new ArrayList<>();
        for (MemberVO vo : memberList) {
            String id = vo.getId();
            Integer balance = Tdao.select(id);
            balanceList.add(balance);
        }
        
        for (WithdrawVO withdraw : withdrawList) {
            String user = withdraw.getMemberId();
            List<MemberVO> userBankAccounts = Mdao.listBankAccount(user);
            bankAccountList.addAll(userBankAccounts);
        }
        
        model.addAttribute("seedHistoryList", transactionList);
        model.addAttribute("withdrawList", withdrawList);
        model.addAttribute("memberList", memberList);
        model.addAttribute("bankAccountList", bankAccountList); // 사용자 계좌 목록
        model.addAttribute("balanceList", balanceList);
        model.addAttribute("sum", sum);
        
        return "admin/adminMoney";
    }
    
    public String loadCs(AdminPageVO pageVo, int pageNumber, Model model) {
        List<CustomerServiceVO> nonPagedCsList = CSdao.nonPagedList();
        List<CustomerServiceVO> nonPagedNeedReplyList = CSdao.nonPagedNeedReply();
    
        /* 페이징 */
        pageVo.setPage(pageNumber);
        pageVo.setStartEnd(pageVo.getPage());
        List<CustomerServiceVO> pagedNeedReplyCsList = CSdao.pagedNeedReply(pageVo);
        int currentPage = pageVo.getPage();
        int count = CSdao.countPagedNeedReply();
        int pages = (int) (count / 10.0 + 1);
    
        model.addAttribute("pagedNeedReplyCsList", pagedNeedReplyCsList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pages", pages);
        model.addAttribute("count", count);
        model.addAttribute("csList", nonPagedCsList);
        model.addAttribute("nonPagedNeedReplyList", nonPagedNeedReplyList);
        return "admin/adminCs";
    }
    
    public String loadReport(AdminPageVO pageVo, int pageNumber, Model model) {
        List<ReportVO> nonPagedReportList = Rdao.nonPagedList();
        List<ReportVO> nonPagedNeedReplyList = Rdao.nonPagedNeedReply();
        List<MemberVO> needPenaltyList = Mdao.needPenaltyList();
        List<Integer> countList = new ArrayList<>();
    
        /* 페이징 */
        pageVo.setPage(pageNumber);
        pageVo.setStartEnd(pageVo.getPage());
        List<ReportVO> pagedNeedReplyReportList = Rdao.pagedNeedReply(pageVo);
        int currentPage = pageVo.getPage();
        int count = Rdao.countPagedNeedReply();
        int pages = (int) (count / 10.0 + 1);
        
        
        for (MemberVO member : needPenaltyList) {
            String memberId = member.getId();
            ReportCountVO RCvo = RCdao.reportCount(memberId);
            int reportCount = 0;
            
            if (RCvo != null) {
                reportCount = RCvo.getCount();
            }
            countList.add(reportCount);
        }
    
        model.addAttribute("pagedNeedReplyReportList", pagedNeedReplyReportList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pages", pages);
        model.addAttribute("count", count);
        model.addAttribute("reportList", nonPagedReportList);
        model.addAttribute("nonPagedNeedReplyList", nonPagedNeedReplyList);
        model.addAttribute("needPenaltyList", needPenaltyList);
        model.addAttribute("countList", countList);
        
        return "admin/adminReport";
    }
    
    public String loadInfo(Model model) throws Exception {
        MemberVO memberVo = Mdao.readAdmin();
        model.addAttribute("adminOne", memberVo);
        return "admin/adminInfo";
    }
    
    public String loadInfoEdit(Model model) throws Exception {
        MemberVO memberVo = Mdao.readAdmin();
        model.addAttribute("adminOne", memberVo);
        return "admin/adminInfoEdit";
    }
}

