%/*************************************************************************************************************
%
%   ����������·AFCϵͳ����������׼
%
%   @Title       : xdrSLE.x
%   @Version     : 1.2.0
%   Author      : ���Ʒ�
%   Date        : 2016/06/20
%   Description  : ����SLE���ļ���ʽ 
%**************************************************************************************************************/

%/*  �޶���ʱ��¼:
%**************************************************************************************************************
%*   Date          *    Author           *     Changes
%**************************************************************************************************************
%*   2016/06/20    *    ���Ʒ�           *     1.2.0 ��������������·AFCϵͳ����������׼V1.2�����ĵ�
%*   2016/08/20    *    �½���           *     SLEYKTUD_t�ṹ��������һ��ͨ����
%*   					       ɾ��SLEYKTUD_t�ṹע��
%*   2017/11/03    *    �½���           *     SLEMessage_t�ṹ����AGMARMSG_t��BOMARMSG_t��TVMARMSG_t��ISMARMSG_t��PCAARMSG_t����Ϊ�б���ʽ
%*   2018/07/03    *    ���Ʒ�           *     ����SLEQRCODEUD_t��QRCodeUDMSG_t�ṹ�壬�޸�SLEBANKUD_t�ṹ��
%**************************************************************************************************************/

%#ifndef XDRSLE_H 
%#define XDRSLE_H
%#include "xdrDCP.h"


%// -------------------------------------------------------------------------------------------------------------
%// ---------- �豸��Ӫ�����ļ��ṹ��UD��AR��
%// -------------------------------------------------------------------------------------------------------------

%// һƱͨ���׽ṹ
union SLEYPTUD_t switch(TransactionType_t TransactionType) {					
	case TX_SJTSale:													/* ����Ʊ���� */
		SJTSale_TX                                SJTSale;					

	case TX_NonNamedCPUSale:												/* �Ǽ�����ֵƱ���� */
		CPUSale_TX                                CPUSale;					

	case TX_NamedCPUSale:													/* ������ֵƱ���� */
		NamedCPUSale_TX                           NamedCPUSale;				

	case TX_CPUCardAddValue:												/* ��ֵƱ��ֵ */
		CPUCardAddValue_TX                        CPUCardAddValue;			

	case TX_SJTRefund:                                       					                        /* ����Ʊ��ʱ��Ʊ */
		SJTRefund_TX                              SJTRefund;				

	case TX_CPUCardImmediateRefund:												/* ��ֵƱ��ʱ��Ʊ */
		CPUCardImmediateRefund_TX                 CPUCardImmediateRefund;	

	case TX_CPUCardNonImmediateRefund:											/* ��ֵƱ�Ǽ�ʱ��Ʊ */
		CPUCardNonImmediateRefund_TX              CPUCardNonImmediateRefund;	

	case TX_TicketValidityPeriod:												/* ��ֵƱ���� */
		CPUCardValidityPeriod_TX                  CPUCardValidityPeriod;		

	case TX_CPUCardBlock:													/* ��ֵƱ���� */
		CPUCardBlock_TX                           CPUCardBlock;				

	case TX_CPUCardUnblock:													/* ��ֵƱ���� */
		CPUCardUnblock_TX                         CPUCardUnblock;			

	case TX_TicketSurcharge:												/* һƱͨ���� */
		TicketSurcharge_TX                        TicketSurcharge;			

	case TX_TicketEntry:													/* һƱͨ��վ */
		TicketEntry_TX                            TicketEntry;				

	case TX_TicketExit:													/* һƱͨ��վ */
		TicketExit_TX                             TicketExit;				

	case TX_CPUCardDeduction:												/* ��ֵƱ�ۿԤ���� */
		CPUCardDeduction_TX                       CPUCardDeduction;	
};

%// һ��ͨ���׽ṹ
union SLEYKTUD_t switch(TransactionType_t TransactionType) {					
	case TX_YKTCardSurcharge:													/* һ��ͨ���� */
		TicketSurcharge_TX                        TicketSurcharge;			

	case TX_YKTCardEntry:														/* һ��ͨ��վ */
		TicketEntry_TX                            TicketEntry;				

	case TX_YKTCardExit:														/* һ��ͨ��վ */
		YKTTicketExit_TX                          YktTicketExit;				
 
        case TX_YKTCardAddValue:													/*һ��ͨ��ֵ */
		CPUCardAddValue_TX                        CPUCardAddValue;	
	
