<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product 상세</title>
<link rel="stylesheet" href="../resources/css/pay.css" type="text/css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&display=swap" rel="stylesheet">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,600,0,0" />
<script type="text/javascript" src="../resources/js/jquery-3.6.4.js"></script>
<script>
$(function() {
	var balance;
	var bank = "";
	var account = "";
	var today = new Date();
	var startDate = new Date(new Date().setMonth(today.getMonth() - 6)).toISOString().substring(0, 10);
	var minDate = new Date(new Date().setFullYear(today.getFullYear() - 3)).toISOString().substring(0, 10);
	var maxDate = today.toISOString().substring(0, 10);
	var endDate = "";
	
	function numberToString(amount) {
		return amount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
	}
	function stringToNumber(amount) {
		return Number(amount.replace(/[-,]/g, ""));
	}
	
	function getRecord(transaction){
		$.ajax({
			url:"userInfo",
			type: "post",
			success: function(memberInfo){
				balance = memberInfo.balance;
				bank = memberInfo.bank;
				account = memberInfo.account;
				$('.balance-amount').text(numberToString(balance) + "원");
				if(account!=""){
					$('.account').text("등록된 계좌 : " + bank + " (" + account.slice(-4) + ")");
				}
			}
		})
	}
	
	function getTransactionRecord(){
		$.ajax({
		url:"record",
		data: {
			start : startDate,
			end : $('.input-date-end').val()
		},
		success : function(x){
			console.log('end' + endDate)
			$('#transaction-record-table').empty();
			$('#transaction-record-table').append(x)
		}
	})
	}

	$('.input-date-start').change(function(){
		$('.input-date-end').attr('min', $('.input-date-start').val())
	})
	
	$('.btn-date-search').click(function(){
		startDate = $('.input-date-start').val();
		endDate = $('.input-date-end').val();
		console.log('startDate' + startDate + ', endDate' + endDate);
		getTransactionRecord();
	})
	
	//btn-d페이지 로딩시 실행 - 전체 잔액 & 전체 거래 기록 가져오기, 출력
	$(document).ready(function() {
		//세션-로그인 여부 확인 후, 정보 있으면 반환, 없으면 메인 페이지 보내기
		getRecord('total');
		$('.input-date-start').val(startDate);
		$('.input-date-end').val(maxDate);
		$('.input-date-start').attr("min", minDate);
		$('.input-date-end').attr("max", maxDate);
		getTransactionRecord();
	});
	//모달창 닫기
	$('.btn-modal-close').click(function(){
		$('.modal').css('visibility', 'hidden');
		$('.calculate').css('visibility', 'hidden');
	})
	//출금 모달창 열기
	$('.btn-withdraw-modal-open').click(function(){
		$('.input-withdraw').val('');
		$('.withdraw').css('visibility', 'visible');
		$('.charge').css('visibility', 'hidden');
	})
	//환급 모달창 열기
	$('.btn-charge-modal-open').click(function(){
		$('.input-charge').val('');
		$('.charge').css('visibility', 'visible');
		$('.withdraw').css('visibility', 'hidden');
	})
	//환급 가능 금액 확인
	$('.btn-calculate').click(function(){
		var inputAmount = $('.input-withdraw').val();
		if (inputAmount==''){
			alert('금액을 입력해주세요.')
		}else{
			var amount = parseFloat(inputAmount);
			var commission = Math.floor(amount * 0.05);
			var result = balance - (amount+commission);
			
			$('.now-amount').text(numberToString(balance));
			$('.withdraw-amount').text("-"+numberToString(inputAmount));
			$('.commission').text("-"+numberToString(commission));
			$('.result-amount').text(numberToString(result));
			
			$('.calculate').css('visibility', 'visible');
			if (balance-amount-commission<0){
				$('.btn-allow').prop('disabled', true);
				$('.btn-allow').addClass('not');
				$('.btn-allow').removeClass('allowed');
				$('.btn-allow').text('환급불가');
			}else {
				$('.btn-allow').prop('disabled', false);
				$('.btn-allow').addClass('allowed')
				$('.btn-allow').removeClass('not')
				$('.btn-allow').text('환급하기');
			}
		}
	})
	//환급 실행
	$('.btn-allow').click(function(){
		if (bank==""){
			alert ("계좌를 등록해주세요.")
		}else{
			msg = $('.account').text() + "로 환급하시겠습니까?";
			confirm(msg);
			var requestedAmount = stringToNumber($('.withdraw-amount').text());
			console.log("requestedAmount : " + requestedAmount)
			var commission = stringToNumber($('.commission').text());
			var totalAmount = commission+requestedAmount;
			$.ajax({
				url:"withdraw",
				type : "POST",
				data:{
					bank : bank,
					account : account,
					requestedAmount : requestedAmount,
					commission : commission,
					totalAmount : totalAmount	
				},
				success : function(x) {
					alert("환급이 완료됐습니다." + x)
				}
			})
			window.location.reload();
		}
	})
	//거래기록 리스트 불러오기
	$('.btn-record-group button').click(function(){
		var id = $(this).attr('id');
		var clicked = $(this).hasClass('clicked')
		$('.btn-record-group button').removeClass('clicked');
		$('.btn-record-group button').addClass('not-clicked');
		if (clicked){
			$(this).addClass('not-clicked');
			$(this).removeClass('clicked');
			$('.tr-buy').css('visibility', 'hidden')
			$('.tr-charge').css('visibility', 'hidden')
		}else {
			$(this).addClass('clicked');
			$(this).removeClass('not-clicked');
			if(id="charge"){
				$('.tr-charge').css('visibility', 'visible')
				$('.tr-buy').css('visibility', 'hidden')
			}else if(id="buy"){
				$('.tr-buy').css('visibility', 'visible')
				$('.tr-charge').css('visibility', 'hidden')
			}else {
				$('.tr-charge').css('visibility', 'visible')
				$('.tr-buy').css('visibility', 'visible')
			}
		}
	})
})
</script>
</head>
<body>
<div id="wrap">
<div id="header">
<jsp:include page="../home/Header.jsp" flush="true"/>
</div>
<jsp:include page="charge.jsp" flush="true"/>
<div id="content_wrap">
<div id="content">
			<div class="pay">
				<div class="state">
					<div class="balance">
						<span class="money">My 가지머니</span> <span class="money balance-amount"></span>
					</div>
					<div class="transaction">
						<span>
							<span><button class="btn-withdraw-modal-open">환급</button></span>
							<span><button class="btn-charge-modal-open">충전</button></span>
						</span>
					</div>
				</div>
			</div>
			
			<div class="record">
				<div class="record-box">
				<div class="title-record">사용 내역</div>
				<div class="date-box">
					<span class="text-date-select">조회 기간</span>
					<div class="date-start"><input type="date" class="input-date-start"></div>
					<span class="dash">-</span>
					<div class="date-end"><input type="date" class="input-date-end"></div>
					<span><button class="btn-date-search">조회</button></span>
				</div>
				</div>
				<div class="text-date-limit">
				최대 3년 전까지 내역만 조회 가능합니다.
				</div>
				<div id="transaction-record-table">
				</div>
			</div>
