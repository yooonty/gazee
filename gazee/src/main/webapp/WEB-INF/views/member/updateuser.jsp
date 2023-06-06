<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700;900&display=swap" rel="stylesheet">
<link href="../resources/css/UpdateUser.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../resources/js/jquery-3.6.4.js"></script>
<script type="text/javascript">
	$(function() {
		//작성
		// 05-17 00:25 추가
			$('#btn_Img').click(function() {				
			
			var formData = new FormData($("#uploadForm")[0]);
			
			$.ajax({
				url: "profile",
				type: "POST",
				data: formData,
				cache: false,
				contentType: false,
				processData: false,
				success: function(response) {
					// Handle success response
					console.log("Upload successful!");
				},
				error: function(jqXHR, textStatus, errorThrown) {
					// Handle error response
					console.error("Upload failed: " + errorThrown);
				}
			});
		   })
		
		  $('#b1').click(function() {
				var id2 = $('#id').val();
				var pw2 = $('#pw').val();
				var nickname2 = $('#nickname').val();
				
				if (id2 != '') {
					if (pw2 != '') {
						if (email != '') {
								$.ajax({
									url:"updateuser",
									type:'post',
									data:{
										id : id2,
										pw : pw2 ,
										nickname : nickname2
									},
									success : function(data) {
										if (data.id == '없음') {
											alert("회원수정 오류");
										} else {
											alert("회원수정 성공");
											"redirect:/"
										}
									},//success
									error: function() {
										alert("서버연결 실패")
									}
								})//ajax
						}else{
							alert("닉네임을 입력해주세요");
						}
						
					}else{
						alert("비밀번호를 입력해주세요");
					}
				}else{
					alert("아이디를 입력해주세요");
				}
			})
			 $('#b2').click(function() {
				var id2 = $('#id2').val();
				var pw2 = $('#pw2').val();
				
				if (id2 != '') {
				if (pw2 != '') {
					$.ajax({
						url:"delete",
						data:{
							id : id2,
							pw : pw2
						},
						success: function(res) {		
							 if (res == '탈퇴실패') {
								alert("가지탈퇴 오류");							
							} else {
								alert('ㅠㅠ가지를 이용해주셔서 감사합니다ㅠㅠ');
								location.href="../member/member.jsp"
							}
						},//success
					})//ajax
					}else{
						alert("회원탈퇴 할 아이디의 비밀번호를 입력해주세요")
					}
				}else{
					alert("회원탈퇴 할 아이디를 입력해주세요")
				}		
			}); 
			
			$('#btn_Img').click(function(){
				uploadFiles();
			})
	}) 
	function nickCheck() {	
	 var nickname = $('#nickname').val()
		 if ($('#nickname').val() != '') {				
		        // 아이디를 서버로 전송 > DB 유효성 검사 > 결과 반환받기
		        $.ajax({		   					
		            type: 'GET',
		            url: 'idcheck',
		            data: {
		            	nickname: nickname
		            	},
		            success: function(result) {	
		            	  $('#result').text(result);
		            	  $('#result').attr('color', '#693FAA');
		            	  
		            },
		            error: function(a, b, c) {
		                console.log(a, b, c);
		            }		   					
		        });		   				
		    } else {
		        alert('닉네임을 입력하세요.');
		        $('#nickname').focus();
		    }	
	}; 
</script>
<style>
#btn_leave{
	width: 150px;
	height: 40px;
	background : #693FAA;
	color: #FFF;
	gap : 10px;
}
#btn_updateUser{
	background: #F6F6F6; 
	width: 150px; 
	height: 40px; 
	font-style: #6f42c1
}
button{
	border-color: #693FAA;
	border-radius: 3px;

}
</style>
	<div id="content_wrap">
		<div id="content">
			<div id="updateUserBox">
				<div class="head">
					<span class="star"></span>
				</div>
					<div class="input_signUp">
						<label class="nametag">닉네임<span class="star">*</span></label>
						<input type="text" id="nickname" class="form-control" placeholder="닉네임">
						<button type="button" id="Nick-Check-Btn" onclick="nickCheck()">중복확인</button>
						<input type="hidden" id ="NicknameCheck"/>
					</div>
				 <div class="head"></div>
		   </div>
				 <br/>
				 <div id="btnBox">
					<button type="submit" id="btn_leave">회원탈퇴</button>
					<button  type="submit" id="btn_updateUser">회원정보 수정</button>
				</div>
		</div>
	</div>

