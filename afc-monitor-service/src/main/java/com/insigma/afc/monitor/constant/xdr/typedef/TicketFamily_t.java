package com.insigma.afc.monitor.constant.xdr.typedef;

/*
 *;
 */
public interface TicketFamily_t {
	//单程类票(UL);
	public final static int TicketFamily_SJTUL = 1;
	//出站类票(UL);
	public final static int TicketFamily_SJTULOUT = 2;
	//计程票;
	public final static int TicketFamily_JCPCHENGCPU = 3;
	//计次票;
	public final static int TicketFamily_JCPCHICPU = 4;
	//计时票;
	public final static int TicketFamily_JSPCPU = 5;
	//公交卡;
	public final static int TicketFamily_GJIC = 6;
	//市民卡;
	public final static int TicketFamily_SMIC = 7;
	//城市交通卡;
	public final static int TicketFamily_CSTIC = 8;
	//二维码电子票;
	public final static int TicketFamily_QTIC = 9;
	//银联卡;
	public final static int TicketFamily_BCIC = 10;
}
