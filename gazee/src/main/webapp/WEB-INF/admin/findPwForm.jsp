<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link href="resources/css/findPw.css" rel="stylesheet" type="text/css">
    <script src="https://code.jquery.com/jquery-latest.js"></script>
    <meta charset="UTF-8">
    <title>가지가지 관리자 비밀번호 찾기</title>
</head>
<body>
<script>
    $(function () {
        $("#findBtn").click(function () {
            const emailToCheck = $("#tf_email").val();

            $.ajax({
                url: "findPw.do",
                type: "POST",
                data: {
                    email: emailToCheck
                },
                success: function (result) {
                    alert(result);
                    location.href = "/admin"
                },
                error: function (xhr, status, error) {
                    alert("에러 발생: " + error);
                }
            });
        });
    });
</script>
<div class="find_pw">
    <div class="find_pw_wrap">
        <div class="admin_logo">
            <a href="/admin">
            <img src="resources/img/gazee_admin_logo.png" alt="admin_logo" style="width: 380px; height: 80px">
            </a>
        </div>
        <div class="find_pw_content">
            <div class="find_pw_title">
                비밀번호찾기
            </div>
            <div class="info_input">
                <label for="tf_email">
                    <br>
                </label>
                <div class="box_email">
                    <input type="text" id="tf_email" name="email" value="" class="tf"
                           placeholder="관리자 이메일 입력">
                </div>
            </div>
            <div class="confirm_relative">
                    <button type="button" id="findBtn" class="btn">임시비밀번호 발송</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>