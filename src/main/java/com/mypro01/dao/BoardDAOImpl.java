package com.mypro01.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.mypro01.vo.BoardVO;
import com.mypro01.vo.Criteria;

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	@Inject
	private SqlSession sqlSession;
	
	@Override
	public void write(BoardVO boardVO) throws Exception {
		sqlSession.insert("BoardMapper.write", boardVO);
	}

	@Override
	public BoardVO read(Integer bno) throws Exception {
		return sqlSession.selectOne("BoardMapper.read", bno);
	}

	@Override
	public void updateWrite(BoardVO boardVO) throws Exception {
		sqlSession.update("BoardMapper.update", boardVO);
	}

	@Override
	public void delete(Integer bno) throws Exception {
		sqlSession.delete("BoardMapper.delete", bno);
	}

	@Override
	public void readCnt(int bno) throws Exception {
		sqlSession.update("BoardMapper.readCnt", bno);
	}
//-------------------------------------------------------------------
	
	@Override
	public List<BoardVO> listPage(Criteria cri) throws Exception{
		return sqlSession.selectList("BoardMapper.listPage", cri);
	}
	@Override
	public int countPaging(Criteria cri) throws Exception{
		return sqlSession.selectOne("BoardMapper.countPaging", cri);
	}
	
	//파일첨부 정보 저장
	@Override
	public void addAttach(String fullName) throws Exception{
		System.out.println("파파 :"+fullName);
		sqlSession.insert("BoardMapper.addAttach", fullName);
	}
	@Override
	public List<String> getAttach(Integer bno) throws Exception{
		return sqlSession.selectList("BoardMapper.getAttach", bno);
	}
	
}
