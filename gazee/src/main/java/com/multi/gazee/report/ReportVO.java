package com.multi.gazee.report;

/**
 * @author Administrator
 *
 */
public class ReportVO {
	private int reportId;
	private int reportNo;
	private String reportTitle;
	private String reportCategory;
	private String reportContent;
	private String reportWriter;
	private String reportDate;
	private int reportView;
	private String reportReply;
	private int reportSecret;
	private int temporary;
	
	
	public int getReportId() {
		return reportId;
	}
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	public int getReportNo() {
		return reportNo;
	}
	public void setReportNo(int reportNo) {
		this.reportNo = reportNo;
	}
	public String getReportTitle() {
		return reportTitle;
	}
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	public String getReportCategory() {
		return reportCategory;
	}
	public void setReportCategory(String reportCategory) {
		this.reportCategory = reportCategory;
	}
	public String getReportWriter() {
		return reportWriter;
	}
	public void setReportWriter(String reportWriter) {
		this.reportWriter = reportWriter;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public int getReportView() {
		return reportView;
	}
	public void setReportView(int reportView) {
		this.reportView = reportView;
	}
	public String getReportReply() {
		return reportReply;
	}
	public void setReportReply(String reportReply) {
		this.reportReply = reportReply;
	}
	public int getReportSecret() {
		return reportSecret;
	}
	public void setReportSecret(int reportSecret) {
		this.reportSecret = reportSecret;
	}
	public int getTemporary() {
		return temporary;
	}
	public void setTemporary(int temporary) {
		this.temporary = temporary;
	}
	public String getReportContent() {
		return reportContent;
	}
	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}
	@Override
	public String toString() {
		return "ReportVO [reportId=" + reportId + ", reportNo=" + reportNo + ", reportTitle=" + reportTitle
				+ ", reportCategory=" + reportCategory + ", reportContent=" + reportContent + ", reportWriter="
				+ reportWriter + ", reportDate=" + reportDate + ", reportView=" + reportView + ", reportReply="
				+ reportReply + ", reportSecret=" + reportSecret + ", temporary=" + temporary + "]";
	}
	
	
	
	
}
