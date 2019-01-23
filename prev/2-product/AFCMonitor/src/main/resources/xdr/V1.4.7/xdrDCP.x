%/*************************************************************************************************************
%
%   温州市域铁路AFC系统线网技术标准
%
%   Title       : xdrDCP.x
%   @Version     : 1.2.0
%   Author      : 华科峰
%   Date        : 2016/06/20
%   Description : 定义系统的设备生成文件及文件格式
%**************************************************************************************************************/

%/*  修订历时记录:
%**************************************************************************************************************
%*   Date          *    Author           *     Changes
%**************************************************************************************************************
%*   2016/06/20    *    华科峰           * 1.2.0 基于温州市域铁路AFC系统线网技术标准V1.2生成文档
%*   2016/08/10    *    陈锦鲂           * 删除struct NoteBoxInfo_t结构体
%                                        * 调整ISMSnapshotInfo_t结构体，将结构体中NoteBoxInfo_t修改为MoneyInBankNoteBox_t；
%                                        * 删除CashBoxInfo_t结构体；
%                                        * SJTSale_t结构体预留字段移动至“票卡状态”字段之后；
% 					 * RebateScheme_t结构体增加PileConcessionType，/*累计优惠方式*/；
%                                        * YKTTicketExit_TX结构体增加预留1和预留2字段；
%                                        * CPUCardAddValue_t结构体中注释BankCardPayment_t银行卡支付结构；
%                                        * 调整YKTUDComm_t结构体;
%*   2017/11/03    *    陈锦鲂           * ESCPUCardInit_t结构和规范文档保持一致
%					 * TVMAuditInfo_t结构体中TVMSnapshotInfo_t字段调整为非列表形式
%					 * 调整TVMSnapshotInfo_t结构体中CashBoxInfo_t字段至最后
%					 * 调整ISMSnapshotInfo_t结构体中的CashBoxInfo_t字段至最后
%*   2018/05/03    *    华科峰           * TicketStockInOutDetailList_t新增TicketValue字段
%*   2018/06/05    *    华科峰           * 删除TicketStockDifferenceFile_t结构体
%*   2018/07/03    *    华科峰           * 新增BankCardTicketComm_t、QRCodeTicketComm_t、QRCodeSurcharge_t、BankCardEntry_t、BankCardExit_t、QRCodeExit_t结构体
%*   2018/07/03    *    华科峰           * 删除BankCardPayment_t结构体
%*   2018/07/03    *    华科峰           * 修改BankCardDeduction_t结构体
%*   2018/07/03    *    华科峰           * 新增BankCardSurcharge_TX、BankCardEntry_TX、BankCardExit_TX、QRCodeSurcharge_TX、QRCodeEntry_TX、QRCodeExit_TX结构体
%*   2018/07/31    *    华科峰           * 修改BankCardSurcharge_t结构体SaleDeviceID字段填写说明
%**************************************************************************************************************/

%#ifndef XDRDCP_H
%#define XDRDCP_H
%
%#include "xdrBaseType.h"


%// -------------------------------------------------------------------------------------------------------------
%// ---------- 设备运营数据文件结构（UD、AR）
%// -------------------------------------------------------------------------------------------------------------

%// ---- 交易公共结构
struct UDComm_t{													
	TransactionType_t					        TransactionType;        
	AFCTime64_t							TransactionDateTime;                /* 交易产生时间 */
	LineID_t							LineID;                             /* 线路编号 */
	StationID_t							StationID;                          /* 车站编号 */
	DeviceID_t							DeviceID;                           /* 设备节点号 */
	SAMID_t								TACSAMID;                           /* SAM卡号 */
	ModeCode_t							ModeCode;                           /* 车站运营模式 */
	SN_t								UDSN;                               /* 设备交易流水号 */
};
%// ---- 一卡通交易公共结构
struct YKTUDComm_t{			
	TransactionType_t					TransactionType;                 
	AFCTime64_t							TransactionDateTime;                /* 交易产生时间 */
	LineID_t							LineID;                             /* 线路编号 */
	StationID_t							StationID;                          /* 车站编号 */
	DeviceID_t							DeviceID;                           /* 设备节点号 */
	SAMID_t								TACSAMID;                           /* SAM卡号 */	
	ModeCode_t							ModeCode;                           /* 车站运营模式 */
	SN_t							    UDSN;                               /* 设备交易流水号 */	
%//	ePurseTransactionType_t             ePurseTransactionType;          /*钱包交易类型*/
%//	CityCode_t                                                      CityCode_TransLocation;         /*城市代码（交易发生地)*/
%//	CityCode_t                                                      CityCode_BelongLocation;         /*城市代码（卡属地)*/       
%//	string				               	TACSAMID<20>;                    	/* SAM卡号 */ 
	TerminateNumber_t				    TerminateNumber;                	/* 终端机编号 */
	SN_t								SAMSN;                    			/* SAM卡流水号*/
	string								IssueNumber<16>;					/* 发行流水号*/
	U32_t								WalletTransactionNumber;			/* 电子钱包交易序号*/
	U16_t                               CardType;                       	/*卡类型 直接取卡上数据*/      
	Date2_t								ApplicationEnableDate;				/* 应用启用日期*/
	Date2_t								ApplicationValidDate;				/* 应用有效日期*/
%//	U8_t                                SAK;                            	/*卡种*/    
	   
%// U8_t                            	CardVer;                           	/*卡内版本号*/   
%// U32_t                           	RechargeSN;                        	/*充值序号  m1取0，公交CPU卡 累计充值次数*/
%// Date2_t                         	AddValueDate;                     	/*充值日期 M1卡叫加款日期，CPU取001A文件*/
%// Date2_t                         	SaleDate;                           /*售卡日期  M1卡叫启用日期，CPU卡为应用开通日期*/
%// U32_t                           	SystemTraceSN;                     	/*主机交易流水号*/

}; 

%// ---- 票卡公共结构
struct TicketComm_t{													
	TicketFamilyType_t					TicketFamilyType;                   /*票卡大类类型*/
	TicketType_t						TicketType;                         /*票种代码*/
	TicketCatalogID_t					TicketCatalogID;                    /*票卡目录,读写器返回时填0*/
	TicketPhyID_t						TicketPhyID;                        /*车票物理卡号 */
	string						        TicketLogicID<20>;                  /*票卡逻辑号公交CPU卡取应用序列号8个字节，M1卡取卡号(城市代码2个字节+行业代码2个字节+发行流水号4个字节)*/
	TicketStatus_t						TicketStatus;                       /*票卡状态 直接取票卡上的状态*/
	U32_t                                                   Reserved;                           /* 预留 */
	TestFlag_t					        TestFlag;			    			/*测试标记 一卡通交易该字段为0即默认为非测试票*/
	SN_t							TicketSN;                     	/*票卡交易计数(单程票指交易序列号,CPU卡指电子钱包联机脱机交易序列号,M1指钱包累计交易次数)*/
        ValueCent_t						RemainingValue;                     /*票卡剩余金额/次数*/   
	
};

%// ---- 银行卡票卡公共结构
struct BankCardTicketComm_t{													
	TicketFamilyType_t					TicketFamilyType;                   /*票卡归类类型*/
	TicketType_t						TicketType;                         /*票种代码*/
	TicketCatalogID_t					TicketCatalogID;                    /*票卡目录*/											
	string							PrimaryAccount<32>;		    /*主账户*/
	string					        	BankCode<32>;			    /*银行代码*/
	string                        		                PosNo<32>;                          /*检索参考号*/
	U32_t							TerminNo;                           /*终端流水号*/  
};

%// ---- 二维码票卡公共结构
struct QRCodeTicketComm_t{													
	TicketFamilyType_t					TicketFamilyType;                   /*票卡归类类型*/
	TicketType_t						TicketType;                         /*票种代码*/
	TicketCatalogID_t					TicketCatalogID;                    /*票卡目录*/											
	string							Uid<32>;			    /*用户ID*/
	string					        	QRCodeID<32>;			    /*卡号*/
};

%// ---- 单程票卡初始化结构
struct SJTInitComm_t{													  
	Date2_t							CardInitDate;                       /*票卡初始化日期*/
	InitBatchCode_t						InitBatchCode;                      /*票卡初始化批次*/
};

%// ---- 单程票发售结构
struct	SJTSale_t{													
	Paymentmeans_t						Paymentmeans;			    /*支付方式*/
	LanguageFlag_t						LanguageFlag;                       /*语言标记*/ 
	ValueCent_t						TransactionValue;                   /*交易金额*/
	ValueCent_t						TicketPriceValue;                   /*票价金额*/
        ValueCent_t						ChangeValue;                        /*找零金额*/
	ValueCent_t						OriginalValue;                      /*原始金额 TVM和BOM乘客投入的金额 用于乘客购票事务分析*/
	TransactionStatus_t                                     TransactionStatus;                  /*交易后状态*/  
	Date2_t							ValidStartDate;                     /*有效开始日期 普通单程票、出站票、福利票等BOM发售只取交易产生时间中年月日，ES发售的其他单程票取票卡中字段售票时间开始有效日期*/
	Date2_t							ValidEndDate;                       /*有效结束日期 普通单程票、出站票、福利票等BOM发售该值同有效开始日期，ES发售的其他单程票取票卡中字段售票时间结束有效日期*/
	OperatorID_t						OperatorID;                         /*操作员编号*/
	BOMShiftID_t						BOMShiftID;                         /*BOM班次号*/ 
};

