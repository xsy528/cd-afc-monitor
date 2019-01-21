%/*************************************************************************************************************
%
%   温州市域铁路AFC系统线网技术标准
%
%   Title       : xdrCOM.x
%   @Version    : 1.2.0
%   @author     : 华科峰
%   @date       : 2016/06/20
%   Description : 定义系统的报文通信格式
%**************************************************************************************************************/

%/*  修订历时记录:
%**************************************************************************************************************
%*    Date         *    Author           *     Changes
%**************************************************************************************************************
%*   2016/06/20    *    华科峰           *     1.2.0 基于温州市域铁路AFC系统线网技术标准V1.2生成文档  
%*   2016/08/10    *    陈锦鲂           *修改CoinBoxInfo_t结构，更改MoneyInCoinBoxs<>为MoneyInCoinBoxs；
%*                                        修改NoteBoxInfo_t结构，更改MoneyInBankNoteBoxs<>为MoneyInBankNoteBoxs；
%*                                        修改CashBoxInfo_t结构，更改MoneyInBoxs<>为MoneyInBoxs；
%*   2017/11/03    *    陈锦鲂           *BillInfoList_t结构体中增加ticketFamilyType、ticketType、TicketStatus、TicketDirClassID;
%*										  调整OperAmount字段类型为U32_t;
%*										  TicketAllocApply_Req_t结构体中增加Operator1ID; 
%*										  TicketAllocOrder_Req_t结构体中源节点和目标节点类型调整为DeviceID_t
%*										  UpdatePassword_Req_t结构体中节点类型调整为DeviceID_t  
%**************************************************************************************************************/

%#ifndef XDRCOM_H
%#define XDRCOM_H
%
%#include "xdrBaseType.h"

%// -------------------------------------------------------------------------------------------------------------
%// ---------- 设备命令类
%// -------------------------------------------------------------------------------------------------------------

%// ---- 应用层Ping请求报文
struct Ping_Req_t{
	DeviceID_t                              DeviceID;				/* 目标节点标识码 */
};


%// ---- 文本消息请求报文(预留)
struct Text_Req_t{
	DeviceID_t                              DeviceID;				/* 目标节点标识码 */
	AFCTime64_t                             ShowTime;				/* 显示时间 */
	UnicodeString_t                         Content;				/* 文本内容 */
};


%// ---- 设备控制命令请求报文
struct SleControlCmd_Req_t{
	DeviceID_t                              DeviceID;				/* 目标节点标识码 */
	ControlCmd_t                            ControlCmd;				/* 设备控制命令 */
};
	

%// ---- 时间强制同步请求报文
struct SyncTime_Req_t{
	DeviceID_t                              DeviceID;				/* 目标节点标识码 */
	AFCTime64_t                             NowTime;				/* 当前时间 */
};
	

%// 设备事件体结构
struct Eventbody_t{
	EventCode_t                             EventCode;				/* 事件代码 */
	EventValue_t                            EventValue;				/* 事件值 */
};

%// ---- 设备事件上传请求报文
struct EventUpload_Req_t{
	DeviceID_t                              DeviceID;				/* 事件源节点标识码 */
	AFCTime64_t                             OccurTime;				/* 发生时间 */
	DeviceType_t                            DeviceType;				/* 设备类型 */
	DeviceStatus_t                          DeviceStatus;			        /* 设备状态 */
	Eventbody_t                             Eventbodys<>;			        /* 设备事件列表 */
};
	

%// ---- 查询下级参数版本请求报文
struct QueryEODVersion_Req_t{
	DeviceID_t                              DeviceID;			        /* 目标节点标识码 */
	VersionQueryType_t			VersionQueryType;		        /* 版本查询类型（0－查询设备本机 1－查询设备读写器 2－查询设备读写器） */
};

%// ---- 查询设备最新交易流水号请求报文
struct QuerySN_Req_t{
	DeviceID_t                              DeviceID;					/* 目标节点标识码 */
};

%// EOD参数大版本信息
struct EODBigVersion_t{
	EODVersionType_t			EODVersionType;				/* 参数版本类型 */
	FileVersionID_t                         BigEODVersion;				/* EOD大版本 */
	AFCTime64_t                             FutureInureTime;			/* EOD生效时间 */
}; 

%// 参数文件版本
struct ParamFileVersionList_t{
	FileHeaderTag_t				FileHeaderTag;				/* 文件类型码 */
	FileVersionID_t                         FileVersionID;				/* 文件版本号 */
}; 

%// EOD参数版本信息
struct EODVersion_t{
	EODBigVersion_t				EODBigVersion;			        /* EOD参数大版本信息 */
	ParamFileVersionList_t			ParamFileVersionList<>;		        /* EOD参数组件版本 */
}; 

%// EOD参数版本列表
struct EODVersionList_t{
	VersionKind_t				VersionKind;				/* 版本性质  0-设备本机 1-读写器1 2-读写器2 */
	EODVersion_t				ACCEODVersion;			        /* EOD参数版本信息 */
}; 

%// ---- 查询下级参数版本应答报文
struct QueryEODVersion_Ans_t{
	MACK_t                                  MackCode;					/* 应答码 */
	EODVersionList_t			EODVersionList<>;			/* EOD参数版本列表 */
};

%// ---- 查询下级文件版本请求报文
struct QueryFileVersion_Req_t{
	DeviceID_t                              DeviceID;			       /* 目标节点标识码 */
	VersionQueryType_t			VersionQueryType;		       /* 版本查询类型（0－查询设备本机 1－查询设备读写器 2－查询设备读写器） */
};


%// 运营参数版本列表
struct ParameterVersionList_t{
	VersionKind_t				VersionKind;	        /* 版本性质  0-设备本机 1-读写器1 2-读写器2*/
	ParamFileVersionList_t			ParamFileVersionList<>;		/* 运营参数组件版本 */
};


%// ---- EOD版本上传请求报文
struct UploadEODVersion_Req_t{
	DeviceID_t                              DeviceID;                   /* 设备节点标识码 */
	AFCTime64_t                             UploadTime;                 /* 上传时间 */
	EODVersionList_t						EODVersionList<>;			/* EOD参数版本列表 */  
};	

%// ---- 运营参数版本上传请求报文
struct UploadParameterVersion_Req_t{
	DeviceID_t                              DeviceID;                   /* 设备节点标识码 */
	AFCTime64_t                             UploadTime;                 /* 上传时间 */
	ParameterVersionList_t			ParameterVersionList<>;		/* 运营参数版本信息 */   
};
	
