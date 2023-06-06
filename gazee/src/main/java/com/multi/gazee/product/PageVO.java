package com.multi.gazee.product;

public class PageVO {
	private int start;
	private int end;
	private int page;
	private int num; // 한 페이지당 상품 갯수

	public void setStartEnd(int page) {
		// page별로 start, end값만 구해주면 됨.
		start = 1 + (page - 1) * num;
		// 무조건 1부터 시작
		// 1page: 1 + 0 * 10 => start 1
		// 2page: 1 + 1 * 10 => start 11
		end = page * num;
		// 1page: 1 * 10 => end 10
		// 2page: 2 * 10 => end 20
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
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "PageVO [start=" + start + ", end=" + end + ", page=" + page + ", num=" + num + "]";
	}

}
