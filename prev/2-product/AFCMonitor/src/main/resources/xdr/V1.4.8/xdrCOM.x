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
%*   2016/06/20    *    ���Ʒ�           * 1.2.0 ��������������·AFCϵͳ����������׼V1.2�����ĵ�  
%*   2016/08/10    *    �½���           *�޸�CoinBoxInfo_t�ṹ������MoneyInCoinBoxs<>ΪMoneyInCoinBoxs��
%*                                        �޸�NoteBoxInfo_t�ṹ������MoneyInBankNoteBoxs<>ΪMoneyInBankNoteBoxs��
%*                                        �޸�CashBoxInfo_t�ṹ������MoneyInBoxs<>ΪMoneyInBoxs��
%*   2017/11/03    *    �½���           *BillInfoList_t�ṹ��������ticketFamilyType��ticketType��TicketStatus��TicketDirClassID;
%*										  ����OperAmount�ֶ�����ΪU32_t;
%*										  TicketAllocApply_Req_t�ṹ��������OperatorID; 
%*										  TicketAllocOrder_Req_t�ṹ����Դ�ڵ��Ŀ��ڵ����͵���ΪDeviceID_t
%*										  UpdatePassword_Req_t�ṹ���нڵ����͵���ΪDeviceID_t 
%*   2018/03/16    *    ���Ʒ�           *�޸�UpdatePassword_Req_t��UpdatePassword_Ans_t�ṹ�ṩ�ڵ�����ΪU32_t����������д˵��
%*                                       *�޸�ParameterVersionList_t������FileVersionList<>,����FileVersionList_t�ṹ��
%*					 *TicketAllocList_t�ṹ��ɾ��TicketAllocType_t�ֶΣ�TicketAllocApply_Req_t��TicketAllocOrder_Req_t�ṹ������TicketAllocType_t�ֶ�
%*   2018/04/13    *    ���Ʒ�           *1001��1401��1901Ӧ��������Ӧ�����ֶ�
%*   2018/05/03    *    ���Ʒ�           *�����ṹ�壺SleInfoList_t,ChangeShiftsBillInfoList_t,UploadChangeShiftsRecord_Req_t,TicketFillingMoneyNotice_Req_t,SleCashList_t,UploadCashForm_Req_t,UploadSellerStatements_Req_t,BomTicketInfoList_t,UploadStationRevenuedaily_Req_t
%*   2018/05/03    *    ���Ʒ�           *�޸Ľṹ�壺BillInfoList_t,UploadTicketIncome_Req_t
%*   2018/05/03    *    ���Ʒ�           *�������½ṹ�����REQMessage_t����
%*   2018/05/11    *    ���Ʒ�           *TicketAllocOrder_Req_t�ṹ��������OperatorID; 
%*   2018/06/04    *    ���Ʒ�           *1701Ӧ��������Ӧ�����ֶ�
%*   2018/06/19    *    ���Ʒ�           *�޸�CommConnectApply_Ans_t��ModeStatusUpload_Req_t��StationModeChange_t��ModeList_t��UploadTicketIncome_Req_t��UploadChangeShiftsRecord_Req_t��TicketFillingMoneyNotice_Req_t��UploadTicketFillingMoney_Req_t��UploadCashForm_Req_t��UploadSellerStatements_Req_t��UploadStationRevenuedaily_Req_t��Station_Maintain_Apply_Req_t��Station_Maintain_Affirm_Req_t��PaperTicketSaleInfoList_t�ṹ����StationID����ΪU32_t; 
%*   2018/06/19    *    ���Ʒ�           *�޸�TicketBoxOperate_Req_t�ṹ�壬����ContainerOperatePos�ֶ�
%*   2018/07/19    *    ���Ʒ�           *�޸�REQMessage_t�ṹ�壬���ӿ�������༰BOM�౨�Ķ���
%*   2018/07/19    *    ���Ʒ�           *����UploadStationImmediateRefund_Req_t��UploadTicketInfo_Req_t��TicketInfoList_t��UploadTicketInventory_Req_t��TickettInventoryList_t�ṹ��
%*   2018/07/19    *    ���Ʒ�           *����UploadReserveFundsInfo_Req_t��UploadSpecFundsInfo_Req_t��SpecFundsInfoList_t��UploadPaperTicketSaleInfo_Req_t��PaperTicketSaleInfoList_t
%*   2018/07/19    *    ���Ʒ�           *����SleCashOperInfoList_t��UploadSleCashOperInfo_Req_t��UploadCustomerserviceStatements_Req_t��SleCashOperInfoList_t��SleCashCheckInfoList_t��CustomerTicketInfoList_t
%*   2018/07/19    *    ���Ʒ�           *�޸�UploadStationRevenuedaily_Req_t�ṹ��
%*   2018/07/19    *    ���Ʒ�           *����UploadWealTicketInfo_Req_t��UploadPassengerAffairInfo_Req_t��StationNonImmediateRefundSend_Req_t�ṹ��
%*   2018/07/31    *    ���Ʒ�           *����StationImmediateRefundList_t�ṹ�壬�޸�UploadStationImmediateRefund_Req_t�ṹ��
%*   2018/07/31    *    ���Ʒ�           *�޸�UploadTicketInfo_Req_t��UploadTicketInventory_Req_t��UploadReserveFundsInfo_Req_t�ṹ�壬����BusinessDate�ֶ�
%*   2018/07/31    *    ���Ʒ�           *�޸�UploadStationRevenuedaily_Req_t�ṹ�壬���ӱ�ע�ֶ�
%*   2018/07/31    *    ���Ʒ�           *�޸�UploadSN_Req_t�ṹ�壬��2��Ԥ���ֶ��޸�Ϊ���п�������ˮ�źͶ�ά�뽻����ˮ��
%*   2018/07/31    *    ���Ʒ�           *�޸�CashBoxInfo_t�ṹ��MoneyInBoxsΪMoneyInBoxs<>
%*   2018/07/31    *    ���Ʒ�           *�޸�CASHBOXQuery_Req_t�ṹ��ΪCashBoxQuery_Req_t��CASHBOXUPLoad_Req_tΪCashBoxUPLoad_Req_t 
%*   2018/07/31    *    ���Ʒ�           *����TicketBoxQuery_Req_t��TicketBoxUPLoad_Req_t��TicketBoxInfo_t�ṹ��
%*   2018/07/31    *    ���Ʒ�           *�޸�SendEmergencyMode_Req_t�ṹ��ModeEnabledTag�ֶ�����ΪU8_t
%*   2018/07/31    *    ���Ʒ�           *�޸�SleCashList_t��UploadSellerStatements_Req_t��BomTicketInfoList_t��UploadStationRevenuedaily_Req_t��StationImmediateRefundList_t��UploadReserveFundsInfo_Req_t��SpecFundsInfoList_t��SleCashOperInfoList_t��UploadCustomerserviceStatements_Req_t��CustomerTicketInfoList_t��UploadPassengerAffairInfo_Req_t��StationNonImmediateRefundSend_Req_t�ṹ��������ֶ�����ΪValueCent_t
%*   2018/07/31    *    ���Ʒ�           *�޸�TicketInventoryList_t�ṹ��DiffQuantity�ֶ�����ΪS32_t
%*   2018/07/31    *    ���Ʒ�           *�޸�SleCashCheckInfoList_t�ṹ��MoneyInBoxs�ֶ�ΪMoneyInBoxs<>
%*   2018/07/31    *    ���Ʒ�           *�޸�NonImmediateRefundSend_Req_t�ṹ����RefundStatus��TicketACCValidDate˳��
%*   2018/08/14    *    ���Ʒ�           *�޸�CustomerTicketInfoList_t�ṹ�壬����SaleValue�ֶ�
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
	DeviceID_t                              DeviceID;				/* Ŀ��ڵ��ʶ�� */
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


