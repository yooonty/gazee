<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="../../resources/css/adminMember.css"/>
<!DOCTYPE html>
<html>
<script>
    $(function () {
        $("#details_container").load("memberList.do");

        var memberThirty = ${memberOfPastThirtyDaysList.size()};
        var element = document.getElementById("newMemberPastThirtyDays");

        if (memberThirty > 0) {
            element.textContent = "+${memberOfPastThirtyDaysList.size()}명"
        } else if (memberThirty === 0) {
            element.textContent = "변동 없음"
        } else {
            element.textContent = "-${memberOfPastThirtyDaysList.size()}명"
        }
    });
</script>
<body>
<div class="cardBox">
    <a href=# onclick="loadMember()">
        <div class="card">
            <div>
                <div class="numbers">${memberList.size() - 1}명</div>
                <div class="cardName">전체 회원 수</div>
            </div>
            <div class="iconBox">
                <i class="fa fa-users" aria-hidden="true"></i>
            </div>
        </div>
    </a>
    <a href=# onclick="loadMemberThisWeek()">
        <div class="card">
            <div>
                <div class="numbers">${newMemberThisWeekList.size() - 1}명</div>
                <div class="cardName">이번 주 신규회원<br></div>
            </div>
            <div class="iconBox">
                <i class="fa fa-child" aria-hidden="true"></i>
            </div>
        </div>
    </a>
    <a href=# onclick="loadSuspended()">
        <div class="card">
            <div>
                <div class="numbers">${suspendedList.size()}명</div>
                <div class="cardName">이용 정지 회원</div>
            </div>
            <div class="iconBox">
                <i class="fa fa-square" aria-hidden="true"></i>
            </div>
        </div>
    </a>
    <a href=# onclick="loadMemberThisMonth()">
        <div class="card">
            <div>
                <div class="numbers" id="newMemberPastThirtyDays"></div>
                <div class="cardName">30일 간 회원 수 변화</div>
            </div>
            <div class="iconBox">
                <i class="fa fa-line-chart" aria-hidden="true"></i>
            </div>
        </div>
    </a>
</div>
<div class="details" id="details_container">
</div>
</body>
</html>
