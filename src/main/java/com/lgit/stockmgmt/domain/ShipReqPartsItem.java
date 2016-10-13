package com.lgit.stockmgmt.domain;

public class ShipReqPartsItem {
	private int itemlistId; // auto increase
	private String userId; //요청자ID.Cart에있을때만사용
	private int itemlistShipId; // 출고요청 Seq
	private int itemlistPartId; // 파츠 ID
	private int itemlistAmount; // 수량
	// to display
	private String partDesc;// Desc
	private int partStock;// 재고
	private String partProjectCode; //프로젝트코드
	

	private String partMemo;


	public String getPartMemo() {
		return partMemo;
	}

	public void setPartMemo(String partMemo) {
		this.partMemo = partMemo;
	}

	public int getItemlistId() {
		return itemlistId;
	}

	public void setItemlistId(int itemlistId) {
		this.itemlistId = itemlistId;
	}

	public int getItemlistShipId() {
		return itemlistShipId;
	}

	public void setItemlistShipId(int itemlistShipId) {
		this.itemlistShipId = itemlistShipId;
	}

	public int getItemlistPartId() {
		return itemlistPartId;
	}

	public void setItemlistPartId(int itemlistPartId) {
		this.itemlistPartId = itemlistPartId;
	}

	public int getItemlistAmount() {
		return itemlistAmount;
	}

	public void setItemlistAmount(int itemlistAmount) {
		this.itemlistAmount = itemlistAmount;
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

	public String getPartProjectCode() {
		return partProjectCode;
	}

	public void setPartProjectCode(String partProjectCode) {
		this.partProjectCode = partProjectCode;
	}



	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	

	public ShipReqPartsItem(int itemlistId, String userId, int itemlistShipId, int itemlistPartId, int itemlistAmount,
			String partDesc, int partStock, String partProjectCode, String partMemo) {
		super();
		this.itemlistId = itemlistId;
		this.userId = userId;
		this.itemlistShipId = itemlistShipId;
		this.itemlistPartId = itemlistPartId;
		this.itemlistAmount = itemlistAmount;
		this.partDesc = partDesc;
		this.partStock = partStock;
		this.partProjectCode = partProjectCode;
		this.partMemo = partMemo;
	}

	public ShipReqPartsItem() {
		super();
		this.itemlistShipId = -1;
	}

	/*
	 * mybatis 통할때 생성자 타입 추가 필요했음
	 */
	public ShipReqPartsItem(Integer itemlistId, Integer itemlistShipId, Integer itemlistPartId, Integer itemlistAmount,
			String partDesc, Integer partStock, String partProjectCode, String userId , String partMemo){
		super();

		this.itemlistId = Integer.valueOf(itemlistId);
		this.itemlistShipId = Integer.valueOf(itemlistShipId);
		this.itemlistPartId = Integer.valueOf(itemlistPartId);
		this.itemlistAmount = Integer.valueOf(itemlistAmount);
		this.partDesc = partDesc;
		this.partStock = Integer.valueOf(partStock);
		this.partProjectCode = partProjectCode;
		this.userId = userId;

		this.partMemo = partMemo;

	}
}
