/**
 * DB의 tb_parts에 매칭되는 클래스
 * 
 * @author jaeyong1.park
 */

package com.lgit.stockmgmt.domain;

import ch.qos.logback.core.net.SyslogOutputStream;

public class PartsItem {
	private int partId;
	private String partProjectCode;
	private String partName;
	private String partDesc;
	private String partLocation;
	private float partCost;
	private int partStock;
	private String partMemo;

	/*
	 * Page calc [start]
	 */
	private int currentPageNum;
	private int rowsPerPage;
	private int _pageOffset; // Not need Setter, Getter

	public int getCurrentPageNum() {
		return currentPageNum;
	}

	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum;
		this._pageOffset = this.rowsPerPage * this.currentPageNum;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
		this._pageOffset = this.rowsPerPage * this.currentPageNum;
	}
	/*
	 * page calc [end]
	 */

	/*
	 * Search Info [start]
	 */
	private String Keyword;
	private int SrchType;

	public String getKeyword() {
		return Keyword;
	}

	public void setKeyword(String keyword) {
		Keyword = keyword;
	}

	public int getSrchType() {
		return SrchType;
	}

	public void setSrchType(int srchType) {
		SrchType = srchType;
	}
	/*
	 * Search Info [end]
	 */

	public int getPartId() {
		return partId;
	}

	public void setPartId(int partId) {
		this.partId = partId;
	}

	public String getPartProjectCode() {
		return partProjectCode;
	}

	public void setPartProjectCode(String partProjectCode) {
		this.partProjectCode = partProjectCode;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartDesc() {
		return partDesc;
	}

	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}

	public String getPartLocation() {
		return partLocation;
	}

	public void setPartLocation(String partLocation) {
		this.partLocation = partLocation;
	}

	public Float getPartCost() {
		return partCost;
	}

	public void setPartCost(Float partCost) {
		this.partCost = partCost;
	}

	public int getPartStock() {
		return partStock;
	}

	public void setPartStock(int partStock) {
		this.partStock = partStock;
	}

	public String getPartMemo() {
		return partMemo;
	}

	public void setPartMemo(String partMemo) {
		this.partMemo = partMemo;
	}

	public PartsItem(int partId, String partProjectCode, String partName, String partDesc, String partLocation,
			float partCost, int partStock, String partMemo) {
		super();
		this.partId = partId;
		this.partProjectCode = partProjectCode;
		this.partName = partName;
		this.partDesc = partDesc;
		this.partLocation = partLocation;
		this.partCost = partCost;
		this.partStock = partStock;
		this.partMemo = partMemo;

		this._pageOffset = 0;
		this.currentPageNum = 0;
		this.rowsPerPage = 0;
	}
	public PartsItem(Integer partId, String partProjectCode, String partName, String partDesc, String partLocation,
			
			/*
			 * mybatis 통할때 생성자 타입 추가 필요했음
			 */
			Float partCost, Integer partStock, String partMemo) {

		super();
		this.partId = Integer.valueOf(partId);
		this.partProjectCode = partProjectCode;
		this.partName = partName;
		this.partDesc = partDesc;
		this.partLocation = partLocation;
		this.partCost = Float.valueOf(partCost);
		this.partStock = Integer.valueOf(partStock);
		this.partMemo = partMemo;

		this._pageOffset = 0;
		this.currentPageNum = 0;
		this.rowsPerPage = 0;
	}

	@Override
	public String toString() {
		return "PartsItem [partId=" + partId + ", partProjectCode=" + partProjectCode + ", partName=" + partName
				+ ", partDesc=" + partDesc + ", partLocation=" + partLocation + ", partCost=" + partCost
				+ ", partStock=" + partStock + ", partMemo=" + partMemo + "]";
	}

	public PartsItem() {
		super();
		this.partId = 0;
		this.partProjectCode = "";
		this.partName = "";
		this.partDesc = "";
		this.partLocation = "";
		this.partCost = 0;
		this.partStock = 0;
		this.partMemo = "";

		this._pageOffset = 0;
		this.currentPageNum = 0;
		this.rowsPerPage = 0;
	}
}
