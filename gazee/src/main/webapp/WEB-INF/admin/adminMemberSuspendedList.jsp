<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="../../resources/css/adminMember.css"/>
<!DOCTYPE html>
<html>
<div class="recentOrders">
        <div class="cardHeader">
            <span><h2>정지 회원 목록</h2></span>
        </div>
        <table>
            <thead>
            <tr>
                <td>No</td>
                <td>ID</td>
                <td>이름</td>
                <td>닉네임</td>
                <td>Email</td>
                <td>상태</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${suspendedList}" var="bag">
                    <tr>
                        <td>${bag.no}</td>
                        <td>${bag.id}</td>
                        <td>${bag.name}</td>
                        <td>${bag.nickname}</td>
                        <td>${bag.email}</td>
                        <td>${bag.status}</td>
                    </tr>
            </c:forEach>
            </tbody>
        </table>
</div>
</html>