%// ---- 储值票卡初始化结构
struct CPUInitComm_t{													 
	IssuerCode_t						IssuerCode;                         /*发卡方代码*/
	Date2_t								CardIssueDate;              /*票卡发行日期（应用启用日期）*/
%//	string						        AppSN<20>;                          /*应用序列号*/
};

%// ---- 储值票发售结构
struct CPUCardSale_t{                                                     
	Paymentmeans_t						Paymentmeans;						/*支付方式*/
	LanguageFlag_t						LanguageFlag;                        /*语言标记*/ 
	ValueCent_t			                        TransactionValue;                   /*交易金额（押金）一卡通（维护费）*/
	TransactionStatus_t                                     TransactionStatus;                  /*交易后状态*/  
	Date2_t				                        ValidStartDate;                     /*有效开始日期*/
	Date2_t				                        ValidEndDate;                       /*有效结束日期*/
	OperatorID_t		                                OperatorID;                         /*操作员编号*/
	BOMShiftID_t                                            BOMShiftID;                         /*BOM班次号*/ 
};

%// ---- 记名卡乘客结构
struct TicketPassengerComm_t{                                         
	PassengerTypeID_t                      PassengerTypeID;                                      /*持卡人类型标识*/
    StaffFlag_t                            PassengerStaffFlag;                                   /*持卡人职工标记*/
	UnicodeString_t                        PassengerCNName;	                                     /*姓名*/
	IdentificationType_t                   IdentificationType;                                   /*证件类型*/	
	string                                 IdentificationCode<32>;                               /*证件号码*/
};

%// ---- 一卡通乘客结构
struct YKTTicketPassengerComm_t{                                         
	UnicodeString_t                            PassengerCNName;	            /*姓名*/
	IdentificationType_t                IdentificationType;                 /*证件类型*/	
	string                            IdentificationCode<32>;             /*证件号码*/
	string							TelephoneCode<32>;                  /*电话号码*/
	UnicodeString_t							Address;                        /*地址*/
	UnicodeString_t							CompanyName;                        /*工作单位*/
};



%// ---- 储值票充值结构
struct CPUCardAddValue_t{                                           
	Paymentmeans_t						Paymentmeans;			    /*支付方式*/
	ValueCent_t					        TransactionValue;                   /*充值金额*/
	Date2_t							ValidDateBeforeAddValue;            /*交易前票卡有效日期至*/
	Date2_t							ValidDateAfterAddValue;             /*交易后票卡有效日期至*/
	ValueCent_t						NewRemainingValue;                  /*充值后卡内余额*/
%// BankCardPayment_t					        BankCardForCPUAddValue;		    /*银行卡支付结构*/
	OperatorID_t		                                OperatorID;                         /*操作员编号*/
	BOMShiftID_t                                            BOMShiftID;                         /*BOM班次号*/ 
	TerminateNumber_t				        TerminateNumber;                    /* 终端机编号 */
	AFCTime64_t                                             HostTransactionTime;                /*主机交易时间*/
%//	ValueCent_t					        RealTransactionValue;               /*实际交款金额，主要考虑通卡公司CPU卡老人卡优惠情况，地铁CPU卡该值同充值金额*/
};



%// ---- 储值票扣款结构
struct CPUCardDeduction_t{												
	ValueCent_t				TransactionValue;                   /*扣值金额*/
	OperatorID_t		                OperatorID;                         /*操作员编号*/
	BOMShiftID_t                            BOMShiftID;                         /*BOM班次号*/ 
};

%// ---- 手机卡扣款结构
struct MobileDeduction_t{												
        TicketFamilyType_t					TicketFamilyType;                   /*票卡大类类型*/
	TicketType_t						TicketType;                         /*票种代码*/
	U32_t                                                   Reserved;                           /* 预留 */
	TestFlag_t						TestFlag;			    /*测试标记*/
	IssuerCode_t						IssuerCode;                         /*发卡方代码*/
        SN_t							IssuerSN;			    /*发行流水号*/
        CityCode_t						CityCode;                           /*城市代码*/
        SIMType_t						SIMType;                            /*手机SIM卡类型*/
	SIMStatus_t						SIMStatus;                          /*手机SIM卡状态*/
	string							SIMID<32>;                          /*手机SIM卡号*/
	string							MobileNo<16>;                       /*手机号码*/
	SN_t							SIMTransSN;                         /*SIM卡交易计数*/
        ValueCent_t						TransactionValue;                   /*扣值金额*/
	SIMStatus_t						SIMStatusAfterTrans;		    /*交易后手机SIM卡状态*/
        ValueCent_t						RemainingValueAfterTrans;           /*交易后SIM卡内余额*/
	OperatorID_t		                                OperatorID;                         /*操作员编号*/
	BOMShiftID_t                                            BOMShiftID;                         /*BOM班次号*/ 
};

%// ---- 银行卡扣款结构
struct BankCardDeduction_t{											
        ValueCent_t						TransactionValue;                   /*扣值金额*/
	ValueCent_t						RemainingValueAfterTrans;           /*交易后银行卡内余额*/
	OperatorID_t		                                OperatorID;                         /*操作员编号*/
	BOMShiftID_t                                            BOMShiftID;                         /*BOM班次号*/ 
	U32_t                                                   Reserved1;                          /* 预留1 */
	U32_t                                                   Reserved2;                          /* 预留2 */
};


%// ---- 银行卡更新结构
struct BankCardSurcharge_t{	
	ValueCent_t						PreAuthValue;                       /*预付款金额*/
	string							AuthCode<32>;                       /*授权码*/	
	SurchargeCode_t						SurchargeCode;                      /*更新方式*/
	Paymentmeans_t		                                Paymentmeans;                       /*支付方式*/
	DeviceID_t                      	                SaleDeviceID;                       /*发售设备节点 填0*/
	ValueCent_t                   		                TicketSaleValue;                    /*发售金额*/ 
	SurchargeArea_t                                         SurchargeArea;                      /*更新区域*/ 
	ValueCent_t                    		                SurchargeValue;                     /*更新金额*/ 
	OperatorID_t                                            OperatorID;                         /*操作员编号*/ 
	BOMShiftID_t                                            BOMShiftID;                         /*BOM班次号*/	
	U32_t                        		                ReserveOne;                         /*自定义域1*/
	U32_t                        		                ReserveTwo;                         /*自定义域2*/  	
};

%// ---- 银行卡进站结构
struct BankCardEntry_t{											
        ValueCent_t						PreAuthValue;                       /*预付款金额*/	
	string		                		        AuthCode<32>;                       /*授权码*/	
	U32_t                        		                ReserveOne;                         /*自定义域1*/ 
	U32_t                        		                ReserveTwo;                         /*自定义域2*/ 
};

%// ---- 银行卡出站结构
struct BankCardExit_t{											
        ValueCent_t						TransValue;                   	    /*优惠前交易金额*/
	DeviceID_t		                	        StartDevice;                        /*进站设备编号*/
	AFCTime64_t                        	                StartTime;                          /*进站时间*/	 
	U32_t                        		                ReserveOne;                         /*自定义域1*/
	U32_t                        		                ReserveTwo;                         /*自定义域2*/ 	
};

%// ---- 二维码出站结构
struct QRCodeExit_t{											
        ValueCent_t					       TransValue;                   	     /*交易金额*/
	DeviceID_t		                	       StartDevice;                    	     /*进站设备编号*/
	AFCTime64_t                        	               StartTime;                      	     /*进站时间*/	 
	U32_t                        		               ReserveOne;                     	     /*自定义域1*/
	U32_t                        		               ReserveTwo;                     	     /*自定义域2*/ 	
};

%// ---- 二维码更新结构
struct QRCodeSurcharge_t{	
        SurchargeCode_t						SurchargeCode;                      /*更新方式*/
	Paymentmeans_t		                                Paymentmeans;                       /*支付方式*/
	DeviceID_t                      	                SaleDeviceID;                       /*发售设备节点*/
	ValueCent_t                   		                TicketSaleValue;                    /*发售金额*/ 
	SurchargeArea_t                                         SurchargeArea;                      /*更新区域*/ 
	ValueCent_t                    		                SurchargeValue;                     /*更新金额*/ 
	OperatorID_t                                            OperatorID;                         /*操作员编号*/ 
	BOMShiftID_t                                            BOMShiftID;                         /*BOM班次号*/	
	U32_t                        		                ReserveOne;                         /*自定义域1*/
	U32_t                        		                ReserveTwo;                         /*自定义域2*/  	
};




