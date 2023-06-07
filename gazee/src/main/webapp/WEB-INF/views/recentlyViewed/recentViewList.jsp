<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script>
/* 페이지 클릭시 다른 페이지로 */
$('.pages').click(function() {
	$.ajax({
		url : "recentViewList",
		data : {
			memberId : '<%= session.getAttribute("id")%>',
			page : $(this).text(),
			num : 10
		},
		success : function(res) {
			$('#recentlyViewedList').empty()
			$('#recentlyViewedList').append(res)
			$('html').scrollTop(0); // 페이지 맨 위로 이동
		}
	})
})
</script>
<table class="recentViewTable">
    <thead>
    <tr>
        <th>상품이미지</th>
        <th>상품명</th>
        <th>상품내용</th>
        <th>상품카테고리</th>
        <th>상품가격</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="i" begin="1" end="${fn:length(list)}">
    <tr>
        <td style="display: none;">${list[i-1].productId}</td>
        <td><img class="recentItemImage" alt="제품이미지" src="http://erxtjrehmojx17106475.cdn.ntruss.com/${list2[i-1].productImageName}?type=f&w=60&h=80"></td>
        <td>${list[i-1].productName}</td>
        <td>${list[i-1].productContent}</td>
        <td>${list[i-1].category}</td>
        <td>${list[i-1].price}원</td>
    </tr>
	</c:forEach>
    </tbody>
</table>
<%
	int pages = (int)request.getAttribute("pages");
	for(int p = 1; p <= pages; p++){
%>
	<button class="pages"><%= p %></button>
<%		
	}
%>
