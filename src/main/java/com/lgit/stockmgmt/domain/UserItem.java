/**
 * DB의 tb_user에 매칭되는 클래스
 * 
 * @author jaeyong1.park
 */

package com.lgit.stockmgmt.domain;

import java.util.Random;

public class UserItem {
	private String userId;
	private String userName;
	private String userPassword;
	private String userEmail;
	private String userTeamname;
	private int userLevel;
	private int cartItems;
	private int cartItemsOthers;

	public int getCartItemsOthers() {
		return cartItemsOthers;
	}

	public void setCartItemsOthers(int cartItemsOthers) {
		this.cartItemsOthers = cartItemsOthers;
	}

	public int getCartItems() {
		return cartItems;
	}

	public void setCartItems(int cartItems) {
		this.cartItems = cartItems;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public UserItem(String userId, String userName, String userPassword, String userEmail, String userTeamname,
			int userLevel) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.userTeamname = userTeamname;
		this.userLevel = userLevel;
		
	}

	/*
	 * mybatis 통할때 생성자 타입 추가 필요했음
	 */

	public UserItem(String userId, String userName, String userPassword, String userEmail, String userTeamname,
			Integer userLevel) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.userTeamname = userTeamname;
		this.userLevel = Integer.valueOf(userLevel);
		
	}

	@Override
	public String toString() {
		return "UserItem [userId=" + userId + ", userName=" + userName + ", userPassword=" + userPassword
				+ ", userEmail=" + userEmail + ", userTeamname=" + userTeamname + ", userLevel=" + userLevel + "]";
	}

	/*
	 * 기본생성자 - DB PK중복방지위해 임의값 생성
	 */
	public UserItem() {
		super();
		Random rand = new Random();

		this.userId = "test" + String.valueOf(rand.nextInt(10000) + 1);
		this.userName = String.valueOf(rand.nextInt(10000) + 1);
		this.userPassword = String.valueOf(rand.nextInt(10000) + 1);
		this.userEmail = String.valueOf(rand.nextInt(10000) + 1);
		this.userTeamname = String.valueOf(rand.nextInt(10000) + 1);
		this.userLevel = Integer.valueOf(rand.nextInt(10000) + 1);
	}

}
