package com.multi.gazee.reportImg;

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

import com.amazonaws.SdkClientException;

@Controller
public class ReportImgController {
	
	@Autowired
	private reportAWSS3Biz s3Service;
	@Autowired
	ReportImgDAO dao;
	
	// 파일 업로드 처리
	@RequestMapping(value = "reportImg/reportUploadMultipleFile", method = RequestMethod.POST)
	public @ResponseBody String uploadMultipleFileHandler(@RequestParam("file") List<MultipartFile> multiFileList, HttpSession session, ReportImgVO bag) {
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
	
	@RequestMapping("customerService/report/reportImgDelete")
	public void	reportImgDelete(int reportId) {
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