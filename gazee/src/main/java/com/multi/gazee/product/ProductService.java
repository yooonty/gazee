package com.multi.gazee.product;

import org.springframework.ui.Model;

public interface ProductService {
    String searchProduct(String searchType, String searchIndex, Model model) throws Exception;
}
