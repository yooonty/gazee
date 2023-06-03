package com.multi.gazee.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.multi.gazee.customerServiceImg.CustomerServiceImgVO;

public interface CustomerServiceImgService {
	
	void uploadMultipleFileHandler(List<MultipartFile> multiFileList, HttpSession session, CustomerServiceImgVO bag);
	
	void csImgDelete(int csId);
	
}
