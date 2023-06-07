<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700;900&display=swap" rel="stylesheet">
<link href="https://webfontworld.github.io/gmarket/GmarketSans.css" rel="stylesheet">
<link href="../resources/css/style.css" rel="stylesheet" type="text/css">
<link href="../resources/css/alarm.css" rel="stylesheet" type="text/css">
<link href="../resources/css/guide.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../resources/js/jquery-3.6.4.js"></script>
<script type="text/javascript" src="../resources/js/sockjs-0.3.4.js"></script>
<script type="text/javascript" src="../resources/js/stomp.js"></script>
<script type="text/javascript" src="../resources/js/WebSocket.js"></script>
<script type="text/javascript">
	$(function() {
		var memberId = "<%= (String)session.getAttribute("id") %>";
		
		if (memberId !== "null") {
			handlePageLoad(memberId);
			unreadMessageCheck(memberId);
		}
	})
</script>
<title>가지가지</title>
</head>
<body>
<div id="wrap">
	<div id="newMessagePushAlarm"></div>
	<div id="header">
		<jsp:include page="../home/Header.jsp" flush="true"/>
	</div>
	<div id="content_wrap">
		<div id="content">
			<div class="guide_wrap">
				<div class="guide_title">
					<div id="gazeeIcon"><img src="../resources/img/icon_eggplant.svg" style="width: 30px;"></div>
					<span style="font-size: 30px;">거래약속도 결제도</span>
					<span style="font-size: 30px; font-weight: bold;">채팅창에서 한 번에!</span>
				</div>
				<hr style="margin: 40px 0; width: 68%;">
				<div class="guide_content">
					<div class="guide_tag">충전/환급</div>
					<span class="guide_content_title">충전 <span style="font-weight: bold;">수수료 X</span></span>
					<span class="guide_content_title">거래 <span style="font-weight: bold;">수수료 X</span></span>
					<span class="guide_content_detail" style="margin-top: 5px;">[마이페이지]에서 충전해 수수료 없이 현금처럼 사용이 가능해요!</span>
					<span class="guide_content_detail">[단, 가지머니 환급 시 수수료 5% 부과됩니다.]</span>
					<div class="guide_content_img"></div>
					
					<div class="guide_tag">사기</div>
					<span class="guide_content_title">사기 걱정 없이</span>
					<span class="guide_content_title" style="font-weight: bold;">안전하게</span>
					<span class="guide_content_detail" style="margin-top: 5px;">구매확정까지 결제금액을 가지가지에서 보관해요!</span>
					<div class="guide_content_img"></div>
					
					<div class="guide_tag">개인정보</div>
					<span class="guide_content_title">개인정보도</span>
					<span class="guide_content_title" style="font-weight: bold;">안전하게</span>
					<span class="guide_content_detail" style="margin-top: 5px;">가지머니를 통해 계좌번호 노출 걱정없이 거래해요!</span>
					<div class="guide_content_img"></div>
						
					<div class="guide_tag">노쇼/네고</div>	
					<span class="guide_content_title" style="font-weight: bold;">직거래 스트레스,</span>
					<span class="guide_content_title">이젠 안녕!</span>
					<span class="guide_content_detail" style="margin-top: 5px;">채팅방에서의 선결제 시스템으로 직거래 노쇼, 네고를 방지할 수 있어요!</span>
					<div class="guide_content_img"></div>
				</div>
					<hr style="margin: 40px 0">
				<div class="guide_content">
					<div class="guide_tag">거래</div>
					<span class="guide_content_title" style="margin-bottom: 20px;">거래는 <span style="font-weight: bold;">어떻게 하나요?</span></span>
					
					<div style="display: flex;">
						<div style="width: 5px; background: #693faa; margin-right: 10px;"></div>
						<span class="guide_step_no">Step 1.</span>
						<span class="guide_step">거래방법을 선택 후 판매자에게 채팅을 걸어 메세지를 보내요</span>
					</div>
					<div class="guide_step_img"></div>
					
					<div style="display: flex;">
						<div style="width: 5px; background: #693faa; margin-right: 10px;"></div>
						<span class="guide_step_no">Step 2.</span>
						<span class="guide_step">협의가 이루어지면 판매자가 [판매하기] 버튼을 눌러 구매자에게 결제를 요청해요</span>
					</div>
					<div class="guide_step_img"></div>
					
					<div style="display: flex; margin-bottom: 10px;">
						<div style="width: 5px; background: #693faa; margin-right: 10px;"></div>
						<span class="guide_step_no">Step 3.</span>
						<span class="guide_step">결제가 완료되면 판매자는 구매 내역은 [마이페이지]에서 확인해요</span>
					</div>
					<span class="guide_content_detail" style="padding-left: 24px;">*택배거래 시 판매자는 [마이페이지]에서 운송장번호를 입력해주세요!</span>
					<div class="guide_step_img"></div>
					
					<div style="display: flex; margin-bottom: 10px;">
						<div style="width: 5px; background: #693faa; margin-right: 10px;"></div>
						<span class="guide_step_no">Step 4.</span>
						<span class="guide_step">거래가 완료되면 [구매확정]을 눌러주세요!</span>
					</div>
					<span class="guide_content_detail" style="padding-left: 24px;">결제금액은 구매확정이 이루어진 후 지급됩니다.</span>
					<span class="guide_content_detail" style="padding-left: 24px;">직거래시 거래날짜로부터 24시간 후, 택배거래시 결제일로부터 일주일 후 자동으로 구매확정이 이루어집니다.</span>
					<div class="guide_step_img"></div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../home/SideBar.jsp" flush="true"/>
	<jsp:include page="../home/Footer.jsp" flush="true"/>
</div>
</body>
</html>