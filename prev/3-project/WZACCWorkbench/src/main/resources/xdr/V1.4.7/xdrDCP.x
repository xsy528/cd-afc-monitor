%/*************************************************************************************************************
%
%   ����������·AFCϵͳ����������׼
%
%   Title       : xdrDCP.x
%   @Version     : 1.2.0
%   Author      : ���Ʒ�
%   Date        : 2016/06/20
%   Description : ����ϵͳ���豸�����ļ����ļ���ʽ
%**************************************************************************************************************/

%/*  �޶���ʱ��¼:
%**************************************************************************************************************
%*   Date          *    Author           *     Changes
%**************************************************************************************************************
%*   2016/06/20    *    ���Ʒ�           * 1.2.0 ��������������·AFCϵͳ����������׼V1.2�����ĵ�
%*   2016/08/10    *    �½���           * ɾ��struct NoteBoxInfo_t�ṹ��
%                                        * ����ISMSnapshotInfo_t�ṹ�壬���ṹ����NoteBoxInfo_t�޸�ΪMoneyInBankNoteBox_t��
%                                        * ɾ��CashBoxInfo_t�ṹ�壻
%                                        * SJTSale_t�ṹ��Ԥ���ֶ��ƶ�����Ʊ��״̬���ֶ�֮��
% 					 * RebateScheme_t�ṹ������PileConcessionType��/*�ۼ��Żݷ�ʽ*/��
%                                        * YKTTicketExit_TX�ṹ������Ԥ��1��Ԥ��2�ֶΣ�
%                                        * CPUCardAddValue_t�ṹ����ע��BankCardPayment_t���п�֧���ṹ��
%                                        * ����YKTUDComm_t�ṹ��;
%*   2017/11/03    *    �½���           * ESCPUCardInit_t�ṹ�͹淶�ĵ�����һ��
%					 * TVMAuditInfo_t�ṹ����TVMSnapshotInfo_t�ֶε���Ϊ���б���ʽ
%					 * ����TVMSnapshotInfo_t�ṹ����CashBoxInfo_t�ֶ������
%					 * ����ISMSnapshotInfo_t�ṹ���е�CashBoxInfo_t�ֶ������
%*   2018/05/03    *    ���Ʒ�           * TicketStockInOutDetailList_t����TicketValue�ֶ�
%*   2018/06/05    *    ���Ʒ�           * ɾ��TicketStockDifferenceFile_t�ṹ��
%*   2018/07/03    *    ���Ʒ�           * ����BankCardTicketComm_t��QRCodeTicketComm_t��QRCodeSurcharge_t��BankCardEntry_t��BankCardExit_t��QRCodeExit_t�ṹ��
%*   2018/07/03    *    ���Ʒ�           * ɾ��BankCardPayment_t�ṹ��
%*   2018/07/03    *    ���Ʒ�           * �޸�BankCardDeduction_t�ṹ��
%*   2018/07/03    *    ���Ʒ�           * ����BankCardSurcharge_TX��BankCardEntry_TX��BankCardExit_TX��QRCodeSurcharge_TX��QRCodeEntry_TX��QRCodeExit_TX�ṹ��
%*   2018/07/31    *    ���Ʒ�           * �޸�BankCardSurcharge_t�ṹ��SaleDeviceID�ֶ���д˵��
%**************************************************************************************************************/

%#ifndef XDRDCP_H
%#define XDRDCP_H
%
%#include "xdrBaseType.h"


%// -------------------------------------------------------------------------------------------------------------
%// ---------- �豸��Ӫ�����ļ��ṹ��UD��AR��
%// -------------------------------------------------------------------------------------------------------------

%// ---- ���׹����ṹ
struct UDComm_t{													
	TransactionType_t					        TransactionType;        
	AFCTime64_t							TransactionDateTime;                /* ���ײ���ʱ�� */
	LineID_t							LineID;                             /* ��·��� */
	StationID_t							StationID;                          /* ��վ��� */
	DeviceID_t							DeviceID;                           /* �豸�ڵ�� */
	SAMID_t								TACSAMID;                           /* SAM���� */
	ModeCode_t							ModeCode;                           /* ��վ��Ӫģʽ */
	SN_t								UDSN;                               /* �豸������ˮ�� */
};
%// ---- һ��ͨ���׹����ṹ
struct YKTUDComm_t{			
	TransactionType_t					TransactionType;                 
	AFCTime64_t							TransactionDateTime;                /* ���ײ���ʱ�� */
	LineID_t							LineID;                             /* ��·��� */
	StationID_t							StationID;                          /* ��վ��� */
	DeviceID_t							DeviceID;                           /* �豸�ڵ�� */
	SAMID_t								TACSAMID;                           /* SAM���� */	
	ModeCode_t							ModeCode;                           /* ��վ��Ӫģʽ */
	SN_t							    UDSN;                               /* �豸������ˮ�� */	
%//	ePurseTransactionType_t             ePurseTransactionType;          /*Ǯ����������*/
%//	CityCode_t                                                      CityCode_TransLocation;         /*���д��루���׷�����)*/
%//	CityCode_t                                                      CityCode_BelongLocation;         /*���д��루������)*/       
%//	string				               	TACSAMID<20>;                    	/* SAM���� */ 
	TerminateNumber_t				    TerminateNumber;                	/* �ն˻���� */
	SN_t								SAMSN;                    			/* SAM����ˮ��*/
	string								IssueNumber<16>;					/* ������ˮ��*/
	U32_t								WalletTransactionNumber;			/* ����Ǯ���������*/
	U16_t                               CardType;                       	/*������ ֱ��ȡ��������*/      
	Date2_t								ApplicationEnableDate;				/* Ӧ����������*/
	Date2_t								ApplicationValidDate;				/* Ӧ����Ч����*/
%//	U8_t                                SAK;                            	/*����*/    
	   
%// U8_t                            	CardVer;                           	/*���ڰ汾��*/   
%// U32_t                           	RechargeSN;                        	/*��ֵ���  m1ȡ0������CPU�� �ۼƳ�ֵ����*/
%// Date2_t                         	AddValueDate;                     	/*��ֵ���� M1���мӿ����ڣ�CPUȡ001A�ļ�*/
%// Date2_t                         	SaleDate;                           /*�ۿ�����  M1�����������ڣ�CPU��ΪӦ�ÿ�ͨ����*/
%// U32_t                           	SystemTraceSN;                     	/*����������ˮ��*/

}; 

%// ---- Ʊ�������ṹ
struct TicketComm_t{													
	TicketFamilyType_t					TicketFamilyType;                   /*Ʊ����������*/
	TicketType_t						TicketType;                         /*Ʊ�ִ���*/
	TicketCatalogID_t					TicketCatalogID;                    /*Ʊ��Ŀ¼,��д������ʱ��0*/
	TicketPhyID_t						TicketPhyID;                        /*��Ʊ������ */
	string						        TicketLogicID<20>;                  /*Ʊ���߼��Ź���CPU��ȡӦ�����к�8���ֽڣ�M1��ȡ����(���д���2���ֽ�+��ҵ����2���ֽ�+������ˮ��4���ֽ�)*/
	TicketStatus_t						TicketStatus;                       /*Ʊ��״̬ ֱ��ȡƱ���ϵ�״̬*/
	U32_t                                                   Reserved;                           /* Ԥ�� */
	TestFlag_t					        TestFlag;			    			/*���Ա�� һ��ͨ���׸��ֶ�Ϊ0��Ĭ��Ϊ�ǲ���Ʊ*/
	SN_t							TicketSN;                     	/*Ʊ�����׼���(����Ʊָ�������к�,CPU��ָ����Ǯ�������ѻ��������к�,M1ָǮ���ۼƽ��״���)*/
        ValueCent_t						RemainingValue;                     /*Ʊ��ʣ����/����*/   
	
};

%// ---- ���п�Ʊ�������ṹ
struct BankCardTicketComm_t{													
	TicketFamilyType_t					TicketFamilyType;                   /*Ʊ����������*/
	TicketType_t						TicketType;                         /*Ʊ�ִ���*/
	TicketCatalogID_t					TicketCatalogID;                    /*Ʊ��Ŀ¼*/											
	string							PrimaryAccount<32>;		    /*���˻�*/
	string					        	BankCode<32>;			    /*���д���*/
	string                        		                PosNo<32>;                          /*�����ο���*/
	U32_t							TerminNo;                           /*�ն���ˮ��*/  
};

%// ---- ��ά��Ʊ�������ṹ
struct QRCodeTicketComm_t{													
	TicketFamilyType_t					TicketFamilyType;                   /*Ʊ����������*/
	TicketType_t						TicketType;                         /*Ʊ�ִ���*/
	TicketCatalogID_t					TicketCatalogID;                    /*Ʊ��Ŀ¼*/											
	string							Uid<32>;			    /*�û�ID*/
	string					        	QRCodeID<32>;			    /*����*/
};

%// ---- ����Ʊ����ʼ���ṹ
struct SJTInitComm_t{													  
	Date2_t							CardInitDate;                       /*Ʊ����ʼ������*/
	InitBatchCode_t						InitBatchCode;                      /*Ʊ����ʼ������*/
};

%// ---- ����Ʊ���۽ṹ
struct	SJTSale_t{													
	Paymentmeans_t						Paymentmeans;			    /*֧����ʽ*/
	LanguageFlag_t						LanguageFlag;                       /*���Ա��*/ 
	ValueCent_t						TransactionValue;                   /*���׽��*/
	ValueCent_t						TicketPriceValue;                   /*Ʊ�۽��*/
        ValueCent_t						ChangeValue;                        /*������*/
	ValueCent_t						OriginalValue;                      /*ԭʼ��� TVM��BOM�˿�Ͷ��Ľ�� ���ڳ˿͹�Ʊ�������*/
	TransactionStatus_t                                     TransactionStatus;                  /*���׺�״̬*/  
	Date2_t							ValidStartDate;                     /*��Ч��ʼ���� ��ͨ����Ʊ����վƱ������Ʊ��BOM����ֻȡ���ײ���ʱ���������գ�ES���۵���������ƱȡƱ�����ֶ���Ʊʱ�俪ʼ��Ч����*/
	Date2_t							ValidEndDate;                       /*��Ч�������� ��ͨ����Ʊ����վƱ������Ʊ��BOM���۸�ֵͬ��Ч��ʼ���ڣ�ES���۵���������ƱȡƱ�����ֶ���Ʊʱ�������Ч����*/
	OperatorID_t						OperatorID;                         /*����Ա���*/
	BOMShiftID_t						BOMShiftID;                         /*BOM��κ�*/ 
};

