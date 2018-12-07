%/*************************************************************************************************************
%
%   温州市域铁路AFC系统线网技术标准
%
%   @Title       : xdrSLE.x
%   @Version     : 1.2.0
%   Author      : 华科峰
%   Date        : 2016/06/20
%   Description  : 定义SLE的文件格式 
%**************************************************************************************************************/

%/*  修订历时记录:
%**************************************************************************************************************
%*   Date          *    Author           *     Changes
%**************************************************************************************************************
%*   2016/06/20    *    华科峰           *     1.2.0 基于温州市域铁路AFC系统线网技术标准V1.2生成文档
%*   2016/08/20    *    陈锦鲂           *     SLEYKTUD_t结构体中增加一卡通锁卡
%*   					       删除SLEYKTUD_t结构注释
%*   2017/11/03    *    陈锦鲂           *     SLEMessage_t结构体中AGMARMSG_t、BOMARMSG_t、TVMARMSG_t、ISMARMSG_t、PCAARMSG_t调整为列表形式
%*   2018/07/03    *    华科峰           *     增加SLEQRCODEUD_t、QRCodeUDMSG_t结构体，修改SLEBANKUD_t结构体
%**************************************************************************************************************/

%#ifndef XDRSLE_H 
%#define XDRSLE_H
%#include "xdrDCP.h"


%// -------------------------------------------------------------------------------------------------------------
%// ---------- 设备运营数据文件结构（UD、AR）
%// -------------------------------------------------------------------------------------------------------------

%// 一票通交易结构
union SLEYPTUD_t switch(TransactionType_t TransactionType) {					
	case TX_SJTSale:													/* 单程票发售 */
		SJTSale_TX                                SJTSale;					

	case TX_NonNamedCPUSale:												/* 非记名储值票发售 */
		CPUSale_TX                                CPUSale;					

	case TX_NamedCPUSale:													/* 记名储值票发售 */
		NamedCPUSale_TX                           NamedCPUSale;				

	case TX_CPUCardAddValue:												/* 储值票充值 */
		CPUCardAddValue_TX                        CPUCardAddValue;			

	case TX_SJTRefund:                                       					                        /* 单程票即时退票 */
		SJTRefund_TX                              SJTRefund;				

	case TX_CPUCardImmediateRefund:												/* 储值票即时退票 */
		CPUCardImmediateRefund_TX                 CPUCardImmediateRefund;	

	case TX_CPUCardNonImmediateRefund:											/* 储值票非即时退票 */
		CPUCardNonImmediateRefund_TX              CPUCardNonImmediateRefund;	

	case TX_TicketValidityPeriod:												/* 储值票延期 */
		CPUCardValidityPeriod_TX                  CPUCardValidityPeriod;		

	case TX_CPUCardBlock:													/* 储值票锁卡 */
		CPUCardBlock_TX                           CPUCardBlock;				

	case TX_CPUCardUnblock:													/* 储值票解锁 */
		CPUCardUnblock_TX                         CPUCardUnblock;			

	case TX_TicketSurcharge:												/* 一票通更新 */
		TicketSurcharge_TX                        TicketSurcharge;			

	case TX_TicketEntry:													/* 一票通进站 */
		TicketEntry_TX                            TicketEntry;				

	case TX_TicketExit:													/* 一票通出站 */
		TicketExit_TX                             TicketExit;				

	case TX_CPUCardDeduction:												/* 储值票扣款（预留） */
		CPUCardDeduction_TX                       CPUCardDeduction;	
};

%// 一卡通交易结构
union SLEYKTUD_t switch(TransactionType_t TransactionType) {					
	case TX_YKTCardSurcharge:													/* 一卡通更新 */
		TicketSurcharge_TX                        TicketSurcharge;			

	case TX_YKTCardEntry:														/* 一卡通进站 */
		TicketEntry_TX                            TicketEntry;				

	case TX_YKTCardExit:														/* 一卡通出站 */
		YKTTicketExit_TX                          YktTicketExit;				
 
