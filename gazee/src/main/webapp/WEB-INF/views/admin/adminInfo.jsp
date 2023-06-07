<%@ page contentType="text/html;charset=UTF-8" %>
<link href="resources/css/adminInfo.css" rel="stylesheet" type="text/css">
<script>
    function adminInfoEdit() {
        $("#contents_container").load("infoEdit.do");
    }
</script>
<html>
<body>
<div class="details">
    <h1>관리자 계정 설정</h1>
    <div class="recentCustomers">
        <div class="cardHeader">
            <h2>관리자 계정 정보</h2>
            <a href="#" class="btn" id="admin_edit_btn" onclick="adminInfoEdit()">암호변경</a>
        </div>
        <div class="admin_info">
            <div class="admin_info_container">
            <table id="admin_info_table">
                <thead style="font-size: 20px; font-weight: bold">
                <tr>
                    <td>ID</td>
                    <td>이름</td>
                    <td>Email</td>
                </tr>
                </thead>
                <tbody style="font-size: 18px">
                <tr>
                    <td>${adminOne.id}</td>
                    <td>${adminOne.name}</td>
                    <td>${adminOne.email}</td>
                </tr>
                </tbody>
            </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>