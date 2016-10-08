package com.lgit.stockmgmt.domain;

import java.util.List;

public class ShipReqItem {

	private String shipReqId; // DB에서 autoincrease
	private String userOwnerId; // 요청자
	private String userOwnerName; // 요청자
	private String userTeamname; // 요청부서
	private String shipReqDate; // 출고요구일

	private String projectCode;
	private String projectName;
	private String shipMemo;

	private String shipCoworkerId;
	private String shipCoworkerName;

	class shipListItem {

		private String partsId; // pk
		private String partName;
		private String partDesc;
		private int partCurrentStock; // 재고수량
		private int partShipStock; // 요청수량

		public String getPartsId() {
			return partsId;
		}

		public void setPartsId(String partsId) {
			this.partsId = partsId;
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

		public int getPartCurrentStock() {
			return partCurrentStock;
		}

		public void setPartCurrentStock(int partCurrentStock) {
			this.partCurrentStock = partCurrentStock;
		}

		public int getPartShipStock() {
			return partShipStock;
		}

		public void setPartShipStock(int partShipStock) {
			this.partShipStock = partShipStock;
		}

	}

	private List<shipListItem> list;

}
