package com.multi.gazee.product;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

@Component
public class ProductDAO {

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
	
	public String checkSeller(int productId) {
		return my.selectOne("product.checkSeller",productId);
	} 
	
	public ProductVO productOne(int productId) {
		ProductVO bag = my.selectOne("product.productOne", productId);
		return bag;
	}
	
	public int sellTimeUpdate(int productId) {
		int result = my.update("product.sellTimeUpdate", productId);
		return result;
	}
	
	public int sellTimeDelete(int productId) {
		int result = my.update("product.sellTimeDelete", productId);
		return result;
	}
	
	public ProductVO productDetail(int productId) {
		ProductVO bag = my.selectOne("product.productDetail",productId);
		return bag;
	}
	
	public List<ProductVO> list() {
		List<ProductVO> list = my.selectList("product.all");
		return list;
	}
	
	public int register(ProductVO product) {
		product.setSavedTime(getTime());
		int result = my.insert("product.register", product);
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
	
	public void productUpdate(ProductVO product) {
		product.setSavedTime(getTime());
		my.update("product.productUpdate", product);
	}
	
	public void productDelete(ProductVO product) {
		my.delete("product.productDelete", product);
	}
	public ProductVO checkTemporaryProduct (ProductVO product) {
		ProductVO bag = my.selectOne("product.checkTemporaryProduct",product);
		return bag;
	}

}