%// 读写器SAM卡信息
struct ReaderSAMInfo_t{
	SAMID_t                                 YPTSAMID;		    /* 一票通PSAM/ISAM卡号 */
	SAMID_t                                 YKTSAMID;		    /* 一卡通SAM卡号 */
	SAMID_t                                 MobileSAMID;		    /* 移动SAM卡号 */
	SAMID_t                                 SAMID1;                     /* 预留1 */
	SAMID_t                                 SAMID2;                     /* 预留2 */
	SAMID_t                                 SAMID3;                     /* 预留3 */
	SAMID_t                                 SAMID4;                     /* 预留4 */
	SAMID_t                                 SAMID5;                     /* 预留5 */

};	


%// ---- 设备SAM卡信息上传请求报文
struct UploadSAM_Req_t{
	DeviceID_t                              DeviceID;                   /* 设备节点标识码 */
	AFCTime64_t                             UploadTime;                 /* 上传时间 */
	ReaderSAMInfo_t                         Reader1SAMInfo;             /* 读卡器1 SAM卡信息 */
	ReaderSAMInfo_t                         Reader2SAMInfo;             /* 读卡器2 SAM卡信息 */
};


%// ---- 设备最新交易流水号上传请求报文
struct UploadSN_Req_t{
	DeviceID_t                              DeviceID;                   /* 设备节点标识码 */
	AFCTime64_t                             UploadTime;                 /* 上传时间 */
	SN_t                                    YPTUSDN;		    /* 一票通交易流水号 */
	SN_t                                    YKTUSDN;		    /* 一卡通交易流水号 */
	SN_t                                    MobileUSDN;		    /* 手机票交易流水号 */
	SN_t                                    USDN1;		            /* 预留卡1流水号 */
	SN_t                                    USDN2;		            /* 预留卡2流水号 */
};


%// ---- 设置运营结束时间请求报文
struct SetRuntime_Req_t{
	DeviceID_t                              DeviceID;				/* 设备节点标识码 */
	SecondsSinceMidNight_t                  Runtime;                                /* 运营结束时间 */
};


%// ---- 操作员登录、暂停、恢复、退出请求报文	
struct OperatorWork_Req_t{
	DeviceID_t                              DeviceID;                   /* 设备节点标识码 */
	IPAddress_t                             DeviceIP;                   /* 设备IP地址 */
	AFCTime64_t                             OperateTime;                /* 操作时间 */
	OperatorID_t                            OperatorID;                 /* 操作员编号 */
	BOMShiftID_t                            BOMShiftID;                 /* 班次号 */
	EventType_t				EventType;                  /* 事件：0登录、1签退、2临时签退（BOM）、3自动签退、4签退恢复（BOM） */
};	
	

%// ---- 通讯链接申请请求报文	
struct CommConnectApply_Req_t{
	DeviceID_t                              DeviceID;                   /* 设备节点标识码 */
	IPAddress_t                             DeviceIP;                   /* 设备IP地址 */  
};
		
 
%// 车站模式列表
struct ModeList_t{
	StationID_t                             StationID;                      /* 模式发生变化的车站节点编号 */
        AFCTime64_t                             ModeChangeTime;			/* 模式发生变化时间 */
	ModeCode_t                              ModeCode;                       /* 车站当前运营模式代码 */	
};


%// FTP目录列表
struct FTPFileDir_t{
	FileHeaderTag_t                        FileHeaderTag;					/* 文件类型码 */
	IPAddress_t                            FtpServerIPAdress;				/* FTP服务器IP地址 */
%//	TCPPort_t                              FtpServerTCPPort;				/* FTP服务器端口号 */
	string                                 FTPUserID<32>;           			/* FTP访问用户编号 */
	string                                 FTPPassword<32>;           			/* FTP访问用户密码 */
	string                                 FTPFileDirectory<64>;				/* FTP工作目录 */
};

%// ---- 通讯链接申请应答报文	
struct CommConnectApply_Ans_t{
	SPCode_t                               SpCode;                    		/* 服务商编号 */  
	LineID_t                               LineID;                   		/* 线路编号 */
	UnicodeString_t                        LineCNName;          			/* 线路中文名称 */
	StationID_t                            StationID;              			/* 车站编号 */
	UnicodeString_t                        StationCNName;      			/* 车站中文名称 */
	DeviceID_t                             DeviceID;                                /* 设备节点标识码 */
	ModeCode_t                             ModeCode;             			/* 当前车站运营模式 */
	ModeList_t                             ModeList<>;                              /* 全路网车站模式列表 */
	FTPFileDir_t			       FTPFileDir<>;                            /* FTP目录列表*/
};			
	

%// ---- 上传票箱操作数据请求报文	
struct TicketBoxOperate_Req_t{
	DeviceID_t                              DeviceID;						/* 设备节点标识码 */
	ContainerID_t                           ContainerID;						/* 票箱编号 */
	AFCTime64_t                             OperateTime;						/* 操作时间 */
	OperatorID_t                            OperatorID;						/* 操作员编号 */
	BOMShiftID_t                            BOMShiftID;						/* 班次号 */
	OperateFlag_t                           OperateFlag;						/* 操作方向（0装入，1取出）*/
	TicketQuantity_t                        TicketQuantity;						/* 票卡数量 */
};		
	
%// 纸币钱箱分类金额	
struct MoneyInBankNoteBox_t{
	NoteFaceValue_t                         NoteFaceValue;					/* 纸币面值 */
	RMBQuantity_t                           NoteQuantity;				        /* 纸币数量 */
	ValueCent_t                             NoteMoney;					/* 纸币金额 */
};

%// ---- 上传纸币钱箱操作数据请求报文
struct BankNoteBoxOperate_Req_t{
	DeviceID_t                              DeviceID;						/* 设备节点标识码 */
	NoteOperatePos_t			NoteOperatePos;					        /* 操作位置 */
	ContainerID_t				BankNoteBoxID;						/* 纸币钱箱编号 */
	AFCTime64_t                             OperateTime;						/* 操作时间 */
	OperatorID_t                            OperatorID;						/* 操作员编号 */
	OperateFlag_t                           OperateTag;						/* 操作方向 0装入，1取出*/
	ValueCent_t                             TotalMoney;						/* 钱箱总金额 */
	MoneyInBankNoteBox_t                    MoneyInBankNoteBoxs<>;					/* 纸币钱箱分类金额 */
};	
	

%// 硬币钱箱分类金额	
struct MoneyInCoinBox_t{
	CoinFaceValue_t                         CoinFaceValue;					    /* 硬币面值 */
	RMBQuantity_t                           CoinQuantity;					    /* 硬币数量 */
	ValueCent_t                             CoinMoney;					    /* 硬币金额 */
};

