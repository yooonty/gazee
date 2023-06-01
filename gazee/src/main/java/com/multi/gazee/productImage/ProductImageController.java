package com.multi.gazee.productImage;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
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
//	@RequestMapping("product/detail")
//	public void productImage(Model model,int productId) {
//		ProductImageVO bag2 = dao2.productImage(productId);
//		System.out.println(bag2);
//		model.addAttribute("bag2", bag2);
//	}
	
	@Autowired
	private ProductImageService s3Service;
	@Autowired
	ProductImageDAO productImgdao;

	// 파일 업로드 처리
	@RequestMapping(value = "product/uploadMultipleFile", method = RequestMethod.POST)
	public void uploadMultipleFileHandler(@RequestParam("file") List<MultipartFile> multiFileList, ProductImageVO productImage, HttpSession session) {
	    StringBuilder message = new StringBuilder();
	    int order = 0;

	    // 상품 등록 후 자동 생성된 productId 가져오기
	    int productId = (int)session.getAttribute("productId");

	    for (MultipartFile file : multiFileList) {
	        String originalFileName = file.getOriginalFilename();
	        String uuidFileName = getUuidFileName(originalFileName);
	        productImage.setProductId(productId);
	        productImage.setProductImageName(uuidFileName);
	        order++;
	        productImage.setProductImageOrder(order);

	        try {
	            // ========= 서버에 파일 저장 ========= // 
	            String res = s3Service.uploadObject(file, uuidFileName);
	            System.out.println("이건 뭐야" + res);
	            message.append("https://gazee.product.image.s3.ap-northeast-2.amazonaws.com/").append(uuidFileName).append("\n");
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to upload " + uuidFileName, e);
	        }
	        System.out.println("productImagecontroller호출" + productImage);
	        dao2.productImageUpload(productImage);
	    }
	}

	private static String getUuidFileName(String originalFileName) {
		return UUID.randomUUID().toString() + "_" + originalFileName;
	}
	
	@RequestMapping("product/S3ProductDelete")
	@ResponseBody
	public void	productImageDelete(int productId,HttpSession session, Model model, ProductVO bag, ProductLikesVO likebag) {
		
		List<ProductImageVO> productImgList = productImgdao.productImage(productId);
		for (ProductImageVO productImg : productImgList ) {
			String objectName= productImg.getProductImageName();
		try {
		    s3Service.deleteObject(objectName);
		    System.out.format("Object %s has been deleted.\n", objectName);
		} catch (Exception e) {
		    e.printStackTrace();
			} 
		}

		model.addAttribute("bag", bag);
		
		like.likeDelete(likebag);
		productImgdao.productImageDelete(productId);
		dao.productDelete(bag);
		
	}
	
}
