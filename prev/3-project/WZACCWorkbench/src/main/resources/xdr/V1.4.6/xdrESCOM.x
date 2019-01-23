%/*************************************************************************************************************
%
%    温州市域铁路AFC系统线网技术标准
%
%   Title       : xdrESCOM.x
%   @Version     : 1.2.0
%    Author      : 华科峰
%    Date        : 2016/06/20
%   Description : 定义与票卡编码分拣机的报文通信格式
%**************************************************************************************************************/

%/*  修订历时记录:
%**************************************************************************************************************
%*    Date         *    Author           *     Changes
%**************************************************************************************************************
%*   2016/06/20    *    华科峰           *     1.2.0 基于温州市域铁路AFC系统线网技术标准V1.2生成文档
%*   2018/03/16    *    华科峰           *     1.4.4 ESLogin_Ans_t，OperatorLogin_Ans_t，Task_Ans_t结构添加应答码字段
%*   2018/03/16    *    华科峰           *     1.4.4 SVTInitComm_t结构新增InitBatchCode字段
%*   2018/04/22    *    华科峰           *     1.4.5 OperatorLogin_Ans_t结构删除OperatorName字段，田间OperatorID字段;   
%**************************************************************************************************************/

%#ifndef XDRESCOM_H
%#define XDRESCOM_H
%
%#include "xdrBaseType.h"

%// -------------------------------------------------------------------------------------------------------------
%// ---------- ES通讯接口
%// -------------------------------------------------------------------------------------------------------------

%// ---- 设备签到请求报文
struct ESLogin_Req_t{
	DeviceID_t                              DeviceID;				/* 编码机标识 */
	AFCTime64_t                             LoginTime;				/* 签到时间 */
};

%// ---- 设备签到应答报文
struct ESLogin_Ans_t{
        MACK_t                                  MackCode;			/* 应答码 */
	AFCTime64_t                             VerifyTime;			/* 校验时钟 */
	Seconds_t				PeriodOfStatusReport;	        /* 设备状态报告间隔 */
	Seconds_t				PeriodOfGetTask;		/* ES取任务间隔 */
};


%// ---- 设备签退请求报文
struct ESLogout_Req_t{
	DeviceID_t                              DeviceID;			 /* 编码机标识 */
	OperatorID_t                            OperatorID;                      /* 操作员编号 */
	AFCTime64_t                             LogoutTime;			 /* 签退时间 */
};


%// ---- 操作员签到请求报文
struct OperatorLogin_Req_t{
	DeviceID_t                              DeviceID;				/* 编码机标识 */
	OperatorID_t                            OperatorID;                             /* 操作员编号 */
	string					OperatorPassword<32>;                   /* 用户密码 */
	AFCTime64_t                             LoginTime;				/* 签到时间 */
};

%// ---- 操作员签到应答报文
struct OperatorLogin_Ans_t{
        MACK_t                                  MackCode;			        /* 应答码 */
	OperatorID_t                            OperatorID;                             /* 操作员编号 */
	OperatorProperty_t                      OperatorProperty;		        /* 操作员属性 */
};


%// ---- 操作员签退请求报文
struct OperatorLogout_Req_t{
	DeviceID_t                              DeviceID;				/* 编码机标识 */
	OperatorID_t                            OperatorID;                             /* 操作员编号 */
	AFCTime64_t                             LogoutTime;				/* 签退时间 */
};


%// ---- 设备工作任务请求报文
struct Task_Req_t{
	DeviceID_t                              DeviceID;				/* 编码机标识 */
	Date2_t					TaskDate;				/* 请求任务的日期 */
};


%// ---- 任务属性结构
struct TaskAttribute_t{
	TaskType_t							TaskType;			/* 任务类型 */
	TaskID_t							TaskID;				/* 任务标识 */
	TicketQuantity_t						TicketQuantity;			/* 票卡数量 */
};	


%// ---- 单程票初始化任务结构
struct TaskOfSJTInit_t{
	TaskAttribute_t				TaskAttribute;          /* 任务属性结构 */
	ChipType_t				ChipType;               /* 芯片类型 */
	TicketType_t				TicketType;             /* 车票类型 */
	Date2_t					CardInitDate;           /* 票卡初始化日期 */
	InitBatchCode_t				InitBatchCode;          /* 票卡初始化批次 */
	LanguageFlag_t				LanguageFlag;           /* 语言标记 */ 
	TestFlag_t				TestFlag;		/* 测试标记 */
	U32_t					Reserve1;		/* 预留1 */
	U32_t					Reserve2;		/* 预留2 */		
	ValueCent_t				TicketValue;		/* 票卡初始余额/次 */
	FareTier_t                              FareTier;               /* 费率等级代码 */
	Date2_t				        ValidStartDate;         /* 有效开始日期 */
	Date2_t					ValidEndDate;           /* 有效结束日期 */
	Duration_t                              Duration;               /*有效天数*/
};	