	case TX_YKTCardBlock:													/* ��ֵƱ���� */
		CPUCardBlock_TX                           CPUCardBlock;	
		
%//	case TX_YKTSale:													         /* һ��ͨ������Ԥ��) */
%//		YKTSale_TX                                YKTSale;		
    };	

%// �ֻ�Ʊ���׽ṹ
union SLEMOBILEUD_t switch(TransactionType_t TransactionType) {					
%//	case TX_MobileSurcharge:													/* �ֻ������� */
%//		TicketSurcharge_TX                        TicketSurcharge;			

%//	case TX_MobileEntry:														/* �ֻ�����վ */
%//		TicketEntry_TX                            TicketEntry;				

%//	case TX_MobileExit:														/* �ֻ�����վ */
%//		TicketExit_TX                             TicketExit;				

	case TX_MobileDeduction:													/* �ֻ������ۿԤ���� */
		MobileDeduction_TX						 MobileDeduction;			
};

%// ���п����׽ṹ
union SLEBANKUD_t switch(TransactionType_t TransactionType) {					
	case TX_BankCardSurcharge:													/* ���п�����*/
		BankCardSurcharge_TX                     BankCardSurcharge;
	case TX_BankCardEntry:														/* ���п���վ*/
		BankCardEntry_TX                         BankCardEntry;
	case TX_BankCardExit:														/* ���п���վ*/
		BankCardExit_TX                          BankCardExit;
	case TX_BankCardDeduction:													/* ���п��ۿ� */
		BankCardDeduction_TX                     BankCardDeduction;			
};


%// ��ά�뽻�׽ṹ
union SLEQRCODEUD_t switch(TransactionType_t TransactionType) {					
	case TX_QRCodeSurcharge:													/* ��ά�����*/
		QRCodeSurcharge_TX                     QRCodeSurcharge;
	case TX_QRCodeEntry:														/* ��ά���վ*/
		QRCodeEntry_TX                         QRCodeEntry;
	case TX_QRCodeExit:															/* ��ά���վ*/
		QRCodeExit_TX                          QRCodeExit;
};

%// �豸һƱͨ������Ϣ�ṹ
struct SLEYPTUDMSG_t {															
	UDComm_t                             UDComm;									/*���׹����ṹ*/
	SLEYPTUD_t                           YPTUDData;									/*�������ݽṹ*/
	TAC_t                                TAC;									/*����TAC��*/
};

%// �豸һ��ͨ������Ϣ�ṹ
struct SLEYKTUDMSG_t {															
	YKTUDComm_t                          YKTUDComm;									/*һ��ͨ���׹����ṹ*/
	SLEYKTUD_t                           YKTUDData;									/*һ��ͨ���׽ṹ*/
	TAC_t                                TAC;										/*����TAC��*/
};

%// �豸�ֻ���������Ϣ�ṹ
struct SLEMobileUDMSG_t {															
	UDComm_t                             UDComm;									/*���׹����ṹ*/
	SLEMOBILEUD_t                        SLEMOBILEUD;								/*�ֻ������׽ṹ*/
	TAC_t                                TAC;										/*����TAC��*/
};

%// ���п�������Ϣ�ṹ
struct SLEBankUDMSG_t {															
	UDComm_t                             UDComm;									/*���׹����ṹ*/
	SLEBANKUD_t                          SLEBANKUD;									/*���п����׽ṹ*/
	TAC_t                                TAC;										/*����TAC��*/
};

%// ��ά�뽻����Ϣ�ṹ
struct QRCodeUDMSG_t {															
	UDComm_t                             UDComm;									/*���׹����ṹ*/
	SLEQRCODEUD_t                        SLEQRCODEUD;								/*���п����׽ṹ*/
	TAC_t                                TAC;										/*����TAC��*/
};

%// AGM��ƼĴ�����Ϣ�ṹ
struct AGMARMSG_t {																	
	ARComm_t                             ARComm;									/*AR�����ṹ*/  
	AGMAuditInfo_t                       AGMAuditInfo;								/*AGM�����Ϣ*/  
};

%// BOM��ƼĴ�����Ϣ�ṹ
struct BOMARMSG_t {																	
	ARComm_t                             ARComm;									/*AR�����ṹ*/  
	BOMAuditInfo_t                       BOMAuditInfo;								/*BOM�����Ϣ*/  
};

