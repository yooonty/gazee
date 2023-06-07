<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="resources/css/adminOrder.css"/>
<!DOCTYPE html>
<table id="boardtable">
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
    <c:forEach items="${searchList}" var="bag" varStatus="loop">
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
                <p id="order_transaction_id" style="display:none;">${bag.transactionId}</p>
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
                <button id="settle_button" style="font-size: 1.0rem">정산</button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

