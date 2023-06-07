package com.multi.gazee.product;

import org.springframework.ui.Model;

public interface ProductService {
    
    /* ADMIN 상품 검색 */
    String searchProduct(String searchType, String searchIndex, Model model) throws Exception;
}