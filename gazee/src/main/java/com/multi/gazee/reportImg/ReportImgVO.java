package com.multi.gazee.reportImg;

public class ReportImgVO {
	private int reportImgId;
	private int reportId;
	private String reportImgName;
	private int reportImgOrder;
	
	public int getReportImgId() {
		return reportImgId;
	}
	public void setReportImgId(int reportImgId) {
		this.reportImgId = reportImgId;
	}
	public int getReportId() {
		return reportId;
	}
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	public String getReportImgName() {
		return reportImgName;
	}
	public void setReportImgName(String reportImgName) {
		this.reportImgName = reportImgName;
	}
	public int getReportImgOrder() {
		return reportImgOrder;
	}
	public void setReportImgOrder(int reportImgOrder) {
		this.reportImgOrder = reportImgOrder;
	}
	
	
	@Override
	public String toString() {
		return "reportImgVO [reportImgId=" + reportImgId + ", reportId=" + reportId + ", reportImgName=" + reportImgName
				+ ", reportImgOrder=" + reportImgOrder + "]";
	}
	
}
