package com.lgit.stockmgmt.domain;

import java.util.List;

public class ShipReqItem {

	private int shipId;
	private String shipRequestorId;
	private String shipDestination;
	private String shipToday;
	private String shipTargetdate;
	// //java.util.Date dt = new java.util.Date();
	//
	// java.text.SimpleDateFormat sdf =
	// new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//
	// String currentTime = sdf.format(dt);
	private String shipProjectCode;
	private String shipMemo;
	private int shipIsmyproject;
	private int shipStateId;
	private String shipCoworkerUserid;

	public int getShipId() {
		return shipId;
	}

	public void setShipId(int shipId) {
		this.shipId = shipId;
	}

	public String getShipRequestorId() {
		return shipRequestorId;
	}

	public void setShipRequestorId(String shipRequestorId) {
		this.shipRequestorId = shipRequestorId;
	}

	public String getShipDestination() {
		return shipDestination;
	}

	public void setShipDestination(String shipDestination) {
		this.shipDestination = shipDestination;
	}

	public String getShipToday() {
		return shipToday;
	}

	public void setShipToday(String shipToday) {
		this.shipToday = shipToday;
	}

	public String getShipTargetdate() {
		return shipTargetdate;
	}

	public void setShipTargetdate(String shipTargetdate) {
		this.shipTargetdate = shipTargetdate;
	}

	public String getShipProjectCode() {
		return shipProjectCode;
	}

	public void setShipProjectCode(String shipProjectCode) {
		this.shipProjectCode = shipProjectCode;
	}

	public String getShipMemo() {
		return shipMemo;
	}

	public void setShipMemo(String shipMemo) {
		this.shipMemo = shipMemo;
	}

	public int getShipIsmyproject() {
		return shipIsmyproject;
	}

	public void setShipIsmyproject(int shipIsmyproject) {
		this.shipIsmyproject = shipIsmyproject;
	}

	public int getShipStateId() {
		return shipStateId;
	}

	public void setShipStateId(int shipStateId) {
		this.shipStateId = shipStateId;
	}

	public String getShipCoworkerUserid() {
		return shipCoworkerUserid;
	}

	public void setShipCoworkerUserid(String shipCoworkerUserid) {
		this.shipCoworkerUserid = shipCoworkerUserid;
	}

	@Override
	public String toString() {
		return "ShipReqItem [shipId=" + shipId + ", shipRequestorId=" + shipRequestorId + ", shipDestination="
				+ shipDestination + ", shipToday=" + shipToday + ", shipTargetdate=" + shipTargetdate
				+ ", shipProjectCode=" + shipProjectCode + ", shipMemo=" + shipMemo + ", shipIsmyproject="
				+ shipIsmyproject + ", shipStateId=" + shipStateId + ", shipCoworkerUserid=" + shipCoworkerUserid + "]";
	}

	public ShipReqItem() {
		super();
	}

	public ShipReqItem(int shipId, String shipRequestorId, String shipDestination, String shipToday,
			String shipTargetdate, String shipProjectCode, String shipMemo, int shipIsmyproject, int shipStateId,
			String shipCoworkerUserid) {
		super();
		this.shipId = shipId;
		this.shipRequestorId = shipRequestorId;
		this.shipDestination = shipDestination;
		this.shipToday = shipToday;
		this.shipTargetdate = shipTargetdate;
		this.shipProjectCode = shipProjectCode;
		this.shipMemo = shipMemo;
		this.shipIsmyproject = shipIsmyproject;
		this.shipStateId = shipStateId;
		this.shipCoworkerUserid = shipCoworkerUserid;
	}

	/*
	 * mybatis 통할때 생성자 타입 추가 필요했음
	 */
	public ShipReqItem(Integer shipId, String shipRequestorId, String shipDestination, String shipToday,
			String shipTargetdate, String shipProjectCode, String shipMemo, Integer shipIsmyproject,
			Integer shipStateId, String shipCoworkerUserid) {
		super();
		this.shipId = Integer.valueOf(shipId);
		this.shipRequestorId = shipRequestorId;
		this.shipDestination = shipDestination;
		this.shipToday = shipToday;
		this.shipTargetdate = shipTargetdate;
		this.shipProjectCode = shipProjectCode;
		this.shipMemo = shipMemo;
		this.shipIsmyproject = Integer.valueOf(shipIsmyproject);
		this.shipStateId = Integer.valueOf(shipStateId);
		this.shipCoworkerUserid = shipCoworkerUserid;
	}

	/*
	 * ship_id ship_requestor_id ship_destination ship_today ship_targetdate
	 * ship_project_code ship_memo ship_ismyproject ship_state_id
	 * ship_coworker_userid
	 * 
	 * 
	 * INSERT INTO `jaeyong2`.`tb_shipreq` ( `ship_id` , `ship_requestor_id` ,
	 * `ship_destination` , `ship_today` , `ship_targetdate` ,
	 * `ship_project_code` , `ship_memo` , `ship_ismyproject` , `ship_state_id`
	 * , `ship_coworker_userid` ) VALUES ( NULL , 'devtest', '평택 1공장',
	 * '2016-10-22', '2016-10-26', 'TESTPRJ002', '메모메모', 'Y', '1', NULL );
	 */

}
