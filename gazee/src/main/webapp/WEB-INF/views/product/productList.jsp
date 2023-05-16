<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
$('.item').click(function() {
	var productId = $(this).find('.itemNo').text()
	location.href = "../product/gazeeDetail.jsp?productId=" + productId
})
</script>
<c:forEach var="i" begin="1" end="${fn:length(list)}">
	<div class="item">
		<div class="itemNo" style="display: none;">${list[i-1].productId}</div>
		<img class="itemImage" alt="제품이미지" src="../resources/img/${list2[i-1].productImageUrl}">
		<div class="itemContent">
			${list[i-1].productName}<!-- 출력용(expression language-EL) -->
		</div>
		<div class="itemContent">
			${list[i-1].productContent}
		</div>
		<div class="itemContent">
			${list[i-1].price}원
		</div>
	</div>
</c:forEach>