%// ��Ӫ�����ļ��汾
struct FileVersionList_t{
	FileHeaderTag_t				FileHeaderTag;				/* �ļ������� */
	SPCode_t                                SPCode;                                 /* �����̱���*/
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
	MACK_t                                  MackCode;				/* Ӧ���� */
	EODVersionList_t			EODVersionList<>;			/* EOD�����汾�б� */
};

%// ---- ��ѯ�¼��ļ��汾������
struct QueryFileVersion_Req_t{
	DeviceID_t                              DeviceID;			       /* Ŀ��ڵ��ʶ�� */
	VersionQueryType_t			VersionQueryType;		       /* �汾��ѯ���ͣ�0����ѯ�豸���� 1����ѯ�豸��д�� 2����ѯ�豸��д���� */
};


%// ��Ӫ�����汾�б�
struct ParameterVersionList_t{
	VersionKind_t				VersionKind;	                      /* �汾����  0-�豸���� 1-��д��1 2-��д��2*/
       	FileVersionList_t			FileVersionList<>;		      /* ��Ӫ��������汾 */
};


%// ---- EOD�汾�ϴ�������
struct UploadEODVersion_Req_t{
	DeviceID_t                              DeviceID;                   /* �豸�ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* �ϴ�ʱ�� */
	EODVersionList_t			EODVersionList<>;			/* EOD�����汾�б� */  
};	

%// ---- ��Ӫ�����汾�ϴ�������
struct UploadParameterVersion_Req_t{
	DeviceID_t                              DeviceID;                   /* �豸�ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* �ϴ�ʱ�� */
	ParameterVersionList_t			ParameterVersionList<>;	     /* ��Ӫ�����汾��Ϣ */   
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
	SN_t                                    UNIONPAYUSDN;		    /* ���п���ˮ�� */
	SN_t                                    QRCODEUSDN;		    /* ��ά����ˮ�� */
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
	U32_t                                   StationID;                      /* ģʽ�����仯�ĳ�վ�ڵ��� */
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
        MACK_t                                 MackCode;			        /* Ӧ���� */
	SPCode_t                               SpCode;                    		/* �����̱�� */  
	LineID_t                               LineID;                   		/* ��·��� */
	UnicodeString_t                        LineCNName;          			/* ��·�������� */
	U32_t                                  StationID;              			/* ��վ��� */
	UnicodeString_t                        StationCNName;      			/* ��վ�������� */
	DeviceID_t                             DeviceID;                                /* �豸�ڵ��ʶ�� */
	ModeCode_t                             ModeCode;             			/* ��ǰ��վ��Ӫģʽ */
	ModeList_t                             ModeList<>;                              /* ȫ·����վģʽ�б� */
	FTPFileDir_t			       FTPFileDir<>;                            /* FTPĿ¼�б�*/
};			
	

%// ---- �ϴ�Ʊ���������������	
struct TicketBoxOperate_Req_t{
	DeviceID_t                              DeviceID;						/* �豸�ڵ��ʶ�� */
	ContainerOperatePos_t			ContainerOperatePos;					/* ����λ�� */
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
	MoneyInBox_t                            MoneyInBoxs<>;			                    /* Ǯ������� */
};