%// 钱箱分类金额	
struct MoneyInBox_t{
	FaceValue_t                             FaceValue;					    /* 钱币面值 */
	RMBQuantity_t                           CashQuantity;					    /* 钱币数量 */
	ValueCent_t                             CashMoney;					    /* 钱币金额 */
};

%// 硬币钱箱信息	
struct CoinBoxInfo_t{
	CoinBoxType_t                           CoinBoxType;					    /* 硬币钱箱类型 */
	ContainerID_t                           CoinBoxID;					    /* 硬币钱箱编号 */
	MoneyInCoinBox_t                        MoneyInCoinBoxs;			            /* 硬币钱箱分类金额 */
};

%// 纸币钱箱信息	
struct NoteBoxInfo_t{
	NoteBoxType_t                           NoteBoxType;					    /* 纸币钱箱类型 */
	ContainerID_t                           NoteBoxID;					    /* 纸币钱箱编号 */
	MoneyInBankNoteBox_t                    MoneyInBankNoteBoxs;			            /* 纸币钱箱分类金额 */
};

%// 钱箱信息	
struct CashBoxInfo_t{
	CashBoxType_t                           CashBoxType;					    /* 钱箱类型 */
	ContainerID_t                           CashBoxID;					    /* 钱箱编号 */
	MoneyInBox_t                            MoneyInBoxs;			                    /* 钱箱分类金额 */
};
	

%// ---- 上传硬币钱箱操作数据请求报文	
struct CoinBoxOperate_Req_t{
	DeviceID_t                              DeviceID;					        /* 设备节点标识码 */
	CoinOperatePos_t			CoinOperatePos;					        /* 操作位置 */
	ContainerID_t                           CoinBoxID;						/* 硬币钱箱编号 */
	AFCTime64_t                             OperateTime;						/* 操作时间 */
	OperatorID_t                            OperatorID;						/* 操作员编号 */
	OperateFlag_t                           OperateTag;						/* 操作方向 0装入，1取出*/
	ValueCent_t                             TotalMoney;						/* 钱箱总金额 */
	MoneyInCoinBox_t                        MoneyInCoinBox<>;					/* 硬币钱箱分类金额 */
};		


%// ---- 上传TVM硬币钱箱清空操作数据请求报文	
struct CoinBoxClear_Req_t{
	DeviceID_t                              DeviceID;						/* 设备节点标识码 */
	AFCTime64_t                             OperateTime;						/* 操作时间 */
	OperatorID_t                            OperatorID;						/* 操作员编号 */
        CoinBoxInfo_t                           CoinBoxInfos<>;                                         /* 硬币钱箱信息 */
};	


%// ---- 上传TVM纸币钱箱清空操作数据请求报文	
struct NoteBoxClear_Req_t{
	DeviceID_t                              DeviceID;						/* 设备节点标识码 */
	AFCTime64_t                             OperateTime;						/* 操作时间 */
	OperatorID_t                            OperatorID;						/* 操作员编号 */
        NoteBoxInfo_t                           NoteBoxInfos<>;                                         /* 纸币钱箱信息 */
};	
  

%// ---- 查询设备钱箱数据请求报文
struct CASHBOXQuery_Req_t{
	DeviceID_t                              AimDeviceID;						/* 目标节点标识码 */	
};		

%// ---- 设备钱箱数据上传请求报文	
struct CASHBOXUPLoad_Req_t{
	DeviceID_t                              DeviceID;						/* 设备节点标识码 */
	CashBoxInfo_t                           CashBoxInfo<>;                                          /* 钱箱信息 */
};	

%// ---- FTP申请请求报文
struct FTPApply_Req_t{
	DeviceID_t                              AimDeviceID;						/* 目标节点标识码 */	
};		


%// ---- FTP申请应答报文	
struct FTPApply_Ans_t{
	FTPFileDir_t				 FTPFileDir<>;			                        /* FTP目录列表*/
};	 


%// ---- 文件更新通知请求报文	
struct FileUpdateNotify_Req_t{
	DeviceID_t                             AimDeviceID;						  /* 目标设备节点标识码 */
	DownLoadCode_t                         DownLoadCode;                                              /* 更新类型 0-普通 1-强制 */
	FileHeaderTag_t                        FileHeaderTag<>;				                  /* 文件类型码 */	
};	
  	   

%// ----  当前文件上传通知请求报文     
struct CurFileUploadNotify_Req_t{
	DeviceID_t                             AimDeviceID;								/* 目标设备节点标识码 */	
	UploadFileBits_t                       UploadFileBits;								/* 上传的文件位信息 */	
};
  	  

%// ---- 指定文件上传通知请求报文	 
struct SpecFileUploadNotify_Req_t{
	DeviceID_t                              AimDeviceID;								/* 目标设备节点标识码 */	
	FileHeaderTag_t                         FileType;								/* 文件类型信息 */	
	Date2_t                                 TransactionDate;							/* 交易日期 */	
	SN_t                                    StartUSDN;								/* 开始流水号，如果是交易数据，为索取交易数据记录的开始流水号，否则为开始文件序列号 */
	SN_t                                    EndUSDN;								/* 结束流水号，如果是交易数据，为索取交易数据记录的结束流水号，否则为结束文件序列号 */
};


%// ---- 启动、解除紧急运营模式请求报文	 
struct SendEmergencyMode_Req_t{
	DeviceID_t                              AimDeviceID;								/* 目标设备节点标识码 */	
	ModeCode_t                              EmergencyModeCode;							/* 系统紧急运营模式代码 */	
	Boolean_t                               ModeEnabledTag;								/* 模式标志：0启动，1解除 */	
};	


%// ---- 发送其他运营模式请求报文	 
struct SendOtherMode_Req_t{
	DeviceID_t                              AimDeviceID;								/* 目标设备节点标识码 */	
	ModeCode_t                              ModeCode;								/* 运营模式代码 */	
};	


%// ---- 模式状态上传请求报文	 
struct ModeStatusUpload_Req_t{
	StationID_t                             StationID;              					/* 模式发生变化的车站节点编号 */
	AFCTime64_t                             ModeOccurTime;							/* 模式发生时间 */
	ModeCode_t                              ModeCode;							/* 改变后的运营模式代码 */	
};


%// ---- 车站模式变化车站列表	 
struct StationModeChange_t{
	StationID_t                             StationID;              					/* 模式发生变化的车站节点编号 */
	AFCTime64_t                             ModeOccurTime;							/* 模式发生时间 */
	ModeCode_t                              ModeCode;							/* 改变后的运营模式代码 */	
};