%// ---- ��ֵƱ����ʼ���ṹ
struct CPUInitComm_t{													 
	IssuerCode_t						IssuerCode;                         /*����������*/
	Date2_t								CardIssueDate;              /*Ʊ���������ڣ�Ӧ���������ڣ�*/
%//	string						        AppSN<20>;                          /*Ӧ�����к�*/
};

%// ---- ��ֵƱ���۽ṹ
struct CPUCardSale_t{                                                     
	Paymentmeans_t						Paymentmeans;						/*֧����ʽ*/
	LanguageFlag_t						LanguageFlag;                        /*���Ա��*/ 
	ValueCent_t			                        TransactionValue;                   /*���׽�Ѻ��һ��ͨ��ά���ѣ�*/
	TransactionStatus_t                                     TransactionStatus;                  /*���׺�״̬*/  
	Date2_t				                        ValidStartDate;                     /*��Ч��ʼ����*/
	Date2_t				                        ValidEndDate;                       /*��Ч��������*/
	OperatorID_t		                                OperatorID;                         /*����Ա���*/
	BOMShiftID_t                                            BOMShiftID;                         /*BOM��κ�*/ 
};

%// ---- �������˿ͽṹ
struct TicketPassengerComm_t{                                         
	PassengerTypeID_t                      PassengerTypeID;                                      /*�ֿ������ͱ�ʶ*/
    StaffFlag_t                            PassengerStaffFlag;                                   /*�ֿ���ְ�����*/
	UnicodeString_t                        PassengerCNName;	                                     /*����*/
	IdentificationType_t                   IdentificationType;                                   /*֤������*/	
	string                                 IdentificationCode<32>;                               /*֤������*/
};

%// ---- һ��ͨ�˿ͽṹ
struct YKTTicketPassengerComm_t{                                         
	UnicodeString_t                            PassengerCNName;	            /*����*/
	IdentificationType_t                IdentificationType;                 /*֤������*/	
	string                            IdentificationCode<32>;             /*֤������*/
	string							TelephoneCode<32>;                  /*�绰����*/
	UnicodeString_t							Address;                        /*��ַ*/
	UnicodeString_t							CompanyName;                        /*������λ*/
};



%// ---- ��ֵƱ��ֵ�ṹ
struct CPUCardAddValue_t{                                           
	Paymentmeans_t						Paymentmeans;			    /*֧����ʽ*/
	ValueCent_t					        TransactionValue;                   /*��ֵ���*/
	Date2_t							ValidDateBeforeAddValue;            /*����ǰƱ����Ч������*/
	Date2_t							ValidDateAfterAddValue;             /*���׺�Ʊ����Ч������*/
	ValueCent_t						NewRemainingValue;                  /*��ֵ�������*/
%// BankCardPayment_t					        BankCardForCPUAddValue;		    /*���п�֧���ṹ*/
	OperatorID_t		                                OperatorID;                         /*����Ա���*/
	BOMShiftID_t                                            BOMShiftID;                         /*BOM��κ�*/ 
	TerminateNumber_t				        TerminateNumber;                    /* �ն˻���� */
	AFCTime64_t                                             HostTransactionTime;                /*��������ʱ��*/
%//	ValueCent_t					        RealTransactionValue;               /*ʵ�ʽ������Ҫ����ͨ����˾CPU�����˿��Ż����������CPU����ֵͬ��ֵ���*/
};



%// ---- ��ֵƱ�ۿ�ṹ
struct CPUCardDeduction_t{												
	ValueCent_t				TransactionValue;                   /*��ֵ���*/
	OperatorID_t		                OperatorID;                         /*����Ա���*/
	BOMShiftID_t                            BOMShiftID;                         /*BOM��κ�*/ 
};

%// ---- �ֻ����ۿ�ṹ
struct MobileDeduction_t{												
        TicketFamilyType_t					TicketFamilyType;                   /*Ʊ����������*/
	TicketType_t						TicketType;                         /*Ʊ�ִ���*/
	U32_t                                                   Reserved;                           /* Ԥ�� */
	TestFlag_t						TestFlag;			    /*���Ա��*/
	IssuerCode_t						IssuerCode;                         /*����������*/
        SN_t							IssuerSN;			    /*������ˮ��*/
        CityCode_t						CityCode;                           /*���д���*/
        SIMType_t						SIMType;                            /*�ֻ�SIM������*/
	SIMStatus_t						SIMStatus;                          /*�ֻ�SIM��״̬*/
	string							SIMID<32>;                          /*�ֻ�SIM����*/
	string							MobileNo<16>;                       /*�ֻ�����*/
	SN_t							SIMTransSN;                         /*SIM�����׼���*/
        ValueCent_t						TransactionValue;                   /*��ֵ���*/
	SIMStatus_t						SIMStatusAfterTrans;		    /*���׺��ֻ�SIM��״̬*/
        ValueCent_t						RemainingValueAfterTrans;           /*���׺�SIM�������*/
	OperatorID_t		                                OperatorID;                         /*����Ա���*/
	BOMShiftID_t                                            BOMShiftID;                         /*BOM��κ�*/ 
};

%// ---- ���п��ۿ�ṹ
struct BankCardDeduction_t{											
        ValueCent_t						TransactionValue;                   /*��ֵ���*/
	ValueCent_t						RemainingValueAfterTrans;           /*���׺����п������*/
	OperatorID_t		                                OperatorID;                         /*����Ա���*/
	BOMShiftID_t                                            BOMShiftID;                         /*BOM��κ�*/ 
	U32_t                                                   Reserved1;                          /* Ԥ��1 */
	U32_t                                                   Reserved2;                          /* Ԥ��2 */
};


%// ---- ���п����½ṹ
struct BankCardSurcharge_t{	
	ValueCent_t						PreAuthValue;                       /*Ԥ������*/
	string							AuthCode<32>;                       /*��Ȩ��*/	
	SurchargeCode_t						SurchargeCode;                      /*���·�ʽ*/
	Paymentmeans_t		                                Paymentmeans;                       /*֧����ʽ*/
	DeviceID_t                      	                SaleDeviceID;                       /*�����豸�ڵ� ��0*/
	ValueCent_t                   		                TicketSaleValue;                    /*���۽��*/ 
	SurchargeArea_t                                         SurchargeArea;                      /*��������*/ 
	ValueCent_t                    		                SurchargeValue;                     /*���½��*/ 
	OperatorID_t                                            OperatorID;                         /*����Ա���*/ 
	BOMShiftID_t                                            BOMShiftID;                         /*BOM��κ�*/	
	U32_t                        		                ReserveOne;                         /*�Զ�����1*/
	U32_t                        		                ReserveTwo;                         /*�Զ�����2*/  	
};

%// ---- ���п���վ�ṹ
struct BankCardEntry_t{											
        ValueCent_t						PreAuthValue;                       /*Ԥ������*/	
	string		                		        AuthCode<32>;                       /*��Ȩ��*/	
	U32_t                        		                ReserveOne;                         /*�Զ�����1*/ 
	U32_t                        		                ReserveTwo;                         /*�Զ�����2*/ 
};

%// ---- ���п���վ�ṹ
struct BankCardExit_t{											
        ValueCent_t						TransValue;                   	    /*�Ż�ǰ���׽��*/
	DeviceID_t		                	        StartDevice;                        /*��վ�豸���*/
	AFCTime64_t                        	                StartTime;                          /*��վʱ��*/	 
	U32_t                        		                ReserveOne;                         /*�Զ�����1*/
	U32_t                        		                ReserveTwo;                         /*�Զ�����2*/ 	
};

%// ---- ��ά���վ�ṹ
struct QRCodeExit_t{											
        ValueCent_t					       TransValue;                   	     /*���׽��*/
	DeviceID_t		                	       StartDevice;                    	     /*��վ�豸���*/
	AFCTime64_t                        	               StartTime;                      	     /*��վʱ��*/	 
	U32_t                        		               ReserveOne;                     	     /*�Զ�����1*/
	U32_t                        		               ReserveTwo;                     	     /*�Զ�����2*/ 	
};

%// ---- ��ά����½ṹ
struct QRCodeSurcharge_t{	
        SurchargeCode_t						SurchargeCode;                      /*���·�ʽ*/
	Paymentmeans_t		                                Paymentmeans;                       /*֧����ʽ*/
	DeviceID_t                      	                SaleDeviceID;                       /*�����豸�ڵ�*/
	ValueCent_t                   		                TicketSaleValue;                    /*���۽��*/ 
	SurchargeArea_t                                         SurchargeArea;                      /*��������*/ 
	ValueCent_t                    		                SurchargeValue;                     /*���½��*/ 
	OperatorID_t                                            OperatorID;                         /*����Ա���*/ 
	BOMShiftID_t                                            BOMShiftID;                         /*BOM��κ�*/	
	U32_t                        		                ReserveOne;                         /*�Զ�����1*/
	U32_t                        		                ReserveTwo;                         /*�Զ�����2*/  	
};




