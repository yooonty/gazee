<%@ page contentType="text/html;charset=UTF-8" %>
<link href="resources/css/adminInfo.css" rel="stylesheet" type="text/css">
<script>
    function adminPwEdit() {
        var currentPw = $("#admin_current_pw").val();
        var newPw = $("#admin_new_pw").val();
        var newPwCheck = $("#admin_new_pw_check").val();

        if (newPw === newPwCheck) {
            $.ajax({
                url: "adminPwEdit.do",
                type: "POST",
                data: {
                    currentPw: currentPw,
                    newPw: newPw,
                    newPwCheck: newPwCheck
                },
                success: function(result) {
                    if (result === "SUCCESS") {
                        alert("비밀번호를 변경하였습니다.");
                        $("#contents_container").load("info.do");
                    } else if (result === "INCORRECT") {
                        alert("현재 비밀번호가 일치하지 않습니다.");
                    } else {
                        alert("비밀번호 변경에 실패하였습니다.");
                    }
                },
                error: function(xhr, status, error) {
                    alert("에러 발생: " + error);
                }
            });
        } else {
            alert("비밀번호 확인이 일치하지 않습니다.");
        }
    }
</script>
<html>
<body>
<div class="details">
    <h1>관리자 계정 설정</h1>
    <div class="recentCustomers">
        <div class="cardHeader">
            <h2>관리자 암호 변경</h2>
        </div>
        <div class="admin_info">
            <div class="admin_info_container">
            <table id="admin_info_table">
                <thead style="font-size: 20px; font-weight: bold">
                <tr>
                    <td>기존 비밀번호</td>
                    <td>새 비밀번호</td>
                    <td>새 비밀번호 확인</td>
                    <td>변경하기</td>
                </tr>
                </thead>
                <tbody style="font-size: 18px">
                <tr>
                    <td><input type="text" class="text_input" id="admin_current_pw" placeholder="기존 비밀번호"></td>
                    <td><input type="text" class="text_input" id="admin_new_pw" placeholder="변경 할 비밀번호"></td>
                    <td><input type="text" class="text_input" id="admin_new_pw_check" placeholder="변경 할 비밀번호 확인"></td>
                    <td><button id="admin_pw_edit_btn" class="btn" onclick="adminPwEdit()">변경</button></td>
                </tr>
                </tbody>
            </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>