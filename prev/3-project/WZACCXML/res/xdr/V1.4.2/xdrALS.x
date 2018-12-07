%/*************************************************************************************************************
%
%    温州市域铁路AFC系统线网技术标准
%
%   @Title       : xdrALS.x
%   @Version     : 1.2.0
%    Author      : 华科峰
%    Date        : 2016/06/20
%   Description  : 定义ACC、SC的文件格式 
%**************************************************************************************************************/

%/*  修订历时记录:
%**************************************************************************************************************
%*   Date          *    Author           *     Changes
%**************************************************************************************************************
%*   2016/06/20    *    华科峰           *     1.2.0 基于温州市域铁路AFC系统线网技术标准V1.2生成文档
%**************************************************************************************************************/

%#ifndef XDRALS_H 
%#define XDRALS_H

%#include "xdrDCP.h"


%// -------------------------------------------------------------------------------------------------------------
%// ---------- SC和ACC其他文件结构
%// -------------------------------------------------------------------------------------------------------------

%// SC和ACC其他消息结构
union ALSMessage_t switch (FileHeaderTag_t TransactionTag) {					  	
	
	case FILE_ENDOFDAY_STOCK_SANPSHOT:											/*日终库存快照文件*/
		TicketStockSnapshotFile_t				TicketStockSnapshotFile;		
	
	case FILE_STOCK_INOUT_BILL:												/*出入库清单文件*/
		TicketStockInOutBillFile_t				TicketStockInOutBillFile;		

	case FILE_ENDOFDAY_STOCK_DIFF:												/*日终库存差异文件*/
		TicketStockDifferenceFile_t				TicketStockDifferenceFile;		

	case FILE_TRANS_ACCOUNT:												/*交易对账文件*/
		TransactionAccountFile_t				TransactionAccountFile;			

	case FILE_DOUBTFULDEAL_RESULT:												/*可疑交易处理文件*/
		TransactionDoubtfulFile_t				TransactionDoubtfulFile;		
	
%//	case FILE_TRANS_CLEAN_RESULT:												/*清分数据文件*/
%//		TransactionClearFile_t					TransactionClearFile;			
};


%// ---- SC和ACC其他文件结构
struct ALSDataFile_t {														
	AFCTime64_t							FileCreationTime;                       /* 文件创建时间 */
	string								FileName<40>;                           /* 文件名 */
	FileHeaderTag_t							FileHeaderTag;				/* 文件类别*/
	ALSMessage_t							ALSMessage;				/* 消息结构*/
	MD5_t								MD5Value;			        /* MD5签名*/
};

%// -------------------------------------------------------------------------------------------------------------

%#endif
%/********************************************** 文件结束 *******************************************************/



