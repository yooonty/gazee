package com.multi.gazee.product;

import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberService;
import com.multi.gazee.member.MemberVO;
import com.multi.gazee.report.ReportDAO;
import com.multi.gazee.report.ReportVO;
import com.multi.gazee.reportCount.ReportCountDAO;
import com.multi.gazee.reportCount.ReportCountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    ProductDAO Pdao;
    
    public String searchProduct(String searchType, String searchIndex, Model model) throws Exception {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("searchType", searchType);
        parameterMap.put("searchIndex", searchIndex);
        
        List<ProductVO> oneWhereList = Pdao.oneWhere(parameterMap);
        model.addAttribute("searchList", oneWhereList);
        
        return "../admin/adminProductSearch";
    }
}

