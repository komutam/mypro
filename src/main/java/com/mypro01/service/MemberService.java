package com.mypro01.service;

import java.util.Date;

import com.mypro01.dto.LoginDTO;
import com.mypro01.vo.SignupVO;

public interface MemberService {
	
	//Signup
	public void insertMember(SignupVO signVO) throws Exception;
	
	//ID check
	public int checkId(String userid) throws Exception;
	
	//login
	public SignupVO login(LoginDTO dto) throws Exception;
	
	//Auto login
	public void keepLogin(String userid, String sessionid, Date next) throws Exception;
	public SignupVO checkUserSessionKey(String value) throws Exception;
	
}
