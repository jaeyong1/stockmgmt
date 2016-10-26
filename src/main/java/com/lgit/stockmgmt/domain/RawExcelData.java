/**
 * DB의 tb_parts에 매칭되는 클래스
 * 
 * @author jaeyong1.park
 */

package com.lgit.stockmgmt.domain;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class RawExcelData {
	private String xlsProjectName;
	private String xlsOwnerTeamName;
	private String xlsOwnerName;
	private String xlsShipperName;
	private String xlsPartName;
	private String xlsPartDesc;
	private String xlsPartMemo;// Maker
	private String xlsPartStock;
	private String xlsPartLocation;




	public String getXlsProjectName() {
		return xlsProjectName;
	}

	public void setXlsProjectName(String xlsProjectName) {
		this.xlsProjectName = xlsProjectName;
	}

	public String getXlsOwnerTeamName() {
		return xlsOwnerTeamName;
	}

	public void setXlsTeamName(String xlsTeamName) {
		this.xlsOwnerTeamName = xlsTeamName;
	}

	public String getXlsOwnerName() {
		return xlsOwnerName;
	}

	public void setXlsOwnerName(String xlsOwnerName) {
		this.xlsOwnerName = xlsOwnerName;
	}

	public String getXlsShipperName() {
		return xlsShipperName;
	}

	public void setXlsShipperName(String xlsShipperName) {
		this.xlsShipperName = xlsShipperName;
	}

	public String getXlsPartName() {
		return xlsPartName;
	}

	public void setXlsPartName(String xlsPartName) {
		this.xlsPartName = xlsPartName;
	}

	public String getXlsPartDesc() {
		return xlsPartDesc;
	}

	public void setXlsPartDesc(String xlsPartDesc) {
		this.xlsPartDesc = xlsPartDesc;
	}

	public String getXlsPartMemo() {
		return xlsPartMemo;
	}

	public void setXlsPartMemo(String xlsPartMemo) {
		this.xlsPartMemo = xlsPartMemo;
	}

	public String getXlsPartStock() {
		return xlsPartStock;
	}

	public void setXlsPartStock(String xlsPartStock) {
		this.xlsPartStock = xlsPartStock;
	}

	public String getXlsPartLocation() {
		return xlsPartLocation;
	}

	public void setXlsPartLocation(String xlsPartLocation) {
		this.xlsPartLocation = xlsPartLocation;
	}

	public RawExcelData() {
		super();
	}

	public RawExcelData(String xlsProjectName, String xlsOwnerTeamName, String xlsOwnerName, String xlsShipperName,
			String xlsPartName, String xlsPartDesc, String xlsPartMemo, String xlsPartStock, String xlsPartLocation) {
		super();
		this.xlsProjectName = xlsProjectName;
		this.xlsOwnerTeamName = xlsOwnerTeamName;
		this.xlsOwnerName = xlsOwnerName;
		this.xlsShipperName = xlsShipperName;
		this.xlsPartName = xlsPartName;
		this.xlsPartDesc = xlsPartDesc;
		this.xlsPartMemo = xlsPartMemo;
		this.xlsPartStock = xlsPartStock;
		this.xlsPartLocation = xlsPartLocation;
	}

	@Override
	public String toString() {
		return "RawExcelData [xlsProjectName=" + xlsProjectName + ", xlsOwnerTeamName=" + xlsOwnerTeamName + ", xlsOwnerName="
				+ xlsOwnerName + ", xlsShipperName=" + xlsShipperName + ", xlsPartName=" + xlsPartName
				+ ", xlsPartDesc=" + xlsPartDesc + ", xlsPartMemo=" + xlsPartMemo + ", xlsPartStock=" + xlsPartStock
				+ ", xlsPartLocation=" + xlsPartLocation + "]";
	}

}