%// ---- 车站模式变更广播请求报文	 
struct ModeChangeBroadcast_Req_t{
	StationModeChange_t						StationModeChanges<>;              			/* 模式发生变化的车站列表 */
};

	 
%// ---- 车站模式查询请求报文 
struct QueryMode_Req_t{
	DeviceID_t                              AimDeviceID;								/* 目标设备节点标识码 */	    
};


%// ---- 票卡调拨票种记录	 
struct TicketAllocList_t{
	TicketCatalogID_t						TicketCatalogID;              				/* 票卡目录编号 */  	
	TicketAllocType_t						TicketAllocType;					/* 票卡调拨类型 */  	
	TicketQuantity_t						TicketAllocQuantity;					/* 票卡调拨数量 */  
	TicketQuantity_t						TicketWasteQuantity;					/* 废票数量 */  	
};


%// ---- 票卡调拨申请请求报文	 
struct TicketAllocApply_Req_t{
	DeviceID_t                             SourceStationID;           			/* 源车站节点编号 */  	
	DeviceID_t                             AimStationID;              			/* 目标车站节点编号 */
	OperatorID_t							Operator1ID;						/* 操作员 */
	string                                  ApplyBillCode<20>;					/* 申请单编号 */          
	AFCTime64_t                             ApplyTime;							/* 申请时间 */
	TicketAllocList_t                       TicketAllocList<>;					/* 申请票卡目录列表 */
};
	 

%// ---- 票卡调拨命令请求报文	 
struct TicketAllocOrder_Req_t{
	DeviceID_t                             SourceStationID;              				/* 源节点编号 */  	
	DeviceID_t                             AimStationID;              				/* 目标节点编号 */   
	string                                  ApplyBillCode<20>;					/* 命令单编号 */          
	AFCTime64_t                             AllocTime;						/* 调拨时间 */
	TicketAllocList_t                       TicketAllocList<>;					/* 调拨票卡目录列表 */
};

%// ---- 要求下级上报票卡库存请求报文	   
struct UploadTicketStockNotify_Req_t{
	DeviceID_t                              AimDeviceID;								/* 目标设备节点标识码 */ 
}; 
	

%// 票卡库存	   
struct TicketStockList_t{  
 	ContainerID_t							ContainerID;              			/* 票箱编号 */  	    
	TicketCatalogID_t						TicketCatalogID;              			/* 票卡目录编号 */  	
	TicketQuantity_t						TicketQuantity;					/* 票卡数量 */  	
	TicketQuantity_t						InvalidTicketQuantity;				/* 废票数量 */  	
};

	
%// ---- 上传票卡库存请求报文   
struct UploadTicketStock_Req_t{
	DeviceID_t                              DeviceID;                   /* 设备节点标识码 */
	AFCTime64_t                             UploadTime;                 /* 上传时间 */
	TicketStockList_t			TicketStockList<>;          /* 票卡库存清单列表 */   
};

%// 单据信息列表(缺票款大类、票卡类型、票卡状态、票卡分类目录 hkf)	   
struct BillInfoList_t{
	U8_t									ticketFamilyType;				/* 票款大类 */
	U8_t									ticketType;						/* 票卡类型 */
	U8_t									TicketStatus;					/* 票卡状态 */
 	ContainerID_t							ContainerID;                 	/* 钱箱、票箱编号 */  	
	U32_t                                  	OperAmount;                  	/* 操作数量 */              
	S32_t                                  	OperMoney;                   	/* 操作金额 */
	U16_t									TicketDirClassID;				/* 票卡分类目录 */
	UnicodeString_t                   		Remark;                     	/* 备注 */
};


struct UploadTicketIncome_Req_t{
	DeviceID_t                              DeviceID;                   /* 设备节点标识码 */
	AFCTime64_t                             UploadTime;                 /* 上传时间 */
        StationID_t                             StationID;                  /* 车站编号 */  	
        string                                  BillCode<20>;		    /* 单据编号 */       
        BillType_t	                        BillType;                   /* 单据类型 */
        BillStatus_t                            BillStatus;                 /* 单据状态*/
        OperatorID_t                            OperatorID;	            /* 操作员编号 */
        AFCTime64_t                             OperateTime;                /* 操作时间 */
	OperatorID_t                            AuditorID;	            /* 审核人员编号 */
        AFCTime64_t                             AuditorTime;                /* 审核时间 */
        DeviceID_t                              SLEID;                      /* 设备编号 是否可以删除 hkf */
        OperatorID_t                            Operator1ID;	            /* BOM售票员和客值操作员编号 */
	string                                  BankCode<32>;               /* 银行编码 */
        UnicodeString_t                         Remark;                     /* 备注 */
	BillInfoList_t			        BillInfoList<>;             /* 单据信息列表 */   
};


%// 差错信息列表	   
struct ErrorInfoList_t{  
        OperatorID_t                            OperatorID;	             /* 操作员编号 */
	AFCTime64_t                             ErrorTime;                   /* 差错时间 */
	ErrorType_t                             ErrorType;                   /* 差错类型 */
        UnicodeString_t                         ErrorReason;                 /* 差错原因 */
        UnicodeString_t                         ErrorExplain;                /* 差错说明 */
        ValueCent_t                             FillingMoneyValue;           /* 补款金额 */
};


struct UploadTicketFillingMoney_Req_t{
	DeviceID_t                              DeviceID;                   /* 设备节点标识码 */
	AFCTime64_t                             UploadTime;                 /* 上传时间 */
        StationID_t                             StationID;                  /* 车站编号 */  	
        string                                  BillCode<20>;		    /* 单据编号 */      
	BillType_t	                        BillType;                   /* 单据类型 */
        BillStatus_t                            BillStatus;                 /* 单据状态*/
        OperatorID_t                            OperatorID;	            /* 操作员编号 */
        AFCTime64_t                             OperateTime;                /* 操作时间 */
	OperatorID_t                            AuditorID;	            /* 审核人员编号 */
        AFCTime64_t                             AuditorTime;                /* 审核时间 */
        ExcuteStatus_t                          ExcuteStatus;               /* 执行状态 */
        FillingMoneyType_t                      FillingMoneyType;           /* 补款类型 */
        ValueCent_t                             TotalFillingMoneyValue;     /* 短款总额 */
        string                                  RelateBillCode<20>;         /* 关联单号 */
        AFCTime64_t                             SettleStartTime;            /* 结算起始时间 */
        AFCTime64_t                             SettleEndTime;              /* 结算结束时间 */
        Date2_t                                 FillingMoneyEndDate;        /* 短款补款截止日期 */
        UnicodeString_t                         Remark;                     /* 备注 */
	ErrorInfoList_t			        ErrorInfoList<>;            /* 差错信息列表 */   
};
 