%// Ʊ����Ϣ	
struct TicketBoxInfo_t{
	TicketBoxType_t                         TicketBoxType;					    /* Ʊ������ */
	ContainerID_t                           TicketBoxID;					    /* Ʊ���� */
	TicketQuantity_t                        TicketQuantity;					    /* Ʊ������ */
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
struct CashBoxQuery_Req_t{
	DeviceID_t                              AimDeviceID;						/* Ŀ��ڵ��ʶ�� */	
};		

%// ---- �豸Ǯ�������ϴ�������	
struct CashBoxUPLoad_Req_t{
	DeviceID_t                              DeviceID;						/* �豸�ڵ��ʶ�� */
	CashBoxInfo_t                           CashBoxInfo<>;                                          /* Ǯ����Ϣ */
};	


%// ---- ��ѯ�豸Ʊ������������
struct TicketBoxQuery_Req_t{
	DeviceID_t                              AimDeviceID;						/* Ŀ��ڵ��ʶ�� */	
};		

%// ---- �豸Ʊ�������ϴ�������	
struct TicketBoxUPLoad_Req_t{
	DeviceID_t                              DeviceID;						/* �豸�ڵ��ʶ�� */
	TicketBoxInfo_t                         TicketBoxInfo<>;                                        /* Ʊ����Ϣ */
};


%// ---- FTP����������
struct FTPApply_Req_t{
	DeviceID_t                              AimDeviceID;						/* Դ�ڵ��ʶ�� */	
};		


%// ---- FTP����Ӧ����	
struct FTPApply_Ans_t{
        MACK_t                                   MackCode;			/* Ӧ���� */
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
	U8_t                                    ModeEnabledTag;								/* ģʽ��־��0������1��� */	
};	

%// ---- ����������Ӫģʽ������	 
struct SendOtherMode_Req_t{
	DeviceID_t                              AimDeviceID;								/* Ŀ���豸�ڵ��ʶ�� */	
	ModeCode_t                              ModeCode;								/* ��Ӫģʽ���� */	
};	


%// ---- ģʽ״̬�ϴ�������	 
struct ModeStatusUpload_Req_t{
	U32_t                                   StationID;              					/* ģʽ�����仯�ĳ�վ�ڵ��� */
	AFCTime64_t                             ModeOccurTime;							/* ģʽ����ʱ�� */
	ModeCode_t                              ModeCode;							/* �ı�����Ӫģʽ���� */	
};


%// ---- ��վģʽ�仯��վ�б�	 
struct StationModeChange_t{
	U32_t                                   StationID;              					/* ģʽ�����仯�ĳ�վ�ڵ��� */
	AFCTime64_t                             ModeOccurTime;							/* ģʽ����ʱ�� */
	ModeCode_t                              ModeCode;							/* �ı�����Ӫģʽ���� */	
};


%// ---- ��վģʽ����㲥������	 
struct ModeChangeBroadcast_Req_t{
	StationModeChange_t			StationModeChanges<>;              			/* ģʽ�����仯�ĳ�վ�б� */
};

	 
%// ---- ��վģʽ��ѯ������ 
struct QueryMode_Req_t{
	DeviceID_t                              AimDeviceID;								/* Ŀ���豸�ڵ��ʶ�� */	    
};


%// ---- Ʊ������Ʊ�ּ�¼	 
struct TicketAllocList_t{
	TicketCatalogID_t						TicketCatalogID;              				/* Ʊ��Ŀ¼��� */  	
	TicketQuantity_t						TicketAllocQuantity;					/* Ʊ���������� */  
	TicketQuantity_t						TicketWasteQuantity;					/* ��Ʊ���� */  	
};


%// ---- Ʊ����������������	 
struct TicketAllocApply_Req_t{
	DeviceID_t                             SourceStationID;           			 /* Դ��վ�ڵ��� */  	
	DeviceID_t                             AimStationID;              			 /* Ŀ�공վ�ڵ��� */
	OperatorID_t			       OperatorID;			              /* ����Ա */
	string                                  ApplyBillCode<20>;					/* ���뵥��� */          
	AFCTime64_t                             ApplyTime;						/* ����ʱ�� */
	TicketAllocType_t		        TicketAllocType;		/* Ʊ���������� */ 
	TicketAllocList_t                       TicketAllocList<>;					/* ����Ʊ��Ŀ¼�б� */
};
	 

%// ---- Ʊ����������������	 
struct TicketAllocOrder_Req_t{
	DeviceID_t                             SourceStationID;              				/* Դ�ڵ��� */  	
	DeviceID_t                             AimStationID;              				/* Ŀ��ڵ��� */   
        OperatorID_t			        OperatorID;			                        /* ����Ա */
	string                                  ApplyBillCode<20>;					/* ������ */          
	AFCTime64_t                             AllocTime;						/* ����ʱ�� */
	TicketAllocType_t			TicketAllocType;		                        /* Ʊ���������� */ 
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

%// ������Ϣ�б�	   
struct BillInfoList_t{
	ContainerID_t							        ContainerID;                 	                /* Ǯ���� */  	
        U8_t                                                                    BoxType;                                        /* Ǯ������ */
	FaceValue_t                                                             FaceValue;                                      /* Ǯ����ֵ */
	U32_t                                       	                        OperAmount;                  	                /* �������� */              
	S32_t                                  	                                OperMoney;                   	                /* ������� */
	U32_t                                       	                        SleAmount;                  	                /* �������� */              
	S32_t                                  	                                SleMoney;                   	                /* ������� */
	UnicodeString_t                   		                        Remark;                     	                /* ��ע */
};

%// �豸��Ϣ�б�	   
struct SleInfoList_t{
	DeviceID_t                              SLEID;                      /* �豸��� */
	OperatorID_t                            OperatorID;	            /* BOM��ƱԱ�Ϳ�ֵ����Ա��� */
	BillInfoList_t			        BillInfoList<>;             /* ������Ϣ�б� */   
};


%// Ʊ��������Ϣ�б�
struct UploadTicketIncome_Req_t{
	U32_t                                   NodeID;                     /* �ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* �ϴ�ʱ�� */
        U32_t                                   StationID;                  /* ��վ��� */  	
        string                                  BillCode<20>;		    /* ���ݱ�� */       
        BillType_t	                        BillType;                   /* �������� */
        BillStatus_t                            BillStatus;                 /* ����״̬*/
        OperatorID_t                            OperatorID;	            /* ����Ա��� */
        AFCTime64_t                             OperateTime;                /* ����ʱ�� */
	OperatorID_t                            AuditorID;	            /* �����Ա��� */
        AFCTime64_t                             AuditorTime;                /* ���ʱ�� */           
	string                                  BankCode<32>;               /* ���б��� */
        U32_t                                   SpareGoldAllocation;        /* ���ý���Դ/ȥ�� */ 
	string                                  SpareGoldBillCode<20>;      /* ���ý����ĵ��ݱ�� */ 
        U8_t                                    SpeIncomeReason;            /* ��������ԭ�� */
        UnicodeString_t                         Remark;                     /* ��ע */
	SleInfoList_t			        SleInfoList<>;              /* �豸��Ϣ�б� */   
};


%// ��ֵ������Ϣ�б�	   
struct ChangeShiftsBillInfoList_t{
	U8_t					TicketFamilyType;				/* Ʊ����� */
	TicketCatalogID_t                       TicketCatalogID;				/* Ʊ��Ŀ¼��� */
	U8_t					TicketType;					/* Ʊ������ */
        U32_t                                   TicketValue;                                    /* ��Ʊ��ֵ*/
	U8_t					TicketStatus;					/* Ʊ��״̬ */
 	U32_t                                   StockAmount;                  	                /* ������� */              
	S32_t                                  	OperMoney;                   	                /* ����� */
	U32_t                                   SysAmount;                  	                /* ϵͳ���� */              
	S32_t                                  	SysMoney;                   	                /* ϵͳ��� */
	UnicodeString_t                         Remark;                     	                 /* ��ע */
};

%// ��ֵ���Ӱ���Ϣ�б�
struct UploadChangeShiftsRecord_Req_t{
	U32_t                                   NodeID;                     /* �ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* �ϴ�ʱ�� */
        U32_t                                   StationID;                  /* ��վ��� */  	
        string                                  BillCode<20>;		    /* ���ݱ�� */       
        BillType_t	                        BillType;                   /* �������� */
        BillStatus_t                            BillStatus;                 /* ����״̬*/
        OperatorID_t                            OperatorID;	            /* ����Ա��� */
        AFCTime64_t                             OperateTime;                /* ����ʱ�� */
	OperatorID_t                            AuditorID;	            /* �����Ա��� */
        AFCTime64_t                             AuditorTime;                /* ���ʱ�� */
        OperatorID_t                            RelationOperatorID;	    /* ��ֵ����Ա��� */
	string                                  RelationBillCode<20>;       /* �������ݱ�� */ 
	UnicodeString_t                         Remark;                     /* ��ע */
	ChangeShiftsBillInfoList_t	        ChangeShiftsBillInfoList<>; /* ������Ϣ�б� */   
};


%// �豸���̿�֪ͨ��Ϣ�б�
struct TicketFillingMoneyNotice_Req_t{
	DeviceID_t                              DeviceID;                   /* �豸�ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* ֪ͨʱ�� */
        U32_t                                   StationID;                  /* ��վ��� */  	
        string                                  BillCode<20>;		    /* ���ݱ�� */       
        BillType_t	                        BillType;                   /* �������� */
        BillStatus_t                            BillStatus;                 /* ����״̬*/
        OperatorID_t                            OperatorID;	            /* ����Ա��� */
        AFCTime64_t                             OperateTime;                /* ����ʱ�� */
	OperatorID_t                            AuditorID;	            /* �����Ա��� */
        AFCTime64_t                             AuditorTime;                /* ���ʱ�� */
        ExcuteStatus_t                          ExcuteStatus;               /* ִ��״̬ */
        ValueCent_t                             TotalFillingMoneyValue;     /* �̿��ܶ� */
        string                                  RelateBillCode<20>;         /* �������� */
        AFCTime64_t                             SettleStartTime;            /* ������ʼʱ�� */
        AFCTime64_t                             SettleEndTime;              /* �������ʱ�� */
        Date2_t                                 FillingMoneyEndDate;        /* �̿���ֹ���� */
	ErrorType_t                             ErrorType;                  /* ������� */
        OperatorID_t                            ErrorOperatorID;	    /* �������Ա��� */
	AFCTime64_t                             ErrorTime;                  /* ���ʱ�� */
	UnicodeString_t                         Remark;                     /* ��ע */  
};

%// �̿���ϴ���Ϣ�б�
struct UploadTicketFillingMoney_Req_t{
	DeviceID_t                              DeviceID;                   /* �豸�ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* �ϴ�ʱ�� */
        U32_t                                   StationID;                  /* ��վ��� */  	
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
	OperatorID_t                            ErrorOperatorID;	     /* �������Ա��� */
	AFCTime64_t                             ErrorTime;                   /* ���ʱ�� */
	ErrorType_t                             ErrorType;                   /* ������� */
        UnicodeString_t                         ErrorReason;                 /* ���ԭ�� */
        UnicodeString_t                         ErrorExplain;                /* ���˵�� */
        ValueCent_t                             FillingMoneyValue;           /* ������ */
};


%// �豸�ֽ��б�	   
struct SleCashList_t{
	U8_t                                    SleType;		        /* �豸���� 0-TVM 1-ISM*/
	DeviceID_t                              DeviceID;                       /* �豸��� */
        ValueCent_t                             AddCoinMoney;                   /* Ӳ�Ҳ��ҽ�� */
        ValueCent_t                             AddNoteMoney;                   /* ֽ�Ҳ��ҽ�� */
        ValueCent_t                             PointMoney;                   	/* ʵ���� */
        ValueCent_t                             ReceiptMoney;                   /* ʵ�ս�� */
};



%// �豸�ֽ�Ǽ��ձ��б�
struct UploadCashForm_Req_t{
	U32_t                                   NodeID;                     /* �ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* �ϴ�ʱ�� */
        U32_t                                   StationID;                  /* ��վ��� */  	
        Date2_t				        BusinessDate;		    /* ��Ӫ�� */
	SleCashList_t                           SleCashList_t<>;            /* �豸�ֽ��б� */
};

%// ��Ʊ��Ϣ�б������Ԥ��ֵƱ������Ʊ�ȣ�	   
struct BomTicketInfoList_t{
	TicketCatalogID_t                       TicketCatalogID;				/* Ʊ��Ŀ¼��� */
	U8_t					TicketType;					/* Ʊ������ */
        U32_t                                   TicketValue;                                    /* ��Ʊ��ֵ*/
	U8_t					TicketStatus;					/* Ʊ��״̬ */
 	U32_t                                   CollarAmount;                  	                /* �������� */              
	U32_t                                   HandInMoney;                   	                /* �Ͻɽ�� */
	U32_t                                   SaleAmount;                  	                /* �������� */              
	ValueCent_t                             SaleMoney;                   	                /* ���۽�� */
	UnicodeString_t                         Remark;                     	                /* ��ע */
};


%// BOM��ƱԱ���㵥
struct UploadSellerStatements_Req_t{
	U32_t                                   NodeID;                     /* �ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* �ϴ�ʱ�� */
        U32_t                                   StationID;                  /* ��վ��� */  	
        DeviceID_t                              DeviceID;                   /* �豸��� */
	Date2_t				        BusinessDate;		    /* ��Ӫ�� */
	OperatorID_t                            OperatorID;	            /* ����Ա��� */
        AFCTime64_t                             OperateTime;                /* ����ʱ�� */
	OperatorID_t                            AuditorID;	            /* �����Ա��� */
        AFCTime64_t                             AuditorTime;                /* ���ʱ�� */
        OperatorID_t                            SellerID;	            /* ��ƱԱ��� */
        ValueCent_t                                   SpareGoldUseMoney;          /* ���ý����ý�� */
	ValueCent_t                                   TransactionValue;           /* ��ֵ��� */
	ValueCent_t                                   SurchargeValue;             /* ���½�� */
	ValueCent_t                                   RefundValue;                /* �˿��� */
	ValueCent_t                                   SpecialIncome;              /* �������� */
	ValueCent_t                                   StatementsMoney;            /* ������ */
        BomTicketInfoList_t                     BomTicketInfoList<>;        /* ��Ʊ��Ϣ */
};




%// �ͷ���Ʊ��Ϣ�б�	   
struct CustomerTicketInfoList_t{
	TicketCatalogID_t                       TicketCatalogID;				/* Ʊ��Ŀ¼��� */
	U8_t					TicketType;					/* Ʊ������ */
        U32_t                                   TicketValue;                                    /* ��Ʊ��ֵ*/
	U32_t                                   CollarAmount;                  	                /* �䷢���� */   
	U32_t                                   AddAmount;                  	                /* �������� */   
	U32_t                                   WasteAmount;                  	                /* ��Ʊ���� */  
	U32_t                                   RecoverAmount;                   	        /* �������� */
	U32_t                                   SaleAmount;                  	                /* �������� */
	ValueCent_t                             SaleValue;				        /* ���۽�� */
	ValueCent_t                             DepositValue;				        /* Ѻ���� */
	ValueCent_t                             AddValue;				        /* ��ֵ��� */
};



%// ��Ʊ�۴���Ϣ�б�
struct TicketInfoList_t{
	TicketCatalogID_t                       TicketCatalogID;				/* Ʊ��Ŀ¼��� */
	U8_t					TicketType;					/* Ʊ������ */
        U32_t                                   YesterdayQuantity;                              /* ���ս�� */
	U32_t                                   DistributeQuantity;                             /* �·��� */
        U32_t                                   StationInQuantity;                              /* վ�����*/
        U32_t                                   AGMRecoveryQuantity;                            /* AGM����*/ 
        U32_t                                   AGMWasteQuantity;                               /* AGM��Ʊ*/
        U32_t                                   TVMRecoveryQuantity;                            /* TVM����*/
        U32_t                                   TVMWasteQuantity;                               /* TVM��Ʊ*/
        U32_t                                   BOMRecoveryQuantity;                            /* BOM����*/
        U32_t                                   BOMWasteQuantity;                               /* BOM��Ʊ*/
        U32_t                                   PassengerReturnQuantity;                        /* �˿���Ʊ*/
        U32_t                                   OtherAddQuantity;                               /* ���������ӣ�*/
        U32_t                                   HandInQuantity;                                 /* �Ͻ��� */
        U32_t                                   StationOutQuantity;                             /* վ����� */
        U32_t                                   TVMAllocateQuantity;                            /* TVM��Ʊ*/
	U32_t                                   BOMAllocateQuantity;                            /* BOM��Ʊ*/
	U32_t                                   OtherReduceQuantity;                            /* ���������٣�*/
 	U32_t                                   TodayQuantity;                  	        /* ���ս�� */              
	U32_t                                   CheckQuantity;                   	        /* ʵ��������� */
};

%// ��Ʊ�̵���Ϣ�б�
struct TicketInventoryList_t{
	U8_t					TicketType;					/* Ʊ������ */
        U32_t                                   PaperQuantity;                                  /* ������ */
	U32_t                                   CheckQuantity;                                  /* ����� */
        S32_t                                   DiffQuantity;                   	        /* �������� */
};

%// �������Ʊ����Ϣ�б�
struct SpecFundsInfoList_t{
	DeviceID_t                              DeviceID;                                       /* �豸��� */
	ValueCent_t                                   InsideValue;                                    /* �ڲ���� */
        ValueCent_t                                   OutsideValue;                   	        /* �ⲿ��� */
	OperatorID_t                            PayeeID;				        /* �տ�Ա��� */
	OperatorID_t                            CheckerID;					/* ȷ���˱�� */
	FaceValue_t                             FaceValue;					/* ��ֵ */
	U32_t                                   Amount;                  	                /* ���� */ 
	AFCTime64_t                             DiscoveryTime;                                  /* ����ʱ�� */
        UnicodeString_t                         DiscoveryPos;                                   /* ����λ�� */
};

%// ֽƱ������Ϣ�б�
struct PaperTicketSaleInfoList_t{
	U8_t                                    PaperTicketValue;                               /* ֽƱ��� */
	U32_t                                   DistributeQuantity;                             /* �䷢���� */
        U32_t                                   AddQuantity;                      	        /* ������ */
	U32_t                                   SaleQuantity;                   	        /* �������� */ 
	U32_t                                   RecoveryQuantity;                               /* �������� */ 
	ValueCent_t                                   TotalValue;                                     /* С�ƽ�� */
};

%// �豸����б�
struct SleCashCheckInfoList_t{
	U8_t                                  	CashBoxType;                                    /* Ǯ������ */
        MoneyInBox_t                            MoneyInBoxs<>;					/* Ǯ����Ϣ */
};



%// �豸���ҡ�����б�
struct SleCashOperInfoList_t{
	DeviceID_t                              DeviceID;                                       /* �豸��� */
        ValueCent_t                                  	AddCoinMoney;                                   /* Ӳ�Ҳ��ҽ�� */
        ValueCent_t                                  	AddNoteMoney;                                   /* ֽ�Ҳ��ҽ�� */
	SleCashCheckInfoList_t                  SleCashCheckInfoList<>;                         /* ����б� */
};


%// ----��վӪ���ձ�
struct UploadStationRevenuedaily_Req_t{
	U32_t                                   NodeID;                     /* �ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* �ϴ�ʱ�� */
        U32_t                                   StationID;                  /* ��վ��� */  	
        Date2_t				        BusinessDate;		    /* ��Ӫ�� */
        ValueCent_t                                    ExchangeBank;               /* �������� */
        ValueCent_t                                    ExchangeReturn;             /* ���㷵�� */
	ValueCent_t                                    PackMoney;                  /* ������ */
        ValueCent_t                                    YesterdayBankMoney;         /* ǰ�ս���Ʊ�� */
	ValueCent_t                                    YesterdayRetainMoney;       /* ǰ������Ʊ�� */
	ValueCent_t                                    TodayBankMoney;             /* ���ս���Ʊ�� */
	ValueCent_t                                    TodayRetainMoney;           /* ��������Ʊ�� */
	ValueCent_t                                    BankFillingMoney;           /* ���в���Ʊ�� */
	ValueCent_t                                    SpecialIncome;              /* �������� */
	ValueCent_t                                    IsmIncome;                  /* ISM�ϼ����� */
	ValueCent_t                                    TvmIncome;                  /* TVM�ϼ����� */
	ValueCent_t                                    BomIncome;                  /* BOM�ϼ����� */
	ValueCent_t                                    IsmFillingMoney;            /* ISM���̿����� */
	ValueCent_t                                    TvmFillingMoney;            /* TVM���̿����� */
	ValueCent_t                                    BomFillingMoney;            /* BOM���̿����� */
	ValueCent_t                                    SpareGoldRetain;            /* ���ý����� */
	ValueCent_t                                    DiffMoney;                  /* ��� */
        UnicodeString_t                         Remark;                     /* ��ע */
        ValueCent_t                                    AddValue;	            /* ��ֵ��� */            
	ValueCent_t                                    DepositValue;		    /* Ѻ���� */
	ValueCent_t                                    TicketSaleValue;            /* ����Ʊ���۽�� */
	ValueCent_t                                    SurchargeValue;             /* ���½�� */
	ValueCent_t                                    TicketReturnValue;          /* ����Ʊ��Ʊ��� */
        ValueCent_t                                    PrepayTicketSaleValue;      /* Ԥ��ֵƱ���۽�� */      
        ValueCent_t                                    SouvenirTicketSaleValue;    /* ����Ʊ���۽�� */      
	ValueCent_t                                    PaperTicketSaleValue;       /* ֽƱ���۽�� */      
	ValueCent_t                                    RefundValue;                /* �����˿� */
};

%// --���������Ʊ��ʱ�˿��б�
struct StationImmediateRefundList_t{
        U8_t					TicketType;		    /* Ʊ������ */
	TicketPhyID_t                           TicketPhyID;		    /* �������� */
        ValueCent_t                                   TicketValue;          /* Ʊ����� */
        AFCTime64_t                             DealTime;                   /* ����ʱ�� */
        UnicodeString_t                         Remark;                     /* ��ע */
}


%// --���������Ʊ��ʱ�˿���Ϣ�ϱ�
struct UploadStationImmediateRefund_Req_t{
	U32_t                                   NodeID;                          /* �ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                      /* �ϴ�ʱ�� */
        U32_t                                   StationID;                       /* ��վ��� */  	
        Date2_t				        BusinessDate;		         /* ��Ӫ�� */
        StationImmediateRefundList_t            StationImmediateRefundList<>;    /*��Ʊ��Ʊ�б�*/
};



%// --��վƱ���۴���Ϣ�ϱ�
struct UploadTicketInfo_Req_t{
        U32_t                                   NodeID;                     /* �ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* �ϴ�ʱ�� */
	Date2_t				        BusinessDate;		    /* ��Ӫ�� */
        TicketInfoList_t                        TicketInfoList<>;           /* ��վƱ���۴���Ϣ�б� */
	UnicodeString_t                         Remark;                     /* ��ע */
};


%// --��վƱ���̵���Ϣ�ϱ�
struct UploadTicketInventory_Req_t{
        U32_t                                   NodeID;                     /* �ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* �ϴ�ʱ�� */ 
	Date2_t				        BusinessDate;		    /* ��Ӫ�� */
	TicketInventoryList_t                   TicketInventoryList<>;      /* ��վƱ���̵���Ϣ�б� */
	UnicodeString_t                         Remark;                     /* ��ע */

};


%// --��վ���ý������Ϣ�ϱ�
struct UploadReserveFundsInfo_Req_t{
        U32_t                                   NodeID;                     /* �ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* �ϴ�ʱ�� */ 
	Date2_t				        BusinessDate;		    /* ��Ӫ�� */
        ValueCent_t                                   PaperReserveFunds;          /* ���ý�Ӧ������ */ 
        ValueCent_t                                   CheckReserveFunds;          /* ���ý�ʵ������ */ 
	UnicodeString_t                         Remark;                     /* ��ע */
};

%// --�������Ʊ����Ϣ�ϱ�
struct UploadSpecFundsInfo_Req_t{
        U32_t                                   NodeID;                     /* �ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* �ϴ�ʱ�� */
        U32_t                                   StationID;                  /* ��վ��� */  	
        Date2_t				        BusinessDate;		    /* ��Ӫ�� */
	SpecFundsInfoList_t                     SpecFundsInfoList<>;         /* �������Ʊ����Ϣ�б� */
	UnicodeString_t                         Remark;                     /* ��ע */
};

%// --ֽƱ������Ϣ�ϱ�
struct UploadPaperTicketSaleInfo_Req_t{
        U32_t                                   NodeID;                     /* �ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* �ϴ�ʱ�� */
        U32_t                                   StationID;                  /* ��վ��� */  	
        Date2_t				        BusinessDate;		    /* ��Ӫ�� */
	PaperTicketSaleInfoList_t               PaperTicketSaleInfoList<>;  /* ֽƱ������Ϣ�б� */
	UnicodeString_t                         Remark;                     /* ��ע */

};

%// --�豸���ҡ������Ϣ�ϱ�
struct UploadSleCashOperInfo_Req_t{
        U32_t                                   NodeID;                     /* �ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* �ϴ�ʱ�� */
        U32_t                                   StationID;                  /* ��վ��� */  	
        Date2_t				        BusinessDate;		    /* ��Ӫ�� */
        SleCashOperInfoList_t                   SleCashOperInfoList<>;      /* �豸���ҡ�����б� */
	UnicodeString_t                         Remark;                     /* ��ע */
};

%// --�ͷ����㵥��Ϣ�ϱ�
struct UploadCustomerserviceStatements_Req_t{
        U32_t                                   NodeID;                     /* �ڵ��ʶ�� */
	AFCTime64_t                             UploadTime;                 /* �ϴ�ʱ�� */
        U32_t                                   StationID;                  /* ��վ��� */  	
        DeviceID_t                              DeviceID;                   /* BOM��� */
	Date2_t				        BusinessDate;		    /* ��Ӫ�� */
	OperatorID_t                            OperatorID;	            /*�ͷ�Ա��� */
	OperatorID_t                            OperatorCheckID;	    /*����ֵ��Ա��� */
        ContainerID_t                           CashBoxID;		    /* Ǯ���� */
        ValueCent_t                                   SpareGoldUseMoney;          /* ���ý��� */
	ValueCent_t                                   SurchargeValue;             /* ���½�� */
	ValueCent_t                                   TicketReturnValue;          /* ����Ʊ��Ʊ��� */
        ValueCent_t                                   RefundValue;                /* �����˿��� */
        ValueCent_t                                   CheckValue;                 /* ʵ���� */
        ValueCent_t                                   TotalValue;                 /* Ӫ�ս�� */
        CustomerTicketInfoList_t                CustomerTicketInfoList<>;   /*�ͷ���Ʊ��Ϣ�б�*/
	UnicodeString_t                         Remark;                     /* ��ע */
};


%// ---- BOM���߳�ֵ��֤������   
struct BOMAddValueAuth_Req_t{
	DeviceID_t                              AimDeviceID;								/* BOM�豸�ڵ��ʶ�� */   
	SAMID_t                                 SAMID;									/* SAM����ʶ�� */  
	BOMShiftID_t                            BOMShiftID;								/* BOM��κ� */
	OperatorID_t                            OperatorID;								/* BOM����Ա��� */
	IssuerCode_t				IssuerCode;								/* ���������� */
	TicketPhyID_t                           TicketPhyID;								/* �û���������� */
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
 	MACK_t                                  MackCode;			                                        /* Ӧ���� */
	DeviceID_t                              AimDeviceID;								/* BOM�豸�ڵ��ʶ�� */   
	BOMShiftID_t                            BOMShiftID;								/* BOM��κ� */
	OperatorID_t                            OperatorID;								/* BOM����Ա��� */
	TicketPhyID_t                           TicketPhyID;								/* �û���������� */
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
	TicketPhyID_t                           TicketPhyID;								/* �û���������� */
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
	string                                  ApplyBillCode<20>;					/* ���뵥���:4�ֽڽڵ��ţ�YYYYMMDD��4λ���кţ�BCD�� */          
	AFCTime64_t                             ApplyTime;							/* ����ʱ�� */
	OperatorID_t                            OperatorID;							/* BOM����Ա��� */
	TicketPhyID_t                           TicketPhyID;						/* �û���������� */
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
	TicketPhyID_t                           TicketPhyID;						/* �û���������� */
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
	Date2_t                                 TicketACCValidDate;					/* ACCƱ����Ч������ */
        RefundStatus_t                          RefundStatus;	                                        /* �˿��״̬  0-������ 1-�Ѵ���*/
        DealResult_t                            DealResult;             	                        /* ������� */
	TicketPhyID_t                           TicketPhyID;						/* �û���������� */
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
	TicketPhyID_t                           TicketPhyID;						/* �û���������� */
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
	TicketPhyID_t                           TicketPhyID;						/* �û���������� */
	string                                  TicketPrintID<20>;					/* �û�ӡˢ����� */
	UnicodeString_t                         PassengerName;					        /* �˿����� */     
	Gender_t                                PassengerSex;						/* �˿��Ա� */     
	IdentificationType_t                    IdentificationType;					/* ֤������ */
	string                                  IDCode<32>;						/* ֤����� */   
};
   

%// ---- Ʊ���˻���ѯ������	   
struct CardAccountQuery_Req_t{
	DeviceID_t                              DeviceID;						/* �豸�ڵ��ʶ�� */   
	TicketPhyID_t                           TicketPhyID;						/* �û���������� */
	string                                  TicketPrintID<20>;					/* �û�ӡˢ����� */
	UnicodeString_t                         PassengerName;					        /* �˿����� */
	IdentificationType_t                    IdentificationType;					/* ֤������ */
	string                                  IDCode<32>;						/* ֤����� */   
};
  

%// ---- ����Ʊ���˻��б��ṹ	   
struct CardAccounList_t{
	TicketPhyID_t                           TicketPhyID;						/* �û���������� */
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
	CardAccounList_t                        CardAccounList<>;              		                /* ����Ʊ���˻��б��ṹ */
};


%// --����Ʊ������Ϣ�ϴ�
struct UploadWealTicketInfo_Req_t{
        DeviceID_t                              DeviceID;						/* BOM�豸�ڵ��ʶ�� */   
	BOMShiftID_t                            BOMShiftID;						/* BOM��κ� */
	Date2_t				        BusinessDate;		                                /* ��Ӫ�� */
	AFCTime64_t                             SaleTime;						/* ����ʱ�� */
	OperatorID_t                            BOMOperatorID;						/* BOM����Ա��� */
        IdentificationType_t                    IdentificationType;					/* ֤������ */
	string                                  IDCode<32>;						/* ֤����� */
	UnicodeString_t                         PassengerName;					        /* �˿����� */    
	string                                  TelNumber<32>;					        /* ��ϵ�绰 */ 
        UnicodeString_t                         Remark;                                                 /* ��ע */
};


%// --��վ�˿���������Ϣ�ϱ�
struct UploadPassengerAffairInfo_Req_t{
        DeviceID_t                              DeviceID;						/* BOM�豸�ڵ��ʶ�� */   
	BOMShiftID_t                            BOMShiftID;						/* BOM��κ� */
	Date2_t				        BusinessDate;		                                /* ��Ӫ�� */
	AFCTime64_t                             DealTime;						/* ����ʱ�� */
	OperatorID_t                            BOMOperatorID;						/* BOM����Ա��� */
	U8_t                                    AffairsType;                                            /* �������� */
	U8_t                                    EventType;                                              /* �¼����� */
        UnicodeString_t                         AffairsDetails;                                         /* �¼����� */    
	UnicodeString_t                         AffairsResult;                                          /* ������� */
	ValueCent_t                                   InvolveMoney;                                           /* �漰��� */
	UnicodeString_t                         Remark;                                                 /* ��ע */
};


%// --��վ�Ǽ�ʱ�˿�����
struct StationNonImmediateRefundSend_Req_t{
        DeviceID_t                              DeviceID;						/* BOM�豸�ڵ��ʶ�� */   
	U32_t                                   StationID;                                              /* ���복վ */  	
	string                                  ApplyTableCode<20>;					/* ���뵥���:4�ֽڽڵ��ţ�YYYYMMDD��4λ���кţ�BCD�� */ 
	AFCTime64_t                             ApplyTime;						/* ����ʱ�� */
	OperatorID_t                            BOMOperatorID;						/* �Ǽǲ���Ա��� */
        ValueCent_t                             ApplyMoney;                                             /* �����˿��� */
	string                                  PassengerAffairCode<20>;				/* �˿�������ƾ����� */ 
        UnicodeString_t                         SituationExplain;                                       /* ���˵�� */
};

%// ---- ��վ��������������	   
struct Station_Maintain_Apply_Req_t{
	U32_t                                   StationID;                                              /* ���޳�վ��� */    
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
	U32_t                                   StationID;                                              /* ���޳�վ��� */    
	AFCTime64_t                             AffirmTime;						/* ȷ��ʱ�� */
        string                                  MaintainBillCode<16>;                                   /* ���޵��� */	
	OperatorID_t                            AffirmOperatorID;					/* ȷ����Ա��� */        
	UnicodeString_t                         AffirmOperatorName;				        /* ȷ����Ա���� */    
        MaintainResult_t                        MaintainResult;                                         /* ά�޽�� */  
        UnicodeString_t                         Remark;                                                 /* ��ע */
};




%// ---- ����Ա�������������	   
struct UpdatePassword_Req_t{
	U32_t                             	NodeID;              		            /* ������� ������ڵ�Ϊ�豸ʱ���ڵ�����ΪDeviceID_t��������ڵ�Ϊ��վʱ���ڵ���ֽ�U16��ʾStationID_t�����ֽڲ���*/   
	OperatorID_t                            OperatorID;				    /* ����Ա��� */   
	AFCTime64_t                             PasswordUpdateTime;			    /* �������ʱ�� */
	string                                  OldPassword<16>;			    /* ������ */ 
	string                                  NewPassword<16>;			    /* ������ */ 
};


%// ---- ����Ա�������Ӧ����	   
struct UpdatePassword_Ans_t{
	MACK_t                                  MackCode;			            /* Ӧ���� */
	U32_t                                   NodeID;              		            /* ������� ������ڵ�Ϊ�豸ʱ���ڵ�����ΪDeviceID_t��������ڵ�Ϊ��վʱ���ڵ���ֽ�U16��ʾStationID_t�����ֽڲ��� */   
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
             CashBoxQuery_Req_t			        CashBoxQuery_Req;	
    
