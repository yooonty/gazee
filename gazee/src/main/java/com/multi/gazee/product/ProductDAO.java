package com.multi.gazee.product;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductDAO {
	
	@Autowired
	SqlSessionTemplate my;
	
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
}