%// ---- ����Ʊ��Ʊ�ṹ
struct SJTRefund_t{													
	ValueCent_t				PriceValue;			    /*Ʊ������ʱ���*/
	RefundReason_t				RefundReason;                       /*��Ʊԭ��*/
        ValueCent_t				TransactionValue;		    /*ʵ����Ʊ���*/
	OperatorID_t		                OperatorID;                         /*����Ա���*/
	BOMShiftID_t                            BOMShiftID;                         /*BOM��κ�*/ 
};

%// ---- ��ֵƱ��ʱ��Ʊ�ṹ
struct CPUCardImmediateRefund_t{                                       
	Date2_t					ValidDate;                          /*Ʊ����Ч������*/
	ValueCent_t				RemainingValue;                     /*Ʊ�����/��*/
	ValueCent_t				CardDepositValue;                   /*Ʊ��Ѻ��*/
	RefundReason_t				RefundReason;                       /*��Ʊԭ��*/
	ValueCent_t				CardDepreciationValue;              /*Ʊ���۾ɷ�*/
	ValueCent_t				ChargeValue;                        /*��Ʊ������*/
        ValueCent_t				TransactionValue;                   /*ʵ����Ʊ���*/
	OperatorID_t		                OperatorID;			    /*����Ա���*/
	BOMShiftID_t                            BOMShiftID;                         /*BOM��κ�*/ 
};

%// ---- ��ֵƱ�Ǽ�ʱ��Ʊ�ṹ
struct CPUCardNonImmediateRefund_t{                                       
	AFCTime64_t						ConfirmTimeAtAcc;                   /*ACCȷ��ʱ��*/
        OperatorID_t						OperatorIDAtAcc;                    /*ACCȷ����*/   
        string							ApplyBillNo<20>;                    /*���뵥��*/
	UnicodeString_t                                         PassengerCNName;	            /*����*/
        Gender_t						Gender;                             /*�Ա�*/
	IdentificationType_t				        IdentificationType;                 /*֤������*/	
	string							IdentificationCode<32>;             /*֤������*/
	string							TelephoneCode<32>;                  /*�绰����*/
	UnicodeString_t						Address;                            /*��ַ*/
	TicketStatus_t						AccTicketStatus;					/*ACCƱ��״̬*/
	Date2_t							ValidDateAtACC;                     /*ACCȷ��Ʊ����Ч������*/
	ValueCent_t						RemainingValueAtACC;                /*ACCȷ�����/��*/
	ValueCent_t						CardDepositValueAtACC;              /*ACCȷ��Ѻ��*/
	RefundReason_t						RefundReason;                       /*��Ʊԭ��*/
	ValueCent_t						CardDepreciationValue;              /*Ʊ���۾ɷ�*/
	ValueCent_t						ChargeValue;                        /*��Ʊ������*/
        ValueCent_t						TransactionValue;                   /*ʵ����Ʊ���*/
	OperatorID_t		                                OperatorID;			    /*����Ա���*/
	BOMShiftID_t                                            BOMShiftID;                         /*BOM��κ�*/ 
};

%// ---- ��ֵƱ���ڽṹ
struct CPUCardValidityPeriod_t{											
        Date2_t							OldValidDate;                       /*Ʊ��ԭ��Ч������*/
	Date2_t							NewValidDate;                       /*Ʊ������Ч������*/
        ValueCent_t						TransactionValue;                   /*������*/
	OperatorID_t		                                OperatorID;                         /*����Ա���*/
	BOMShiftID_t                                            BOMShiftID;                         /*BOM��κ�*/ 
};

%// ---- ��ֵƱ�����ṹ
struct CPUCardBlock_t{													
        BlockReasonCode_t			BlockReasonCode;                    /*����ԭ��*/ 
	OperatorID_t		                OperatorID;                         /*����Ա���*/
	BOMShiftID_t                            BOMShiftID;                         /*BOM��κ�*/ 
};

%// ---- ��ֵƱ�����ṹ
struct CPUCardUnblock_t{												
	UnblockReasonCode_t			UnblockReasonCode;	            /*����ԭ��*/
	OperatorID_t		                OperatorID;                         /*����Ա���*/
	BOMShiftID_t                            BOMShiftID;                         /*BOM��κ�*/ 
};

%// ---- Ʊ�����½ṹ
struct TicketSurcharge_t{											
	SurchargeCode_t						SurchargeCode;                      /*���·�ʽ*/
        Paymentmeans_t						Paymentmeans;			    /*֧����ʽ*/
        TransactionStatus_t					TransStatusBeforeTrans;		    /*����ǰƱ������״̬*/
	TransactionStatus_t					TransStatusAfterTrans;		    /*���׺�Ʊ������״̬*/
	DeviceID_t						SaleDeviceID;                       /*��Ʊ�豸�ڵ��� ֻӦ���ڵ���Ʊ*/
	ValueCent_t						TicketSaleValue;                    /*���۽�� ֻӦ���ڵ���Ʊ*/
	SurchargeArea_t						SurchargeArea;                      /*��������0-�Ǹ����� 1-��������*/
        ValueCent_t						TransactionValue;                   /*���½��*/
	OperatorID_t		                                OperatorID;                         /*����Ա���*/
	BOMShiftID_t                                            BOMShiftID;                         /*BOM��κ�*/ 
};

%// ---- Ʊ����վ�ṹ
struct TicketEntry_t{													
        TransactionStatus_t					TransStatusBeforeTrans;		    /*����ǰƱ������״̬*/
	TransactionStatus_t					TransStatusAfterTrans;		    /*���׺�Ʊ������״̬*/
        ValueCent_t						RemainingValue;                     /*����ƱΪ���۽�� ��ֵƱ��һ��ͨ���ֻ�ƱΪƱ�������*/
};

%// ---- Ʊ����վ�ṹ
struct TicketExit_t{													
      DeviceID_t						EntryDeviceID;                      /*��վ�豸�ڵ���*/
      AFCTime64_t						EntryTime;                          /*��վʱ��*/
      TransactionStatus_t					TransStatusBeforeTrans;		    /*����ǰƱ������״̬*/
      TransactionStatus_t					TransStatusAfterTrans;		    /*���׺�Ʊ������״̬*/
      ValueCent_t						RemainingValueBeforeTrans;          /*����ǰ�������*/
      ValueCent_t						RemainingValueAfterTrans;           /*���׺������*/
      ValueCent_t						TransactionValue;                   /*���׽��*/
      ValueCent_t						OriginalValue;                      /*Ӧ�ս��*/
      SJTRecycleFlag_t					        SJTRecycleFlag;                     /*����Ʊ���ձ��*/
      DeduceLocation_t					        DeduceLocation;                     /*�۷�λ�ã�0������Ǯ�� 1������ר��Ǯ��1 2������ר��Ǯ��2 3��Ա����վ�����۷� 4������Ʊ��*/
%//   TerminateNumber_t				                TerminateNumber;                    /* �ն˻���� */
%//   SN_t						        SAMSN;                              /* SAM����ˮ�ţ�CPU���ն˽������,M1��0 */
};

%// ---- һ��ͨƱ����վ�ṹ
struct YKTTicketExit_t{													
    DeviceID_t							EntryDeviceID;                      /*��վ�豸�ڵ���*/
    AFCTime64_t							EntryTime;                          /*��վʱ��*/
    TransactionStatus_t					        TransStatusBeforeTrans;				/*����ǰƱ������״̬*/
	TransactionStatus_t					TransStatusAfterTrans;				/*���׺�Ʊ������״̬*/
    ValueCent_t							RemainingValueBeforeTrans;          /*����ǰ�������*/
	ValueCent_t						RemainingValueAfterTrans;           /*���׺������*/
    ValueCent_t							TransactionValue;                   /*���׽��*/
    ValueCent_t							OriginalValue;                      /*Ӧ�ս��*/
};

%// ---- ��վ�����Żݽṹ
struct RebateScheme_t{													
    ConcessionID_t						JoinConcessionID;                   /*�����Żݴ���*/
    JoinConcessionType_t				JoinConcessionType;                 /*�����Żݷ�ʽ*/ 
    ValueCent_t                         JoinConcessionValue;                /*�����Żݽ��*/
    Percentage_t                        JoinConcessionPercentage;           /*�����Żݱ���*/
    ConcessionID_t						PileConcessionID;		    		/*�ۼ��Żݴ���*/
	JoinConcessionType_t                PileConcessionType;                 /*�ۼ��Żݷ�ʽ*/
    ValueCent_t                         PileConcessionValue;                /*�ۻ��Żݽ��*/
    Percentage_t						PileConcessionPercentage;           /*�ۼ��Żݱ���*/
    ValueCent_t                         CurrentBonus;                       /*���ʽ��׻���*/
    ValueCent_t                         AccumulationBonus;                  /*�ۼ��Żݻ���*/
};


%// ---- ����Ʊ���۽��׽ṹ
struct SJTSale_TX{														
	TicketComm_t						TicketComm;				/*Ʊ�������ṹ*/
	SJTInitComm_t						SJTInitComm;				/*����Ʊ����ʼ���ṹ*/
	SJTSale_t						SJTSale;				/*����Ʊ���۽ṹ*/
	U32_t                                                   Reserved1;                              /* Ԥ��1 */
	U32_t                                                   Reserved2;                              /* Ԥ��2 */
};

%// ---- �Ǽ�����ֵ�����۽��׽ṹ
struct CPUSale_TX{														
	TicketComm_t						TicketComm;				/*Ʊ�������ṹ*/
	CPUInitComm_t						CPUInitComm;				/*��ֵƱ����ʼ���ṹ*/
	CPUCardSale_t						CPUCardSale;				/*��ֵƱ���۽ṹ*/
	U32_t                                                   Reserved1;                              /* Ԥ��1 */
	U32_t                                                   Reserved2;                              /* Ԥ��2 */
};

