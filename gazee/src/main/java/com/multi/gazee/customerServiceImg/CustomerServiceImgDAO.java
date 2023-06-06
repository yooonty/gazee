package com.multi.gazee.customerServiceImg;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.multi.gazee.customerService.CustomerServiceVO;

@Component
public class CustomerServiceImgDAO {

	@Autowired
	SqlSessionTemplate my;
	
	public int csImgUpload(CustomerServiceImgVO bag) {
		int result = my.insert("csImg.csImgUpload", bag);
		System.out.println(bag);
		return result;
	}
	
	public List<CustomerServiceImgVO> csImgList(int csId){
		List<CustomerServiceImgVO> csImgList = my.selectList("csImg.csImgList",csId);
		return csImgList;
	}
	
	public void csImgDelete(int csId) {
		my.delete("csImg.csImgDel", csId);
	}
	
}
