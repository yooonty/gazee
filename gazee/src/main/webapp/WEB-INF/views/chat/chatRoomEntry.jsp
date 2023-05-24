<%@page import="org.apache.ibatis.reflection.SystemMetaObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
	function dealDirectDateUpdate(roomId) {
		const day = document.querySelector("#input_date").value;
		const timestamp = new Date(day).toISOString().slice(0, 19).replace('T', ' ');
		$.ajax({
			url: 'dealDirectDateUpdate',
			data: {
				roomId: roomId,
				dealDirectDate: timestamp
			},
			success: function() {
				console.log('등록완료')
			},
			error: function(x) {
				console.log(x)
			}
		})
	}
</script>
		<%
			String sessionId = (String)session.getAttribute("id");
			String sellerId = (String)request.getAttribute("sellerId");
			if(sessionId.equals(sellerId)) {
		%>
			<!-- 판매자일 때 -->
			<div class="chatRoomHeader">
				<span style="display: none;" id="chatRoomNumber">${bag.roomId}</span>
				<div style="display: flex; gap: 20px; align-items: center;">
					<div style="display: flex; flex-flow: column;">
						<span id="status">판매중</span>
						<div class="productThumbnail"></div>
					</div>
					<div class="chatRoomTitle_wrap">
						<div style="display: flex; align-items: center;">
							<span id="productTitle">${bag2.productName}</span>
						</div>
						<span id="chatPartner">${buyerNickname}</span>
					</div>
				</div>
				<div style="display: flex; align-items: center; gap: 14px;">
					<div id="dealType">
						<span>${bag.dealType}</span>
					</div>
					<div id="btn_sell">
						<button>판매하기</button>
					</div>
					<div id="productPrice_wrap">
						<div style="font-size: 26px; font-weight: bold; color: #000; margin: 0 5px;">
							${priceDec}원
						</div>
					</div>
					<div id="btn_report_wrap" class="menu-trigger">
						<img src="../resources/img/menu.svg" style="height: 20px;">
					</div>
				</div>
			</div>
			<%
				String dealType = (String)request.getAttribute("dealType");
				if(dealType.equals("직거래")) {
			%>
				<button id="btn_dealDirectDate" onclick="dealDirectDateUpdate(${bag.roomId})">입력</button>
				<div id="dealDirectDate">
					<input id="input_date" type="datetime-local" name="starttime" style="border: none; outline: none;" value="${dealDirectDate}">
				</div>
			<%
				}
			%>
			<div class="chatArea">
				<div class="chatLog" id="chatLog${bag.roomId}">
				</div>
			</div>
			<div class="chatWrite_wrap">
				<div id="inp_chatArea">
					<textarea maxlength="2000" placeholder="메시지를 입력하세요" id="chatMessageText"></textarea>
				</div>
				<div id="btn_chatSubmit">
					<button class="chat_send">전송</button>
				</div>
			</div>
		<%
			} else {
		%>
			<!-- 구매자일 때 -->
			<div class="chatRoomHeader">
				<span style="display: none;" id="chatRoomNumber">${bag.roomId}</span>
				<div style="display: flex; gap: 20px; align-items: center;">
					<div style="display: flex; flex-flow: column;">
						<span id="status">판매중</span>
						<div class="productThumbnail"></div>
					</div>
					<div class="chatRoomTitle_wrap">
						<span id="productTitle">${bag2.productName}</span>
						<span id="chatPartner">${sellerNickname}</span>
					</div>
				</div>
				<div style="display: flex; align-items: center; gap: 14px;">
					<div id="dealType">
						<span>${bag.dealType}</span>
					</div>
					<div id="productPrice_wrap">
						<div style="font-size: 26px; font-weight: bold; color: #000; margin: 0 5px;">
							${priceDec}원
						</div>
					</div>
					<div id="btn_report_wrap" class="menu-trigger">
						<img src="../resources/img/menu.svg" style="height: 20px;">
					</div>
				</div>
			</div>
			<div class="chatArea" id="chatArea">
				<div class="chatLog" id="chatLog${bag.roomId}">

				</div>
			</div>
			<div class="chatWrite_wrap">
				<div id="inp_chatArea">
					<textarea maxlength="2000" placeholder="메시지를 입력하세요" id="chatMessageText"></textarea>
				</div>
				<div id="btn_chatSubmit">
					<button class="chat_send">전송</button>
				</div>
			</div>
		<%
			};
		%>