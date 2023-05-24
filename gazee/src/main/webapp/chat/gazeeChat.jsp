<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700;900&display=swap" rel="stylesheet">
<link href="https://webfontworld.github.io/gmarket/GmarketSans.css" rel="stylesheet">
<link href="../resources/css/style.css" rel="stylesheet" type="text/css">
<link href="../resources/css/chatRoom.css" rel="stylesheet" type="text/css">
<link href="../resources/css/alarm.css" rel="stylesheet" type="text/css">
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="../resources/js/jquery-3.6.4.js"></script>
<script type="text/javascript" src="../resources/js/sockjs-0.3.4.js"></script>
<script type="text/javascript" src="../resources/js/stomp.js"></script>
<script type="text/javascript" src="../resources/js/WebSocket.js"></script>
<script type="text/javascript">
	
	var urlParams = new URLSearchParams(location.search);
	
	var roomId = urlParams.get('roomId');
	var dealType = urlParams.get('dealType');
	var memberId = '<%= session.getAttribute("id") %>';
	var socketSession = '<%= session.getAttribute("subscribedRoomIds")%>';
	var selectedRoomId = null;
	
	$(function() {
		if (socketSession != null) {
			$(document).ready(function() {
				$.ajax({
					url: '../chat/getSubscribedRoomIds',
					type: 'GET',
			        dataType: 'json',
			        success: function(response) {
			            var roomIds = response;
			            roomIds.forEach(function(roomId) {
			            	allSocketConnect(roomId);
			            });
			        },
			        error: function(error) {
			            console.error('Failed to get subscribed roomIds from session');
			            console.log(error);
			        }
				})
			})
		}
		
		/* 채팅 내역 불러오는 함수 */
		function getChatHistory(roomId) {
			$.ajax({
				url: 'chatMessageList',
				data: {
					roomId: roomId
				},
				success: function(result) {
					for (var i = 0; i < result.length; i++) {
						showMessageOutput(result[i])
					}
				}, 
				error: function(e) {
					console.log("---" + e)
				}
			})
		}
		
		/* 결제요청 메세지 */
		function paymentMessage(roomId) {
			let sender = memberId;
			let content = "결제요청";
			
			stompClient.send('/app/chat/'+roomId, {}, JSON.stringify({
				'sender' : sender,
				'content' : content
			}));
		}
		
		/* 거래약속 메세지 */
		function dealDateMessage(roomId, dealDate) {
			let sender = memberId;
			let content = "[" + dealDate + "]로 약속을 만들었어요. 꼭 지켜주세요!"
			console.log(content)
			
			stompClient.send('/app/chat/'+roomId, {}, JSON.stringify({
				'sender' : sender,
				'content' : content
			}));
		}
		
		/* 메세지 보내는 함수 */
		function sendMessage(roomId) {
			lastMessageTime(roomId)
			let sender = memberId;
			let content = document.getElementById('chatMessageText').value;
			
			stompClient.send('/app/chat/'+roomId, {}, JSON.stringify({
				'sender' : sender,
				'content' : content
			}));
		}
		
		/* 마지막 채팅이 이루어진 시간 업데이트 함수 */
		function lastMessageTime(roomId) {
			$.ajax({
				url: 'lastMessageTimeUpdate',
				data: {
					roomId: roomId
				},
				success: function() {
					
				},
				error: function(e) {
					console.log(e)
				}
			})
		}
		
		/* 자동 스크롤 */
		function scrollToBottom() {
			let chatArea = $('.chatArea');
			if (chatArea.length > 0) {
			    chatArea.scrollTop(chatArea.prop("scrollHeight"));
			}
		}
		
		/* 채팅방 나가기 함수 */
		function roomLeave(roomId) {
			$.ajax({
				url: 'roomLeave',
				data: {
					roomId
				},
				success: function() {
					alert('채팅방을 나갔습니다.')
				}
			})
		}
		
		/* 결제내역 모달창 닫기 버튼 이벤트 */
		$('.btn_payment_modal_close').click(function() {
			$('#payment_modal').fadeOut();
			document.body.style.overflow = "unset";
		})
		
		/* 충전안내 모달창 닫기 버튼 이벤트 */
		$('.btn_gazeepay_modal_close').click(function() {
			$('#gazeepay_modal').fadeOut();
			document.body.style.overflow = "unset";
		})
		
		/* 내 채팅목록 불러오는 함수 */
		$(document).ready(function() {
			$.ajax({
				url: 'chatList',
				data: {
					memberId : memberId
				},
				success: function(result) {
					$('.myChatList').append(result)
					
					let li = document.querySelectorAll('.chat_list');
					let select = null;

					for (let i = 0; i < li.length; i++) {
						li[i].addEventListener('click', function() {
							
						/* 빨간 점 삭제 */
						const liId = li[i].getAttribute('id');
						let liElement = document.getElementById(liId);
						let chatListElement = liElement.querySelector('.chatList');
						let newMessageElement = chatListElement.querySelector('.newMessage');
						newMessageElement.style.visibility = 'hidden';
						
						const roomId = li[i].getAttribute('value');
							$.ajax ({
								url: 'chatRoomEntry',
								data: {
									roomId: roomId
								},
								success: function(result) {
									getChatHistory(roomId);
									$('.chatRoomEntry').empty();
									$('.chatRoomEntry').append(result)

									/* 채팅전송 엔터키 이벤트 */
									$("#chatMessageText").keyup(function(event) {
										if (event.which == 13) {
									        event.preventDefault();
									        sendMessage(roomId);
									        $(this).val('');
									    }
									})
									
									/* 채팅전송 클릭이벤트 */
									let btn_chatSend = $('.chat_send');
									btn_chatSend.click(function() {
										sendMessage(roomId);
									});
									
									/* 햄버거 토글 메뉴 */
									let burger = $('.menu-trigger');
									burger.each(function(index){
									  let $this = $(this);
									  
									  $this.on('click', function(e){
									    e.preventDefault();
									    $(this).toggleClass('active-' + (index+1));
									    $('#toggle_menu_list').slideToggle();
									  })
									});
									
									/* 토글 메뉴 - 채팅방 나가기 */
									let btn_roomLeave = $("#btn_roomLeave");
									btn_roomLeave.on('click', function() {
										if (!confirm('채팅방을 나가시겠습니까? 모든 채팅방 내용이 삭제됩니다.')) {
											alert("취소")
										} else{
											roomLeave(selectedRoomId);
											location.reload();
										}
									})
									
									/* 토글 메뉴 - 신고하기 */
									let btn_report = $("#btn_report");
									btn_report.on('click', function() {
										console.log('신고하기')
										disconnect(roomId);
									})
									
									/* [판매자]일 때 [판매하기] 버튼을 눌러 결제요청 */
									let btn_sell = $("#btn_sell");
									btn_sell.on('click', function() {
										paymentMessage(roomId);
									})
									
									/* [판매자]가 직거래 시간을 등록했을 때 전송 메세지 */
									let btn_dealDirectDate = $("#btn_dealDirectDate");
									btn_dealDirectDate.on('click', function() {
										const day = document.querySelector("#input_date").value;
										const dateObj = new Date(day);
										const formattedDate = dateObj.toLocaleString('ko-KR', {
											  year: 'numeric',
											  month: 'long',
											  day: 'numeric',
											  weekday: 'long',
											  hour: 'numeric',
											  minute: 'numeric',
											  hour12: true
										});
										dealDateMessage(roomId, formattedDate);
									})
								}
							})
							
							/* 이미 선택된 li(목록)가 있는 상태에서 다른 li를 클릭 시 */
							if (select != null) {
								select.children[0].style.backgroundColor = '#fff';
								select.children[0].style.transition = '0.5s'; 
								$('#toggle_menu_list').hide();
							}
							/* li(목록) 클릭할 때 */
							this.children[0].style.backgroundColor = '#e7e7e7';
							select = this;
							selectedRoomId = this.getAttribute('value');
						})
						
						/* 이미 roomId가 있을 때 */
						if (roomId === li[i].getAttribute('value')) {
							li[i].click();
							let myChatList = document.querySelector('.myChatList');
							/* 해당 채팅방을 맨 위로 */
							myChatList.insertBefore(li[i], myChatList.firstChild);
						}
					}
				}
			})
		})
	})
	
	/* 우편번호 찾기 */
	function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수
                
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("extraAddress").value = extraAddr;
                
                } else {
                    document.getElementById("extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById("postcode").value = data.zonecode;
                document.getElementById("address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("detailAddress").focus();
            }
        }).open();
    }
	
	/* 가지페이 부족할 때 결제 모달창 open */
	function gazeepay(buyerId, productId, dealType) {
		let modal = document.getElementById('gazeepay_modal');
		modal.style.display = 'flex';
		
		let price = 0;
		let balance = 0;
		let lackBalance = 0;
		
		/* 상품 가격 가져오기 */
		$.ajax({
			url: '../product/productOne',
			data: {
				productId: productId
			},
			success: function(result) {
				price = result.price
				let productPrice = document.getElementById('productPrice')
				let formattedPrice = formatNumber(price);
				productPrice.textContent = formattedPrice + '원';
				
				/* 내 보유 가지씨앗 가져오기 */
				$.ajax({
					url: '../transactionHistory/checkBalance',
					data: {
						memberId: buyerId
					},
					success: function(balance) {
						balance = balance;
						let myBalance = document.getElementById('myBalance')
						let formattedMyBalance = formatNumber(balance);
						myBalance.textContent = formattedMyBalance + '원';
						
						lackBalance = price - balance;
						
						/* 부족한 가지씨앗 계산 */
						let lackBalanceSpan = document.getElementById('lackBalance')
						let formattedLackBalance = formatNumber(lackBalance);
						lackBalanceSpan.textContent = formattedLackBalance + '원';
						lackBalanceSpan.value = lackBalance;
					}
				})
			}
		})
	}
	
	/* 결제가 완료되었을 때 */
	function order(roomId, dealType) {
		if (dealType == '직거래') {
			$.ajax({
				url: '../order/orderComplete',
				data: {
					roomId: roomId
				},
				success: function(result) {
					if (result == 1) {
						dealDirectComplete(roomId);
						location.href = "../home/gazeeMain.jsp"
					}
				}
			})
		} else {
			let city = $("#address").val();
			let detailAddress = $("#detailAddress").val();
			let address = city + " " + detailAddress;
			
			$.ajax({
				url: '../order/orderComplete',
				data: {
					roomId: roomId,
					address: address
				},
				success: function(result) {
					if (result == 1) {
						dealDeliveryComplete(roomId);
						location.href = "../home/gazeeMain.jsp"
					}
				}
			})
		}
	}
	
	/* 직거래 거래완료 메세지 */
	function dealDirectComplete(roomId) {
		let sender = memberId;
		let content = "결제완료";
		
		stompClient.send('/app/chat/'+roomId, {}, JSON.stringify({
			'sender' : sender,
			'content' : content
		}));
	}
	
	/* 택배거래 거래완료 메세지 */
	function dealDeliveryComplete(roomId) {
		let sender = memberId;
		let content = "운송장번호";
		
		stompClient.send('/app/chat/'+roomId, {}, JSON.stringify({
			'sender' : sender,
			'content' : content
		}));
	}
	
	/* 숫자 쉼표 */
	function formatNumber(number) {
		return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}
