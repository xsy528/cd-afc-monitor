%/*************************************************************************************************************
%
%   ����������·AFCϵͳ����������׼
%
%   Title       : xdrCOM.x
%   @Version    : 1.2.0
%   @author     : ���Ʒ�
%   @date       : 2016/06/20
%   Description : ����ϵͳ�ı���ͨ�Ÿ�ʽ
%**************************************************************************************************************/

%/*  �޶���ʱ��¼:
%**************************************************************************************************************
%*    Date         *    Author           *     Changes
%**************************************************************************************************************
%*   2016/06/20    *    ���Ʒ�           *     1.2.0 ��������������·AFCϵͳ����������׼V1.2�����ĵ�  
%*   2016/08/10    *    �½���           *�޸�CoinBoxInfo_t�ṹ������MoneyInCoinBoxs<>ΪMoneyInCoinBoxs��
%*                                        �޸�NoteBoxInfo_t�ṹ������MoneyInBankNoteBoxs<>ΪMoneyInBankNoteBoxs��
%*                                        �޸�CashBoxInfo_t�ṹ������MoneyInBoxs<>ΪMoneyInBoxs��
%*   2017/11/03    *    �½���           *BillInfoList_t�ṹ��������ticketFamilyType��ticketType��TicketStatus��TicketDirClassID;
%*										  ����OperAmount�ֶ�����ΪU32_t;
%*										  TicketAllocApply_Req_t�ṹ��������Operator1ID; 
%*										  TicketAllocOrder_Req_t�ṹ����Դ�ڵ��Ŀ��ڵ����͵���ΪDeviceID_t
%*										  UpdatePassword_Req_t�ṹ���нڵ����͵���ΪDeviceID_t  
%**************************************************************************************************************/

%#ifndef XDRCOM_H
%#define XDRCOM_H
%
%#include "xdrBaseType.h"

%// -------------------------------------------------------------------------------------------------------------
%// ---------- �豸������
%// -------------------------------------------------------------------------------------------------------------

%// ---- Ӧ�ò�Ping������
struct Ping_Req_t{
	DeviceID_t                              DeviceID;				/* Ŀ��ڵ��ʶ�� */
};


%// ---- �ı���Ϣ������(Ԥ��)
struct Text_Req_t{
	DeviceID_t                              DeviceID;				/* Ŀ��ڵ��ʶ�� */
	AFCTime64_t                             ShowTime;				/* ��ʾʱ�� */
	UnicodeString_t                         Content;				/* �ı����� */
};


%// ---- �豸��������������
struct SleControlCmd_Req_t{
	DeviceID_t                              DeviceID;				/* Ŀ��ڵ��ʶ�� */
	ControlCmd_t                            ControlCmd;				/* �豸�������� */
};
	

%// ---- ʱ��ǿ��ͬ��������
struct SyncTime_Req_t{
	DeviceID_t                              DeviceID;				/* Ŀ��ڵ��ʶ�� */
	AFCTime64_t                             NowTime;				/* ��ǰʱ�� */
};
	

%// �豸�¼���ṹ
struct Eventbody_t{
	EventCode_t                             EventCode;				/* �¼����� */
	EventValue_t                            EventValue;				/* �¼�ֵ */
};

%// ---- �豸�¼��ϴ�������
struct EventUpload_Req_t{
	DeviceID_t                              DeviceID;				/* �¼�Դ�ڵ��ʶ�� */
	AFCTime64_t                             OccurTime;				/* ����ʱ�� */
	DeviceType_t                            DeviceType;				/* �豸���� */
	DeviceStatus_t                          DeviceStatus;			        /* �豸״̬ */
	Eventbody_t                             Eventbodys<>;			        /* �豸�¼��б� */
};
	

%// ---- ��ѯ�¼������汾������
struct QueryEODVersion_Req_t{
	DeviceID_t                              DeviceID;			        /* Ŀ��ڵ��ʶ�� */
	VersionQueryType_t			VersionQueryType;		        /* �汾��ѯ���ͣ�0����ѯ�豸���� 1����ѯ�豸��д�� 2����ѯ�豸��д���� */
};

%// ---- ��ѯ�豸���½�����ˮ��������
struct QuerySN_Req_t{
	DeviceID_t                              DeviceID;					/* Ŀ��ڵ��ʶ�� */
};

%// EOD������汾��Ϣ
struct EODBigVersion_t{
	EODVersionType_t			EODVersionType;				/* �����汾���� */
	FileVersionID_t                         BigEODVersion;				/* EOD��汾 */
	AFCTime64_t                             FutureInureTime;			/* EOD��Чʱ�� */
}; 

%// �����ļ��汾
struct ParamFileVersionList_t{
	FileHeaderTag_t				FileHeaderTag;				/* �ļ������� */
	FileVersionID_t                         FileVersionID;				/* �ļ��汾�� */
}; 

%// EOD�����汾��Ϣ
struct EODVersion_t{
	EODBigVersion_t				EODBigVersion;			        /* EOD������汾��Ϣ */
	ParamFileVersionList_t			ParamFileVersionList<>;		        /* EOD��������汾 */
}; 

%// EOD�����汾�б�
struct EODVersionList_t{
	VersionKind_t				VersionKind;				/* �汾����  0-�豸���� 1-��д��1 2-��д��2 */
	EODVersion_t				ACCEODVersion;			        /* EOD�����汾��Ϣ */
}; 

%// ---- ��ѯ�¼������汾Ӧ����
struct QueryEODVersion_Ans_t{
	MACK_t                                  MackCode;					/* Ӧ���� */
	EODVersionList_t			EODVersionList<>;			/* EOD�����汾�б� */
};

%// ---- ��ѯ�¼��ļ��汾������
struct QueryFileVersion_Req_t{
	DeviceID_t                              DeviceID;			       /* Ŀ��ڵ��ʶ�� */
	VersionQueryType_t			VersionQueryType;		       /* �汾��ѯ���ͣ�0����ѯ�豸���� 1����ѯ�豸��д�� 2����ѯ�豸��д���� */
};


%// ��Ӫ�����汾�б�
struct ParameterVersionList_t{
	VersionKind_t				VersionKind;	        /* �汾����  0-�豸���� 1-��д��1 2-��д��2*/
	ParamFileVersionList_t			ParamFileVersionList<>;		/* ��Ӫ��������汾 */
};


%// ---- EOD�汾�ϴ�������
struct UploadEODVersion_Req_t{
	DeviceID_t                              DeviceID;                   /* �豸�ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* �ϴ�ʱ�� */
	EODVersionList_t						EODVersionList<>;			/* EOD�����汾�б� */  
};	

%// ---- ��Ӫ�����汾�ϴ�������
struct UploadParameterVersion_Req_t{
	DeviceID_t                              DeviceID;                   /* �豸�ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* �ϴ�ʱ�� */
	ParameterVersionList_t			ParameterVersionList<>;		/* ��Ӫ�����汾��Ϣ */   
};
	
