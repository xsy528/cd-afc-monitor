package com.insigma.acc.wz.xdr.typedef;

/*
 *;
 */
public interface DurationMode_t {
	// 对票卡有效期不做检查 ;
	public final static int DM_NOTCHECK = 0x00;
	// 在票卡第一次使用后的一段时间内有效 ;
	public final static int DM_FIRSTUSERAFTER = 0x01;
	// 在票卡发售时设定的起止日期 ;
	public final static int DM_STARTANDEND = 0x02;
	// 在票卡发售之日起一段时间内有效 ;
	public final static int DM_SELLAFTER = 0x03;
	// 在上次使用日期之后的一段时间内有效 ;
	public final static int DM_LASTTIMEAFTER = 0x04;
}
