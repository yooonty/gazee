<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <link href="resources/css/adminLogin.css" rel="stylesheet" type="text/css">
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <meta charset="UTF-8">
    <title>가지가지 관리자 로그인 페이지</title>
</head>
<body>
<script>
    $(function () {
        let msg = "${msg}";
        if (msg != "") {
            $("#error_here").text(msg);
        }
    })
</script>
<div class="gazee_admin_login">
    <div class="admin_login_wrap">
        <div class="admin_logo">
            <img src="resources/img/gazee_admin_logo.png" alt="admin_logo" style="width: 380px; height: 80px">
        </div>
        <form action="admin_main" method="post">
            <div class="admin_login_content">
                <div class="login_id">
                    <label for="tf_id">아이디</label>
                    <div class="box_id">
                        <input type="text" id="tf_id" name="user_id" value="" class="tf" placeholder="아이디 입력">
                    </div>
                </div>
                <div class="login_pwd">
                    <label for="tf_pwd">비밀번호</label>
                    <div class="box_pwd">
                        <input type="password" id="tf_pwd" name="user_pwd" value="" class="tf" placeholder="비밀번호 입력"
                               autocomplete="off">
                    </div>
                </div>
                <div class="login_relative">
                    <div id="error_here">
                        <br>
                    </div>
                    <div id="login_btn_container">
                        <button type="submit" class="btn" id="login_btn" name="login_btn">관리자 로그인</button>
                    </div>
                    <div id="pw_reset_container">
                        <a href="findPwForm.do" id="pw_reset">비밀번호 재설정</a>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
<div id="footer" style="text-align: center; width: 100vw; background-color: #363636;
	color: #fff; height: 8%; position: absolute !important; bottom: 0 !important; display: flex;
  align-items: center; justify-content: center;">
    <div>
        <p style="margin: 0; font-size: 12px;">Copyrightⓒ 2023. gazee. All rights reserved.</p>
    </div>
</div>
</html>