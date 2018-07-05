package com.mypro01.service;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mypro01.dao.BoardDAO;
import com.mypro01.vo.BoardVO;
import com.mypro01.vo.Criteria;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Inject
	private BoardDAO boardDAO;
	
	@Transactional
	@Override
	public void write(BoardVO boardVO) throws Exception {
		if(boardVO.getTitle() == "" || boardVO.getContent() == "") {
			return;
		}else {
			boardDAO.write(boardVO);
		}
		
		//파일첨부가 추가되었기에 글을 등록하면 attach테이블에 첨부파일 이름이 함께 등록되도록 한다.
		//작업의 순서는 먼저 게시물을 등록하는 boardDao.write()를 호출한 후 첨부파일의 이름 배열을 이용해서 각각의 파일 이름을 데이터베이스에 추가하는 형태로 구현
		String[] files = boardVO.getFiles();
		System.out.println("BoardServiceImpl Log : "+Arrays.toString(files));
		System.out.println("BoardServiceImpl Log : "+boardVO);
		if(files == null) {
			System.out.println("BoardServiceImpl Log faile");
			return;
		}
		for(String fileName : files) {
			System.out.println("BoardServiceImpl Log success: "+fileName);
			boardDAO.addAttach(fileName);
		}
	}

	@Override
	public BoardVO read(Integer bno) throws Exception {
		/*boardDAO.readCnt(bno);*/
		return boardDAO.read(bno);
	}
	
	@Override
	public void updateWrite(BoardVO boardVO) throws Exception {
		boardDAO.updateWrite(boardVO);
	}

	@Override
	public void delete(Integer bno) throws Exception {
		boardDAO.delete(bno);
	}

	@Override
	public List<BoardVO> listPage(Criteria cri) throws Exception{
		return boardDAO.listPage(cri);
	}
	@Override
	public int listCount(Criteria cri) throws Exception{
		return boardDAO.countPaging(cri);
	}
	
	@Override
	public void readCnt(int bno, HttpSession session) throws Exception{
		 long update_time = 0;
	        // 세션에 저장된 조회시간 검색
	        // 최초로 조회할 경우 세션에 저장된 값이 없기 때문에 if문은 실행X
	        if(session.getAttribute("update_time_"+bno) != null){
	                                // 세션에서 읽어오기
	            update_time = (long)session.getAttribute("update_time_"+bno);
	        }
	        // 시스템의 현재시간을 current_time에 저장
	        long current_time = System.currentTimeMillis();
	        // 일정시간이 경과 후 조회수 증가 처리 24*60*60*1000(24시간)
	        // 시스템현재시간 - 열람시간 > 일정시간(조회수 증가가 가능하도록 지정한 시간)
	        if(current_time - update_time > 24*60*60*1000){
	            boardDAO.readCnt(bno);
	            // 세션에 시간을 저장 : "update_time_"+bno는 다른변수와 중복되지 않게 명명한 것
	            session.setAttribute("update_time_"+bno, current_time);
	            
	       }
	 }
	
	@Override
	public List<String> getAttach(Integer bno) throws Exception{
		return boardDAO.getAttach(bno);
	}
}
