package com.insigma.acc.wz.xdr.typedef;

/*
 *;
 */
public interface TicketFamilyType_t {
	// 单程票 ;
	public final static int TF_SJT = 0x01;
	// 出站票 ;
	public final static int TF_EXIT = 0x02;
	// 计程票 ;
	public final static int TF_MILE = 0x03;
	// 计时票 ;
	public final static int TF_TIME = 0x04;
	// 计次票 ;
	public final static int TF_RIDE = 0x05;
	// 员工票 ;
	public final static int TF_STAFF = 0x06;
	// 市民卡 ;
	public final static int TF_CITIZEN = 0x07;
	// 手机卡 ;
	public final static int TF_MOBILE = 0x08;
	//银联卡;
	public final static int TF_UNIONPAY = 0x09;
	//二维码;
	public final static int TF_QRCODE = 0x0A;
}
