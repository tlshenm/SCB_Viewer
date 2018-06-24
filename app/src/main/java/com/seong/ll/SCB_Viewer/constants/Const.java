package com.seong.ll.SCB_Viewer.constants;

public class Const {
	/* 서버통신후 결과 값 */
	public static final String SUCCESS = "1000";							// 성공시
	public static final String INVALID_AUTHPARAM = "2001";				// Error Message
	public static final String INVALID_EMPID = "2002";					// Error Message
	public static final String INVALID_ITEMID = "2003";					// Error Message
	public static final String INVALID_SALARYID = "2004";					// Error Message
	public static final String INTERNAL_SERVER_EXCEPTION = "2100";		// Error Message
	
	/*
	 * 팝업관련
	 */
	public static final int TYPE_CONFIRM = 100;						// 팝업 버튼2
	public static final int TYPE_PASS = 101;						// 팝업 PASS

	public static final int REQ_SELECT_IMAGE = 0;
}