%// ---- 储值票初始化公共结构
struct SVTInitComm_t{
	TaskAttribute_t				TaskAttribute;          /* 任务属性结构 */
	ChipType_t				ChipType;               /* 芯片类型 */
	TicketType_t			        TicketType;             /* 车票类型 */
	TicketCatalogID_t			TicketCatalogID;        /* 票卡目录 */
	IssuerCode_t				IssuerCode;             /* 发卡方代码 */
	TradeCode_t				TradeCode;		/* 行业代码 */
	CityCode_t				CityCode;               /*城市代码*/
	Date2_t					CardInitDate;           /*票卡初始化日期*/
	InitBatchCode_t				InitBatchCode;          /* 票卡初始化批次 */
	TicketFamilyType_t			TicketFamilyType;       /*票卡大类类型*/
	ValueCent_t				TicketDepositValue;     /* 押金金额 */
	Date2_t					CardValidDate;		/* 卡有效日期 */
	U32_t					Reserve;		/* 预留 */
	TestFlag_t				TestFlag;		/* 测试标记 */
	AppVersion_t				AppVersion;		/* 应用版本 */
	Date2_t					AppStartDate;           /* 应用有效开始日期 */
	Date2_t					AppValidDate;           /* 应用有效结束日期 */
        AreaTicketFlag_t                        AreaTicketFlag;         /*范围标志*/
        BitMap_t                                BitMap1;                /*线路车站位图*/    
	BitMap_t                                BitMap2;                /*车站位图*/   
	ValueCent_t				TicketValue;		/* 票卡初始余额/次 */
        U32_t					Reserve1;		/* 预留1 */
	U32_t					Reserve2;		/* 预留2 */	
};

%// ---- 储值票初始化任务结构
struct TaskOfSVTInit_t{
        SVTInitComm_t							SVTInitComm;            /* 储值票初始化公共结构 */
	
};	

%// ---- 票卡注销任务结构
struct TaskOfTicketCancel_t{
	TaskAttribute_t				TaskAttribute;          /* 任务属性结构 */
	ChipType_t				ChipType;               /* 芯片类型 */
	TicketType_t				TicketType;             /* 车票类型 */
	TicketCatalogID_t			TicketCatalogID;        /* 票卡目录 */
	KeyVersion_t				KeyVersion;		/* 版本号 */
	Date2_t                                 UseDate;                /* 使用时间 */
	Times_t					UseTimes;		/* 使用次数 */
        U32_t					Reserve1;		/* 预留1 */
	U32_t					Reserve2;		/* 预留2 */	
};	


%// ---- 票卡分拣任务结构
struct TaskOfTicketEncoding_t{
	TaskAttribute_t				TaskAttribute;          /* 任务属性结构 */
	ChipType_t				ChipType;               /* 芯片类型 */
	TicketType_t				TicketType;             /* 车票类型 */
	TicketCatalogID_t			TicketCatalogID;        /* 票卡目录 */
	KeyVersion_t				KeyVersion;		/* 版本号 */
	Times_t					UseTimes;		/* 使用次数 */
        U32_t					Reserve1;		/* 预留1 */
	U32_t					Reserve2;		/* 预留2 */	
	Date2_t					CardInitDate;           /* 票卡初始化日期 */
	InitBatchCode_t			        InitBatchCode;          /* 票卡初始化批次 */
	ValueCent_t			        TicketValue;		/* 分拣票价 */
};


%// ---- 储值票个性化列表结构
struct SVTPersonalList_t{
	string					ApplyNo<32>;			    /* 申请单号 */
	PassengerTypeID_t                       PassengerTypeID;                    /*持卡人类型标识*/
        StaffFlag_t                             PassengerStaffFlag;                 /*持卡人职工标记*/
	UnicodeString_t                         PassengerCNName;		    /*姓名*/
	Gender_t				PassengerCNSex;			    /*性别*/
	IdentificationType_t                    IdentificationType;                 /*证件类型*/	
	string					IdentificationCode<32>;             /*证件号码*/
	string				        StaffId<8>;			    /*员工号*/	
	AllowRightType_t			AllowRightType;			    /*权利控制*/
	MultiRideNumber_t			MultiRideNumber;		    /*乘坐次数*/	
	TicketPhyID_t				TicketPhyID;                        /*车票物理卡号*/
	string			                TicketLogicID<20>;                  /*车票逻辑卡号*/
};

