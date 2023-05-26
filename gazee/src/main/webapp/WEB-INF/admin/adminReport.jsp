<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="../../resources/css/adminReport.css"/>
<script>
    // 테이블의 Row 클릭시 값 가져오기
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
        var stauts = td.eq(5).text();
        var penaltyType = td.eq(6).find("select[name='penalty']").val();

        $.ajax({
            url: "penaltyComplete.do",
            type: "POST",
            data: {
                reporteeId: reporteeId,
                penaltyType: penaltyType
            },
            success: function (response) {
                alert(response);
                loadReport();
            },
            error: function (xhr, status, error) {
                alert("제재 실패, 대상 정보를 확인하세요.")
            }
        });
    });
</script>
<!DOCTYPE html>
<html>
<div class="details" id="details_container">
    <div class="recentOrders" id="reported_member">
        <div class="cardHeader">
            <span><h2>제재 대상 회원 목록</h2></span>
        </div>
        <table>
            <thead>
            <tr>
                <td>No</td>
                <td>ID</td>
                <td>이름</td>
                <td>닉네임</td>
                <td>누적 제재 횟수</td>
                <td>현재 상태</td>
                <td>제재 형식</td>
                <td>제재 실행</td>
            </tr>
            </thead>
            <tbody id="needPenaltyTable">
            <c:forEach items="${needPenaltyList}" var="bag" varStatus="status">
                <tr>
                    <td>${bag.no}</td>
                    <td>${bag.id}</td>
                    <td>${bag.name}</td>
                    <td>${bag.nickname}</td>
                    <td>${countList[status.index]}회</td>
                    <td>${bag.status}</td>
                    <td>
                        <select name="penalty" id="days-${status.index}">
                            <option value="seven">7일 정지</option>
                            <option value="thirty">30일 정지</option>
                            <option value="permanent">영구 정지</option>
                            <option value="release">정지 해제</option>
                        </select>
                    </td>
                    <td>
                        <a href="#" class="btn">확인</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="recentOrders" id="report_list">
        <div class="cardHeader">
            <span><h2>답변이 필요한 신고 목록</h2></span>
        </div>
        <table>
            <thead>
            <tr>
                <td>카테고리</td>
                <td>ID</td>
                <td>제목</td>
                <td>날짜</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${nonPagedNeedReplyList}" var="bag">
                <tr>
                    <p style="display: none" id="bag_report_id">${bag.reportId}</p>
                    <td>${bag.reportCategory}</td>
                    <td>${bag.reportWriter}</td>
                    <td><a href=# id="report_list_title" onclick="loadReportOne()">${bag.reportTitle} </a></td>
                    <td>${bag.reportDate}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</html>