%// ---- 单程票退票结构
struct SJTRefund_t{													
	ValueCent_t				PriceValue;			    /*票卡售买时金额*/
	RefundReason_t				RefundReason;                       /*退票原因*/
        ValueCent_t				TransactionValue;		    /*实际退票金额*/
	OperatorID_t		                OperatorID;                         /*操作员编号*/
	BOMShiftID_t                            BOMShiftID;                         /*BOM班次号*/ 
};

%// ---- 储值票即时退票结构
struct CPUCardImmediateRefund_t{                                       
	Date2_t					ValidDate;                          /*票卡有效日期至*/
	ValueCent_t				RemainingValue;                     /*票卡余额/次*/
	ValueCent_t				CardDepositValue;                   /*票卡押金*/
	RefundReason_t				RefundReason;                       /*退票原因*/
	ValueCent_t				CardDepreciationValue;              /*票卡折旧费*/
	ValueCent_t				ChargeValue;                        /*退票手续费*/
        ValueCent_t				TransactionValue;                   /*实际退票金额*/
	OperatorID_t		                OperatorID;			    /*操作员编号*/
	BOMShiftID_t                            BOMShiftID;                         /*BOM班次号*/ 
};

%// ---- 储值票非即时退票结构
struct CPUCardNonImmediateRefund_t{                                       
	AFCTime64_t						ConfirmTimeAtAcc;                   /*ACC确认时间*/
        OperatorID_t						OperatorIDAtAcc;                    /*ACC确认人*/   
        string							ApplyBillNo<20>;                    /*申请单号*/
	UnicodeString_t                                         PassengerCNName;	            /*姓名*/
        Gender_t						Gender;                             /*性别*/
	IdentificationType_t				        IdentificationType;                 /*证件类型*/	
	string							IdentificationCode<32>;             /*证件号码*/
	string							TelephoneCode<32>;                  /*电话号码*/
	UnicodeString_t						Address;                            /*地址*/
	TicketStatus_t						AccTicketStatus;					/*ACC票卡状态*/
	Date2_t							ValidDateAtACC;                     /*ACC确认票卡有效日期至*/
	ValueCent_t						RemainingValueAtACC;                /*ACC确认余额/次*/
	ValueCent_t						CardDepositValueAtACC;              /*ACC确认押金*/
	RefundReason_t						RefundReason;                       /*退票原因*/
	ValueCent_t						CardDepreciationValue;              /*票卡折旧费*/
	ValueCent_t						ChargeValue;                        /*退票手续费*/
        ValueCent_t						TransactionValue;                   /*实际退票金额*/
	OperatorID_t		                                OperatorID;			    /*操作员编号*/
	BOMShiftID_t                                            BOMShiftID;                         /*BOM班次号*/ 
};

%// ---- 储值票续期结构
struct CPUCardValidityPeriod_t{											
        Date2_t							OldValidDate;                       /*票卡原有效日期至*/
	Date2_t							NewValidDate;                       /*票卡新有效日期至*/
        ValueCent_t						TransactionValue;                   /*手续费*/
	OperatorID_t		                                OperatorID;                         /*操作员编号*/
	BOMShiftID_t                                            BOMShiftID;                         /*BOM班次号*/ 
};

%// ---- 储值票锁卡结构
struct CPUCardBlock_t{													
        BlockReasonCode_t			BlockReasonCode;                    /*锁卡原因*/ 
	OperatorID_t		                OperatorID;                         /*操作员编号*/
	BOMShiftID_t                            BOMShiftID;                         /*BOM班次号*/ 
};

%// ---- 储值票解锁结构
struct CPUCardUnblock_t{												
	UnblockReasonCode_t			UnblockReasonCode;	            /*解锁原因*/
	OperatorID_t		                OperatorID;                         /*操作员编号*/
	BOMShiftID_t                            BOMShiftID;                         /*BOM班次号*/ 
};

%// ---- 票卡更新结构
struct TicketSurcharge_t{											
	SurchargeCode_t						SurchargeCode;                      /*更新方式*/
        Paymentmeans_t						Paymentmeans;			    /*支付方式*/
        TransactionStatus_t					TransStatusBeforeTrans;		    /*交易前票卡交易状态*/
	TransactionStatus_t					TransStatusAfterTrans;		    /*交易后票卡交易状态*/
	DeviceID_t						SaleDeviceID;                       /*售票设备节点编号 只应用于单程票*/
	ValueCent_t						TicketSaleValue;                    /*发售金额 只应用于单程票*/
	SurchargeArea_t						SurchargeArea;                      /*更新区域（0-非付费区 1-付费区）*/
        ValueCent_t						TransactionValue;                   /*更新金额*/
	OperatorID_t		                                OperatorID;                         /*操作员编号*/
	BOMShiftID_t                                            BOMShiftID;                         /*BOM班次号*/ 
};

%// ---- 票卡进站结构
struct TicketEntry_t{													
        TransactionStatus_t					TransStatusBeforeTrans;		    /*交易前票卡交易状态*/
	TransactionStatus_t					TransStatusAfterTrans;		    /*交易后票卡交易状态*/
        ValueCent_t						RemainingValue;                     /*单程票为发售金额 储值票、一卡通、手机票为票卡余额，余次*/
};

%// ---- 票卡出站结构
struct TicketExit_t{													
      DeviceID_t						EntryDeviceID;                      /*进站设备节点编号*/
      AFCTime64_t						EntryTime;                          /*进站时间*/
      TransactionStatus_t					TransStatusBeforeTrans;		    /*交易前票卡交易状态*/
      TransactionStatus_t					TransStatusAfterTrans;		    /*交易后票卡交易状态*/
      ValueCent_t						RemainingValueBeforeTrans;          /*交易前卡内余额*/
      ValueCent_t						RemainingValueAfterTrans;           /*交易后卡内余额*/
      ValueCent_t						TransactionValue;                   /*交易金额*/
      ValueCent_t						OriginalValue;                      /*应收金额*/
      SJTRecycleFlag_t					        SJTRecycleFlag;                     /*单程票回收标记*/
      DeduceLocation_t					        DeduceLocation;                     /*扣费位置（0－公用钱包 1－地铁专用钱包1 2－地铁专用钱包2 3－员工出站，不扣费 4－单程票）*/
%//   TerminateNumber_t				                TerminateNumber;                    /* 终端机编号 */
%//   SN_t						        SAMSN;                              /* SAM卡流水号，CPU卡终端交易序号,M1填0 */
};

%// ---- 一卡通票卡出站结构
struct YKTTicketExit_t{													
    DeviceID_t							EntryDeviceID;                      /*进站设备节点编号*/
    AFCTime64_t							EntryTime;                          /*进站时间*/
    TransactionStatus_t					        TransStatusBeforeTrans;				/*交易前票卡交易状态*/
	TransactionStatus_t					TransStatusAfterTrans;				/*交易后票卡交易状态*/
    ValueCent_t							RemainingValueBeforeTrans;          /*交易前卡内余额*/
	ValueCent_t						RemainingValueAfterTrans;           /*交易后卡内余额*/
    ValueCent_t							TransactionValue;                   /*交易金额*/
    ValueCent_t							OriginalValue;                      /*应收金额*/
};

%// ---- 出站积分优惠结构
struct RebateScheme_t{													
    ConcessionID_t						JoinConcessionID;                   /*联乘优惠代码*/
    JoinConcessionType_t				JoinConcessionType;                 /*联乘优惠方式*/ 
    ValueCent_t                         JoinConcessionValue;                /*联乘优惠金额*/
    Percentage_t                        JoinConcessionPercentage;           /*联乘优惠比例*/
    ConcessionID_t						PileConcessionID;		    		/*累计优惠代码*/
	JoinConcessionType_t                PileConcessionType;                 /*累计优惠方式*/
    ValueCent_t                         PileConcessionValue;                /*累积优惠金额*/
    Percentage_t						PileConcessionPercentage;           /*累计优惠比例*/
    ValueCent_t                         CurrentBonus;                       /*当笔交易积分*/
    ValueCent_t                         AccumulationBonus;                  /*累计优惠积分*/
};


%// ---- 单程票发售交易结构
struct SJTSale_TX{														
	TicketComm_t						TicketComm;				/*票卡公共结构*/
	SJTInitComm_t						SJTInitComm;				/*单程票卡初始化结构*/
	SJTSale_t						SJTSale;				/*单程票发售结构*/
	U32_t                                                   Reserved1;                              /* 预留1 */
	U32_t                                                   Reserved2;                              /* 预留2 */
};

%// ---- 非记名储值卡发售交易结构
struct CPUSale_TX{														
	TicketComm_t						TicketComm;				/*票卡公共结构*/
	CPUInitComm_t						CPUInitComm;				/*储值票卡初始化结构*/
	CPUCardSale_t						CPUCardSale;				/*储值票发售结构*/
	U32_t                                                   Reserved1;                              /* 预留1 */
	U32_t                                                   Reserved2;                              /* 预留2 */
};

