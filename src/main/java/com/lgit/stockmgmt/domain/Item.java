/**
 * Test Code
 * 
 * @author jaeyong1.park
 */

package com.lgit.stockmgmt.domain;

public class Item {
	private String devId;
	private String projecName;

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public String getProjecName() {
		return projecName;
	}

	public void setProjecName(String projecName) {
		this.projecName = projecName;
	}

	public Item(String devId, String projecName) {
		super();
		this.devId = devId;
		this.projecName = projecName;
	}

}