<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>가지가지</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<link href="../resources/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../resources/js/jquery-3.6.4.js"></script>
<script type="text/javascript">
	$(function() { //body 읽어왔을때
		$('.pages').click(function() {
			alert($(this).text())
			$.ajax({
				url : "../product/productList2",
				data : {
					page : $(this).text(),
					category : '${category}'
				},
				success : function(res) {
					$('#d1').html(res)
				},
				error : function() {
					alert('실패!')
				}
			})
		})
		$('#searchButton').click(function() {
			var search = $('#searchBar').val()
			location.href = "../product/searchList?page=1&search=" + search
		})
		$('.categoryMenu').click(function() {
			var category = $(this).text()
			location.href = "../product/categoryList?page=1&category=" + category
		})
		$('.item').click(function() {
			var productId = $(this).find('.itemNo').text()
			location.href = "../product/gazeeDetail.jsp?productId=" + productId
		})
	}) 
</script>
<style type="text/css">
	
	#categoryWrap {
		width: 100%;
    	display: flex;
    	justify-content: center;
    	flex-wrap: wrap;
    	transition: height 0.3s ease-in-out 0s;
    	margin-bottom: 50px;
	}
	
	.category{
		width: 207px;
	    height: 48px;
	    background: rgb(255, 255, 255);
	    margin: 0px 1px 1px 0px;
	    border: 1px solid rgb(238, 238, 238);
	    display: flex;
	    align-items: center;
	    padding: 0 20px
	}
	
	#categoryTitle {
		margin-top: 0;
		text-align: center;
		font-size: 24px;
		font-weight: 500;
	}
	
	#searchHelper {
		display: flex;
		justify-content: space-between;
	}
	
	#searchOrder {
		display: flex;
		list-style-type: none;
		margin: 0;
	}
</style>
</head>
<body>
<div id = "header">
	<div class = "headerContent">
		<a href="../home/gazeeMain.jsp">
			<img src="../resources/img/gazee_logo.png" id = "logo">
		</a>
		<div id="search">
			<input type="text" id="searchBar" placeholder="검색어를 입력하세요.">
			<input type="submit" id="searchButton" value="검색">
		</div>
		<ul class = "menu">
			<li id="login">로그인</li>
			<li class= "line">|</li>
			<li>회원가입</li>
			<li class= "line">|</li>
			<li>고객센터</li>
			<li class= "line">|</li>
			<li>
				<div class="mobile_btn">
					<input type="checkbox" id="hamburger" />
					<label for="hamburger">
					  	<span></span>
					  	<span></span>
					  	<span></span>
					</label>
					<div class="sidebar">
					    <h2 style="text-align: center; position: relative; top: 60px; color: #693FAA;">카테고리</h2>
					    <hr style="position: relative; top:60px; border: solid 1px black;">
					    <ul class="nav_mobile">
					      <li><a class="categoryMenu">의류</a><a class="categoryMenu">잡화</a><a class="categoryMenu">도서</a></li>
					      <li><a class="categoryMenu">디지털기기</a><a class="categoryMenu">생활가전</a><a class="categoryMenu">가구/인테리어</a></li>
					      <li><a class="categoryMenu">뷰티/미용</a><a class="categoryMenu">스포츠/레저</a><a class="categoryMenu">생활/주방</a></li>
					      <li><a class="categoryMenu">취미/게임/음반</a><a class="categoryMenu">반려동물용품</a><a class="categoryMenu">기타</a></li>
					    </ul>
					</div>
				</div>
			</li>
		</ul>
	</div>
</div>
<div id = "content" style="height: 1500px;">
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
				<li>조회순</li>
				<li class= "line">|</li>
				<li>가격순</li>
			</ul>
		</div>
	</div>
	<hr>
	<div id="d1" style="height: 1140px;">
	<c:forEach var="i" begin="1" end="${fn:length(list)}">
		<div class="item">
			<div class="itemNo" style="display: none;">${list[i-1].productId}</div>
			<img class="itemImage" alt="제품이미지" src="../resources/img/${list2[i-1].productImageUrl}">
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
<%
	int pages = (int)request.getAttribute("pages");
	for(int p = 1; p <= pages; p++){
%>
	<button style="width: 50px;" class="pages"><%= p %></button>
<%		
	}
%>
	
	
</div>
<div id = "footer">
	<div>
		<p style = "margin: 0; font-size: 12px;">Copyrightⓒ 2023. gazee. All rights reserved.</p>
	</div>
</div>
	
</body>
</html>