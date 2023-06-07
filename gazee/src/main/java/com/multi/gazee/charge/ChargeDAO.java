package com.multi.gazee.charge;

import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChargeDAO {
	
	@Autowired
	SqlSessionTemplate my;

	public int insert(ChargeVO charge) {
		return my.insert("charge.insert", charge);
	}
	public int update(String transactionId) {
		return my.update("charge.update", transactionId);
	}
	public ChargeVO select(String transactionId) {
		return my.selectOne("charge.select", transactionId);
	}
    public List<ChargeVO> listCharge() {
        List<ChargeVO> chargeList = my.selectList("charge.listCharge");
        return chargeList;
    }
}