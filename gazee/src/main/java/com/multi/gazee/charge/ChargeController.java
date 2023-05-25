package com.multi.gazee.charge;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;

@Controller
public class ChargeController {
	
	@Autowired
	ChargeDAO chargeDAO;
	
	@Autowired
	MemberDAO memberDAO;
	
	@RequestMapping(value="pay/charge", method = {RequestMethod.POST})
	public void payCharge(ChargeVO charge, HttpSession session) {
		System.out.println("charge 도달");
		String memberId = String.valueOf(session.getAttribute("id"));
		MemberVO member = memberDAO.selectOne(memberId);
		charge.setMemberId(memberId);
		
		chargeDAO.charge(charge, member);
	}
	
	
}
