package com.multi.gazee.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.multi.gazee.admin.paging.AdminPageVO;
import com.multi.gazee.customerService.CustomerServiceDAO;
import com.multi.gazee.customerService.CustomerServiceVO;
import com.multi.gazee.customerService.PageVO;
import com.multi.gazee.customerServiceImg.CustomerServiceImgDAO;
import com.multi.gazee.customerServiceImg.CustomerServiceImgVO;
import com.multi.gazee.member.MemberDAO;
import com.multi.gazee.member.MemberVO;

@Service
public class CustomerServiceServiceImpl implements CustomerServiceService{
	
	@Autowired
	CustomerServiceDAO dao;
	
	@Autowired
	CustomerServiceImgDAO dao2;
	
	@Autowired
	MemberDAO memberDao;
	
	public String csList(PageVO vo, Model model, int mode) {
		vo.setStartEnd(vo.getPage());
		List<CustomerServiceVO> list = dao.list(vo);
		List<String> list2 = new ArrayList<String>();
		for (CustomerServiceVO CustomerServiceVO : list) {
			String csWriterId=CustomerServiceVO.getCsWriter();
			MemberVO bag2= memberDao.selectOne(csWriterId);
			String nickname=bag2.getNickname();
			list2.add(nickname);
		}
		int count = dao.count();
		int pages = count / 10 +1;		
		model.addAttribute("list", list);
		model.addAttribute("nickName", list2);
		model.addAttribute("count", count);
		model.addAttribute("pages", pages);
		
		if(mode==2) {
			return "cs/csList2";
		}
		else 
			return "cs/csList";
	}
	
	public void csDelete(CustomerServiceVO bag) {
		dao.csDelete(bag);
	}
	
	public void csWrite(CustomerServiceVO bag, HttpSession session) {
		bag.setCsWriter((String)session.getAttribute("id"));
		dao.csRegister(bag);
		int csId = bag.getCsId();
		session.setAttribute("csId", csId);
	}
	
	public void csUpdate(CustomerServiceVO bag) {
		dao.csUpdate(bag);
	}
	
	public String csCategory(PageVO vo, Model model, String category1, int mode) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("category1", category1);
		List<CustomerServiceVO> qnaCategory = dao.category(map);
		List<String> list2 = new ArrayList<String>();
		for (CustomerServiceVO CustomerServiceVO : qnaCategory) {
			String csWriterId=CustomerServiceVO.getCsWriter();
			MemberVO bag2= memberDao.selectOne(csWriterId);
			String nickname=bag2.getNickname();
			list2.add(nickname);
		}
		int count = dao.countCategory(category1);
		int pages1 = count / 10 +1;
		model.addAttribute("category", qnaCategory);
		model.addAttribute("count", count);
		model.addAttribute("pages1", pages1);
		model.addAttribute("nickName", list2);
		model.addAttribute("categoryValue",category1);
		if(mode==2) {
			return "cs/csCategory2";
		}
		else 
			return "cs/csCategory";
	}
		
	public String search(PageVO vo, String search1, Model model, int mode) {
		vo.setStartEnd(vo.getPage());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", vo.getStart());
		map.put("end", vo.getEnd());
		map.put("search1", search1);
		List<CustomerServiceVO> qnaSearch = dao.search(map); 
		List<String> list2 = new ArrayList<String>();
		for (CustomerServiceVO CustomerServiceVO : qnaSearch) {
			String csWriterId=CustomerServiceVO.getCsWriter();
			MemberVO bag2= memberDao.selectOne(csWriterId);
			String nickname=bag2.getNickname();
			list2.add(nickname);
		}
		int count = dao.countSearch(search1);
		int pages1 = count / 10 +1;
		model.addAttribute("search", qnaSearch);
		model.addAttribute("count", count);
		model.addAttribute("pages1", pages1);
		model.addAttribute("nickName", list2);
		model.addAttribute("searchValue",search1);
		if(mode==2) {
			return "cs/csSearch2";
		}
		else 
			return "cs/csSearch";
	}
	
	public void one(int id, Model model) {
		CustomerServiceVO bag = dao.one(id);
		List<CustomerServiceImgVO> csImgList = dao2.csImgList(id);
		model.addAttribute("csImgList",csImgList);
		System.out.println(csImgList);
		model.addAttribute("bag",bag);
		model.addAttribute("csWriter",bag.getCsWriter());
	}
	
	public String goToCsWrite() {
		return "cs/csWrite";
	}
	
	public String goToCsUpdate(Model model, int id) {
		CustomerServiceVO bag = dao.one(id);
		model.addAttribute("bag",bag);
		return "cs/csUpdate";
	}
	
	public void checkTemporaryCs(Model model, CustomerServiceVO bag) {
		CustomerServiceVO bag2 =dao.checkTemporaryCs(bag);
		if(bag2 !=null) {//임시저장 존재
			model.addAttribute("result",1);
			model.addAttribute("bag",bag2);
			model.addAttribute("csId",bag2.getCsId());
		} else { //임시저장 없음 바로 글쓰기
			model.addAttribute("result",0);
			bag2= new CustomerServiceVO();
			bag2.setCsId(0);
			model.addAttribute("bag",bag2);
		}
	}
	
	@Override
    public String csOne(int id, Model model) {
        CustomerServiceVO csOne = dao.adminOne(id);
        model.addAttribute("csOne", csOne);
        return "admin/adminCsOne";
    }
    
    @Override
    public String getCsList(AdminPageVO pageVo, int pageNumber, Model model) {
        List<CustomerServiceVO> csList = dao.nonPagedList();
        
        /* 페이징 */
        pageVo.setPage(pageNumber);
        pageVo.setStartEnd(pageVo.getPage());
        List<CustomerServiceVO> pagedList = dao.pagedList(pageVo);
        int currentPage = pageVo.getPage();
        int count = dao.count();
        int pages = (int) (count / 10.0 + 1);
    
        model.addAttribute("pagedList", pagedList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pages", pages);
        model.addAttribute("count", count);
        model.addAttribute("csList", csList);
        return "admin/adminCsList";
    }
    
    @Override
    public String csReply(int csId, String replyContent) {
        CustomerServiceVO vo = dao.one(csId);
        vo.setCsReply(replyContent);
        dao.replyRegister(vo);
        return "admin/adminCs";
    }
}