%// ��д��SAM����Ϣ
struct ReaderSAMInfo_t{
	SAMID_t                                 YPTSAMID;		    /* һƱͨPSAM/ISAM���� */
	SAMID_t                                 YKTSAMID;		    /* һ��ͨSAM���� */
	SAMID_t                                 MobileSAMID;		    /* �ƶ�SAM���� */
	SAMID_t                                 SAMID1;                     /* Ԥ��1 */
	SAMID_t                                 SAMID2;                     /* Ԥ��2 */
	SAMID_t                                 SAMID3;                     /* Ԥ��3 */
	SAMID_t                                 SAMID4;                     /* Ԥ��4 */
	SAMID_t                                 SAMID5;                     /* Ԥ��5 */

};	


%// ---- �豸SAM����Ϣ�ϴ�������
struct UploadSAM_Req_t{
	DeviceID_t                              DeviceID;                   /* �豸�ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* �ϴ�ʱ�� */
	ReaderSAMInfo_t                         Reader1SAMInfo;             /* ������1 SAM����Ϣ */
	ReaderSAMInfo_t                         Reader2SAMInfo;             /* ������2 SAM����Ϣ */
};


%// ---- �豸���½�����ˮ���ϴ�������
struct UploadSN_Req_t{
	DeviceID_t                              DeviceID;                   /* �豸�ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* �ϴ�ʱ�� */
	SN_t                                    YPTUSDN;		    /* һƱͨ������ˮ�� */
	SN_t                                    YKTUSDN;		    /* һ��ͨ������ˮ�� */
	SN_t                                    MobileUSDN;		    /* �ֻ�Ʊ������ˮ�� */
	SN_t                                    USDN1;		            /* Ԥ����1��ˮ�� */
	SN_t                                    USDN2;		            /* Ԥ����2��ˮ�� */
};


%// ---- ������Ӫ����ʱ��������
struct SetRuntime_Req_t{
	DeviceID_t                              DeviceID;				/* �豸�ڵ��ʶ�� */
	SecondsSinceMidNight_t                  Runtime;                                /* ��Ӫ����ʱ�� */
};


%// ---- ����Ա��¼����ͣ���ָ����˳�������	
struct OperatorWork_Req_t{
	DeviceID_t                              DeviceID;                   /* �豸�ڵ��ʶ�� */
	IPAddress_t                             DeviceIP;                   /* �豸IP��ַ */
	AFCTime64_t                             OperateTime;                /* ����ʱ�� */
	OperatorID_t                            OperatorID;                 /* ����Ա��� */
	BOMShiftID_t                            BOMShiftID;                 /* ��κ� */
	EventType_t				EventType;                  /* �¼���0��¼��1ǩ�ˡ�2��ʱǩ�ˣ�BOM����3�Զ�ǩ�ˡ�4ǩ�˻ָ���BOM�� */
};	
	

%// ---- ͨѶ��������������	
struct CommConnectApply_Req_t{
	DeviceID_t                              DeviceID;                   /* �豸�ڵ��ʶ�� */
	IPAddress_t                             DeviceIP;                   /* �豸IP��ַ */  
};
		
 
%// ��վģʽ�б�
struct ModeList_t{
	StationID_t                             StationID;                      /* ģʽ�����仯�ĳ�վ�ڵ��� */
        AFCTime64_t                             ModeChangeTime;			/* ģʽ�����仯ʱ�� */
	ModeCode_t                              ModeCode;                       /* ��վ��ǰ��Ӫģʽ���� */	
};


%// FTPĿ¼�б�
struct FTPFileDir_t{
	FileHeaderTag_t                        FileHeaderTag;					/* �ļ������� */
	IPAddress_t                            FtpServerIPAdress;				/* FTP������IP��ַ */
%//	TCPPort_t                              FtpServerTCPPort;				/* FTP�������˿ں� */
	string                                 FTPUserID<32>;           			/* FTP�����û���� */
	string                                 FTPPassword<32>;           			/* FTP�����û����� */
	string                                 FTPFileDirectory<64>;				/* FTP����Ŀ¼ */
};

%// ---- ͨѶ��������Ӧ����	
struct CommConnectApply_Ans_t{
	SPCode_t                               SpCode;                    		/* �����̱�� */  
	LineID_t                               LineID;                   		/* ��·��� */
	UnicodeString_t                        LineCNName;          			/* ��·�������� */
	StationID_t                            StationID;              			/* ��վ��� */
	UnicodeString_t                        StationCNName;      			/* ��վ�������� */
	DeviceID_t                             DeviceID;                                /* �豸�ڵ��ʶ�� */
	ModeCode_t                             ModeCode;             			/* ��ǰ��վ��Ӫģʽ */
	ModeList_t                             ModeList<>;                              /* ȫ·����վģʽ�б� */
	FTPFileDir_t			       FTPFileDir<>;                            /* FTPĿ¼�б�*/
};			
	

%// ---- �ϴ�Ʊ���������������	
struct TicketBoxOperate_Req_t{
	DeviceID_t                              DeviceID;						/* �豸�ڵ��ʶ�� */
	ContainerID_t                           ContainerID;						/* Ʊ���� */
	AFCTime64_t                             OperateTime;						/* ����ʱ�� */
	OperatorID_t                            OperatorID;						/* ����Ա��� */
	BOMShiftID_t                            BOMShiftID;						/* ��κ� */
	OperateFlag_t                           OperateFlag;						/* ��������0װ�룬1ȡ����*/
	TicketQuantity_t                        TicketQuantity;						/* Ʊ������ */
};		
	
%// ֽ��Ǯ�������	
struct MoneyInBankNoteBox_t{
	NoteFaceValue_t                         NoteFaceValue;					/* ֽ����ֵ */
	RMBQuantity_t                           NoteQuantity;				        /* ֽ������ */
	ValueCent_t                             NoteMoney;					/* ֽ�ҽ�� */
};

%// ---- �ϴ�ֽ��Ǯ���������������
struct BankNoteBoxOperate_Req_t{
	DeviceID_t                              DeviceID;						/* �豸�ڵ��ʶ�� */
	NoteOperatePos_t			NoteOperatePos;					        /* ����λ�� */
	ContainerID_t				BankNoteBoxID;						/* ֽ��Ǯ���� */
	AFCTime64_t                             OperateTime;						/* ����ʱ�� */
	OperatorID_t                            OperatorID;						/* ����Ա��� */
	OperateFlag_t                           OperateTag;						/* �������� 0װ�룬1ȡ��*/
	ValueCent_t                             TotalMoney;						/* Ǯ���ܽ�� */
	MoneyInBankNoteBox_t                    MoneyInBankNoteBoxs<>;					/* ֽ��Ǯ������� */
};	
	

