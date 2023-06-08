<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>가지가지</title>
<link rel="shortcut icon" href="../resources/favicon.ico">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700;900&display=swap" rel="stylesheet">
<link href="../resources/css/customerServiceStyle.css" rel="stylesheet" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		$('.pages').click(function() {
			$('#result').empty()
			$.ajax({
				url : "csSearch", //views/bbsList2.jsp가 결과!
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
			})
		}) //qna 페이징

		$('#csCategoryList')
				.click(
						function() {
							var category1 = $('#category').val();
							location.href = "csCategory?page=1&mode=1&category1="
									+ category1;

						})//cs 카테고리 버튼(목록)

		$('#csSearchBtn')
				.click(
						function() {
							var search1 = $('#csSearch').val();
							location.href = "csSearch?page=1&mode=1&search1="
									+ search1;

						})//cs 검색 버튼

		$('#csWrite').click(function() {
			  	var sessionId = "<%=session.getAttribute("id")%>";
					$.ajax({
						url: "checkTemporaryCs",
						data:{
							csWriter: sessionId
						},
						success: function(x){
							$('#alert').html(x);
						},
						error: function(xhr, status, error){
							location.href = "goToCsWrite?csWriter="+sessionId;
						}
					
					})
					})//qna 글쓰기 버튼
	})
</script>
</head>
<body>
    <div id="alert"></div>
	<div id="wrap">
		<div id="header">
			<jsp:include page="/home/Header.jsp" flush="true" />
		</div>
		<div id="content_wrap" style="text-align: left">
			<div id="content">
				<div id="customerServiceMain">
					<div id="customerHead">
						<h1 style="color: #693FAA">고객센터</h1>
					</div>
					<div id="customerMenu1" style="margin-top: 30px">
						<div class="FAQ">
							<div style="display: flex; justify-content: space-between;">
								<h3 style="color: #693FAA">
									<a href="csList?page=1&mode=1" style="color: #693FAA !important;">1:1 질문 게시판(QnA)</a>
								</h3>
								<div style="display: flex; justify-content: space-between; align-items: center; gap: 10px">
									<form method="get">
										<label for="category">카테고리</label> <select id="category"
											name="category" size="1">
											<option value="">선택하세요.</option>
											<option value="페이 관련">페이 관련</option>
											<option value="배송 관련">배송 관련</option>
											<option value="계정 관련">계정 관련</option>
											<option value="거래 관련">거래 관련</option>
										</select>
									</form>
									<button id="csCategoryList">목록</button>
									<button id="csWrite">글쓰기</button>
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
								<table class="table table-striped"
									style="margin: 0 auto;">
									<tr>
										<td class="top">번호</td>
										<td class="top">제목</td>
										<td class="top">작성자</td>
										<td class="top">작성날짜</td>
										<td class="top">조회수</td>
									</tr>
									<c:forEach items="${search}" var="bag" varStatus="status">
										<%
											@SuppressWarnings("unchecked")
											List<String> nickName = (List<String>) request.getAttribute("nickName");
										%>
										<tr>
											<td class="down">${bag.csNo}</td>
											<c:choose>
												<c:when
													test="${sessionScope.id eq bag.csWriter}">
													<td class="down"><a
														href="csOne?id=${bag.csId}&csWriter=${bag.csWriter}">${bag.csTitle}</a></td>
												</c:when>
												<c:otherwise>
													<td class="down"><a
														href="csOne?id=${bag.csId}&csWriter=${bag.csWriter}">비밀글입니다.</a></td>
												</c:otherwise>
											</c:choose>
											<td class="down">${nickName[status.index]}</td>
											<td class="down">${bag.csDate}</td>
											<td class="down">${bag.csView}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
							<div id="paging" style="margin-top: 10px; text-align: center">
								<%int pages1 = (int) request.getAttribute("pages1");
								     for (int p = 1; p <= pages1; p++) {
								%>
								<button class="pages"><%=p%></button>
								<%
									}
								%>
							</div>
							<div id=csButtom style="margin-top: 10px; text-align: center">

								<div id="search" style="margin-top: 10px">
									<input id="csSearch" type="text" size=40; placeholder="ID로 검색해주세요">
									<button id=csSearchBtn>검색</button>
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