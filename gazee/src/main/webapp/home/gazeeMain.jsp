<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = (String)session.getAttribute("id");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700;900&display=swap" rel="stylesheet">
<link href="../resources/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../resources/js/jquery-3.6.4.js"></script>
<script type="text/javascript">
	$(function() { //body 읽어왔을때
		
		/* 사용자 아이디 받아오기 */
		var id = '<%=id%>'
		// var id2 = '${id}'

		if(id!="null"){ //사용자가 로그인했을때
			/* 사용자 맞춤 추천 상품 */
			$.ajax({
				url : "../product/userBest",
				data : {
					memberId : id
				},
				success : function(res) {
					$('#recommend_item').append(res)
				}
			})
			
			/* Weka 추천 상품 */
			$.ajax({
				url : "../product/wekaBest",
				data : {
					memberId : id
				},
				success : function(res) {
					$('#recommend_item').append(res)
				}
			})
			
			/* 최근 본 상품 숫자 */
			$.ajax({
				url : "../recentlyViewed/recentViewCount",
				data : {
					memberId : id
				},
				success : function(res) {
					$('.viewCount').append(res)
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
		
		/* 검색창을 이용한 검색 */
		$('#searchButton').click(function() {
			var search = $('#searchBar').val()
			location.href = "../product/searchList?page=1&search=" + search
		})
		
		/* 카테고리(햄버거바)를 이용한 검색 */
		$('.categoryMenu').click(function() {
			var category = $(this).text()
			location.href = "../product/categoryList?page=1&category=" + category
		})
		
		/* 배너 클릭 -> 판매하기 */
		$('#banner').click(function() {
			alert("판매하기")
		})
	}) 
</script>
<style type="text/css">
#btn_recentItem {
	background-color: white;
	color: #693FAA;
	border: 1px solid #693FAA;
	border-radius: 3px;
	font-size: 12px;
	font-weight: 600;
}

</style>
<title>가지가지</title>
</head>
<body>
	<div id="wrap">
		<div id="header">
			<jsp:include page="../home/Header.jsp" flush="true"/>
		</div>
		<div id="content_wrap">
			<div id="content">
				<div id="banner">
					<img src="../resources/img/banner.jpg" width="1000px">
				</div>
				<div id="recommend_item">
				</div>
			</div>
		</div>
		<%
			if(id!=null){
		%>
		<div id="recentView">
			<div class="recentViewItem">
				<div class="recentViewTxt">채팅방</div>
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
			<div class="recentViewItem">
				<div class="recentViewTxt">가지 Chatbot</div>
			</div>
		</div>
		<%
			} 
		%>
		<jsp:include page="../home/Footer.jsp" flush="true"/>
	</div>
</body>
</html>