<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="resources/css/adminOrder.css"/>
<html>
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
</html>