</script>
<style>
	body {
		background-color: #fafafa;
	}
</style>
<title>가지가지</title>
</head>
<body>
<div id="wrap">
	<div id="newMessagePushAlarm"></div>
	<div id="header">
		<jsp:include page="../home/Header.jsp" flush="true"/>
	</div>
	<jsp:include page="../pay/charge.jsp" flush="true"/>
	<div id="gazeepay_modal">
		<div class="gazeepay_modal_body">
			<div class="btn_gazeepay_modal_close">
				<img src="../resources/img/cancle_icon.svg" width="20px" height="20px">
			</div>
			<div>
				<span>가지씨앗 부족합니다.</span><br>
				<span>충전 후 결제해 주세요.</span>
			</div>
			<div class="gazeepay_modal_amount">
				<div class="amount">
					<span>상품금액</span>
					<span id="productPrice"></span>
				</div>
				<div class="amount">
					<span>보유 가지씨앗</span>
					<span id="myBalance"></span>
				</div>
				<hr style="border: 1px solid #e1e1e1; width: 100%;">
				<div class="amount">
					<span>부족한 가지씨앗</span>
					<span id="lackBalance" class="input_charge"></span>
				</div>
			</div>
			<div>
				<button onclick="requestPay(1)" class="btn_charge">신용카드</button>
				<button onclick="requestPay(2)" class="btn_charge">계좌이체</button>
			</div>
		</div>
	</div>
	<div id="payment_modal">
		<div class="payment_modal_body">
			<div class="btn_payment_modal_close">
				<img src="../resources/img/cancle_icon.svg" width="25px" height="25px">
			</div>
			<div class="modal_content"></div>
		</div>
	</div>
	<div id="content_wrap">
		<div id="content">
			<div style="font-size: 26px; font-weight: bold; text-align: left; color:#363636; margin-bottom: 20px; display: flex; align-content: center; gap: 15px;">
				<div id="chatIcon"><img src="../resources/img/icon_chat.svg" style="width: 30px;"></div>
				<div>내 채팅목록</div>
			</div>
			<div class="chat">
				<ul id="toggle_menu_list" class="list-group">
					<li class="list-group-item" id="btn_report" style="border-radius: 10px 10px 0 0;">신고하기</li>
					<li class="list-group-item" id="btn_roomLeave" style="border-radius: 0 0 10px 10px;">채팅방 나가기</li>
				</ul>
				<div class="chatList_wrap">
					<ul class="myChatList"></ul>
				</div>
				<div class="chatRoomEntry">
					대화내역이 없습니다. 대화를 시작하세요!
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../home/Footer.jsp" flush="true"/>
</div>
</body>
</html>