<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" href="../resources/favicon.ico">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700;900&display=swap" rel="stylesheet">
<link href="../resources/css/recently-viewed.css" rel="stylesheet" type="text/css">
<link href="../resources/css/alarm.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../resources/js/jquery-3.6.4.js"></script>
<script type="text/javascript" src="../resources/js/sockjs-0.3.4.js"></script>
<script type="text/javascript" src="../resources/js/stomp.js"></script>
<script type="text/javascript" src="../resources/js/WebSocket.js"></script>
<script type="text/javascript">
	$(function() { //body 읽어왔을때
		var memberId = "<%= (String)session.getAttribute("id") %>";
		
		if (memberId !== "null") {
			/* 웹소켓 연결 */
			handlePageLoad(memberId);
				
			/* 안 읽은 메세지 체크 */
			unreadMessageCheck(memberId);
		}
		
		/* 최근 본 상품 리스트 */
		$.ajax({
			url : "recentViewList",
			data : {
				memberId : memberId,
				page : 1,
				num : 10
			},
			success : function(res) {
				// console.log(res)
				$('#recentlyViewedList').append(res)
			}
		})
	}) 
</script>
<title>가지가지</title>
</head>
<body>
	<div id="wrap">
		<div id="header">
			<jsp:include page="../home/Header.jsp" flush="true"/>
		</div>
		<div id="content_wrap">
			<div id="content">
				<h2 id="recentlyViewed_title">최근 본 상품</h2>
				<hr>
				<div id="recentlyViewedList">
				</div>
			</div>
		</div>
		<jsp:include page="../home/SideBar.jsp" flush="true"/>
		<jsp:include page="../home/Footer.jsp" flush="true"/>
	</div>
</body>
</html>