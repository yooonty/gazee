<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="resources/css/adminDashboard.css"/>
<script>
    $("#settle_button").click(function () {

        var str = ""
        var pArr = new Array();
        var settle_button = $(this);

        var tr = settle_button.parent().parent();
        var td = tr.children();
        var p = td.children();

        var productId = Number(p.eq(0).text());
        var orderTransactionId = p.eq(1).text();
        var sellerId = p.eq(2).text();

        $.ajax({
            url: "set.do",
            type: "POST",
            data: {
                sellerId: sellerId,
                productId: productId,
                orderTransactionId: orderTransactionId
            },
            success: function (result) {
                alert(result);
                loadDashboard();
            },
            error: function (xhr, status, error) {
                alert("에러 발생: " + error);
            }
        });
    });
</script>
<!DOCTYPE html>
<html>
<body>
<div class="cardBox">
    <a href=# onclick="loadMember()">
        <div class="card">
            <div>
                <div class="numbers">${memberList.size() - 1}명</div>
                <div class="cardName">전체 회원 수</div>
            </div>
            <div class="iconBox">
                <i class="fa fa-users" aria-hidden="true"></i>
            </div>
        </div>
    </a>
    <a href=# onclick="loadProduct()">
        <div class="card">
            <div>
                <div class="numbers">${productList.size()}개</div>
                <div class="cardName">총 상품</div>
            </div>
            <div class="iconBox">
                <i class="fa fa-cubes" aria-hidden="true"></i>
            </div>
        </div>
    </a>
    <a href=# onclick="loadOrder()">
        <div class="card">
            <div>
                <div class="numbers">${orderList.size()}건</div>
                <div class="cardName">총 거래</div>
            </div>
            <div class="iconBox">
                <i class="fa fa-handshake-o" aria-hidden="true"></i>
            </div>
        </div>
    </a>
    <div class="card">
        <div>
            <%--JSTL 사용, 세 자리 수마다 콤마(,) 삽입--%>
            <div class="numbers"><fmt:formatNumber value="${sum}" type="number" pattern="#,###"/>원</div>
            <div class="cardName" id="입">수수료 수입</div>
        </div>
        <div class="iconBox">
            <i class="fa fa-calculator" aria-hidden="true"></i>
        </div>
    </div>
    <a href=# onclick="loadCs()">
        <div class="card">
            <div>
                <div class="numbers">${nonPagedNeedCsReplyList.size()}개</div>
                <div class="cardName">새 문의글</div>
            </div>
            <div class="iconBox">
                <i class="fa fa-comment-o" aria-hidden="true"></i>
            </div>
        </div>
    </a>
    <a href=# onclick="loadReport()">
        <div class="card">
            <div>
                <div class="numbers">${nonPagedNeedReportReplyList.size()}개</div>
                <div class="cardName">새 신고글</div>
            </div>
            <div class="iconBox">
                <i class="fa fa-legal" aria-hidden="true"></i>
            </div>
        </div>
    </a>
    <a href=# onclick="loadOrder()">
        <div class="card">
            <div>
                <div class="numbers">${orderNeedToSetList.size()}건</div>
                <div class="cardName">정산 요청</div>
            </div>
            <div class="iconBox">
                <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
            </div>
        </div>
    </a>
    <div class="card">
        <div>
            <%--JSTL 사용, 세 자리 수마다 콤마(,) 삽입--%>
            <div class="numbers"><fmt:formatNumber value="${total}" type="number" pattern="#,###"/>원</div>
            <div class="cardName" id="입">총 거래금액</div>
        </div>
        <div class="iconBox">
            <i class="fa fa-usd" aria-hidden="true"></i>
        </div>
    </div>
</div>
<div class="details" id="details_container">
    <div class="recentOrders" id="order_container">
        <div class="cardHeader">
            <h2>정산 완료 내역</h2>
        </div>
        <table>
            <thead>
            <tr>
                <td>정산일시</td>
                <td>판매자</td>
                <td>정산금액</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${setList}" var="bag">
                <tr>
                    <td>
                        <fmt:formatDate value="${bag.transactionTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td>${bag.sellerId}</td>
                    <td>
                        <fmt:formatNumber value="${bag.amount}" type="number" pattern="#,###"/>원
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="recentCustomers">
        <div class="cardHeader">
            <h2>전체 거래 목록</h2>
            <a href=# class="btn" onclick="returnToSetList()">정산 대상 조회</a>
        </div>
        <table>
            <thead>
            <tr>
                <td>거래 No</td>
                <td>판매자</td>
                <td>구매자</td>
                <td>결제시간</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${orderList}" var="orderList">
                <tr>
                    <td>
                            ${orderList.no}
                    </td>
                    <td>
                            ${orderList.sellerId}
                    </td>
                    <td>
                            ${orderList.buyerId}
                    </td>
                    <td>
                            ${orderList.paymentTime}
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div style="text-align: right">
            <form action="excelOrder.do" method="get">
                <button class="btn" type="submit">엑셀 다운로드</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>