		var stompClient = null;
		var connectedRoomIds = [];
		
		window.addEventListener("beforeunload", function() {
			console.log("소켓삭제");
			disconnectWebSocket();
		});

		function subscribeToChatRooms(roomIds) {
			roomIds.forEach(function(roomId) {
				allSocketConnect(roomId);
				connectedRoomIds.push(roomId);
			});
			saveSubscribedRoomIdsToSession(connectedRoomIds);
		}
		
		function allSocketConnect(roomId) {
			// 소켓 생성
			let socket = new SockJS('/gazee/chat/' + roomId);
			stompClient = Stomp.over(socket);
			
		
			stompClient.connect({}, function(frame) {
				stompClient.subscribe('/topic/' + roomId, function(messageOutput) {
					showMessageOutput(JSON.parse(messageOutput.body));
					lastChatMessage(JSON.parse(messageOutput.body));
				}, function(error) {
					console.error('ERROR', error);
					reconnectWebSocket(roomId);
				});
			}, function(error) {
				console.error('ERROR', error);
				reconnectWebSocket(roomId);
			});
		}
		
		function reconnectWebSocket(roomId) {
			setTimeout(function() {
				console.log('WebSocket 재연결 시도');
				allSocketConnect(roomId); // 새로운 웹 소켓 연결을 시도합니다.
			}, 3000); // 일정 시간 후에 연결을 시도합니다. 여기서는 3초로 설정하였습니다.
		}
		
		function saveSubscribedRoomIdsToSession(roomIds) {
			// Ajax를 사용하여 서버에 세션에 저장하는 요청을 보냄
			$.ajax({
				url: '../chat/saveSubscribedRoomIds',
				type: 'POST',
				data: JSON.stringify(roomIds), // JSON.stringify를 사용하여 데이터를 JSON 형식으로 변환
				contentType: 'application/json', // 데이터 형식을 JSON으로 지정
				success: function(response) {
					console.log('Subscribed roomIds saved to session');
				},
				error: function(error) {
					console.error('Failed to save subscribed roomIds to session');
					console.log(error)
				}
			});
		}
		
		function disconnectWebSocket() {
		    if (stompClient !== null) {
		        stompClient.disconnect();
		    }
		}
		
		/* 결제창 modal open */
		function payment() {
			$('#payment_modal').fadeIn();
			let modal = document.getElementById('payment_modal');
			modal.style.display = 'flex';
			$.ajax({
				url: 'chatPaymentModal',
				data: {
					roomId: selectedRoomId
				},
				success: function(result) {
					$('.modal_content').empty();
					$('.modal_content').append(result)
					document.body.style.overflow = "hidden";
				}
			})
		}
		
		/* 메세지 출력 함수 */
		function showMessageOutput(messageOutput) {
			let response = document.getElementById('chatLog'+messageOutput.roomId)
			
			if (response == null) {
				newChatMessageBadge(messageOutput);
				newChatMessagePush(messageOutput);
			} else {
				let responseId = response.id;
				let roomId = responseId.substring(7);
			
				if (String(messageOutput.roomId) == roomId) { 
					if (messageOutput.sender == memberId) {
						if (messageOutput.content == '결제요청') {
							paymentMyChat(messageOutput);
						} else if (messageOutput.content == '결제완료') {
							direct_paymentCompleteMyChat(messageOutput);
						} else if (messageOutput.content == '운송장번호') {
							console.log('운송장번호 입력해주세요')
						} else {
							myChat(messageOutput);
						}
					} else {
						if (messageOutput.content == '결제요청') {
							paymentPartnerChat(messageOutput);
						} else if (messageOutput.content == '결제완료') {
							direct_paymentCompletePartnerChat(messageOutput);
						} else if (messageOutput.content == '운송장번호') {
							console.log('운송장번호 입력해주세요')
						} else {
							partnerChat(messageOutput);
						}
					}
				}
			}
		}
		
		/* 결제요청 메세지 - 내가 보낸 메세지 */
		function paymentMyChat(messageOutput) {
			let response = document.getElementById('chatLog'+messageOutput.roomId)
		
			let temp = document.createElement('div');
			temp.classList.add('message');
			temp.classList.add('chat_me');
							
			let innerDiv = document.createElement('div');
			innerDiv.id = 'paymentMessage_wrap';
							
			let span = document.createElement('span');
			span.textContent = '판매자가 결제를 요청합니다.';
							
			let btn_payment = document.createElement('button');
			btn_payment.id = 'btn_payment';
			btn_payment.textContent = '결제하기';
							
			innerDiv.appendChild(span);
			innerDiv.appendChild(btn_payment);
			temp.appendChild(innerDiv);
							
			let timeDiv = document.createElement('div');
			timeDiv.classList.add('messageTime');
			timeDiv.textContent = messageOutput.time;
							
			temp.appendChild(timeDiv)
			response.appendChild(temp);
			scrollToBottom();
		}
		
		/* 결제요청 메세지 - 내가 받은 메세지 */
		function paymentPartnerChat(messageOutput) {
			let response = document.getElementById('chatLog'+messageOutput.roomId)	
	
			let temp = document.createElement('div');
			temp.classList.add('message');
			temp.classList.add('chat_partner');
							
			let profileDiv = document.createElement('div');
			profileDiv.classList.add('chatPartnerProfile');
			temp.appendChild(profileDiv);
							
			let innerDiv = document.createElement('div');
			innerDiv.id = 'paymentMessage_wrap';
							
			let span = document.createElement('span');
			span.textContent = '판매자가 결제를 요청합니다.';
							
			let btn_payment = document.createElement('button');
			btn_payment.id = 'btn_payment';
			btn_payment.textContent = '결제하기';
			btn_payment.style.cursor = 'pointer';
			btn_payment.addEventListener('click', payment);
							
			innerDiv.appendChild(span);
			innerDiv.appendChild(btn_payment);
			temp.appendChild(innerDiv);
							
			let timeDiv = document.createElement('div');
			timeDiv.classList.add('messageTime');
			timeDiv.textContent = messageOutput.time;
							
			temp.appendChild(timeDiv)
			response.appendChild(temp);
			scrollToBottom();
		}
		
		/* 직거래일 때 결제완료 메세지 - 내가 보낸 메세지 */
		function direct_paymentCompleteMyChat(messageOutput) {
			let response = document.getElementById('chatLog'+messageOutput.roomId)
		
			let temp = document.createElement('div');
			temp.classList.add('message');
			temp.classList.add('chat_me');
							
			let innerDiv = document.createElement('div');
			innerDiv.id = 'paymentMessage_wrap';
							
			let span = document.createElement('span');
			span.textContent = '결제가 완료되었습니다!';
			
			let span2 = document.createElement('span');
			span2.textContent = '마이페이지에서 결제내역을 확인하세요.';
							
			let btn_myPage = document.createElement('button');
			btn_myPage.id = 'btn_myPage';
			btn_myPage.textContent = '확인하기';
			btn_myPage.style.cursor = 'pointer';
			btn_myPage.addEventListener('click', myPage);
							
			innerDiv.appendChild(span);
			innerDiv.appendChild(span2);
			innerDiv.appendChild(btn_myPage);
			temp.appendChild(innerDiv);
							
			let timeDiv = document.createElement('div');
			timeDiv.classList.add('messageTime');
			timeDiv.textContent = messageOutput.time;
							
			temp.appendChild(timeDiv)
			response.appendChild(temp);
			scrollToBottom();
		}
		
		/* 직거래일 때 결제완료 메세지 - 내가 받은 메세지 */
		function direct_paymentCompletePartnerChat(messageOutput) {
			let response = document.getElementById('chatLog'+messageOutput.roomId)	
	
			let temp = document.createElement('div');
			temp.classList.add('message');
			temp.classList.add('chat_partner');
							
			let profileDiv = document.createElement('div');
			profileDiv.classList.add('chatPartnerProfile');
			temp.appendChild(profileDiv);
							
			let innerDiv = document.createElement('div');
			innerDiv.id = 'paymentMessage_wrap';
							
			let span = document.createElement('span');
			span.textContent = '결제가 완료되었습니다!';
			
			let span2 = document.createElement('span');
			span2.textContent = '마이페이지에서 결제내역을 확인하세요.';
							
			let btn_myPage = document.createElement('button');
			btn_myPage.id = 'btn_myPage';
			btn_myPage.textContent = '확인하기';
			btn_myPage.style.cursor = 'pointer';
			btn_myPage.addEventListener('click', myPage);
							
			innerDiv.appendChild(span);
			innerDiv.appendChild(span2);
			innerDiv.appendChild(btn_myPage);
			temp.appendChild(innerDiv);
							
			let timeDiv = document.createElement('div');
			timeDiv.classList.add('messageTime');
			timeDiv.textContent = messageOutput.time;
							
			temp.appendChild(timeDiv)
			response.appendChild(temp);
			scrollToBottom();
		}
		
		/* 택배거래일 때 결제완료 메세지 - 내가 보낸 메세지 */
		function delivery_paymentCompleteMyChat(messageOutput) {
			let response = document.getElementById('chatLog'+messageOutput.roomId)
		
			let temp = document.createElement('div');
			temp.classList.add('message');
			temp.classList.add('chat_me');
							
			let innerDiv = document.createElement('div');
			innerDiv.id = 'paymentMessage_wrap';
							
			let span = document.createElement('span');
			span.textContent = '결제가 완료되었습니다!';
			
			let span2 = document.createElement('span');
			span2.textContent = '마이페이지에서 운송장번호를 입력해 주세요!';
							
			let btn_trackingNo = document.createElement('button');
			btn_trackingNo.id = 'btn_trackingNo';
			btn_trackingNo.textContent = '입력하러가기';
							
			innerDiv.appendChild(span);
			innerDiv.appendChild(span2);
			innerDiv.appendChild(btn_trackingNo);
			temp.appendChild(innerDiv);
							
			let timeDiv = document.createElement('div');
			timeDiv.classList.add('messageTime');
			timeDiv.textContent = messageOutput.time;
							
			temp.appendChild(timeDiv)
			response.appendChild(temp);
			scrollToBottom();
		}
		
		/* 택배거래일 때 결제완료 메세지 - 내가 받은 메세지 */
		function delivery_paymentCompletePartnerChat(messageOutput) {
			let response = document.getElementById('chatLog'+messageOutput.roomId)	
	
			let temp = document.createElement('div');
			temp.classList.add('message');
			temp.classList.add('chat_partner');
							
			let profileDiv = document.createElement('div');
			profileDiv.classList.add('chatPartnerProfile');
			temp.appendChild(profileDiv);
							
			let innerDiv = document.createElement('div');
			innerDiv.id = 'paymentMessage_wrap';
							
			let span = document.createElement('span');
			span.textContent = '결제가 완료되었습니다!';
			
			let span2 = document.createElement('span');
			span2.textContent = '마이페이지에서 운송장번호를 입력해 주세요!';
							
			let btn_trackingNo = document.createElement('button');
			btn_trackingNo.id = 'btn_trackingNo';
			btn_trackingNo.textContent = '입력하러가기';
			btn_trackingNo.style.cursor = 'pointer';
			btn_trackingNo.addEventListener('click', payment);
							
			innerDiv.appendChild(span);
			innerDiv.appendChild(span2);
			innerDiv.appendChild(btn_trackingNo);
			temp.appendChild(innerDiv);
							
			let timeDiv = document.createElement('div');
			timeDiv.classList.add('messageTime');
			timeDiv.textContent = messageOutput.time;
							
			temp.appendChild(timeDiv)
			response.appendChild(temp);
			scrollToBottom();
		}
		
		/* 일반 메세지 - 내가 보낸 메세지 */
		function myChat(messageOutput) {
			let response = document.getElementById('chatLog'+messageOutput.roomId)
		
			let temp = document.createElement('div');
			temp.classList.add('message');
			temp.classList.add('chat_me');
							
			let innerDiv = document.createElement('div');
			let span = document.createElement('span');
			span.textContent = messageOutput.content;
							
			innerDiv.appendChild(span);
			temp.appendChild(innerDiv);
							
			let timeDiv = document.createElement('div');
			timeDiv.classList.add('messageTime');
			timeDiv.textContent = messageOutput.time;
							
			temp.appendChild(timeDiv)
			response.appendChild(temp);
			scrollToBottom();
		}
		
		/* 일반 메세지 - 내가 받은 메세지 */
		function partnerChat(messageOutput) {
			let response = document.getElementById('chatLog'+messageOutput.roomId)
		
			let temp = document.createElement('div');
			temp.classList.add('message');
			temp.classList.add('chat_partner');
							
			let profileDiv = document.createElement('div');
			profileDiv.classList.add('chatPartnerProfile');
			temp.appendChild(profileDiv);
							
			let innerDiv = document.createElement('div');
			let span = document.createElement('span');
			span.textContent = messageOutput.content;
							
			innerDiv.appendChild(span);
			temp.appendChild(innerDiv);
							
			let timeDiv = document.createElement('div');
			timeDiv.classList.add('messageTime');
			timeDiv.textContent = messageOutput.time;
							
			temp.appendChild(timeDiv)
			response.appendChild(temp);
			scrollToBottom();
		}
		
		/* 자동 스크롤 */
		function scrollToBottom() {
			let chatArea = $('.chatArea');
			if (chatArea.length > 0) {
			    chatArea.scrollTop(chatArea.prop("scrollHeight"));
			}
		}
		
		/* 마지막 채팅을 채팅 목록에서 계속 로드하는 함수 */
		function lastChatMessage(messageOutput) {
			let li = document.querySelectorAll('.chat_list');
			for (var i = 0; i < li.length; i++) {
				const liValue = li[i].getAttribute('value');
				if (messageOutput.roomId == liValue) {
				const liId = li[i].getAttribute('id');
					$("#" + liId + " .recentMessage").empty();
					$("#" + liId + " .recentMessageDate").empty();
					$("#" + liId + " .recentMessage").append(messageOutput.content);
					$("#" + liId + " .recentMessageDate").append(messageOutput.time);
					$(".myChatList").prepend($("#" + liId));
				}
			}
		}
		
		/* 새로운 메세지 뱃지 알람 */
		function newChatMessageBadge(messageOutput) {
			let li = document.querySelectorAll('.chat_list');
			for (var i = 0; i < li.length; i++) {
				const liValue = li[i].getAttribute('value');
				if (messageOutput.roomId == liValue) {
					const liId = li[i].getAttribute('id');
					let liElement = document.getElementById(liId);
					let chatListElement = liElement.querySelector('.chatList');
					let newMessageElement = chatListElement.querySelector('.newMessage');
					newMessageElement.style.visibility = 'visible';
				}
			}
		}
		
		/* 새로운 메세지 Push알람 */
		function newChatMessagePush(messageOutput) {
			var x = document.getElementById("newMessagePushAlarm");
			let memberId = messageOutput.sender;
			
			if (x.innerHTML !== null) {
				x.innerHTML = "";
			}
			
			$.ajax({
				url: '../member/searchOne',
				data: {
					memberId: memberId
				},
				success: function(result) {
					console.log(result);
					let nickname = result.nickname;
					let senderDiv = document.createElement('div');
					senderDiv.textContent = nickname;
					x.append(senderDiv);
					let contentDiv = document.createElement('div');
					contentDiv.textContent = "새로운 메세지가 도착했습니다.";
					x.append(contentDiv);
				}
			})
			x.className = "show";
			setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
		}
		
		/* 결제내역 확인하러 마이페이지로 이동 */
		function myPage() {
			location.href = "../home/gazeeMain.jsp";
		}