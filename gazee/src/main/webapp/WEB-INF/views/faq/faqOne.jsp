<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>가지가지</title>
<link rel="shortcut icon" href="../resources/favicon.ico">
<link href="../resources/css/customerServiceStyle.css" rel="stylesheet" />
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		$('#faqList').click(function() {
			location.href = "faqlist?page=1&mode=1";

		})

	})
</script>

</head>
<body>
<div id ="wrap">
	<div id="header">
		<jsp:include page="/home/Header.jsp" flush="true" />
	</div>
	<div id="content_wrap">
		<div id="content">
			<div id="customerServiceMain">
				<div id="customerHead" style="text-align:left;">
					<h1 style="color: #693FAA">고객센터</h1>
				</div>
				<div id="customerMenu1" style="margin-top: 30px">

					<div class="FAQ">
						<div style="display: flex; justify-content: space-between;">

							<h3 style="color: #693FAA">
								<a href="faqlist?page=1&mode=1"
									style="color: #693FAA !important;">자주 묻는 질문(FAQ)</a>
							</h3>
							<div
								style="display: flex; justify-content: space-between; align-items: center; gap: 10px">

								<button id="faqList">목록으로</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id=result>
				<label>제목</label><label> ${bag.faqTitle}</label>
				<hr>
				<label>내용</label><label> ${bag.faqContent}</label>
			</div>
		</div>
	</div>
	<jsp:include page="/home/SideBar.jsp" flush="true" />
	<jsp:include page="/home/Footer.jsp" flush="true" />
	</div>
</body>
</html>