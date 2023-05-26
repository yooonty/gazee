<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="../../resources/css/adminDashboard.css"/>
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

        /*let sellerId = $("seller_id").text();
        let productId = Number($("product_id").text());
        let orderTransacionId = $("order_transaction_id").text();*/
        console.log(typeof sellerId + "seller_id : " + sellerId);
        console.log(typeof productId + "product_id : " + productId);
        console.log(typeof orderTransactionId + "order_transaction_id : " + orderTransactionId);

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
            <i class="fa fa-usd" aria-hidden="true"></i>
        </div>
    </div>
</div>
<div class="details" id="details_container">
    <div class="recentOrders" id="order_container">
        <div class="cardHeader">
            <h2>정산이 필요한 거래 : ${orderNeedToSetList.size()}건</h2>
            <a href="#" class="btn" onclick="orderList()">전체목록 조회</a>
        </div>
        <table>
            <thead>
            <tr>
                <td>거래 NO</td>
                <td>판매자</td>
                <td>구매자</td>
                <td>판매자확정</td>
                <td>구매자확정</td>
                <td>정산하기</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${orderNeedToSetList}" var="bag" varStatus="loop">
                <script>
                    $(function () {
                        const buyerConfirm = ${bag.buyerConfirm};
                        if (buyerConfirm === 1) {
                            $("#buyer_confirm_${loop.index}").html('<span class="status confirm">확정</span>');
                        } else {
                            $("#buyer_confirm_${loop.index}").html('<span class="status cancel">미확정</span>');
                        }
                    });
                </script>
                <tr>
                    <td>
                            ${bag.no}
                    </td>
                    <td>
                        <p style="display:none;">${bag.productId}</p>
                        <p style="display:none;">${bag.transactionId}</p>
                        <p>${bag.sellerId}</p>
                    </td>
                    <td>
                            ${bag.buyerId}
                    </td>
                    <td>
                        <span id="seller_confirm_${loop.index}">
                            <span class="status confirm">확정</span>
                        </span>
                    </td>
                    <td>
                        <span id="buyer_confirm_${loop.index}">
                        ${bag.buyerConfirm}
                        </span>
                    </td>
                    <td>
                        <button id="settle_button" style="font-size: 1.0rem">정산</button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="recentCustomers">
        <div class="cardHeader">
            <h2>신고목록</h2>
        </div>
        <table>
            <thead>
            <tr>
                <th scope="col">제목
                </td>
                <th scope="col">작성자
                </td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${reportList}" var="bag">
                <p style="display: none" id="bag_report_id">${bag.reportId}</p>
                <tr>
                    <td><a href=# id="report_list_title" onclick="loadReportOneDash()">${bag.reportTitle}</a></td>
                    <td>${bag.reportWriter}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
