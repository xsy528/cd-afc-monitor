package com.insigma.acc.wz.xdr.typedef;

/*
 *;
 */
public interface SAMType_t {
	// 一票通消费SAM卡 ;
	public final static int ACC_PSAM = 0x01;
	// 一卡通充值SAM卡 ;
	public final static int YKT_ISAM = 0x03;
	// 一卡通消费SAM卡(建设部密钥) ;
	public final static int YKT_PSAM = 0x04;
	// 单程票编码用SAM卡 ;
	public final static int ES_SAM = 0x05;
	// PBOC发卡母卡 ;
	public final static int PBOC_MAIN = 0x06;
	// PBOC发卡母卡认证卡 ;
	public final static int PBOC_AUTH = 0x07;
	// 手机SAM，预留 ;
	public final static int MOBILE_SAM = 0x08;
	// 一卡通消费SAM卡(市民卡密钥) ;
	public final static int YKT_PSAM_WZ = 0x09;
}
