<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	int productId = Integer.parseInt(request.getParameter("productId").trim());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>가지가지</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<link href="../resources/css/style.css" rel="stylesheet" type="text/css">
<link href="../resources/css/alarm.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../resources/js/sockjs-0.3.4.js"></script>
<script type="text/javascript" src="../resources/js/stomp.js"></script>
<script type="text/javascript" src="../resources/js/WebSocket.js"></script>
<script type="text/javascript" src="../resources/js/jquery-3.6.4.js"></script>
<script type="text/javascript">
	$(function() {
		var memberId; // memberId 가져오기
	    var sessionId = "<%= session.getAttribute("id") %>";
		
		$.ajax({
			url : "viewsCount",
			data : {
				productId : '<%=productId%>'
			},
			success : function(res) {
				//console.log(res)
			}
		})
		$.ajax({
			url : "checkSeller",
			async : false,
			data : {
				productId : '<%=productId%>'
			},
			success : function(res) {
				memberId = res;
			}
		})
		
		if (memberId !== "null") {
			handlePageLoad(sessionId);
			unreadMessageCheck(sessionId);
			
			$.ajax({
				url : "../recentlyViewed/recentView",
				data : {
					productId : '<%=productId%>',
					memberId : sessionId
				},
				success : function(res) {
					console.log(res)
				}
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
		        	console.log("1");
		            $('#product_table').append(res);
		        }
		    });
		} else if (sessionId !== "null") {
		    $.ajax({
		        url: "detail", // memberId와 sessionId가 다를 때의 URL
		        data: {
		            productId: <%=productId%>,
		            memberId: memberId
		        },
		        success: function(res) {
		        	console.log(sessionId + "2");
		            $('#product_table').append(res);
		        }
		    });
		} else {
		    $.ajax({
		        url: "detail_nomember", // sessionId가 null일 때의 URL
		        data: {
		            productId: <%=productId%>,
		            memberId: memberId
		        },
		        success: function(res) {
		        	console.log("3");
		            $('#product_table').append(res);
		        }
		    });
		}
	    
	});
</script>
<style type="text/css">
	#product_table {
		font-size: medium;
		justify-content: center;
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
		<jsp:include page="../home/SideBar.jsp" flush="true"/>
		<jsp:include page="../home/Footer.jsp" flush="true" />
		<jsp:include page="kakaomap.jsp" flush="true"/>
	</div>
</body>
</html>