%// ---- BOM在线充值认证请求报文   
struct BOMAddValueAuth_Req_t{
	DeviceID_t                              AimDeviceID;								/* BOM设备节点标识码 */   
	SAMID_t                                 SAMID;									/* SAM卡标识码 */  
	BOMShiftID_t                            BOMShiftID;								/* BOM班次号 */
	OperatorID_t                            OperatorID;								/* BOM操作员编号 */
	IssuerCode_t				IssuerCode;								/* 发卡方代码 */
	TicketPhyID_t                           TicketPhyID;								/* 用户物理卡编号 */
	TicketAppSerialID_t                     TicketAppSerialID;							/* 票卡应用序列号 */
	TicketType_t				TicketType;								/* 票种代码 */
	SN_t					TicketSN;								/* 充值前卡交易计数器 */
	ValueCent_t                             PreValue;								/* 充值前票卡余额 */
	AFCTime64_t                             AddValueTime;								/* 充值时间 */
	ValueCent_t                             AddValue;								/* 充值金额 */
	SN_t					OnlineTransSN;								/* 电子钱包联机交易序号 */
	KeyVersion_t				KeyVersion;								/* 密钥版本 */
	AlgorithmicType_t			AlgorithmicType;							/* 算法标识 */
	RandomNum_t                             RandomNum;								/* 用户卡随机数 */
	MAC_t                                   MAC1;									/* ISAM卡对用户卡号、用户卡随机数等信息计算得到的MAC，ACC也需要计算，并验证是否一致 */
};
 

%// ---- BOM在线充值认证应答报文   
struct BOMAddValueAuth_Ans_t{
 	DeviceID_t                              AimDeviceID;								/* BOM设备节点标识码 */   
	BOMShiftID_t                            BOMShiftID;								/* BOM班次号 */
	OperatorID_t                            OperatorID;								/* BOM操作员编号 */
	TicketPhyID_t                           TicketPhyID;								/* 用户物理卡编号 */
	TicketAppSerialID_t                     TicketAppSerialID;							/* 票卡应用序列号 */
	AFCTime64_t                             TransTime;								/* 交易时间（主机） */
	MAC_t                                   MAC2;									/* ACC计算所得MAC2 */
};  


%// ---- BOM在线充值确认请求报文   
struct BOMAddValueAffirm_Req_t{
	DeviceID_t                              AimDeviceID;								/* BOM设备节点标识码 */   
	SAMID_t                                 SAMID;									/* SAM卡标识码 */  
	BOMShiftID_t                            BOMShiftID;								/* BOM班次号 */
	OperatorID_t                            OperatorID;								/* BOM操作员编号 */
	IssuerCode_t				IssuerCode;								/* 发卡方代码 */
	TicketPhyID_t                           TicketPhyID;								/* 用户物理卡编号 */
	TicketAppSerialID_t                     TicketAppSerialID;							/* 票卡应用序列号 */
	TicketType_t				TicketType;								/* 票种代码 */
	SN_t					TicketSN;								/* 充值前卡交易计数器 */
	ValueCent_t                             PreValue;								/* 充值前票卡余额 */
	AFCTime64_t                             AddValueTime;								/* 充值时间 */
	ValueCent_t                             AddValue;								/* 充值金额 */
	SN_t					OnlineTransSN;								/* 电子钱包联机交易序号 */
	KeyVersion_t				KeyVersion;								/* 密钥版本 */
	AlgorithmicType_t			AlgorithmicType;							/* 算法标识 */
	RandomNum_t                             RandomNum;								/* 用户卡随机数 */
	MAC_t                                   MAC1;									/* ISAM卡对用户卡号、用户卡随机数等信息计算得到的MAC，ACC也需要计算，并验证是否一致 */
	Boolean_t                               WriteStatus;                                                            /* 写卡状态 0-写卡成功 1-写卡失败*/
	TAC_t                                   TAC;
};


%// 乘客结构	   
struct PassengerComm_t{
	UnicodeString_t                         PassengerName;					/* 乘客姓名 */
	Gender_t                                PassengerSex;					/* 乘客性别 */
	IdentificationType_t                    IdentificationType;				/* 证件类型 */
	string                                  IDCode<32>;					/* 证件编号 */   
	string                                  TelNumber<32>;					/* 联系电话 */ 
	string                                  FaxNumber<32>;					/* 传真电话 */ 
	UnicodeString_t                          Address;					/* 住址 */ 
};


%// ---- 个性化卡申请请求报文	   
struct PersonalCardApply_Req_t{
	DeviceID_t                              DeviceID;						/* 申请BOM设备节点标识码 */   
	BOMShiftID_t                            BOMShiftID;						/* BOM班次号 */
	string                                  ApplyTableCode<20>;					/* 申请单编号:4字节节点编号＋YYYYMMDD＋4位序列号（BCD） */          
	AFCTime64_t                             ApplyTime;						/* 申请时间 */
	OperatorID_t                            OperatorID;						/* BOM操作员编号 */
	PassengerComm_t				PassengerComm;						/* 乘客结构 */
	TicketFamilyType_t                      TicketFamilyType;					/* 票种归类代码 */ 
	TicketType_t                            TicketType;						/* 票种代码 */
	ValueCent_t                             DepositValue;						/* 押金金额 */
};
	  

%// ---- 非即时退款申请请求报文   
struct NonImmediateRefundApply_Req_t{
	DeviceID_t                              DeviceID;						/* 申请BOM设备节点标识码 */   
%//	BOMShiftID_t                            BOMShiftID;						/* BOM班次号 是否需要 */
	string                                  ApplyBillCode<20>;					/* 申请单编号:4字节节点编号＋YYYYMMDD＋4位序列号（BCD） */          
	AFCTime64_t                             ApplyTime;							/* 申请时间 */
	OperatorID_t                            OperatorID;							/* BOM操作员编号 */
	TicketPhyID_t                           TicketPhyID;						/* 用户物理卡编号 */
	string                                  TicketPrintID<20>;					/* 用户印刷卡编号 */
	PassengerComm_t				PassengerComm;						/* 乘客结构 */
	TicketFamilyType_t                       TicketFamilyType;						/* 票种归类代码 */ 
	TicketType_t                            TicketType;							/* 票种代码 */
};
  

