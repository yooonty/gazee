<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700;900&display=swap" rel="stylesheet">
<link href="../resources/css/customerServiceStyle.css" rel="stylesheet" />
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		var sessionId = "<%=session.getAttribute("id")%>";
		
		$('#csList').click(function() {
			location.href = "csList?page=1&mode=1";

		})
		
		
		
		$('#goToCsUpdate').click(function() {
			location.href = "goToCsUpdate?csWriter="+sessionId+"&id="+${bag.csId};
		})

		$('#csDelete').click(function(){
			$.ajax({
				url:"csImgDelete",
				data:{
					csId:${bag.csId}
				},
				success:function(x){
					$.ajax({
						url:"csDelete",
						data:{
							csId:${bag.csId}
						},
						success:function(x){
							alert('삭제되었습니다.');
						}
					})
                    location.href = "csList?page=1&mode=1";
				}
				
			})
		})//csDelete
		
	})
</script>

</head>
<body>
	<div id="wrap">
		<div id="header">
			<jsp:include page="/home/Header.jsp" flush="true" />
		</div>
		<div id="content_wrap">
			<div id="content">
				<div id="customerServiceMain">
					<div id="customerHead" style="text-align: left;">
						<h1 style="color: #693FAA">고객센터</h1>
					</div>
					<div id="customerMenu1" style="margin-top: 30px">

						<div class="FAQ">
							<div style="display: flex; justify-content: space-between;">

								<h3 style="color: #693FAA">
									<a href="csList?page=1&mode=1"
										style="color: #693FAA !important;">1:1 질문 게시판(QnA)</a>
								</h3>
								<div
									style="display: flex; justify-content: space-between; align-items: center; gap: 10px">

									<button id="csList">목록으로</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				<%	String sessionID = (String) session.getAttribute("id");
					String csWriter = (String) request.getAttribute("csWriter");
					
					if (sessionID != null && sessionID.equals(csWriter)) {
				%>
						<div id="result">
					        <label>제목 : </label><label>${bag.csTitle}</label>
					        <hr>
					        <label>내용 : </label><label>${bag.csContent}</label>
					        <hr>
					        <label>사진 : </label>
					        <c:forEach items="${csImgList}" var="bag2">
					            <label><img src="http://erxtjrehmojx17106475.cdn.ntruss.com/${bag2.csImgName}?type=m&w=300&h=300"></label>
					        </c:forEach>
					        <hr>
					        <label>답글 : </label><label>${bag.csReply}</label>
					        <hr>
					        <div style="display: flex; justify-content: right; gap:15px;">
					            <button id="goToCsUpdate">수정하기</button>
					            <button id="csDelete">삭제하기</button>
					        </div>
					    </div>
					<%
					} else {
					%>
					    <div id="result">
					        <h1>작성자가 아니면 열람하실 수 없습니다.</h1>
					    </div>
					<%
					}
					%>
			</div>


		</div>
		<jsp:include page="/home/SideBar.jsp" flush="true" />
		<jsp:include page="/home/Footer.jsp" flush="true" />
	</div>
</body>
</html>