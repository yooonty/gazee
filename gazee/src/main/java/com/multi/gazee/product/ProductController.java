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
import com.multi.gazee.weka.WekaRecommendService;

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
	
	/* 메인페이지 - 상품 추천 */
	@RequestMapping("product/best")
	public String best(Model model) throws Exception {
		return service.best(model);
	}
	
	/* 메인페이지 - 유저 맞춤 추천 */
	@RequestMapping("product/userBest")
	public String userBest(String memberId, Model model) throws Exception {
		return service.userBest(memberId, model);
	}
	
	/* 메인페이지 - AI 추천 */
	@RequestMapping("product/wekaBest")
	public String wekaBest(String memberId, WekaRecommendService wekaRecommendService, Model model) throws Exception {
		return service.wekaBest(memberId, wekaRecommendService, model);
	}

	/* 메인페이지 - 전체 상품 검색 */
	@RequestMapping("product/searchList")
	public void searchList(PageVO vo, String search, Model model) {
		service.searchList(vo, search, model);
	}
	
	/* 메인페이지 - 상품 검색 페이징 */
	@RequestMapping("product/productList")
	public void productList(PageVO vo, String search, Model model) {
		service.productList(vo, search, model);
	}
	
	/* 메인페이지 - 상품 검색 페이징2 */
	@RequestMapping("product/productList2")
	public String productList2(PageVO vo, String category, Model model) throws Exception {
		return service.productList2(vo, category, model);
	}
	
	/* 메인페이지 - 카테고리 검색 */
	@RequestMapping("product/categoryList")
	public void categoryList(PageVO vo, String category, Model model) {
		service.categoryList(vo, category, model);
	}
	
	/* 메인페이지 - 조회수 증가 */
	@RequestMapping("product/viewsCount")
	public void viewsCount(int productId) {
		service.viewCount(productId);
	}
	
	/* 메인페이지 - 판매 중인 상품 */
	@RequestMapping("product/searchListOnSale")
	public void searchListOnSale(PageVO vo, String search, Model model) {
		service.searchListOnSale(vo, search, model);
	}
	
	/* 메인페이지 - 판매 중인 상품 페이징 */
	@RequestMapping("product/productListOnSale")
	public String productListOnSale(PageVO vo, String search, Model model) throws Exception {
		return service.productListOnSale(vo, search, model);
	}

	/* 메인페이지 - 카테고리 - 판매 중인 상품 */
	@RequestMapping("product/categoryListOnSale")
	public void categoryListOnSale(PageVO vo, String category, Model model) {
		service.categoryListOnSale(vo, category, model);
	}
	
	/* 메인페이지 - 카테고리 - 판매 중인 상품 페이징 */
	@RequestMapping("product/productListOnSale2")
	public String productListOnSale2(PageVO vo, String category, Model model) throws Exception {
		return service.productListOnSale2(vo, category, model);
	}
	
	/* 상품판매자 확인 */
	@RequestMapping("product/checkSeller")
	@ResponseBody
	public String checkSeller(int productId) throws Exception {
		return service.checkSeller(productId);
	}
	
	/* 상품 상세페이지 */
	@RequestMapping("product/detail")
	public void productDetail(HttpSession session, Model model, int productId) {
		service.productDetail(model, productId);
	}

	/* 상품 상세페이지 - 작성자 */
	@RequestMapping("product/detail_owner")
	public void productDetailOwner(HttpSession session, Model model, int productId) {
		service.productDetail(model, productId);
	}
	
	/* 상품 상세페이지 - 로그인 안한경우 */
	@RequestMapping("product/detail_nomember")
	public void productDetailNoMember(HttpSession session, Model model, int productId) {
		service.productDetail(model, productId);
	}
	
	/* 상품 상세페이지 - 이미지 슬라이드 */
	@RequestMapping("product/imgslide")
	public void imgslide(Model model, int productId) {
		service.productImgSlide(model, productId);
	}

	/* 상품 상세페이지 - 판매하기 */
	@RequestMapping("product/register")
	public void productRegister(HttpSession session, ProductVO product, HttpServletResponse response) {
		service.productRegister(session, product, response);
	}
	
	/* 상품 상세페이지 - 판매글 수정 */
	@RequestMapping("product/productUpdate")
	public void productUpdate(HttpSession session, Model model, ProductVO bag, int productId) {
		service.productUpdate(bag);
	}
	
	/* 상품 상세페이지 - 판매글 삭제 */
	@RequestMapping("product/productDelete")
	public void productDelete(HttpSession session, Model model, ProductVO bag) {
		service.productDelete(bag);
	}

	/* 상품 상세페이지 - 판매하기 */
	@RequestMapping("product/productUpdateSel")
	public void productUpdateSel(HttpSession session, Model model, ProductVO bag, int productId) {
		service.productUpdateSelect(model, productId);
	}

	/* 상품 판매 - 임시저장 확인 */
	@RequestMapping("product/checkTemporaryProduct")
	public void checkTemporaryProduct(HttpSession session, Model model, ProductVO bag) {
		service.checkTemporaryProduct(model, bag);
	}

	/* 상품 상세 정보 */
	@RequestMapping("product/productOne")
	@ResponseBody
	public ProductVO productOne(int productId) {
		return service.productOne(productId);
	}

	/* 채팅 페이지 - [판매하기] 시간 업데이트 */
	@RequestMapping("product/sellTimeUpdate")
	@ResponseBody
	public int sellTimeUpdate(int productId) {
		return service.sellTimeUpdate(productId);
	}

	/* 채팅 페이지 - [판매하기] 시간 초기화 */
	@RequestMapping("product/sellTimeDelete")
	@ResponseBody
	public int sellTimeDelete(int productId) {
		return service.sellTimeDelete(productId);
	}

	/* 채팅 페이지 - [판매하기] 버튼 클릭 여부 확인 */
	@RequestMapping("product/sellTimeCheck")
	@ResponseBody
	public ProductVO sellTimeCheck(int productId) {
		return service.sellTimeCheck(productId);
	}
}