<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>가지가지</title>
<link rel="shortcut icon" href="../resources/favicon.ico">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700;900&display=swap" rel="stylesheet">
<link href="../resources/css/customerServiceStyle.css" rel="stylesheet" type="text/css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		$('.pages').click(function() {
			$('#result').empty()
			$.ajax({
				url : "faqSearch", //views/bbsList2.jsp가 결과!
				data : {
					page : $(this).text(),
					mode : 2,
					search1 : '${searchValue}'
				},
				success : function(result) { //결과가 담겨진 table부분코드
					$('#result').html(result)
				},
				error : function() {
					alert('실패.@@@')
				}
			}) //ajax
		})//click

		$('#faqList')
				.click(
						function() {
							var category1 = $('#category').val();
							location.href = "faqCategory?page=1&mode=1&category1="
									+ category1;

						})//category

		$('#btn_faqSearch')
				.click(
						function() {
							var search1 = $('#faqSearch').val();
							location.href = "faqSearch?page=1&mode=1&search1="
									+ search1;

						})//search
	})
</script>
</head>
<body>
	<div id="wrap">
		<div id="header">
			<jsp:include page="/home/Header.jsp" flush="true" />
		</div>
		<div id="content_wrap">
			<div id="customerHead" style="text-align: left;">
				<h1 style="color: #693FAA">고객센터</h1>
			</div>
			<div id="content">
				<div id="customerServiceMain">
					<div id="customerMenu1" style="margin: 30px 0">
						<div class="FAQ">
							<div style="display: flex; justify-content: space-between;">
								<h3>
									<a href="faqlist?page=1&mode=1"
										style="color: #693FAA !important">자주 묻는 질문(FAQ)</a>
								</h3>
								<div
									style="display: flex; justify-content: space-between; align-items: center; gap: 10px">
									<form method="get">
										<label for="category">카테고리</label> <select id="category"
											name="category" size="1">
											<option value="">선택하세요.</option>
											<option value="결제 관련">결제 관련</option>
											<option value="배송 관련">배송 관련</option>
											<option value="거래 관련">거래 관련</option>
										</select>
									</form>
									<button id="faqList">목록</button>
								</div>
							</div>
						</div>
					</div>
					<div style="display: flex; justify-content: space-between;">
						<div id="customerServiceSideBar">
							<table>
								<tr height="75">
									<td><a href="../faq/faqlist?page=1&mode=1">FAQ게시판 목록</a></td>
								</tr>
								<tr height="75">
									<td><a href="../cs/csList?page=1&mode=1">질문게시판 목록</a></td>
								</tr>
								<tr height="75">
									<td><a href="../report/reportList?page=1&mode=1">신고게시판
											목록</a></td>
								</tr>
							</table>
						</div>
						<div style="width: 80%;">
							<div id=result>
								<table class="table table-striped" style="margin: 0 auto;">
									<tr>
										<td class="top">번호</td>
										<td class="top">제목</td>
										<td class="top">조회수</td>
									</tr>
									<c:forEach items="${search}" var="bag">
										<tr>
											<td class="down">${bag.faqNo}</td>
											<td class="down"><a href="faqOne?id=${bag.faqId}">${bag.faqTitle}</a>
											</td>
											<td class="down">${bag.faqView}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
							<div id="paging" style="margin-top: 10px; text-align: center">
								<%
									int pages1 = (int) request.getAttribute("pages1");
								for (int p = 1; p <= pages1; p++) {
								%>
								<button class="pages"><%=p%></button>
								<%
									}
								%>
							</div>
							<div id=faqButtom style="margin-top: 10px; text-align: center">

								<div id="search" style="margin-top: 10px">
									<input id="faqSearch" type="text" size=40;>
									<button id="btn_faqSearch">검색</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="/home/SideBar.jsp" flush="true" />
		<jsp:include page="/home/Footer.jsp" flush="true" />
	</div>
</body>
</html>