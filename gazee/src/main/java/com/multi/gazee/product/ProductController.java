package com.multi.gazee.product;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.multi.gazee.productImage.ProductImageDAO;
import com.multi.gazee.productLikes.ProductLikesDAO;
import com.multi.gazee.reportCount.ReportCountDAO;
import com.multi.gazee.service.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	ProductDAO dao;
	
	@Autowired
	ProductImageDAO dao2;
	
	@Autowired
	ProductLikesDAO like;
	
	@Autowired
	ReportCountDAO dao3;
	
	@Autowired
	ProductService service;

	@RequestMapping("product/detail")
	public void productDetail(HttpSession session, Model model, int productId) {
		service.productDetail(model, productId);
	}

	@RequestMapping("product/detail_owner")
	public void productDetailOwner(HttpSession session, Model model, int productId) {
		service.productDetail(model, productId);
	}

	@RequestMapping("product/list")
	public void list(Model model) {
		service.productList(model);
	}
	
	@RequestMapping("product/imgslide")
	public void imgslide(Model model, int productId) {
		service.productImgSlide(model, productId);
	}

	@RequestMapping("product/register")
	public void productRegister(HttpSession session, ProductVO product, HttpServletResponse response) {
		service.productRegister(session, product, response);
	}
	
	@RequestMapping("product/productUpdate") // product수정
	public void productUpdate(HttpSession session, Model model, ProductVO bag, int productId) {
		service.productUpdate(bag);
	}

	@RequestMapping("product/productUpdateSel") // product수정
	public void productUpdateSel(HttpSession session, Model model, ProductVO bag, int productId) {
		service.productUpdateSelect(model, productId);
	}

	@RequestMapping("product/checkTemporaryProduct")
	public void checkTemporaryProduct(HttpSession session, Model model, ProductVO bag) {
		service.checkTemporaryProduct(model, bag);
	}

	@RequestMapping("product/productOne")
	@ResponseBody
	public ProductVO productOne(int productId) {
		return service.productOne(productId);
	}

	@RequestMapping("product/sellTimeUpdate")
	@ResponseBody
	public int sellTimeUpdate(int productId) {
		return service.sellTimeUpdate(productId);
	}

	@RequestMapping("product/sellTimeDelete")
	@ResponseBody
	public int sellTimeDelete(int productId) {
		return service.sellTimeDelete(productId);
	}

	@RequestMapping("product/sellTimeCheck")
	@ResponseBody
	public ProductVO sellTimeCheck(int productId) {
		return service.sellTimeCheck(productId);
	}
}