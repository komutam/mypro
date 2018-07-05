package com.mypro01.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mypro01.controller.BoardController;
import com.mypro01.dao.BoardDAO;
import com.mypro01.service.BoardService;
import com.mypro01.vo.BoardVO;
import com.mypro01.vo.Criteria;
import com.mypro01.vo.PageMaker;
import com.mysql.jdbc.StringUtils;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Inject
	private BoardService service;
	
	@RequestMapping(value="/writePage", method=RequestMethod.GET)
	public void writeGET(BoardVO boardVO, Model model) throws Exception{
		
	}
	@RequestMapping(value="/writePage", method=RequestMethod.POST)
	public String writePOST(BoardVO boardVO, RedirectAttributes rttr) throws Exception{
		service.write(boardVO);
		rttr.addFlashAttribute("msg", "success");
		return "redirect:/board/listPage";
	}
	
	@RequestMapping(value="/listPage", method=RequestMethod.GET)
	public void listPage(@ModelAttribute("cri") Criteria cri, Model model) throws Exception{
		model.addAttribute("list", service.listPage(cri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.listCount(cri));
		model.addAttribute("pageMaker", pageMaker);
	}
	
	@RequestMapping(value="/readPage", method=RequestMethod.GET)
	public ModelAndView read(@RequestParam("bno") int bno, Model model, @ModelAttribute("cri") Criteria cri, HttpSession session) throws Exception{
		service.readCnt(bno, session); // 조회수 증가 처리
		ModelAndView mav = new ModelAndView(); // 모델(데이터)+뷰(화면)를 함께 전달하는 객체
		mav.setViewName("board/readPage"); //뷰의 이름
		mav.addObject("dto", service.read(bno)); // 뷰에 전달할 데이터
		return mav;
	}
	
	@RequestMapping(value="/deletePage", method=RequestMethod.POST)
	public String delete(@RequestParam("bno") int bno, Criteria cri, RedirectAttributes rttr) throws Exception{
		service.delete(bno);
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addFlashAttribute("msg", "success");
		return "redirect:/board/listPage";
	}
	
	@RequestMapping(value="/modifyPage", method=RequestMethod.GET)
	public void modifyGET(@RequestParam("bno") int bno, Model model, @ModelAttribute("cri")Criteria cri) throws Exception{
		model.addAttribute(service.read(bno));
	}
	@RequestMapping(value="/modifyPage", method=RequestMethod.POST)
	public String modifyPOST(BoardVO boardVO, RedirectAttributes rttr, Criteria cri) throws Exception{
		service.updateWrite(boardVO);
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addFlashAttribute("msg", "success");
		
		return "redirect:/board/listPage";
	}
	
	//Ajax로 호출되는 특정 게시물의 첨부파일을 처리하는 메소드
	@RequestMapping(value="/getAttach/{bno}")
	@ResponseBody
	public List<String> getAttach(@PathVariable("bno") Integer bno)throws Exception{
		//호출하는 경로는 '/board/getAttach/게시물 번호'의 형태가 되고, 리턴 타입은 첨부파일의 문자열 리스트 형태로 작성
		return service.getAttach(bno);
	}
	
}
