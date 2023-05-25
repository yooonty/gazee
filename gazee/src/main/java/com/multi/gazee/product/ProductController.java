package com.multi.gazee.product;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.multi.gazee.productImage.ProductImageDAO;
import com.multi.gazee.productImage.ProductImageVO;
import com.multi.gazee.reportCount.ReportCountDAO;
import com.multi.gazee.reportCount.ReportCountVO;

@Controller
public class ProductController {
	@Autowired
	ProductDAO dao;
	@Autowired
	ProductImageDAO dao2;
	@Autowired
	ReportCountDAO dao3;


	@RequestMapping("product/detail")
	public void productDetail(HttpSession session, Model model, int productId) {

		ProductVO bag = dao.productDetail(productId);
		model.addAttribute("bag", bag); // 상품상세 불러오기
		ProductImageVO bag2 = dao2.productImage(productId);
		model.addAttribute("bag2", bag2); // 상품이미지 불러오기
		String memberId = bag.getMemberId();
		ReportCountVO bag3 = dao3.reportCount(memberId);
		model.addAttribute("bag3", bag3); // 판매자 정보 불러오기
	}

	@RequestMapping("product/detail_owner")
	public void productDetailOwner(HttpSession session, Model model, int productId) {

		ProductVO bag = dao.productDetail(productId);
		model.addAttribute("bag", bag); // 상품상세 불러오기
		ProductImageVO bag2 = dao2.productImage(productId);
		model.addAttribute("bag2", bag2); // 상품이미지 불러오기
		String memberId = bag.getMemberId();
		ReportCountVO bag3 = dao3.reportCount(memberId);
		model.addAttribute("bag3", bag3); // 판매자 정보 불러오기
	}

	@RequestMapping("product/list")
	public void list(Model model) {
		List<ProductVO> list = dao.list();
		model.addAttribute("list", list);
	}

	@RequestMapping("product/register")
	public void productRegister(HttpSession session, ProductVO product, HttpServletResponse response) {
		System.out.println("product/register 호출" + product);
		int result = dao.register(product);
		if (result == 1) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping("product/productUpdate") // product수정
	public void productUpdate(HttpSession session, Model model, ProductVO bag, int productId) {

		System.out.println("update호출" + bag);
		dao.productUpdate(bag);
	}

	@RequestMapping("product/productUpdateSel") // product수정
	public void productUpdateSel(HttpSession session, Model model, ProductVO bag, int productId) {
		bag = dao.productDetail(productId);
		model.addAttribute("bag", bag); // 상품상세 불러오기
		ProductImageVO bag2 = dao2.productImage(productId);
		model.addAttribute("bag2", bag2); // 상품이미지 불러오기
		System.out.println(bag + "updateSel");
	}

	@RequestMapping("product/productDelete") // product삭제
	public void productDelete(HttpSession session, Model model, ProductVO bag) {
		System.out.println(bag);
		dao2.productImageDelete(bag);
		dao.productDelete(bag);
		model.addAttribute("bag", bag);

	}

	@RequestMapping("product/checkTemporaryProduct")
	public void checkTemporaryProduct(HttpSession session, Model model, ProductVO bag) {
	    ProductVO bag2 = dao.checkTemporaryProduct(bag);
	    System.out.println(bag2);

	    if (bag2 != null) { // 임시저장 존재
	        model.addAttribute("result", 1);
	        model.addAttribute("bag", bag2);
	        model.addAttribute("productId", bag2.getProductId());
	    } else { // 임시저장 없음 바로 글쓰기
	        model.addAttribute("result", 0);
	        bag2 = new ProductVO(); // new ProductVO
	        bag2.setProductId(0);
	        model.addAttribute("bag", bag2);
	    }
	}

	@RequestMapping("product/productOne")
	@ResponseBody
	public ProductVO productOne(int productId) {
		ProductVO bag = dao.productone(productId);
		return bag;
	}

}