%// TVM��ƼĴ�����Ϣ�ṹ
struct TVMARMSG_t {																	
	ARComm_t                             ARComm;									/*AR�����ṹ*/  
	TVMAuditInfo_t                       TVMAuditInfo;								/*TVM�����Ϣ*/  
};

%// ISM��ƼĴ�����Ϣ�ṹ
struct ISMARMSG_t {																	
	ARComm_t                             ARComm;									/*AR�����ṹ*/  
	ISMAuditInfo_t                       ISMAuditInfo;								/*ISM�����Ϣ*/  
};


%// PCA��ƼĴ�����Ϣ�ṹ
struct PCAARMSG_t {																	
	ARComm_t                             ARComm;									/*AR�����ṹ*/  
	PCAAuditInfo_t                       PCAAuditInfo;								/*PCA�����Ϣ*/  
};

%// �豸��Ϣ�ṹ
union SLEMessage_t switch (FileHeaderTag_t TransactionTag) {					   	
	case FILE_YPT_TRANSACTION:                                                                                       /*�豸һƱͨ������Ϣ�ṹ*/
		SLEYPTUDMSG_t             YPTUD<>;									

	case FILE_YKT_TRANSACTION:                                                                                      /*�豸һ��ͨ������Ϣ�ṹ*/
		SLEYKTUDMSG_t             YKTUD<>;									

	case FILE_MOBILE_TRANSACTION:                                                                                    /*�豸�ֻ���������Ϣ�ṹ*/
		SLEMobileUDMSG_t          MOBILEUD<>;							     	       

	case FILE_BANK_TRANSACTION:                                                                                      /*���п�������Ϣ�ṹ*/
		SLEBankUDMSG_t		  BANKUD<>;								       

        case FILE_QRCODE_TRANSACTION:
		QRCodeUDMSG_t		  QRCodeUD<>;								         /*��ά�뽻����Ϣ�ṹ*/

	case FILE_AGM_AUDIT_REGISTER:                                                                                   /*AGM��ƼĴ�����Ϣ�ṹ*/
		AGMARMSG_t                AGMARMSG<>;									

	case FILE_BOM_AUDIT_REGISTER:                                                                                  /*BOM��ƼĴ�����Ϣ�ṹ*/
		BOMARMSG_t                BOMARMSG<>;									

	case FILE_TVM_AUDIT_REGISTER:                                                                                   /*TVM��ƼĴ�����Ϣ�ṹ*/
		TVMARMSG_t                TVMARMSG<>;									

	case FILE_ISM_AUDIT_REGISTER:                                                                                   /*ISM��ƼĴ�����Ϣ�ṹ*/
		ISMARMSG_t                ISMARMSG<>;									

	case FILE_PCA_AUDIT_REGISTER:                                                                                   /*PCA��ƼĴ�����Ϣ�ṹ*/
		PCAARMSG_t                PCAARMSG<>;									

};



%// ---- �豸��Ӫ�ļ�ͷ�ṹ
struct SLEDataFileHeader_t {															
	FileHeaderTag_t						      FileHeaderTag;			      /* �ļ����*/
        SLEFileVersionID_t                                            FileVersion;                            /* �ļ��汾*/
	AFCTime64_t						      FileCreationTime;                       /* �ļ�����ʱ�� */
	string							      FileName<40>;                           /* �ļ��� */
	LineID_t						      LineID;				      /*��·���*/
	StationID_t						      StationID;			      /*��վ���*/
	DeviceID_t						      DeviceID;				      /*�豸�ڵ���*/
	Date2_t							      BusinessDay;			      /*��Ӫ����*/
	SN_t							      FileSN;				      /*�ļ���ˮ��*/
	RecordsInFile_t						      RecordsInFile;                          /*�ļ���¼��*/
};

%// ---- �豸��Ӫ�ļ��ṹ
struct SLEDataFile_t {															
	SLEDataFileHeader_t						SLEDataFileHeader;                      /* �豸��Ӫ�ļ�ͷ�ṹ */
	SLEMessage_t							SLEMessages;				/*�豸��Ϣ�ṹ*/
	MD5_t								MD5Value;				/*MD5ǩ��*/
};

%// -------------------------------------------------------------------------------------------------------------

%#endif
%/********************************************** �ļ����� *******************************************************/