%// ---- 非即时退款查询请求报文	   
struct NonImmediateRefundQuery_Req_t{
	DeviceID_t                              DeviceID;						/* 申请BOM设备节点标识码 */   
	AFCTime64_t                             ApplyTime;						/* 操作时间 */
	OperatorID_t                            OperatorID;						/* 操作员编号 */
	string                                  ApplyBillCode<20>;					/* 申请单编号 */          
	TicketPhyID_t                           TicketPhyID;						/* 用户物理卡编号 */
	string                                  TicketPrintID<20>;					/* 用户印刷卡编号 */
};  
 	     

%// ---- 非即时退款信息下发请求报文	   
struct NonImmediateRefundSend_Req_t{
	DeviceID_t                              QueryDeviceID;						/* 查询BOM设备节点标识码 */   
	AFCTime64_t                             QueryTime;						/* 查询时间 */
	DeviceID_t                              DeviceID;						/* 申请BOM设备节点标识码 */   
	AFCTime64_t                             ApplyTime;						/* 申请时间 */
	OperatorID_t                            BOMOperatorID;						/* BOM操作员编号 */
	AFCTime64_t                             ACCConfirmTime;						/* 确认时间 */
	OperatorID_t                            ACCOperatorID;						/* ACC确认操作员编号 */
        TicketACCStatus_t                       TicketACCStatus;	                                /* ACC票卡帐号状态 */
        RefundStatus_t                          RefundStatus;	                                        /* 退款处理状态  0-处理中 1-已处理*/
        Date2_t                                 TicketACCValidDate;					/* ACC票卡有效日期至 */
        DealResult_t                            DealResult;             	                        /* 处理结果 */
	TicketPhyID_t                           TicketPhyID;						/* 用户物理卡编号 */
	string                                  TicketPrintID<20>;					/* 用户印刷卡编号 */
	PassengerComm_t				PassengerComm;						/* 乘客结构 */
	TicketFamilyType_t                      TicketFamilyType;					/* 票种大类 */ 
	TicketType_t                            TicketType;						/* 票种代码 */
	ValueCent_t                             ReaminingValue;						/* 票卡余额 */
	ValueCent_t                             DepositValue;						/* 押金金额 */
	ValueCent_t                             FeeValue;						/* 退款手续费 */
	ValueCent_t                             DepreciateValue;					/* 票卡折旧费 */
	ValueCent_t                             DeserveValue;						/* 应退金额 */
};   
 

%// ---- 票卡挂失请求报文	   
struct TicketLost_Req_t{
	DeviceID_t                              DeviceID;						/* 挂失BOM设备节点标识码 */   
	BOMShiftID_t                            BOMShiftID;						/* BOM班次号 */
	AFCTime64_t                             HangTime;						/* 挂失时间 */
	OperatorID_t                            BOMOperatorID;						/* BOM操作员编号 */         
	TicketPhyID_t                           TicketPhyID;						/* 用户物理卡编号 */
	string                                  TicketPrintID<20>;					/* 用户印刷卡编号 */
	UnicodeString_t                         PassengerName;					        /* 乘客姓名 */     
	Gender_t                                PassengerSex;						/* 乘客性别 */     
	IdentificationType_t                    IdentificationType;					/* 证件类型 */
	string                                  IDCode<32>;						/* 证件编号 */   
};
   

%// ---- 票卡解挂请求报文   
struct TicketDisLost_Req_t{
	DeviceID_t                              DeviceID;						/* 挂失BOM设备节点标识码 */   
	BOMShiftID_t                            BOMShiftID;						/* BOM班次号 */
	AFCTime64_t                             DisLostTime;						/* 解挂时间 */
	OperatorID_t                            BOMOperatorID;						/* BOM操作员编号 */         
	TicketPhyID_t                           TicketPhyID;						/* 用户物理卡编号 */
	string                                  TicketPrintID<20>;					/* 用户印刷卡编号 */
	UnicodeString_t                         PassengerName;					        /* 乘客姓名 */     
	Gender_t                                PassengerSex;						/* 乘客性别 */     
	IdentificationType_t                    IdentificationType;					/* 证件类型 */
	string                                  IDCode<32>;						/* 证件编号 */   
};
   

%// ---- 票卡账户查询请求报文	   
struct CardAccountQuery_Req_t{
	DeviceID_t                              DeviceID;						/* 设备节点标识码 */   
	TicketPhyID_t                           TicketPhyID;						/* 用户物理卡编号 */
	string                                  TicketPrintID<20>;					/* 用户印刷卡编号 */
	UnicodeString_t                         PassengerName;					        /* 乘客姓名 */
	IdentificationType_t                    IdentificationType;					/* 证件类型 */
	string                                  IDCode<32>;						/* 证件编号 */   
};
  

%// ---- 持有票卡账户列表结构	   
struct CardAccounList_t{
	TicketPhyID_t                           TicketPhyID;						/* 用户物理卡编号 */
	string                                TicketPrintID<20>;					/* 用户印刷卡编号 */
	Date2_t                                 TicketSellDate;						/* 票卡发售日期 */
	TicketStatus_t                          TicketStatus;						/* 票卡状态  */	
	PassengerComm_t				PassengerComm;						/* 乘客结构 */
	TicketFamilyType_t                      TicketFamilyType;					/* 票种大类代码 */ 
	TicketType_t                            TicketType;						/* 票种代码 */
	ValueCent_t                             RemainingValue;						/* 票卡剩余金额 对计程票*/
	MultiRideNumber_t                       RemainingTimes;                                         /* 票卡剩余次数 对计次票*/
	Date2_t                                 StartValidDate;						/* 票卡有效开始日期 */
	Date2_t                                 EndValidDate;						/* 票卡有效结束日期 */
};
   

%// ---- 票卡账户信息下发请求报文	   
struct CardAccountSend_Req_t{
	DeviceID_t                              QueryDeviceID;						/* 查询设备节点标识码 */   
	AFCTime64_t                             QueryTime;						/* 查询时间 */
	CardAccounList_t                        CardAccounList<>;              		                /* 持有票卡账户列表结构 */
};

%// ---- 车站报修申请请求报文	   
struct Station_Maintain_Apply_Req_t{
	StationID_t                             StationID;                                              /* 报修车站编号 */    
	AFCTime64_t                             MaintainTime;						/* 报修时间 */
        string                                  MaintainBillCode<16>;                                   /* 报修单号 */	
	DeviceID_t                              DeviceID;						/* 报修设备节点标识码 */   
	OperatorID_t                            MaintainOperatorID;					/* 报修员工编号 */        
	UnicodeString_t                         MaintainOperatorName;				        /* 报修员工姓名 */    
        Faultlevel_t                            Faultlevel;                                             /* 故障等级 */  
        UnicodeString_t                         FaultCode;                                              /* 故障代码 */
        UnicodeString_t                         FaultCase;                                              /* 故障现象 */
};

