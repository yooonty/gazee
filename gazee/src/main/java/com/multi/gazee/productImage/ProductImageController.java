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

import com.multi.gazee.product.ProductVO;

@Controller
public class ProductImageController {
	
	@Autowired
	ProductImageDAO dao2;
	
//	@RequestMapping("product/detail")
//	public void productImage(Model model,int productId) {
//		ProductImageVO bag2 = dao2.productImage(productId);
//		System.out.println(bag2);
//		model.addAttribute("bag2", bag2);
//	}
	
	@Autowired
	private ProductImageService s3Service;

	// 파일 업로드 처리
	@RequestMapping(value = "product/uploadMultipleFile", method = RequestMethod.POST)
	public @ResponseBody String uploadMultipleFileHandler(@RequestParam("file") List<MultipartFile> multiFileList, ProductImageVO productImage, HttpSession session) {
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
	            return "You failed to upload " + uuidFileName + " => " + e.getMessage();
	        }
	        System.out.println("productImagecontroller호출" + productImage);
	        dao2.productImageUpload(productImage);
	    }
	    
	    return message.toString();
	}

	private static String getUuidFileName(String originalFileName) {
		return UUID.randomUUID().toString() + "_" + originalFileName;
	}
	
}