        case TX_YKTCardAddValue:													/*一卡通充值 */
		CPUCardAddValue_TX                        CPUCardAddValue;	
	
	case TX_YKTCardBlock:													/* 储值票锁卡 */
		CPUCardBlock_TX                           CPUCardBlock;	
		
%//	case TX_YKTSale:													         /* 一卡通售卖（预留) */
%//		YKTSale_TX                                YKTSale;		
    };	

%// 手机票交易结构
union SLEMOBILEUD_t switch(TransactionType_t TransactionType) {					
%//	case TX_MobileSurcharge:													/* 手机卡更新 */
%//		TicketSurcharge_TX                        TicketSurcharge;			

%//	case TX_MobileEntry:														/* 手机卡进站 */
%//		TicketEntry_TX                            TicketEntry;				

%//	case TX_MobileExit:														/* 手机卡出站 */
%//		TicketExit_TX                             TicketExit;				

	case TX_MobileDeduction:													/* 手机卡卡扣款（预留） */
		MobileDeduction_TX						 MobileDeduction;			
};

%// 银行卡交易结构
union SLEBANKUD_t switch(TransactionType_t TransactionType) {					
	case TX_BankCardSurcharge:													/* 银行卡更新*/
		BankCardSurcharge_TX                     BankCardSurcharge;
	case TX_BankCardEntry:														/* 银行卡进站*/
		BankCardEntry_TX                         BankCardEntry;
	case TX_BankCardExit:														/* 银行卡出站*/
		BankCardExit_TX                          BankCardExit;
	case TX_BankCardDeduction:													/* 银行卡扣款 */
		BankCardDeduction_TX                     BankCardDeduction;			
};


%// 二维码交易结构
union SLEQRCODEUD_t switch(TransactionType_t TransactionType) {					
	case TX_QRCodeSurcharge:													/* 二维码更新*/
		QRCodeSurcharge_TX                     QRCodeSurcharge;
	case TX_QRCodeEntry:														/* 二维码进站*/
		QRCodeEntry_TX                         QRCodeEntry;
	case TX_QRCodeExit:															/* 二维码出站*/
		QRCodeExit_TX                          QRCodeExit;
};

%// 设备一票通交易消息结构
struct SLEYPTUDMSG_t {															
	UDComm_t                             UDComm;									/*交易公共结构*/
	SLEYPTUD_t                           YPTUDData;									/*交易数据结构*/
	TAC_t                                TAC;									/*交易TAC码*/
};

%// 设备一卡通交易消息结构
struct SLEYKTUDMSG_t {															
	YKTUDComm_t                          YKTUDComm;									/*一卡通交易公共结构*/
	SLEYKTUD_t                           YKTUDData;									/*一卡通交易结构*/
	TAC_t                                TAC;										/*交易TAC码*/
};

%// 设备手机卡交易消息结构
struct SLEMobileUDMSG_t {															
	UDComm_t                             UDComm;									/*交易公共结构*/
	SLEMOBILEUD_t                        SLEMOBILEUD;								/*手机卡交易结构*/
	TAC_t                                TAC;										/*交易TAC码*/
};

%// 银行卡交易消息结构
struct SLEBankUDMSG_t {															
	UDComm_t                             UDComm;									/*交易公共结构*/
	SLEBANKUD_t                          SLEBANKUD;									/*银行卡交易结构*/
	TAC_t                                TAC;										/*交易TAC码*/
};

%// 二维码交易消息结构
struct QRCodeUDMSG_t {															
	UDComm_t                             UDComm;									/*交易公共结构*/
	SLEQRCODEUD_t                        SLEQRCODEUD;								/*银行卡交易结构*/
	TAC_t                                TAC;										/*交易TAC码*/
};

%// AGM审计寄存器消息结构
struct AGMARMSG_t {																	
	ARComm_t                             ARComm;									/*AR公共结构*/  
	AGMAuditInfo_t                       AGMAuditInfo;								/*AGM审计信息*/  
};

