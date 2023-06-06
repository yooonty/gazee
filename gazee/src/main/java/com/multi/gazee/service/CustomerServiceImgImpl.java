package com.multi.gazee.service;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.multi.gazee.customerServiceImg.CustomerServiceImgDAO;
import com.multi.gazee.customerServiceImg.CustomerServiceImgVO;
import com.multi.gazee.customerServiceImg.csAWSS3Biz;

@Service
public class CustomerServiceImgImpl implements CustomerServiceImgService{

	@Autowired
	CustomerServiceImgDAO dao;
	@Autowired
	csAWSS3Biz s3Service;
	
	public void uploadMultipleFileHandler(List<MultipartFile> multiFileList, HttpSession session, CustomerServiceImgVO bag) {
		int csId =(int)session.getAttribute("csId");
		int order=0;
		
		StringBuilder message = new StringBuilder();
	    for (MultipartFile file : multiFileList) {
	        String originalFileName = file.getOriginalFilename();
	        String uuidFileName = getUuidFileName(originalFileName);
	        bag.setCsId(csId);
	        bag.setCsImgName(uuidFileName);
	        order++;
	        bag.setCsImgOrder(order);
	        dao.csImgUpload(bag);
	        try {
	            // ========= 서버에 파일 저장 ========= // 
	            s3Service.uploadObject(file, uuidFileName);
	            message.append("https://gazee.customerService.image.s3.ap-northeast-2.amazonaws.com/").append(uuidFileName).append("\n");
	        } catch (Exception e) {
	        	throw new RuntimeException("Failed to upload " + uuidFileName, e);
	        }
	    }
	}
	
	private static String getUuidFileName(String originalFileName) {
		return UUID.randomUUID().toString() + "_" + originalFileName;
	}
	
	public void csImgDelete(int csId) {
		List<CustomerServiceImgVO> csImgList = dao.csImgList(csId);
		for(CustomerServiceImgVO csImg : csImgList) {
			String objectName= csImg.getCsImgName();
			try {
			    s3Service.deleteObject(objectName);
			    System.out.format("Object %s has been deleted.\n", objectName);
			} catch (Exception e) {
			    e.printStackTrace();
				} 
		}
		dao.csImgDelete(csId);
	}
}
