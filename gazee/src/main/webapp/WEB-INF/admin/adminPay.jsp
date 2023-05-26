<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="../../resources/css/adminPay.css"/>
<script>
    function chargeList() {
        $(".details").load("chargeList.do");
    }
</script>
<html>
<body>
<div class="cardBox">
    <a href=# onclick="loadMember()">
        <div class="card">
            <div>
                <div class="numbers">회원별 잔액</div>
                <div class="cardName"></div>
            </div>
            <div class="iconBox">
                <i class="fa fa-users" aria-hidden="true"></i>
            </div>
        </div>
    </a>
    <a href=# onclick="loadProduct()">
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
    <a href=# onclick="chargeList()">
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
            <a href="#" class="btn">전체목록 조회</a>
        </div>
        <table>
            <thead>
            <tr>
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
                    <p id="withdraw_id" style="display:none;">${bag.transactionId}</p>
                    <td>${bag.memberId}</td>
                    <td><fmt:formatNumber value="${bag.totalAmount}" type="number" pattern="#,###"/>원</td>
                    <td><fmt:formatNumber value="${bag.requestedAmount}" type="number" pattern="#,###"/>원</td>
                    <td id="requested_amoumt"><fmt:formatNumber value="${bag.commission}" type="number" pattern="#,###"/>원</td>
                    <td>${bag.bank} - ${bag.account}</td>
                    <td><span class="status confirm">확인</span></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
 <%--   <div class="recentCustomers">
        <div class="cardHeader">
            <h2>최근 출금 내역</h2>
        </div>
        <table>
            <thead>
            <tr>
                <td>ID</td>
                <td>신청인</td>
                <td>실제 출금 금액</td>
                <td>수수료</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${withdrawCompleteList}" var="bag">
                <tr>
                    <td>${bag.no}</td>
                    <td>${bag.memberId}</td>
                    <td>
                        <fmt:formatNumber value="${bag.requestedAmount}" type="number" pattern="#,###"/>원
                    </td>
                    <td>
                        <fmt:formatNumber value="${bag.commission}" type="number" pattern="#,###"/>원
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>--%>
</html>