%// BOM审计寄存器消息结构
struct BOMARMSG_t {																	
	ARComm_t                             ARComm;									/*AR公共结构*/  
	BOMAuditInfo_t                       BOMAuditInfo;								/*BOM审计信息*/  
};

%// TVM审计寄存器消息结构
struct TVMARMSG_t {																	
	ARComm_t                             ARComm;									/*AR公共结构*/  
	TVMAuditInfo_t                       TVMAuditInfo;								/*TVM审计信息*/  
};

%// ISM审计寄存器消息结构
struct ISMARMSG_t {																	
	ARComm_t                             ARComm;									/*AR公共结构*/  
	ISMAuditInfo_t                       ISMAuditInfo;								/*ISM审计信息*/  
};


%// PCA审计寄存器消息结构
struct PCAARMSG_t {																	
	ARComm_t                             ARComm;									/*AR公共结构*/  
	PCAAuditInfo_t                       PCAAuditInfo;								/*PCA审计信息*/  
};

%// 设备消息结构
union SLEMessage_t switch (FileHeaderTag_t TransactionTag) {					   	
	case FILE_YPT_TRANSACTION:                                                                                       /*设备一票通交易消息结构*/
		SLEYPTUDMSG_t             YPTUD<>;									

	case FILE_YKT_TRANSACTION:                                                                                      /*设备一卡通交易消息结构*/
		SLEYKTUDMSG_t             YKTUD<>;									

	case FILE_MOBILE_TRANSACTION:                                                                                    /*设备手机卡交易消息结构*/
		SLEMobileUDMSG_t          MOBILEUD<>;							     	       

	case FILE_BANK_TRANSACTION:                                                                                      /*银行卡交易消息结构*/
		SLEBankUDMSG_t		  BANKUD<>;								       

        case FILE_QRCODE_TRANSACTION:
		QRCodeUDMSG_t		  QRCodeUD<>;								         /*二维码交易消息结构*/

	case FILE_AGM_AUDIT_REGISTER:                                                                                   /*AGM审计寄存器消息结构*/
		AGMARMSG_t                AGMARMSG<>;									

	case FILE_BOM_AUDIT_REGISTER:                                                                                  /*BOM审计寄存器消息结构*/
		BOMARMSG_t                BOMARMSG<>;									

	case FILE_TVM_AUDIT_REGISTER:                                                                                   /*TVM审计寄存器消息结构*/
		TVMARMSG_t                TVMARMSG<>;									

	case FILE_ISM_AUDIT_REGISTER:                                                                                   /*ISM审计寄存器消息结构*/
		ISMARMSG_t                ISMARMSG<>;									

	case FILE_PCA_AUDIT_REGISTER:                                                                                   /*PCA审计寄存器消息结构*/
		PCAARMSG_t                PCAARMSG<>;									

};



%// ---- 设备运营文件头结构
struct SLEDataFileHeader_t {															
	FileHeaderTag_t						      FileHeaderTag;			      /* 文件类别*/
        SLEFileVersionID_t                                            FileVersion;                            /* 文件版本*/
	AFCTime64_t						      FileCreationTime;                       /* 文件创建时间 */
	string							      FileName<40>;                           /* 文件名 */
	LineID_t						      LineID;				      /*线路编号*/
	StationID_t						      StationID;			      /*车站编号*/
	DeviceID_t						      DeviceID;				      /*设备节点编号*/
	Date2_t							      BusinessDay;			      /*运营日期*/
	SN_t							      FileSN;				      /*文件流水号*/
	RecordsInFile_t						      RecordsInFile;                          /*文件记录数*/
};

%// ---- 设备运营文件结构
struct SLEDataFile_t {															
	SLEDataFileHeader_t						SLEDataFileHeader;                      /* 设备运营文件头结构 */
	SLEMessage_t							SLEMessages;				/*设备消息结构*/
	MD5_t								MD5Value;				/*MD5签名*/
};

%// -------------------------------------------------------------------------------------------------------------

%#endif
%/********************************************** 文件结束 *******************************************************/



