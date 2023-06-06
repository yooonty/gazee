package com.multi.gazee.service;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.multi.gazee.reportImg.ReportImgDAO;
import com.multi.gazee.reportImg.ReportImgVO;
import com.multi.gazee.reportImg.reportAWSS3Biz;

@Service
public class ReportImgServcieImpl implements ReportImgService{

	@Autowired
	ReportImgDAO dao;
	@Autowired
	private reportAWSS3Biz s3Service;
	
	public void uploadMultipleFileHandler(List<MultipartFile> multiFileList, HttpSession session, ReportImgVO bag) {
		int reportId =(int)session.getAttribute("reportId");
	    int order=0;
		
		StringBuilder message = new StringBuilder();
	    for (MultipartFile file : multiFileList) {
	        String originalFileName = file.getOriginalFilename();
	        String uuidFileName = getUuidFileName(originalFileName);
	        bag.setReportId(reportId);
	        bag.setReportImgName(uuidFileName);
	        order++;
	        bag.setReportImgOrder(order);
	        dao.reportImgUpload(bag);
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
	
	public void reportImgDelete(int reportId) {
		List<ReportImgVO> reportImgList = dao.reportImgList(reportId);
		for (ReportImgVO reportImg : reportImgList ) {
			String objectName= reportImg.getReportImgName();
		try {
		    s3Service.deleteObject(objectName);
		    System.out.format("Object %s has been deleted.\n", objectName);
		} catch (Exception e) {
		    e.printStackTrace();
			} 
		}
		dao.reportImgDelete(reportId);
	}
	
}
