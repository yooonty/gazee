package com.multi.gazee.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.multi.gazee.product.ProductDAO;
import com.multi.gazee.product.ProductVO;
import com.multi.gazee.productImage.ProductImageDAO;
import com.multi.gazee.productImage.ProductImageVO;
import com.multi.gazee.productLikes.ProductLikesDAO;
import com.multi.gazee.productLikes.ProductLikesVO;

@PropertySource("classpath:application.properties")
@Service
public class ProductImageServiceImpl implements ProductImageService {

	@Autowired
	private AmazonS3 s3Client;
	
	@Value("${productImgbucketName}")
    private String bucketName;
	
	@Autowired
	ProductDAO dao;
	
	@Autowired
	ProductImageDAO dao2;
	
	@Autowired
	ProductLikesDAO like;
	
	public String uploadObject(MultipartFile multipartFile, String storedFileName) throws IOException {
		ObjectMetadata metadata = new ObjectMetadata();
		String filePath = storedFileName;
		byte[] bytes = IOUtils.toByteArray(multipartFile.getInputStream());
		metadata.setContentLength(bytes.length);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
		
		// 1. bucket name, 2. key : full path to the file 3. file : new File로 생성한 file instance  
		// 2. PutObjectRequest로 구현 가능 
		s3Client.putObject(new PutObjectRequest(bucketName, filePath, byteArrayInputStream, metadata));
		
		return s3Client.getUrl(bucketName, filePath).toString();
	}

	public void deleteObject( String storedFileName) throws AmazonServiceException {
		s3Client.deleteObject(new DeleteObjectRequest(bucketName , storedFileName));
	}
	
	// 파일 업로드 처리
	public void uploadMultipleFileHandler(List<MultipartFile> multiFileList, ProductImageVO productImage, HttpSession session) {
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
	            uploadObject(file, uuidFileName);
	            message.append("https://gazee.product.image.s3.ap-northeast-2.amazonaws.com/").append(uuidFileName).append("\n");
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to upload " + uuidFileName, e);
	        }
	        System.out.println("productImagecontroller호출" + productImage);
	        dao2.productImageUpload(productImage);
	    }
	}
	
	public static String getUuidFileName(String originalFileName) {
		return UUID.randomUUID().toString() + "_" + originalFileName;
	}
	
	public void productImageDelete(int productId, Model model, ProductVO bag, ProductLikesVO likebag) {
		List<ProductImageVO> productImgList = dao2.productImage(productId);
		for (ProductImageVO productImg : productImgList ) {
			String objectName= productImg.getProductImageName();
		try {
		    deleteObject(objectName);
		} catch (Exception e) {
		    e.printStackTrace();
			} 
		}
		model.addAttribute("bag", bag);
		like.likeDelete(likebag);
		dao2.productImageDelete(productId);
		dao.productDelete(bag);
	}
	
	public String productImageThumbnail(int productId) {
		ProductImageVO productImageVO = dao2.productImageThumbnail(productId);
		String thumbnail = productImageVO.getProductImageName();
		String thumbnailAddr = "http://awswlqccbpkd17595311.cdn.ntruss.com/"+thumbnail+"?type=f&w=120&h=120";
		return thumbnailAddr;
	}
}
