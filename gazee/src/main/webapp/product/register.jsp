<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%  %>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
<link rel="stylesheet" href="../resources/css/product-register.css" type="text/css">
<script type="text/javascript" src="../resources/js/jquery-3.6.4.js"></script>
<script>
let latitude = 0.0000000000;
let longitude = 0.0000000000;
let dealDirect = 0;
let dealDelivery = 0;

$(function() {
	
	$('.delivery').click(function(){
		if (dealDelivery == 1) {
			dealDelivery = 0;
		}else {
			dealDelivery = 1
		}
		console.log("배송여부 : "+dealDelivery);
	})
	$('.direct').click(function() {
		if ($('#map').css('visibility') == 'hidden') {
			dealDirect = 1;
			$('#map').css('visibility', 'visible');
			$('.address').css('visibility', 'visible');
			$('.prd-info').css('width', '500px');
		}else {	//카카오맵 보이기
			dealDirect = 0;
			latitude = 0.0000000000;
			longitude = 0.0000000000;
			$('#map').css('visibility', 'hidden');
			$('.address').css('visibility', 'hidden');
			$('.prd-info').css('width', '100%');
		}
		console.log("직거래 여부 : "+dealDirect);
	})
	$('.save').click(function(){
		var memberId = 1;
		var category = $('.category').val();
		var productName = $('.name').val();
		var productContent = $('.product-content').val();
		var price = $('.price').val();
		var save = $(this).val(); // 버튼의 value 값 가져오기
		console.log("latitude" + latitude);
		console.log("longitude" + longitude);
		if(save == 1 && dealDirect==1 && longitude<1){
			alert("직거래 장소를 정해주세요.")
		}else if(save == 1&&(category==null || productName==null || productContent==null || price==null)){
			alert("필수값을 입력해주세요.")
		}else if(save == 1 && dealDirect==0 && dealDelivery==0){
			alert("거래방식을 정해주세요.")
		}
		else{
			$.ajax({
				url : "register",
				data : {
					memberId : 1,
					category : category,
					productName : productName,
					productContent : productContent,
					price : price,
					dealDirect : dealDirect,
					directAddressx : longitude,
					directAddressy : latitude,
					dealDelivery : dealDelivery,
					temporary : save,
				},
				success : function(x) {
					if(save==1) {
						alert("판매글을 등록했습니다.")
					}else if(save==0) {
						alert("글을 저장했습니다.")
					}
				}
			})
		}
	})
})
	
</script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="../home/Header.jsp" flush="true"/>
	<div id="wrap">
		<div id="main">
			<div id="title">내 물건 팔기</div>
			<div id="register_table">
			<span id="map"></span>
				<table>
					<tr class="each-row">
						<td class="attribute">제목</td>
						<td><input class="prd-info name" type="text">
						</td>
						
					</tr>
					<tr class="each-row">
						<td class="attribute">가격</td>
						<td><input class="prd-info price" type="number" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"></td>
						
					</tr>
					<tr class="each-row">
						<td class="attribute">카테고리</td>
						<td><select class="prd-info category" name="category" >
								<option value="의류">의류</option>					<option value="잡화">잡화</option>
								<option value="디지털 기기">디지털 기기</option>		<option value="생활가전">생활가전</option>
								<option value="가구/인테리어">가구/인테리어</option>	<option value="뷰티/미용">뷰티/미용</option>
								<option value="스포츠/레저">스포츠/레저</option>		<option value="도서">도서</option>
								<option value="반려동물용품">반려동물용품</option>		<option value="취미/게임/음반">취미/게임/음반</option>
								<option value="생활/주방">생활/주방</option>			<option value="기타">기타</option>
						</select></td>
						<td></td>

					</tr>
					<tr class="each-row">
						<td class="attribute">거래방법</td>
						<td class="deal">
							<div>
								<button class="delivery deal-type">
									<span class="material-symbols-outlined">local_shipping</span><br>
									택배거래</button>
								<button class="direct deal-type">
									<span class="material-symbols-outlined">partner_exchange</span><br>
									직거래</button>
							</div>
							
							<div class="address">
								직거래 장소 정하기<br>
								<input class="search" placeholder="직거래 장소 입력">
								<button onclick="getXY()"><span class="material-symbols-outlined">search</span></button>
							</div>
						</td>
					</tr>
					<tr class="content-row">
						<td class="attribute">내용</td>
						<td class="content"><textarea class="product-content"> </textarea></td>
					</tr>
					<tr class="each-row">
						<td class="attribute">사진첨부</td>
						<td><input type="file"></td>
					</tr>
				</table>
			</div>
			<div>
				<button class="save" value="1">저장</button>
				<button class="save" value="0">임시저장</button>
			</div>
		</div>
	</div>
		<jsp:include page="../home/Footer.jsp" flush="true"/>
		<jsp:include page="kakaomap.jsp" flush="true"/>
	<div>
</div> 
</body>
</html>