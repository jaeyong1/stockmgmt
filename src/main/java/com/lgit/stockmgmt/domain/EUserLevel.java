package com.lgit.stockmgmt.domain;

import java.util.ArrayList;
import java.util.List;

public enum EUserLevel {

	Lv1_NOTUSE(1, "(internal only)"),
	Lv2_DEV(2, "개발담당자"),
	Lv3_SHIPPER(3, "출고담당자"),
	Lv4_GUEST(4, "손님"), 
	Lv5_ADMIN(5, "관리자");

	private int curr = 0;
	private String korcurr = "";

	private EUserLevel(int curr, String korcurr) {
		this.curr = curr;
		this.korcurr = korcurr;
	}

	public int getLevelInt() {
		return this.curr;
	}

	public String getLevelKor() {
		return this.korcurr;
	}

	public static ArrayList<String> getKorList() {
		return elevel2kor;
	}

	public static String getUserLevel2Kor(int i) {
		switch (i) {
		case 1:
			return EUserLevel.Lv1_NOTUSE.getLevelKor();
		case 2:
			return EUserLevel.Lv2_DEV.getLevelKor();
		case 3:
			return EUserLevel.Lv3_SHIPPER.getLevelKor();
		case 4:
			return EUserLevel.Lv4_GUEST.getLevelKor();
		case 5:
			return EUserLevel.Lv5_ADMIN.getLevelKor();
		
		default:
			return "잘못된 데이터";

		}
	}

	private static ArrayList<String> elevel2kor = new ArrayList<String>() {
		{
			add(EUserLevel.Lv1_NOTUSE.getLevelKor());
			add(EUserLevel.Lv2_DEV.getLevelKor());
			add(EUserLevel.Lv3_SHIPPER.getLevelKor());
			add(EUserLevel.Lv4_GUEST.getLevelKor());
			add(EUserLevel.Lv5_ADMIN.getLevelKor());

		}
	};

}
