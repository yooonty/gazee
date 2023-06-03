<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700;900&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
<script type="text/javascript" src="../resources/js/jquery-3.6.4.js"></script>
<script type="text/javascript" src="../resources/js/chatBot.js"></script>
<script type="text/javascript">
	$(function() {
		connect();
	})
</script>

<style>
/* 

#chatOpenBtn {
	background-color: rgba(255, 165, 0, 0.9);
	padding: 10px;
}

#chatOpenBtn span {
	font-size: 50px;
	color: white;
} */

.chatCommon {
	background-color: #eff2f5;
}


#chatMain {
	background-color: #eff2f5;
	width: 500px;
	height: 700px;
}

.msgBox {
	width: 75%;	
	background-color: #ffffff;
	padding: 12px;
	margin: 10px 0;
	border-radius: 20px;
}

.btnBox {
	text-align: right;
}

.suggestBtn {
	
	border-radius: 10px;
	padding: 10px;
	font-weight: bold;
	font-size: 18px;
	margin: 0 6px 6px 0;
	background-color: #693FAA;
	color: white;
}

.chatMenuBtn {
	border: 0;
	padding: 8px;
	border-radius: 10px;
	background-color: rgba(0, 0, 0, 0.05);
}

#chatTop {
	position: absolute;
	top: 20px;
	box-sizing: border-box;
	display: flex;
	padding: 0 20px;
	width: 100%;
	align-items: center;
}

#chatTitle {
	width: 100%;
	color: #693FAA;
}

#chatTitle span {
	vertical-align: middle;
}

#chat {
	height: 100%;
	padding: 80px 20px;
	box-sizing: border-box;
}

#chatList {
	overflow: auto;
	padding: 0;
	margin: 0;
	height: 100%;
	-ms-overflow-style: none; /* 인터넷 익스플로러 */
  	scrollbar-width: none; /* 파이어폭스 */
}

/* ( 크롬, 사파리, 오페라, 엣지 ) 동작 */
#chatList::-webkit-scrollbar {
  display: none;
}


#chatBottom {
	position: absolute;
	right: 20px;
	bottom: 20px;
}



.symbol {
	font-size: 30px; 
}

</style> 
</head>
<body style="background-color: #eff2f5;">
	<div id="chatMain" class="chatCommon">
		<div id="chatTop">
			<div id="chatTitle">
				<span class="material-symbols-outlined symbol">contact_support</span>
				<span style="font-size: 24px;">가지 챗봇</span>
			</div>
			<!-- <button class="chatBtn chatMenuBtn" onclick="chatToggle()">
				<span class="material-symbols-outlined symbol">close</span>
			</button> -->
		</div>
		<div id="chat">
			<ul id="chatList"></ul>
		</div>
		<div id="chatBottom">
			<button id="home" class="chatMenuBtn" onclick="sendMessage(this)" value="홈">
				<span class="material-symbols-outlined symbol">home</span>
			</button>
		</div>
	</div>
	<!-- <button id="chatOpenBtn" class="chatBtn chatCommon" onclick="chatToggle()">
		<span class="material-symbols-outlined symbol">contact_support</span>
	</button> -->
</body>
</html>