%// ---- 车站维修结果确认请求报文	   
struct Station_Maintain_Affirm_Req_t{
	StationID_t                             StationID;                                              /* 报修车站编号 */    
	AFCTime64_t                             AffirmTime;						/* 确认时间 */
        string                                  MaintainBillCode<16>;                                   /* 报修单号 */	
	OperatorID_t                            AffirmOperatorID;					/* 确认人员编号 */        
	UnicodeString_t                         AffirmOperatorName;				        /* 确认人员姓名 */    
        MaintainResult_t                        MaintainResult;                                         /* 维修结果 */  
        UnicodeString_t                         Remark;                                                 /* 备注 */
};




%// ---- 操作员密码更新请求报文	   
struct UpdatePassword_Req_t{
	DeviceID_t                             	DeviceID;              		    /* 节点编号 */   
	OperatorID_t                            OperatorID;				    /* 操作员编号 */   
	AFCTime64_t                             PasswordUpdateTime;			    /* 密码更新时间 */
	string                                  OldPassword<16>;			    /* 旧密码 */ 
	string                                  NewPassword<16>;			    /* 新密码 */ 
};


%// ---- 操作员密码更新请求报文	   
struct UpdatePassword_Ans_t{
	StationID_t                             StationID;              		    /* 车站编号 */   
	DealResult_t                            DealResult;                                 /* 更新结果 */ 
        FailReason_t                            FailReason;                                 /* 失败原因 */ 
}; 


%// ---- MACK应答报文
struct Mack_Ans_t{
	MACK_t                                  MackCode;							/* 应答码 */
};	
	

%// ---- 请求报文
union REQMessage_t switch (ComType_t ComType) {             
	case COM_COMM_CONNECT_APPLY:										/* (1001) 通讯链接申请 */
             CommConnectApply_Req_t			CommConnectApply_Req;						

	case COM_PING:												/* (1101) PING请求报文 */
	     Ping_Req_t					Ping_Req;									
	
	case COM_TEXT:												/* (1102) 文本消息 */
  	     Text_Req_t					Text_Req;
								
	case COM_SLE_CONTROL_CMD:										/* (1103) 设备控制命令 */
	     SleControlCmd_Req_t			SleControlCmd_Req;						

	case COM_TIME_SYNC:											/* (1104)强制时钟同步命令 */
	     SyncTime_Req_t			        SyncTime_Req;						

	case COM_EVENT_UPLOAD:											/* (1201) 设备事件上传 */
	     EventUpload_Req_t				EventUpload_Req;						

	case COM_QUERY_EOD_VERSION:										/* (1202) 查询下级参数版本 */
	     QueryEODVersion_Req_t			QueryEODVersion_Req;					

	case COM_QUERY_FILE_VERSION:										/* (1203) 查询下级文件版本 */
	     QueryFileVersion_Req_t			QueryFileVersion_Req;				

	case COM_QUERY_TRANS_SN:										/* (1205) 查询设备交易流水号 */
	     QuerySN_Req_t				QuerySN_Req;							

	case COM_UPLOAD_EOD_VERSION:										/* (1206) EOD版本上传 */
	     UploadEODVersion_Req_t		        UploadEODVersion_Req;					
	
	case COM_UPLOAD_FILE_VERSION:										/* (1207) 文件版本上传 */
	     UploadParameterVersion_Req_t	        UploadParameterVersion_Req;

	case COM_UPLOAD_SAM:											/* (1208) 设备SAM信息上传 */
	     UploadSAM_Req_t				UploadSAM_Req;				
		   
	case COM_UPLOAD_SN:											/* (1210) 设备最新流水号上传 */
	     UploadSN_Req_t				UploadSN_Req;				
	
	case COM_EndTime_SET:											/* (1211)  设置运营结束时间 */
	     SetRuntime_Req_t				SetRuntime_Req;				
        
   	case COM_OPERATOR_WORK:											/* (1301) 操作员登录、暂停、恢复和退出等操作 */
	     OperatorWork_Req_t				OperatorWork_Req;					

	case COM_TICKETBOX_OPERATE:										/* (1303) 票箱操作数据上传 */
	     TicketBoxOperate_Req_t			TicketBoxOperate_Req;				

	case COM_BANKNOTEBOX_OPERATE:										/* (1304) 纸币钱箱操作数据上传 */
	     BankNoteBoxOperate_Req_t			BankNoteBoxOperate_Req;				

	case COM_COINBOX_OPERATE:										/* (1305) 硬币钱箱操作数据上传 */
	     CoinBoxOperate_Req_t			CoinBoxOperate_Req;					

	case COM_COINBOX_CLEAR:											/* (1306) TVM硬币钱箱清空数据上传 */
	     CoinBoxClear_Req_t				CoinBoxClear_Req;		
 
        case COM_NOTEBOX_CLEAR:											/* (1307) TVM纸币钱箱清空数据上传 */
	     NoteBoxClear_Req_t				NoteBoxClear_Req;		
  
        case COM_CASHBOX_QUERY:											/* (1308) 查询设备钱箱数据 */
              CASHBOXQuery_Req_t			CashBoxQuery_Req;	
    
        case COM_CASHBOX_UPLOAD:										/* (1309) 设备钱箱数据上传 */
             CASHBOXUPLoad_Req_t			CashBoxUpload_Req;				

	case COM_FTP_APPLY:											/* (1401) FTP申请 */
	     FTPApply_Req_t				FTPApply_Req;						

	case COM_FILE_UPDATE_NOTIFY:										/* (1402) 文件下载通知 */
	     FileUpdateNotify_Req_t			FileUpdateNotify_Req;				

	case COM_CURFILE_UPLOAD_NOTIFY:										/* (1403) 当前文件上载通知 */
	     CurFileUploadNotify_Req_t			CurFileUploadNotity_Req;				

	case COM_SPECFILE_UPLOAD_NOTIFY:									/* (1404) 指定文件上载通知 */
	     SpecFileUploadNotify_Req_t			SpecFileUploadNotify_Req;			

	case COM_SEND_EMERGENCY_MODE:										/* (1501) 启动/解除紧急运营模式 */
	     SendEmergencyMode_Req_t			SendEmergencyModeReq;				

