<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="resources/css/adminMoney.css"/>
<script>
    function getSearchList() {
        $.ajax({
            type: 'GET',
            url: "/searchWithdraw.do",
            data: $("form[name=search-form]").serialize(),
            success: function (result) {
                $("#table_container").html(result);
            }
        })
    }
</script>
<html>
<div class="recentOrders">
    <div class="cardHeader">
        <span><h2>충전 목록</h2></span>
    </div>
    <table>
        <thead>
        <tr>
            <td>충전시각</td>
            <td>ID</td>
            <td>충전금액</td>
            <td>충전방법</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${chargeList}" var="bag">
            <tr>
                <td><fmt:formatDate value="${bag.transactionTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                <td>${bag.memberId}</td>
                <td>
                    <fmt:formatNumber value="${bag.amount}" type="number" pattern="#,###"/>원
                </td>
                <td>${bag.payMethod}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div style="text-align: right">
        <form action="excelCharge.do" method="get">
            <button class="btn" type="submit">엑셀 다운로드</button>
        </form>
    </div>
</div>
</html>
