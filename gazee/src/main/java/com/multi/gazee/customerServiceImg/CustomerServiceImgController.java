package com.multi.gazee.customerServiceImg;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CustomerServiceImgController {
	
	@Autowired
	private csAWSS3Biz s3Service;
	@Autowired
	CustomerServiceImgDAO dao;
	
	// 파일 업로드 처리
	@RequestMapping(value = "customerServiceImg/csUploadMultipleFile", method = RequestMethod.POST)
	public @ResponseBody String uploadMultipleFileHandler(@RequestParam("file") List<MultipartFile> multiFileList, HttpSession session, CustomerServiceImgVO bag) {
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
	            String res = s3Service.uploadObject(file, uuidFileName);
	            System.out.println("이건 뭐야" + res);
	            message.append("https://gazee.customerService.image.s3.ap-northeast-2.amazonaws.com/").append(uuidFileName).append("\n");
	        } catch (Exception e) {
	            return "You failed to upload " + uuidFileName + " => " + e.getMessage();
	        }
	    }
	    return message.toString();
	}

	private static String getUuidFileName(String originalFileName) {
		return UUID.randomUUID().toString() + "_" + originalFileName;
	}
	
	@RequestMapping("customerService/cs/csImgDelete")
	public void	csImgDelete(int csId) {
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