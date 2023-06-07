package com.multi.gazee.admin.paging;

public class AdminPageVO {
	private int start;
	private int end;
	private int page = 1; // 현재 페이지 = 1로 설정

	public void setStartEnd(int page) {
		start = 1 + (page - 1) * 10;
		end = page * 10;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

}