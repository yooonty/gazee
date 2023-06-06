package com.multi.gazee.service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.multi.gazee.product.ProductVO;
import com.multi.gazee.productLikes.ProductLikesVO;

public interface ProductService {
	void productDetail(Model model, int productId);
	
	ProductVO productOne(int productId);
	
	void productList(Model model);
	
	void productImgSlide(Model model, int productId);
	
	void productUpdateSelect(Model model, int productId);
	
	void productRegister(HttpSession session, ProductVO product, HttpServletResponse response);
	
	void productUpdate(ProductVO bag);
	
	void checkTemporaryProduct(Model model, ProductVO bag);
	
	void productLikes(ProductLikesVO bag);
	
	void unLikes(ProductLikesVO bag, Model model);
	
	void checkLikes(Model model, ProductLikesVO bag);


}
