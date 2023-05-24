<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="../../resources/css/adminOrder.css"/>
<html>
<div class="cardHeader">
    <h2>전체 거래 목록</h2>
    <a href=# class="btn" onclick="returnToSetList()">정산 대상 조회</a>
</div>
<table>
    <thead>
    <tr>
        <td>거래 NO</td>
        <td>판매자</td>
        <td>구매자</td>
        <td>결제시간</td>
        <td>판매자확정</td>
        <td>구매자확정</td>
        <td>정산여부</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${orderList}" var="orderList">
        <script>
            $(function () {
                const buyerConfirm =
                ${orderList.buyerConfirm}
                const setStatus =
                ${orderList.setStatus}
                if (buyerConfirm === 1) {
                    $("#buyer_confirm").html('<span class="status confirm">확정</span>');
                } else {
                    $("#buyer_confirm").html('<span class="status cancel">미확정</span>');
                }
                if (setStatus === 1) {
                    $("#set_status").html('정산완료');
                } else {
                    $("#set_status").html('미정산');
                }
            });
        </script>
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
            <td>
                    <span id="seller_confirm">
                        <span class="status confirm">확정</span>
                    </span>
            </td>
            <td>
                <span id="buyer_confirm"></span>
            </td>
            <td>
                <span id="set_status"></span>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</html>