%// ---- ������ֵ�����۽��׽ṹ
struct NamedCPUSale_TX{												
	TicketComm_t						TicketComm;				/*Ʊ�������ṹ*/
	CPUInitComm_t						CPUInitComm;				/*��ֵƱ����ʼ���ṹ*/
	CPUCardSale_t						CPUCardSale;				/*��ֵƱ���۽ṹ*/
	TicketPassengerComm_t				TicketPassengerComm;				/*�������˿ͽṹ*/
	U32_t                                                   Reserved1;                              /* Ԥ��1 */
	U32_t                                                   Reserved2;                              /* Ԥ��2 */
};

%// ---- ��ֵƱ��ֵ���׽ṹ
struct CPUCardAddValue_TX{												
	TicketComm_t						TicketComm;				/*Ʊ�������ṹ*/
	CPUInitComm_t						CPUInitComm;				/*��ֵƱ����ʼ���ṹ*/
	CPUCardAddValue_t					CPUCardAddValue;			/*��ֵƱ��ֵ�ṹ*/
	U32_t                                                   Reserved1;                              /* Ԥ��1 */
	U32_t                                                   Reserved2;                              /* Ԥ��2 */
};

%// ---- ��ֵƱ�ۿ�׽ṹ
struct CPUCardDeduction_TX{												
	TicketComm_t						TicketComm;				/*Ʊ�������ṹ*/
	CPUInitComm_t						CPUInitComm;				/*��ֵƱ����ʼ���ṹ*/
	CPUCardDeduction_t					CPUCardDeduction;			/*��ֵƱ�ۿ�ṹ*/
	U32_t                                                   Reserved1;                              /* Ԥ��1 */
	U32_t                                                   Reserved2;                              /* Ԥ��2 */
};

%// ---- �ֻ����ۿ�׽ṹ
struct MobileDeduction_TX{												
        MobileDeduction_t					MobileDeduction;			/*�ֻ����ۿ�ṹ*/
	U32_t                                                   Reserved1;                              /* Ԥ��1 */
	U32_t                                                   Reserved2;                              /* Ԥ��2 */
};

%// ---- ���п��ۿ�׽ṹ
struct BankCardDeduction_TX{											
        BankCardTicketComm_t				        BankCardTicketComm;			/*���п�Ʊ�������ṹ*/	
	BankCardDeduction_t					BankCardDeduction;			/*���п��ۿ�ṹ*/
};

%// ---- ���п����½��׽ṹ
struct BankCardSurcharge_TX{
	BankCardTicketComm_t				        BankCardTicketComm;					/*���п�Ʊ�������ṹ*/											
        BankCardSurcharge_t				  	BankCardSurcharge;					/*���п����½ṹ*/
};

%// ---- ���п���վ���׽ṹ
struct BankCardEntry_TX{
	BankCardTicketComm_t				        BankCardTicketComm;					/*���п�Ʊ�������ṹ*/											
        BankCardEntry_t						BankCardEntry;						/*���п���վ�ṹ*/
};

%// ---- ���п���վ���׽ṹ
struct BankCardExit_TX{											
	BankCardTicketComm_t				        BankCardTicketComm;					/*���п�Ʊ�������ṹ*/
        BankCardExit_t						BankCardExit;						/*���п���վ�ṹ*/
};


%// ---- ��ά����½��׽ṹ
struct QRCodeSurcharge_TX{
	QRCodeTicketComm_t					QRCodeTicketComm;					/*��ά��Ʊ�������ṹ*/											
        QRCodeSurcharge_t					QRCodeSurcharge;					/*��ά����½ṹ*/
	U32_t                        		                ReserveOne;                      	                /*�Զ�����1*/ 
	U32_t                        		                ReserveTwo;                      	                /*�Զ�����2*/
};

%// ---- ��ά���վ���׽ṹ
struct QRCodeEntry_TX{
	QRCodeTicketComm_t					QRCodeTicketComm;					/*��ά��Ʊ�������ṹ*/											
	U32_t                        		                ReserveOne;                      	                /*�Զ�����1*/ 
	U32_t                        		                ReserveTwo;                      	                /*�Զ�����2*/
};

%// ---- ��ά���վ���׽ṹ
struct QRCodeExit_TX{											
	QRCodeTicketComm_t					QRCodeTicketComm;					/*��ά��Ʊ�������ṹ*/
        QRCodeExit_t						QRCodeExit;						/*��ά���վ�ṹ*/
};


%// ---- ����Ʊ��Ʊ���׽ṹ
struct SJTRefund_TX{												
	TicketComm_t						TicketComm;					/*Ʊ�������ṹ*/
        SJTInitComm_t						SJTInitComm;					/*����Ʊ��ʼ���ṹ*/
	SJTRefund_t						SJTRefund;					/*����Ʊ��Ʊ�ṹ*/
	U32_t                                                   Reserved1;                                      /* Ԥ��1 */
	U32_t                                                   Reserved2;                                      /* Ԥ��2 */
};

%// ---- ��ֵƱ��ʱ��Ʊ���׽ṹ
struct CPUCardImmediateRefund_TX{									
	TicketComm_t						TicketComm;				/*Ʊ�������ṹ*/
	CPUInitComm_t						CPUInitComm;				/*��ֵƱ��ʼ���ṹ*/
	CPUCardImmediateRefund_t			        CPUCardImmediateRefund;			/*��ֵƱ��ʱ��Ʊ�ṹ*/
	U32_t                                                   Reserved1;                              /* Ԥ��1 */
	U32_t                                                   Reserved2;                              /* Ԥ��2 */
};

%// ---- ��ֵƱ�Ǽ�ʱ��Ʊ���׽ṹ
struct CPUCardNonImmediateRefund_TX{								
        TicketComm_t						TicketComm;				/*Ʊ�������ṹ*/
	CPUInitComm_t						CPUInitComm;				/*��ֵƱ��ʼ���ṹ*/
	CPUCardNonImmediateRefund_t			        CPUCardNonImmediateRefund;		/*��ֵƱ�Ǽ�ʱ��Ʊ�ṹ*/
	U32_t                                                   Reserved1;                              /* Ԥ��1 */
	U32_t                                                   Reserved2;                              /* Ԥ��2 */
};

%// ---- ��ֵƱ���ڽ��׽ṹ
struct CPUCardValidityPeriod_TX{									
	TicketComm_t						TicketComm;				/*Ʊ�������ṹ*/
	CPUInitComm_t						CPUInitComm;				/*��ֵƱ��ʼ���ṹ*/
	CPUCardValidityPeriod_t				        CPUCardValidityPeriod;			/*��ֵƱ���ڽṹ*/
	U32_t                                                   Reserved1;                              /* Ԥ��1 */
	U32_t                                                   Reserved2;                              /* Ԥ��2 */
};

%// ---- ��ֵƱ�������׽ṹ
struct CPUCardBlock_TX{												
	TicketComm_t						TicketComm;				/*Ʊ�������ṹ*/
	CPUInitComm_t						CPUInitComm;				/*��ֵƱ��ʼ���ṹ*/
	CPUCardBlock_t						CPUCardBlock;				/*��ֵƱ�����ṹ*/
	U32_t                                                   Reserved1;                              /* Ԥ��1 */
	U32_t                                                   Reserved2;                              /* Ԥ��2 */
};

%// ---- ��ֵƱ�������׽ṹ
struct CPUCardUnblock_TX{											
	TicketComm_t						TicketComm;				/*Ʊ�������ṹ*/
	CPUInitComm_t						CPUInitComm;				/*��ֵƱ��ʼ���ṹ*/
	CPUCardUnblock_t					CPUCardUnblock;				/*��ֵƱ�����ṹ*/
	U32_t                                                   Reserved1;                              /* Ԥ��1 */
	U32_t                                                   Reserved2;                              /* Ԥ��2 */
};

%// ---- Ʊ�����½��׽ṹ
struct TicketSurcharge_TX{											
        TicketComm_t						TicketComm;				/*Ʊ�������ṹ*/
	TicketSurcharge_t					TicketSurcharge;			/*Ʊ�����½ṹ*/
	U32_t                                                   Reserved1;                              /* Ԥ��1 */
	U32_t                                                   Reserved2;                              /* Ԥ��2 */
};

%// ---- Ʊ����վ���׽ṹ
struct TicketEntry_TX{													
        TicketComm_t						TicketComm;				/*Ʊ�������ṹ*/
	TicketEntry_t						TicketEntry;				/*Ʊ����վ�ṹ*/
	U32_t                                                   Reserved1;                              /* Ԥ��1 */
	U32_t                                                   Reserved2;                              /* Ԥ��2 */
};

%// ---- Ʊ����վ���׽ṹ
struct TicketExit_TX{												
        TicketComm_t						TicketComm;				/*Ʊ�������ṹ*/
	TicketExit_t						TicketExit;				/*Ʊ����վ�ṹ*/
	RebateScheme_t						RebateScheme;				/*��վ�����Żݽṹ*/
	U32_t                                                   Reserved1;                              /* Ԥ��1 */
	U32_t                                                   Reserved2;                              /* Ԥ��2 */
};

%// ---- һ��ͨ��վ���׽ṹ
struct YKTTicketExit_TX{												
    TicketComm_t						TicketComm;						/*Ʊ�������ṹ*/
	YKTTicketExit_t						TicketExit;						/*һ��ͨƱ����վ�ṹ*/
	RebateScheme_t						RebateScheme;					/*��վ�����Żݽṹ*/
	U32_t                               Reserved1;                      /* Ԥ��1 */
	U32_t                               Reserved2;                      /* Ԥ��2 */
};




%// ---- һ��ͨ����
struct YKTSale_TX{													
        TicketComm_t						TicketComm;					/*Ʊ�������ṹ*/
	CPUCardSale_t						CPUCardSale;					/*��ֵƱ�����ṹ*/
	YKTTicketPassengerComm_t                                YKTTicketPassengerComm;                         /*һ��ͨ�˿ͽṹ*/
};
%// ---------------------------------------------------------------------------------------


%// ---------------------------------------------------------------------------------------
%// ---- ��ƼĴ�������
%// ---------------------------------------------------------------------------------------

