package com.insigma.acc.wz.xdr.typedef;

/*
 *;
 */
public interface RUNComponentType_t {
	// 一票通全量明细黑名单 ;
	public final static int RUN_BLKACCFULLLIST = 0x41;
	// 一票通增量明细黑名单 ;
	public final static int RUN_BLKACCINCRLIST = 0x42;
	// 一票通全量号段黑名单 ;
	public final static int RUN_BLKACCFULLSECTLIST = 0x43;
	// 一卡通全量明细黑名单（预留） ;
	public final static int RUN_BLKYKTFULLLIST = 0x44;
	// 员工卡全量明细黑名单 ;
	public final static int RUN_BLKSTAFFFULLLIST = 0x45;
	// 单程票回收条件 ;
	public final static int RUN_SJTRECYCLE = 0x46;
	// 模式履历 ;
	public final static int RUN_WAIVERDATE = 0x47;
	// 票卡库存目录 ;
	public final static int RUN_TICKETCATALOG = 0x48;
	// 操作员基本信息文件 ;
	public final static int RUN_OPERINFO = 0x50;
	// 操作员权限文件 ;
	public final static int RUN_OPERRIGHT = 0x51;
}
