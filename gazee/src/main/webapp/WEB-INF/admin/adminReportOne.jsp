<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="../../resources/css/adminReport.css"/>
<!DOCTYPE html>
<script>
    function prev() {
        $("#contents_container").load("report.do")
    }

    function replyResgister() {
        var replyContent = $("#reply_content").val();
        console.log(replyContent)
        $.ajax({
            url: "replyRegisterComplete.do",
            type: "POST",
            data: {
                reportId: ${reportOne.reportId},
                replyContent: replyContent
            },
            success: function (result) {
                alert("답변이 등록되었습니다.")
                $('#contents_container').html(result);
            },
            error: function (xhr, status, error) {
                alert("에러 발생: " + error);
            }
        });
    }
</script>
<div class="recentOrders" id="reported_member">
    <div class="cardHeader">
        <span><h2>신고 대상 정보</h2></span>
    </div>
    <table>
        <thead>
        <tr>
            <td>ID</td>
            <td>닉네임</td>
            <td>이름</td>
            <td>TEL</td>
            <td>이메일</td>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${reporteeInfo.id}</td>
            <td>${reporteeInfo.nickname}</td>
            <td>${reporteeInfo.name}</td>
            <td>${reporteeInfo.tel}</td>
            <td>${reporteeInfo.email}</td>
        </tr>
        </tbody>
    </table>
</div>
<div class="recentOrders" id="report_one">
    <div id="report_one_container">
        <div class="cardHeader">
            <span><h2>신고 상세 조회</h2></span>
            <span style="text-align: right">
            <a href=# id="returnPrev" onclick="prev()">목록으로 돌아가기</a>
        </span>
        </div>
        <table>
            <thead>
            <tr>
                <td>No</td>
                <td>카테고리</td>
                <td>제목</td>
                <td>ID</td>
                <td>신고 대상</td>
                <td>날짜</td>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${reportOne.reportId}</td>
                <td>${reportOne.reportCategory}</td>
                <td>${reportOne.reportTitle}</td>
                <td>${reportOne.reportWriter}</td>
                <td>${reportOne.reportee}</td>
                <td>${reportOne.reportDate}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <div id="report_content_container">
        <div>
            <span><h3>신고 내용</h3></span>
        </div>
        <div>
            ${reportOne.reportContent}
        </div>
    </div>
    <div id="report_reply_container">
        <div>
            <span><h3>답변하기</h3></span>
        </div>
        <div>
            <textarea id="reply_content"></textarea>
        </div>
        <div>
            <a href=# id="reply_register_button" onclick="replyResgister()">답변 등록하기</a>
        </div>
    </div>
</div>

