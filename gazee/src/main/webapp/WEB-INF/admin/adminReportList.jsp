<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="../../resources/css/adminReport.css"/>
<!DOCTYPE html>
<html>
<div class="cardHeader">
    <span><h2>전체 신고 목록</h2></span>
    <a href="#" class="btn" onclick="loadReport()">답변이 필요한 신고 목록</a>
</div>
<table>
    <thead>
    <tr>
        <td>카테고리</td>
        <td>ID</td>
        <td>제목</td>
        <td>날짜</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${reportList}" var="bag">
        <tr>
            <p style="display: none" id="bag_report_id">${bag.reportId}</p>
            <td>${bag.reportCategory}</td>
            <td>${bag.reportWriter}</td>
            <td><a href=# id="report_list_title" onclick="loadReportOne()">${bag.reportTitle} </a></td>
            <td>${bag.reportDate}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</html>