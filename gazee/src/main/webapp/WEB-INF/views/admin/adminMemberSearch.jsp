<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="resources/css/adminMember.css"/>
<script>
    $("#delete_button").click(function () {
        var delete_button = $(this);

        var tr = delete_button.parent().parent();
        var td = tr.children();
        var no = Number(td.eq(0).text());

        $.ajax({
            url: "adminMemberDelete.do",
            type: "POST",
            data: {
                no: no,
            },
            success: function (result) {
                alert(result);
                loadMember();
            },
            error: function (xhr, status, error) {
                alert("에러 발생: " + error);
            }
        });
    });
</script>
<!DOCTYPE html>
<table id="boardtable">
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
        <td>회원삭제</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${searchList}" var="bag" varStatus="status">
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
            <td>
                <button id="delete_button" class="btn" style="font-size: 1.0rem">삭제</button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>