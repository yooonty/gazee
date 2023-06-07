package com.multi.gazee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.multi.gazee.charge.ChargeDAO;
import com.multi.gazee.charge.ChargeVO;

import java.util.List;

@Service
public class ChargeServiceImpl implements ChargeService {
    @Autowired
    ChargeDAO Cdao;
    
    @Override
    public String getChargeList(Model model) {
        List<ChargeVO> chargeList = Cdao.listCharge();
        model.addAttribute("chargeList", chargeList);
        return "admin/adminMoneyChargeList";
    }
}