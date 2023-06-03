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
	
	public void productImageDelete(int productId) {
		my.delete("productImage.productImageDelete", productId);
	}
	
	public int productImageUpload(ProductImageVO productImage) {
		int result = my.insert("productImage.productImageUpload", productImage);
		return result;
	}
	
	public ProductImageVO productImageThumbnail(int productId) {
		ProductImageVO bag = my.selectOne("productImage.productImageThumbnail",productId);
		return bag;
	}
}
