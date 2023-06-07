package com.multi.gazee.service;

import org.springframework.ui.Model;

public interface ChargeService {
    
    /* ADMIN 충전 목록 */
    String getChargeList(Model model) throws Exception;
}