%// Ӳ��Ǯ�������	
struct MoneyInCoinBox_t{
	CoinFaceValue_t                         CoinFaceValue;					    /* Ӳ����ֵ */
	RMBQuantity_t                           CoinQuantity;					    /* Ӳ������ */
	ValueCent_t                             CoinMoney;					    /* Ӳ�ҽ�� */
};

%// Ǯ�������	
struct MoneyInBox_t{
	FaceValue_t                             FaceValue;					    /* Ǯ����ֵ */
	RMBQuantity_t                           CashQuantity;					    /* Ǯ������ */
	ValueCent_t                             CashMoney;					    /* Ǯ�ҽ�� */
};

%// Ӳ��Ǯ����Ϣ	
struct CoinBoxInfo_t{
	CoinBoxType_t                           CoinBoxType;					    /* Ӳ��Ǯ������ */
	ContainerID_t                           CoinBoxID;					    /* Ӳ��Ǯ���� */
	MoneyInCoinBox_t                        MoneyInCoinBoxs;			            /* Ӳ��Ǯ������� */
};

%// ֽ��Ǯ����Ϣ	
struct NoteBoxInfo_t{
	NoteBoxType_t                           NoteBoxType;					    /* ֽ��Ǯ������ */
	ContainerID_t                           NoteBoxID;					    /* ֽ��Ǯ���� */
	MoneyInBankNoteBox_t                    MoneyInBankNoteBoxs;			            /* ֽ��Ǯ������� */
};

%// Ǯ����Ϣ	
struct CashBoxInfo_t{
	CashBoxType_t                           CashBoxType;					    /* Ǯ������ */
	ContainerID_t                           CashBoxID;					    /* Ǯ���� */
	MoneyInBox_t                            MoneyInBoxs;			                    /* Ǯ������� */
};
	

%// ---- �ϴ�Ӳ��Ǯ���������������	
struct CoinBoxOperate_Req_t{
	DeviceID_t                              DeviceID;					        /* �豸�ڵ��ʶ�� */
	CoinOperatePos_t			CoinOperatePos;					        /* ����λ�� */
	ContainerID_t                           CoinBoxID;						/* Ӳ��Ǯ���� */
	AFCTime64_t                             OperateTime;						/* ����ʱ�� */
	OperatorID_t                            OperatorID;						/* ����Ա��� */
	OperateFlag_t                           OperateTag;						/* �������� 0װ�룬1ȡ��*/
	ValueCent_t                             TotalMoney;						/* Ǯ���ܽ�� */
	MoneyInCoinBox_t                        MoneyInCoinBox<>;					/* Ӳ��Ǯ������� */
};		


%// ---- �ϴ�TVMӲ��Ǯ����ղ�������������	
struct CoinBoxClear_Req_t{
	DeviceID_t                              DeviceID;						/* �豸�ڵ��ʶ�� */
	AFCTime64_t                             OperateTime;						/* ����ʱ�� */
	OperatorID_t                            OperatorID;						/* ����Ա��� */
        CoinBoxInfo_t                           CoinBoxInfos<>;                                         /* Ӳ��Ǯ����Ϣ */
};	


%// ---- �ϴ�TVMֽ��Ǯ����ղ�������������	
struct NoteBoxClear_Req_t{
	DeviceID_t                              DeviceID;						/* �豸�ڵ��ʶ�� */
	AFCTime64_t                             OperateTime;						/* ����ʱ�� */
	OperatorID_t                            OperatorID;						/* ����Ա��� */
        NoteBoxInfo_t                           NoteBoxInfos<>;                                         /* ֽ��Ǯ����Ϣ */
};	
  

%// ---- ��ѯ�豸Ǯ������������
struct CASHBOXQuery_Req_t{
	DeviceID_t                              AimDeviceID;						/* Ŀ��ڵ��ʶ�� */	
};		

%// ---- �豸Ǯ�������ϴ�������	
struct CASHBOXUPLoad_Req_t{
	DeviceID_t                              DeviceID;						/* �豸�ڵ��ʶ�� */
	CashBoxInfo_t                           CashBoxInfo<>;                                          /* Ǯ����Ϣ */
};	

%// ---- FTP����������
struct FTPApply_Req_t{
	DeviceID_t                              AimDeviceID;						/* Ŀ��ڵ��ʶ�� */	
};		


%// ---- FTP����Ӧ����	
struct FTPApply_Ans_t{
	FTPFileDir_t				 FTPFileDir<>;			                        /* FTPĿ¼�б�*/
};	 


%// ---- �ļ�����֪ͨ������	
struct FileUpdateNotify_Req_t{
	DeviceID_t                             AimDeviceID;						  /* Ŀ���豸�ڵ��ʶ�� */
	DownLoadCode_t                         DownLoadCode;                                              /* �������� 0-��ͨ 1-ǿ�� */
	FileHeaderTag_t                        FileHeaderTag<>;				                  /* �ļ������� */	
};	
  	   

%// ----  ��ǰ�ļ��ϴ�֪ͨ������     
struct CurFileUploadNotify_Req_t{
	DeviceID_t                             AimDeviceID;								/* Ŀ���豸�ڵ��ʶ�� */	
	UploadFileBits_t                       UploadFileBits;								/* �ϴ����ļ�λ��Ϣ */	
};
  	  

%// ---- ָ���ļ��ϴ�֪ͨ������	 
struct SpecFileUploadNotify_Req_t{
	DeviceID_t                              AimDeviceID;								/* Ŀ���豸�ڵ��ʶ�� */	
	FileHeaderTag_t                         FileType;								/* �ļ�������Ϣ */	
	Date2_t                                 TransactionDate;							/* �������� */	
	SN_t                                    StartUSDN;								/* ��ʼ��ˮ�ţ�����ǽ������ݣ�Ϊ��ȡ�������ݼ�¼�Ŀ�ʼ��ˮ�ţ�����Ϊ��ʼ�ļ����к� */
	SN_t                                    EndUSDN;								/* ������ˮ�ţ�����ǽ������ݣ�Ϊ��ȡ�������ݼ�¼�Ľ�����ˮ�ţ�����Ϊ�����ļ����к� */
};


%// ---- ���������������Ӫģʽ������	 
struct SendEmergencyMode_Req_t{
	DeviceID_t                              AimDeviceID;								/* Ŀ���豸�ڵ��ʶ�� */	
	ModeCode_t                              EmergencyModeCode;							/* ϵͳ������Ӫģʽ���� */	
	Boolean_t                               ModeEnabledTag;								/* ģʽ��־��0������1��� */	
};	


%// ---- ����������Ӫģʽ������	 
struct SendOtherMode_Req_t{
	DeviceID_t                              AimDeviceID;								/* Ŀ���豸�ڵ��ʶ�� */	
	ModeCode_t                              ModeCode;								/* ��Ӫģʽ���� */	
};	


%// ---- ģʽ״̬�ϴ�������	 
struct ModeStatusUpload_Req_t{
	StationID_t                             StationID;              					/* ģʽ�����仯�ĳ�վ�ڵ��� */
	AFCTime64_t                             ModeOccurTime;							/* ģʽ����ʱ�� */
	ModeCode_t                              ModeCode;							/* �ı�����Ӫģʽ���� */	
};


