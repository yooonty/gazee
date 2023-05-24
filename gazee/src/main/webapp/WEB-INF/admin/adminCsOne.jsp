<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="../../resources/css/admincs.css"/>
<!DOCTYPE html>
<script>
    function prev() {
        $("#contents_container").load("cs.do")
    }

    function replyResgister() {
        var replyContent = $("#reply_content").val();
        console.log(replyContent)
        $.ajax({
            url: "replyRegisterComplete.do",
            type: "POST",
            data: {
                csId: ${csOne.csId},
                replyContent : replyContent
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
    <div class="cardHeader">
        <span><h2>신고 상세 조회</h2></span>
        <span style="text-align: right">
            <a href=# id="returnPrev" onclick="prev()">목록으로 돌아가기</a>
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
            <span><h3>신고 내용</h3></span>
        </div>
        <div>
            ${csOne.csContent}
        </div>
        <div>
            <span><h3>답변하기</h3></span>
        </div>
        <div>
            <textarea id="reply_content"></textarea>
        </div>
        <div>
            <a href=# id="reply_register_button" onclick="replyResgister()">답변 등록하기</a>
        </div>

