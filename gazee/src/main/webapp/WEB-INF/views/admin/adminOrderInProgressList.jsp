<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="resources/css/adminOrder.css"/>
<html>
<div class="cardHeader">
    <h2>진행 중인 거래 목록</h2>
</div>
<table>
    <thead>
    <tr>
        <td>거래 NO</td>
        <td>상품 ID</td>
        <td>판매자</td>
        <td>구매자</td>
        <td>결제시간</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${orderInProgressList}" var="bag">
        <tr>
            <td>
                    ${bag.no}
            </td>
            <td>
                    ${bag.productId}
            </td>
            <td>
                    ${bag.sellerId}
            </td>
            <td>
                    ${bag.buyerId}
            </td>
            <td>
                    ${bag.paymentTime}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</html>
