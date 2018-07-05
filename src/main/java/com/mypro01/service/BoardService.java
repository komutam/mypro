package com.mypro01.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.mypro01.vo.BoardVO;
import com.mypro01.vo.Criteria;

public interface BoardService {

	public void write(BoardVO boardVO) throws Exception;
	
	public BoardVO read(Integer bno) throws Exception;
	
	public void updateWrite(BoardVO boardVO) throws Exception;
	
	public void delete(Integer bno) throws Exception;
	
	public List<BoardVO> listPage(Criteria cri) throws Exception;
	
	public int listCount(Criteria cri) throws Exception;
	
	public void readCnt(int bno, HttpSession session) throws Exception;
	
	//첨부파일 조회(첨부파일들 정보 가져오기)
	public List<String> getAttach(Integer bno) throws Exception;
}
