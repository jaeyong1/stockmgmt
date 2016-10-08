package com.lgit.stockmgmt.domain;

public class JoinDBItem {
	private int partId;
	private String partProjectCode;
	private String userTeamname;
	private String userOwnerName;
	private String userShipperName;

	private String partName;
	private String partDesc;
	private int partStock;
	private String partLocation;
	private float partCost;
	private String partMemo;

	@Override
	public String toString() {
		return "JoinDBItem [partId=" + partId + ", partProjectCode=" + partProjectCode + ", userTeamname="
				+ userTeamname + ", userOwnerName=" + userOwnerName + ", userShipperName=" + userShipperName
				+ ", partName=" + partName + ", partDesc=" + partDesc + ", partStock=" + partStock + ", partLocation="
				+ partLocation + ", partCost=" + partCost + ", partMemo=" + partMemo + "]";
	}

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

	public String getUserTeamname() {
		return userTeamname;
	}

	public void setUserTeamname(String userTeamname) {
		this.userTeamname = userTeamname;
	}

	public String getUserOwnerName() {
		return userOwnerName;
	}

	public void setUserOwnerName(String userOwnerName) {
		this.userOwnerName = userOwnerName;
	}

	public String getUserShipperName() {
		return userShipperName;
	}

	public void setUserShipperName(String userShipperName) {
		this.userShipperName = userShipperName;
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

	public int getPartStock() {
		return partStock;
	}

	public void setPartStock(int partStock) {
		this.partStock = partStock;
	}

	public String getPartLocation() {
		return partLocation;
	}

	public void setPartLocation(String partLocation) {
		this.partLocation = partLocation;
	}

	public float getPartCost() {
		return partCost;
	}

	public void setPartCost(float partCost) {
		this.partCost = partCost;
	}

	public String getPartMemo() {
		return partMemo;
	}

	public void setPartMemo(String partMemo) {
		this.partMemo = partMemo;
	}

	public JoinDBItem(int partId, String partProjectCode, String userTeamname, String userOwnerName,
			String userShipperName, String partName, String partDesc, int partStock, String partLocation,
			float partCost, String partMemo) {
		super();
		this.partId = partId;
		this.partProjectCode = partProjectCode;
		this.userTeamname = userTeamname;
		this.userOwnerName = userOwnerName;
		this.userShipperName = userShipperName;
		this.partName = partName;
		this.partDesc = partDesc;
		this.partStock = partStock;
		this.partLocation = partLocation;
		this.partCost = partCost;
		this.partMemo = partMemo;
	}

	public JoinDBItem() {
		super();
	}

	/*
	 * mybatis 통할때 생성자 타입 추가 필요했음
	 */
	public JoinDBItem(Integer partId, String partProjectCode, String userTeamname, String userOwnerName,
			String userShipperName, String partName, String partDesc, Integer partStock, String partLocation,
			Float partCost, String partMemo) {
		super();

		this.partId = Integer.valueOf(partId);
		this.partProjectCode = partProjectCode;
		this.userTeamname = userTeamname;
		this.userOwnerName = userOwnerName;
		this.userShipperName = userShipperName;
		this.partName = partName;
		this.partDesc = partDesc;
		this.partStock = Integer.valueOf(partStock);
		this.partLocation = partLocation;
		this.partCost = Float.valueOf(partCost);
		this.partMemo = partMemo;

	}

}
