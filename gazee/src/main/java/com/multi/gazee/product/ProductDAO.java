package com.multi.gazee.product;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
	
	public ProductVO productone(int productId) {
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