%// ---- 记名储值卡发售交易结构
struct NamedCPUSale_TX{												
	TicketComm_t						TicketComm;				/*票卡公共结构*/
	CPUInitComm_t						CPUInitComm;				/*储值票卡初始化结构*/
	CPUCardSale_t						CPUCardSale;				/*储值票发售结构*/
	TicketPassengerComm_t				TicketPassengerComm;				/*记名卡乘客结构*/
	U32_t                                                   Reserved1;                              /* 预留1 */
	U32_t                                                   Reserved2;                              /* 预留2 */
};

%// ---- 储值票充值交易结构
struct CPUCardAddValue_TX{												
	TicketComm_t						TicketComm;				/*票卡公共结构*/
	CPUInitComm_t						CPUInitComm;				/*储值票卡初始化结构*/
	CPUCardAddValue_t					CPUCardAddValue;			/*储值票充值结构*/
	U32_t                                                   Reserved1;                              /* 预留1 */
	U32_t                                                   Reserved2;                              /* 预留2 */
};

%// ---- 储值票扣款交易结构
struct CPUCardDeduction_TX{												
	TicketComm_t						TicketComm;				/*票卡公共结构*/
	CPUInitComm_t						CPUInitComm;				/*储值票卡初始化结构*/
	CPUCardDeduction_t					CPUCardDeduction;			/*储值票扣款结构*/
	U32_t                                                   Reserved1;                              /* 预留1 */
	U32_t                                                   Reserved2;                              /* 预留2 */
};

%// ---- 手机卡扣款交易结构
struct MobileDeduction_TX{												
        MobileDeduction_t					MobileDeduction;			/*手机卡扣款结构*/
	U32_t                                                   Reserved1;                              /* 预留1 */
	U32_t                                                   Reserved2;                              /* 预留2 */
};

%// ---- 银行卡扣款交易结构
struct BankCardDeduction_TX{											
        BankCardTicketComm_t				        BankCardTicketComm;			/*银行卡票卡公共结构*/	
	BankCardDeduction_t					BankCardDeduction;			/*银行卡扣款结构*/
};

%// ---- 银行卡更新交易结构
struct BankCardSurcharge_TX{
	BankCardTicketComm_t				        BankCardTicketComm;					/*银行卡票卡公共结构*/											
        BankCardSurcharge_t				  	BankCardSurcharge;					/*银行卡更新结构*/
};

%// ---- 银行卡进站交易结构
struct BankCardEntry_TX{
	BankCardTicketComm_t				        BankCardTicketComm;					/*银行卡票卡公共结构*/											
        BankCardEntry_t						BankCardEntry;						/*银行卡进站结构*/
};

%// ---- 银行卡出站交易结构
struct BankCardExit_TX{											
	BankCardTicketComm_t				        BankCardTicketComm;					/*银行卡票卡公共结构*/
        BankCardExit_t						BankCardExit;						/*银行卡出站结构*/
};


%// ---- 二维码更新交易结构
struct QRCodeSurcharge_TX{
	QRCodeTicketComm_t					QRCodeTicketComm;					/*二维码票卡公共结构*/											
        QRCodeSurcharge_t					QRCodeSurcharge;					/*二维码更新结构*/
	U32_t                        		                ReserveOne;                      	                /*自定义域1*/ 
	U32_t                        		                ReserveTwo;                      	                /*自定义域2*/
};

%// ---- 二维码进站交易结构
struct QRCodeEntry_TX{
	QRCodeTicketComm_t					QRCodeTicketComm;					/*二维码票卡公共结构*/											
	U32_t                        		                ReserveOne;                      	                /*自定义域1*/ 
	U32_t                        		                ReserveTwo;                      	                /*自定义域2*/
};

%// ---- 二维码出站交易结构
struct QRCodeExit_TX{											
	QRCodeTicketComm_t					QRCodeTicketComm;					/*二维码票卡公共结构*/
        QRCodeExit_t						QRCodeExit;						/*二维码出站结构*/
};


%// ---- 单程票退票交易结构
struct SJTRefund_TX{												
	TicketComm_t						TicketComm;					/*票卡公共结构*/
        SJTInitComm_t						SJTInitComm;					/*单程票初始化结构*/
	SJTRefund_t						SJTRefund;					/*单程票退票结构*/
	U32_t                                                   Reserved1;                                      /* 预留1 */
	U32_t                                                   Reserved2;                                      /* 预留2 */
};

%// ---- 储值票即时退票交易结构
struct CPUCardImmediateRefund_TX{									
	TicketComm_t						TicketComm;				/*票卡公共结构*/
	CPUInitComm_t						CPUInitComm;				/*储值票初始化结构*/
	CPUCardImmediateRefund_t			        CPUCardImmediateRefund;			/*储值票即时退票结构*/
	U32_t                                                   Reserved1;                              /* 预留1 */
	U32_t                                                   Reserved2;                              /* 预留2 */
};

%// ---- 储值票非即时退票交易结构
struct CPUCardNonImmediateRefund_TX{								
        TicketComm_t						TicketComm;				/*票卡公共结构*/
	CPUInitComm_t						CPUInitComm;				/*储值票初始化结构*/
	CPUCardNonImmediateRefund_t			        CPUCardNonImmediateRefund;		/*储值票非即时退票结构*/
	U32_t                                                   Reserved1;                              /* 预留1 */
	U32_t                                                   Reserved2;                              /* 预留2 */
};

%// ---- 储值票续期交易结构
struct CPUCardValidityPeriod_TX{									
	TicketComm_t						TicketComm;				/*票卡公共结构*/
	CPUInitComm_t						CPUInitComm;				/*储值票初始化结构*/
	CPUCardValidityPeriod_t				        CPUCardValidityPeriod;			/*储值票续期结构*/
	U32_t                                                   Reserved1;                              /* 预留1 */
	U32_t                                                   Reserved2;                              /* 预留2 */
};

%// ---- 储值票锁卡交易结构
struct CPUCardBlock_TX{												
	TicketComm_t						TicketComm;				/*票卡公共结构*/
	CPUInitComm_t						CPUInitComm;				/*储值票初始化结构*/
	CPUCardBlock_t						CPUCardBlock;				/*储值票锁卡结构*/
	U32_t                                                   Reserved1;                              /* 预留1 */
	U32_t                                                   Reserved2;                              /* 预留2 */
};

%// ---- 储值票解锁交易结构
struct CPUCardUnblock_TX{											
	TicketComm_t						TicketComm;				/*票卡公共结构*/
	CPUInitComm_t						CPUInitComm;				/*储值票初始化结构*/
	CPUCardUnblock_t					CPUCardUnblock;				/*储值票解锁结构*/
	U32_t                                                   Reserved1;                              /* 预留1 */
	U32_t                                                   Reserved2;                              /* 预留2 */
};

%// ---- 票卡更新交易结构
struct TicketSurcharge_TX{											
        TicketComm_t						TicketComm;				/*票卡公共结构*/
	TicketSurcharge_t					TicketSurcharge;			/*票卡更新结构*/
	U32_t                                                   Reserved1;                              /* 预留1 */
	U32_t                                                   Reserved2;                              /* 预留2 */
};

%// ---- 票卡进站交易结构
struct TicketEntry_TX{													
        TicketComm_t						TicketComm;				/*票卡公共结构*/
	TicketEntry_t						TicketEntry;				/*票卡进站结构*/
	U32_t                                                   Reserved1;                              /* 预留1 */
	U32_t                                                   Reserved2;                              /* 预留2 */
};

%// ---- 票卡出站交易结构
struct TicketExit_TX{												
        TicketComm_t						TicketComm;				/*票卡公共结构*/
	TicketExit_t						TicketExit;				/*票卡出站结构*/
	RebateScheme_t						RebateScheme;				/*出站积分优惠结构*/
	U32_t                                                   Reserved1;                              /* 预留1 */
	U32_t                                                   Reserved2;                              /* 预留2 */
};

%// ---- 一卡通出站交易结构
struct YKTTicketExit_TX{												
    TicketComm_t						TicketComm;						/*票卡公共结构*/
	YKTTicketExit_t						TicketExit;						/*一卡通票卡出站结构*/
	RebateScheme_t						RebateScheme;					/*出站积分优惠结构*/
	U32_t                               Reserved1;                      /* 预留1 */
	U32_t                               Reserved2;                      /* 预留2 */
};




%// ---- 一卡通售卖
struct YKTSale_TX{													
        TicketComm_t						TicketComm;					/*票卡公共结构*/
	CPUCardSale_t						CPUCardSale;					/*储值票售卖结构*/
	YKTTicketPassengerComm_t                                YKTTicketPassengerComm;                         /*一卡通乘客结构*/
};
%// ---------------------------------------------------------------------------------------


%// ---------------------------------------------------------------------------------------
%// ---- 审计寄存器定义
%// ---------------------------------------------------------------------------------------

