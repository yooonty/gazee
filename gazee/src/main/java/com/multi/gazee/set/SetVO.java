package com.multi.gazee.set;

import java.sql.Timestamp;

public class SetVO {
	private int no;
	private String transactionId;
	private String orderTransactionId;
	private String sellerId;
	private Timestamp transactionTime;
	private int amount;
	
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
	public String getOrderTransactionId() {
		return orderTransactionId;
	}
	public void setOrderTransactionId(String orderTransactionId) {
		this.orderTransactionId = orderTransactionId;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public Timestamp getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(Timestamp transactionTime) {
		this.transactionTime = transactionTime;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

}
