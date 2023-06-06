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
	margin-top: 50px;
}

#chatList {
	width: 300px;
	height: 80px;
	background: #693FAA;
	border-radius: 20px;
	font-size: large;
	color: white;
	border: none;
	cursor: pointer;
}

.btn_product {
	border: none;
	background: #e8e8e8;
	height: 50px;
	width: 120px;
	margin: 5px;
	border-radius: 5%;
	cursor: pointer;
}

.modal {
	display: none;
	position: absolute;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.5);
	z-index: 9999;
}

.modal-content {
	position: absolute;
	top: 50%;
	left: 50%;
	width: 400px;
	height: 400px;
	padding: 40px;
	text-align: center;
	background-color: rgb(255, 255, 255);
	border-radius: 10px;
	box-shadow: 0 2px 3px 0 rgba(34, 36, 38, 0.15);
	transform: translateX(-50%) translateY(-50%);
	z-index: 10000;
}

.close {
	color: #aaa;
	float: right;
	font-size: 28px;
	font-weight: bold;
	cursor: pointer;
}

.close:hover, .close:focus {
	color: black;
	text-decoration: none;
	cursor: pointer;
}

#map {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	width: 300px;
	height: 300px;
}
.bAddr {
	position:absolute;
	display: block;
    background: #50627F;
    color: #fff;
    text-align: center;
    height: 24px;
    line-height:22px;
    border-radius:4px;
    padding:0px 10px;
    width: 150px;
    white-space: normal;
    height: max-content;
    word-wrap: break-word;
}

#directclick {
	position: absolute;
	margin: 50px;
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: 14px;
	top: -10px;
	margin: 50px;
	width: 240px;
	height: 30px;
	background: #E2DAED;
	borde: 1px solid #dbdbdb;
	border-radius: 10px;
}

#directclick:after {
	border-top: 0px solid transparent;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
	border-bottom: 10px solid #E2DAED;
	content: "";
	position: absolute;
	top: -10px;
	left: 67px;
}

#btn_directMapRemove {
	position: absolute;
	left: 220px;
	top: 10px;
	width: 10px;
	height: 10px;
	cursor: pointer;
}

#show_status {
	background: #693FAA;
	border-radius: 10px;
	padding: 3px 20px;
	color: #fff;
	font-size: 14px
}

#order_status {
	display: flex;
	gap: 5px;
	flex-direction: column;
	align-items: center;
}

#seller_info {
	padding: 10px;
	display: flex;
	flex-flow: column;
	flex-direction: row;
	justify-content: center;
	align-items: center;
	gap : 5px;
}

#nickreport {
	display: flex;
	flex-flow: column;
	padding: 10px;
	align-items: flex-start;
	gap: 5px;
}

