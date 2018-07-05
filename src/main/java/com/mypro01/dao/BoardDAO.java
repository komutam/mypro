package com.mypro01.dao;

import java.util.List;
import com.mypro01.vo.BoardVO;
import com.mypro01.vo.Criteria;

public interface BoardDAO {

	public void write(BoardVO boardVO) throws Exception;
	
	public BoardVO read(Integer bno) throws Exception;
	
	public void updateWrite(BoardVO boardVO) throws Exception;
	
	public void delete(Integer bno) throws Exception;
	
	public void readCnt(int bno) throws Exception;
	
	public List<BoardVO> listPage(Criteria cri) throws Exception;
	
	public int countPaging(Criteria cri) throws Exception;
	
	//파일첨부 정보 저장
	public void addAttach(String fullName) throws Exception;
	//게시물의 첨부파일을 시간 순서대로 가져오기
	public List<String> getAttach(Integer bno) throws Exception;
}
