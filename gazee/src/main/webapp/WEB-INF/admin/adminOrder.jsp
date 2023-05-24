<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="../../resources/css/adminOrder.css"/>
<script>
    function set() {
        var sellerId = $("seller_id").text();
        const productId = $("product_id").text();
        $.ajax({
            url: "set.do",
            type: "POST",
            data: {
                sellerId: sellerId,
                productId: productId
            },
            success: function (result) {
                alert(result);
            },
            error: function (xhr, status, error) {
                alert("에러 발생: " + error);
            }
        });
    }
</script>
<html>
<body>
<div class="cardBox">
    <a href=# onclick="loadProduct()">
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
    <a href=# onclick="loadMember()">
        <div class="card">
            <div>
                <div class="numbers">${orderInProgress}건</div>
                <div class="cardName">진행 중인 거래</div>
            </div>
            <div class="iconBox">
                <i class="fa fa-exchange" aria-hidden="true"></i>
            </div>
        </div>
    </a>
    <a href=# onclick="loadProduct()">
        <div class="card">
            <div>
                <div class="numbers">${orderFinishedList.size()}건</div>
                <div class="cardName">완료 된 거래</div>
            </div>
            <div class="iconBox">
                <i class="fa fa-check-square-o" aria-hidden="true"></i>
            </div>
        </div>
    </a>
    <div class="card">
        <div>
            <%--JSTL 사용, 세 자리 수마다 콤마(,) 삽입--%>
            <div class="numbers"><fmt:formatNumber value="${sum}" type="number" pattern="#,###"/>원</div>
            <div class="cardName">수수료 수입</div>
        </div>
        <div class="iconBox">
            <i class="fa fa-usd" aria-hidden="true"></i>
        </div>
    </div>
</div>
<div class="details">
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
                        <p id="product_id" style="display:none;">${bag.productId}</p>
                        <p id="seller_id">${bag.sellerId}</p>
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
                        <button onclick="set()">정산</button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="recentCustomers">
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
                    <td>${bag.transactionTime}</td>
                    <td>${bag.sellerId}</td>
                    <td>
                        <fmt:formatNumber value="${bag.amount}" type="number" pattern="#,###"/>원
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