%// ---- ��ƼĴ��������ṹ
struct ARComm_t{												
	DeviceID_t						        DeviceID;		            /*�豸�ڵ���*/
	ARFileTag_t							ARFileTag;                          /* ��Ʒ�ʽ*/
	SN_t								ACCUDSNStart;                       /*��һ���Ĵ����ļ����������һ�ʽ��׼�¼��UDSN+1*/
	SN_t								ACCUDSNEnd;			    /*��ǰ�Ĵ����ļ��ر�ʱ�����������һ�ʽ��׼�¼��UDSN*/
	SN_t								YKTUDSNStart;                       /*��һ���Ĵ����ļ����������һ�����񿨡����������׼�¼��UDSN+1*/
	SN_t								YKTUDSNEnd;                         /*��ǰ�Ĵ����ļ��ر�ʱ�����������һ�����񿨡����������׼�¼��UDSN*/
	SN_t								MobileUDSNStart;                    /*��һ���Ĵ����ļ����������һ���ֻ�֧�����׼�¼��UDSN+1*/
	SN_t								MobileUDSNEnd;                      /*��ǰ�Ĵ����ļ��ر�ʱ�����������һ���ֻ�֧�����׼�¼��UDSN*/
	SN_t								BankUDSNStart;                      /*��һ���Ĵ����ļ����������һ���ֻ�֧�����׼�¼��UDSN+1*/
	SN_t								BankUDSNEnd;                        /*��ǰ�Ĵ����ļ��ر�ʱ�����������һ���ֻ�֧�����׼�¼��UDSN*/
};

%// ---- բ��Ʊ����Ϣ��ƼĴ���
struct AGMTicketFareInfo_t{												
	TicketType_t						        TicketType;                         /*Ʊ�ִ���*/
	IssuerCode_t						        IssuerCode;                         /*����������*/
	ARCount_t							NbofEntries;		            /*��վ����*/
	ARCount_t							NbofExit;	        	    /*��վ����*/	
	ValueCent_t							ValueCollected;                     /*���׽��*/
	ARCount_t							NbofBlocked;                        /*���ܺ�������*/
};

%// ---- բ���ۺϿ�����ƼĴ���
struct AGMSnapshotInfo_t{													
       ARCount_t							TotalNbofSJTRead;                       /*����Ʊ��д����*/
       ARCount_t							TotalNbofACCCSCRead;                    /*��ֵƱ��д����*/
       ARCount_t							TotalNbofYKTCSCRead;                    /*һ��ͨ��д����*/
       ARCount_t							TotalNbofMobileRead;                    /*�ֻ�����д����*/
       ARCount_t							TotalNbofSJTEntry;                      /*����Ʊ��վ����*/
       ARCount_t							TotalNbofACCCSCEntry;                   /*��ֵƱ��վ����*/
       ARCount_t							TotalNbofYKTCSCEntry;                   /*һ��ͨ��վ����*/
       ARCount_t							TotalNbofMobileEntry;                   /*�ֻ�����վ����*/
       ARCount_t							TotalNbofSJTExit;                       /*����Ʊ��վ����*/
       ARCount_t							TotalNbofACCCSCExit;                    /*��ֵƱ��վ����*/
       ARCount_t							TotalNbofYKTCSCExit;                    /*һ��ͨ��վ����*/
       ARCount_t							TotalNbofMobileExit;                    /*�ֻ�����վ����*/
       ValueCent_t							TotalValueCollectedbySJT;               /*����Ʊ���׽��*/
       ValueCent_t							TotalValueCollectedbyACCCSC;            /*��ֵƱ���׽��*/
       ValueCent_t							TotalValueCollectedbyYKTCSC;            /*һ��ͨ���׽��*/
       ValueCent_t							TotalValueCollectedbyMobile;            /*�ֻ������׽��*/
       ARCount_t							TotalNbofSJTCallback;                   /*����Ʊ��������*/
       ARCount_t							TotalNbofInvalidSJT;                    /*��Ч����Ʊ����*/
       ARCount_t							TotalNbofInvalidACCCSC;                 /*��Ч��ֵƱ����*/
       ARCount_t							TotalNbofInvalidYKTCSC;                 /*��Чһ��ͨ����*/
       ARCount_t							TotalNbofInvalidMobile;                 /*��Ч�ֻ�������*/
       ARCount_t							NbofSJTBlocked;                         /*��������Ʊ����������*/
       ARCount_t							NbofACCCSCBlocked;                      /*������ֵƱ����������*/
       ARCount_t							NbofYKTBlocked;                         /*����һ��ͨ����������*/
       ARCount_t							NbofMobileBlocked;                      /*�����ֻ�������������*/
       ARCount_t							TotalNbofTestEntry;                     /*����Ʊ��վ��*/
       ARCount_t							TotalNbofTestExit;                      /*����Ʊ��վ��*/
       ValueCent_t							TotalValueTestCollectedbySJT;           /*����Ʊ����Ʊ���׽�Ԥ����*/
       ValueCent_t							TotalValueTestCollectedbyACCCSC;        /*��ֵƱ����Ʊ���׽�Ԥ����*/
       ValueCent_t							TotalValueTestCollectedbyYKTCSC;        /*һ��ͨ����Ʊ���׽�Ԥ����*/
       ValueCent_t							TotalValueTestCollectedbyMobile;        /*�ֻ�������Ʊ���׽�Ԥ����*/
       ARCount_t							TotalNbofManualOpeningClosedFlaps;      /*����ǿ�ƴ򿪴���*/
       ARCount_t							TotalNbofIntrusionEntries;              /*��վ�������*/
       ARCount_t							TotalNbofIntrusionExit;                 /*��վ�������*/
       ARCount_t							TotalNbofSJTJams;                       /*����Ʊ��������*/
       ARCount_t							Reserved1;                              /* Ԥ��1 */
       ARCount_t							Reserved2;                              /* Ԥ��2 */
       ARCount_t							Reserved3;                              /* Ԥ��3 */
	
};

%// ---- �Զ���Ʊ�������Ϣ
struct AGMAuditInfo_t{	
	AGMSnapshotInfo_t					AGMSnapshotInfo;					/*�Զ���Ʊ���ۺϿ�����ƼĴ���*/
	AGMTicketFareInfo_t					AGMTicketFareInfo<>;					/*�Զ���Ʊ��Ʊ����Ϣ��ƼĴ���*/
};

%// ---- ���Զ�Ʊ����Ϣ��ƼĴ���
struct BOMTicketFareInfo_t{													
	TicketType_t						TicketType;                             /*Ʊ�ִ���*/
	IssuerCode_t						IssuerCode;                             /*����������*/
	ARCount_t						NbofIssue;                              /*��������*/
	ValueCent_t						TotalValueofIssue;                      /*���۽��*/
	ValueCent_t						TotalValueofDeposit;                    /*Ѻ����*/ 
        ARCount_t						NbofAddValue;                           /*��ֵ����*/
	ValueCent_t						TotalValueofAddValue;	                /*��ֵ���*/
	ARCount_t						NbofSurcharge;                          /*��������*/
	ValueCent_t						TotalValueofSurcharge;                  /*���½��*/
	ARCount_t						NbofImmediateRefund;                    /*��ʱ�˿�����*/
	ValueCent_t						TotalValueofImmediateRefund;	        /*��ʱ��Ʊ���*/
        ValueCent_t						TotalValueofImmediateRefundCharge;      /*��ʱ��Ʊ������*/
	ARCount_t						NbofNonImmediateRefund;                 /*�Ǽ�ʱ��Ʊ����*/
	ValueCent_t						TotalValueofNonImmediateRefund;	        /*�Ǽ�ʱ��Ʊ���*/
        ValueCent_t						TotalValueofNonImmediateRefundCharge;   /*�Ǽ�ʱ��Ʊ������*/
	ValueCent_t						TotalValueofRefundDeposit;              /*��Ѻ����*/
	ARCount_t						NbofBlacklistBlocked;                   /*��������ֹ����*/
	ARCount_t						NbofUnblocked;                          /*��������*/
	ARCount_t						NbofCardLossDeclaration;                /*����ʧ����*/
        ARCount_t						NbofCardLossDeclarationCancel;          /*���������*/
	ARCount_t						NbofTicketValidityPeriodExtension;      /*��������*/
};

%// ---- ���Զ���Ʊ��Ʊ�������ϸ�ṹ
struct TicketCatalogDetails_t {												
	TicketCatalogID_t					TicketCatalogID;			/*Ʊ��Ŀ¼*/
	TicketQuantity_t					RemainingTicketQuantity;                /*Ʊ���������*/
};

