package com.mypro01.interceptor;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.mypro01.interceptor.AuthInterceptor;
import com.mypro01.service.MemberService;
import com.mypro01.vo.SignupVO;

public class AuthInterceptor extends HandlerInterceptorAdapter{

	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);	
	
	@Inject
	private MemberService service;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		HttpSession session = request.getSession();
		if(session.getAttribute("login") == null) {
			logger.info("no login");
			saveDest(request);
			
			//if exist -> loginCookie in Cookie
			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
			if(loginCookie != null) {
				SignupVO signupVO = service.checkUserSessionKey(loginCookie.getValue());
				logger.info("VO : "+signupVO);
				if(signupVO != null) {
					session.setAttribute("login", signupVO);
					return true;
				}
			}//----------------------------------end
			
			response.sendRedirect("/mypro01/member/login");
			return false;
		}
		return true;
	}
	
	private void saveDest(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String query = request.getQueryString();
		
		if(query == null || query.equals("null")) {
			query = "";
		}else {
			query = "?"+query;
		}
		if(request.getMethod().equals("GET")) {
			logger.info("dest"+(uri+query));
			request.getSession().setAttribute("dest", uri+query);
		}
	}
	
}
