<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="resources/css/adminReport.css"/>
<!DOCTYPE html>
<script>
    function prev() {
        $.ajax({
            type: 'GET',
            url: "cs.do",
            data: {
                pageNumber : 1
            },
            success: function (result) {
                $("#contents_container").html(result);
            }
        })
    }

    function replyResgister() {
        var replyContent = $("#reply_content").val();
        $.ajax({
            url: "csReplyRegisterComplete.do",
            type: "POST",
            data: {
                csId: ${csOne.csId},
                replyContent: replyContent
            },
            success: function (result) {
                alert("답변이 등록되었습니다.")
                loadCs()
            },
            error: function (xhr, status, error) {
                alert("에러 발생: " + error);
            }
        });
    }
</script>
<div class="recentOrders" id="report_one">
    <div id="report_one_container">
        <div class="cardHeader">
            <span><h2>문의 상세 조회</h2></span>
            <span style="text-align: right">
            <a href=# id="returnPrev" class="btn" onclick="prev()">목록으로 돌아가기</a>
        </span>
        </div>
        <table>
            <thead>
            <tr>
                <td>ID</td>
                <td>카테고리</td>
                <td>제목</td>
                <td>ID</td>
                <td>날짜</td>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${csOne.csId}</td>
                <td>${csOne.csCategory}</td>
                <td>${csOne.csTitle}</td>
                <td>${csOne.csWriter}</td>
                <td>${csOne.csDate}</td>
            </tr>
            </tbody>
        </table>
        <div>
            <span><h3>문의 내용</h3></span>
        </div>
        <div>
            ${csOne.csContent}
        </div>
        <div id="report_reply_container">
            <div>
                <span><h3>답변하기</h3></span>
            </div>
            <div>
                <textarea id="reply_content" style="width: 100%; height: 100px; font-size: 1.2rem"></textarea>
            </div>
            <div style="text-align: center">
                <a href=# id="reply_register_button" class="btn" style="font-size: 1.0rem" onclick="replyResgister()">답변 등록하기</a>
            </div>
        </div>
    </div>
</div>