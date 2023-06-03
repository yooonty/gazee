<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String id = (String)session.getAttribute("id");
%>
<script type="text/javascript">
$(function() {
	var sessionId = "<%= session.getAttribute("id") %>";
	/* 최근 본 상품 목록 */
	$('#btn_recentItem').click(function() {
		location.href = "../recentlyViewed/recentlyViewedList.jsp"
	})
	
	/* 채팅 페이지 */
	$(".btn_myChatlist").click(function() {
		location.href = "../chat/gazeeChat.jsp";
	})
	
	/* 판매하기 버튼 */
	$(".btn_sell").click(function() {
		location.href = "../product/checkTemporaryProduct?memberId=" + sessionId;
	})
	
	if(sessionId != "null"){ //사용자가 로그인했을때
		/* 최근 본 상품 숫자 */
		$.ajax({
			url : "../recentlyViewed/recentViewCount",
			data : {
				memberId : sessionId
			},
			success : function(res) {
				$('.viewCount').append(res)
			}
		})
	}
	
})
function chatBotOpen() {
	window.open("../home/chatBot.jsp","_blank", "width=500, height=665");
}
</script>
<link rel="stylesheet" href="../resources/css/style.css" type="text/css">
		<%
			if(id!=null){
		%>
		<div id="recentView">
			<div class="recentViewItem" style="background: #693FAA;">
				<div class="btn_sell recentViewTxt" style="color: white;">
					<img src="../resources/img/icon_money.svg" width="18px;">
					판매하기
				</div>
			</div>
			<div class="recentViewItem">
				<div class="recentViewTxt">최근본상품</div>
				<div class="recentViewCount">
					<div class="viewCount">
					</div>
				</div>
				<div class="recentItem">
					<button id="btn_recentItem">목록보기</button>
				</div>
			</div>
			<div id="newMessageBadge"></div>
			<div class="recentViewItem">
				<div class="btn_myChatlist recentViewTxt" id="btn_myChatlist">
					<img src="../resources/img/icon_chat2.svg" width="18px;">
					채팅방
				</div>
			</div>
			<div class="recentViewItem">
				<div class="btn_chatBot recentViewTxt" onclick='chatBotOpen()'>
					<img src="../resources/img/icon_bot.svg" width="20px;">
					챗봇상담
				</div>
			</div>
		</div>
		<%
			} 
		%>