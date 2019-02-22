package com.insigma.afc.monitor.constant.xdr.typedef;

/*
 *;
 */
public interface DeviceSubType_t {
	//卡式编码分拣机;
	public final static int DTSub_CardES = 1;
	//筹码式编码分拣机;
	public final static int DTSub_TokenES = 2;
	//TVM(按键式);
	public final static int DTSub_TvmButton = 3;
	//TVM(触摸式);
	public final static int DTSub_TvmTouch = 4;
	//BOM(非付费区);
	public final static int DTSub_PaidBom = 5;
	//BOM(付费区);
	public final static int DTSub_UnpaidBom = 6;
	//进站检票机;
	public final static int DTSub_EnG = 7;
	//出站检票机;
	public final static int DTSub_ExG = 8;
	//双向检票机;
	public final static int DTSub_DoubleAgm = 9;
	//个性化票卡及;
	public final static int DTSub_CUS = 10;
	//预留;
	public final static int DTSub_TCM = 11;
	//手持式验票机;
	public final static int DTSub_PCA = 12;
	//互联网取票机;
	public final static int DTSub_ITVM = 13;
	//出站检票机;
	public final static int DTSub_Server = 21;
	//双向检票机;
	public final static int DTSub_Workstation = 22;
	//交换机;
	public final static int DTSub_Swither = 23;
	//路由器;
	public final static int DTSub_Router = 24;
	//防火墙;
	public final static int DTSub_Firewall = 25;
	//不间断电源;
	public final static int DTSub_UPS = 26;
}
