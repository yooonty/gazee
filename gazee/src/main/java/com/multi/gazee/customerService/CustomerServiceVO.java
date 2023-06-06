package com.multi.gazee.customerService;

public class CustomerServiceVO {
	private int csId;
	private int csNo;
	private String csTitle;
	private String csContent;
	private String csWriter;
	private String csDate;
	private int csView;
	private String csCategory;
	private String csReply;
	private int temporary;
	
	
	public int getCsId() {
		return csId;
	}
	public void setCsId(int csId) {
		this.csId = csId;
	}
	public int getCsNo() {
		return csNo;
	}
	public void setCsNo(int csNo) {
		this.csNo = csNo;
	}
	public String getCsTitle() {
		return csTitle;
	}
	public void setCsTitle(String csTitle) {
		this.csTitle = csTitle;
	}
	public String getCsContent() {
		return csContent;
	}
	public void setCsContent(String csContent) {
		this.csContent = csContent;
	}
	public String getCsWriter() {
		return csWriter;
	}
	public void setCsWriter(String csWriter) {
		this.csWriter = csWriter;
	}
	public String getCsDate() {
		return csDate;
	}
	public void setCsDate(String csDate) {
		this.csDate = csDate;
	}
	public int getCsView() {
		return csView;
	}
	public void setCsView(int csView) {
		this.csView = csView;
	}
	public String getCsCategory() {
		return csCategory;
	}
	public void setCsCategory(String csCategory) {
		this.csCategory = csCategory;
	}
	public String getCsReply() {
		return csReply;
	}
	public void setCsReply(String csReply) {
		this.csReply = csReply;
	}

	public int getTemporary() {
		return temporary;
	}
	public void setTemporary(int temporary) {
		this.temporary = temporary;
	}
	@Override
	public String toString() {
		return "QnaVO [csId=" + csId + ", csNo=" + csNo + ", csTitle=" + csTitle + ", csContent=" + csContent
				+ ", csWriter=" + csWriter + ", csDate=" + csDate + ", csView=" + csView + ", csCategory=" + csCategory
				+ ", csReply=" + csReply + ", temporary=" + temporary + "]";
	}
	
	
	
}
