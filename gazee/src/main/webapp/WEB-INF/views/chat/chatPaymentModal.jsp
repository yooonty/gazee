<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div>
	<div style="display: flex;">
		<div class="modal_productImg"></div>
		<div class="modal_productInfo">
			<div>
				<p>${productVO.productName}</p>
			</div>
			<div style="font-size: 30px; font-weight: bold; margin-bottom: 5px;">
				<p>${priceDec}원</p>
			</div>
			<div
				style="display: flex; justify-content: space-between; font-size: 14px; text-align: center; color: #808080;">
				<div style="width: 20%; padding: 10px; border: 1px solid #A1A1A1; border-radius: 10px;">
					<p>${chatVO.dealType}</p>
				</div>
				<%
					String dealType2 = (String)request.getAttribute("dealType");
					if (dealType2.equals("직거래")) {
				%>
				<div style="padding: 10px 20px; border: 1px solid #A1A1A1; border-radius: 10px;">
					<p>${dealDirectDate} 거래예정</p>
				</div>
				<%
					}
				%>
			</div>
			<div style="display: flex; justify-content: space-between; margin: 5px 0;">
				<div>
					<p style="color: #808080;">보유 가지 씨앗</p>
				</div>
				<div>
					<p style="font-weight: bold; font-size: 18px;">${balanceDec} 원</p>
				</div>
			</div>
			<div>
				<p style="font-weight: bold; font-size: 18px; text-align: right; color: #808080;">- ${priceDec} 원</p>
			</div>
			<hr style="border: 1px solid #e1e1e1; width: 100%;">
			<%
				int amount = (Integer)request.getAttribute("amount");
				if (amount >= 0) {
			%>
			<div style="display: flex; justify-content: space-between;">
				<div>
					<p style="color: #808080;">남는 가지 씨앗</p>
				</div>
				<div>
					<p style="font-weight: bold; font-size: 18px;">${amountDec} 원</p>
				</div>
			</div>
				<%
					String order = (String)request.getAttribute("order");
					if (order.equals("null")) {
				%>
					<button id="btn_finalPayment" onclick="order(${chatVO.roomId}, ${productVO.productId}, '${dealType}')">결제하기</button>
				<%
					} else {
				%>
					<button id="btn_finalPayment" onclick="orderDone()">결제하기</button>
				<%
					}
				%>
			<%
				} else {
			%>
			<div style="display: flex; justify-content: space-between;">
				<div>
					<p style="color: #808080;">부족한 가지 씨앗</p>
				</div>
				<div>
					<p style="font-weight: bold; font-size: 18px;">- ${amountDec} 원</p>
				</div>
			</div>
				<%
					String order = (String)request.getAttribute("order");
					if (order.equals("null")) {
				%>
					<button id="btn_finalPayment" onclick="gazeepay('${chatVO.buyerId}', ${productVO.productId}, '${dealType}')">결제하기</button>
				<%
					} else {
				%>
					<button id="btn_finalPayment" onclick="orderDone()">결제하기</button>
				<%
					}
				%>
			<%
				}
			%>
			<div>
				<p style="color: #693FAA; font-size: 14px;">결제시 가지 씨앗에서 ${priceDec} 원 차감됩니다.</p>
			</div>
		</div>
	</div>
</div>
<%
	String dealType3 = (String)request.getAttribute("dealType");
	if (dealType3.equals("택배거래")) {
%>
<div style="margin-top: 20px; padding: 0 20px;">
	<hr style="border: 1px solid #e1e1e1; width: 100%;">
	<div style="display: flex; flex-flow: column; text-align: left;">
		<span style="font-size: 18px; font-weight: bold; margin: 10px 0;">배송지 입력</span>
		<div>
			<input type="text" id="postcode" class="address_input" placeholder="우편번호">
			<input type="button" onclick="execDaumPostcode()" value="우편번호 찾기" class="btn_address"><br>
		</div>
		<div style="display: flex; gap: 5px;">
			<input type="text" id="address" class="address_input" placeholder="주소" style="width: 40%;">
			<input type="text" id="detailAddress" class="address_input" placeholder="상세주소" style="width: 40%;">
			<input type="text" id="extraAddress" placeholder="참고항목" style="display: none;">
		</div>
	</div>
</div>
<%
	}
%>
<div style="width: 100%; margin-top: 20px; padding: 0 20px;">
	<hr style="border: 1px solid #e1e1e1; width: 100%;">
	<div style="margin-top: 20px; text-align: left;">
		<span style="font-size: 18px; font-weight: bold;">결제 안내사항</span>
		<div style="width: 100%; display: flex; flex-flow: column; font-size: 14px; margin: 10px 0 0 0;">
			<span style="color: #808080">결제 후 결제하신 대금은 정산이 이루어지기 전까지 가지페이가 보관하며, 확정 이후에 판매자에게 정산이 이루어집니다.</span>
			<span style="color: red;">정산 이후에는 환불이 불가하오니 신중히 결정하시기 바랍니다.</span>
		</div>
	</div>
</div>