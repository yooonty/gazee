<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String id = (String)session.getAttribute("id");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>가지가지</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<link href="../resources/css/gazee-main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../resources/js/jquery-3.6.4.js"></script>
<script type="text/javascript">
	$(function() { //body 읽어왔을때
		/* 페이지 클릭시 다른 페이지로 */
		$('.pages').click(function() {
			alert($(this).text())
			$.ajax({
				url : "../product/productList",
				data : {
					page : $(this).text(),
					num : 20, 
					search : '${search}'
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
		
		/* 검색창을 이용한 검색 */
		$('#searchButton').click(function() {
			var search = $('#searchBar').val()
			location.href = "../product/searchList?page=1&num=20&search=" + search
		})
		
		/* 카테고리를 이용한 검색 */
		$('.categoryMenu').click(function() {
			var category = $(this).text()
			location.href = "../product/categoryList?page=1&num=20&category=" + category
		})
		
		/* 상품 상세페이지로 이동 */
		$('.item').click(function() {
			var productId = $(this).find('.itemNo').text()
			location.href = "../product/gazeeDetail.jsp?productId=" + productId
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
		<div id="content">
			<h3 id="categoryTitle">전체 카테고리</h3>
			<div id="categoryWrap">
				<div class="category"><a class="categoryMenu">의류</a></div><div class="category"><a class="categoryMenu">잡화</a></div><div class="category"><a class="categoryMenu">도서</a></div><div class="category"><a class="categoryMenu">디지털기기</a></div>
				<div class="category"><a class="categoryMenu">생활가전</a></div><div class="category"><a class="categoryMenu">가구/인테리어</a></div><div class="category"><a class="categoryMenu">뷰티/미용</a></div><div class="category"><a class="categoryMenu">스포츠/레저</a></div>
				<div class="category"><a class="categoryMenu">생활/주방</a></div><div class="category"><a class="categoryMenu">취미/게임/음반</a></div><div class="category"><a class="categoryMenu">반려동물용품</a></div><div class="category"><a class="categoryMenu">기타</a></div>
			</div>
			<div id="searchHelper">
				<div>
					<span style="color: #693FAA;">${search}</span>에 대한 검색 <span style="color: #888888;">${count}개</span>
				</div>
				<div>
					<ul id="searchOrder">
						<li><a href="../product/searchListOnSale?page=1&num=20&search=${search}">판매중인 상품보기</a></li>
					</ul>
				</div>
			</div>
			<hr>
			<div id="d1" style="height: 1170px; margin-top: 20px;">
			<c:forEach var="i" begin="1" end="${fn:length(list)}">
				<div class="item">
					<div class="itemNo" style="display: none;">${list[i-1].productId}</div>
					<img class="itemImage" alt="제품이미지" src="http://erxtjrehmojx17106475.cdn.ntruss.com/${list2[i-1].productImageName}?type=f&w=195&h=195">
					<div class="itemContent">
						${list[i-1].productName}<!-- 출력용(expression language-EL) -->
					</div>
					<div class="itemContent">
						${list[i-1].productContent}
					</div>
					<div class="itemContent">
						${list[i-1].price}원
					</div>
				</div>
			</c:forEach>
			</div>
			<div style="display: flex; justify-content: center;">
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
	<jsp:include page="/home/Footer.jsp" flush="true"/>
</div>
</body>
</html>