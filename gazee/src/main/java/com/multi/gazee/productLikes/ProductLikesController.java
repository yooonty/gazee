package com.multi.gazee.productLikes;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.gazee.product.ProductVO;
import com.multi.gazee.service.ProductService;

@Controller
public class ProductLikesController {
	
	@Autowired
	ProductLikesDAO like;
	
	@Autowired
	ProductService service;

	@RequestMapping("product/like")
	public void productLikes(ProductLikesVO bag) {
		service.productLikes(bag);
	}

	@RequestMapping("product/unlike")
	public void unLikes(ProductLikesVO bag, Model model) {
		service.unLikes(bag, model);
	}

	@RequestMapping("product/checkLikes")
	public void checkLikes(HttpSession session, Model model, ProductLikesVO bag) {
		service.checkLikes(model, bag);
	}

}