%// ---- 储值票个性化任务结构
struct TaskOfSVTPersonal_t{
        SVTInitComm_t					  SVTInitComm;                  /* 储值票初始化公共结构 */	
	string						  PersonalFileName<28>;	        /* 个性化资料文件 */
	U8_t					          IssueType;	  	        /* 制卡方式：0-顺序制卡，1-关联制卡 */
};

%// ---- 储值票个性化资料文件结构
struct PersonalFile_t{
	TaskID_t					  TaskID;		     /* 任务标识 */
	SVTPersonalList_t				  SVTPersonalList<>;         /* 储值票个性化列表结构 */
	MD5_t						  MD5Value;		     /*MD5签名*/
};

%// ---- 预付值单程票抵销任务结构
struct TaskOfSJTOffset_t{
	TaskAttribute_t				TaskAttribute;          /* 任务属性结构 */
	ChipType_t				ChipType;               /* 芯片类型 */
	TicketType_t				TicketType;             /* 车票类型 */
	TicketCatalogID_t			TicketCatalogID;        /* 票卡目录 */
	Date2_t					CardInitDate;           /* 票卡初始化日期 */
	InitBatchCode_t				InitBatchCode;        /* 票卡初始化批次 */
	KeyVersion_t			        KeyVersion;		/* 版本号 */
	ValueCent_t				TicketValue;	        /* 票卡初始余额/次 */
	Date2_t					ValidStartDate;         /* 有效开始日期 */
	Date2_t					ValidEndDate;           /* 有效结束日期 */
};

%// ---- 取消任务结构
struct TaskOfCancel_t{
	TaskAttribute_t				TaskAttribute;          /* 任务属性结构 */
	TaskID_t				CancelTaskIDList<>;	/* 被取消的任务标识 */	
};

%// ---- 工作任务结构
union Task_t switch (ESTaskType_t ESTaskType) {             
	case EST_SJT_INIT:										/* 单程票初始化 */
	     TaskOfSJTInit_t			TaskOfSJTInit;						

	case EST_SVT_INIT:										/* 储值票初始化 */
             TaskOfSVTInit_t			TaskOfSVTInit;	
									
	case EST_TICKET_CANCEL:									        /* 票卡注销 */
  	     TaskOfTicketCancel_t		TaskOfTicketCancel;		
								
	case EST_TICKET_ENCODING:								        /* 票卡分拣 */
	     TaskOfTicketEncoding_t	        TaskOfTicketEncoding;						

	case EST_TICKET_PERSONAL:									/* 票卡个性化 */
	     TaskOfSVTPersonal_t		TaskOfSVTPersonal;						

	case EST_SVT_OFFSET:										/* 预付值单程票抵销 */
	     TaskOfSJTOffset_t		        TaskOfSJTOffset;								
		   
	case EST_TASK_CANCEL:										/* 任务取消 */
	     TaskOfCancel_t			TaskOfCancel;									
};

%// ---- 设备工作任务应答报文
struct Task_Ans_t{
	MACK_t                                  MackCode;			        /* 应答码 */
	DeviceID_t                              DeviceID;				/* 编码机标识 */
	TaskStatus_t			        TaskStatus;				/* 工作任务状态 */
	RecordsNum_t				TaskNumber;				/* 任务数 */
	Task_t					Task<>;					/* 工作任务 */
};

%// ---- 工作任务报告结构
struct TaskReport_Req_t{
	DeviceID_t                              DeviceID;		              /* 编码机标识 */
	OperatorID_t                            OperatorID;                           /* 操作员编号 */
	TaskAttribute_t				TaskAttribute;                        /* 任务属性结构 */
	TaskChangeFlag_t                        TaskChangeFlag;                       /* 任务变更标记 */
	ChipType_t				ChipType;                             /* 芯片类型 */
	TicketType_t				TicketType;                           /* 车票类型 */
	TicketCatalogID_t			TicketCatalogID;                      /* 票卡目录 */
	TicketQuantity_t			CompleteQuantity;                     /* 完成数量 */
	TicketQuantity_t			ValidTicketQuantity;                  /* 成功完成数量 */
	TicketQuantity_t			InvalidTicketQuantity;		      /* 废票数量 */ 
	AFCTime64_t				TaskStartTime;                        /* 任务开始时间 */
	AFCTime64_t				TaskEndTime;                          /* 任务结束时间 */
};


