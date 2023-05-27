<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	int productId = Integer.parseInt(request.getParameter("productId").trim());
%>
<%
	String memberId = request.getParameter("memberId");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>가지가지</title>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap"
	rel="stylesheet">
<link href="../resources/css/style.css" rel="stylesheet" type="text/css">
<link href="../resources/css/alarm.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../resources/js/sockjs-0.3.4.js"></script>
<script type="text/javascript" src="../resources/js/stomp.js"></script>
<script type="text/javascript" src="../resources/js/WebSocket.js"></script>
<script type="text/javascript" src="../resources/js/jquery-3.6.4.js"></script>
<script type="text/javascript">
var socketSession = '<%= session.getAttribute("subscribedRoomIds")%>';
$(function() {
    var memberId = "<%=memberId%>"; // memberId 가져오기
    var sessionId = "<%= session.getAttribute("id") %>";
    if (socketSession != null) {
		$(document).ready(function() {
			$.ajax({
				url: '../chat/getSubscribedRoomIds',
				type: 'GET',
		        dataType: 'json',
		        success: function(response) {
		            var roomIds = response;
		            roomIds.forEach(function(roomId) {
		            	allSocketConnect(roomId);
		            });
		        },
		        error: function(error) {
		            console.error('Failed to get subscribed roomIds from session');
		            console.log(error);
		        }
			})
		})
	}

    if (memberId === sessionId) {
        $.ajax({
            url: "detail_owner", // memberId와 sessionId가 같을 때의 URL
            data: {
                productId: <%=productId%>,
                memberId: memberId
            },
            success: function(res) {
                $('#product_table').append(res);
            }
        });
    } else {
        $.ajax({
            url: "detail", // memberId와 sessionId가 다를 때의 URL
            data: {
                productId: <%=productId%>,
                memberId: memberId
            },
            success: function(res) {
                $('#product_table').append(res);
            }
        });
    }
});
</script>
<style type="text/css">
#product_table {
	font-size: medium;
	/* font-weight : 500; */
	justify-content: center;
	/* padding: 0 250px 0 250px; */
	align-self: center;
	align-content: center;
}
</style>
</head>
<body>
<div id="newMessagePushAlarm"></div>
	<div id="wrap">
		<div id="header">
			<jsp:include page="../home/Header.jsp" flush="true" />
		</div>
		<div id="content_wrap">
			<div id="content">
				<div id="product_table"></div>
			</div>
		</div>
		<jsp:include page="../home/Footer.jsp" flush="true" />
	</div>
</body>
</html>