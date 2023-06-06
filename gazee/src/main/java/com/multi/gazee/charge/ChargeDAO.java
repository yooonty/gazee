package com.multi.gazee.charge;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChargeDAO {
    @Autowired
    SqlSessionTemplate my;
    
    /* ALL */
    public List<ChargeVO> listCharge() {
        List<ChargeVO> chargeList = my.selectList("charge.listCharge");
        return chargeList;
    }
    
}
