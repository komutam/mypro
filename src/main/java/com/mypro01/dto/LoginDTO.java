package com.mypro01.dto;

public class LoginDTO {
	
	private String userid;
	private String userpwd;
	private boolean useCookie;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserpwd() {
		return userpwd;
	}
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	public boolean isUseCookie() {
		return useCookie;
	}
	public void setUseCookie(boolean useCookie) {
		this.useCookie = useCookie;
	}
	
	@Override
	public String toString() {
		return "LoginDTO [userid="+userid+", userpwd="+userpwd+", useCookie="+useCookie+"]";
	}
	
}
