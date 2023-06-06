package com.multi.gazee.admin.excel;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

public interface ExcelService {
    ResponseEntity<InputStreamResource> memberExcel() throws Exception;
    
    ResponseEntity<InputStreamResource> productExcel() throws Exception;
    
    ResponseEntity<InputStreamResource> orderExcel() throws Exception;
    
    ResponseEntity<InputStreamResource> withdrawExcel() throws Exception;
    
    ResponseEntity<InputStreamResource> chargeExcel() throws Exception;
}