%// ---- ��վģʽ�仯��վ�б�	 
struct StationModeChange_t{
	StationID_t                             StationID;              					/* ģʽ�����仯�ĳ�վ�ڵ��� */
	AFCTime64_t                             ModeOccurTime;							/* ģʽ����ʱ�� */
	ModeCode_t                              ModeCode;							/* �ı�����Ӫģʽ���� */	
};


%// ---- ��վģʽ����㲥������	 
struct ModeChangeBroadcast_Req_t{
	StationModeChange_t						StationModeChanges<>;              			/* ģʽ�����仯�ĳ�վ�б� */
};

	 
%// ---- ��վģʽ��ѯ������ 
struct QueryMode_Req_t{
	DeviceID_t                              AimDeviceID;								/* Ŀ���豸�ڵ��ʶ�� */	    
};


%// ---- Ʊ������Ʊ�ּ�¼	 
struct TicketAllocList_t{
	TicketCatalogID_t						TicketCatalogID;              				/* Ʊ��Ŀ¼��� */  	
	TicketAllocType_t						TicketAllocType;					/* Ʊ���������� */  	
	TicketQuantity_t						TicketAllocQuantity;					/* Ʊ���������� */  
	TicketQuantity_t						TicketWasteQuantity;					/* ��Ʊ���� */  	
};


%// ---- Ʊ����������������	 
struct TicketAllocApply_Req_t{
	DeviceID_t                             SourceStationID;           			/* Դ��վ�ڵ��� */  	
	DeviceID_t                             AimStationID;              			/* Ŀ�공վ�ڵ��� */
	OperatorID_t							Operator1ID;						/* ����Ա */
	string                                  ApplyBillCode<20>;					/* ���뵥��� */          
	AFCTime64_t                             ApplyTime;							/* ����ʱ�� */
	TicketAllocList_t                       TicketAllocList<>;					/* ����Ʊ��Ŀ¼�б� */
};
	 

%// ---- Ʊ����������������	 
struct TicketAllocOrder_Req_t{
	DeviceID_t                             SourceStationID;              				/* Դ�ڵ��� */  	
	DeviceID_t                             AimStationID;              				/* Ŀ��ڵ��� */   
	string                                  ApplyBillCode<20>;					/* ������ */          
	AFCTime64_t                             AllocTime;						/* ����ʱ�� */
	TicketAllocList_t                       TicketAllocList<>;					/* ����Ʊ��Ŀ¼�б� */
};

%// ---- Ҫ���¼��ϱ�Ʊ�����������	   
struct UploadTicketStockNotify_Req_t{
	DeviceID_t                              AimDeviceID;								/* Ŀ���豸�ڵ��ʶ�� */ 
}; 
	

%// Ʊ�����	   
struct TicketStockList_t{  
 	ContainerID_t							ContainerID;              			/* Ʊ���� */  	    
	TicketCatalogID_t						TicketCatalogID;              			/* Ʊ��Ŀ¼��� */  	
	TicketQuantity_t						TicketQuantity;					/* Ʊ������ */  	
	TicketQuantity_t						InvalidTicketQuantity;				/* ��Ʊ���� */  	
};

	
%// ---- �ϴ�Ʊ�����������   
struct UploadTicketStock_Req_t{
	DeviceID_t                              DeviceID;                   /* �豸�ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* �ϴ�ʱ�� */
	TicketStockList_t			TicketStockList<>;          /* Ʊ������嵥�б� */   
};

%// ������Ϣ�б�(ȱƱ����ࡢƱ�����͡�Ʊ��״̬��Ʊ������Ŀ¼ hkf)	   
struct BillInfoList_t{
	U8_t									ticketFamilyType;				/* Ʊ����� */
	U8_t									ticketType;						/* Ʊ������ */
	U8_t									TicketStatus;					/* Ʊ��״̬ */
 	ContainerID_t							ContainerID;                 	/* Ǯ�䡢Ʊ���� */  	
	U32_t                                  	OperAmount;                  	/* �������� */              
	S32_t                                  	OperMoney;                   	/* ������� */
	U16_t									TicketDirClassID;				/* Ʊ������Ŀ¼ */
	UnicodeString_t                   		Remark;                     	/* ��ע */
};


struct UploadTicketIncome_Req_t{
	DeviceID_t                              DeviceID;                   /* �豸�ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* �ϴ�ʱ�� */
        StationID_t                             StationID;                  /* ��վ��� */  	
        string                                  BillCode<20>;		    /* ���ݱ�� */       
        BillType_t	                        BillType;                   /* �������� */
        BillStatus_t                            BillStatus;                 /* ����״̬*/
        OperatorID_t                            OperatorID;	            /* ����Ա��� */
        AFCTime64_t                             OperateTime;                /* ����ʱ�� */
	OperatorID_t                            AuditorID;	            /* �����Ա��� */
        AFCTime64_t                             AuditorTime;                /* ���ʱ�� */
        DeviceID_t                              SLEID;                      /* �豸��� �Ƿ����ɾ�� hkf */
        OperatorID_t                            Operator1ID;	            /* BOM��ƱԱ�Ϳ�ֵ����Ա��� */
	string                                  BankCode<32>;               /* ���б��� */
        UnicodeString_t                         Remark;                     /* ��ע */
	BillInfoList_t			        BillInfoList<>;             /* ������Ϣ�б� */   
};


%// �����Ϣ�б�	   
struct ErrorInfoList_t{  
        OperatorID_t                            OperatorID;	             /* ����Ա��� */
	AFCTime64_t                             ErrorTime;                   /* ���ʱ�� */
	ErrorType_t                             ErrorType;                   /* ������� */
        UnicodeString_t                         ErrorReason;                 /* ���ԭ�� */
        UnicodeString_t                         ErrorExplain;                /* ���˵�� */
        ValueCent_t                             FillingMoneyValue;           /* ������ */
};


struct UploadTicketFillingMoney_Req_t{
	DeviceID_t                              DeviceID;                   /* �豸�ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* �ϴ�ʱ�� */
        StationID_t                             StationID;                  /* ��վ��� */  	
        string                                  BillCode<20>;		    /* ���ݱ�� */      
	BillType_t	                        BillType;                   /* �������� */
        BillStatus_t                            BillStatus;                 /* ����״̬*/
        OperatorID_t                            OperatorID;	            /* ����Ա��� */
        AFCTime64_t                             OperateTime;                /* ����ʱ�� */
	OperatorID_t                            AuditorID;	            /* �����Ա��� */
        AFCTime64_t                             AuditorTime;                /* ���ʱ�� */
        ExcuteStatus_t                          ExcuteStatus;               /* ִ��״̬ */
        FillingMoneyType_t                      FillingMoneyType;           /* �������� */
        ValueCent_t                             TotalFillingMoneyValue;     /* �̿��ܶ� */
        string                                  RelateBillCode<20>;         /* �������� */
        AFCTime64_t                             SettleStartTime;            /* ������ʼʱ�� */
        AFCTime64_t                             SettleEndTime;              /* �������ʱ�� */
        Date2_t                                 FillingMoneyEndDate;        /* �̿���ֹ���� */
        UnicodeString_t                         Remark;                     /* ��ע */
	ErrorInfoList_t			        ErrorInfoList<>;            /* �����Ϣ�б� */   
};
 

