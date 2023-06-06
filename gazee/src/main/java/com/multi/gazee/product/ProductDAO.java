package com.multi.gazee.product;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
	
	
	public List<ProductVO> best() {
		List<ProductVO> list = my.selectList("product.best");
		return list;
	}

	public List<ProductVO> userBest(String memberId) {
		List<ProductVO> list = my.selectList("product.userBest",memberId);
		return list;
	}
	
	public List<ProductVO> wekaBest(String category) {
		List<ProductVO> list = my.selectList("product.wekaBest",category);
		return list;
	}
	
	public List<ProductVO> searchAll(HashMap<String, Object> map) {
		return my.selectList("product.searchAll", map);
	}

	public List<ProductVO> searchAllOnSale(HashMap<String, Object> map) {
		return my.selectList("product.searchAllOnSale", map);
	}

	public List<ProductVO> categoryAll(HashMap<String, Object> map) {
		return my.selectList("product.categoryAll", map);
	}

	public List<ProductVO> categoryAllOnSale(HashMap<String, Object> map) {
		return my.selectList("product.categoryAllOnSale", map);
	}
	
	public int countSearch(String search) {
		return my.selectOne("product.countSearch",search);
	} 

	public int countSearchOnSale(String search) {
		return my.selectOne("product.countSearchOnSale",search);
	} 

	public int countCategory(String category) {
		return my.selectOne("product.countCategory",category);
	}

	public int countCategoryOnSale(String category) {
		return my.selectOne("product.countCategoryOnSale",category);
	}
	
	public int viewsCount(int productId) {
		return my.update("product.viewsCount", productId);
	}
	
	/* 판매하기 */
	public int register(ProductVO product) {
		//product.setSavedTime(Timestamp.valueOf(LocalDateTime.now()));
		product.setSavedTime(getTime());
		int result = my.insert("product.register", product);
		System.out.println("myBatis 처리, DAO 완료");
		return result;
	}
	
	public Timestamp getTime() {
		// 표준 시간대 설정
		ZoneId zoneId = ZoneId.of("Asia/Seoul");

		// 표준 시간대에 맞는 현재 시간 생성
		ZonedDateTime zonedDateTime = LocalDateTime.now().atZone(zoneId);
		LocalDateTime currentDateTime = zonedDateTime.toLocalDateTime();

		// 표준 시간대에 맞는 Timestamp 생성
		Timestamp timestamp = Timestamp.valueOf(currentDateTime);
		
		return timestamp;
	}
	
	/* 상세페이지 */

}