.map-trigger {
	cursor: pointer;
}
</style>
<script type="text/javascript">
$(function() {
		let directclick = document.getElementById("directclick");
		
		if (directclick) {
			document.getElementById("btn_directMapRemove").addEventListener("click", function() {
			  document.getElementById("directclick").style.visibility = "hidden";
			});
		}

    $.ajax({
		url : "imgslide",
		data:{
			productId : ${bag.productId}
		},
		success : function(res) {
			$('#imgslide').append(res)
		}
	})
	
	
	    
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
                  url: 'S3ProductDelete',
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
		
	//이미지 클릭 시 모달 창 열기
	var mapTrigger = document.getElementsByClassName("map-trigger");
	var mapModal = document.getElementById("mapModal");
	var closeModal = document.getElementsByClassName("close")[0];
	for (var i = 0; i < mapTrigger.length; i++) {
	    mapTrigger[i].addEventListener("click", function() {
	        mapModal.style.display = "block";
	
	     // 지도 크기 재조정
	        map.relayout();
	        var newCenter = new kakao.maps.LatLng(${bag.directAddressy}, ${bag.directAddressx});
	        map.setCenter(newCenter);
	        
	    });
	}
	
	// 모달 창 닫기
	closeModal.addEventListener("click", function() {
	    mapModal.style.display = "none";
	});
	window.addEventListener("click", function(event) {
	    if (event.target === mapModal) {
	        mapModal.style.display = "none";
	        
	    }
	});
</script>
</head>
<body>
<div id="mapModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
			<div class="map_wrap">
				<div id="map"></div>
			</div>
		</div>
</div>
	<table>
		<tr>
			<td rowspan="6" style="width: 500px; height: 500px;"><div id="imgslide"></div></td>
			<td id="order_status">
					<%
						String order = (String)request.getAttribute("order");
						if(order.equals("null")) {
					%>
						<div id="show_status">판매중</div>
					<%
						} else if (order.equals("yet")) {
					%>
						<div id="show_status">거래중</div>
					<%
						} else {
					%>
						<div id="show_status">거래완료</div>
					<%
						}
					%>
			
			<div>${bag.category}</div>
			</td>
		</tr>
		<tr>
			<td style="font-weight: 900; font-size: xx-large;"><div>
					${bag.productName}</div></td>
		</tr>
		<tr>
			<td><div style="position: relative;">
					<c:if test="${bag.dealDirect == 1}">
						<div id="directclick"><img src="../resources/img/cancel_icon.svg" id="btn_directMapRemove"> 클릭하시면 지도가 보여요!</div>
						<img height="30px" src="../resources/img/direct.png" class="map-trigger">
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
			<td >
			<div id="seller_info"> <img src="http://zurvmfyklzsa17604146.cdn.ntruss.com/${userProfileImg}?type=f&w=50&h=50" style="width: 70px; height: 70px; border-radius: 20px;">
				<div id="nickreport"> <div style="font-size: 20px; font-weight: bold;">${nickname}</div> 
				<div style=" color: red;">신고횟수
					${bag3.count}회</div></div></div>
			</td>
			<td><button id="chatList">채팅 목록 보기</button></td>

		</tr>
	</table>
</body>
<script type="text/javascript">
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 

mapOption = {
    center: new kakao.maps.LatLng(${bag.directAddressy}, ${bag.directAddressx}), // 지도의 중심좌표
    level: 3 // 지도의 확대 레벨
};  


//지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption); 

//주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();


searchDetailAddrFromCoords(map.getCenter(), function(result, status) {
 if (status === kakao.maps.services.Status.OK) {
 	var detailAddr = '';
 	if (result[0].road_address) {
 	    detailAddr += result[0].road_address.address_name ;
 	} else if (result[0].address) {
 	    detailAddr += result[0].address.address_name ;
 	}
 	var marker = new kakao.maps.Marker(), // 중심 위치를 표시할 마커입니다
	infowindow = new kakao.maps.InfoWindow({zindex:1}); // 중심 위치에 대한 주소를 표시할 인포윈도우입니다
     var content = '<div class="bAddr">' + detailAddr + '</div>';

     // 중심 위치에 표시합니다 
     marker.setPosition(map.getCenter());
     marker.setMap(map);

     // 중심 위치에 대한 법정동 상세 주소정보를 표시합니다
     infowindow.setContent(content);
     infowindow.open(map, marker);
     
     var infoTitle = document.querySelector('.bAddr');
     if (infoTitle) {
		var w = infoTitle.offsetWidth + 10;
     var ml = w/2;
     infoTitle.parentElement.style.top = "82px";
     infoTitle.parentElement.style.left = "0%";
     infoTitle.parentElement.style.marginLeft = -ml+"px";
     infoTitle.parentElement.style.width = w+"px";
     infoTitle.parentElement.previousSibling.style.display = "none";
     infoTitle.parentElement.parentElement.style.border = "0px";
     infoTitle.parentElement.parentElement.style.background = "unset";
	}
 }   
});


function searchAddrFromCoords(coords, callback) {
 // 좌표로 행정동 주소 정보를 요청합니다
 geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);         
}

function searchDetailAddrFromCoords(coords, callback) {
 // 좌표로 법정동 상세 주소 정보를 요청합니다
 geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
}

</script>
</html>