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
import com.multi.gazee.service.ReportImgService;

@Controller
public class ReportImgController {
	
	@Autowired
	reportAWSS3Biz s3Service;
	@Autowired
	ReportImgDAO dao;
	@Autowired
	ReportImgService service;
	
	// 파일 업로드 처리
	@RequestMapping(value = "reportImg/reportUploadMultipleFile", method = RequestMethod.POST)
	public @ResponseBody void uploadMultipleFileHandler(@RequestParam("file") List<MultipartFile> multiFileList, HttpSession session, ReportImgVO bag) {
	    service.uploadMultipleFileHandler(multiFileList, session, bag);
	}
	
	@RequestMapping("report/reportImgDelete")
	public void	reportImgDelete(int reportId) {
		service.reportImgDelete(reportId);
	}
}