%// ---- BOM���߳�ֵ��֤������   
struct BOMAddValueAuth_Req_t{
	DeviceID_t                              AimDeviceID;								/* BOM�豸�ڵ��ʶ�� */   
	SAMID_t                                 SAMID;									/* SAM����ʶ�� */  
	BOMShiftID_t                            BOMShiftID;								/* BOM��κ� */
	OperatorID_t                            OperatorID;								/* BOM����Ա��� */
	IssuerCode_t				IssuerCode;								/* ���������� */
	TicketPhyID_t                           TicketPhyID;								/* �û�������� */
	TicketAppSerialID_t                     TicketAppSerialID;							/* Ʊ��Ӧ�����к� */
	TicketType_t				TicketType;								/* Ʊ�ִ��� */
	SN_t					TicketSN;								/* ��ֵǰ�����׼����� */
	ValueCent_t                             PreValue;								/* ��ֵǰƱ����� */
	AFCTime64_t                             AddValueTime;								/* ��ֵʱ�� */
	ValueCent_t                             AddValue;								/* ��ֵ��� */
	SN_t					OnlineTransSN;								/* ����Ǯ������������� */
	KeyVersion_t				KeyVersion;								/* ��Կ�汾 */
	AlgorithmicType_t			AlgorithmicType;							/* �㷨��ʶ */
	RandomNum_t                             RandomNum;								/* �û�������� */
	MAC_t                                   MAC1;									/* ISAM�����û����š��û������������Ϣ����õ���MAC��ACCҲ��Ҫ���㣬����֤�Ƿ�һ�� */
};
 

%// ---- BOM���߳�ֵ��֤Ӧ����   
struct BOMAddValueAuth_Ans_t{
 	DeviceID_t                              AimDeviceID;								/* BOM�豸�ڵ��ʶ�� */   
	BOMShiftID_t                            BOMShiftID;								/* BOM��κ� */
	OperatorID_t                            OperatorID;								/* BOM����Ա��� */
	TicketPhyID_t                           TicketPhyID;								/* �û�������� */
	TicketAppSerialID_t                     TicketAppSerialID;							/* Ʊ��Ӧ�����к� */
	AFCTime64_t                             TransTime;								/* ����ʱ�䣨������ */
	MAC_t                                   MAC2;									/* ACC��������MAC2 */
};  


%// ---- BOM���߳�ֵȷ��������   
struct BOMAddValueAffirm_Req_t{
	DeviceID_t                              AimDeviceID;								/* BOM�豸�ڵ��ʶ�� */   
	SAMID_t                                 SAMID;									/* SAM����ʶ�� */  
	BOMShiftID_t                            BOMShiftID;								/* BOM��κ� */
	OperatorID_t                            OperatorID;								/* BOM����Ա��� */
	IssuerCode_t				IssuerCode;								/* ���������� */
	TicketPhyID_t                           TicketPhyID;								/* �û�������� */
	TicketAppSerialID_t                     TicketAppSerialID;							/* Ʊ��Ӧ�����к� */
	TicketType_t				TicketType;								/* Ʊ�ִ��� */
	SN_t					TicketSN;								/* ��ֵǰ�����׼����� */
	ValueCent_t                             PreValue;								/* ��ֵǰƱ����� */
	AFCTime64_t                             AddValueTime;								/* ��ֵʱ�� */
	ValueCent_t                             AddValue;								/* ��ֵ��� */
	SN_t					OnlineTransSN;								/* ����Ǯ������������� */
	KeyVersion_t				KeyVersion;								/* ��Կ�汾 */
	AlgorithmicType_t			AlgorithmicType;							/* �㷨��ʶ */
	RandomNum_t                             RandomNum;								/* �û�������� */
	MAC_t                                   MAC1;									/* ISAM�����û����š��û������������Ϣ����õ���MAC��ACCҲ��Ҫ���㣬����֤�Ƿ�һ�� */
	Boolean_t                               WriteStatus;                                                            /* д��״̬ 0-д���ɹ� 1-д��ʧ��*/
	TAC_t                                   TAC;
};


%// �˿ͽṹ	   
struct PassengerComm_t{
	UnicodeString_t                         PassengerName;					/* �˿����� */
	Gender_t                                PassengerSex;					/* �˿��Ա� */
	IdentificationType_t                    IdentificationType;				/* ֤������ */
	string                                  IDCode<32>;					/* ֤����� */   
	string                                  TelNumber<32>;					/* ��ϵ�绰 */ 
	string                                  FaxNumber<32>;					/* ����绰 */ 
	UnicodeString_t                          Address;					/* סַ */ 
};


%// ---- ���Ի�������������	   
struct PersonalCardApply_Req_t{
	DeviceID_t                              DeviceID;						/* ����BOM�豸�ڵ��ʶ�� */   
	BOMShiftID_t                            BOMShiftID;						/* BOM��κ� */
	string                                  ApplyTableCode<20>;					/* ���뵥���:4�ֽڽڵ��ţ�YYYYMMDD��4λ���кţ�BCD�� */          
	AFCTime64_t                             ApplyTime;						/* ����ʱ�� */
	OperatorID_t                            OperatorID;						/* BOM����Ա��� */
	PassengerComm_t				PassengerComm;						/* �˿ͽṹ */
	TicketFamilyType_t                      TicketFamilyType;					/* Ʊ�ֹ������ */ 
	TicketType_t                            TicketType;						/* Ʊ�ִ��� */
	ValueCent_t                             DepositValue;						/* Ѻ���� */
};
	  

%// ---- �Ǽ�ʱ�˿�����������   
struct NonImmediateRefundApply_Req_t{
	DeviceID_t                              DeviceID;						/* ����BOM�豸�ڵ��ʶ�� */   
%//	BOMShiftID_t                            BOMShiftID;						/* BOM��κ� �Ƿ���Ҫ */
	string                                  ApplyBillCode<20>;					/* ���뵥���:4�ֽڽڵ��ţ�YYYYMMDD��4λ���кţ�BCD�� */          
	AFCTime64_t                             ApplyTime;							/* ����ʱ�� */
	OperatorID_t                            OperatorID;							/* BOM����Ա��� */
	TicketPhyID_t                           TicketPhyID;						/* �û�������� */
	string                                  TicketPrintID<20>;					/* �û�ӡˢ����� */
	PassengerComm_t				PassengerComm;						/* �˿ͽṹ */
	TicketFamilyType_t                       TicketFamilyType;						/* Ʊ�ֹ������ */ 
	TicketType_t                            TicketType;							/* Ʊ�ִ��� */
};
  

