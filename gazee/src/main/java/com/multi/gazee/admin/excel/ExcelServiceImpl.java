package com.multi.gazee.admin.excel;

import com.multi.gazee.charge.ChargeDAO;
import com.multi.gazee.charge.ChargeVO;
import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.order.OrderDAO;
import com.multi.gazee.order.OrderVO;
import com.multi.gazee.product.ProductDAO;
import com.multi.gazee.product.ProductVO;
import com.multi.gazee.withdraw.WithdrawDAO;
import com.multi.gazee.withdraw.WithdrawVO;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {
    @Autowired
    OrderDAO Odao;
    @Autowired
    MemberDAO Mdao;
    @Autowired
    ProductDAO Pdao;
    @Autowired
    WithdrawDAO Wdao;
    @Autowired
    ChargeDAO Cdao;
    
    public ResponseEntity<InputStreamResource> memberExcel() throws Exception {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("회원목록");
            int rowNo = 0;
            
            CellStyle headStyle = workbook.createCellStyle();
            headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_BLUE.getIndex());
            Font font = workbook.createFont();
            font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
            font.setFontHeightInPoints((short) 13);
            headStyle.setFont(font);
            
            Row headerRow = sheet.createRow(rowNo++);
            headerRow.createCell(0).setCellValue("No");
            headerRow.createCell(1).setCellValue("ID");
            headerRow.createCell(2).setCellValue("이름");
            headerRow.createCell(3).setCellValue("Email");
            headerRow.createCell(4).setCellValue("가입일시");
            headerRow.createCell(5).setCellValue("회원상태");
            
            List<MemberVO> list = Mdao.listExceptAdmin();
            for (MemberVO vo : list) {
                int no = vo.getNo();
                String id = vo.getId();
                String name = vo.getName();
                String email = vo.getEmail();
                String date = String.valueOf(vo.getJoinDate());
                String status = vo.getStatus();
                Row row = sheet.createRow(rowNo++);
                row.createCell(0).setCellValue(no);
                row.createCell(1).setCellValue(id);
                row.createCell(2).setCellValue(name);
                row.createCell(3).setCellValue(email);
                row.createCell(4).setCellValue(date);
                row.createCell(5).setCellValue(status);
            }
            
            sheet.setColumnWidth(0, 3000);
            sheet.setColumnWidth(1, 3000);
            sheet.setColumnWidth(2, 4000);
            sheet.setColumnWidth(3, 4000);
            sheet.setColumnWidth(4, 4000);
            sheet.setColumnWidth(5, 4000);
            
            File tmpFile = File.createTempFile("TMP~", ".xlsx");
            try (OutputStream fos = new FileOutputStream(tmpFile);) {
                workbook.write(fos);
            }
            InputStream res = new FileInputStream(tmpFile) {
                @Override
                public void close() throws IOException {
                    super.close();
                }
            };
            
            return ResponseEntity.ok() //
                    .contentLength(tmpFile.length()) //
                    .contentType(MediaType.APPLICATION_OCTET_STREAM) //
                    .header("Content-Disposition", "attachment;filename=member_list.xlsx") //
                    .body(new InputStreamResource(res));
        }
    }
    
    public ResponseEntity<InputStreamResource> productExcel() throws Exception {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("회원목록");
            int rowNo = 0;
            
            CellStyle headStyle = workbook.createCellStyle();
            headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_BLUE.getIndex());
            Font font = workbook.createFont();
            font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
            font.setFontHeightInPoints((short) 13);
            headStyle.setFont(font);
            
            Row headerRow = sheet.createRow(rowNo++);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("등록일시");
            headerRow.createCell(2).setCellValue("상품명");
            headerRow.createCell(3).setCellValue("판매자");
            headerRow.createCell(4).setCellValue("판매가격");
            
            List<ProductVO> list = Pdao.list();
            for (ProductVO vo : list) {
                int id = vo.getProductId();
                String date = String.valueOf(vo.getSavedTime());
                String name = vo.getProductName();
                String sellerId = vo.getMemberId();
                String price = String.valueOf(vo.getPrice());
                Row row = sheet.createRow(rowNo++);
                row.createCell(0).setCellValue(id);
                row.createCell(1).setCellValue(date);
                row.createCell(2).setCellValue(name);
                row.createCell(3).setCellValue(sellerId);
                row.createCell(4).setCellValue(price + "원");
            }
            
            sheet.setColumnWidth(0, 3000);
            sheet.setColumnWidth(1, 3000);
            sheet.setColumnWidth(2, 4000);
            sheet.setColumnWidth(3, 4000);
            sheet.setColumnWidth(4, 4000);
            
            File tmpFile = File.createTempFile("TMP~", ".xlsx");
            try (OutputStream fos = new FileOutputStream(tmpFile);) {
                workbook.write(fos);
            }
            InputStream res = new FileInputStream(tmpFile) {
                @Override
                public void close() throws IOException {
                    super.close();
                }
            };
            
            return ResponseEntity.ok() //
                    .contentLength(tmpFile.length()) //
                    .contentType(MediaType.APPLICATION_OCTET_STREAM) //
                    .header("Content-Disposition", "attachment;filename=product_list.xlsx") //
                    .body(new InputStreamResource(res));
        }
    }
    
    public ResponseEntity<InputStreamResource> orderExcel() throws Exception {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Exported Document");
            int rowNo = 0;
            
            CellStyle headStyle = workbook.createCellStyle();
            headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_BLUE.getIndex());
            Font font = workbook.createFont();
            font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
            font.setFontHeightInPoints((short) 13);
            headStyle.setFont(font);
            
            Row headerRow = sheet.createRow(rowNo++);
            headerRow.createCell(0).setCellValue("거래 No");
            headerRow.createCell(1).setCellValue("판매자");
            headerRow.createCell(2).setCellValue("구매자");
            headerRow.createCell(3).setCellValue("결제 시간");
            
            List<OrderVO> list = Odao.listOrder();
            for (OrderVO vo : list) {
                int no = vo.getNo();
                String sellerId = vo.getSellerId();
                String buyerId = vo.getBuyerId();
                String paymentTime = String.valueOf(vo.getPaymentTime());
                Row row = sheet.createRow(rowNo++);
                row.createCell(0).setCellValue(no);
                row.createCell(1).setCellValue(sellerId);
                row.createCell(2).setCellValue(buyerId);
                row.createCell(3).setCellValue(paymentTime);
            }
            
            sheet.setColumnWidth(0, 3000);
            sheet.setColumnWidth(1, 3000);
            sheet.setColumnWidth(2, 4000);
            sheet.setColumnWidth(3, 5000);
            
            File tmpFile = File.createTempFile("TMP~", ".xlsx");
            try (OutputStream fos = new FileOutputStream(tmpFile);) {
                workbook.write(fos);
            }
            InputStream res = new FileInputStream(tmpFile) {
                @Override
                public void close() throws IOException {
                    super.close();
                }
            };
            
            return ResponseEntity.ok() //
                    .contentLength(tmpFile.length()) //
                    .contentType(MediaType.APPLICATION_OCTET_STREAM) //
                    .header("Content-Disposition", "attachment;filename=order_list.xlsx") //
                    .body(new InputStreamResource(res));
        }
    }
    
    public ResponseEntity<InputStreamResource> withdrawExcel() throws Exception {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Exported Document");
            int rowNo = 0;
            
            CellStyle headStyle = workbook.createCellStyle();
            headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_BLUE.getIndex());
            Font font = workbook.createFont();
            font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
            font.setFontHeightInPoints((short) 13);
            headStyle.setFont(font);
    
            Row headerRow = sheet.createRow(rowNo++);
            headerRow.createCell(0).setCellValue("No");
            headerRow.createCell(1).setCellValue("신청일시");
            headerRow.createCell(2).setCellValue("신청자");
            headerRow.createCell(3).setCellValue("출금신청금액");
            headerRow.createCell(4).setCellValue("실출금액");
            headerRow.createCell(5).setCellValue("수수료");
            headerRow.createCell(6).setCellValue("출금계좌");
            
            List<WithdrawVO> list = Wdao.listWithdraw();
            for (WithdrawVO vo : list) {
                int no = vo.getNo();
                String date = String.valueOf(vo.getTransactionTime());
                String id = vo.getMemberId();
                int amount = vo.getTotalAmount();
                int req_amount = vo.getRequestedAmount();
                int commission = vo.getCommission();
                String account = vo.getBank() + "/" +  vo.getAccount();
                Row row = sheet.createRow(rowNo++);
                row.createCell(0).setCellValue(no);
                row.createCell(1).setCellValue(date);
                row.createCell(2).setCellValue(id);
                row.createCell(3).setCellValue(amount + "원");
                row.createCell(4).setCellValue(req_amount + "원");
                row.createCell(5).setCellValue(commission + "원");
                row.createCell(6).setCellValue(account);
            }
            
            sheet.setColumnWidth(0, 3000);
            sheet.setColumnWidth(1, 3000);
            sheet.setColumnWidth(2, 4000);
            sheet.setColumnWidth(3, 5000);
            sheet.setColumnWidth(4, 5000);
            sheet.setColumnWidth(5, 5000);
            sheet.setColumnWidth(6, 6000);
            
            File tmpFile = File.createTempFile("TMP~", ".xlsx");
            try (OutputStream fos = new FileOutputStream(tmpFile);) {
                workbook.write(fos);
            }
            InputStream res = new FileInputStream(tmpFile) {
                @Override
                public void close() throws IOException {
                    super.close();
                }
            };
            
            return ResponseEntity.ok() //
                    .contentLength(tmpFile.length()) //
                    .contentType(MediaType.APPLICATION_OCTET_STREAM) //
                    .header("Content-Disposition", "attachment;filename=withdraw_list.xlsx") //
                    .body(new InputStreamResource(res));
        }
    }
    
    public ResponseEntity<InputStreamResource> chargeExcel() throws Exception {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Exported Document");
            int rowNo = 0;
            
            CellStyle headStyle = workbook.createCellStyle();
            headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_BLUE.getIndex());
            Font font = workbook.createFont();
            font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
            font.setFontHeightInPoints((short) 13);
            headStyle.setFont(font);
            
            Row headerRow = sheet.createRow(rowNo++);
            headerRow.createCell(0).setCellValue("No");
            headerRow.createCell(1).setCellValue("충전시각");
            headerRow.createCell(2).setCellValue("충전회원 ID");
            headerRow.createCell(3).setCellValue("충전금액");
            headerRow.createCell(4).setCellValue("충전방법");
            
            List<ChargeVO> list = Cdao.listCharge();
            for (ChargeVO vo : list) {
                int no = vo.getNo();
                String date = String.valueOf(vo.getTransactionTime());
                String id = vo.getMemberId();
                int amount = vo.getAmount();
                int method = vo.getPayMethod();
                Row row = sheet.createRow(rowNo++);
                row.createCell(0).setCellValue(no);
                row.createCell(1).setCellValue(date);
                row.createCell(2).setCellValue(id);
                row.createCell(3).setCellValue(amount + "원");
                if (method == 1) {
                    row.createCell(4).setCellValue("신용카드");
                } else {
                    row.createCell(4).setCellValue("계좌이체");
                }
            }
            
            sheet.setColumnWidth(0, 3000);
            sheet.setColumnWidth(1, 3000);
            sheet.setColumnWidth(2, 4000);
            sheet.setColumnWidth(3, 5000);
            sheet.setColumnWidth(4, 5000);
            
            File tmpFile = File.createTempFile("TMP~", ".xlsx");
            try (OutputStream fos = new FileOutputStream(tmpFile);) {
                workbook.write(fos);
            }
            InputStream res = new FileInputStream(tmpFile) {
                @Override
                public void close() throws IOException {
                    super.close();
                }
            };
            
            return ResponseEntity.ok() //
                    .contentLength(tmpFile.length()) //
                    .contentType(MediaType.APPLICATION_OCTET_STREAM) //
                    .header("Content-Disposition", "attachment;filename=charge_list.xlsx") //
                    .body(new InputStreamResource(res));
        }
    }
}