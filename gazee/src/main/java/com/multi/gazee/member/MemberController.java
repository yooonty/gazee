package com.multi.gazee.member;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.multi.gazee.order.OrderVO;
import com.multi.gazee.productLikes.ProductLikesVO;

@Controller 
public class MemberController {
	
	@Autowired
	MemberDAO dao; 
	
	//비밀번호 암호화
    //로그인
	@RequestMapping(value ="member/login" , produces = "application/text; charset=utf8")  
    @ResponseBody	
    public String login(@RequestParam("id") String id,@RequestParam("pw") String pw, HttpSession session) {
		MemberVO check = dao.logincheck(id);
        System.out.println("테스트>>"+id);
        System.out.println(pw);
        System.out.println(check);
       
        if (check != null) {
        	String hasedpw = check.getPw();        				
        	if (BCrypt.checkpw(pw, hasedpw) == true) {
                session.setAttribute("id", check.getId());
                session.setAttribute("nickname", check.getNickname());
                return "성공";
            } else {
                return "비밀번호가 다릅니다.다시 확인해 주세요.";
            }
        }else {
            return "존재하지 않는 아이디입니다.";
        }
    }
	
	// 회원가입
    @RequestMapping("member/insert")
    @ResponseBody
    public String insert(HttpServletRequest request) {
        
        MemberVO bag = new MemberVO();
        String id = request.getParameter("id");
        String pw = request.getParameter("pw");
        String name = request.getParameter("name");
        String tel = request.getParameter("tel");
        String nickname = request.getParameter("nickname");
        String email = request.getParameter("email");
        String birth = request.getParameter("birth");
        String gender = request.getParameter("gender");
        
        Bcrypt bcry = new Bcrypt();
        String bcryPw = bcry.encrypt(pw);
        bag.setPw(bcryPw);
        bag.setId(id);
        bag.setName(name);
        bag.setTel(tel);
        bag.setNickname(nickname);
        bag.setEmail(email);
        bag.setGender(gender);
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(birth);
            bag.setBirth(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (id.length() >= 6) {
        	dao.insert(bag);	
		}else {
			return "0";
		}      
        return "1";
    }
	
	@RequestMapping("member/searchOne")
	@ResponseBody
	public MemberVO searchOne(String memberId) {
		MemberVO bag = dao.selectOne(memberId);
		return bag;
	}
	
	@RequestMapping(value = "member/idCheck", method = { RequestMethod.GET }, produces = "application/text; charset=utf8")
	@ResponseBody
	public String idCheck(HttpServletRequest req, HttpServletResponse resp, HttpSession session, String id) {
		dao.idCheck(id);
		if (id.length() >= 6) {
			if (dao.idCheck(id) != null) {
				return "기존에 있는 아이디입니다";
			} else {
				return "가입 가능한 아이디입니다.";	
			}
		}else {
			return "6자 이상으로 입력해야합니다.";
		}
	}
	
	// PW찾기 id체크 
	@RequestMapping(value = "member/PWidcheck", method = { RequestMethod.GET }, produces = "application/text; charset=utf8")
	@ResponseBody
	public String PWidcheck(HttpServletRequest req, HttpServletResponse resp, HttpSession session,@ModelAttribute("id") String id) {
		MemberVO vo = dao.idCheck(id);
		if ( vo != null ) {
			return "확인가능한 아이디 입니다.";
		}else {
			return "기존에 없는 아이디입니다.";
		}
	}
	
	// ID찾기 이메일 사용
    @RequestMapping("member/emailcheck")
    public String emailcheck(@ModelAttribute("email") String email, MemberVO bag,Model model) {
    	MemberVO vo = dao.emailCheck(email);
    	if (vo == null) {
			return "0";
		}else {
			model.addAttribute("email", email);
			return "../../member/newId";
		}
    }
    
	//아이디 찾기 이메일 인증
    @RequestMapping(value = "member/IdcheckMail", produces = "application/text; charset=utf8")
    @ResponseBody
    public String IdcheckMail(@ModelAttribute("email") String email) {
    	return dao.joinEmail(email);
    } 
    
    //이메일 인증
    @RequestMapping(value = "member/mailCheck", produces = "application/text; charset=utf8")
  	@ResponseBody
  	public String mailCheck(@ModelAttribute("email") String email) {
  	    return dao.joinEmail(email);
  	}
    
    //비밀번호 찾기 이메일 인증
    @RequestMapping(value = "member/checkMail", produces = "application/text; charset=utf8")
    @ResponseBody
    public String PwcheckMail(@ModelAttribute("email") String email) {
    	return dao.joinEmail(email);
    }
    
    // 회원 탈퇴 
    @RequestMapping(value = "member/delete", produces = "application/text; charset=utf8")
    @ResponseBody
    public String delete(@ModelAttribute("pw") String pw, HttpSession session,RedirectAttributes rttr) {
    	String id =(String)session.getAttribute("id");
    	MemberVO bag = dao.selectOne(id); 	
    	String hasedpw = bag.getPw();
    	System.out.println("컨"+id);
    	System.out.println("트"+pw);
    	System.out.println("롤"+hasedpw);
    	if (BCrypt.checkpw(pw, hasedpw) == true) {
			dao.leave(bag);
			session.invalidate();
	    	return "탈퇴성공";
		}else {
			rttr.addFlashAttribute("msg", false);
			return "탈퇴실패";			
		}
    }
    
    // newPw_패스워드 재설정
    @RequestMapping(value = "member/updatePw",produces = "application/text; charset=utf8")
    @ResponseBody
	public String updatePw(@ModelAttribute("id") String id,@ModelAttribute("pw") String pw,@ModelAttribute("pw2") String pw2, MemberVO bag) {
    	bag = dao.selectOne(id);
		if (bag.getUserLevel() == 2) {
			return "없는 아이디입니다.";
		}else {			
			if (pw.equals(pw2)) {				
				Bcrypt bcry = new Bcrypt();
				String bcryPw = bcry.encrypt(pw);
				System.out.println("비크라이비번"+bcryPw);
				bag.setPw(bcryPw);
				System.out.println("컨트롤러 가방"+bag);
				dao.updatePw(bag);
				return "비밀번호 재설정되었습니다.";
			}else {
				return "비밀번호 확인이 어렵습니다.";
			}		
		}
	}
	
    @RequestMapping("member/logout")
    public String logout(HttpSession session) {
    	dao.logoutTimeUpdate((String)session.getAttribute("id"));
        session.invalidate();
        return "redirect:member.jsp";
    }
    
    @RequestMapping("member/getId")
	@ResponseBody
	public String getId(String email) {
		String id = dao.getId(email);
		return id;
	}
    
    // 비밀번호찾기
    @RequestMapping("member/findPw")
    public String findPw(@ModelAttribute("id") String id,@ModelAttribute("email") String email, MemberVO bag,Model model) {
    	System.out.println(id);
    	MemberVO vo = dao.findPw(id);
    	if (vo == null) {
			return "0";
		}else {
			model.addAttribute("id", id);
			return "../../member/newPw";
		}
    }
    
    @RequestMapping("member/balance")
    public void balance(String id, Model model, HttpSession session) {
        List<OrderVO> list = dao.purchsList(id);
        List<OrderVO> list2 = dao.sellList(id);
        System.out.println("트>> "+list);
        System.out.println("롤>> "+list2);
        model.addAttribute("purchsList", list);
        model.addAttribute("sellList", list2);
    }
    
    // 구매내역 리스트
    @RequestMapping("member/purchsList")
    public void purchsList(String id, Model model) {
        List<OrderVO> list = dao.purchsList(id);
        System.out.println("컨트롤러" + list.size());
        model.addAttribute("purchsList", list);
    }
    // 판매내역 리스트
    @RequestMapping("member/sellList")
    public void sellList(String id, Model model) {
        List<OrderVO> list = dao.sellList(id);
        List<String> sellStatus = new LinkedList<String>();
        List<String> dealType = new LinkedList<String>();
        for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getSetStatus() == 1) {
				sellStatus.add("정산 완료");
			} else if (list.get(i).getCompleteStatus() == 1 && list.get(i).getSetStatus() == 0) {
				sellStatus.add("거래 완료");
			} else if (list.get(i).getCompleteStatus() == 0) {
				sellStatus.add("거래 중");
			}
			if (list.get(i).getDealType().equals("직거래")) {
				dealType.add("직거래");
			} else {
				dealType.add("택배거래");
			}
		}
        System.out.println("컨트롤러sell" + list.size());
        System.out.println(sellStatus);
        System.out.println(dealType);
        model.addAttribute("sellStatus", sellStatus);
        model.addAttribute("sellList", list);
        model.addAttribute("dealType", dealType);
    }
    
    // 찜 목록(리스트)
    @RequestMapping("member/basketList")
    public void basketList(String id, Model model) {
        List<ProductLikesVO> list = dao.basketList(id);
        model.addAttribute("basketList", list);       
    }
    
    //회원정보 수정
    @RequestMapping("member/updateuser")
	public void updateuser(MemberVO bag) {
		dao.updateuser(bag);
	}
    
    //택배사/운송잔번호 업데이트
    @RequestMapping("member/trackingNo")
    @ResponseBody
    public void trackingNo(OrderVO bag) {
    	dao.trackingNo(bag);
    }
    
    // 구매확정 버튼
    @RequestMapping("member/buyerCon")
    @ResponseBody
    public void buyerCon(MemberVO bag) {
    	dao.buyerCon(bag);
    }
    
    @RequestMapping("member/profile")
    @ResponseBody
    public String profile(String id, Model model) {
    	System.out.println("프로파일 이미지>>"+id);
    	MemberVO bag = dao.selectOne(id);
    	String profileImgAddr = "url('http://zurvmfyklzsa17604146.cdn.ntruss.com/" + bag.getProfileImg() + "?type=f&w=120&h=120')";
    	return profileImgAddr;
    }
}