package com.insigma.afc.monitor.constant.xdr.typedef;

/*
 *;
 */
public interface TicketFamily_t {
	//单程类票(UL);
	public final static int TicketFamily_SJTUL = 0x01;
	//日期类票（UL）;
	public final static int TicketFamily_SJTULRQ = 0x02;
	//计程票;
	public final static int TicketFamily_JCPCHENGCPU = 0x03;
	//计次票;
	public final static int TicketFamily_JCPCHICPU = 0x04;
	//计时票;
	public final static int TicketFamily_JSPCPU = 0x05;
	//公交卡;
	public final static int TicketFamily_GJIC = 0x06;
	//市民卡;
	public final static int TicketFamily_SMIC = 0x07;
	//城市交通卡;
	public final static int TicketFamily_CSTIC = 0x08;
	//二维码电子票;
	public final static int TicketFamily_QTIC = 0x09;
	//银联卡;
	public final static int TicketFamily_BCIC = 0x0A;
}
