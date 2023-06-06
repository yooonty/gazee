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
<!-- <link href="../resources/css/gazee-main.css" rel="stylesheet" type="text/css"> -->
<script type="text/javascript" src="../resources/js/jquery-3.6.4.js"></script>
<script type="text/javascript">
	$(function() { //body 읽어왔을때
		/* 사용자 아이디 받아오기 */
		var id = '<%=id%>'
		
		/* 최근 본 상품 리스트 */
		$.ajax({
			url : "recentViewList",
			data : {
				memberId : id,
				page : 1,
				num : 10
			},
			success : function(res) {
				// console.log(res)
				$('#recentlyViewedList').append(res)
			}
		})
		
	}) 
</script>
<style type="text/css">
#recentlyViewed_title {
    font-weight: 500;
    font-size: 28px;
    line-height: 35px;
}

.recentItem {
	display: flex;
	align-items: center;
	border-bottom: 1px solid rgb(244, 244, 244);
	padding-top: 8px;
	padding-bottom: 8px;
	font-weight: 600;
}

.recentItemImage {
	margin-right: 20px;
}

.recentItemName, .recentItemContent{
	text-align: left;
	width: 340px;
}

.recentViewTable {
  border: 1px #a39485 solid;
  font-size: .9em;
  box-shadow: 0 2px 5px rgba(0,0,0,.25);
  width: 100%;
  border-collapse: collapse;
  border-radius: 5px;
  overflow: hidden;
  margin-bottom: 8px;
}

th, tr {
  text-align: center;
}
  
thead {
  font-weight: bold;
  color: #fff;
  background: #693FAA;
}

td, th {
  padding: 1em .5em;
  vertical-align: middle;
}
td {
  border-bottom: 1px solid rgba(0,0,0,.1);
  background: #fff;
}

.pages {
	width: 34px;
	height: 34px;
	background-color: white;
	border: 1px solid rgb(204, 204, 204);
	color: rgb(155, 153, 169);
	cursor: pointer;
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
				<h2 id="recentlyViewed_title">최근 본 상품</h2>
				<hr>
				<div id="recentlyViewedList">
				</div>
			</div>
		</div>
		<%-- <%
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
		%> --%>
		<jsp:include page="../home/Footer.jsp" flush="true"/>
	</div>
</body>
</html>