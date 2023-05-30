<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>관리자 메인 페이지</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-latest.js"></script>
    <link rel="stylesheet" type="text/css" href="../../resources/css/adminSidebar.css"/>
</head>
<body>
<script>
    $(function () {
        $("#contents_container").load("dashboard.do");
    });

    $(function() {
        checkLogin(60000);

        function checkLogin(delay) {
            setTimeout(function() {
                var hasCookie, hasSession;

                var loginCookie = document.cookie.indexOf("AUTH");
                if ((loginCookie > 0) && (typeof loginCookie === "number")) {
                    hasCookie = true;
                }

                if (document.getElementsByClassName("fa fa-sign-out") !== null) {
                    hasSession = true;
                }

                if (hasCookie && hasSession) {
                    console.log("isLogin: true");
                }
                else {
                    if (hasCookie || hasSession) {
                        // 둘 중 하나만 존재하는 경우 로그아웃 페이지로 이동시킴
                        alert("로그인 후 30분이 지나 자동으로 로그아웃 됩니다.")
                        location.href="logout.do"
                    }
                    else {
                        return false;
                    }
                }
                checkLogin(60000); // 재귀함수를 사용하여 반복수행
            }, delay);
        }

    })

    function loadDashboard() {
        $("#contents_container").load("dashboard.do");
    }

    function loadSetting() {
        $("#contents_container").load("info.do");
    }

    function loadMember() {
        $("#contents_container").load("member.do");
    }

    function loadMemberThisWeek() {
        $("#details_container").load("memberThisWeekList.do");
    }

    function loadMemberThisMonth() {
        $("#details_container").load("memberThisMonthList.do");
    }

    function loadSuspended() {
        $("#details_container").load("memberSuspendedList.do");
    }

    function loadProduct() {
        $("#contents_container").load("product.do");
    }

    function loadOrder() {
        $("#contents_container").load("order.do");
    }

    function loadMoney() {
        $("#contents_container").load("money.do");
    }

    function loadCs() {
        $("#contents_container").load("cs.do");
    }

    function loadReport() {
        $("#contents_container").load("report.do");
    }

    function orderList() {
        $("#order_container").load("orderList.do");
    }

    function returnToSetList() {
        $("#contents_container").load("order.do");
    }

    function loadReportOneDash() {
        const bagReportId = $("#bag_report_id").text();
        $.ajax({
            url: "report_one.do",
            type: "POST",
            data: {
                reportId: bagReportId
            },
            success: function (result) {
                $('#contents_container').html(result);
            },
            error: function (xhr, status, error) {
                alert("에러 발생: " + error);
            }
        })
    };

    function loadReportOne() {
        const bagReportId = $("#bag_report_id").text();
        $.ajax({
            url: "report_one.do",
            type: "POST",
            data: {
                reportId: bagReportId
            },
            success: function (result) {
                $('#details_container').html(result);
            },
            error: function (xhr, status, error) {
                alert("에러 발생: " + error);
            }
        })
    };