%// ---- �Ǽ�ʱ�˿��ѯ������	   
struct NonImmediateRefundQuery_Req_t{
	DeviceID_t                              DeviceID;						/* ����BOM�豸�ڵ��ʶ�� */   
	AFCTime64_t                             ApplyTime;						/* ����ʱ�� */
	OperatorID_t                            OperatorID;						/* ����Ա��� */
	string                                  ApplyBillCode<20>;					/* ���뵥��� */          
	TicketPhyID_t                           TicketPhyID;						/* �û�������� */
	string                                  TicketPrintID<20>;					/* �û�ӡˢ����� */
};  
 	     

%// ---- �Ǽ�ʱ�˿���Ϣ�·�������	   
struct NonImmediateRefundSend_Req_t{
	DeviceID_t                              QueryDeviceID;						/* ��ѯBOM�豸�ڵ��ʶ�� */   
	AFCTime64_t                             QueryTime;						/* ��ѯʱ�� */
	DeviceID_t                              DeviceID;						/* ����BOM�豸�ڵ��ʶ�� */   
	AFCTime64_t                             ApplyTime;						/* ����ʱ�� */
	OperatorID_t                            BOMOperatorID;						/* BOM����Ա��� */
	AFCTime64_t                             ACCConfirmTime;						/* ȷ��ʱ�� */
	OperatorID_t                            ACCOperatorID;						/* ACCȷ�ϲ���Ա��� */
        TicketACCStatus_t                       TicketACCStatus;	                                /* ACCƱ���ʺ�״̬ */
        RefundStatus_t                          RefundStatus;	                                        /* �˿��״̬  0-������ 1-�Ѵ���*/
        Date2_t                                 TicketACCValidDate;					/* ACCƱ����Ч������ */
        DealResult_t                            DealResult;             	                        /* ������ */
	TicketPhyID_t                           TicketPhyID;						/* �û�������� */
	string                                  TicketPrintID<20>;					/* �û�ӡˢ����� */
	PassengerComm_t				PassengerComm;						/* �˿ͽṹ */
	TicketFamilyType_t                      TicketFamilyType;					/* Ʊ�ִ��� */ 
	TicketType_t                            TicketType;						/* Ʊ�ִ��� */
	ValueCent_t                             ReaminingValue;						/* Ʊ����� */
	ValueCent_t                             DepositValue;						/* Ѻ���� */
	ValueCent_t                             FeeValue;						/* �˿������� */
	ValueCent_t                             DepreciateValue;					/* Ʊ���۾ɷ� */
	ValueCent_t                             DeserveValue;						/* Ӧ�˽�� */
};   
 

%// ---- Ʊ����ʧ������	   
struct TicketLost_Req_t{
	DeviceID_t                              DeviceID;						/* ��ʧBOM�豸�ڵ��ʶ�� */   
	BOMShiftID_t                            BOMShiftID;						/* BOM��κ� */
	AFCTime64_t                             HangTime;						/* ��ʧʱ�� */
	OperatorID_t                            BOMOperatorID;						/* BOM����Ա��� */         
	TicketPhyID_t                           TicketPhyID;						/* �û�������� */
	string                                  TicketPrintID<20>;					/* �û�ӡˢ����� */
	UnicodeString_t                         PassengerName;					        /* �˿����� */     
	Gender_t                                PassengerSex;						/* �˿��Ա� */     
	IdentificationType_t                    IdentificationType;					/* ֤������ */
	string                                  IDCode<32>;						/* ֤����� */   
};
   

%// ---- Ʊ�����������   
struct TicketDisLost_Req_t{
	DeviceID_t                              DeviceID;						/* ��ʧBOM�豸�ڵ��ʶ�� */   
	BOMShiftID_t                            BOMShiftID;						/* BOM��κ� */
	AFCTime64_t                             DisLostTime;						/* ���ʱ�� */
	OperatorID_t                            BOMOperatorID;						/* BOM����Ա��� */         
	TicketPhyID_t                           TicketPhyID;						/* �û�������� */
	string                                  TicketPrintID<20>;					/* �û�ӡˢ����� */
	UnicodeString_t                         PassengerName;					        /* �˿����� */     
	Gender_t                                PassengerSex;						/* �˿��Ա� */     
	IdentificationType_t                    IdentificationType;					/* ֤������ */
	string                                  IDCode<32>;						/* ֤����� */   
};
   

%// ---- Ʊ���˻���ѯ������	   
struct CardAccountQuery_Req_t{
	DeviceID_t                              DeviceID;						/* �豸�ڵ��ʶ�� */   
	TicketPhyID_t                           TicketPhyID;						/* �û�������� */
	string                                  TicketPrintID<20>;					/* �û�ӡˢ����� */
	UnicodeString_t                         PassengerName;					        /* �˿����� */
	IdentificationType_t                    IdentificationType;					/* ֤������ */
	string                                  IDCode<32>;						/* ֤����� */   
};
  

%// ---- ����Ʊ���˻��б�ṹ	   
struct CardAccounList_t{
	TicketPhyID_t                           TicketPhyID;						/* �û�������� */
	string                                TicketPrintID<20>;					/* �û�ӡˢ����� */
	Date2_t                                 TicketSellDate;						/* Ʊ���������� */
	TicketStatus_t                          TicketStatus;						/* Ʊ��״̬  */	
	PassengerComm_t				PassengerComm;						/* �˿ͽṹ */
	TicketFamilyType_t                      TicketFamilyType;					/* Ʊ�ִ������ */ 
	TicketType_t                            TicketType;						/* Ʊ�ִ��� */
	ValueCent_t                             RemainingValue;						/* Ʊ��ʣ���� �ԼƳ�Ʊ*/
	MultiRideNumber_t                       RemainingTimes;                                         /* Ʊ��ʣ����� �Լƴ�Ʊ*/
	Date2_t                                 StartValidDate;						/* Ʊ����Ч��ʼ���� */
	Date2_t                                 EndValidDate;						/* Ʊ����Ч�������� */
};
   

%// ---- Ʊ���˻���Ϣ�·�������	   
struct CardAccountSend_Req_t{
	DeviceID_t                              QueryDeviceID;						/* ��ѯ�豸�ڵ��ʶ�� */   
	AFCTime64_t                             QueryTime;						/* ��ѯʱ�� */
	CardAccounList_t                        CardAccounList<>;              		                /* ����Ʊ���˻��б�ṹ */
};

