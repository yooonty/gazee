<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<script type="text/javascript">
	 $(function() {
			$('#buyerCon').click(function() {
				let no = $('#no').text()
						$.ajax({
							url:"buyerCon",
							type:'post',
							data:{
								no : no
							},
							success: function(result) {
								console.log(result)
								location.href="mypage.jsp"
							},//success
						})//ajax
			})
		})
		
	</script>
<div id="purchsList">
      <h5>구매 목록(내역)</h5>
      <table class="table table-striped" style="border: 0px !important" >
       <thead>
         <tr>
	        <th>상품이름</th>
	        <th>구매자</th>
	        <th>판매자</th>
	        <th>셀러컨필름</th>
	        <th>현상태</th>
	     </tr>
        </thead>
        <tbody>
      	<c:forEach var="vo" items="${purchsList}">
      	<p id="no" style="display: none">${vo.no}</p>	
		<tr>
			<td>${vo.productId}</td>
			<td>${vo.buyerId}</td>
			<td>${vo.sellerId}</td>
			<td>${vo.sellerConfirm}</td>
			<td> <button id="buyerCon" >구매확정</button></td>
		</tr>
		</c:forEach>
		</tbody>
	</table>   
      <br>
</div>