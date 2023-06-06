<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="../resources/js/jquery-3.6.4.js"></script>
<script>
$(function() {
	$(document).ready(function() {
		var tableHeight = $( ".table-record-total" ).css( "height" );
		$('.table-record-height').css("height", tableHeight);
	});
	$('.btn-record-group button').click(function(){
		var id = $(this).attr('id');
		var clicked = $(this).hasClass('btn-record-clicked')
		$('.btn-record-group button').removeClass('btn-record-clicked');
		$('.btn-record-group button').addClass('btn-record-not-clicked');
		$('.table-record-content').removeClass('table-visible');
		$('.table-record-content').removeClass('table-hidden');
		$('.table-record-content').addClass('table-hidden');
		if (clicked){
			$(this).addClass('btn-record-not-clicked');
			$(this).removeClass('btn-record-clicked');
		}else {
			$(this).addClass('btn-record-clicked');
			$(this).removeClass('btn-record-not-clicked');
			if(id=="charge"){
				$('.table-record-charge').removeClass('table-hidden');
				$('.table-record-charge').addClass('table-visible');
				var tableHeight = $( ".table-record-charge" ).css( "height" );
				$('.table-record-height').css("height", tableHeight);
			}else if (id=="buy"){
				$('.table-record-buy').removeClass('table-hidden');
				$('.table-record-buy').addClass('table-visible');
				var tableHeight = $( ".table-record-buy" ).css( "height" );
				$('.table-record-height').css("height", tableHeight);
				
			}else {
				$('.table-record-total').removeClass('table-hidden');
				$('.table-record-total').addClass('table-visible');
				var tableHeight = $( ".table-record-total" ).css( "height" );
				$('.table-record-height').css("height", tableHeight);
			}
		}
	})
	$('.btn-charge-cancel').click(function(){
		if(confirm("해당 충전 내역을 취소하시겠습니까?")){
			var transactionId = $(this).attr("data-transaction-id");
			console.log("transaction : "+transactionId)
			refundCharge(transactionId)
		}
	})

	function refundCharge(transactionId){
		$.ajax({
			url : "charge/refund",
			type: "POST",
			data : {
				cancelTransactionId : transactionId				
			},
			success : function(x){
				var msg = "충전이 정상적으로 취소되었습니다."
				alert(msg)
			},
		    error: function(x){
		      	var msg = "충전이 취소되지 않았습니다. 다시 시도해주세요"
		    	alert(msg)	
		    }
		})
		window.location.reload();
	}
})
</script>
<style>
.btn-record-not-clicked{
	background-color: #fff;
	color: #693FAA;
}
.btn-record-clicked {
	background-color: #693FAA;
	color: #fff;
}
.btn-record-group button{
	font-weight:bold;
	font-size:15px;
	width: 250px;
	height: 30px;
	border-radius: 30px;
	border: 2px solid;
	line-height: 30px;
	cursor: pointer;
	border-color: #693FAA;
}
.table-record-title {
	width:100%;
	margin-top: 20px;
	border-top: 1px solid #898989;
	background: #f8f9fa;
	font-weight: bold;
}
.trans {
	width:20%;
}
.date {
	width:40%;
}
.price {
	width:20%;
}
.td-balance{
	width:20%;
}
.table-record-line {
	width: 100%;
	height: 50px;
	height: 50px;
	display: flex;
	flex-direction: row;
	border-bottom: 1px solid #898989;
	line-height: 50px;
}
.table-record-content {
	position: absolute;
	width: 900px;
	top: 540px;
	margin: 0 auto;
}
.table-visible {
	visibility: visible;
}
.table-hidden {
	visibility: hidden;
}
.btn-charge-cancel {
	width: 50px;
	height: 30px;
	border-radius: 30px;
	border: 1px solid #898989;
	background-color: #693FAA;
	color: #fff;
	line-height: 30px;
	cursor: pointer;
	font-weight: bold;
}

</style>
</head>
<body>
<div class="btn-record-group">
	<div>
		<button id="total" class="clicked">전체</button><br>
	</div>
	<div>
		<button id="buy" class="not-clicked">결제/정산</button><br>
	</div>
	<div>
		<button id="charge" class="not-clicked">충전/환급</button><br>
	</div>
</div>
<div class="table-record">
	<div class="table-record-title table-record-line">
		<div class="trans">이용</div>
		<div class="date">날짜</div>
		<div class="price">금액</div>
		<div class="td-balance">잔액</div>
	</div>
	<div class="table-record-height"></div>
	
	<div class="table-record-charge table-record-content table-hidden">
		<c:forEach var="record" items="${transactionRecord}">
		<c:if test="${record.transactionType eq '충전' || record.transactionType eq '환급' || record.transactionType eq '충전(취소)'}">
		<div class="table-record-line" style="width:100%; height:50px">
			<div class="trans">${record.transactionType}
				<c:if test="${record.refund eq 1}">
				<button class="btn-charge-cancel" data-transaction-id="${record.transactionId}">취소</button>
				</c:if>
			</div>
			<div class="date">${record.transactionDate}</div>
			<div class="price">${record.amount}</div>
			<div class="td-balance">${record.balance}</div>
		</div>
		</c:if>
		</c:forEach>
	</div>
	<div class="table-record-buy table-record-content table-hidden">
		<c:forEach var="record" items="${transactionRecord}">
		<c:if test="${record.transactionType eq '결제' || record.transactionType eq '정산'}">
		<div class="table-record-line" style="width:100%; height:50px">
			<div class="trans">${record.transactionType}</div>
			<div class="date">${record.transactionDate}</div>
			<div class="price">${record.amount}</div>
			<div class="td-balance">${record.balance}</div>
		</div>
		</c:if>
		</c:forEach>
	</div>
	<div class="table-record-total table-record-content table-visible">
		<c:forEach var="record" items="${transactionRecord}">
		<div class="table-record-line" style="width:100%; height:50px">
			<div class="trans">${record.transactionType}
			<c:if test="${record.refund eq 1}">
				<button class="btn-charge-cancel" data-transaction-id="${record.transactionId}">취소</button>
			</c:if>
			</div>
			<div class="date">${record.transactionDate}</div>
			<div class="price">${record.amount}</div>
			<div class="td-balance">${record.balance}</div>
		</div>
		</c:forEach>
	</div>	
</div>
</body>
</html>