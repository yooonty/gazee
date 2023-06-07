package com.multi.gazee.recentlyViewed;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.gazee.product.PageVO;
import com.multi.gazee.service.RecentlyViewedService;

@Controller // 스프링에서 제어하는 역할로 등록!
public class RecentlyViewedController {
	
	@Autowired
	RecentlyViewedService recentlyViewedService;

	/* 최근 본 상품 추가 */
	@RequestMapping("recentlyViewed/recentView")
	public void recentView(int productId, String memberId) {
		recentlyViewedService.recentView(productId, memberId);
	}

	/* 최근 본 상품 갯수 */
	@RequestMapping("recentlyViewed/recentViewCount")
	public void recentViewCount(String memberId, Model model) {
		recentlyViewedService.recentViewCount(memberId, model);
	}

	/* 최근 본 상품 목록 */
	@RequestMapping("recentlyViewed/recentViewList")
	public void recentViewList(PageVO vo, String memberId, Model model) {
		recentlyViewedService.recentViewList(vo, memberId, model);
	}

}
