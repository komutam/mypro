package com.mypro01.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CommonExceptionAdvice {

	private static final Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);

	@ExceptionHandler(Exception.class)
	public ModelAndView errorModelAndView(Exception ex) {//errorModelAndView()에서는 내부적으로 ModelAndView의 객체를 생성하고 반환
		ModelAndView mv = new ModelAndView();
		//ModelAndView는 하나의 객체에 Model 데이터와 View 의 처리를 동시에 할 수 있는 객체
		mv.setViewName("error_common");
		mv.addObject("exception", ex);
		return mv;
	}
}