%// ---- 审计寄存器公共结构
struct ARComm_t{												
	DeviceID_t						        DeviceID;		            /*设备节点编号*/
	ARFileTag_t							ARFileTag;                          /* 审计方式*/
	SN_t								ACCUDSNStart;                       /*上一个寄存器文件关联的最后一笔交易记录的UDSN+1*/
	SN_t								ACCUDSNEnd;			    /*当前寄存器文件关闭时所关联的最后一笔交易记录的UDSN*/
	SN_t								YKTUDSNStart;                       /*上一个寄存器文件关联的最后一笔市民卡、公交卡交易记录的UDSN+1*/
	SN_t								YKTUDSNEnd;                         /*当前寄存器文件关闭时所关联的最后一笔市民卡、公交卡交易记录的UDSN*/
	SN_t								MobileUDSNStart;                    /*上一个寄存器文件关联的最后一笔手机支付交易记录的UDSN+1*/
	SN_t								MobileUDSNEnd;                      /*当前寄存器文件关闭时所关联的最后一笔手机支付交易记录的UDSN*/
	SN_t								BankUDSNStart;                      /*上一个寄存器文件关联的最后一笔手机支付交易记录的UDSN+1*/
	SN_t								BankUDSNEnd;                        /*当前寄存器文件关闭时所关联的最后一笔手机支付交易记录的UDSN*/
};

%// ---- 闸机票种信息审计寄存器
struct AGMTicketFareInfo_t{												
	TicketType_t						        TicketType;                         /*票种代码*/
	IssuerCode_t						        IssuerCode;                         /*发卡方代码*/
	ARCount_t							NbofEntries;		            /*进站人数*/
	ARCount_t							NbofExit;	        	    /*出站人数*/	
	ValueCent_t							ValueCollected;                     /*交易金额*/
	ARCount_t							NbofBlocked;                        /*被拒黑名单数*/
};

%// ---- 闸机综合快照审计寄存器
struct AGMSnapshotInfo_t{													
       ARCount_t							TotalNbofSJTRead;                       /*单程票读写次数*/
       ARCount_t							TotalNbofACCCSCRead;                    /*储值票读写次数*/
       ARCount_t							TotalNbofYKTCSCRead;                    /*一卡通读写次数*/
       ARCount_t							TotalNbofMobileRead;                    /*手机卡读写次数*/
       ARCount_t							TotalNbofSJTEntry;                      /*单程票进站次数*/
       ARCount_t							TotalNbofACCCSCEntry;                   /*储值票进站次数*/
       ARCount_t							TotalNbofYKTCSCEntry;                   /*一卡通进站次数*/
       ARCount_t							TotalNbofMobileEntry;                   /*手机卡进站次数*/
       ARCount_t							TotalNbofSJTExit;                       /*单程票出站次数*/
       ARCount_t							TotalNbofACCCSCExit;                    /*储值票出站次数*/
       ARCount_t							TotalNbofYKTCSCExit;                    /*一卡通出站次数*/
       ARCount_t							TotalNbofMobileExit;                    /*手机卡出站次数*/
       ValueCent_t							TotalValueCollectedbySJT;               /*单程票交易金额*/
       ValueCent_t							TotalValueCollectedbyACCCSC;            /*储值票交易金额*/
       ValueCent_t							TotalValueCollectedbyYKTCSC;            /*一卡通交易金额*/
       ValueCent_t							TotalValueCollectedbyMobile;            /*手机卡交易金额*/
       ARCount_t							TotalNbofSJTCallback;                   /*单程票回收张数*/
       ARCount_t							TotalNbofInvalidSJT;                    /*无效单程票数量*/
       ARCount_t							TotalNbofInvalidACCCSC;                 /*无效储值票数量*/
       ARCount_t							TotalNbofInvalidYKTCSC;                 /*无效一卡通数量*/
       ARCount_t							TotalNbofInvalidMobile;                 /*无效手机卡数量*/
       ARCount_t							NbofSJTBlocked;                         /*被锁单程票黑名单数量*/
       ARCount_t							NbofACCCSCBlocked;                      /*被锁储值票黑名单数量*/
       ARCount_t							NbofYKTBlocked;                         /*被锁一卡通黑名单数量*/
       ARCount_t							NbofMobileBlocked;                      /*被锁手机卡黑名单数量*/
       ARCount_t							TotalNbofTestEntry;                     /*测试票进站数*/
       ARCount_t							TotalNbofTestExit;                      /*测试票出站数*/
       ValueCent_t							TotalValueTestCollectedbySJT;           /*单程票测试票交易金额（预留）*/
       ValueCent_t							TotalValueTestCollectedbyACCCSC;        /*储值票测试票交易金额（预留）*/
       ValueCent_t							TotalValueTestCollectedbyYKTCSC;        /*一卡通测试票交易金额（预留）*/
       ValueCent_t							TotalValueTestCollectedbyMobile;        /*手机卡测试票交易金额（预留）*/
       ARCount_t							TotalNbofManualOpeningClosedFlaps;      /*扇门强制打开次数*/
       ARCount_t							TotalNbofIntrusionEntries;              /*进站闯入次数*/
       ARCount_t							TotalNbofIntrusionExit;                 /*出站闯入次数*/
       ARCount_t							TotalNbofSJTJams;                       /*单程票阻塞张数*/
       ARCount_t							Reserved1;                              /* 预留1 */
       ARCount_t							Reserved2;                              /* 预留2 */
       ARCount_t							Reserved3;                              /* 预留3 */
	
};

%// ---- 自动检票机审计信息
struct AGMAuditInfo_t{	
	AGMSnapshotInfo_t					AGMSnapshotInfo;					/*自动检票机综合快照审计寄存器*/
	AGMTicketFareInfo_t					AGMTicketFareInfo<>;					/*自动检票机票种信息审计寄存器*/
};

%// ---- 半自动票种信息审计寄存器
struct BOMTicketFareInfo_t{													
	TicketType_t						TicketType;                             /*票种代码*/
	IssuerCode_t						IssuerCode;                             /*发卡方代码*/
	ARCount_t						NbofIssue;                              /*发售数量*/
	ValueCent_t						TotalValueofIssue;                      /*发售金额*/
	ValueCent_t						TotalValueofDeposit;                    /*押金金额*/ 
        ARCount_t						NbofAddValue;                           /*充值数量*/
	ValueCent_t						TotalValueofAddValue;	                /*充值金额*/
	ARCount_t						NbofSurcharge;                          /*更新数量*/
	ValueCent_t						TotalValueofSurcharge;                  /*更新金额*/
	ARCount_t						NbofImmediateRefund;                    /*即时退款数量*/
	ValueCent_t						TotalValueofImmediateRefund;	        /*即时退票金额*/
        ValueCent_t						TotalValueofImmediateRefundCharge;      /*即时退票手续费*/
	ARCount_t						NbofNonImmediateRefund;                 /*非即时退票数量*/
	ValueCent_t						TotalValueofNonImmediateRefund;	        /*非即时退票金额*/
        ValueCent_t						TotalValueofNonImmediateRefundCharge;   /*非即时退票手续费*/
	ValueCent_t						TotalValueofRefundDeposit;              /*退押金金额*/
	ARCount_t						NbofBlacklistBlocked;                   /*黑名单阻止数量*/
	ARCount_t						NbofUnblocked;                          /*解锁数量*/
	ARCount_t						NbofCardLossDeclaration;                /*卡挂失数量*/
        ARCount_t						NbofCardLossDeclarationCancel;          /*卡解挂数量*/
	ARCount_t						NbofTicketValidityPeriodExtension;      /*延期数量*/
};

%// ---- 半自动售票机票卡库存明细结构
struct TicketCatalogDetails_t {												
	TicketCatalogID_t					TicketCatalogID;			/*票卡目录*/
	TicketQuantity_t					RemainingTicketQuantity;                /*票卡库存数量*/
};

