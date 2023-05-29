package com.multi.gazee.charge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.List;

@Service
public class ChargeServiceImpl implements ChargeService {
    @Autowired
    ChargeDAO Cdao;
    
    public String getChargeList(Model model) {
        List<ChargeVO> chargeList = Cdao.listCharge();
        model.addAttribute("chargeList", chargeList);
        return "../admin/adminMoneyChargeList";
    }
}
