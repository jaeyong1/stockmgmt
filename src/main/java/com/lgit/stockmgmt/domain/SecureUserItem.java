/**
 * DB의 tb_user에 매칭되는 클래스
 * 
 * @author jaeyong1.park
 */

package com.lgit.stockmgmt.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SecureUserItem {

	private String userId;
	private String lastLoginedDate;
	private String lastPwChangedDate;
	private int pwErrorCount;
	private int isLocked;
	private int isReseted;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLastLoginedDate() {
		return lastLoginedDate;
	}

	public void setLastLoginedDate(String lastLoginedDate) {
		this.lastLoginedDate = lastLoginedDate;
	}

	public int getPwErrorCount() {
		return pwErrorCount;
	}

	public void setPwErrorCount(int pwErrorCount) {
		this.pwErrorCount = pwErrorCount;
	}

	public String getLastPwChangedDate() {
		return lastPwChangedDate;
	}

	public void setLastPwChangedDate(String lastPwChangedDate) {
		this.lastPwChangedDate = lastPwChangedDate;
	}

	public int getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(int isLocked) {
		this.isLocked = isLocked;
	}

	public int getIsReseted() {
		return isReseted;
	}

	public void setIsReseted(int isReseted) {
		this.isReseted = isReseted;
	}

	/*
	 * mybatis 통할때 생성자 타입 추가 필요했음
	 */

	public SecureUserItem(String userId, String lastLoginedDate, String lastPwChangedDate, Integer pwErrorCount,
			Integer isLocked, Integer isReseted) {
		super();
		this.userId = userId;
		this.lastLoginedDate = lastLoginedDate;
		this.lastPwChangedDate = lastPwChangedDate;
		this.pwErrorCount = pwErrorCount;
		this.isLocked = isLocked;
		this.isReseted = isReseted;
	}

	public SecureUserItem(String userId, java.sql.Date lastLoginedDate, java.sql.Date lastPwChangedDate,
			Integer pwErrorCount, Integer isLocked, Integer isReseted) {
		super();
		this.userId = userId;

		DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
		String text1 = df1.format(lastLoginedDate);

		this.lastLoginedDate = text1;

		DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
		String text2 = df2.format(lastLoginedDate);

		this.lastPwChangedDate = text2;
		this.pwErrorCount = pwErrorCount;
		this.isLocked = isLocked;
		this.isReseted = isReseted;
	}

	@Override
	public String toString() {
		return "SecureUserItem [userId=" + userId + ", lastLoginedDate=" + lastLoginedDate + ", lastPwChangedDate="
				+ lastPwChangedDate + ", pwErrorCount=" + pwErrorCount + ", isLocked=" + isLocked + ", isReseted="
				+ isReseted + "]";
	}

}
