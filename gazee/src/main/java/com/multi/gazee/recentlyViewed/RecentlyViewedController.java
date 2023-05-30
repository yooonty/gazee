package com.multi.gazee.recentlyViewed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.gazee.product.PageVO;
import com.multi.gazee.product.ProductVO;
import com.multi.gazee.productImage.ProductImageDAO;
import com.multi.gazee.productImage.ProductImageVO;

@Controller // 스프링에서 제어하는 역할로 등록!
public class RecentlyViewedController {
	
	@Autowired
	RecentlyViewedDAO dao;
	
	@Autowired
	ProductImageDAO dao2;
	
	@RequestMapping("recentlyViewed/recentView")
	public void recentView(int productId, String memberId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("memberId", memberId);
		int result = dao.recentView(map);
		if (result == 1) {
			System.out.println("insert");
		} else {
			System.out.println("insert에러");
		}
	}

	@RequestMapping("recentlyViewed/recentViewCount")
	public void recentViewCount(String memberId, Model model) {
		int count = dao.recentViewCount(memberId);
		model.addAttribute("count", count);
	}

	@RequestMapping("recentlyViewed/recentViewList")
	public void recentViewList(PageVO vo, String memberId, Model model) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("memberId", memberId);
		System.out.println(map);
		List<ProductVO> list = dao.recentViewList(map);
		for (int i = 0; i < list.size(); i++) {
			list2.add(dao2.one(list.get(i).getProductId()));
		}
		int count = dao.recentViewCount(memberId);
		System.out.println("all count>> " + count);
		int pages = count / vo.getNum() + 1; // 전체 페이지 개수 구하기
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		model.addAttribute("count", count);
		model.addAttribute("pages", pages);
	}
}
