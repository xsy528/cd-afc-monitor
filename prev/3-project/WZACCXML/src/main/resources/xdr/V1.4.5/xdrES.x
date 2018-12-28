%/*************************************************************************************************************
%
%    温州市域铁路AFC系统线网技术标准
%
%   @Title       : xdrES.x
%   @Version     : 1.2.0
%    Author      : 华科峰
%    Date        : 2016/06/20
%   Description  : 定义ES的文件格式 
%**************************************************************************************************************/

%/*  修订历时记录:
%**************************************************************************************************************
%*   Date          *    Author           *     Changes
%**************************************************************************************************************
%*   2016/06/20    *    华科峰           *     1.2.0 基于温州市域铁路AFC系统线网技术标准V1.2生成文档
%**************************************************************************************************************/

%#ifndef XDRES_H 
%#define XDRES_H

%#include "xdrDCP.h"


%// -------------------------------------------------------------------------------------------------------------
%// ---------- ES数据文件结构
%// -------------------------------------------------------------------------------------------------------------

%// ES交易结构
union ESUD_t switch(TransactionType_t TransactionType) {					
	case TX_SJTInit:													/* 单程票初始化 */
	     ESSJTInit_TX                                ESSJTInit;					

	case TX_CPUCardInit:													/* 储值票初始化 */
	     ESCPUCardInit_TX                            ESCPUCardInit;					

	case TX_TicketCancel:													/* 票卡注销 */
	     ESTicketCancel_TX                           ESTicketCancel;				

	case TX_CPUCardPersonal:												/* 储值票个性化 */
	     ESCPUCardPersonal_TX			    ESCPUCardPersonal;		

	case TX_PrePaySJTOffset:											        /* 预付值单程票抵销 */
	     PrePaySJTOffset_TX			    PrePaySJTOffset;					
};

%// 设备一票通交易消息结构
struct ESUDMSG_t {															
	UDComm_t                             UDComm;									/*交易公共结构*/
	ESUD_t				     ESUDData;									/*ES交易数据结构*/
	TAC_t                                TAC;									/*交易TAC码*/
};

%// 设备消息结构
union ESMessage_t switch (FileHeaderTag_t TransactionTag) {					   	
	case FILE_ES_TRANSACTION:                                                               /*ES交易消息结构*/
		ESUDMSG_t									ESUD<>;			

	case FILE_ES_ENCODING:                                                                 /*ES编码文件结构*/
		TicketEncodingReportFile_t              TicketEncodingReportFile;				
};

%// ---- ES运营文件头结构
struct ESDataFileHeader_t {															
	FileHeaderTag_t							FileHeaderTag;				/* 文件类别*/
	AFCTime64_t							FileCreationTime;                       /* 文件创建时间 */
	string								FileName<40>;				/* 文件名 */
	LineID_t							LineID;					/*线路编号*/
	StationID_t							StationID;			        /*车站编号*/
	DeviceID_t							DeviceID;				/*设备节点编号*/
	Date2_t								BusinessDay;				/*运营日期*/
	SN_t								FileSN;					/*文件流水号*/
	RecordsInFile_t							RecordsInFile;                          /*文件记录数*/
};

%// ---- ES运营文件结构
struct ESDataFile_t {															
	ESDataFileHeader_t						ESDataFileHeader;                      /* ES运营文件头结构 */
	ESMessage_t							ESMessages;			       /* ES设备消息结构*/
	MD5_t								MD5Value;			       /* MD5签名*/
};

%// -------------------------------------------------------------------------------------------------------------

%#endif
%/********************************************** 文件结束 *******************************************************/



