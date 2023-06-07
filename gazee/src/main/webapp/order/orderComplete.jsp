<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" href="../resources/favicon.ico">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700;900&display=swap" rel="stylesheet">
<link href="https://webfontworld.github.io/gmarket/GmarketSans.css" rel="stylesheet">
<link href="../resources/css/style.css" rel="stylesheet" type="text/css">
<link href="../resources/css/alarm.css" rel="stylesheet" type="text/css">
<link href="../resources/css/orderComplete.css" rel="stylesheet" type="text/css">
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="../resources/js/jquery-3.6.4.js"></script>
<script type="text/javascript" src="../resources/js/sockjs-0.3.4.js"></script>
<script type="text/javascript" src="../resources/js/stomp.js"></script>
<script type="text/javascript" src="../resources/js/WebSocket.js"></script>
<script type="text/javascript">

	var urlParams = new URLSearchParams(location.search);
	var dealType = urlParams.get('dealType');
	var productId = urlParams.get('productId');
	
	console.log(dealType);
	console.log(productId);
	
	$(function() {
		var sessionId = "<%= session.getAttribute("id") %>";
	    
		handlePageLoad(sessionId);
		unreadMessageCheck(sessionId);
		
		$.ajax({
			url: '../order/orderCheck',
			data: {
				productId: productId
			},
			dataType: 'json',
			success: function(orderVO) {
				roomId = orderVO.roomId
				transactionId = orderVO.transactionId
				sellerId = orderVO.sellerId
				buyerId = orderVO.buyerId
				paymentTime = orderVO.paymentTime
				address = orderVO.address
				
				let date = new Date(paymentTime);
				let formattedTime = date.toLocaleString("ko-KR", {
					year: "numeric",
					month: "2-digit",
					day: "2-digit",
					hour: "2-digit",
					minute: "2-digit"
				})
				
				let link_order_transactionId = document.getElementById('link_order_transactionId');
				let order_sellerId = document.getElementById('order_sellerId');
				let order_buyerId = document.getElementById('order_buyerId');
				let order_transactionId = document.getElementById('order_transactionId');
				let order_dealType = document.getElementById('order_dealType');
				let order_paymentTime = document.getElementById('order_paymentTime');
				
				link_order_transactionId.append(transactionId);
				order_sellerId.append(sellerId);
				order_buyerId.append(buyerId);
				order_transactionId.append(transactionId);
				order_dealType.append(dealType);
				order_paymentTime.append(formattedTime);
				
				document.getElementById("redirect_chat").addEventListener("click", function() {
					chatRoom(roomId);
				});
				
				if (dealType == "택배거래") {
					let order_address = document.getElementById('order_address');
					order_address.append(address);
				}
				
				$.ajax({
					url: '../product/productOne',
					data: {
						productId : productId
					},
					dataType: 'json',
					success: function(productVO) {
						category = productVO.category
						productName = productVO.productName
						productContent = productVO.productContent
						price = productVO.price
						let formattedPrice = formatNumber(price);
						
						let order_category = document.getElementById('order_category');
						let order_productName = document.getElementById('order_productName');
						let productPriceElements = document.querySelectorAll('.order_productPrice');
						
						order_category.append(category)
						order_productName.append(productName)
						productPriceElements.forEach((td, index) => {
							td.textContent = formattedPrice + "원"; // 소수점 둘째 자리까지 표시하도록 설정
						});
					},
					error: function(error) {
						console.log(error)
					}
				})
				
				$.ajax({
					url: '../productImage/productImageThumbnail',
					data: {
						productId : productId
					},
					success: function(result) {
						console.log(result)
						const thumbnailAddr = result;
						console.log(thumbnailAddr)
						let order_productThumbnail = document.getElementById("order_productThumbnail");
						order_productThumbnail.style.backgroundImage = "url('"+result+"')";
					}
				})
			},
			error: function(error) {
				console.log(error)
			}
		})
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
			<div style="font-size: 26px; font-weight: bold; text-align: left; color:#363636; margin-bottom: 20px; display: flex; flex-flow: column; align-content: center; gap: 5px;">
				<div id="creditIcon"><img src="../resources/img/icon_credit.svg" style="width: 30px;"></div>
				<div>결제가 완료되었습니다</div>
				<div style="font-size: 14px; font-weight: normal; margin-top: 10px;">
					거래번호 <span style="color: #693FAA; font-weight: bold; text-decoration: underline;" id="link_order_transactionId"></span> 결제가 완료되었습니다.
				</div>
			</div>
			<hr style="margin: 40px 0">
			<div id="order_productInfo">
				<div id="order_productThumbnail"></div>
				<div class="order_productText">
					<div id="order_category"></div>
					<div id="order_productName"></div>
					<div class="order_productPrice" id="order_productPrice"></div>
				</div>
			</div>
			<div id="order_paymentHistory">
				<span style="font-size: 18px; font-weight: bold; margin-bottom: 15px;">최종 결제금액</span>
				<table id="paymentHistory_table">
					<tbody>
						<tr>
							<th>판매자</th>
							<td id="order_sellerId"></td>
							<th>구매자</th>
							<td id="order_buyerId"></td>
						</tr>
						<tr>
							<th>거래번호</th>
							<td colspan="3" id="order_transactionId"></td>
						</tr>
						<tr>
							<th>결제수단</th>
							<td>가지페이</td>
							<th>거래방법</th>
							<td id="order_dealType"></td>
						</tr>
						<tr>
							<th>상품 금액</th>
							<td class="order_productPrice"></td>
							<th>결제일시</th>
							<td id="order_paymentTime"></td>
						</tr>
						<%
							String dealType = (String)request.getParameter("dealType");
							if (dealType.equals("택배거래")) {
						%>
							<tr>
								<th>배송지 주소</th>
								<td colspan="3" id="order_address"></td>
							</tr>
						<%
							}
						%>
					</tbody>
				</table>
			</div>
			<div style="margin: 60px 0 40px 0;">
				<button id="redirect_chat">채팅으로 돌아가기</button>
				<button id="redirect_myPage" onclick="myPage()">구매내역 확인하기</button>
			</div>
		</div>
	</div>
	<jsp:include page="../home/Footer.jsp" flush="true"/>
</div>
</body>
</html>