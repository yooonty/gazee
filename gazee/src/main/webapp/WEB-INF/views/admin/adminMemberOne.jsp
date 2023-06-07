<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="resources/css/adminMember.css"/>
<!DOCTYPE html>
<div class="details">
    <div class="recentOrders">
        <div class="cardHeader">
            <span><h2>회원 상세 정보 조회</h2></span>
        </div>
        <table>
            <thead>
            <tr>
                <td>No</td>
                <td>ID</td>
                <td>이름</td>
                <td>닉네임</td>
                <td>Email</td>
                <td>가지씨앗 잔액</td>
                <td>판매 중 물품</td>
                <td>가입일시</td>
                <td>회원상태</td>
                <td>제재횟수</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${memberList}" var="bag">
                <tr>
                    <td>${bag.no}</td>
                    <td>${bag.id}</td>
                    <td>${bag.name}</td>
                    <td>${bag.nickname}</td>
                    <td>${bag.email}</td>
                    <td>60,000</td>
                    <td>3개</td>
                    <td>${bag.joinDate}</td>
                    <td>${bag.status}</td>
                    <td>1회</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>