%// ---- ���Զ���Ʊ���ۺϿ�����ƼĴ���
struct BOMSnapshotInfo_t{												
	ARCount_t							TotalNbofIssueSJT;                      /*����Ʊ��������*/
	ValueCent_t							TotalValueofIssueSJT;                   /*����Ʊ���۽��*/
        ARCount_t							TotalNbofIssueACCCSC;                   /*��ֵƱ��������*/
	ValueCent_t							TotalValueofIssueACCCSC;                /*��ֵƱ���۽��*/
%//     ValueCent_t							TotalValueofDeposit;                    /*Ѻ����*/
	ARCount_t							NbofAddValue;                           /*��ֵ����*/
	ValueCent_t							TotalValueofAddValue;	                /*��ֵ���*/
	ARCount_t							NbofSurcharge;                          /*��������*/
	ValueCent_t							TotalValueofSurcharge;                  /*���½��*/
        ARCount_t							NbofImmediateRefund;                    /*��ʱ�˿�����*/
	ValueCent_t							TotalValueofImmediateRefund;	        /*��ʱ��Ʊ���*/
        ValueCent_t							TotalValueofImmediateRefundCharge;      /*��ʱ��Ʊ������*/
	ARCount_t							NbofNonImmediateRefund;                 /*�Ǽ�ʱ��Ʊ����*/
	ValueCent_t							TotalValueofNonImmediateRefund;	        /*�Ǽ�ʱ��Ʊ���*/
        ValueCent_t							TotalValueofNonImmediateRefundCharge;   /*�Ǽ�ʱ��Ʊ������*/
        ValueCent_t							TotalValueofRefundDeposit;              /*��Ѻ����*/
	ARCount_t							NbofBlacklistBlocked;                   /*��������ֹ����*/
	ARCount_t							NbofUnblocked;                          /*��������*/
	ARCount_t							NbofCardLossDeclaration;                /*����ʧ����*/
        ARCount_t							NbofCardLossDeclarationCancel;          /*���������*/
	ARCount_t							NbofTicketValidityPeriodExtension;      /*��������*/
        ARCount_t							TotalNbofTestIssueSJT;                  /*���Ե���Ʊ��������*/
	ValueCent_t							TotalValueofTestIssueSJT;               /*���Ե���Ʊ���۽��*/
        ARCount_t							TotalNbofTestIssueACCCSC;               /*���Դ�ֵƱ��������*/
	ValueCent_t							TotalValueofTestIssueACCCSC;            /*���Դ�ֵƱ���۽��*/
        ValueCent_t							TotalValueofTestDeposit;                /*���Դ�ֵƱѺ����*/
	ARCount_t							NbofTestAddValue;                       /*����Ʊ��ֵ����*/
	ValueCent_t							TotalValueofTestAddValue;		/*����Ʊ��ֵ���*/
	ARCount_t							NbofTestSurcharge;                      /*���Ը�������*/
	ValueCent_t							TotalValueofTestSurcharge;              /*���Ը��½��*/
	ARCount_t							NbofTestRefund;                         /*������Ʊ����*/
	ValueCent_t							TotalValueofTestRefund;	                /*������Ʊ���*/
	ARCount_t							TotalNbofValidSJTCSCRead;               /*����Ʊ��Ч��д����*/
	ARCount_t							TotalNbofValidACCCSCRead;               /*��ֵƱ��Ч��д����*/
	ARCount_t							TotalNbofValidYKTCSCRead;               /*һ��ͨ��Ч��д����*/ 
	ARCount_t							Reserved1;                              /* Ԥ��1 */
        ARCount_t							Reserved2;                              /* Ԥ��2 */
        ARCount_t							Reserved3;                              /* Ԥ��3 */
        TicketCatalogDetails_t				                TicketCatalogDetails<>;			/*Ʊ�������ϸ*/
};

%// ---- ���Զ���Ʊ�������Ϣ
struct BOMAuditInfo_t{	
	BOMSnapshotInfo_t					BOMSnapshotInfo;					/*���Զ���Ʊ���ۺϿ�����ƼĴ���*/
	BOMTicketFareInfo_t					BOMTicketFareInfo<>;					/*���Զ���Ʊ��Ʊ����ƼĴ���*/
};

%// ---- �Զ���Ʊ���ۺϿ�����ƼĴ���
struct TVMSnapshotInfo_t{											
    	ARCount_t							TotalNbofStockInSJT;                    /*����Ʊ�������*/
	ARCount_t							TotalNbofStockOutSJT;                   /*����Ʊ��������*/
	ARCount_t							TotalNbofIssuedSJT;                     /*����Ʊ��������*/
	ValueCent_t							TotalValueofIssueSJT;                   /*����Ʊ���۽��*/
	ARCount_t							TotalNbofRejectedSJT;                   /*����Ʊ��Ʊ����*/
        ValueCent_t							TotalValueofOverCashPayment;            /*�ึ���۽��*/
	ValueCent_t							TotalValueofChangeGiven;                /*������*/
	ValueCent_t							TotalValueofCashIn;                     /*�ӱҽ��*/
	ValueCent_t							TotalValueofCashOut;                    /*����Ǯ����*/
        ARCount_t							NbofCashAddValue;                       /*�ֽ��ֵ����*/
	ValueCent_t							TotalValueofCashAddValue;	        /*�ֽ��ֵ���*/
        ARCount_t							NbofBankAddValue;                       /*���п���ֵ����*/
	ValueCent_t							TotalValueofBankAddValue;	        /*���п���ֵ���*/
	ContainerID_t						        ContainerID;                            /*ֽ�ҷϱ�����*/
	ARCount_t							TotalNbofWasteMoney;                    /*ֽ�ҷϱ���ֽ��������*/
%//	ValueCent_t							TotalValueofWasteMoney;                 /*ֽ�ҷϱ���ֽ���ܽ��*/
        
        ARCount_t							TotalNbofCoinInsert;                    /*����Ӳ�Ҹ���*/
	ValueCent_t							TotalValuebyInsertedCoins;              /*Ӳ�ҽ��׽��*/
        ARCount_t							TotalNbofBNAInsert;                     /*����ֽ������*/
	ValueCent_t							TotalValuebyInsertedBanknote;           /*ֽ�ҽ��׽��*/
	ARCount_t							TotalNbofValidSJTCSCRead;               /*����Ʊ��Ч��д����*/
        ARCount_t							TotalNbofValidACCCSCRead;               /*��ֵƱ��Ч��д����*/
	ARCount_t							TotalNbofValidYKTCSCRead;               /*һ��ͨ��Ч��д����*/
 	ARCount_t							Reserved1;                              /* Ԥ��1 */
        ARCount_t							Reserved2;                              /* Ԥ��2 */
        ARCount_t							Reserved3;                              /* Ԥ��3 */
	CashBoxInfo_t                                                   CashBoxInfos<>;                         /*Ǯ����Ϣ�б�*/
};


%// ---- ����Ʊ���������Ϣ
struct SJTAuditInfo_t{
        ValueCent_t                                          SJTFaceValue;                         /* ������ֵ*/
	ARCount_t					     NbofCash;                             /* Ǯ������*/
	ValueCent_t					     TotalValueofCash;	                   /* Ǯ�ҽ��*/
};

%// ---- �Զ���Ʊ��Ʊ�������Ϣ
struct TVMTicketFareInfo_t{
        TicketType_t				              TicketType;                         /*Ʊ�ִ���*/
	IssuerCode_t					      IssuerCode;                         /*����������*/
	ARCount_t					      NbofCashAddValue;                   /*�ֽ��ֵ����*/
	ValueCent_t					      TotalValueofCashAddValue;	          /*�ֽ��ֵ���*/
	ARCount_t					      NbofBankAddValue;                   /*���п���ֵ����*/
	ValueCent_t					      TotalValueofBankAddValue;	          /*���п���ֵ���*/
};

%// ---- �Զ���Ʊ�������Ϣ
struct TVMAuditInfo_t{														
        TVMSnapshotInfo_t                                               TVMSnapshotInfos;
	SJTAuditInfo_t					                SJTAuditInfos<>;			/*����Ʊ������ƼĴ���*/
	TVMTicketFareInfo_t					        TVMTicketFareInfo<>;		        /*�Զ���Ʊ��Ʊ����ƼĴ���*/
};

%// ---- ��ѯ��ֵ��Ʊ����Ϣ�ṹ
struct ISMTicketFareInfo_t{												
	TicketType_t						        TicketType;                             /*Ʊ�ִ���*/
        IssuerCode_t					                IssuerCode;                             /*����������*/
	ARCount_t							NbofAddValueByCash;			/*�ֽ��ֵ����*/
	ValueCent_t							TotalValueofAddValueByCash;             /*�ֽ��ֵ���*/
	ARCount_t							NbofAddValueByBankCard;	                /*���п���ֵ����*/
	ValueCent_t							TotalValueofAddValueByBankCard;         /*���п���ֵ���*/
};

%// ---- ��ѯ��ֵ���ۺϿ��սṹ
struct ISMSnapshotInfo_t{														
    ARCount_t							NbofAddValueByCash;			/*�ֽ��ֵ����*/
	ValueCent_t							TotalValueofAddValueByCash;             /*�ֽ��ֵ���*/
	ARCount_t							NbofAddValueByBankCard;	                /*���п���ֵ����*/
	ValueCent_t							TotalValueofAddValueByBankCard;         /*���п���ֵ���*/
    ContainerID_t						BankNoteBoxID;                          /*ֽ������*/
	ValueCent_t							TotalValueofBankNotebox;                /*ֽ�����ܽ��*/

    ARCount_t							TotalNbofBNAInsert;                     /*����ֽ������*/
	ValueCent_t							TotalValuebyInsertedBanknote;           /*ֽ�ҽ��׽��*/
	ARCount_t							TotalNbofValidACCCSCRead;               /*��ֵƱ��Ч��д����*/
	ARCount_t							TotalNbofValidBankCardRead;             /*���п���Ч��д����*/
	ARCount_t							TotalNbofValidYKTCSCRead;               /*һ��ͨ��Ч��д����*/
	ARCount_t							Reserved1;                              /* Ԥ��1 */
        ARCount_t							Reserved2;                              /* Ԥ��2 */
        ARCount_t							Reserved3;                              /* Ԥ��3 */
	MoneyInBankNoteBox_t                MoneyInBankNoteBox_t<>;                 /*ֽ����Ϣ�б�*/
 };

%// ---- ��ѯ��ֵ�������Ϣ
struct ISMAuditInfo_t{	
	ISMSnapshotInfo_t					ISMSnapshotInfo;					/*��ѯ��ֵ���ۺϿ��սṹ*/
	ISMTicketFareInfo_t					ISMTicketFareInfo<>;					/*��ѯ��ֵ��Ʊ����ƼĴ���*/
};


%// ---- �ֳ�ʽ��Ʊ��Ʊ����Ϣ�ṹ
struct PCATicketFareInfo_t{											
	TicketType_t						TicketType;                             /*Ʊ�ִ���*/
        IssuerCode_t					        IssuerCode;                             /*����������*/
	ARCount_t						NbofEntries;				/*��վ����*/
	ARCount_t						NbofExit;				/*��վ����*/	
	ValueCent_t						ValueCollected;                         /*���׽��*/
	ARCount_t						NbofBlocked;                            /*���ܺ�������*/
};

