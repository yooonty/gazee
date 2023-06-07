<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="resources/css/adminMoney.css"/>
<!DOCTYPE html>
<div class="cardHeader">
    <h2>검색 결과</h2>
    <form name="search-form" autocomplete="off">
            <span style="text-align: right">
                <select name="search_type" style="font-size: 1.0rem">
                    <option value="no">No</option>
                    <option value="member_id">신청자</option>
                </select>
                <input name="search_index" style="font-size: 18px" placeholder="검색 할 값 입력">
                <input class="btn" type="button" onclick="getSearchList()" value="검색"></input>
                    <a href="#" class="btn" onclick="getWithdrawList()">전체 목록 조회</a>
             </span>
    </form>
</div>
<div id="table_container">
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
                <td id="requested_amoumt"><fmt:formatNumber value="${bag.commission}" type="number" pattern="#,###"/>원
                </td>
                <td>${bag.bank} - ${bag.account}</td>
                <td><span class="status confirm">확인</span></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div style="text-align: right">
        <form action="excelWithdraw.do" method="get">
            <button class="btn" type="submit">엑셀 다운로드</button>
        </form>
    </div>
</div>
</html>