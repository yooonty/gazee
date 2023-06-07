package com.multi.gazee.order;

import java.sql.Timestamp;

public class OrderVO {
	private int no;
	private String transactionId;
	private int roomId;
	private String dealType;
	private int productId;
	private String sellerId;
	private String buyerId;
	private int sellerConfirm;
	private int buyerConfirm;
	private int completeStatus;
	private int setStatus;
	private Timestamp paymentTime;
	private String trackingNo;
	private String deliveryCom;
	private String address;
	private int canceled;
	private String cancelTransactionId;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public int getSellerConfirm() {
		return sellerConfirm;
	}
	public void setSellerConfirm(int sellerConfirm) {
		this.sellerConfirm = sellerConfirm;
	}
	public int getBuyerConfirm() {
		return buyerConfirm;
	}
	public void setBuyerConfirm(int buyerConfirm) {
		this.buyerConfirm = buyerConfirm;
	}
	public int getCompleteStatus() {
		return completeStatus;
	}
	public void setCompleteStatus(int completeStatus) {
		this.completeStatus = completeStatus;
	}
	public int getSetStatus() {
		return setStatus;
	}
	public void setSetStatus(int setStatus) {
		this.setStatus = setStatus;
	}
	public Timestamp getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Timestamp paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getTrackingNo() {
		return trackingNo;
	}
	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}
	public String getDeliveryCom() {
		return deliveryCom;
	}
	public void setDeliveryCom(String deliveryCom) {
		this.deliveryCom = deliveryCom;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getCanceled() {
		return canceled;
	}
	public void setCanceled(int canceled) {
		this.canceled = canceled;
	}
	public String getCancelTransactionId() {
		return cancelTransactionId;
	}
	public void setCancelTransactionId(String cancelTransactionId) {
		this.cancelTransactionId = cancelTransactionId;
	}
}