        case COM_CASHBOX_UPLOAD:										/* (1309) �豸Ǯ�������ϴ� */
             CashBoxUPLoad_Req_t			CashBoxUpload_Req;		
	     
        case COM_TICKETBOX_QUERY:										/* (1310) ��ѯ�豸Ʊ������ */
             TicketBoxQuery_Req_t			TicketBoxQuery_Req;	
    
        case COM_TICKETBOX_UPLOAD:										/* (1311) �豸Ʊ�������ϴ� */
             TicketBoxUPLoad_Req_t			TicketBoxUpload_Req;	

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

	case COM_UPLOAD_TICKET_STOCK_NOTIFY:									/* (1603) ֪ͨ�¼��ϴ�Ʊ�������Ϣ */
	     UploadTicketStockNotify_Req_t		UploadTicketStockNotify_Req;		
	
	case COM_UPLOAD_TICKET_STOCK:										/* (1604) �ϴ�Ʊ�������Ϣ */
	     UploadTicketStock_Req_t			UploadTicketStock_Req;	
	
	case COM_UPLOAD_TICKET_INCOME:										/* (1605) �ϴ�Ʊ��������Ϣ */
	     UploadTicketIncome_Req_t			UploadTicketIncome_Req;	    

        case COM_UPLOAD_CHANGESHIFTS_RECORD:									/* (1606) ��ֵ���Ӱ���Ϣ�ϱ� */
	     UploadChangeShiftsRecord_Req_t	        UploadChangeShiftsRecord_Req;	  
	
