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
<link href="../resources/css/customerServiceStyle.css" rel="stylesheet" />
<link href="../resources/css/product-register.css" rel="stylesheet" type="text/css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		var sessionId = "<%=session.getAttribute("id")%>";

		$('#csUpdateBtn').click(
			function() {
				var csWriter = sessionId;
				var csCategory = $('.csCategory').val();
				var csTitle = $('.csTitle').val();
				var csContent = $('.csContent').val();
				var save= $(this).val();
					
				if (save == 1 && (csCategory == null || csTitle == null || csContent == null)) {
					alert("필수값을 입력해주세요");
				} else {
					$.ajax({
						url : "csUpdate",
						data : {
							csWriter : sessionId,
							csCategory : csCategory,
							csTitle : csTitle,
							csContent : csContent,
							csId:${bag.csId}
						},
						success : function(x) {
								alert("글 수정이 완료되었습니다.");
								location.href="csList?page=1&mode=1"
							}, error: function() {
								alert("글 수정 실패.");
							}
						
					})
				}

			})
		})
</script>

</head>
<body>
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
									<a href="csList?page=1&mode=1"
										style="color: #693FAA !important;">1:1 질문 게시판(QnA)</a>
								</h3>
							</div>
						</div>
					</div>
				</div>
				<div id=result>
					<div id="main">
						<div id="title">1:1 질문 게시글 쓰기</div>
						<div id="register_table">
							<span id="map"></span>
							<table>
								<tr class="each-row">
									<td class="attribute">제목</td>
									<td><input class="prd-info csTitle" type="text"	value="${bag.csTitle}"></td>
								</tr>
								<tr class="each-row">
									<td class="attribute">카테고리</td>
									<td><select class="csCategory" name="csCategory">
											<option value="페이 관련"
												${bag.csCategory == '페이 관련' ? 'selected' : ''}>페이
												관련</option>
											<option value="결제 관련"
												${bag.csCategory == '결제 관련' ? 'selected' : ''}>결제
												관련</option>
											<option value="계정 관련"
												${bag.csCategory == '계정 관련' ? 'selected' : ''}>계정
												관련</option>
											<option value="배송 관련"
												${bag.csCategory == '배송 관련' ? 'selected' : ''}>배송
												관련</option>
									</select></td>
									<td></td>
								</tr>
								<tr class="content-row">
									<td class="attribute">내용</td>
									<td class="content"><textarea class="prd-info csContent">${bag.csContent}</textarea></td>
								</tr>
								<tr class="each-row">
									<td class="attribute">사진첨부</td>
									<td style="color:red; font-size: 20px">문의 및 신고 게시판에서 사진 수정은 불가합니다.</td>
								</tr>
							</table>
						</div>
						<div style="display: flex; justify-content: right">
							<button id="csUpdateBtn">수정하기</button>
						</div>
					</div>
				</div>
				<div id="csButtom" style="margin-top: 10px; text-align: center">
				</div>
			</div>
		</div>
		<jsp:include page="/home/SideBar.jsp" flush="true" />
		<jsp:include page="/home/Footer.jsp" flush="true" />
	</div>

</body>
</html>