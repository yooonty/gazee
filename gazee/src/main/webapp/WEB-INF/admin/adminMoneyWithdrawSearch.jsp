<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="../../resources/css/adminMoney.css"/>
<!DOCTYPE html>
<table id="boardtable">
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
    <c:forEach items="${searchList}" var="bag" varStatus="withdrawStatus">
        <tr>
            <td>${bag.no}</td>
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
