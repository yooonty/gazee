package com.multi.gazee.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.multi.gazee.product.PageVO;
import com.multi.gazee.product.ProductVO;
import com.multi.gazee.productImage.ProductImageDAO;
import com.multi.gazee.productImage.ProductImageVO;
import com.multi.gazee.recentlyViewed.RecentlyViewedDAO;

@Service
public class RecentlyViewedServiceImpl implements RecentlyViewedService{
	
	@Autowired
	RecentlyViewedDAO recentlyViewedDao;

	@Autowired
	ProductImageDAO productImageDao;
	
	public void recentView(int productId, String memberId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("memberId", memberId);
		int result = recentlyViewedDao.recentView(map);
		if (result == 1) {
			System.out.println("insert");
		} else {
			System.out.println("insert에러");
		}
		int count = recentlyViewedDao.recentViewCount(memberId);
		if (count > 20) {
			int control = recentlyViewedDao.recentViewControl(memberId);
			System.out.println("제어성공? " + control);
		}
	}
	
	public void recentViewCount(String memberId, Model model) {
		int count = recentlyViewedDao.recentViewCount(memberId);
		model.addAttribute("count", count);
	}
	
	public void recentViewList(PageVO vo, String memberId, Model model) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("memberId", memberId);
		System.out.println(map);
		List<ProductVO> list = recentlyViewedDao.recentViewList(map);
		for (int i = 0; i < list.size(); i++) {
			list2.add(productImageDao.productImageThumbnail(list.get(i).getProductId()));
		}
		int count = recentlyViewedDao.recentViewCount(memberId);
		System.out.println("all count>> " + count);
		int pages = count / vo.getNum() + 1; // 전체 페이지 개수 구하기
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		model.addAttribute("count", count);
		model.addAttribute("pages", pages);
	}
}
