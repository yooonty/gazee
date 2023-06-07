<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="resources/css/adminMoney.css"/>
<!DOCTYPE html>
<table id="boardtable">
    <thead>
    <tr>
        <td>ID</td>
        <td>잔액</td>
    </tr>
    </thead>
    <tbody>
        <tr>
            <td>${memberId}</td>
            <td><fmt:formatNumber value="${balance}" type="number" pattern="#,###"/>원</td>
    </tbody>
</table>
