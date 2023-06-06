<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <div class="">
    <div class="">
      <div class="fakeimg">
      <h5>구매 목록(내역)</h5>
      <table class="table table-striped">
      <tr>
        <th>상품이름</th>
        <th>구매자</th>
        <th>판매자</th>
        <th>금액</th>
      </tr>
      	<c:forEach var="vo" items="${purchsList}">
		<tr>
			<td>${vo.productId}</td>
			<td>${vo.buyerId}</td>
			<td>${vo.sellerId}</td>
			<%-- <td>${vo.paid}</td> --%>
		</tr>
		</c:forEach>
	</table>
      </div>
      
      <div class="fakeimg">
		<h5>판매 목록(내역)</h5>
     <table class="table">
      <tr>
        <th>판매자</th>
        <th>구매자</th>
        <th>금액</th>
      </tr>
   
      	<c:forEach var="vo" items="${sellList}">
      	<tbody>
		<tr>
			<td>${vo.sellerId}</td>
			<td>${vo.buyerId}</td>
		</tr>
		</tbody>
		</c:forEach>
	</table>
      </div>
    </div>
  </div>
