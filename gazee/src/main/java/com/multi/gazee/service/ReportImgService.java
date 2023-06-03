package com.multi.gazee.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.multi.gazee.reportImg.ReportImgVO;

public interface ReportImgService {

	void uploadMultipleFileHandler(List<MultipartFile> multiFileList, HttpSession session, ReportImgVO bag);
	
	void reportImgDelete(int reportId);
	
}