%// ---- �ֳ�ʽ��Ʊ���ۺϿ��սṹ
struct PCASnapshotInfo_t{													
        ARCount_t							TotalNbofSJTRead;                       /*����Ʊ��д����*/
        ARCount_t							TotalNbofACCCSCRead;                    /*��ֵƱ��д����*/
        ARCount_t							TotalNbofYKTCSCRead;                    /*һ��ͨ��д����*/
        ARCount_t							TotalNbofMobileRead;                    /*�ֻ�����д����*/
        ARCount_t							TotalNbofSJTEntry;                      /*����Ʊ��վ����*/
	ARCount_t							TotalNbofACCCSCEntry;                   /*��ֵƱ��վ����*/
        ARCount_t							TotalNbofYKTCSCEntry;                   /*һ��ͨ��վ����*/
        ARCount_t							TotalNbofMobileEntry;                   /*�ֻ�����վ����*/
        ARCount_t							TotalNbofSJTExit;                       /*����Ʊ��վ����*/
	ARCount_t							TotalNbofACCCSCExit;                    /*��ֵƱ��վ����*/
        ARCount_t							TotalNbofYKTCSCExit;                    /*һ��ͨ��վ����*/
        ARCount_t							TotalNbofMobileExit;                    /*�ֻ�����վ����*/
	ValueCent_t							TotalValueCollectedbySJT;               /*����Ʊ���׽��*/
	ValueCent_t							TotalValueCollectedbyACCCSC;            /*��ֵƱ���׽��*/
	ValueCent_t							TotalValueCollectedbyYKTCSC;            /*һ��ͨ���׽��*/
	ValueCent_t							TotalValueCollectedbyMobile;            /*�ֻ������׽��*/
        ARCount_t							TotalNbofInvalidSJT;                    /*��Ч����Ʊ����*/
	ARCount_t							TotalNbofInvalidACCCSC;                 /*��Ч��ֵƱ����*/
	ARCount_t							TotalNbofInvalidYKTCSC;                 /*��Чһ��ͨ����*/
	ARCount_t							TotalNbofInvalidMobile;                 /*��Ч�ֻ�������*/
        ARCount_t							NbofACCCSCBlocked;                      /*������ֵƱ����������*/
	ARCount_t							NbofYKTBlocked;                         /*����һ��ͨ����������*/
	ARCount_t							NbofMobileBlocked;                      /*�����ֻ�������������*/
	ARCount_t							TotalNbofTestEntry;                     /*����Ʊ��վ��*/
        ARCount_t							TotalNbofTestExit;                      /*����Ʊ��վ��*/
	ValueCent_t							TotalValueTestCollectedbySJT;           /*����Ʊ����Ʊ���׽��*/
	ValueCent_t							TotalValueTestCollectedbyACCCSC;        /*��ֵƱ����Ʊ���׽��*/
	ValueCent_t							TotalValueTestCollectedbyYKTCSC;        /*һ��ͨ����Ʊ���׽��*/
	ValueCent_t							TotalValueTestCollectedbyMobile;        /*�ֻ�������Ʊ���׽��*/
	ARCount_t							Reserved1;                              /* Ԥ��1 */
        ARCount_t							Reserved2;                              /* Ԥ��2 */
        ARCount_t							Reserved3;                              /* Ԥ��3 */
	
};

%// ---- �ֳ�ʽ��Ʊ�������Ϣ
struct PCAAuditInfo_t{	
	PCASnapshotInfo_t					PCASnapshotInfo;					/*�ֳ�ʽ��Ʊ���ۺϿ��սṹ*/
	PCATicketFareInfo_t					PCATicketFareInfo<>;					/*�ֳ�ʽ��Ʊ��Ʊ����ƼĴ���*/
};

%// ---------------------------------------------------------------------------------------

%// ---------------------------------------------------------------------------------------
%// ---- ACC��SC�����ļ�����
%// ---------------------------------------------------------------------------------------

%// ���㳵Ʊ���ṹ
struct TicketStockPointList_t{	
	TicketCatalogID_t                            TicketCatalogID;							/* Ʊ��Ŀ¼��� */ 
	TicketQuantity_t			     TicketQuantity;							/* ��Ʊ���� */ 
	TicketQuantity_t			     InvalidTicketQuantity;						/* ��Ʊ���� */ 
};

%// ��Ʊ������б�ṹ
struct TicketStockInOutDetailList_t{
	string                          BillCode<32>;									/* ���ݱ�� */	
	AFCTime64_t                     OccurTime;									/* ����ʱ�� */
        OperatorID_t                    OperatorID;								        /*  ����Ա  */
        OperatorID_t                    Operator1ID;								        /*  ����Ա  */
	StationID_t			Station1ID;	                                                                /* ��Դ/Ŀ�ĳ�վ��� */
        TicketQuantity_t		AllocateQuantity;								/* �������� */ 
	InOutFlag_t			InOutFlag;									/* ������� */ 
	InOutType_t			InOutType;									/* ��������� */ 
	TicketCatalogID_t               TicketCatalogID;								/* Ʊ��Ŀ¼��� */ 
	TicketQuantity_t		TicketQuantity;									/* ��Ʊ���� */ 
	U32_t                           TicketValue;                                                                    /* ��Ʊ��ֵ*/
	TicketQuantity_t		InvalidTicketQuantity;								/* ��Ʊ���� */ 
};

%// ---- ������嵥�ļ��ṹ
struct TicketStockInOutBillFile_t{													
	LineID_t						LineID;		                                /* ��·��� */
	StationID_t						StationID;	                                /* ��վ��� */
	TicketStockInOutDetailList_t	                        TicketStockInOutDetailList<>;			/* ��Ʊ������б�ṹ */
};

%// ---- ���տ����Ϣ�ļ��ṹ
struct TicketStockSnapshotFile_t{													
	LineID_t					LineID;							/* ��·��� */
	StationID_t					StationID;						/* ��վ��� */		
	Date2_t						BusinessDate;						/* ��Ӫ�� */
	TicketStockPointList_t				TicketStockPointList<>;					/* ��Ʊ���ṹ */
};

%// ��Ʊ�������б�ṹ
struct TicketDifferenceStockList_t{												
	TicketCatalogID_t                               TicketCatalogID;							/* Ʊ��Ŀ¼��� */ 
	TicketQuantity_t				TicketQuantity;								/* SC��Ʊ���� */ 
	TicketQuantity_t				InvalidTicketQuantity;							/* SC��Ʊ���� */ 
	TicketQuantity_t				ACCStatTicketQuantity;							/* ACCͳ�Ƴ�Ʊ���� */ 
	TicketQuantity_t				ACCStatInvalidTicketQuantity;						/* ACCͳ�Ʒ�Ʊ���� */ 
};


%// �����ļ��б�ṹ
struct SettlementFileList_t{														
	string						UDFileName<40>;						/* �����ļ��� */
	RecordsNum_t					UDRecordsNum;						/* ���㽻���� */
	ValueCent_t					TransMoney;						/* ���㽻�׽�� */
};

%// ������ɽ��׼�¼�б�ṹ
struct SettlementDoubtfulRecordList_t{		
        DeviceID_t					DeviceID;                                               /* �豸�ڵ��� */
	SN_t						UDSN;							/* ���׼�¼��ˮ�� */
	FileDealResult_t				DoubtfulType;						/* �������� */
};

%// ��������ļ��б�ṹ
struct SettlementDoubtfulFileList_t{											
	SettlementFileList_t			       SettlementDoubtfulFileList;				/* ��������ļ��б�ṹ */ 
	SettlementStatus_t			       SettlementStatus;					/* �ļ��������� */
	SettlementDoubtfulRecordList_t	               SettlementDoubtfulRecordList<>;				/* ������ɽ��׼�¼�б�ṹ */
};


%// ---- ���׶����ļ��ṹ
struct TransactionAccountFile_t{		
        LineID_t					LineID;					/* ��·��� */
	StationID_t					StationID;				/* ��վ��� */	
        Date2_t						SettlementDate;				/* ������ */
        CardSeries_t					CardSeries;				/* ��ϵ */														
	RecordsNum_t					TotalNbOfUDNormal;			/* �����������׼�¼���� */
	ValueCent64_t					TotalValueOfNormal;			/* �����������׽������ */
	RecordsNum_t					TotalNbOfDoubtful;			/* ������ɽ��׼�¼���� */
	ValueCent64_t					TotalValueOfDoubtful;			/* ������ɽ��׽������ */
	SettlementFileList_t			        SettlementNormalFileList<>;		/* ���������ļ��б�ṹ */ 
	SettlementDoubtfulFileList_t	                SettlementDoubtfulFileList<>;		/* ��������ļ��б�ṹ */ 
};

%// ���ɽ��׼�¼�б�ṹ
struct DoubtfulUDList_t{															
	DeviceID_t					DeviceID;                               /* �豸�ڵ��� */
	SN_t						UDSN;				        /* ���׼�¼��ˮ�� */  
	Boolean_t					DealResultOfDoubtful;		        /* ������ */  
};

%// ���ɵ����ļ��б�ṹ
struct DoubtfulAdjustFileList_t{													
	string						UDFileName<40>;							/* ���ɽ����ļ��� */  
	DoubtfulUDList_t				DoubtfulUDList<>;						/* ���ɽ��׼�¼�б�ṹ */
};

