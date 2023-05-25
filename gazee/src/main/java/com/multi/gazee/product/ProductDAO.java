package com.multi.gazee.product;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class ProductDAO {
	
	@Autowired
	SqlSessionTemplate my;

public ProductVO productone(int productId) {
		ProductVO bag = my.selectOne("product.productOne", productId);
		return bag;
	}
	
	public ProductVO productDetail(int productId) {
		ProductVO bag = my.selectOne("product.productDetail",productId);
		return bag;
	}
	
	public List<ProductVO> list() {
		List<ProductVO> list = my.selectList("product.all");
		//row하나당 어떤 vo에 넣을지만 지정하면 my.selectList()를 호출한 경우에는 myBatis가 list vo가 잔뜩 들어간 List로 만들어준다.
		System.out.println(list.size());
		return list;
	}
	
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
	
	public void productUpdate(ProductVO product) {
		product.setSavedTime(getTime());
		int result = my.update("product.productUpdate", product);
		System.out.println(result);
	}
	
	public void productDelete(ProductVO product) {
		int result = my.delete("product.productDelete", product);
		System.out.println(result);
	}
	public ProductVO checkTemporaryProduct (ProductVO product) {
		ProductVO bag = my.selectOne("product.checkTemporaryProduct",product);
		System.out.println(bag);
		return bag;
	}
}