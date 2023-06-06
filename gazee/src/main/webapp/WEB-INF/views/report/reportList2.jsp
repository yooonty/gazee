<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List" %>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700;900&display=swap"
	rel="stylesheet">
<script type="text/javascript">

$('.pages').click(function() {
			$('#result').empty()
			$.ajax({
				url : "reportList", //views/bbsList2.jsp가 결과!
				data : {
					page : $(this).text(),
					mode: 2
				},
				success : function(result) { //결과가 담겨진 table부분코드
					$('#result').html(result)
				},
				error : function() {
					location.reload();
				}
			}) //ajax
		})
</script>

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

