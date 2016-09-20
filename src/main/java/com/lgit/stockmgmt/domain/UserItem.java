/**
 * DB의 tb_user에 매칭되는 클래스
 * 
 * @author jaeyong1.park
 */
 
package com.lgit.stockmgmt.domain;

public class UserItem {
	private String userId;
	private String userPassword;
	private String userEmail;
	private String userTeamname;
	private int userLevel;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserTeamname() {
		return userTeamname;
	}

	public void setUserTeamname(String userTeamname) {
		this.userTeamname = userTeamname;
	}

	public int getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}

	public UserItem(String userId, String userPassword, String userEmail, String userTeamname, int userLevel) {
		super();
		this.userId = userId;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.userTeamname = userTeamname;
		this.userLevel = userLevel;
	}
	
	/*
	 * mybatis 통할때 생성자 타입 추가 필요했음
	 */
	
	public UserItem(String userId, String userPassword, String userEmail, String userTeamname, Integer userLevel) {
		super();
		this.userId = userId;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.userTeamname = userTeamname;
		this.userLevel =  Integer.valueOf(userLevel);
	}

	

}