%// ---- 半自动售票机综合快照审计寄存器
struct BOMSnapshotInfo_t{												
	ARCount_t							TotalNbofIssueSJT;                      /*单程票发售数量*/
	ValueCent_t							TotalValueofIssueSJT;                   /*单程票发售金额*/
        ARCount_t							TotalNbofIssueACCCSC;                   /*储值票发售数量*/
	ValueCent_t							TotalValueofIssueACCCSC;                /*储值票发售金额*/
%//     ValueCent_t							TotalValueofDeposit;                    /*押金金额*/
	ARCount_t							NbofAddValue;                           /*充值数量*/
	ValueCent_t							TotalValueofAddValue;	                /*充值金额*/
	ARCount_t							NbofSurcharge;                          /*更新数量*/
	ValueCent_t							TotalValueofSurcharge;                  /*更新金额*/
        ARCount_t							NbofImmediateRefund;                    /*即时退款数量*/
	ValueCent_t							TotalValueofImmediateRefund;	        /*即时退票金额*/
        ValueCent_t							TotalValueofImmediateRefundCharge;      /*即时退票手续费*/
	ARCount_t							NbofNonImmediateRefund;                 /*非即时退票数量*/
	ValueCent_t							TotalValueofNonImmediateRefund;	        /*非即时退票金额*/
        ValueCent_t							TotalValueofNonImmediateRefundCharge;   /*非即时退票手续费*/
        ValueCent_t							TotalValueofRefundDeposit;              /*退押金金额*/
	ARCount_t							NbofBlacklistBlocked;                   /*黑名单阻止数量*/
	ARCount_t							NbofUnblocked;                          /*解锁数量*/
	ARCount_t							NbofCardLossDeclaration;                /*卡挂失数量*/
        ARCount_t							NbofCardLossDeclarationCancel;          /*卡解挂数量*/
	ARCount_t							NbofTicketValidityPeriodExtension;      /*延期数量*/
        ARCount_t							TotalNbofTestIssueSJT;                  /*测试单程票发售数量*/
	ValueCent_t							TotalValueofTestIssueSJT;               /*测试单程票发售金额*/
        ARCount_t							TotalNbofTestIssueACCCSC;               /*测试储值票发售数量*/
	ValueCent_t							TotalValueofTestIssueACCCSC;            /*测试储值票发售金额*/
        ValueCent_t							TotalValueofTestDeposit;                /*测试储值票押金金额*/
	ARCount_t							NbofTestAddValue;                       /*测试票充值数量*/
	ValueCent_t							TotalValueofTestAddValue;		/*测试票充值金额*/
	ARCount_t							NbofTestSurcharge;                      /*测试更新数量*/
	ValueCent_t							TotalValueofTestSurcharge;              /*测试更新金额*/
	ARCount_t							NbofTestRefund;                         /*测试退票数量*/
	ValueCent_t							TotalValueofTestRefund;	                /*测试退票金额*/
	ARCount_t							TotalNbofValidSJTCSCRead;               /*单程票有效读写次数*/
	ARCount_t							TotalNbofValidACCCSCRead;               /*储值票有效读写次数*/
	ARCount_t							TotalNbofValidYKTCSCRead;               /*一卡通有效读写次数*/ 
	ARCount_t							Reserved1;                              /* 预留1 */
        ARCount_t							Reserved2;                              /* 预留2 */
        ARCount_t							Reserved3;                              /* 预留3 */
        TicketCatalogDetails_t				                TicketCatalogDetails<>;			/*票卡库存明细*/
};

%// ---- 半自动售票机审计信息
struct BOMAuditInfo_t{	
	BOMSnapshotInfo_t					BOMSnapshotInfo;					/*半自动售票机综合快照审计寄存器*/
	BOMTicketFareInfo_t					BOMTicketFareInfo<>;					/*半自动售票机票种审计寄存器*/
};

%// ---- 自动售票机综合快照审计寄存器
struct TVMSnapshotInfo_t{											
    	ARCount_t							TotalNbofStockInSJT;                    /*单程票入库数量*/
	ARCount_t							TotalNbofStockOutSJT;                   /*单程票出库数量*/
	ARCount_t							TotalNbofIssuedSJT;                     /*单程票发售数量*/
	ValueCent_t							TotalValueofIssueSJT;                   /*单程票发售金额*/
	ARCount_t							TotalNbofRejectedSJT;                   /*单程票废票数量*/
        ValueCent_t							TotalValueofOverCashPayment;            /*多付发售金额*/
	ValueCent_t							TotalValueofChangeGiven;                /*找零金额*/
	ValueCent_t							TotalValueofCashIn;                     /*加币金额*/
	ValueCent_t							TotalValueofCashOut;                    /*导出钱箱金额*/
        ARCount_t							NbofCashAddValue;                       /*现金充值数量*/
	ValueCent_t							TotalValueofCashAddValue;	        /*现金充值金额*/
        ARCount_t							NbofBankAddValue;                       /*银行卡充值数量*/
	ValueCent_t							TotalValueofBankAddValue;	        /*银行卡充值金额*/
	ContainerID_t						        ContainerID;                            /*纸币废币箱编号*/
	ARCount_t							TotalNbofWasteMoney;                    /*纸币废币箱纸币总张数*/
%//	ValueCent_t							TotalValueofWasteMoney;                 /*纸币废币箱纸币总金额*/
        
        ARCount_t							TotalNbofCoinInsert;                    /*接受硬币个数*/
	ValueCent_t							TotalValuebyInsertedCoins;              /*硬币交易金额*/
        ARCount_t							TotalNbofBNAInsert;                     /*接受纸币张数*/
	ValueCent_t							TotalValuebyInsertedBanknote;           /*纸币交易金额*/
	ARCount_t							TotalNbofValidSJTCSCRead;               /*单程票有效读写次数*/
        ARCount_t							TotalNbofValidACCCSCRead;               /*储值票有效读写次数*/
	ARCount_t							TotalNbofValidYKTCSCRead;               /*一卡通有效读写次数*/
 	ARCount_t							Reserved1;                              /* 预留1 */
        ARCount_t							Reserved2;                              /* 预留2 */
        ARCount_t							Reserved3;                              /* 预留3 */
	CashBoxInfo_t                                                   CashBoxInfos<>;                         /*钱箱信息列表*/
};


%// ---- 单程票发售审计信息
struct SJTAuditInfo_t{
        ValueCent_t                                          SJTFaceValue;                         /* 发售面值*/
	ARCount_t					     NbofCash;                             /* 钱币数量*/
	ValueCent_t					     TotalValueofCash;	                   /* 钱币金额*/
};

%// ---- 自动售票机票种审计信息
struct TVMTicketFareInfo_t{
        TicketType_t				              TicketType;                         /*票种代码*/
	IssuerCode_t					      IssuerCode;                         /*发卡方代码*/
	ARCount_t					      NbofCashAddValue;                   /*现金充值数量*/
	ValueCent_t					      TotalValueofCashAddValue;	          /*现金充值金额*/
	ARCount_t					      NbofBankAddValue;                   /*银行卡充值数量*/
	ValueCent_t					      TotalValueofBankAddValue;	          /*银行卡充值金额*/
};

%// ---- 自动售票机审计信息
struct TVMAuditInfo_t{														
        TVMSnapshotInfo_t                                               TVMSnapshotInfos;
	SJTAuditInfo_t					                SJTAuditInfos<>;			/*单程票发售审计寄存器*/
	TVMTicketFareInfo_t					        TVMTicketFareInfo<>;		        /*自动售票机票种审计寄存器*/
};

%// ---- 查询充值机票种信息结构
struct ISMTicketFareInfo_t{												
	TicketType_t						        TicketType;                             /*票种代码*/
        IssuerCode_t					                IssuerCode;                             /*发卡方代码*/
	ARCount_t							NbofAddValueByCash;			/*现金充值数量*/
	ValueCent_t							TotalValueofAddValueByCash;             /*现金充值金额*/
	ARCount_t							NbofAddValueByBankCard;	                /*银行卡充值数量*/
	ValueCent_t							TotalValueofAddValueByBankCard;         /*银行卡充值金额*/
};

%// ---- 查询充值机综合快照结构
struct ISMSnapshotInfo_t{														
    ARCount_t							NbofAddValueByCash;			/*现金充值数量*/
	ValueCent_t							TotalValueofAddValueByCash;             /*现金充值金额*/
	ARCount_t							NbofAddValueByBankCard;	                /*银行卡充值数量*/
	ValueCent_t							TotalValueofAddValueByBankCard;         /*银行卡充值金额*/
    ContainerID_t						BankNoteBoxID;                          /*纸币箱编号*/
	ValueCent_t							TotalValueofBankNotebox;                /*纸币箱总金额*/

    ARCount_t							TotalNbofBNAInsert;                     /*接受纸币张数*/
	ValueCent_t							TotalValuebyInsertedBanknote;           /*纸币交易金额*/
	ARCount_t							TotalNbofValidACCCSCRead;               /*储值票有效读写次数*/
	ARCount_t							TotalNbofValidBankCardRead;             /*银行卡有效读写次数*/
	ARCount_t							TotalNbofValidYKTCSCRead;               /*一卡通有效读写次数*/
	ARCount_t							Reserved1;                              /* 预留1 */
        ARCount_t							Reserved2;                              /* 预留2 */
        ARCount_t							Reserved3;                              /* 预留3 */
	MoneyInBankNoteBox_t                MoneyInBankNoteBox_t<>;                 /*纸币信息列表*/
 };

%// ---- 查询充值机审计信息
struct ISMAuditInfo_t{	
	ISMSnapshotInfo_t					ISMSnapshotInfo;					/*查询充值机综合快照结构*/
	ISMTicketFareInfo_t					ISMTicketFareInfo<>;					/*查询充值机票种审计寄存器*/
};


%// ---- 手持式检票机票种信息结构
struct PCATicketFareInfo_t{											
	TicketType_t						TicketType;                             /*票种代码*/
        IssuerCode_t					        IssuerCode;                             /*发卡方代码*/
	ARCount_t						NbofEntries;				/*进站人数*/
	ARCount_t						NbofExit;				/*出站人数*/	
	ValueCent_t						ValueCollected;                         /*交易金额*/
	ARCount_t						NbofBlocked;                            /*被拒黑名单数*/
};

