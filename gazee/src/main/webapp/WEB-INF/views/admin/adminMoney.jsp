<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="resources/css/adminMoney.css"/>
<script>
    function getBalanceList() {
        $(".details").load("balanceList.do");
    }

    function getChargeList() {
        $(".details").load("chargeList.do");
    }

    function getWithdrawList() {
        $(".details").load("withdrawList.do");
    }
</script>
<html>
<body>
<div class="cardBox">
    <a href=# onclick="getBalanceList()">
        <div class="card">
            <div>
                <div class="numbers">회원별 잔액</div>
                <div class="cardName">&nbsp;</div>
            </div>
            <div class="iconBox">
                <i class="fa fa-users" aria-hidden="true"></i>
            </div>
        </div>
    </a>
    <a href=# onclick="getWithdrawList()">
        <div class="card">
            <div>
                <div class="numbers">출금관리</div>
                <div class="cardName">&nbsp;</div>
            </div>
            <div class="iconBox">
                <i class="fa fa-cubes" aria-hidden="true"></i>
            </div>
        </div>
    </a>
    <a href=# onclick="getChargeList()">
        <div class="card">
            <div>
                <div class="numbers">충전관리</div>
                <div class="cardName">&nbsp;</div>
            </div>
            <div class="iconBox">
                <i class="fa fa-shopping-cart" aria-hidden="true"></i>
            </div>
        </div>
    </a>
    <div class="card">
        <div>
            <div class="numbers"><fmt:formatNumber value="${sum}" type="number" pattern="#,###"/>원</div>
            <div class="cardName">수수료 수입</div>
        </div>
        <div class="iconBox">
            <i class="fa fa-usd" aria-hidden="true"></i>
        </div>
    </div>
</div>
<div class="details">
    <div class="recentOrders">
        <div class="cardHeader">
            <h2>최근 출금 내역</h2>
            <form name="search-form" autocomplete="off">
            <span style="text-align: right">
                <select name="search_type" style="font-size: 1.0rem">
                    <option value="no">No</option>
                    <option value="member_id">신청자</option>
                </select>
                <a href="#" class="btn" onclick="getWithdrawList()">전체목록 조회</a>
             </span>
            </form>
        </div>
        <div id="table_container">
            <table>
                <thead>
                <tr>
                    <td>No</td>
                    <td>신청자</td>
                    <td>출금신청금액</td>
                    <td>실출금액</td>
                    <td>수수료</td>
                    <td>출금계좌</td>
                    <td>잔액확인</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${withdrawList}" var="bag" varStatus="withdrawStatus">
                    <tr>
                        <td>${bag.no}</td>
                        <p id="withdraw_id" style="display:none;">${bag.transactionId}</p>
                        <td>${bag.memberId}</td>
                        <td><fmt:formatNumber value="${bag.totalAmount}" type="number" pattern="#,###"/>원</td>
                        <td><fmt:formatNumber value="${bag.requestedAmount}" type="number" pattern="#,###"/>원</td>
                        <td id="requested_amoumt"><fmt:formatNumber value="${bag.commission}" type="number"
                                                                    pattern="#,###"/>원
                        </td>
                        <td>${bag.bank} - ${bag.account}</td>
                        <td><span class="status confirm">확인</span></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
