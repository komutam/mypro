package com.mypro01.service;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.mypro01.dao.MemberDAO;
import com.mypro01.dto.LoginDTO;
import com.mypro01.vo.SignupVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Inject
	MemberDAO memberDAO;
	
	//signup
	@Override
	public void insertMember(SignupVO signVO) throws Exception {
		memberDAO.insertMember(signVO);
	}
	//ID check
	@Override
	public int checkId(String userid) throws Exception{
		return memberDAO.checkId(userid);
	}
	
	//login
	@Override
	public SignupVO login(LoginDTO dto) throws Exception{
		return memberDAO.login(dto);
	}
	
	//Auto login
	@Override
	public void keepLogin(String userid, String sessionid, Date next) throws Exception{
		memberDAO.keepLogin(userid, sessionid, next);
	}
	@Override
	public SignupVO checkUserSessionKey(String value) throws Exception{
		return memberDAO.checkUserSessionKey(value);
	}
}
