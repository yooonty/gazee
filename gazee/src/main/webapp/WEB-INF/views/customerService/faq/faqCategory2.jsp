<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript">
	$('.pages').click(function() {
		$('#result').empty()
		$.ajax({
			url : "faqCategory", //views/bbsList2.jsp가 결과!
			data : {
				page : $(this).text(),
				mode : 2,
				category1 : '${categoryValue}'
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

<table class="table table-striped" style="margin: 0 auto;">
	<tr>
		<td class="top">번호</td>
		<td class="top">제목</td>
		<td class="top">조회수</td>
	</tr>
	<c:forEach items="${category}" var="bag">
		<tr>
			<td class="down">${bag.faqNo}</td>
			<td class="down"><a href="faqOne?id=${bag.faqId}">${bag.faqTitle}</a>
			</td>
			<td class="down">${bag.faqView}</td>
		</tr>
	</c:forEach>
</table>
