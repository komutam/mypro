package com.mypro01.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.mypro01.dto.LoginDTO;
import com.mypro01.vo.SignupVO;

@Repository
public class MemberDAOImpl implements MemberDAO {
	
	@Inject
	SqlSession sqlSession;
	
	//signup
	@Override
	public void insertMember(SignupVO signVO) throws Exception {
		sqlSession.insert("memberMapper.insertMember", signVO);
	}
	//Id check
	@Override
	public int checkId(String userid) throws Exception{
		return sqlSession.selectOne("memberMapper.checkId", userid);
	}
	
	//login
	@Override
	public SignupVO login(LoginDTO dto) throws Exception{
		return sqlSession.selectOne("memberMapper.login", dto);
	}
	
	//Auto login
	@Override
	public void keepLogin(String userid, String sessionid, Date next) throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userid", userid);		
		paramMap.put("sessionid", sessionid);		
		paramMap.put("next", next);
		
		sqlSession.update("memberMapper.keepLogin", paramMap);
	}
	public SignupVO checkUserSessionKey(String value) throws Exception{
		return sqlSession.selectOne("memberMapper.checkUserSessionKey", value);
	}
}
