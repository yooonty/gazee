<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="../../resources/css/adminReport.css"/>
<script>
    function loadCsOne() {
        const bagCsId = $("#bag_cs_id").text();
        $.ajax({
            url: "cs_one.do",
            type: "POST",
            data: {
                csId: bagCsId
            },
            success: function (result) {
                $('#cs_list').html(result);
            },
            error: function (xhr, status, error) {
                alert("에러 발생: " + error);
            }
        });
    }
</script>
<!DOCTYPE html>
<div class="details">
    <div class="recentOrders" id="reported_member">
        <div class="cardHeader">
            <span><h2>회원별 제재 현황</h2></span>
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
                    <td>${bag.status}</td>
                    <td>1회</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="recentOrders" id="cs_list">
        <div class="cardHeader">
            <span><h2>신고 목록</h2></span>
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
            <c:forEach items="${csList}" var="bag">
                <tr>
                    <p style="display: none" id="bag_cs_id">${bag.csId}</p>
                    <td>${bag.csCategory}</td>
                    <td>${bag.csWriter}</td>
                    <td><a href=# id="cs_list_title" onclick="loadCsOne()">${bag.csTitle} </a></td>
                    <td>${bag.csDate}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
