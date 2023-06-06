package com.multi.gazee.product;

import com.multi.gazee.admin.paging.PageVO;
import com.multi.gazee.member.MemberVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public class ProductDAO {
	@Autowired
	SqlSessionTemplate my;
	
	/* ALL */
	public List<ProductVO> list() {
		List<ProductVO> productList = my.selectList("product.all");
		return productList;
	}
	
	/* 상품 목록 (페이징) */
	public List<ProductVO> pagedList(PageVO pageVo) {
		List<ProductVO> pagedProductList = my.selectList("product.pagedAll", pageVo);
		return pagedProductList;
	}
	
	/* 전체 상품 갯수 COUNT */
	public int count() {
		return my.selectOne("product.count");
	}
	
	/* 오늘 등록된 상품 목록*/
	public List<ProductVO> listProductToday() {
		List<ProductVO> productTodayList = my.selectList("product.listProductToday");
		return productTodayList;
	}
	
	/* ONE */
	public List<ProductVO> productOneById(String id) {
		List<ProductVO> productOneById = my.selectList("product.productOneById", id);
		return productOneById;
	}
	
	/* Order No로 Price 가져오기 */
	public int priceByOrderNo(int productId) {
		int price = my.selectOne("product.priceByOrderNo", productId);
		return price;
	}
	
	/* ADMIN 상품 관리 내 검색 */
	public List<ProductVO> search(Map parameterMap) {
		List<ProductVO> search = my.selectList("product.search", parameterMap);
		return search;
	}
}