%// ---- ��վ��������������	   
struct Station_Maintain_Apply_Req_t{
	StationID_t                             StationID;                                              /* ���޳�վ��� */    
	AFCTime64_t                             MaintainTime;						/* ����ʱ�� */
        string                                  MaintainBillCode<16>;                                   /* ���޵��� */	
	DeviceID_t                              DeviceID;						/* �����豸�ڵ��ʶ�� */   
	OperatorID_t                            MaintainOperatorID;					/* ����Ա����� */        
	UnicodeString_t                         MaintainOperatorName;				        /* ����Ա������ */    
        Faultlevel_t                            Faultlevel;                                             /* ���ϵȼ� */  
        UnicodeString_t                         FaultCode;                                              /* ���ϴ��� */
        UnicodeString_t                         FaultCase;                                              /* �������� */
};

%// ---- ��վά�޽��ȷ��������	   
struct Station_Maintain_Affirm_Req_t{
	StationID_t                             StationID;                                              /* ���޳�վ��� */    
	AFCTime64_t                             AffirmTime;						/* ȷ��ʱ�� */
        string                                  MaintainBillCode<16>;                                   /* ���޵��� */	
	OperatorID_t                            AffirmOperatorID;					/* ȷ����Ա��� */        
	UnicodeString_t                         AffirmOperatorName;				        /* ȷ����Ա���� */    
        MaintainResult_t                        MaintainResult;                                         /* ά�޽�� */  
        UnicodeString_t                         Remark;                                                 /* ��ע */
};




%// ---- ����Ա�������������	   
struct UpdatePassword_Req_t{
	DeviceID_t                             	DeviceID;              		    /* �ڵ��� */   
	OperatorID_t                            OperatorID;				    /* ����Ա��� */   
	AFCTime64_t                             PasswordUpdateTime;			    /* �������ʱ�� */
	string                                  OldPassword<16>;			    /* ������ */ 
	string                                  NewPassword<16>;			    /* ������ */ 
};


%// ---- ����Ա�������������	   
struct UpdatePassword_Ans_t{
	StationID_t                             StationID;              		    /* ��վ��� */   
	DealResult_t                            DealResult;                                 /* ���½�� */ 
        FailReason_t                            FailReason;                                 /* ʧ��ԭ�� */ 
}; 


%// ---- MACKӦ����
struct Mack_Ans_t{
	MACK_t                                  MackCode;							/* Ӧ���� */
};	
	

%// ---- ������
union REQMessage_t switch (ComType_t ComType) {             
	case COM_COMM_CONNECT_APPLY:										/* (1001) ͨѶ�������� */
             CommConnectApply_Req_t			CommConnectApply_Req;						

	case COM_PING:												/* (1101) PING������ */
	     Ping_Req_t					Ping_Req;									
	
	case COM_TEXT:												/* (1102) �ı���Ϣ */
  	     Text_Req_t					Text_Req;
								
	case COM_SLE_CONTROL_CMD:										/* (1103) �豸�������� */
	     SleControlCmd_Req_t			SleControlCmd_Req;						

	case COM_TIME_SYNC:											/* (1104)ǿ��ʱ��ͬ������ */
	     SyncTime_Req_t			        SyncTime_Req;						

	case COM_EVENT_UPLOAD:											/* (1201) �豸�¼��ϴ� */
	     EventUpload_Req_t				EventUpload_Req;						

	case COM_QUERY_EOD_VERSION:										/* (1202) ��ѯ�¼������汾 */
	     QueryEODVersion_Req_t			QueryEODVersion_Req;					

	case COM_QUERY_FILE_VERSION:										/* (1203) ��ѯ�¼��ļ��汾 */
	     QueryFileVersion_Req_t			QueryFileVersion_Req;				

	case COM_QUERY_TRANS_SN:										/* (1205) ��ѯ�豸������ˮ�� */
	     QuerySN_Req_t				QuerySN_Req;							

	case COM_UPLOAD_EOD_VERSION:										/* (1206) EOD�汾�ϴ� */
	     UploadEODVersion_Req_t		        UploadEODVersion_Req;					
	
	case COM_UPLOAD_FILE_VERSION:										/* (1207) �ļ��汾�ϴ� */
	     UploadParameterVersion_Req_t	        UploadParameterVersion_Req;

	case COM_UPLOAD_SAM:											/* (1208) �豸SAM��Ϣ�ϴ� */
	     UploadSAM_Req_t				UploadSAM_Req;				
		   
	case COM_UPLOAD_SN:											/* (1210) �豸������ˮ���ϴ� */
	     UploadSN_Req_t				UploadSN_Req;				
	
	case COM_EndTime_SET:											/* (1211)  ������Ӫ����ʱ�� */
	     SetRuntime_Req_t				SetRuntime_Req;				
        
   	case COM_OPERATOR_WORK:											/* (1301) ����Ա��¼����ͣ���ָ����˳��Ȳ��� */
	     OperatorWork_Req_t				OperatorWork_Req;					

	case COM_TICKETBOX_OPERATE:										/* (1303) Ʊ����������ϴ� */
	     TicketBoxOperate_Req_t			TicketBoxOperate_Req;				

	case COM_BANKNOTEBOX_OPERATE:										/* (1304) ֽ��Ǯ����������ϴ� */
	     BankNoteBoxOperate_Req_t			BankNoteBoxOperate_Req;				

	case COM_COINBOX_OPERATE:										/* (1305) Ӳ��Ǯ����������ϴ� */
	     CoinBoxOperate_Req_t			CoinBoxOperate_Req;					

	case COM_COINBOX_CLEAR:											/* (1306) TVMӲ��Ǯ����������ϴ� */
	     CoinBoxClear_Req_t				CoinBoxClear_Req;		
 
        case COM_NOTEBOX_CLEAR:											/* (1307) TVMֽ��Ǯ����������ϴ� */
	     NoteBoxClear_Req_t				NoteBoxClear_Req;		
  
        case COM_CASHBOX_QUERY:											/* (1308) ��ѯ�豸Ǯ������ */
              CASHBOXQuery_Req_t			CashBoxQuery_Req;	
    
        case COM_CASHBOX_UPLOAD:										/* (1309) �豸Ǯ�������ϴ� */
             CASHBOXUPLoad_Req_t			CashBoxUpload_Req;				

	case COM_FTP_APPLY:											/* (1401) FTP���� */
	     FTPApply_Req_t				FTPApply_Req;						

	case COM_FILE_UPDATE_NOTIFY:										/* (1402) �ļ�����֪ͨ */
	     FileUpdateNotify_Req_t			FileUpdateNotify_Req;				

	case COM_CURFILE_UPLOAD_NOTIFY:										/* (1403) ��ǰ�ļ�����֪ͨ */
	     CurFileUploadNotify_Req_t			CurFileUploadNotity_Req;				

	case COM_SPECFILE_UPLOAD_NOTIFY:									/* (1404) ָ���ļ�����֪ͨ */
	     SpecFileUploadNotify_Req_t			SpecFileUploadNotify_Req;			

	case COM_SEND_EMERGENCY_MODE:										/* (1501) ����/���������Ӫģʽ */
	     SendEmergencyMode_Req_t			SendEmergencyModeReq;				

