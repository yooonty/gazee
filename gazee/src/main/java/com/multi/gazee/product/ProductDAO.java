package com.multi.gazee.product;

import com.multi.gazee.member.MemberVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ProductDAO {
	@Autowired
	SqlSessionTemplate my;
	
	public List<ProductVO> list() {
		List<ProductVO> productList = my.selectList("product.all");
		return productList;
	}
}

