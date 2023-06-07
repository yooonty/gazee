package com.multi.gazee.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.order.OrderDAO;
import com.multi.gazee.order.OrderVO;
import com.multi.gazee.product.PageVO;
import com.multi.gazee.product.ProductDAO;
import com.multi.gazee.product.ProductVO;
import com.multi.gazee.productImage.ProductImageDAO;
import com.multi.gazee.productImage.ProductImageVO;
import com.multi.gazee.productLikes.ProductLikesDAO;
import com.multi.gazee.productLikes.ProductLikesVO;
import com.multi.gazee.reportCount.ReportCountDAO;
import com.multi.gazee.reportCount.ReportCountVO;
import com.multi.gazee.scheduler.ProductScheduler;
import com.multi.gazee.transactionHistory.TransactionHistoryDAO;
import com.multi.gazee.weka.WekaRecommendService;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	ProductDAO productDao;
	
	@Autowired
	ProductImageDAO productImgDao;
	
	@Autowired
	ReportCountDAO reportCountDao;
	
	@Autowired
	OrderDAO orderDao;
	
	@Autowired
	MemberDAO memberDao;
	
	@Autowired
	ProductLikesDAO like;
	
	@Autowired
	ProductScheduler productScheduler;
	
	@Autowired
	TransactionHistoryDAO historyDao;
	
	@Autowired
	private TaskScheduler taskScheduler;
	
	public String best(Model model) throws Exception {
		List<ProductVO> list = productDao.best();
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		/*
		 * System.out.println(list); System.out.println(list.size());
		 */
		for (int i = 0; i < list.size(); i++) {
			list2.add(productImgDao.productImageThumbnail(list.get(i).getProductId()));
		}
		/*
		 * System.out.println(list2); System.out.println(list2.size());
		 */
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		model.addAttribute("mode", 0);
		return "product/productList";
	}
	
	public String userBest(String memberId, Model model) throws Exception {
		List<ProductVO> list = productDao.userBest(memberId);
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		System.out.println(list);
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			list2.add(productImgDao.productImageThumbnail(list.get(i).getProductId()));
		}
		System.out.println(list2);
		System.out.println(list2.size());
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		model.addAttribute("mode", 1);
		if(list.size() == 0) {
			return "product/userBest";
		}
		return "product/productList";
	}
	
	public String wekaBest(String memberId, WekaRecommendService wekaRecommendService, Model model) throws Exception {
		MemberVO vo = memberDao.selectOne(memberId);
		Date now = new Date(); // 올해
		Date birth = vo.getBirth(); // 태어난 해
		int age = (now.getYear() - birth.getYear()); // 나이 계산
		int gender = vo.getGender().equals("남성") ? 1 : 0; // 0은 여성, 1은 남성
		int seed = historyDao.select(memberId); // 회원의 보유잔액
		double[] values = { age, gender, seed / 100 };
		String category = wekaRecommendService.ml(values);
		System.out.println("weka에게 추천받은 카테고리: " + category);
		List<ProductVO> list = productDao.wekaBest(category);
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		/*
		 * System.out.println(list); System.out.println(list.size());
		 */
		for (int i = 0; i < list.size(); i++) {
			list2.add(productImgDao.productImageThumbnail(list.get(i).getProductId()));
		} // ml에 의해 추천받아온다.
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		model.addAttribute("mode", 2);
		return "product/productList";
	}
	
	public void searchList(PageVO vo, String search, Model model) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("search", search);
		System.out.println(map);
		List<ProductVO> list;
		list = productDao.searchAll(map);
		for (int i = 0; i < list.size(); i++) {
			list2.add(productImgDao.productImageThumbnail(list.get(i).getProductId()));
		}
		int count = productDao.countSearch(search);
		System.out.println("all count>> " + count);
		int pages = count / vo.getNum() + 1; // 전체 페이지 개수 구하기
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		model.addAttribute("count", count);
		model.addAttribute("pages", pages);
		model.addAttribute("search", search);
	}
	
	public void productList(PageVO vo, String search, Model model) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("search", search);
		System.out.println(map);
		List<ProductVO> list = productDao.searchAll(map);
		for (int i = 0; i < list.size(); i++) {
			list2.add(productImgDao.productImageThumbnail(list.get(i).getProductId()));
		}
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
	}
	
	public void categoryList(PageVO vo, String category, Model model) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("category", category);
		System.out.println(map);
		List<ProductVO> list = productDao.categoryAll(map);
		for (int i = 0; i < list.size(); i++) {
			list2.add(productImgDao.productImageThumbnail(list.get(i).getProductId()));
		}
		int count = productDao.countCategory(category);
		System.out.println("all count>> " + count);
		int pages = count / vo.getNum() + 1; // 전체 페이지 개수 구하기
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		model.addAttribute("count", count);
		model.addAttribute("pages", pages);
		model.addAttribute("category", category);
	}
	
	public String productList2(PageVO vo, String category, Model model) throws Exception {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("category", category);
		System.out.println(map);
		List<ProductVO> list = productDao.categoryAll(map);
		for (int i = 0; i < list.size(); i++) {
			list2.add(productImgDao.productImageThumbnail(list.get(i).getProductId()));
		}
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		return "product/productList";
	}
	
	public void viewCount(int productId) {
		int result = productDao.viewsCount(productId);
		if (result == 1) {
			System.out.println("뷰 증가");
		} else {
			System.out.println("뷰 증가 에러");
		}
	}
	
	public void searchListOnSale(PageVO vo, String search, Model model) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("search", search);
		System.out.println(map);
		List<ProductVO> list;
		list = productDao.searchAllOnSale(map);
		for (int i = 0; i < list.size(); i++) {
			list2.add(productImgDao.productImageThumbnail(list.get(i).getProductId()));
		}
		int count = productDao.countSearchOnSale(search);
		System.out.println("all count>> " + count);
		int pages = count / vo.getNum() + 1; // 전체 페이지 개수 구하기
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		model.addAttribute("count", count);
		model.addAttribute("pages", pages);
		model.addAttribute("search", search);
	}
	
	public String productListOnSale(PageVO vo, String search, Model model) throws Exception {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("search", search);
		System.out.println(map);
		List<ProductVO> list = productDao.searchAllOnSale(map);
		for (int i = 0; i < list.size(); i++) {
			list2.add(productImgDao.productImageThumbnail(list.get(i).getProductId()));
		}
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		return "product/productList";
	}
	
	public void categoryListOnSale(PageVO vo, String category, Model model) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("category", category);
		System.out.println(map);
		List<ProductVO> list = productDao.categoryAllOnSale(map);
		for (int i = 0; i < list.size(); i++) {
			list2.add(productImgDao.productImageThumbnail(list.get(i).getProductId()));
		}
		int count = productDao.countCategoryOnSale(category);
		System.out.println("all count>> " + count);
		int pages = count / vo.getNum() + 1; // 전체 페이지 개수 구하기
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		model.addAttribute("count", count);
		model.addAttribute("pages", pages);
		model.addAttribute("category", category);
	}
	
	public String productListOnSale2(PageVO vo, String category, Model model) throws Exception {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProductImageVO> list2 = new ArrayList<ProductImageVO>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("category", category);
		System.out.println(map);
		List<ProductVO> list = productDao.categoryAllOnSale(map);
		for (int i = 0; i < list.size(); i++) {
			list2.add(productImgDao.productImageThumbnail(list.get(i).getProductId()));
		}
		model.addAttribute("list", list);
		model.addAttribute("list2", list2);
		return "product/productList";
	}
	
	public String checkSeller(int productId) throws Exception {
		return productDao.checkSeller(productId);
	}
	
	public void productDetail(Model model, int productId) {
		ProductVO bag = productDao.productDetail(productId);
		model.addAttribute("bag", bag); // 상품상세 불러오기
		List<ProductImageVO> bag2 = productImgDao.productImage(productId);
		model.addAttribute("bag2", bag2); // 상품이미지 불러오기
		String memberId = bag.getMemberId();
		ReportCountVO bag3 = reportCountDao.reportCount(memberId);
		if(bag3 != null) {
			model.addAttribute("bag3", bag3); // 판매자 정보 불러오기
		} else {
			ReportCountVO bag4 = new ReportCountVO();
			bag4.setCount(0);
			model.addAttribute("bag3", bag4);
		}
		OrderVO orderVO = orderDao.orderCheck(productId);//주문상태 확인
		if (orderVO != null && orderVO.getCanceled() == 0) { //결제 후
			if (orderVO.getCompleteStatus() == 1) {
				model.addAttribute("order", "done"); //거래 완료
			} else {
				model.addAttribute("order", "yet"); //거래 전
			}
		} else { //결제 전
			model.addAttribute("order", "null");
		}
		MemberVO memberVO = memberDao.selectOne(memberId);
		model.addAttribute("nickname", memberVO.getNickname());
		model.addAttribute("userProfileImg", memberVO.getProfileImg());
	}
	
	public ProductVO productOne(int productId) {
		ProductVO bag = productDao.productOne(productId);
		return bag;
	}
	
	public void productList(Model model) {
		List<ProductVO> list = productDao.list();
		model.addAttribute("list", list);
	}
	
	public void productImgSlide(Model model, int productId) {
		List<ProductImageVO> bag2 = productImgDao.productImage(productId);
		model.addAttribute("bag2", bag2); // 상품이미지 불러오기
	}
	
	public void productUpdateSelect(Model model, int productId) {
		ProductVO bag;
		bag = productDao.productDetail(productId);
		model.addAttribute("bag", bag); // 상품상세 불러오기
		productImgSlide(model, productId);
	}
	
	public void productRegister(HttpSession session, ProductVO product, HttpServletResponse response) {
		int result = productDao.register(product);
		int productId = product.getProductId();
		session.setAttribute("productId", productId);
		if (result == 1) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	public void productUpdate(ProductVO bag) {
		productDao.productUpdate(bag);
	}
	
	public void productDelete(ProductVO bag) {
		productDao.productDelete(bag);
	}

	public void checkTemporaryProduct(Model model, ProductVO bag) {
		ProductVO bag2 = productDao.checkTemporaryProduct(bag);

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
	
	public void productLikes(ProductLikesVO bag) {
		like.productLikes(bag);
	}
	
	public void unLikes(ProductLikesVO bag, Model model) {
		int result = like.unLikes(bag);
		if (result == 1) {
			model.addAttribute("result", 1);
		} else {
			model.addAttribute("result", 0);

		}
	}
	
	public void checkLikes(Model model, ProductLikesVO bag) {
		ProductLikesVO bag2 = like.checkLikes(bag);
	    if (bag2 != null) {
	    	model.addAttribute("isLiked", 1);
		} else {
			model.addAttribute("isLiked", 0);
			
		}
	}
	
	public int sellTimeUpdate(int productId) {
		int result = productDao.sellTimeUpdate(productId);
		if (result != 0 ) {
			productScheduler.setProductId(productId);
			// 스케줄러를 다시 시작
		    taskScheduler.schedule(() -> productScheduler.scheduleSellTimeUpdate(productId), new Date(System.currentTimeMillis() + 10 * 60 * 1000));
		}
		return result;
	}
	
	public int sellTimeDelete(int productId) {
		int result = productDao.sellTimeDelete(productId);
		return result;
	}
	
	public ProductVO sellTimeCheck(int productId) {
		ProductVO productVO = productDao.productDetail(productId);
		return productVO;
	}
	
	@Override
    public String searchProduct(String searchType, String searchIndex, Model model) throws Exception {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("searchType", searchType);
        parameterMap.put("searchIndex", searchIndex);
        
        List<ProductVO> oneWhereList = productDao.search(parameterMap);
        model.addAttribute("searchList", oneWhereList);
        
        return "admin/adminProductSearch";
    }
}
