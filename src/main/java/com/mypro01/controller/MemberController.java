package com.mypro01.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.mypro01.dto.LoginDTO;
import com.mypro01.service.MemberService;
import com.mypro01.vo.SignupVO;

@Controller
@RequestMapping("/member/*")
public class MemberController {

	@Inject
	private MemberService memberService;
	
	//signup GET
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public void signupGET() {
		
	}
	//signup POST
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signupPOST(SignupVO signVO, RedirectAttributes rttr) throws Exception {
		memberService.insertMember(signVO);
		rttr.addFlashAttribute("msg", "success");
		return "redirect:/";
	}
	
	//ID Check
	@ResponseBody
	@RequestMapping(value="/checkId", method= {RequestMethod.GET, RequestMethod.POST})
	public Map<Object, Object> checkId(String userid) throws Exception{
		Map<Object, Object> map = new HashMap<Object, Object>();
		int count = memberService.checkId(userid);
		map.put("check", count);
		return map;
	}
//---------------------------------------------------------------
	
	//loginGET
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public void loginGET(@ModelAttribute("dto") LoginDTO dto) {
		
	}
	//loginPOST
	@RequestMapping(value="/loginPost", method=RequestMethod.POST)
	public void loginPOST(LoginDTO dto, HttpSession session, Model model) throws Exception{
		try {
			SignupVO vo = memberService.login(dto);
			
			if(vo==null) {
				return;
			}
			model.addAttribute("signupVO", vo);
			
			//Auto login select
			if(dto.isUseCookie()) {
				int amount = 60 * 60 * 24 * 7;
				Date sessionLimit = new Date(System.currentTimeMillis()+(1000 * amount));
				memberService.keepLogin(vo.getUserid(), session.getId(), sessionLimit);
			}// end............................
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//logout
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		Object obj = session.getAttribute("login");
		
		if(obj != null) {
			SignupVO vo = (SignupVO) obj;
			
			session.removeAttribute("login");
			session.invalidate();
			
			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
			
			response.sendRedirect("/mypro01/");
			
			if(loginCookie != null) {
				loginCookie.setPath("/mypro01/");
				loginCookie.setMaxAge(0);
				response.addCookie(loginCookie);
				memberService.keepLogin(vo.getUserid(), session.getId(), new Date());
			}
		}
	}
}
