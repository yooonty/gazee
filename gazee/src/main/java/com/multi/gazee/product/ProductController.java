package com.multi.gazee.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.productImage.ProductImageDAO;
import com.multi.gazee.productImage.ProductImageVO;
import com.multi.gazee.transactionHistory.TransactionHistoryDAO;
import com.multi.gazee.weka.WekaRecommendService;

@Controller // 스프링에서 제어하는 역할로 등록!
public class ProductController {

	@Autowired // 만들어둔 싱글톤 주소 넣어줌.
	ProductDAO dao; // 전역변수(글로벌 변수)

	@Autowired
	ProductImageDAO dao2;

	@Autowired
	MemberDAO dao3;

	@Autowired
	TransactionHistoryDAO dao4;

	@RequestMapping("product/best")
	public String best(Model model) {
		List<ProductVO> list = dao.best();
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		/*
		 * System.out.println(list); System.out.println(list.size());
		 */
		for (int i = 0; i < list.size(); i++) {
			list2.add(dao2.one(list.get(i).getProductId()));
		}
		/*
		 * System.out.println(list2); System.out.println(list2.size());
		 */
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		model.addAttribute("mode", 0);
		return "product/productList";
	}

	@RequestMapping("product/userBest")
	public String userBest(String memberId, Model model) {
		List<ProductVO> list = dao.userBest(memberId);
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		System.out.println(list);
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			list2.add(dao2.one(list.get(i).getProductId()));
		}
		System.out.println(list2);
		System.out.println(list2.size());
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		model.addAttribute("mode", 1);
		return "product/productList";
	}

	@RequestMapping("product/wekaBest")
	public String weka(String memberId, WekaRecommendService wekaRecommendService, Model model) throws Exception {
		MemberVO vo = dao3.one(memberId);
		Date now = new Date(); // 올해
		Date birth = vo.getBirth(); // 태어난 해
		int age = (now.getYear() - birth.getYear()); // 나이 계산
		int gender = vo.getGender().equals("남성") ? 1 : 0; // 0은 여성, 1은 남성
		int seed = dao4.checkBalance(memberId); // 회원의 보유잔액
		double[] values = { age, gender, seed / 100 };
		String category = wekaRecommendService.ml(values);
		System.out.println("weka에게 추천받은 카테고리: " + category);
		List<ProductVO> list = dao.wekaBest(category);
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		/*
		 * System.out.println(list); System.out.println(list.size());
		 */
		for (int i = 0; i < list.size(); i++) {
			list2.add(dao2.one(list.get(i).getProductId()));
		} // ml에 의해 추천받아온다.
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		model.addAttribute("mode", 2);

		return "product/productList";
	}

	@RequestMapping("product/searchList")
	public void searchList(PageVO vo, String search, Model model) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("search", search);
		System.out.println(map);
		List<ProductVO> list;
		list = dao.searchAll(map);
		for (int i = 0; i < list.size(); i++) {
			list2.add(dao2.one(list.get(i).getProductId()));
		}
		int count = dao.countSearch(search);
		System.out.println("all count>> " + count);
		int pages = count / vo.getNum() + 1; // 전체 페이지 개수 구하기
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		model.addAttribute("count", count);
		model.addAttribute("pages", pages);
		model.addAttribute("search", search);
	}

	@RequestMapping("product/productList")
	public void productList(PageVO vo, String search, Model model) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("search", search);
		System.out.println(map);
		List<ProductVO> list = dao.searchAll(map);
		for (int i = 0; i < list.size(); i++) {
			list2.add(dao2.one(list.get(i).getProductId()));
		}
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
	}

	@RequestMapping("product/categoryList")
	public void categoryList(PageVO vo, String category, Model model) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("category", category);
		System.out.println(map);
		List<ProductVO> list = dao.categoryAll(map);
		for (int i = 0; i < list.size(); i++) {
			list2.add(dao2.one(list.get(i).getProductId()));
		}
		int count = dao.countCategory(category);
		System.out.println("all count>> " + count);
		int pages = count / vo.getNum() + 1; // 전체 페이지 개수 구하기
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		model.addAttribute("count", count);
		model.addAttribute("pages", pages);
		model.addAttribute("category", category);
	}

	@RequestMapping("product/productList2")
	public String productList2(PageVO vo, String category, Model model) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("category", category);
		System.out.println(map);
		List<ProductVO> list = dao.categoryAll(map);
		for (int i = 0; i < list.size(); i++) {
			list2.add(dao2.one(list.get(i).getProductId()));
		}
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		return "product/productList";
	}

	@RequestMapping("product/viewsCount")
	public void viewsCount(int productId) {
		int result = dao.viewsCount(productId);
		if (result == 1) {
			System.out.println("뷰 증가");
		} else {
			System.out.println("뷰 증가 에러");
		}
	}

	/* 판매중인 상품 */
	@RequestMapping("product/searchListOnSale")
	public void searchListOnSale(PageVO vo, String search, Model model) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("search", search);
		System.out.println(map);
		List<ProductVO> list;
		list = dao.searchAllOnSale(map);
		for (int i = 0; i < list.size(); i++) {
			list2.add(dao2.one(list.get(i).getProductId()));
		}
		int count = dao.countSearchOnSale(search);
		System.out.println("all count>> " + count);
		int pages = count / vo.getNum() + 1; // 전체 페이지 개수 구하기
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		model.addAttribute("count", count);
		model.addAttribute("pages", pages);
		model.addAttribute("search", search);
	}
	
	@RequestMapping("product/productListOnSale")
	public String productListOnSale(PageVO vo, String search, Model model) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("search", search);
		System.out.println(map);
		List<ProductVO> list = dao.searchAllOnSale(map);
		for (int i = 0; i < list.size(); i++) {
			list2.add(dao2.one(list.get(i).getProductId()));
		}
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		return "product/productList";
	}
	
	@RequestMapping("product/categoryListOnSale")
	public void categoryListOnSale(PageVO vo, String category, Model model) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("category", category);
		System.out.println(map);
		List<ProductVO> list = dao.categoryAllOnSale(map);
		for (int i = 0; i < list.size(); i++) {
			list2.add(dao2.one(list.get(i).getProductId()));
		}
		int count = dao.countCategoryOnSale(category);
		System.out.println("all count>> " + count);
		int pages = count / vo.getNum() + 1; // 전체 페이지 개수 구하기
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		model.addAttribute("count", count);
		model.addAttribute("pages", pages);
		model.addAttribute("category", category);
	}
	
	@RequestMapping("product/productListOnSale2")
	public String productListOnSale2(PageVO vo, String category, Model model) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("category", category);
		System.out.println(map);
		List<ProductVO> list = dao.categoryAllOnSale(map);
		for (int i = 0; i < list.size(); i++) {
			list2.add(dao2.one(list.get(i).getProductId()));
		}
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		return "product/productList";
	}
	
	/* 판매하기 */
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

}
