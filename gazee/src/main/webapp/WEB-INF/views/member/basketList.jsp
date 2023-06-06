<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="basketList" style="margin-top:30px">
      <h5>내 가지바구니</h5>
      <table class="table table-striped" style="border: 0px !important" >
       <thead>
         <tr>
	        <th>상품번호</th>
	        <th>상품이름</th>
	        <th>아이디</th>
	     </tr>
        </thead>
        <tbody>
      	<c:forEach var="vo" items="${basketList}">
		<tr>
			<td>${vo.likeId}</td>
			<td>${vo.productId}</td>
			<td>${vo.memberId}</td>
		</tr>
		</c:forEach>
		</tbody>
	</table>   
      <br>
</div>
	
		
</body>
</html>