package com.lgit.stockmgmt.domain;

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

}
