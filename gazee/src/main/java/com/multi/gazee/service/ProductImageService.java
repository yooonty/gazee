package com.multi.gazee.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.multi.gazee.product.ProductVO;
import com.multi.gazee.productImage.ProductImageVO;
import com.multi.gazee.productLikes.ProductLikesVO;

public interface ProductImageService {
	
	String uploadObject(MultipartFile multipartFile, String storedFileName) throws IOException;
	
	void uploadMultipleFileHandler(List<MultipartFile> multiFileList, ProductImageVO productImage, HttpSession session);
	
	void productImageDelete(int productId, Model model, ProductVO bag, ProductLikesVO likebag);
	
	String productImageThumbnail(int productId);
}


