package com.insigma.acc.wz.xdr.typedef;

/*
 *;
 */
public interface ESTaskType_t {
	// 单程票初始化 ;
	public final static int EST_SJT_INIT = 0x01;
	// 储值票初始化 ;
	public final static int EST_SVT_INIT = 0x02;
	// 票卡注销 ;
	public final static int EST_TICKET_CANCEL = 0x03;
	// 票卡分拣 ;
	public final static int EST_TICKET_ENCODING = 0x04;
	// 票卡个性化 ;
	public final static int EST_TICKET_PERSONAL = 0x05;
	// 预付值单程票抵销 ;
	public final static int EST_SVT_OFFSET = 0x06;
	// 任务取消 ;
	public final static int EST_TASK_CANCEL = 0x07;
}