	case COM_TICKET_FILLINGMONEY_NOTICE:									/* (1607) �̿����Ϣ֪ͨ */
	     TicketFillingMoneyNotice_Req_t	        TicketFillingMoneyNotice_Req;	       

        case COM_UPLOAD_TICKET_FILLINGMONEY:									/* (1608) �̿����Ϣ�ϱ� */
	     UploadTicketFillingMoney_Req_t	        UploadTicketFillingMoney_Req;	  

        case COM_UPLOAD_CASH_FORM:									        /* (1609) �豸�ֽ�Ǽ��ձ��ϱ� */
	     UploadCashForm_Req_t	                UploadCashForm_Req;	  

        case COM_UPLOAD_SELLER_STATEMENTS:									/* (1610) BOM��ƱԱ���㵥�ϱ� */
	     UploadSellerStatements_Req_t	        UploadSellerStatements_Req;	  

        case COM_UPLOAD_STATION_REVENUEDAILY:									/* (1611) ��վӪ���ձ��ϱ� */
	     UploadStationRevenuedaily_Req_t	        UploadStationRevenuedaily_Req;	  
	
	case COM_UPLOAD_STATION_IMMEDIATE_REFUND:								/* (1612) ���������Ʊ��ʱ�˿���Ϣ�ϱ� */
	     UploadStationImmediateRefund_Req_t	        UploadStationImmediateRefund_Req;	  