%// ---- 手持式检票机综合快照结构
struct PCASnapshotInfo_t{													
        ARCount_t							TotalNbofSJTRead;                       /*单程票读写次数*/
        ARCount_t							TotalNbofACCCSCRead;                    /*储值票读写次数*/
        ARCount_t							TotalNbofYKTCSCRead;                    /*一卡通读写次数*/
        ARCount_t							TotalNbofMobileRead;                    /*手机卡读写次数*/
        ARCount_t							TotalNbofSJTEntry;                      /*单程票进站次数*/
	ARCount_t							TotalNbofACCCSCEntry;                   /*储值票进站次数*/
        ARCount_t							TotalNbofYKTCSCEntry;                   /*一卡通进站次数*/
        ARCount_t							TotalNbofMobileEntry;                   /*手机卡进站次数*/
        ARCount_t							TotalNbofSJTExit;                       /*单程票出站次数*/
	ARCount_t							TotalNbofACCCSCExit;                    /*储值票出站次数*/
        ARCount_t							TotalNbofYKTCSCExit;                    /*一卡通出站次数*/
        ARCount_t							TotalNbofMobileExit;                    /*手机卡出站次数*/
	ValueCent_t							TotalValueCollectedbySJT;               /*单程票交易金额*/
	ValueCent_t							TotalValueCollectedbyACCCSC;            /*储值票交易金额*/
	ValueCent_t							TotalValueCollectedbyYKTCSC;            /*一卡通交易金额*/
	ValueCent_t							TotalValueCollectedbyMobile;            /*手机卡交易金额*/
        ARCount_t							TotalNbofInvalidSJT;                    /*无效单程票数量*/
	ARCount_t							TotalNbofInvalidACCCSC;                 /*无效储值票数量*/
	ARCount_t							TotalNbofInvalidYKTCSC;                 /*无效一卡通数量*/
	ARCount_t							TotalNbofInvalidMobile;                 /*无效手机卡数量*/
        ARCount_t							NbofACCCSCBlocked;                      /*被锁储值票黑名单数量*/
	ARCount_t							NbofYKTBlocked;                         /*被锁一卡通黑名单数量*/
	ARCount_t							NbofMobileBlocked;                      /*被锁手机卡黑名单数量*/
	ARCount_t							TotalNbofTestEntry;                     /*测试票进站数*/
        ARCount_t							TotalNbofTestExit;                      /*测试票出站数*/
	ValueCent_t							TotalValueTestCollectedbySJT;           /*单程票测试票交易金额*/
	ValueCent_t							TotalValueTestCollectedbyACCCSC;        /*储值票测试票交易金额*/
	ValueCent_t							TotalValueTestCollectedbyYKTCSC;        /*一卡通测试票交易金额*/
	ValueCent_t							TotalValueTestCollectedbyMobile;        /*手机卡测试票交易金额*/
	ARCount_t							Reserved1;                              /* 预留1 */
        ARCount_t							Reserved2;                              /* 预留2 */
        ARCount_t							Reserved3;                              /* 预留3 */
	
};

%// ---- 手持式检票机审计信息
struct PCAAuditInfo_t{	
	PCASnapshotInfo_t					PCASnapshotInfo;					/*手持式检票机综合快照结构*/
	PCATicketFareInfo_t					PCATicketFareInfo<>;					/*手持式检票机票种审计寄存器*/
};

%// ---------------------------------------------------------------------------------------

%// ---------------------------------------------------------------------------------------
%// ---- ACC与SC其他文件定义
%// ---------------------------------------------------------------------------------------

%// 库存点车票库存结构
struct TicketStockPointList_t{	
	TicketCatalogID_t                            TicketCatalogID;							/* 票卡目录编号 */ 
	TicketQuantity_t			     TicketQuantity;							/* 车票数量 */ 
	TicketQuantity_t			     InvalidTicketQuantity;						/* 废票数量 */ 
};

%// 车票出入库列表结构
struct TicketStockInOutDetailList_t{
	string                          BillCode<32>;									/* 单据编号 */	
	AFCTime64_t                     OccurTime;									/* 发生时间 */
        OperatorID_t                    OperatorID;								        /*  操作员  */
        OperatorID_t                    Operator1ID;								        /*  配送员  */
	StationID_t			Station1ID;	                                                                /* 来源/目的车站编号 */
        TicketQuantity_t		AllocateQuantity;								/* 调配数量 */ 
	InOutFlag_t			InOutFlag;									/* 出入库标记 */ 
	InOutType_t			InOutType;									/* 出入库类型 */ 
	TicketCatalogID_t               TicketCatalogID;								/* 票卡目录编号 */ 
	TicketQuantity_t		TicketQuantity;									/* 车票数量 */ 
	U32_t                           TicketValue;                                                                    /* 车票面值*/
	TicketQuantity_t		InvalidTicketQuantity;								/* 废票数量 */ 
};

%// ---- 出入库清单文件结构
struct TicketStockInOutBillFile_t{													
	LineID_t						LineID;		                                /* 线路编号 */
	StationID_t						StationID;	                                /* 车站编号 */
	TicketStockInOutDetailList_t	                        TicketStockInOutDetailList<>;			/* 车票出入库列表结构 */
};

%// ---- 日终库存信息文件结构
struct TicketStockSnapshotFile_t{													
	LineID_t					LineID;							/* 线路编号 */
	StationID_t					StationID;						/* 车站编号 */		
	Date2_t						BusinessDate;						/* 运营日 */
	TicketStockPointList_t				TicketStockPointList<>;					/* 车票库存结构 */
};

%// 车票库存差异列表结构
struct TicketDifferenceStockList_t{												
	TicketCatalogID_t                               TicketCatalogID;							/* 票卡目录编号 */ 
	TicketQuantity_t				TicketQuantity;								/* SC车票数量 */ 
	TicketQuantity_t				InvalidTicketQuantity;							/* SC废票数量 */ 
	TicketQuantity_t				ACCStatTicketQuantity;							/* ACC统计车票数量 */ 
	TicketQuantity_t				ACCStatInvalidTicketQuantity;						/* ACC统计废票数量 */ 
};


%// 结算文件列表结构
struct SettlementFileList_t{														
	string						UDFileName<40>;						/* 交易文件名 */
	RecordsNum_t					UDRecordsNum;						/* 结算交易数 */
	ValueCent_t					TransMoney;						/* 结算交易金额 */
};

%// 结算可疑交易记录列表结构
struct SettlementDoubtfulRecordList_t{		
        DeviceID_t					DeviceID;                                               /* 设备节点编号 */
	SN_t						UDSN;							/* 交易记录流水号 */
	FileDealResult_t				DoubtfulType;						/* 可疑类型 */
};

%// 结算可疑文件列表结构
struct SettlementDoubtfulFileList_t{											
	SettlementFileList_t			       SettlementDoubtfulFileList;				/* 结算可疑文件列表结构 */ 
	SettlementStatus_t			       SettlementStatus;					/* 文件解析代码 */
	SettlementDoubtfulRecordList_t	               SettlementDoubtfulRecordList<>;				/* 结算可疑交易记录列表结构 */
};


%// ---- 交易对账文件结构
struct TransactionAccountFile_t{		
        LineID_t					LineID;					/* 线路编号 */
	StationID_t					StationID;				/* 车站编号 */	
        Date2_t						SettlementDate;				/* 结算日 */
        CardSeries_t					CardSeries;				/* 卡系 */														
	RecordsNum_t					TotalNbOfUDNormal;			/* 结算正常交易记录总数 */
	ValueCent64_t					TotalValueOfNormal;			/* 结算正常交易金额总数 */
	RecordsNum_t					TotalNbOfDoubtful;			/* 结算可疑交易记录总数 */
	ValueCent64_t					TotalValueOfDoubtful;			/* 结算可疑交易金额总数 */
	SettlementFileList_t			        SettlementNormalFileList<>;		/* 结算正常文件列表结构 */ 
	SettlementDoubtfulFileList_t	                SettlementDoubtfulFileList<>;		/* 结算可疑文件列表结构 */ 
};

%// 可疑交易记录列表结构
struct DoubtfulUDList_t{															
	DeviceID_t					DeviceID;                               /* 设备节点编号 */
	SN_t						UDSN;				        /* 交易记录流水号 */  
	Boolean_t					DealResultOfDoubtful;		        /* 处理结果 */  
};

%// 可疑调整文件列表结构
struct DoubtfulAdjustFileList_t{													
	string						UDFileName<40>;							/* 可疑交易文件名 */  
	DoubtfulUDList_t				DoubtfulUDList<>;						/* 可疑交易记录列表结构 */
};