<!-- 모달창 -->
	<!-- 환급 모달창 -->			
	<div class="withdraw modal">
		<div class="modal-close">
			<span class="material-symbols-outlined btn-modal-close">close</span>
		</div>
		
		<div class="modal-body">
			<div class="title">환급금액</div>
			<div class="confirm">
				<div class="amount">
					<input class="input-withdraw" type="number" min="1" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
					<span><span class="won">원</span> <button class="btn-calculate">확인</button></span>
				</div>
			</div>
			<div class="rule">환급 시 수수료 5%가 부과됩니다.</div>
			
			
			<div class="calculate">
				<div class="cms">
					<div class="cal now-balance">
						<span>현재 잔액</span>
						<span class="cms-value now-amount"></span>
					</div>
					<div class="cal">
						<span>(-) 환급 요청 금액</span>
						<span class="cms-value withdraw-amount"></span>
					</div>
					<div class="cal">
						<span>(-) 수수료(5%)</span>
						<span class="cms-value commission"></span>
					</div>
					
					<hr>
					<div class="cal">
						<span>환급 후 잔액</span>
						<span class="cms-value result-amount"></span>
					</div>
						
				</div>
				
				<div class="account-box">
					<div class="account"></div>
					<div>
						<button class="btn-allow"></button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 충전 모달창 -->
	<div class="charge modal">
		<div class="modal-close">
			<span class="material-symbols-outlined btn-modal-close">close</span>
		</div>
		
		<div class="modal-body">
			<div class="title">충전금액</div>
			<div class="confirm">
				<div class="amount">
					<input class="input-charge" type="number" min="1" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
					<span class="won">원</span>
				</div>
			</div>
			<div class="rule">1회 최대 충전금액은 1,000,000원 입니다.</div>
			<div class="btn-charge-group">
				<button onclick="requestPay(1)">카드결제</button>
					<button onclick="requestPay(2)" >실시간 계좌 이체</button>
			</div>

		</div>
	</div>
	</div>
	</div>
			<jsp:include page="../home/Footer.jsp" flush="true"/>
		</div>

</body>

</html>