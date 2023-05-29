package com.multi.gazee.product;

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
	
	public List<ProductVO> list() {
		List<ProductVO> productList = my.selectList("product.all");
		return productList;
	}
	public List<ProductVO> listProductToday() {
		List<ProductVO> productTodayList = my.selectList("product.listProductToday");
		return productTodayList;
	}
	
	public ProductVO productOneById(int id) {
		ProductVO productOneById = my.selectOne("product.productOneById", id);
		return productOneById;
	}
	
	public int priceByOrderNo(int productId) {
		int price = my.selectOne("product.priceByOrderNo", productId);
		return price;
	}
	
	public List<ProductVO> oneWhere(Map parameterMap) {
		List<ProductVO> oneWhere = my.selectList("product.oneWhere", parameterMap);
		return oneWhere;
	}
}

