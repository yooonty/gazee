<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>가지가지</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700;900&display=swap" rel="stylesheet">
<link href="../resources/css/customerServiceStyle.css" rel="stylesheet" type="text/css">
<link href="../resources/css/alarm.css" rel="stylesheet" type="text/css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script type="text/javascript" src="../resources/js/WebSocket.js"></script>
<script type="text/javascript" src="../resources/js/sockjs-0.3.4.js"></script>
<script type="text/javascript" src="../resources/js/stomp.js"></script>
<script type="text/javascript">
	$(function() {
		var memberId = "<%= (String)session.getAttribute("id") %>";
		
		if (memberId !== "null") {
			/* 웹소켓 연결 */
			handlePageLoad(memberId);
			
			/* 안 읽은 메세지 체크 */
			unreadMessageCheck(memberId);
		}
		
		$('.pages').click(function() {
			//$('#result').empty()
			$.ajax({
				url : "reportList",
				data : {
					page : $(this).text(),
					mode : 2
				},
				success : function(result) { //결과가 담겨진 table부분코드
					$('#result').html(result)
				},
				error : function() {
					location.reload();
				}
			}) //ajax
		})

		$('#reportCategoryList')
				.click(
						function() {
							var category1 = $('#category').val();
							location.href = "reportCategory?page=1&mode=1&category1="
									+ category1;

						})//category

		$('#btn_reportSearch')
				.click(
						function() {
							var search1 = $('#reportSearch').val();
							location.href = "reportSearch?page=1&mode=1&search1="
									+ search1;

						})//category
		
		$('#reportWrite').click(function() {
			var sessionId = "<%=session.getAttribute("id")%>";
							$.ajax({
								url : "checkTemporaryReport",
								data : {
									reportWriter : sessionId
								},
								success : function(x) {
									$('#alert').html(x);
								},
								error : function(xhr, status, error) {
									location.href = "goToReportWrite?reportWriter="
											+ sessionId;
								}
							})
						})//report글쓰기 버튼

	})
</script>
</head>
<body>
	<div id="alert"></div>
	<div id="wrap">
		<div id="header">
			<jsp:include page="/home/Header.jsp" flush="true" />
		</div>
		<div id="content_wrap">
			<div id="customerHead" style="text-align: left">
				<h1 style="color: #693FAA">고객센터</h1>
			</div>
			<div id="content">
				<div id="customerServiceMain">
					<div id="customerMenu1" style="margin-top: 30px">
						<div class="FAQ">
							<div style="display: flex; justify-content: space-between;">
								<h3 style="color: #693FAA">
									<a href="reportList?page=1&mode=1" style="color: #693FAA !important;">신고 게시판</a>
								</h3>
								<div style="display: flex; justify-content: space-between; align-items: center; gap: 10px">
									<form method="get">
										<label for="category">카테고리</label> <select id="category"
											name="category" size="1">
											<option value="">선택하세요.</option>
											<option value="노쇼 관련">노쇼 관련</option>
											<option value="사기 관련">사기 관련</option>
											<option value="거래 관련">거래 관련</option>
										</select>
									</form>
									<button id="reportCategoryList">목록</button>
									<button id="reportWrite">글쓰기</button>
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
										<td class="top">작성자</td>
										<td class="top">작성날짜</td>
										<td class="top">조회수</td>
									</tr>
									<c:forEach items="${list}" var="bag" varStatus="status">
										<%
											@SuppressWarnings("unchecked")
											List<String> nickName = (List<String>) request.getAttribute("nickName");
										%>
										<tr>
											<td class="down">${bag.reportNo}</td>
											<c:choose>
												<c:when
													test="${sessionScope.id eq bag.reportWriter}">
													<td class="down"><a
														href="reportOne?id=${bag.reportId}&reportWriter=${bag.reportWriter}">${bag.reportTitle}</a></td>
												</c:when>
												<c:otherwise>
													<td class="down"><a
														href="reportOne?id=${bag.reportId}&reportWriter=${bag.reportWriter}">비밀글입니다.</a></td>
												</c:otherwise>
											</c:choose>
											<td class="down">${nickName[status.index]}</td>
											<td class="down">${bag.reportDate}</td>
											<td class="down">${bag.reportView}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
							<div id="paging" style="margin-top: 10px; text-align: center">
								<%int pages = (int) request.getAttribute("pages");
								for (int p = 1; p <= pages; p++) {
								%>
								<button class="pages"><%=p%></button>
								<%
									}
								%>
							</div>
							<div id="faqButtom" style="margin-top: 10px; text-align: center">

								<div id="search" style="margin-top: 10px">
									<input id="reportSearch" type="text" size=40; placeholder="ID로 검색해주세요">
									<button id="btn_reportSearch">검색</button>
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