%// ---- ���ɽ��׵����ļ��ṹ
struct TransactionDoubtfulFile_t{											/* ���ɽ��׵����ļ��ṹ */
       LineID_t						LineID;								/* ��·��� */
       StationID_t					StationID;				                        /* ��վ��� */	
       Date2_t						SettlementDate;							/* ������ */
       CardSeries_t				        CardSeries;							/* ��ϵ */			
       RecordsNum_t					TotalNbOfDoubtfulAdjust;					/* ���ɵ������׼�¼���� */
       RecordsNum_t					TotalNbOfAccept;						/* ���ܽ��׼�¼���� */
       ValueCent64_t					TotalValueOfAccept;						/* ���ܽ��׽������ */
       RecordsNum_t					TotalNbOfReject;						/* �ܸ����׼�¼���� */
       ValueCent64_t					TotalValueOfReject;						/* �ܸ����׽������ */ 
       DoubtfulAdjustFileList_t		                DoubtfulAdjustFileList<>;					/* ���ɵ����ļ��б� */ 
};

%// ---- ��������ļ��ṹ
%//struct TransactionClearFile_t{	
%//     LineID_t					LineID;												/* ��·��� */
%//     Date2_t						SettlementDate;											/* ������ */		
%//     CardSeries_t					CardSeries;											/* ��ϵ */												
%//	ValueCent64_t					AddUp;												/* ��·�����ۼ�����         */
%//	ValueCent64_t					Normal;												/* ��·����������           */
%//	ValueCent64_t					DoubtfulAdjust;										/* ��·���ɵ���������       */
%//	ValueCent64_t					SettlementAdjust;									/* ��·�������������       */
%//	ValueCent64_t					NormalChargeOfSell;									/* ��Ʊ�����ʷ����         */
%//	ValueCent64_t					DoubtfulAdjustChargeOfSell;							/* ��Ʊ���ɵ����ʷ����     */
%//	ValueCent64_t					NormalChargeOfAddValue;								/* ��ֵ�����ʷ����         */
%//	ValueCent64_t					DoubtfulAdjustChargeOfAddValue;						/* ��ֵ���ɵ����ʷ����     */
%//	ValueCent64_t					NormalChargeOfSurcharge;							/* ���������ʷ����         */
%//	ValueCent64_t					DoubtfulAdjustChargeOfSurcharge;					/* ���¿��ɵ����ʷ����     */
%//	ValueCent64_t					NormalChargeOfRefund;								/* ��Ʊ�����ʷ����         */
%//	ValueCent64_t					DoubtfulAdjustChargeOfRefund;						/* ��Ʊ���ɵ����ʷ����     */
%//	ValueCent64_t					NormalChargeOfReplacement;							/* ��Ʊ�����ʷ����         */
%//	ValueCent64_t					DoubtfulAdjustChargeOfReplacement;					/* ��Ʊ���ɵ����ʷ����     */
%//	ValueCent64_t					NormalChargeOfAdministrationDeal;					/* �������������ʷ����     */
%//	ValueCent64_t					DoubtfulAdjustChargeOfAdministrationDeal;			/* ����������ɵ����ʷ���� */
%//};

%// --------------------------------------------------------------------------------------------------------------

%// -------------------------------------------------------------------------------------------------------------
%// ---------- ES�����ļ��ͱ��뱨���ļ�
%// -------------------------------------------------------------------------------------------------------------

%// ---- ESƱ�������ṹ
struct ESTicketComm_t{													
	TicketFamilyType_t					TicketFamilyType;                   /*Ʊ����������*/
	TicketType_t						TicketType;                         /*Ʊ�ִ���*/
	TicketCatalogID_t					TicketCatalogID;                    /*Ʊ��Ŀ¼*/
	TicketPhyID_t						TicketPhyID;                        /*��Ʊ������*/
	TicketStatus_t						TicketStatus;                       /*Ʊ��״̬*/
};

%// ---- ����Ʊ��ʼ���ṹ
struct ESSJTInit_t{		
	KeyVersion_t						IssueKeyVersion;		    /*������Կ�汾*/				
	TestFlag_t						TestFlag;			    /*���Ա��*/
	LanguageFlag_t						LanguageFlag;                       /*���Ա��*/ 
	ValueCent_t						TicketValue;			    /*Ʊ����ֵ��Ԥ��ֵ��*/
	Date2_t							ValidStartDate;                     /*��Ч��ʼ����*/
	Date2_t							ValidEndDate;                       /*��Ч��������*/
	OperatorID_t						OperatorID;                         /*����Ա���*/
	Duration_t                                              Duration;                           /*��Ч����*/
};

%// ---- ����Ʊ��ʼ�����׽ṹ
struct ESSJTInit_TX{														
	ESTicketComm_t						ESTicketComm;					/*Ʊ�������ṹ*/
	SJTInitComm_t						SJTInitComm;					/*����Ʊ����ʼ�������ṹ*/
	ESSJTInit_t						ESSJTInit;					/*����Ʊ��ʼ���ṹ*/
};


%// ---- ��ֵƱ��ʼ���ṹ
struct ESCPUCardInit_t{													
	TestFlag_t						TestFlag;			    	/*���Ա��*/
	LanguageFlag_t					LanguageFlag;           	/*���Ա��*/ 
	CityCode_t						CityCode;               	/*���д���*/
	TradeCode_t						TradeCode;			    	/*��ҵ����*/
	Date2_t							CardInitDate;               /*��ʼ������*/
	ValueCent_t						TicketDepositValue;		    /*Ѻ����*/
	Date2_t							CardValidDate;			    /*����Ч����*/
	string						    AppSN<20>;			    	/*Ӧ�����к�*/
	AppVersion_t					AppVersion;			    	/*Ӧ�ð汾*/
%// Date2_t							AppStartDate;            	/*Ӧ����������*/
	Date2_t							AppValidDate;         		/*Ӧ����Ч����*/
    AreaTicketFlag_t                AreaTicketFlag;             /*��Χ��־*/
    BitMap_t                        BitMap1;                    /*��·��վλͼ*/    
	BitMap_t                        BitMap2;                    /*��վλͼ*/     
	ValueCent_t						TicketValue;		        /*Ʊ����ʼ���/��*/
	OperatorID_t					OperatorID;             	/*����Ա���*/
};

%// ---- ��ֵƱ��ʼ�����׽ṹ
struct ESCPUCardInit_TX{														
	ESTicketComm_t						ESTicketComm;						/*Ʊ�������ṹ*/
	CPUInitComm_t						CPUInitComm;						/*��ֵƱ����ʼ�������ṹ*/
	ESCPUCardInit_t						ESCPUCardInit;						/*��ֵƱ��ʼ���ṹ*/
};


%// ---- Ʊ��ע���ṹ
struct ESTicketCancel_t{													
%//	KeyVersion_t						CancelKeyVersion;		    /*ע������Կ�汾*/	
	ValueCent_t						TicketValue;			    /*ע��ʱƱ�����/��*/
	OperatorID_t						OperatorID;                         /*����Ա���*/
};

%// ---- Ʊ��ע�����׽ṹ
struct ESTicketCancel_TX{														
	ESTicketComm_t						ESTicketComm;						/*Ʊ�������ṹ*/
	ESTicketCancel_t					ESTicketCancel;						/*Ʊ��ע���ṹ*/
};


%// ---- Ա������Ϣ�ṹ
struct StaffTicketInfo_t{													
	string    						StaffId<8>;						/*Ա����*/	
	AllowRightType_t					AllowRightType;					        /*Ȩ������*/
	MultiRideNumber_t					MultiRideNumber;					/*��������*/	
	Date2_t							ValidDate;						/*Ա��Ʊ��Ч��*/
};

%// ---- ��ֵƱ���Ի����׽ṹ
struct ESCPUCardPersonal_TX{														
	ESTicketComm_t						ESTicketComm;						/*Ʊ�������ṹ*/
	CPUInitComm_t						CPUInitComm;						/*��ֵƱ����ʼ�������ṹ*/
	ESCPUCardInit_t						ESCPUCardInit;						/*��ֵƱ��ʼ���ṹ*/
	TicketPassengerComm_t				        TicketPassengerComm;				        /*�������˿ͽṹ*/
	StaffTicketInfo_t					StaffTicketInfo;					/*Ա������Ϣ�ṹ*/
};

%// ---- Ԥ��ֵ����Ʊ�����ṹ
struct PrePaySJTOffset_t{													
	TestFlag_t						TestFlag;			    /*���Ա��*/
	LanguageFlag_t						LanguageFlag;                       /*���Ա��*/ 
	TradeCode_t						TradeCode;			    /*��ҵ����*/
	ValueCent_t						PrePayValue;			    /*Ԥ��ֵ���*/
	Date2_t							ValidStartDate;                     /*��Ч��ʼ����*/
	Date2_t							ValidEndDate;                       /*��Ч��������*/
	OperatorID_t						OperatorID;                         /*����Ա���*/
};

%// ---- Ԥ��ֵ����Ʊ�������׽ṹ
struct PrePaySJTOffset_TX{														
	ESTicketComm_t						ESTicketComm;			    /*Ʊ�������ṹ*/
	SJTInitComm_t						SJTInitComm;			    /*����Ʊ����ʼ�������ṹ*/
	PrePaySJTOffset_t					PrePaySJTOffset;		    /*Ԥ��ֵ����Ʊ�����ṹ*/
};


%// ---- ��Ʊ������Ϣ�б�ṹ
struct TicketEncodingList_t{																
	AFCTime64_t					    EncodingTime;			   /* ����ʱ��  */
	TicketPhyID_t					    TicketPhyID;			   /* ��Ʊ���к� */
	string						    AppSN<20>;				   /*Ӧ�����к�*/
};

%// ---- ��Ʊ���뱨���ļ��ṹ
struct TicketEncodingReportFile_t{	
	OperatorID_t					OperatorID;					/* ����Ա���   */
	TaskID_t					TaskID;						/* �����ʶ   */
	TaskType_t					TaskType;					/* ��������   */
	TicketType_t					TicketType;					/* Ʊ�ִ��� */
	TicketEncodingList_t			        TicketEncodingList<>;				/* ��Ʊ������Ϣ�б�ṹ */
};

%// --------------------------------------------------------------------------------------------------------------

%#endif 
%/********************************************** �ļ����� *******************************************************/
