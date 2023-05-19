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
		var id = '<%=id%>'
		// var id2 = '${id}'
		if(id!="null"){ //사용자가 로그인했을때
			$.ajax({
				url : "../product/userBest",
				data : {
					memberId : id
				},
				success : function(res) {
					$('#userBest').append(res)
				}
			})
			$.ajax({
				url : "../product/wekaBest",
				data : {
					memberId : id
				},
				success : function(res) {
					$('#wekaBest').append(res)
				}
			})
			$.ajax({
				url : "../product/viewCount",
				data : {
					memberId : id
				},
				success : function(res) {
					$('.viewCount').append(res)
				}
			})
		}
		$.ajax({
			url : "../product/best",
			success : function(res) {
				$('#content').append(res)
			}
		}) 
		$('#searchButton').click(function() {
			var search = $('#searchBar').val()
			location.href = "../product/searchList?page=1&search=" + search
		})
		$('.categoryMenu').click(function() {
			var category = $(this).text()
			location.href = "../product/categoryList?page=1&category=" + category
		})
	}) 
</script>
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
			</div>
		</div>
	<jsp:include page="../home/Footer.jsp" flush="true"/>
	</div>
</body>
</html>