	case COM_SEND_OTHER_MODE:										/* (1502) 发送其他运营模式命令 */
	     SendOtherMode_Req_t			SendOtherMode_Req;					

	case COM_MODE_STATUS_UPLOAD:										/* (1503) 模式状态上传 */
	     ModeStatusUpload_Req_t			ModeStatusUpload_Req;				

	case COM_MODE_CHANGE_BROADCAST:										/* (1504) 模式变更广播 */
	     ModeChangeBroadcast_Req_t			ModeChangeBroadcast_Req;				

	case COM_MODE_QUERY:											/* (1505) 车站模式查询 */
	     QueryMode_Req_t				QueryMode_Req;						

	case COM_TICKET_ALLOC_APPLY:										/* (1601) 票卡调拨申请 */
	     TicketAllocApply_Req_t			TicketAllocApply_Req;				

	case COM_TICKET_ALLOC_ORDER:										/* (1602) 票卡调拨命令 */
	     TicketAllocOrder_Req_t			TicketAllocOrder_Req;				

	case COM_UPLOAD_TICKET_STOCK_NOTIFY:									/* (1604) 通知下级上传票卡库存信息 */
	     UploadTicketStockNotify_Req_t		UploadTicketStockNotify_Req;		
	
	case COM_UPLOAD_TICKET_STOCK:										/* (1605) 上传票卡库存信息 */
	     UploadTicketStock_Req_t			UploadTicketStock_Req;	
	
	case COM_UPLOAD_TICKET_INCOME:										/* (1606) 上传票款收益信息 */
	     UploadTicketIncome_Req_t			UploadTicketIncome_Req;	    

        case COM_UPLOAD_TICKET_FILLINGMONEY:									/* (1607) 上传短款补款信息 */
	     UploadTicketFillingMoney_Req_t	        UploadTicketFillingMoney_Req;	    

	case COM_BOM_ADDVALUE_AUTH:										/* (1701) BOM在线充值认证 */
	     BOMAddValueAuth_Req_t			BOMAddValueAuth_Req;	
	     
	case COM_BOM_ADDVALUE_AFFIRM:										/* (1702) 充值确认 */
	     BOMAddValueAffirm_Req_t			BOMAddValueAffirm_Req;	

	case COM_PERSONAL_CARD_APPLY:										/* (1703) 个性化卡申请 */
	     PersonalCardApply_Req_t			PersonalCardApply_Req;				

	case COM_NONIMMEDIATE_REFUND_APPLY:									/* (1704) 非即时退款申请 */ 
	     NonImmediateRefundApply_Req_t		NonImmediateRefundApply_Req;			

	case COM_NONIMMEDIATE_REFUND_QUERY:									/* (1705) 非即时退款查询 */
	     NonImmediateRefundQuery_Req_t		NonImmediateRefundQuery_Req;		
	     
	case COM_TICKET_LOST:										        /* (1706) 票卡挂失 */
	     TicketLost_Req_t				TicketLost_Req;						

	case COM_TICKET_DISLOST:										/* (1707) 票卡解挂 */
	     TicketDisLost_Req_t			TicketDisLost_Req;			
	     
	case COM_NONIMMEDIATE_REFUND_SEND:									/* (1708) 非即时退款信息下发 */
	     NonImmediateRefundSend_Req_t		NonImmediateRefundSend_Req;			

	case COM_CARD_ACCOUNT_QUERY:										/* (1709) 票卡账户查询 */
	     CardAccountQuery_Req_t			CardAccountQuery_Req;				

	case COM_CARD_ACCOUNT_SEND:										/* (1710) 票卡账户信息下发 */
	     CardAccountSend_Req_t			CardAccountSend_Req;				

        case COM_STATION_MAINTAIN_APPLY:								        /* (1801) 车站报修申请 */
	     Station_Maintain_Apply_Req_t		Station_Maintain_Apply_Req;				

	case COM_STATION_MAINTAIN_AFFIRM:								        /* (1802) 车站维修结果确认 */
	    Station_Maintain_Affirm_Req_t		Station_Maintain_Affirm_Req;				
	     
	case COM_UPDATE_PASSWORD:										/* (1901) 操作员密码更新 */
	     UpdatePassword_Req_t			UpdatePassword_Req;
	     
};


%// ---- 应答报文
union ANSMessage_t switch (ComType_t ComType) {     
	case COM_COMM_CONNECT_APPLY:										   /* (1001) 通讯链接申请 */
	     CommConnectApply_Ans_t				CommConnectApply_Ans;	
		        
	case COM_FTP_APPLY:											   /* (1401) FTP申请 */
	     FTPApply_Ans_t					FTPApply_Ans;						

	case COM_BOM_ADDVALUE_AUTH:										   /* (1701) BOM在线充值认证 */
	     BOMAddValueAuth_Ans_t				BOMAddValueAuth_Ans;					
        
	case COM_UPDATE_PASSWORD:										   /* (1901) 操作员密码更新 */
	     UpdatePassword_Ans_t			        UpdatePassword_Ans;
        
	default: 												   /*  MACK应答报文 */
		Mack_Ans_t					MackAns;							
};


%// ---- 请求/应答报文
union Message_t switch (MessageType_t MessageType) {             
    case MT_REQMESSAGE:															/* 请求报文 */
        REQMessage_t						REQMessage;							

    case MT_ANSMESSAGE:															/* 应答报文 */
        ANSMessage_t						ANSMessage;							
};


%// ---- 包头
struct PackHead_t{
	ProtocolVer_t                                           ProtocolVer;						/* 报文协议字 */
	ComType_t				                ComType;						/* 消息类型码 */
	DeviceID_t						SendDeviceID;						/* 发送方标识码 */
	DeviceID_t						ReceiveDeviceID;					/* 接受方标识码 */
	RouterTag_t						RouterTag;						/* 路由标记 */
	AFCTime64_t						SendTime;						/* 发送时间 */
	SN_t							SessionSN;						/* 会话流水号 */
	MessageType_t						MessageType;						/* 请求应答标志 */
	MACK_t							MACK;							/* 应答码 */
	AlgorithmicType_t					AlgorithmicType;					/* 压缩加密算法 */	
};


%// ---- 消息包结构
struct Pack_t{
	PackLength_t                            PackLength;                         /* 包长度 */
	PackHead_t                              PackHead;                           /* 包头 */
	Message_t                               PackBody;                           /* 包体 */
	MD5_t                                   MD5Value;                           /* MD5，验证包头和包体 */
};

%// -------------------------------------------------------------------------------------------------------------

%#endif 
%/********************************************** 文件结束 *******************************************************/