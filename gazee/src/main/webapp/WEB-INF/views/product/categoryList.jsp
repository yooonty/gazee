<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>가지가지</title>
<link rel="shortcut icon" href="../resources/favicon.ico">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<link href="../resources/css/gazee-main.css" rel="stylesheet" type="text/css">
<link href="../resources/css/alarm.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../resources/js/jquery-3.6.4.js"></script>
<script type="text/javascript" src="../resources/js/WebSocket.js"></script>
<script type="text/javascript" src="../resources/js/sockjs-0.3.4.js"></script>
<script type="text/javascript" src="../resources/js/stomp.js"></script>
<script type="text/javascript">
	$(function() { //body 읽어왔을때
		var memberId = "<%= (String)session.getAttribute("id") %>";
		
		if (memberId !== "null") {
			/* 웹소켓 연결 */
			handlePageLoad(memberId);
			
			/* 안 읽은 메세지 체크 */
			unreadMessageCheck(memberId);
		}
		
		/* 페이지 클릭시 다른 페이지로 */
		$('.pages').click(function() {
			$.ajax({
				url : "../product/productList2",
				data : {
					page : $(this).text(),
					num : 20,
					category : '${category}'
				},
				success : function(res) {
					$('#d1').html(res);
					$('html').scrollTop(0); // 페이지 맨 위로 이동
				},
				error : function() {
					alert('실패!')
				}
			})
		})
		
		/* 상품 상세페이지로 이동 */
		$('.item').click(function() {
			var productId = $(this).find('.itemNo').text()
			location.href = "../product/productDetail.jsp?productId=" + productId
		})
	}) 
</script>
</head>
<body>
<div id="wrap">
	<div id = "header">
		<jsp:include page="/home/Header.jsp" flush="true"/>
	</div>
	<div id="content_wrap">
		<div id = "content" style="display: flex; flex-flow: column;">
			<h3 id="categoryTitle">전체 카테고리</h3>
			<div id="categoryWrap">
				<div class="category"><a class="categoryMenu">의류</a></div><div class="category"><a class="categoryMenu">잡화</a></div><div class="category"><a class="categoryMenu">도서</a></div><div class="category"><a class="categoryMenu">디지털기기</a></div>
				<div class="category"><a class="categoryMenu">생활가전</a></div><div class="category"><a class="categoryMenu">가구/인테리어</a></div><div class="category"><a class="categoryMenu">뷰티/미용</a></div><div class="category"><a class="categoryMenu">스포츠/레저</a></div>
				<div class="category"><a class="categoryMenu">생활/주방</a></div><div class="category"><a class="categoryMenu">취미/게임/음반</a></div><div class="category"><a class="categoryMenu">반려동물용품</a></div><div class="category"><a class="categoryMenu">기타</a></div>
			</div>
			<div id="searchHelper">
				<div>
					<span style="color: #693FAA;">${category}</span>에 대한 검색 <span style="color: #888888;">${count}개</span>
				</div>
				<div>
					<ul id="searchOrder">
						<li><a href="../product/searchListOnSale?page=1&num=20&search=" style="color: #693FAA !important;">전체상품</a></li>
						<li><a href="../product/categoryListOnSale?page=1&num=20&category=${category}">판매중인 상품보기</a></li>
					</ul>
				</div>
			</div>
			<hr style="width: 100%;">
			<div id="d1" style="margin-top: 20px;">
			<c:forEach var="i" begin="1" end="${fn:length(list)}">
				<div class="item">
					<div class="itemNo" style="display: none;">${list[i-1].productId}</div>
					<img class="itemImage" alt="제품이미지" src="http://awswlqccbpkd17595311.cdn.ntruss.com/${list2[i-1].productImageName}?type=f&w=195&h=195">
					<div class="itemCategory" style="color: #693FAA;">
						${list[i-1].category}
					</div>
					<div class="itemProductName">
						${list[i-1].productName}<!-- 출력용(expression language-EL) -->
					</div>
					<div class="itemPrice">
						<fmt:formatNumber value="${list[i-1].price}" pattern="#,###"/>원
					</div>
				</div>
			</c:forEach>
			</div>
			<div style="display: flex; justify-content: center; margin: 50px 0;">
		<%
			int pages = (int)request.getAttribute("pages");
			for(int p = 1; p <= pages; p++){
		%>
			<button class="pages"><%= p %></button>
		<%		
			}
		%>
			</div>
		</div>
	</div>
	<jsp:include page="/home/SideBar.jsp" flush="true"/>
	<jsp:include page="/home/Footer.jsp" flush="true"/>
</div>
</body>
</html>