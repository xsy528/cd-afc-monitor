package com.insigma.afc.dic;

public interface DeviceType_t {
	// ACC服务器 ;
	public final static int DT_ACC_SERVER = 0x01;
	// SC服务器 ;
	public final static int DT_SC_SERVER = 0x02;
	// MC服务器 ;
	public final static int DT_MC_SERVER = 0x03;
	// ACC工作站 ;
	public final static int DT_ACC_WS = 0x04;
	// SC工作站 ;
	public final static int DT_SC_WS = 0x05;
	// MC工作站 ;
	public final static int DT_MC_WS = 0x06;
	// 进站AGM ;
	public final static int DT_ENG = 0x07;
	// 出站AGM ;
	public final static int DT_EXG = 0x08;
	// 双向AGM ;
	public final static int DT_RG = 0x09;
	// BOM ;
	public final static int DT_BOM = 0x10;
	// TVM ;
	public final static int DT_TVM = 0x11;
	// ISM ;
	public final static int DT_ISM = 0x12;
	// PCA ;
	public final static int DT_PCA = 0x13;
	// ES;
	public final static int DT_ES = 0x14;
}