%// ---- 可疑交易调整文件结构
struct TransactionDoubtfulFile_t{											/* 可疑交易调整文件结构 */
       LineID_t						LineID;								/* 线路编号 */
       StationID_t					StationID;				                        /* 车站编号 */	
       Date2_t						SettlementDate;							/* 结算日 */
       CardSeries_t				        CardSeries;							/* 卡系 */			
       RecordsNum_t					TotalNbOfDoubtfulAdjust;					/* 可疑调整交易记录总数 */
       RecordsNum_t					TotalNbOfAccept;						/* 接受交易记录总数 */
       ValueCent64_t					TotalValueOfAccept;						/* 接受交易金额总数 */
       RecordsNum_t					TotalNbOfReject;						/* 拒付交易记录总数 */
       ValueCent64_t					TotalValueOfReject;						/* 拒付交易金额总数 */ 
       DoubtfulAdjustFileList_t		                DoubtfulAdjustFileList<>;					/* 可疑调整文件列表 */ 
};

%// ---- 清分数据文件结构
%//struct TransactionClearFile_t{	
%//     LineID_t					LineID;												/* 线路编号 */
%//     Date2_t						SettlementDate;											/* 结算日 */		
%//     CardSeries_t					CardSeries;											/* 卡系 */												
%//	ValueCent64_t					AddUp;												/* 线路当日累计收益         */
%//	ValueCent64_t					Normal;												/* 线路正常帐收益           */
%//	ValueCent64_t					DoubtfulAdjust;										/* 线路可疑调整帐收益       */
%//	ValueCent64_t					SettlementAdjust;									/* 线路结算调整帐收益       */
%//	ValueCent64_t					NormalChargeOfSell;									/* 售票正常帐服务费         */
%//	ValueCent64_t					DoubtfulAdjustChargeOfSell;							/* 售票可疑调整帐服务费     */
%//	ValueCent64_t					NormalChargeOfAddValue;								/* 充值正常帐服务费         */
%//	ValueCent64_t					DoubtfulAdjustChargeOfAddValue;						/* 充值可疑调整帐服务费     */
%//	ValueCent64_t					NormalChargeOfSurcharge;							/* 更新正常帐服务费         */
%//	ValueCent64_t					DoubtfulAdjustChargeOfSurcharge;					/* 更新可疑调整帐服务费     */
%//	ValueCent64_t					NormalChargeOfRefund;								/* 退票正常帐服务费         */
%//	ValueCent64_t					DoubtfulAdjustChargeOfRefund;						/* 退票可疑调整帐服务费     */
%//	ValueCent64_t					NormalChargeOfReplacement;							/* 换票正常帐服务费         */
%//	ValueCent64_t					DoubtfulAdjustChargeOfReplacement;					/* 换票可疑调整帐服务费     */
%//	ValueCent64_t					NormalChargeOfAdministrationDeal;					/* 行政处理正常帐服务费     */
%//	ValueCent64_t					DoubtfulAdjustChargeOfAdministrationDeal;			/* 行政处理可疑调整帐服务费 */
%//};

%// --------------------------------------------------------------------------------------------------------------

%// -------------------------------------------------------------------------------------------------------------
%// ---------- ES交易文件和编码报告文件
%// -------------------------------------------------------------------------------------------------------------

%// ---- ES票卡公共结构
struct ESTicketComm_t{													
	TicketFamilyType_t					TicketFamilyType;                   /*票卡大类类型*/
	TicketType_t						TicketType;                         /*票种代码*/
	TicketCatalogID_t					TicketCatalogID;                    /*票卡目录*/
	TicketPhyID_t						TicketPhyID;                        /*车票物理卡号*/
	TicketStatus_t						TicketStatus;                       /*票卡状态*/
};

%// ---- 单程票初始化结构
struct ESSJTInit_t{		
	KeyVersion_t						IssueKeyVersion;		    /*发行密钥版本*/				
	TestFlag_t						TestFlag;			    /*测试标记*/
	LanguageFlag_t						LanguageFlag;                       /*语言标记*/ 
	ValueCent_t						TicketValue;			    /*票卡面值（预付值金额）*/
	Date2_t							ValidStartDate;                     /*有效开始日期*/
	Date2_t							ValidEndDate;                       /*有效结束日期*/
	OperatorID_t						OperatorID;                         /*操作员编号*/
	Duration_t                                              Duration;                           /*有效天数*/
};

%// ---- 单程票初始化交易结构
struct ESSJTInit_TX{														
	ESTicketComm_t						ESTicketComm;					/*票卡公共结构*/
	SJTInitComm_t						SJTInitComm;					/*单程票卡初始化公共结构*/
	ESSJTInit_t						ESSJTInit;					/*单程票初始化结构*/
};


%// ---- 储值票初始化结构
struct ESCPUCardInit_t{													
	TestFlag_t						TestFlag;			    	/*测试标记*/
	LanguageFlag_t					LanguageFlag;           	/*语言标记*/ 
	CityCode_t						CityCode;               	/*城市代码*/
	TradeCode_t						TradeCode;			    	/*行业代码*/
	Date2_t							CardInitDate;               /*初始化日期*/
	ValueCent_t						TicketDepositValue;		    /*押金金额*/
	Date2_t							CardValidDate;			    /*卡有效日期*/
	string						    AppSN<20>;			    	/*应用序列号*/
	AppVersion_t					AppVersion;			    	/*应用版本*/
%// Date2_t							AppStartDate;            	/*应用启用日期*/
	Date2_t							AppValidDate;         		/*应用有效日期*/
    AreaTicketFlag_t                AreaTicketFlag;             /*范围标志*/
    BitMap_t                        BitMap1;                    /*线路车站位图*/    
	BitMap_t                        BitMap2;                    /*车站位图*/     
	ValueCent_t						TicketValue;		        /*票卡初始余额/次*/
	OperatorID_t					OperatorID;             	/*操作员编号*/
};

%// ---- 储值票初始化交易结构
struct ESCPUCardInit_TX{														
	ESTicketComm_t						ESTicketComm;						/*票卡公共结构*/
	CPUInitComm_t						CPUInitComm;						/*储值票卡初始化公共结构*/
	ESCPUCardInit_t						ESCPUCardInit;						/*储值票初始化结构*/
};


%// ---- 票卡注销结构
struct ESTicketCancel_t{													
%//	KeyVersion_t						CancelKeyVersion;		    /*注销用密钥版本*/	
	ValueCent_t						TicketValue;			    /*注销时票卡余额/次*/
	OperatorID_t						OperatorID;                         /*操作员编号*/
};

%// ---- 票卡注销交易结构
struct ESTicketCancel_TX{														
	ESTicketComm_t						ESTicketComm;						/*票卡公共结构*/
	ESTicketCancel_t					ESTicketCancel;						/*票卡注销结构*/
};


%// ---- 员工卡信息结构
struct StaffTicketInfo_t{													
	string    						StaffId<8>;						/*员工号*/	
	AllowRightType_t					AllowRightType;					        /*权利控制*/
	MultiRideNumber_t					MultiRideNumber;					/*乘坐次数*/	
	Date2_t							ValidDate;						/*员工票有效期*/
};

%// ---- 储值票个性化交易结构
struct ESCPUCardPersonal_TX{														
	ESTicketComm_t						ESTicketComm;						/*票卡公共结构*/
	CPUInitComm_t						CPUInitComm;						/*储值票卡初始化公共结构*/
	ESCPUCardInit_t						ESCPUCardInit;						/*储值票初始化结构*/
	TicketPassengerComm_t				        TicketPassengerComm;				        /*记名卡乘客结构*/
	StaffTicketInfo_t					StaffTicketInfo;					/*员工卡信息结构*/
};

%// ---- 预付值单程票抵销结构
struct PrePaySJTOffset_t{													
	TestFlag_t						TestFlag;			    /*测试标记*/
	LanguageFlag_t						LanguageFlag;                       /*语言标记*/ 
	TradeCode_t						TradeCode;			    /*行业代码*/
	ValueCent_t						PrePayValue;			    /*预付值金额*/
	Date2_t							ValidStartDate;                     /*有效开始日期*/
	Date2_t							ValidEndDate;                       /*有效结束日期*/
	OperatorID_t						OperatorID;                         /*操作员编号*/
};

%// ---- 预付值单程票抵销交易结构
struct PrePaySJTOffset_TX{														
	ESTicketComm_t						ESTicketComm;			    /*票卡公共结构*/
	SJTInitComm_t						SJTInitComm;			    /*单程票卡初始化公共结构*/
	PrePaySJTOffset_t					PrePaySJTOffset;		    /*预付值单程票抵销结构*/
};


%// ---- 车票编码信息列表结构
struct TicketEncodingList_t{																
	AFCTime64_t					    EncodingTime;			   /* 编码时间  */
	TicketPhyID_t					    TicketPhyID;			   /* 车票序列号 */
	string						    AppSN<20>;				   /*应用序列号*/
};

%// ---- 车票编码报告文件结构
struct TicketEncodingReportFile_t{	
	OperatorID_t					OperatorID;					/* 操作员编号   */
	TaskID_t					TaskID;						/* 任务标识   */
	TaskType_t					TaskType;					/* 任务类型   */
	TicketType_t					TicketType;					/* 票种代码 */
	TicketEncodingList_t			        TicketEncodingList<>;				/* 车票编码信息列表结构 */
};

%// --------------------------------------------------------------------------------------------------------------

%#endif 
%/********************************************** 文件结束 *******************************************************/
