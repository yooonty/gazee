<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="resources/css/adminMoney.css"/>
<script>
    function getSearchList() {
        $.ajax({
            type: 'GET',
            url: "searchBalance.do",
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
        <span><h2>회원별 잔액</h2></span>
        <form name="search-form" autocomplete="off">
            <span style="text-align: right">
                <select name="search_type" style="font-size: 1.0rem">
                    <option value="member_id">회원ID</option>
                </select>
                <input name="search_index" style="font-size: 18px" placeholder="검색 할 값 입력">
                <input class="btn" type="button" onclick="getSearchList()" value="검색"></input>
             </span>
        </form>
    </div>
    <div id="table_container">
        <table>
            <thead>
            <tr>
                <td>ID</td>
                <td>잔액</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${memberList}" var="bag" varStatus="status">
                <tr>
                    <td>${bag.id}</td>
                    <td><fmt:formatNumber value="${balanceList[status.index]}" type="number" pattern="#,###"/>원</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</html>
