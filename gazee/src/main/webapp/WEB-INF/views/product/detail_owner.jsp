<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
table {
	margin-left: auto;
	margin-right: auto;
	text-align: center;
}

#chatList {
	width: 300px;
	height: 80px;
	background: #693FAA;
	border-radius: 20px;
	font-size: large;
	color: white;
	border: none;
}

.btn_product {
	border: none;
	background: #e8e8e8;
	height: 50px;
	width: 120px;
	margin: 5px;
	border-radius: 5%;
}
</style>
<script type="text/javascript">
$(function() {
	    
	  $('#productUpdate').click(function() {	
		  var sessionId = "<%=session.getAttribute("id")%>";
		    var productId = ${bag.productId};
		    
	    location.href = "../product/productUpdate.jsp?sessionId=" + sessionId + "&productId="+ productId;
	  });

	  
	  $('#productDelete').click(function() {
      	var memberId = "<%=session.getAttribute("id")%>";
      	console.log("sessionId" + memberId);
  		console.log("productId" + ${bag.productId});
          if (confirm('정말로 삭제하시겠습니까?')) {
              $.ajax({
                  url: 'productDelete',
                  type: 'POST',
                  data: {
                      memberId: memberId,
                      productId: ${bag.productId}
                  },
                  success: function(x) {
                          alert('삭제되었습니다.');
                          location.href = "../home/gazeeMain.jsp";
                          
                  }
              });
          }
      });
	  
	  $("#chatList").click(function() {
		  var sessionId = "<%=session.getAttribute("id")%>";
		    var productId = ${bag.productId};
		  if (sessionId != null) {
				location.href = "../chat/gazeeChat.jsp";
			}
	  });
	});
</script>
</head>
<body>
	<table>
		<tr>
			<td rowspan="6" style="width: 500px; height: 500px;"><img
				width="500px" height="500px"
				src="../resources/img/${bag2.productImageName}"></td>
			<td style="width: 250px"><div>${bag.category}</div></td>
		</tr>
		<tr>
			<td style="font-weight: 900; font-size: xx-large;"><div>
					${bag.productName}</div></td>
		</tr>
		<tr>
			<td><div>
					<c:if test="${bag.dealDirect == 1}">
						<img height="30px" src="../resources/img/direct.png">
					</c:if>
					<c:if test="${bag.dealDelivery == 1}">
						<img height="30px" src="../resources/img/delivery.png">
					</c:if>
				</div></td>
		</tr>

		<tr height="150px">
			<td><div style="font-size: medium;">${bag.productContent}</div></td>
		</tr>
		<tr>
			<td><div style="font-weight: 900; font-size: xx-large;"><fmt:formatNumber value="${bag.price}" pattern="#,###"/>원</div></td>
		</tr>
		<tr>
			<td><button class="btn_product" id="productUpdate">수정</button>
				<button class="btn_product" id="productDelete">삭제</button></td>
		</tr>
		<tr>
			<td style="display: inline-block;">
				<div style="display: inline-block;">판매자 id : ${bag.memberId}</div>
				<div style="display: inline-block; color: red;">신고횟수
					${bag3.count}회</div>
			</td>
			<td><button id="chatList">채팅 목록 보기</button></td>

		</tr>
	</table>
</body>
</html>