<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="resources/css/adminMember.css"/>
<!DOCTYPE html>
<html>
<script type="text/javascript">
    $(function () {
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

    $(function () {
            var thisPage = "${currentPage}"
            $(".pagination button").each(function () {
                var idx = $(this).index();
                var thistitle = $(this).attr("title");
                if (thistitle == thisPage) {
                    $(".pagination").find("button").eq(idx).addClass("active");
                }
            });
        }
    )

    function getSearchList() {
        $.ajax({
            type: 'GET',
            url: "searchMember.do",
            data: $("form[name=search-form]").serialize(),
            success: function (result) {
                $("#table_container").html(result);
            }
        })
    }

    $('.pages').click(function () {
        //$('#result').empty()
        $.ajax({
            url: "member.do",
            data: {
                page: $(this).text(),
                /*mode: 2*/
            },
            success: function (result) { //결과가 담겨진 table부분코드
                $('#result').html(result)
            },
            error: function () {
                location.reload();
            }
        }) //ajax
    })

    function loadPage(pageNumber) {
        $.ajax({
            type: 'GET',
            url: "member.do",
            data: {
                pageNumber: pageNumber
            },
            success: function (result) {
                $("#contents_container").html(result);
            }
        })
    }
</script>
<body>
<div class="cardBox">
    <a href=# onclick="loadMember()">
        <div class="card">
            <div>
                <div class="numbers">${memberExceptAdminList.size()}명</div>
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
    <div class="card">
        <div>
            <div class="numbers" id="newMemberPastThirtyDays"></div>
            <div class="cardName">30일 간 회원 수 변화</div>
        </div>
        <div class="iconBox">
            <i class="fa fa-line-chart" aria-hidden="true"></i>
        </div>
    </div>
</div>
<div class="details" id="details_container">
    <div class="recentOrders">
        <div class="cardHeader">
            <span><h2>회원 목록</h2></span>
            <form name="search-form" autocomplete="off">
            <span style="text-align: right">
                <select name="search_type" style="font-size: 1.0rem">
                    <option value="id">ID</option>
                    <option value="name">이름</option>
                    <option value="nickname">닉네임</option>
                </select>
                <input name="search_index" style="font-size: 18px" placeholder="검색 할 값 입력">
                <input class="btn" type="button" onclick="getSearchList()" value="검색"></input>
             </span>
            </form>
        </div>
        <div id="table_container">
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
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${memberListToShow}" var="bag" varStatus="status">
                    <tr>
                        <td>${bag.no}</td>
                        <td>${bag.id}</td>
                        <td>${bag.name}</td>
                        <td>${bag.nickname}</td>
                        <td>${bag.email}</td>
                        <td><fmt:formatNumber value="${balanceList[status.index]}" type="number" pattern="#,###"/>원</td>
                        <td>${sellingProductQtyList[status.index]}개</td>
                        <td><fmt:formatDate value="${bag.joinDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${bag.status}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div style="text-align: right">
                <form action="excelMember.do" method="get">
                    <button class="btn" type="submit">엑셀 다운로드</button>
                </form>
            </div>
            <div class="pagination" style="text-align: center">
                <c:forEach begin="1" end="${pages}" varStatus="page">
                    <button class="paging" title="${page.index}"
                            onclick="loadPage(${page.index})">${page.index}</button>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>