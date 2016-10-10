package com.lgit.stockmgmt.domain;

import java.util.ArrayList;
import java.util.List;

public enum EShipState {

	STATE1_ADDCART(1, "요청서작성중"), // 요청서작성중
	STATE2_REQCOWORKSHIPPING(2, "합의요청중"), // 합의요청중
	STATE3_REQSHIPPING(3, "출고접수중"), // 출고접수중
	STATE4_LISTPRINTED(4, "출고접수완료"), // 출고접수완료
	STATE5_SHIPPINGFINISHED(5, "출고완료"), // 출고완료
	STATE6_REJSHIPPING(6, "반려"); // 반려

	private int curr = 0;
	private String korcurr = "";

	private EShipState(int curr, String korcurr) {
		this.curr = curr;
		this.korcurr = korcurr;
	}

	public int getStateInt() {
		return this.curr;
	}

	public String getStateKor() {
		return this.korcurr;
	}

	public static ArrayList<String> getKorList() {
		return estate2kor;
	}

	public static String getShipStateKor(int i) {
		switch (i) {
		case 1:
			return EShipState.STATE1_ADDCART.getStateKor();
		case 2:
			return EShipState.STATE2_REQCOWORKSHIPPING.getStateKor();
		case 3:
			return EShipState.STATE3_REQSHIPPING.getStateKor();
		case 4:
			return EShipState.STATE4_LISTPRINTED.getStateKor();
		case 5:
			return EShipState.STATE5_SHIPPINGFINISHED.getStateKor();
		case 6:
			return EShipState.STATE6_REJSHIPPING.getStateKor();

		default:
			return "잘못된 데이터";

		}
	}

	private static ArrayList<String> estate2kor = new ArrayList<String>() {
		{
			add(EShipState.STATE1_ADDCART.getStateKor());
			add(EShipState.STATE2_REQCOWORKSHIPPING.getStateKor());
			add(EShipState.STATE3_REQSHIPPING.getStateKor());
			add(EShipState.STATE4_LISTPRINTED.getStateKor());
			add(EShipState.STATE5_SHIPPINGFINISHED.getStateKor());
			add(EShipState.STATE6_REJSHIPPING.getStateKor());

		}
	};

}
