package com.multi.gazee.service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.multi.gazee.product.PageVO;
import com.multi.gazee.product.ProductVO;
import com.multi.gazee.productLikes.ProductLikesVO;
import com.multi.gazee.weka.WekaRecommendService;

public interface ProductService {
	void productDetail(Model model, int productId);
	
	ProductVO productOne(int productId);
	
	void productList(Model model);
	
	void productImgSlide(Model model, int productId);
	
	void productUpdateSelect(Model model, int productId);
	
	void productRegister(HttpSession session, ProductVO product, HttpServletResponse response);
	
	void productUpdate(ProductVO bag);
	
	void productDelete(ProductVO bag);
	
	void checkTemporaryProduct(Model model, ProductVO bag);
	
	void productLikes(ProductLikesVO bag);
	
	void unLikes(ProductLikesVO bag, Model model);
	
	void checkLikes(Model model, ProductLikesVO bag);

	int sellTimeUpdate(int productId);
	
	int sellTimeDelete(int productId);
	
	ProductVO sellTimeCheck(int productId);
	
	String best(Model model) throws Exception;

	String userBest(String memberId, Model model) throws Exception;
	
	String wekaBest(String memberId, WekaRecommendService wekaRecommendService, Model model) throws Exception;

	void searchList(PageVO vo, String search, Model model);
	
	void productList(PageVO vo, String search, Model model);
	
	void categoryList(PageVO vo, String category, Model model);
	
	String productList2(PageVO vo, String category, Model model) throws Exception;
	
	void viewCount(int productId);
	
	void searchListOnSale(PageVO vo, String search, Model model);
	
	String productListOnSale(PageVO vo, String search, Model model) throws Exception;

	void categoryListOnSale(PageVO vo, String category, Model model);
	
	String productListOnSale2(PageVO vo, String category, Model model) throws Exception;
	
	String checkSeller(int productId) throws Exception;
	
	/* ADMIN 상품 검색 */
    String searchProduct(String searchType, String searchIndex, Model model) throws Exception;
}
