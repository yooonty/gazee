<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String mode = request.getParameter("mode");
	String id = (String)session.getAttribute("id");
%>	
<script type="text/javascript">
$(function() {
	/* 검색창을 이용한 검색 */
	$('#searchButton').click(function() {
		var search = $('#searchBar').val()
		location.href = "../product/searchListOnSale?page=1&num=20&search=" + search
	})
	
	/* 검색 엔터키 이벤트 */
	$("#searchBar").keyup(function(event) {
		if (event.which == 13) {
			var search = $('#searchBar').val()
			location.href = "../product/searchListOnSale?page=1&num=20&search=" + search
		}
	})
	
	/* 카테고리(햄버거바)를 이용한 검색 */
	$('.categoryMenu').click(function() {
		var category = $(this).text()
		location.href = "../product/categoryListOnSale?page=1&num=20&category=" + category
	})
})
</script>
<link rel="stylesheet" href="../resources/css/style.css" type="text/css">
		<div class="headerContent">
			<div class="logo">
				<%
					if(mode!=null){
				%>
				<a href="../home/gazeeMain.jsp"> 
					<img src="../resources/img/gazee_logo.png" id="logo">
				</a>
				<%
					} else {
				%>
				<a href="../home/gazeeMain.jsp"> 
					<img src="../resources/img/gazee_logo.png" id="logo">
				</a>
				<%} %>
			</div>
			
			<div id="search">
				<input type="text" id="searchBar" placeholder="검색어를 입력하세요.">
				<input type="submit" id="searchButton" value="검색">
			</div>
			
			<div class="main-menu">
			<ul class="menu">
				<%
					if(id!=null){
				%>
				<li id="login"><a href="../member/mypage.jsp" style="color: #693faa !important;">${nickname}님</a></li>
				<li class= "line">|</li>
				<li><a href="../member/logout" onclick="LocalStorageClear()">로그아웃</a></li>
				<li class= "line">|</li>
				<li><a href="../faq/faqlist?page=1&mode=1">고객센터</a></li>
				<li class= "line">|</li>
				<li>
				<%
					} else {
				%>
				<li id="login"><a href="../member/member.jsp">로그인</a></li>
				<li class= "line">|</li>
				<li><a href="../member/signup.jsp">회원가입</a></li>
				<li class= "line">|</li>
				<li><a href="../faq/faqlist?page=1&mode=1">고객센터</a></li>
				<li class= "line">|</li>
				<li>
				<%} %>
					<div class="mobile_btn">
						<input type="checkbox" id="hamburger" /> <label for="hamburger">
							<span></span> <span></span> <span></span>
						</label>
						<div class="sidebar">
							<h2
								style="text-align: center; position: relative; top: 60px; color: #693FAA;">카테고리</h2>
							<hr
								style="position: relative; top: 60px; border: solid 1px black;">
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