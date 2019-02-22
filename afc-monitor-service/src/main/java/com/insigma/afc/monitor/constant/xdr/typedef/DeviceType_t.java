package com.insigma.afc.monitor.constant.xdr.typedef;

/*
 *;
 */
public interface DeviceType_t {
	//E/S（编码分拣机）;
	public final static int DT_ES = 0x00;
	//TVM自动售票机;
	public final static int DT_TVM = 0x01;
	//BOM（半自动售票机）;
	public final static int DT_BOM = 0x02;
	//CUS，个性化票卡机;
	public final static int DT_CUS = 0x03;
	//PCA（手持式验票机）;
	public final static int DT_PCA = 0x05;
	//AGM（自动检票机）;
	public final static int DT_AGM = 0x06;
	//ITVM（互联网取票机）;
	public final static int DT_ITVM = 0x07;
	//服务器;
	public final static int DT_Server = 0x11;
	//工作站;
	public final static int DT_Workstation = 0x12;
	//网络设备;
	public final static int DT_Network = 0x13;
	//UPS;
	public final static int DT_UPS = 0x14;
}
