<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="resources/css/adminMember.css"/>
<script>
    $(".btn").click(function () {

        var str = ""
        var tdArr = new Array();	// 배열 선언
        var confirmPenalty = $(this);

        var tr = confirmPenalty.parent().parent();
        var td = tr.children();

        var no = td.eq(0).text();
        var reporteeId = td.eq(1).text();
        var name = td.eq(2).text();
        var nickname = td.eq(3).text();
        var count = td.eq(4).text();
        var status = 'release'

        $.ajax({
            url: "releaseSuspension.do",
            type: "POST",
            data: {
                reporteeId: reporteeId,
                penaltyType: status
            },
            success: function (response) {
                alert(response);
                loadSuspended();
            },
            error: function (xhr, status, error) {
                alert("제재 실패, 대상 정보를 확인하세요.")
            }
        });
    });
</script>
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
                <td>정지해제</td>
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
                        <td><a href="#" class="btn">해제</a></td>
                    </tr>
            </c:forEach>
            </tbody>
        </table>
</div>
</html>
