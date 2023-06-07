package com.multi.gazee.customerServiceImg;

public class CustomerServiceImgVO {
	private int csImgId;
	private int csId;
	private String csImgName;
	private int csImgOrder;
	
	public int getCsImgId() {
		return csImgId;
	}
	public void setCsImgId(int csImgId) {
		this.csImgId = csImgId;
	}
	public int getCsId() {
		return csId;
	}
	public void setCsId(int csId) {
		this.csId = csId;
	}
	public String getCsImgName() {
		return csImgName;
	}
	public void setCsImgName(String csImgName) {
		this.csImgName = csImgName;
	}
	public int getCsImgOrder() {
		return csImgOrder;
	}
	public void setCsImgOrder(int csImgOrder) {
		this.csImgOrder = csImgOrder;
	}
	@Override
	public String toString() {
		return "customerServiceImgVO [csImgId=" + csImgId + ", csId=" + csId + ", csImgName=" + csImgName
				+ ", csImgOrder=" + csImgOrder + "]";
	}
	
	
}
