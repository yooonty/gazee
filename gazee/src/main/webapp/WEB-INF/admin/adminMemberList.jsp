<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="../../resources/css/adminMember.css"/>
<!DOCTYPE html>
<div class="recentOrders">
    <div class="cardHeader">
        <span><h2>회원 목록</h2></span>
        <span style="text-align: right">
            <input id="search_index" style="font-size: 18px" placeholder="검색 할 값 입력">
            <a href="#" class="btn">검색</a>
            </span>
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
        <c:forEach items="${memberExceptAdminList}" var="bag" varStatus="status">
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
                <td>${countList[status.index]}회
                <td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
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
</body>
</html>
