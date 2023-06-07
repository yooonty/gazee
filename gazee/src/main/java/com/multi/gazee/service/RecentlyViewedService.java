package com.multi.gazee.service;

import org.springframework.ui.Model;

import com.multi.gazee.product.PageVO;

public interface RecentlyViewedService {
	
	void recentView(int productId, String memberId);
	
	void recentViewCount(String memberId, Model model);
	
	void recentViewList(PageVO vo, String memberId, Model model);
	
}