	case COM_SEND_OTHER_MODE:										/* (1502) ����������Ӫģʽ���� */
	     SendOtherMode_Req_t			SendOtherMode_Req;					

	case COM_MODE_STATUS_UPLOAD:										/* (1503) ģʽ״̬�ϴ� */
	     ModeStatusUpload_Req_t			ModeStatusUpload_Req;				

	case COM_MODE_CHANGE_BROADCAST:										/* (1504) ģʽ����㲥 */
	     ModeChangeBroadcast_Req_t			ModeChangeBroadcast_Req;				

	case COM_MODE_QUERY:											/* (1505) ��վģʽ��ѯ */
	     QueryMode_Req_t				QueryMode_Req;						

	case COM_TICKET_ALLOC_APPLY:										/* (1601) Ʊ���������� */
	     TicketAllocApply_Req_t			TicketAllocApply_Req;				

	case COM_TICKET_ALLOC_ORDER:										/* (1602) Ʊ���������� */
	     TicketAllocOrder_Req_t			TicketAllocOrder_Req;				

	case COM_UPLOAD_TICKET_STOCK_NOTIFY:									/* (1604) ֪ͨ�¼��ϴ�Ʊ�������Ϣ */
	     UploadTicketStockNotify_Req_t		UploadTicketStockNotify_Req;		
	
	case COM_UPLOAD_TICKET_STOCK:										/* (1605) �ϴ�Ʊ�������Ϣ */
	     UploadTicketStock_Req_t			UploadTicketStock_Req;	
	
	case COM_UPLOAD_TICKET_INCOME:										/* (1606) �ϴ�Ʊ��������Ϣ */
	     UploadTicketIncome_Req_t			UploadTicketIncome_Req;	    

        case COM_UPLOAD_TICKET_FILLINGMONEY:									/* (1607) �ϴ��̿����Ϣ */
	     UploadTicketFillingMoney_Req_t	        UploadTicketFillingMoney_Req;	    

	case COM_BOM_ADDVALUE_AUTH:										/* (1701) BOM���߳�ֵ��֤ */
	     BOMAddValueAuth_Req_t			BOMAddValueAuth_Req;	
	     
	case COM_BOM_ADDVALUE_AFFIRM:										/* (1702) ��ֵȷ�� */
	     BOMAddValueAffirm_Req_t			BOMAddValueAffirm_Req;	

	case COM_PERSONAL_CARD_APPLY:										/* (1703) ���Ի������� */
	     PersonalCardApply_Req_t			PersonalCardApply_Req;				

	case COM_NONIMMEDIATE_REFUND_APPLY:									/* (1704) �Ǽ�ʱ�˿����� */ 
	     NonImmediateRefundApply_Req_t		NonImmediateRefundApply_Req;			

	case COM_NONIMMEDIATE_REFUND_QUERY:									/* (1705) �Ǽ�ʱ�˿��ѯ */
	     NonImmediateRefundQuery_Req_t		NonImmediateRefundQuery_Req;		
	     
	case COM_TICKET_LOST:										        /* (1706) Ʊ����ʧ */
	     TicketLost_Req_t				TicketLost_Req;						

	case COM_TICKET_DISLOST:										/* (1707) Ʊ����� */
	     TicketDisLost_Req_t			TicketDisLost_Req;			
	     
	case COM_NONIMMEDIATE_REFUND_SEND:									/* (1708) �Ǽ�ʱ�˿���Ϣ�·� */
	     NonImmediateRefundSend_Req_t		NonImmediateRefundSend_Req;			

	case COM_CARD_ACCOUNT_QUERY:										/* (1709) Ʊ���˻���ѯ */
	     CardAccountQuery_Req_t			CardAccountQuery_Req;				

	case COM_CARD_ACCOUNT_SEND:										/* (1710) Ʊ���˻���Ϣ�·� */
	     CardAccountSend_Req_t			CardAccountSend_Req;				

        case COM_STATION_MAINTAIN_APPLY:								        /* (1801) ��վ�������� */
	     Station_Maintain_Apply_Req_t		Station_Maintain_Apply_Req;				

	case COM_STATION_MAINTAIN_AFFIRM:								        /* (1802) ��վά�޽��ȷ�� */
	    Station_Maintain_Affirm_Req_t		Station_Maintain_Affirm_Req;				
	     
	case COM_UPDATE_PASSWORD:										/* (1901) ����Ա������� */
	     UpdatePassword_Req_t			UpdatePassword_Req;
	     
};


%// ---- Ӧ����
union ANSMessage_t switch (ComType_t ComType) {     
	case COM_COMM_CONNECT_APPLY:										   /* (1001) ͨѶ�������� */
	     CommConnectApply_Ans_t				CommConnectApply_Ans;	
		        
	case COM_FTP_APPLY:											   /* (1401) FTP���� */
	     FTPApply_Ans_t					FTPApply_Ans;						

	case COM_BOM_ADDVALUE_AUTH:										   /* (1701) BOM���߳�ֵ��֤ */
	     BOMAddValueAuth_Ans_t				BOMAddValueAuth_Ans;					
        
	case COM_UPDATE_PASSWORD:										   /* (1901) ����Ա������� */
	     UpdatePassword_Ans_t			        UpdatePassword_Ans;
        
	default: 												   /*  MACKӦ���� */
		Mack_Ans_t					MackAns;							
};


%// ---- ����/Ӧ����
union Message_t switch (MessageType_t MessageType) {             
    case MT_REQMESSAGE:															/* ������ */
        REQMessage_t						REQMessage;							

    case MT_ANSMESSAGE:															/* Ӧ���� */
        ANSMessage_t						ANSMessage;							
};


%// ---- ��ͷ
struct PackHead_t{
	ProtocolVer_t                                           ProtocolVer;						/* ����Э���� */
	ComType_t				                ComType;						/* ��Ϣ������ */
	DeviceID_t						SendDeviceID;						/* ���ͷ���ʶ�� */
	DeviceID_t						ReceiveDeviceID;					/* ���ܷ���ʶ�� */
	RouterTag_t						RouterTag;						/* ·�ɱ�� */
	AFCTime64_t						SendTime;						/* ����ʱ�� */
	SN_t							SessionSN;						/* �Ự��ˮ�� */
	MessageType_t						MessageType;						/* ����Ӧ���־ */
	MACK_t							MACK;							/* Ӧ���� */
	AlgorithmicType_t					AlgorithmicType;					/* ѹ�������㷨 */	
};


%// ---- ��Ϣ���ṹ
struct Pack_t{
	PackLength_t                            PackLength;                         /* ������ */
	PackHead_t                              PackHead;                           /* ��ͷ */
	Message_t                               PackBody;                           /* ���� */
	MD5_t                                   MD5Value;                           /* MD5����֤��ͷ�Ͱ��� */
};

%// -------------------------------------------------------------------------------------------------------------

%#endif 
%/********************************************** �ļ����� *******************************************************/