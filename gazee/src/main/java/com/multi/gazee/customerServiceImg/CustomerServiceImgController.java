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

import com.multi.gazee.service.CustomerServiceImgService;

@Controller
public class CustomerServiceImgController {
	
	@Autowired
	private csAWSS3Biz s3Service;
	@Autowired
	CustomerServiceImgDAO dao;
	@Autowired
	CustomerServiceImgService service;
	// 파일 업로드 처리
	@RequestMapping(value = "cs/csUploadMultipleFile", method = RequestMethod.POST)
	public @ResponseBody void uploadMultipleFileHandler(@RequestParam("file") List<MultipartFile> multiFileList, HttpSession session, CustomerServiceImgVO bag) {
	    service.uploadMultipleFileHandler(multiFileList, session, bag);
	}
	
	@RequestMapping("cs/csImgDelete")
	public void	csImgDelete(int csId) {
		service.csImgDelete(csId);
	}
}