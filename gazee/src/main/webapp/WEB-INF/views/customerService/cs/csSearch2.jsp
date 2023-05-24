<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="../../resources/css/style2.css" rel="stylesheet" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script type="text/javascript">
	$('.pages').click(function() {
		//$('#result').empty()
		$.ajax({
			url : "csSearch", //views/bbsList2.jsp가 결과!
			data : {
				page : $(this).text(),
				mode : 2,
				search1 : '${search1}'
			},
			success : function(result) { //결과가 담겨진 table부분코드
				$('#result').html(result)
			},
			error : function() {
				alert('실패.@@@')
			}
		}) //ajax
	})
	$('#csSearchBtn').click(function() {
			var search1=$('#csSearch').val();
			location.href="../../customerService/cs/csSearch?page=1&mode=1&search1="+search1;
			
		})//search
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
	<c:forEach items="${search}" var="bag">
		<tr>
			<td class="down">${bag.csNo}</td>
			<td class="down"><a href="csOne?id=${bag.csId}">${bag.csTitle}</a></td>
			<td class="down">${bag.csWriter}</td>
			<td class="down">${bag.csDate}</td>
			<td class="down">${bag.csView}</td>
		</tr>
	</c:forEach>
</table>