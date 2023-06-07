<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
$('.item').click(function() {
	var productId = $(this).find('.itemNo').text()
	var memberId = $(this).find('.itemSeller').text()
	location.href = "../product/productDetail.jsp?productId=" + productId;
})
</script>
<div class="recommend">
<c:choose>
    <c:when test="${mode==0}">
        	<h3 class="title">오늘의 인기상품</h3>
    </c:when>
    <c:when test="${mode==1}">
	        <h3 class="title">이런 상품은 어때요?</h3>
    </c:when>
    <c:when test="${mode==2}">
        <h3 class="title">AI 추천 상품</h3>
    </c:when>
</c:choose>
<c:forEach var="i" begin="1" end="${fn:length(list)}">
	<div class="item">
		<div class="itemNo" style="display: none;">${list[i-1].productId}</div>
		<img class="itemImage" alt="제품이미지" src="http://awswlqccbpkd17595311.cdn.ntruss.com/${list2[i-1].productImageName}?type=f&w=195&h=195">
		<div class="itemCategory" style="color: #693FAA;">
			${list[i-1].category}
		</div>
		<div class="itemProductName">
			${list[i-1].productName}<!-- 출력용(expression language-EL) -->
		</div>
		<div class="itemPrice">
			<fmt:formatNumber value="${list[i-1].price}" pattern="#,###"/>원
		</div>
	</div>
</c:forEach>
</div>




