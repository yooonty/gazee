package com.multi.gazee.productImage;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.multi.gazee.product.ProductDAO;
import com.multi.gazee.product.ProductVO;
import com.multi.gazee.productLikes.ProductLikesDAO;
import com.multi.gazee.productLikes.ProductLikesVO;
import com.multi.gazee.service.ProductImageService;

@Controller
public class ProductImageController {
	
	@Autowired
	ProductImageDAO dao2;
	
	@Autowired
	ProductLikesDAO like;
	
	@Autowired
	ProductDAO dao;
	
	@Autowired
	ProductImageService s3Service;
	
	@Autowired
	ProductImageDAO productImgdao;

	// 파일 업로드 처리
	@RequestMapping(value = "product/uploadMultipleFile", method = RequestMethod.POST)
	public @ResponseBody void uploadMultipleFileHandler(@RequestParam("file") List<MultipartFile> multiFileList, ProductImageVO productImage, HttpSession session) {
	    s3Service.uploadMultipleFileHandler(multiFileList, productImage, session);
	}
	
	@RequestMapping("product/S3ProductDelete")
	@ResponseBody
	public void	productImageDelete(int productId,HttpSession session, Model model, ProductVO bag, ProductLikesVO likebag) {
		s3Service.productImageDelete(productId, model, bag, likebag);
	}

	@RequestMapping("productImage/productImageThumbnail")
	@ResponseBody
	public String productImageThumbnail(int productId) {
		return s3Service.productImageThumbnail(productId);
	}
}
