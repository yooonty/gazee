package com.multi.gazee.productLikes;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.gazee.product.ProductVO;

@Controller
public class ProductLikesController {
	@Autowired
	ProductLikesDAO dao;

	@RequestMapping("product/like")
	public void productLikes(ProductLikesVO bag) {
		System.out.println(bag);
		dao.productLikes(bag);
	}

	@RequestMapping("product/unlike")
	public void unLikes(ProductLikesVO bag, Model model) {
		System.out.println("unlike 요청됨." + bag);
		int result = dao.unLikes(bag);
		if (result == 1) {
			model.addAttribute("result", 1);
		} else {
			model.addAttribute("result", 0);

		}
	}
	
	@RequestMapping("product/checkLikes")
	public void checkLikes(HttpSession session, Model model, ProductLikesVO bag) {
	    ProductLikesVO bag2 = dao.checkLikes(bag);
	    if (bag2 != null) {
	    	model.addAttribute("isLiked", 1);
		} else {
			model.addAttribute("isLiked", 0);
			
		}
	}

}
