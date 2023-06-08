<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
    	Integer productId = Integer.parseInt(request.getParameter("productId"));
	%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="../resources/favicon.ico">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" rel="stylesheet">
<link href="../resources/css/product-register.css" rel="stylesheet"  type="text/css">
<link href="../resources/css/alarm.css" rel="stylesheet" type="text/css">
<link href="../resources/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../resources/js/sockjs-0.3.4.js"></script>
<script type="text/javascript" src="../resources/js/stomp.js"></script>
<script type="text/javascript" src="../resources/js/WebSocket.js"></script>
<script type="text/javascript" src="../resources/js/jquery-3.6.4.js"></script>
<style type="text/css">
	.each-row{
		text-align: left;
	}
</style>
<script>
	$(function() {
	    var sessionId = "<%= session.getAttribute("id") %>";
	    var productId = <%=productId%>;
	
	    console.log(sessionId);
	    console.log(productId);
		
		if (sessionId !== "null") {
			handlePageLoad(sessionId);
			unreadMessageCheck(sessionId);
		}
	
	    $.ajax({
	        url: "productUpdateSel",
	        data: {
	            sessionId: sessionId,
	            productId: productId,
	        },
	        success: function(response) {
	            $('#register_table').append(response)
	        }
	    });
	});
</script>
<meta charset="UTF-8">
<title>가지가지</title>
</head>
<body>
	<div id="wrap">
		<div id="newMessagePushAlarm"></div>
		<div id="header">
			<jsp:include page="../home/Header.jsp" flush="true" />
		</div>
		<div id="content_wrap">
			<div id="content">
				<div id="title">내 물건 팔기</div>
				<div id="register_table"></div>
			</div>
		</div>
		<jsp:include page="../home/SideBar.jsp" flush="true"/>
		<jsp:include page="../home/Footer.jsp" flush="true" />
		<jsp:include page="kakaomap.jsp" flush="true"/>
	</div>
</body>
</html>