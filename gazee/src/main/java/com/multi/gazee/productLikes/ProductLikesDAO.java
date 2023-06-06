package com.multi.gazee.productLikes;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ProductLikesDAO {
	@Autowired
	SqlSessionTemplate my;
	
	public int productLikes(ProductLikesVO bag) {
		int result = my.insert("productLikes.productLikes", bag);
		return result;
	}
	
	public int unLikes(ProductLikesVO bag) {
		int result = my.delete("productLikes.unLikes", bag);
		return result;
	}
	public int likeDelete(ProductLikesVO bag) {
		int result = my.delete("productLikes.likeDelete", bag);
		return result;
	}
	
	public ProductLikesVO checkLikes(ProductLikesVO bag) {
		ProductLikesVO bag2 = my.selectOne("productLikes.checkLikes", bag);
		return bag2;
	}
}
