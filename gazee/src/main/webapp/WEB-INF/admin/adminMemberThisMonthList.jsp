<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="../../resources/css/adminMember.css"/>
<!DOCTYPE html>
<html>
<div class="recentOrders">
        <div class="cardHeader">
            <span><h2>최근 30일 신규 회원 목록</h2></span>
        </div>
        <table>
            <thead>
            <tr>
                <td>No</td>
                <td>ID</td>
                <td>이름</td>
                <td>닉네임</td>
                <td>Email</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${newMemberThisMonthList}" var="bag" varStatus="status">
                    <tr>
                        <td>${bag.no}</td>
                        <td>${bag.id}</td>
                        <td>${bag.name}</td>
                        <td>${bag.nickname}</td>
                        <td>${bag.email}</td>
                    </tr>
            </c:forEach>
            </tbody>
        </table>
    <%-- <div class="recentCustomers">
         <div class="cardHeader">
             <h2>신고 목록</h2>
         </div>
         <table>
             <thead>
             <tr>
                 <td>제목</td>
                 <td>작성자</td>
             </tr>
             </thead>
             <tbody>
             <c:forEach items="${reportList}" var="bag">
                 <tr>
                     <td>${bag.reportTitle}</td>
                     <td>${bag.reportWriter}</td>
                 </tr>
             </c:forEach>
             </tbody>
         </table>
     </div>--%>
</div>
</html>
