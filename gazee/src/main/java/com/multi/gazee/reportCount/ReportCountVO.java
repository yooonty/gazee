package com.multi.gazee.reportCount;

public class ReportCountVO {
	int no;
	String memberId;
	int count;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "ReportCountVO [no=" + no + ", memberId=" + memberId + ", count=" + count + "]";
	}


}