</script>
<div class="container">
    <div class="navigation">
        <div>
            <ul>
                <li>
                    <a href="main.do">
                    <span class="title" id="logo">
                        <img src="/resources/img/gazee_admin_logo_white.png" alt="logo"
                             style="margin-top: 10px; width: 100%;">
                        </span>
                    </a>
                </li>
                <li>
                    <a href="gazee.do">
                        <span class="icon"><i class="fa fa-home" aria-hidden="true"></i></span>
                        <span class="title">사이트로 바로가기</span>
                    </a>
                </li>
                <li>
                    <hr>
                </li>
                <li>
                    <a href=# id="nav_dashboard" onclick="loadDashboard()">
                        <span class="icon"><i class="fa fa-dashboard" aria-hidden="true"></i></span>
                        <span class="title">대시보드</span>
                    </a>
                </li>
                <li>
                    <a href=# id="nav_member" onclick="loadMember()">
                        <span class="icon"><i class="fa fa-users" aria-hidden="true"></i></span>
                        <span class="title">회원 관리</span>
                    </a>
                </li>
                <li>
                    <a href=# id="nav_product" onclick="loadProduct()">
                        <span class="icon"><i class="fa fa-shopping-cart" aria-hidden="true"></i></span>
                        <span class="title">상품 관리</span>
                    </a>
                </li>
                <li>
                    <a href=# id="nav_order" onclick="loadOrder()">
                        <span class="icon"><i class="fa fa-handshake-o" aria-hidden="true"></i></span>
                        <span class="title">거래 관리</span>
                    </a>
                </li>
                <li>
                    <a href=# id="nav_money" onclick="loadMoney()">
                        <span class="icon"><i class="fa fa-credit-card" aria-hidden="true"></i></span>
                        <span class="title">가지머니 관리</span>
                    </a>
                </li>
                <li>
                    <a href=# id="nav_cs" onclick="loadCs()">
                        <span class="icon"><i class="fa fa-phone" aria-hidden="true"></i></span>
                        <span class="title">문의 관리</span>
                    </a>
                </li>
                <li>
                    <a href=# id="nav_report" onclick="loadReport()">
                        <span class="icon"><i class="fa fa-user-secret" aria-hidden="true"></i></span>
                        <span class="title">신고 관리</span>
                    </a>
                </li>
                <li>
                    <hr>
                </li>
                <li>
                    <a href=# id="nav_setting" onclick="loadSetting()">
                        <span class="icon"><i class="fa fa-user-circle" aria-hidden="true"></i></span>
                        <span class="title">관리자 설정</span>
                    </a>
                </li>
                <li>
                    <a href="logout.do">
                        <span class="icon"><i class="fa fa-sign-out" aria-hidden="true"></i></span>
                        <span class="title">로그아웃</span>
                    </a>
                </li>
            </ul>
        </div>
        <div id="footer_container">
            <div id="footer">
                <p style="margin: 0; font-size: 14px;">Copyrightⓒ 2023. gazee. All rights reserved.</p>
            </div>
        </div>
    </div>
    <div class="main">
        <div class="top-bar">
            <div class="toggle" onclick="toggleMenu()"></div>
            <%--  <div class="search">
                  <label>
                      <input type="text" placeholder="검색어를 입력하세요">
                      <i class="fa fa-search" aria-hidden="true"></i>
                  </label>
              </div>--%>
            <span class="adminInfo" style="font-size: 1.2rem; width: 20%; text-align: right; display: flex;
  align-items: center; justify-content: center; margin-right: 3%;">
                <span>
                    <div style="font-size: 1.0rem"><%=request.getSession().getAttribute("username")%>님, 안녕하세요!</div>
                    <span style="font-size: 0.8rem">로그인 시각 : <%=request.getSession().getAttribute("login_time")%></span>
                </span>
                <span id="top_bar_icons" style="margin-left: 15px">
                    <span class="icon" style="font-size: 1.7rem; color: #333333">
                        <i class="fa fa-user-circle-o" aria-hidden="true" onclick="loadSetting()"></i>
                        <a href="logout.do" style="margin-left: 10px; font-size: 1.8rem; color: #333333">
                            <i class="fa fa-sign-out" aria-hidden="true"></i>
                        </a>
                    </span>
                </span>
            </span>
        </div>
        <div class="contents" id="contents_container">
        </div>
    </div>
</div>
</body>
<script>
    function toggleMenu() {
        let toggle = document.querySelector(".toggle");
        let navigation = document.querySelector(".navigation");
        let main = document.querySelector(".main");
        toggle.classList.toggle("active");
        navigation.classList.toggle("active");
        main.classList.toggle("active");
        if ($('#logo').css('visibility') == 'hidden' && $('#footer_container').css('visibility') == 'hidden') {
            $('#logo').css('visibility', 'visible');
            $('#footer_container').css('visibility', 'visible');
        } else {
            $('#logo').css('visibility', 'hidden');
            $('#footer_container').css('visibility', 'hidden');
        }
    }
</script>
</html>