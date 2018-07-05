package com.mypro01.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	//preHandle()
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		HttpSession session = request.getSession();
		
		if(session.getAttribute("login") != null) {
			logger.info("clear login");
			session.removeAttribute("login");
		}
		return true;
	}
	//postHandle()
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
		
		HttpSession session = request.getSession();
		
		ModelMap modelMap = modelAndView.getModelMap();
		Object signupVO = modelMap.get("signupVO");
		
		if(signupVO != null) {
			logger.info("new log success");
			session.setAttribute("login", signupVO);
			
			//Auto login cookie create
			if(request.getParameter("useCookie") != null) {
				logger.info("remember!");
				Cookie loginCookie = new Cookie("loginCookie", session.getId());
				loginCookie.setPath("/mypro01/");
				
				loginCookie.setMaxAge(60 * 60 * 24 * 7);
				response.addCookie(loginCookie);
			}// Auto login cookie create End...............
			
			
			Object dest=session.getAttribute("dest");
			response.sendRedirect(dest !=null ? (String)dest : "/mypro01/");
		}else {
			response.sendRedirect("/mypro01/member/login");
		}	
	}
	
	
	
	
	
	
	
}
