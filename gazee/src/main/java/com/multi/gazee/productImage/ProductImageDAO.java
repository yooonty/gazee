package com.multi.gazee.productImage;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.multi.gazee.product.ProductVO;

@Component
public class ProductImageDAO {
	
	@Autowired
	SqlSessionTemplate my;
	
	public List<ProductImageVO> productImage(int productId) {
		List<ProductImageVO> list = my.selectList("productImage.productImage",productId);
		return list;
	}
	
	public void productImageDelete(ProductVO product) {
		int result = my.delete("productImage.productImageDelete", product);
		System.out.println(result);
	}
	
	public int productImageUpload(ProductImageVO productImage) {
		int result = my.insert("productImage.productImageUpload", productImage);
		System.out.println("imageDAO 완료" + productImage);
		return result;
	}
}
