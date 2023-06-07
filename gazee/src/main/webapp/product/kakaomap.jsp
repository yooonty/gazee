<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=585d16ec9470eb99517d6a572cd44a26&libraries=services"></script>
    <script>
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };  

	// 지도를 생성합니다    
	var map = new kakao.maps.Map(mapContainer, mapOption); 

	// 주소-좌표 변환 객체를 생성합니다
	var geocoder = new kakao.maps.services.Geocoder();
	
	function getXY(){
	    var address = $('.search').val(); // address-search 입력 필드의 값 가져오기
	    console.log('주소 값 : ' + address)
	    if (address=='') {
	    	alert("주소를 입력해주세요")
	    }else {
			// 주소로 좌표를 검색합니다
			geocoder.addressSearch(address, function(result, status) {
		    // 정상적으로 검색이 완료됐으면 
		    if (status === kakao.maps.services.Status.OK) {
		
			       var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
					
			       console.log('경도, 위도 : ' + result[0].y+", "+ result[0].x);
			       longitude = result[0].y;
	               latitude = result[0].x;
	                
			       // 결과값으로 받은 위치를 마커로 표시합니다
			       var marker = new kakao.maps.Marker({
			           map: map,
			           position: coords
			       });
			       
			       var detailAddr = '';
			       if (result[0].road_address) {
			           detailAddr += result[0].road_address.address_name;
			       } else if (result[0].address) {
			           detailAddr += result[0].address.address_name;
			       }
			       // 인포윈도우로 장소에 대한 설명을 표시합니다
			       var infowindow = new kakao.maps.InfoWindow({
			           content: '<div class="bAddr">' + detailAddr +  '</div>'
			       });
			       infowindow.open(map, marker);
			       
			    	// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
			       map.setCenter(coords);
			       
			       var infoTitle = document.querySelector('.bAddr');
			       if (infoTitle) {
						var w = infoTitle.offsetWidth + 10;
						var ml = w/2;
						infoTitle.parentElement.style.top = "82px";
						infoTitle.parentElement.style.left = "50%";
						infoTitle.parentElement.style.marginLeft = -ml+"px";
						infoTitle.parentElement.style.width = w+"px";
						infoTitle.parentElement.previousSibling.style.display = "none";
						infoTitle.parentElement.parentElement.style.border = "none";
						infoTitle.parentElement.parentElement.style.background = "unset";
			       }
    		}else{console.log("검색 안됨")}
			})
	    }
	}
	
	// 주소로 좌표를 검색하는 함수입니다
    function searchCoordinateFromAddress(address, callback) {
        var geocoder = new kakao.maps.services.Geocoder();
        geocoder.addressSearch(address, function (latitude, longitude) {
            if (status === kakao.maps.services.Status.OK) {
                callback(result[0].y, result[0].x);
            }
        });
    }
	</script>