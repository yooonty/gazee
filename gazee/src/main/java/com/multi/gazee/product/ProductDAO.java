package com.multi.gazee.product;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


//스프링에 dao는 싱글톤으로 하나만 만들어서 사용할게!라고 설정해야함.
//2가지 방법으로 할 수 있음.
//어노테이션(표시)방법, xml방법
@Component
public class ProductDAO { // DAO는 테이블 당 하나씩
	
	//MyBatis부품!
	@Autowired
	SqlSessionTemplate my;
	
	/*
	 * public void update(ProductVO bag) { my.delete("member.update", bag); }
	 */
	
	public List<ProductVO> best() {
		List<ProductVO> list = my.selectList("product.best");
		return list;
	}
	
	/*
	 * public List<ProductVO> list() { List<ProductVO> list =
	 * my.selectList("product.all"); return list; }
	 */
	
	/*
	 * public List<ProductVO> search(String search) { List<ProductVO> list =
	 * my.selectList("product.search",search); return list; }
	 */
	
	/*
	 * public List<ProductVO> searchList(PageVO vo) { return
	 * my.selectList("product.searchList", vo); }
	 */
	
	public List<ProductVO> searchAll(HashMap<String, Object> map) {
		return my.selectList("product.searchAll", map);
	}

	public List<ProductVO> categoryAll(HashMap<String, Object> map) {
		return my.selectList("product.categoryAll", map);
	}
	
	public int countSearch(String search) {
		return my.selectOne("product.countSearch",search);
	} 

	public int countCategory(String category) {
		return my.selectOne("product.countCategory",category);
	} 
}
