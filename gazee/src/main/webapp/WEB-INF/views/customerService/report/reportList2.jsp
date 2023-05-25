<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
					alert('실패.@@@')
				}
			}) //ajax
		})
</script>

<table class="table table-striped"
					style="margin: 0 auto;">
					<tr>
						<td class="top">번호</td>
						<td class="top">제목</td>
						<td class="top">작성자</td>
						<td class="top">작성날짜</td>
						<td class="top">조회수</td>
					</tr>
					<c:forEach items="${list}" var="bag">
						<tr>
							<td class="down">${bag.reportNo}</td>
							<td class="down"><a href="reportOne?id=${bag.reportId}">${bag.reportTitle}</a></td>
							<td class="down">${bag.reportWriter}</td>
							<td class="down">${bag.reportDate}</td>
							<td class="down">${bag.reportView}</td>
						</tr>
					</c:forEach>
				</table>