%// ---- 设备状态报告结构
struct StatusReport_Req_t{
	DeviceID_t                              DeviceID;				/* 编码机标识 */
	OperatorID_t                            OperatorID;                             /* 操作员编号 */
	ESWorkStatus_t				ESWorkStatus;			        /* 设备工作状态 */
	TaskID_t				CurrentTaskID;			        /* 当前任务标识 */
	TaskType_t				CurrentTaskType;		        /* 当前任务类型 */
	TicketQuantity_t			TicketQuantity;			        /* 当前任务数 */
	TicketQuantity_t			CompleteQuantity;                       /* 当前任务完成数量 */
	TicketQuantity_t			ValidTicketQuantity;		        /* 当前任务成功完成数量 */ 
	TicketQuantity_t			InvalidTicketQuantity;		        /* 当前任务废票数量 */ 
	AFCTime64_t				TaskStartDate;                          /* 当前任务开始时间 */
	AFCTime64_t				TaskEndDate;                            /* 当前任务结束时间 */
};


%// ---- MACK应答报文
struct ESMack_Ans_t{
	MACK_t                                  MackCode;							/* 应答码 */
};	
	

%// ---- 请求报文
union ESREQMessage_t switch (ComType_t ComType) {             
	case COM_ES_LOGIN:										 /* (1A01) 设备签到请求 */
	     ESLogin_Req_t			ESLogin_Req;						

	case COM_ES_LOGOUT:										 /* (1A02) 设备签退 */
	     ESLogout_Req_t			ESLogout_Req;	
									
	case COM_OPERATOR_LOGIN:							                 /* (1A03) 操作员签到请求 */
  	     OperatorLogin_Req_t		OperatorLogin_Req;							
	
	case COM_OPERATOR_LOGOUT:									/* (1A04) 操作员签退 */
	     OperatorLogout_Req_t		OperatorLogout_Req;						

	case COM_WORK_TASK:										/* (1A05)设备工作任务请求 */
	     Task_Req_t			        Task_Req;						

	case COM_TASK_REPORT:										/* (1A06) 工作任务报告 */
	     TaskReport_Req_t			TaskReport_Req;						

	case COM_STATUS_REPORT:										/* (1A07) 设备状态报告 */
	     StatusReport_Req_t			StatusReport_Req;					
};


%// ---- 应答报文
union ESANSMessage_t switch (ComType_t ComType) {     
	case COM_ES_LOGIN:										/* (1A01) 设备签到请求 */
             ESLogin_Ans_t			ESLogin_Ans;						
									
	case COM_OPERATOR_LOGIN:									/* (1A03) 操作员签到请求 */
  	     OperatorLogin_Ans_t		OperatorLogin_Ans;		
													
	case COM_WORK_TASK:										/* (1A05)设备工作任务请求 */
	     Task_Ans_t				Task_Ans;										

	default: 											/*  MACK应答报文 */
	     ESMack_Ans_t			Mack_Ans;							
};


%// ---- 请求/应答报文
union ESComMessage_t switch (MessageType_t MessageType) {             
    case MT_REQMESSAGE:											/* 请求报文 */
        ESREQMessage_t				ESREQMessage;							

    case MT_ANSMESSAGE:											/* 应答报文 */
        ESANSMessage_t				ESANSMessage;							
};


%// ---- 包头
struct ESPackHead_t{
	ProtocolVer_t                           ProtocolVer;						/* 报文协议字 */
	ComType_t				ComType;						/* 消息类型码 */
	DeviceID_t				SendDeviceID;						/* 发送方标识码 */
	DeviceID_t				ReceiveDeviceID;					/* 接受方标识码 */
	RouterTag_t				RouterTag;						/* 路由标记 */
	AFCTime64_t				SendTime;						/* 发送时间 */
	SN_t					SessionSN;						/* 会话流水号 */
	MessageType_t				MessageType;						/* 请求应答标志 */
	MACK_t					MACK;							/* 应答码 */
	AlgorithmicType_t			AlgorithmicType;					/* 压缩加密算法 */	
};


%// ---- 消息包结构
struct ESPack_t{
	PackLength_t                            PackLength;                         /* 包长度 */
	ESPackHead_t                            PackHead;                           /* 包头 */
	ESComMessage_t                          PackBody;                           /* 包体 */
	MD5_t                                   MD5Value;                           /* MD5，验证包头和包体 */
};

%// -------------------------------------------------------------------------------------------------------------

%#endif 
%/********************************************** 文件结束 *******************************************************/