        case COM_UPLOAD_TICKET_INFO:									        /* (1613) ��վƱ���۴���Ϣ�ϱ� */
	     UploadTicketInfo_Req_t	                UploadTicketInfo_Req;	  
	
	case COM_UPLOAD_TICKET_INVENTORY:									/* (1614) ��վƱ���̵���Ϣ�ϱ� */
	     UploadTicketInventory_Req_t	        UploadTicketInventory_Req;	  
	
	case COM_UPLOAD_RESERVEFUNDS_INFO:									/* (1615) ��վ���ý������Ϣ�ϱ� */
	     UploadReserveFundsInfo_Req_t	        UploadReserveFundsInfo_Req;	  
	      
	case COM_UPLOAD_SPECFUNDS_INFO:									        /* (1616) �������Ʊ����Ϣ�ϱ� */
	     UploadSpecFundsInfo_Req_t	                UploadSpecFundsInfo_Req;	  
	       
	case COM_UPLOAD_PAPERTICKET_SALEINFO:									/* (1617) ֽƱ������Ϣ�ϱ� */
	     UploadPaperTicketSaleInfo_Req_t	        UploadPaperTicketSaleInfo_Req;	  
	      
	case COM_UPLOAD_SLECASH_OPERINFO:									/* (1618) �豸���ҡ������Ϣ�ϱ� */
	     UploadSleCashOperInfo_Req_t	        UploadSleCashOperInfo_Req;	  
	       
	case COM_UPLOAD_CUSTOMERSERVICE_STATEMENTS:							        /* (1619) �ͷ����㵥��Ϣ�ϱ� */
	     UploadCustomerserviceStatements_Req_t	UploadCustomerserviceStatements_Req;	  


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

	case COM_UPLOAD_WEALTICKETINFO:										/* (1711) ����Ʊ������Ϣ�ϴ� */
	     UploadWealTicketInfo_Req_t			UploadWealTicketInfo_Req;				

	case COM_UPLOAD_PASSENGERAFFAIRINFO:									/* (1712) ��վ�˿���������Ϣ�ϱ� */
	     UploadPassengerAffairInfo_Req_t		UploadPassengerAffairInfo_Req;				

	case COM_STATION_NONIMMEDIATE_REFUND_APPLY:								/* (1713) ��վ�Ǽ�ʱ�˿����� */
	     StationNonImmediateRefundSend_Req_t	StationNonImmediateRefundSend_Req;				

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
	     Mack_Ans_t					        MackAns;							
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