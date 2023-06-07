<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>가지가지</title>
<link rel="shortcut icon" href="../resources/favicon.ico">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" rel="stylesheet"/>
<link href="../resources/css/style.css" rel="stylesheet" type="text/css">
<link href="../resources/css/alarm.css" rel="stylesheet" type="text/css">
<link href="../resources/css/gazee-main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../resources/js/jquery-3.6.4.js"></script>
<script type="text/javascript" src="../resources/js/WebSocket.js"></script>
<script type="text/javascript" src="../resources/js/sockjs-0.3.4.js"></script>
<script type="text/javascript" src="../resources/js/stomp.js"></script>
<script type="text/javascript">
	$(function() {
		var memberId = "<%= (String)session.getAttribute("id") %>";
		
		if (memberId !== "null") {
			/* 웹소켓 연결 */
			handlePageLoad(memberId);
			
			/* 안 읽은 메세지 체크 */
			unreadMessageCheck(memberId);
			
			/* 사용자 맞춤 추천 상품 */
			$.ajax({
				url : "../product/userBest",
				data : {
					memberId : memberId
				},
				success : function(res) {
					$('#recommend_item').append(res)
				}
			})
			
			/* Weka 추천 상품 */
			$.ajax({
				url : "../product/wekaBest",
				data : {
					memberId : memberId
				},
				success : function(res) {
					$('#recommend_item').append(res)
				}
			})
		}
		
		/* 인기 상품 추천 (조회수) */
		$.ajax({
			url : "../product/best",
			success : function(res) {
				$('#content').append(res)
			}
		})
		
		$("#register").click(function() {
			if (memberId !== "null") {
				location.href = "../product/checkTemporaryProduct?memberId=" + memberId;
			} else{
				alert("판매하기는 로그인 후 이용해주세요!");
			}
		})
	})
</script>

</head>
<body>
	<div id="wrap">
		<div id="newMessagePushAlarm"></div>
		<div id="header">
			<jsp:include page="../home/Header.jsp" flush="true">
				<jsp:param name="mode" value="1"/>
			</jsp:include>
		</div>
		<div id="content_wrap">
			<div id="content">
				<div class="slidebox">
				    <input type="radio" name="slide" id="slide01" checked>
				    <input type="radio" name="slide" id="slide02">
				    <ul class="slidelist">
				        <li class="slideitem">
				            <div>
				                <!-- <label for="slide02" class="left"></label>  -->
				                <label for="slide02" class="right"></label>
				                <span class="material-symbols-outlined right">
								arrow_forward_ios
								</span>
				                <img src="../resources/img/banner.jpg" id="register">
				            </div>
				        </li>
				        <li class="slideitem">
				            <div>
				                <label for="slide01" class="left"></label>
				                <span class="material-symbols-outlined left">
								arrow_back_ios
								</span>
				                <a href="gazeeGuide.jsp"><img src="../resources/img/banner2.jpg"></a>
				            </div>
				        </li>
				    </ul>
				</div>
				<div id="recommend_item"></div>
			</div>
		</div>
		<jsp:include page="../home/SideBar.jsp" flush="true"/>
		<jsp:include page="../home/Footer.jsp" flush="true" />
	</div>
</body>
</html>