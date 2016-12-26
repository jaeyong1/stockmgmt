/**
 * DB의 tb_user_log에 매칭되는 클래스
 * 
 * @author jaeyong1.park
 */

package com.lgit.stockmgmt.domain;

public class LogUserItem {
	private int logId;
	private String logDate;
	private String logClientIP;
	private String logUser;
	private String logAction;

	public int getLogId() {
		return logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}

	public String getLogDate() {
		return logDate;
	}

	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}

	public String getLogClientIP() {
		return logClientIP;
	}

	public void setLogClientIP(String logClientIP) {
		this.logClientIP = logClientIP;
	}

	public String getLogUser() {
		return logUser;
	}

	public void setLogUser(String logUser) {
		this.logUser = logUser;
	}

	public String getLogAction() {
		return logAction;
	}

	public void setLogAction(String logAction) {
		this.logAction = logAction;
	}

	@Override
	public String toString() {
		return "LogUserItem [logId=" + logId + ", logDate=" + logDate + ", logClientIP=" + logClientIP + ", logUser="
				+ logUser + ", logAction=" + logAction + "]";
	}

	public LogUserItem(Integer logId, String logDate, String logClientIP, String logUser, String logAction) {
		super();
		this.logId = logId;
		this.logDate = logDate;
		this.logClientIP = logClientIP;
		this.logUser = logUser;
		this.logAction = logAction;
	}

	public LogUserItem(int logId, String logDate, String logClientIP, String logUser, String logAction) {
		super();
		this.logId = logId;
		this.logDate = logDate;
		this.logClientIP = logClientIP;
		this.logUser = logUser;
		this.logAction = logAction;
	}

	public LogUserItem() {
		super();
	}

}
