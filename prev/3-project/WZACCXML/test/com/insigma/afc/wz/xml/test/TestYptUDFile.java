/* 
 * 日期：2016年5月10日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.wz.xml.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.junit.Test;

import com.distinct.rpc.XDRInputStream;
import com.distinct.rpc.XDROutputStream;
import com.insigma.acc.wz.xdr.WZXDRDecoder;
import com.insigma.acc.wz.xdr.WZXDREncoder;
import com.insigma.acc.wz.xdr.comm.SLEMessage_t;
import com.insigma.acc.wz.xdr.comm.SLEYPTUDMSG_t;
import com.insigma.acc.wz.xdr.typedef.FileHeaderTag_t;
import com.insigma.acc.wz.xdr.typedef.TerminateNumber_t;
import com.insigma.acc.wz.xdr.typedef.TransactionType_t;
import com.insigma.acc.wz.xdr.ud.BankCardPayment_t;
import com.insigma.acc.wz.xdr.ud.CPUCardAddValue_TX;
import com.insigma.acc.wz.xdr.ud.CPUCardAddValue_t;
import com.insigma.acc.wz.xdr.ud.CPUCardBlock_TX;
import com.insigma.acc.wz.xdr.ud.CPUCardBlock_t;
import com.insigma.acc.wz.xdr.ud.CPUCardDeduction_TX;
import com.insigma.acc.wz.xdr.ud.CPUCardDeduction_t;
import com.insigma.acc.wz.xdr.ud.CPUCardImmediateRefund_TX;
import com.insigma.acc.wz.xdr.ud.CPUCardImmediateRefund_t;
import com.insigma.acc.wz.xdr.ud.CPUCardNonImmediateRefund_TX;
import com.insigma.acc.wz.xdr.ud.CPUCardNonImmediateRefund_t;
import com.insigma.acc.wz.xdr.ud.CPUCardSale_t;
import com.insigma.acc.wz.xdr.ud.CPUCardUnblock_TX;
import com.insigma.acc.wz.xdr.ud.CPUCardUnblock_t;
import com.insigma.acc.wz.xdr.ud.CPUCardValidityPeriod_TX;
import com.insigma.acc.wz.xdr.ud.CPUCardValidityPeriod_t;
import com.insigma.acc.wz.xdr.ud.CPUInitComm_t;
import com.insigma.acc.wz.xdr.ud.CPUSale_TX;
import com.insigma.acc.wz.xdr.ud.NamedCPUSale_TX;
import com.insigma.acc.wz.xdr.ud.RebateScheme_t;
import com.insigma.acc.wz.xdr.ud.SJTInitComm_t;
import com.insigma.acc.wz.xdr.ud.SJTRefund_TX;
import com.insigma.acc.wz.xdr.ud.SJTRefund_t;
import com.insigma.acc.wz.xdr.ud.SJTSale_TX;
import com.insigma.acc.wz.xdr.ud.SJTSale_t;
import com.insigma.acc.wz.xdr.ud.SLEDataFileHeader_t;
import com.insigma.acc.wz.xdr.ud.SLEDataFile_t;
import com.insigma.acc.wz.xdr.ud.SLEDataFile_t_Decoder;
import com.insigma.acc.wz.xdr.ud.SLEDataFile_t_Encoder;
import com.insigma.acc.wz.xdr.ud.TicketComm_t;
import com.insigma.acc.wz.xdr.ud.TicketEntry_TX;
import com.insigma.acc.wz.xdr.ud.TicketEntry_t;
import com.insigma.acc.wz.xdr.ud.TicketExit_TX;
import com.insigma.acc.wz.xdr.ud.TicketExit_t;
import com.insigma.acc.wz.xdr.ud.TicketPassengerComm_t;
import com.insigma.acc.wz.xdr.ud.TicketSurcharge_TX;
import com.insigma.acc.wz.xdr.ud.TicketSurcharge_t;
import com.insigma.acc.wz.xdr.ud.UDComm_t;
import com.insigma.commons.util.lang.DateTimeUtil;

public class TestYptUDFile {

	//	 @Test
	//	 public void testSLEARMSG_t(){
	//
	//	 testModel(SLEARMSG_t.class, "com.insigma.afc.sjz.xml.sle");
	//
	//	 }

	@Test
	public void testSLEYPTUDMSG_t() {

		testModel(SLEYPTUDMSG_t.class, "com.insigma.acc.wz.xdr.comm");

		testDecode(SLEYPTUDMSG_t.class, "com.insigma.acc.wz.xdr.comm");

	}

	private void testDecode(Class<?> clazz, String packageName) {

		String filePath = System.getProperty("user.dir");

		String fileName = "UA05010100002017062913412201.000001";

		// decode
		SLEDataFile_t file_t = decodeSLEDataFile_t(filePath, fileName);

		SLEMessage_t fileBodyData = file_t.getSLEMessages();
		//List<SLEMessage_t> fileBodyData = file_t.getSLEMessages();

		System.out.println(fileBodyData);
	}

	private void testModel(Class<?> clazz, String packageName) {

		String filePath = System.getProperty("user.dir");

		String fileName = "UA05010100002017062913412201.000001";

		String fileName2 = "UA05010100002017062913412201.000002";

		SLEDataFile_t file = fileGenrateBean(clazz, packageName);
		System.out.println(ReflectionToStringBuilder.toString(file));

		// encode
		encodeSLEDataFile_t(filePath, fileName, file);

		// decode
		SLEDataFile_t file_t = decodeSLEDataFile_t(filePath, fileName);

		// 反向encode
		encodeSLEDataFile_t(filePath, fileName2, file_t);

		// 反向decode
		SLEDataFile_t file_t2 = decodeSLEDataFile_t(filePath, fileName2);

		// 比较实体的各个域是否一致
		boolean result = EntityUtil.deepCompare(file_t2, file_t);
		if (!result) {
			System.err.println("编解码不一致。");
		}

		// 比较两个文件的字节流是否一致
		byte[] original = getDataFromFile(filePath, fileName);
		byte[] original2 = getDataFromFile(filePath, fileName2);
		for (int i = 0; i < original.length; i++) {
			if (original[i] != original2[i]) {
				System.out.println(
						"总长：" + original.length + ">>>" + original[i] + "<-序列:" + (i + 1) + "->" + original2[i]);
			}
		}

	}

	private byte[] getDataFromFile(String filePath, String fileName) {
		try {
			return FileUtils.readFileToByteArray(new File(filePath + File.separator + fileName));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private SLEDataFile_t fileGenrateBean(Class<?> clazz, String packageName) {
		SLEDataFile_t file = new SLEDataFile_t();
		// 设备运营文件头结构 
		SLEDataFileHeader_t fileHead = new SLEDataFileHeader_t();

		fileHead.setFileHeaderTag(FileHeaderTag_t.FILE_YPT_TRANSACTION);
		fileHead.setFileVersion((short) 1);
		fileHead.setFileCreationTime(new Timestamp(DateTimeUtil.getNow().getTime()));
		fileHead.setFileName("AAAAAAAAA");
		fileHead.setLineID((short) 01);
		fileHead.setStationID(121);
		fileHead.setDeviceID(16975105);
		fileHead.setBusinessDay(new Date());
		fileHead.setFileSN(12233445);
		fileHead.setRecordsInFile(1);
		file.setSLEDataFileHeader(fileHead);

		file.setMD5Value("AABBCCDDEEFFAABBCCDDEEFFAABBCCDD");

		// 设备消息结构
		SLEMessage_t SLEMessage = new SLEYPTUDMSG_t();
		int[] testValues = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };

		// TODO
		//List<SLEMessage_t> SLEMessages = new ArrayList<SLEMessage_t>();
		for (int i = 0; i < testValues.length; i++) {
			file.setSLEMessages(generateUDBean(testValues[i]));
		}

		return file;
	}

	private UDComm_t generateUDcommBean() {
		UDComm_t comm = new UDComm_t();
		comm.setTransactionType(1);
		comm.setTransactionDateTime(new Timestamp(new Date().getTime()));
		comm.setLineID((short) 1);
		comm.setStationID(259);
		comm.setDeviceID(16975105);
		comm.setTACSAMID(123455678);
		comm.setModeCode((short) 1);
		comm.setUDSN(1);

		return comm;
	}

	private SLEYPTUDMSG_t generateUDBean(int value) {
		SLEYPTUDMSG_t yptudmsg = new SLEYPTUDMSG_t();

		TicketComm_t ticketcomm = new TicketComm_t();
		ticketcomm.setTicketFamilyType(1);
		ticketcomm.setTicketType((short) 3);
		ticketcomm.setTicketCatalogID(1);
		ticketcomm.setTicketPhyID("11111111");
		ticketcomm.setTicketLogicID("212221");
		ticketcomm.setTicketStatus((short) 12);
		ticketcomm.setTestFlag((short) 1);
		ticketcomm.setTicketSN(value);
		ticketcomm.setRemainingValue(value);
		ticketcomm.setSAMSN(1);

		CPUInitComm_t cpuInitComm = new CPUInitComm_t();
		cpuInitComm.setIssuerCode(1);
		cpuInitComm.setCardIssueDate(new Date());

		CPUCardSale_t cpuCardSale = new CPUCardSale_t();
		cpuCardSale.setPaymentmeans((short) 1);
		cpuCardSale.setLanguageFlag((short) 1);
		cpuCardSale.setTransactionValue(1);
		cpuCardSale.setTransactionStatus((short) 1);
		cpuCardSale.setValidStartDate(new Date());
		cpuCardSale.setValidEndDate(new Date());
		cpuCardSale.setOperatorID(111111);
		cpuCardSale.setBOMShiftID(222222);

		SJTInitComm_t sjtInitComm = new SJTInitComm_t();
		sjtInitComm.setCardInitDate(new Date());
		sjtInitComm.setInitBatchCode((short) 1);

		yptudmsg.setUDComm(generateUDcommBean());
		yptudmsg.setTAC(123456789L);
		switch (value) {
		// 单程票发售
		case TransactionType_t.TX_SJTSale:
			SJTSale_TX sjtSaleTx = new SJTSale_TX();
			sjtSaleTx.setTicketComm(ticketcomm);
			sjtSaleTx.setReserved1(111);
			sjtSaleTx.setReserved2(222);
			sjtSaleTx.setSJTInitComm(sjtInitComm);

			SJTSale_t sjtSale = new SJTSale_t();
			sjtSale.setPaymentmeans((short) 1);
			sjtSale.setLanguageFlag((short) 1);
			sjtSale.setTransactionValue(12);
			sjtSale.setTicketPriceValue(13);
			sjtSale.setChangeValue(14);
			sjtSale.setOriginalValue(15);
			sjtSale.setTransactionStatus((short) 1);
			sjtSale.setValidStartDate(new Date());
			sjtSale.setValidEndDate(new Date());
			sjtSale.setOperatorID(111111);
			sjtSale.setBOMShiftID(222222);
			sjtSaleTx.setSJTSale(sjtSale);

			yptudmsg.setYPTUDData(sjtSaleTx);
			break;

		// 非记名储值票发售
		case TransactionType_t.TX_NonNamedCPUSale:
			CPUSale_TX cpuSaleTx = new CPUSale_TX();
			cpuSaleTx.setTicketComm(ticketcomm);
			cpuSaleTx.setReserved1(111);
			cpuSaleTx.setReserved2(222);

			cpuSaleTx.setCPUInitComm(cpuInitComm);
			cpuSaleTx.setCPUCardSale(cpuCardSale);

			yptudmsg.setYPTUDData(cpuSaleTx);
			break;

		// 记名储值票发售
		case TransactionType_t.TX_NamedCPUSale:
			NamedCPUSale_TX namedCPUSale = new NamedCPUSale_TX();
			namedCPUSale.setTicketComm(ticketcomm);
			namedCPUSale.setReserved1(111);
			namedCPUSale.setReserved2(222);

			namedCPUSale.setCPUInitComm(cpuInitComm);
			namedCPUSale.setCPUCardSale(cpuCardSale);

			TicketPassengerComm_t ticketPassengerComm = new TicketPassengerComm_t();
			ticketPassengerComm.setPassengerTypeID((short) 1);
			ticketPassengerComm.setPassengerStaffFlag((short) 1);
			ticketPassengerComm.setPassengerCNName("wang");
			ticketPassengerComm.setIdentificationType((short) 1);
			ticketPassengerComm.setIdentificationCode("11111111");
			namedCPUSale.setTicketPassengerComm(ticketPassengerComm);

			yptudmsg.setYPTUDData(namedCPUSale);
			break;

		// 储值票充值
		case TransactionType_t.TX_CPUCardAddValue:
			CPUCardAddValue_TX cpuCardAddValue = new CPUCardAddValue_TX();
			cpuCardAddValue.setTicketComm(ticketcomm);
			cpuCardAddValue.setReserved1(111);
			cpuCardAddValue.setReserved2(222);

			cpuCardAddValue.setCPUInitComm(cpuInitComm);

			CPUCardAddValue_t cpuCardAddValue_t = new CPUCardAddValue_t();
			cpuCardAddValue_t.setPaymentmeans((short) 1);
			cpuCardAddValue_t.setTransactionValue(1);
			cpuCardAddValue_t.setValidDateBeforeAddValue(new Date());
			cpuCardAddValue_t.setValidDateAfterAddValue(new Date());
			cpuCardAddValue_t.setNewRemainingValue(100);

			BankCardPayment_t bankCardPayment = new BankCardPayment_t();
			bankCardPayment.setBankCode("111");
			bankCardPayment.setBIZCode("222");
			bankCardPayment.setPosNo("333");
			bankCardPayment.setBankCardCode("444");
			bankCardPayment.setBankTransSN(555);
			//			cpuCardAddValue_t.setBankCardForCPUAddValue(bankCardPayment);

			cpuCardAddValue_t.setOperatorID(1);
			cpuCardAddValue_t.setBOMShiftID(1);

			TerminateNumber_t terminateNumber = new TerminateNumber_t();
			terminateNumber.setCityCode(1);
			terminateNumber.setSerialID(1);
			cpuCardAddValue_t.setTerminateNumber(terminateNumber);
			cpuCardAddValue_t.setHostTransactionTime(new Timestamp(DateTimeUtil.getNow().getTime()));

			yptudmsg.setYPTUDData(cpuCardAddValue);
			break;
		// 单程票即时退票
		case TransactionType_t.TX_SJTRefund:
			SJTRefund_TX sjtRefund = new SJTRefund_TX();
			sjtRefund.setTicketComm(ticketcomm);
			sjtRefund.setReserved1(111);
			sjtRefund.setReserved2(222);
			sjtRefund.setSJTInitComm(sjtInitComm);

			SJTRefund_t sjtRefund_t = new SJTRefund_t();
			sjtRefund_t.setPriceValue(1);
			sjtRefund_t.setRefundReason((short) 1);
			sjtRefund_t.setTransactionValue(1);
			sjtRefund_t.setOperatorID(111);
			sjtRefund_t.setBOMShiftID(222);
			sjtRefund.setSJTRefund(sjtRefund_t);

			yptudmsg.setYPTUDData(sjtRefund);
			break;

		// 储值票即时退票
		case TransactionType_t.TX_CPUCardImmediateRefund:
			CPUCardImmediateRefund_TX cpuCardImmediateRefund = new CPUCardImmediateRefund_TX();
			cpuCardImmediateRefund.setTicketComm(ticketcomm);
			cpuCardImmediateRefund.setReserved1(111);
			cpuCardImmediateRefund.setReserved2(222);
			cpuCardImmediateRefund.setCPUInitComm(cpuInitComm);

			CPUCardImmediateRefund_t cpuCardImmediateRefund_t = new CPUCardImmediateRefund_t();
			cpuCardImmediateRefund_t.setValidDate(new Date());
			cpuCardImmediateRefund_t.setRemainingValue(1);
			cpuCardImmediateRefund_t.setCardDepositValue(1);
			cpuCardImmediateRefund_t.setRefundReason((short) 1);
			cpuCardImmediateRefund_t.setCardDepreciationValue(1);
			cpuCardImmediateRefund_t.setChargeValue(1);
			cpuCardImmediateRefund_t.setTransactionValue(1);
			cpuCardImmediateRefund_t.setOperatorID(111);
			cpuCardImmediateRefund_t.setBOMShiftID(222);
			cpuCardImmediateRefund.setCPUCardImmediateRefund(cpuCardImmediateRefund_t);

			yptudmsg.setYPTUDData(cpuCardImmediateRefund);
			break;
		// 	储值票非即时退票
		case TransactionType_t.TX_CPUCardNonImmediateRefund:
			CPUCardNonImmediateRefund_TX cpuCardNonImmediateRefund = new CPUCardNonImmediateRefund_TX();
			cpuCardNonImmediateRefund.setTicketComm(ticketcomm);
			cpuCardNonImmediateRefund.setReserved1(111);
			cpuCardNonImmediateRefund.setReserved2(222);
			cpuCardNonImmediateRefund.setCPUInitComm(cpuInitComm);

			CPUCardNonImmediateRefund_t cpuCardNonImmediateRefund_t = new CPUCardNonImmediateRefund_t();
			cpuCardNonImmediateRefund_t.setConfirmTimeAtAcc(new Timestamp(DateTimeUtil.getNow().getTime()));
			cpuCardNonImmediateRefund_t.setOperatorIDAtAcc(1);
			cpuCardNonImmediateRefund_t.setApplyBillNo("111");
			cpuCardNonImmediateRefund_t.setPassengerCNName("wang");
			cpuCardNonImmediateRefund_t.setGender(1);
			cpuCardNonImmediateRefund_t.setIdentificationType((short) 1);
			cpuCardNonImmediateRefund_t.setIdentificationCode("111");
			cpuCardNonImmediateRefund_t.setTelephoneCode("12324112");
			cpuCardNonImmediateRefund_t.setAccTicketStatus((short) 1);
			cpuCardNonImmediateRefund_t.setValidDateAtACC(new Date());
			cpuCardNonImmediateRefund_t.setRemainingValueAtACC(1);
			cpuCardNonImmediateRefund_t.setCardDepositValueAtACC(1);
			cpuCardNonImmediateRefund_t.setRefundReason((short) 1);
			cpuCardNonImmediateRefund_t.setCardDepreciationValue(1);
			cpuCardNonImmediateRefund_t.setChargeValue(1);
			cpuCardNonImmediateRefund_t.setTransactionValue(1);
			cpuCardNonImmediateRefund_t.setOperatorID(111);
			cpuCardNonImmediateRefund_t.setBOMShiftID(222);
			cpuCardNonImmediateRefund.setCPUCardNonImmediateRefund(cpuCardNonImmediateRefund_t);

			yptudmsg.setYPTUDData(cpuCardNonImmediateRefund);
			break;

		// 储值票延期
		case TransactionType_t.TX_TicketValidityPeriod:
			CPUCardValidityPeriod_TX cpuCardValidityPeriod = new CPUCardValidityPeriod_TX();
			cpuCardValidityPeriod.setTicketComm(ticketcomm);
			cpuCardValidityPeriod.setReserved1(111);
			cpuCardValidityPeriod.setReserved2(222);
			cpuCardValidityPeriod.setCPUInitComm(cpuInitComm);

			CPUCardValidityPeriod_t cpuCardValidityPeriod_t = new CPUCardValidityPeriod_t();
			cpuCardValidityPeriod_t.setOldValidDate(new Date());
			cpuCardValidityPeriod_t.setNewValidDate(new Date());
			cpuCardValidityPeriod_t.setTransactionValue(1);
			cpuCardValidityPeriod_t.setOperatorID(1);
			cpuCardValidityPeriod_t.setBOMShiftID(1);
			cpuCardValidityPeriod.setCPUCardValidityPeriod(cpuCardValidityPeriod_t);

			yptudmsg.setYPTUDData(cpuCardValidityPeriod);
			break;
		// 储值票锁卡
		case TransactionType_t.TX_CPUCardBlock:
			CPUCardBlock_TX cpuCardBlock = new CPUCardBlock_TX();
			cpuCardBlock.setTicketComm(ticketcomm);
			cpuCardBlock.setReserved1(111);
			cpuCardBlock.setReserved2(222);
			cpuCardBlock.setCPUInitComm(cpuInitComm);

			CPUCardBlock_t cpuCardBlock_t = new CPUCardBlock_t();
			cpuCardBlock_t.setBlockReasonCode((short) 1);
			cpuCardBlock_t.setOperatorID(111);
			cpuCardBlock_t.setBOMShiftID(222);
			cpuCardBlock.setCPUCardBlock(cpuCardBlock_t);

			yptudmsg.setYPTUDData(cpuCardBlock);
			break;
		// 储值票解锁	
		case TransactionType_t.TX_CPUCardUnblock:
			CPUCardUnblock_TX cpuCardUnblock = new CPUCardUnblock_TX();
			cpuCardUnblock.setTicketComm(ticketcomm);
			cpuCardUnblock.setReserved1(111);
			cpuCardUnblock.setReserved2(222);
			cpuCardUnblock.setCPUInitComm(cpuInitComm);

			CPUCardUnblock_t cpuCardUnblock_t = new CPUCardUnblock_t();
			cpuCardUnblock_t.setUnblockReasonCode((short) 1);
			cpuCardUnblock_t.setOperatorID(111);
			cpuCardUnblock_t.setBOMShiftID(222);
			cpuCardUnblock.setCPUCardUnblock(cpuCardUnblock_t);

			yptudmsg.setYPTUDData(cpuCardUnblock);
			break;

		// 一票通更新
		case TransactionType_t.TX_TicketSurcharge:
			TicketSurcharge_TX ticketSurcharge = new TicketSurcharge_TX();
			ticketSurcharge.setTicketComm(ticketcomm);
			ticketSurcharge.setReserved1(111);
			ticketSurcharge.setReserved2(222);

			TicketSurcharge_t ticketSurcharge_t = new TicketSurcharge_t();
			ticketSurcharge_t.setSurchargeCode((short) 1);
			ticketSurcharge_t.setPaymentmeans((short) 1);
			ticketSurcharge_t.setTransStatusBeforeTrans((short) 1);
			ticketSurcharge_t.setTransStatusAfterTrans((short) 1);
			ticketSurcharge_t.setSaleDeviceID(1);
			ticketSurcharge_t.setTicketSaleValue(1);
			ticketSurcharge_t.setSurchargeArea((short) 1);
			ticketSurcharge_t.setTransactionValue(1);
			ticketSurcharge_t.setOperatorID(111);
			ticketSurcharge_t.setBOMShiftID(222);
			ticketSurcharge.setTicketSurcharge(ticketSurcharge_t);

			yptudmsg.setYPTUDData(ticketSurcharge);
			break;

		// 一票通进站
		case TransactionType_t.TX_TicketEntry:
			TicketEntry_TX ticketEntry = new TicketEntry_TX();
			ticketEntry.setTicketComm(ticketcomm);
			ticketEntry.setReserved1(111);
			ticketEntry.setReserved2(222);

			TicketEntry_t ticketEntry_t = new TicketEntry_t();
			ticketEntry_t.setTransStatusBeforeTrans((short) 1);
			ticketEntry_t.setTransStatusAfterTrans((short) 1);
			ticketEntry_t.setRemainingValue(1);
			ticketEntry.setTicketEntry(ticketEntry_t);

			yptudmsg.setYPTUDData(ticketEntry);
			break;

		// 一票通出站
		case TransactionType_t.TX_TicketExit:
			TicketExit_TX ticketExit = new TicketExit_TX();
			ticketExit.setTicketComm(ticketcomm);
			ticketExit.setReserved1(111);
			ticketExit.setReserved2(222);

			TicketExit_t ticketExit_t = new TicketExit_t();
			ticketExit_t.setEntryDeviceID(1);
			ticketExit_t.setEntryTime(new Timestamp(DateTimeUtil.getNow().getTime()));
			ticketExit_t.setTransStatusBeforeTrans((short) 1);
			ticketExit_t.setTransStatusAfterTrans((short) 1);
			ticketExit_t.setRemainingValueBeforeTrans(1);
			ticketExit_t.setRemainingValueAfterTrans(1);
			ticketExit_t.setTransactionValue(1);
			ticketExit_t.setOriginalValue(1);
			ticketExit_t.setSJTRecycleFlag((short) 1);
			ticketExit_t.setDeduceLocation((short) 1);
			ticketExit.setTicketExit(ticketExit_t);

			RebateScheme_t rebateScheme = new RebateScheme_t();
			rebateScheme.setJoinConcessionID((short) 1);
			rebateScheme.setJoinConcessionType((short) 1);
			rebateScheme.setJoinConcessionValue(1);
			rebateScheme.setJoinConcessionPercentage((short) 1);
			rebateScheme.setPileConcessionID((short) 1);
			rebateScheme.setPileConcessionValue((short) 1);
			rebateScheme.setPileConcessionPercentage((short) 1);
			rebateScheme.setCurrentBonus(1);
			rebateScheme.setAccumulationBonus(1);
			ticketExit.setRebateScheme(rebateScheme);

			yptudmsg.setYPTUDData(ticketExit);
			break;
		// 储值票扣款（预留）	
		case TransactionType_t.TX_CPUCardDeduction:
			CPUCardDeduction_TX cpuCardDeduction = new CPUCardDeduction_TX();
			cpuCardDeduction.setTicketComm(ticketcomm);
			cpuCardDeduction.setReserved1(111);
			cpuCardDeduction.setReserved2(222);
			cpuCardDeduction.setCPUInitComm(cpuInitComm);

			CPUCardDeduction_t cpuCardDeduction_t = new CPUCardDeduction_t();
			cpuCardDeduction_t.setTransactionValue(1);
			cpuCardDeduction_t.setOperatorID(111);
			cpuCardDeduction_t.setBOMShiftID(222);
			cpuCardDeduction.setCPUCardDeduction(cpuCardDeduction_t);

			yptudmsg.setYPTUDData(cpuCardDeduction);
			break;

		}

		return yptudmsg;
	}

	private void encodeSLEDataFile_t(String filePath, String fileName, SLEDataFile_t file) {
		OutputStream out = null;
		// ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			assertFolder(filePath);
			out = new FileOutputStream(new File(filePath, fileName));
			XDROutputStream outputStream = new XDROutputStream(out);
			WZXDREncoder encoder = new WZXDREncoder(outputStream);
			new SLEDataFile_t_Encoder().encode(encoder, file);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private SLEDataFile_t decodeSLEDataFile_t(String filePath, String fileName) {
		InputStream in = null;
		try {
			in = new FileInputStream(new File(filePath, fileName));
			XDRInputStream inputStream = new XDRInputStream(in);
			WZXDRDecoder decoder = new WZXDRDecoder(inputStream);
			SLEDataFile_t file1 = new SLEDataFile_t_Decoder().decode(decoder);
			return file1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 检查文件夹是否存在，如果不存在则创建
	 * 
	 * @param filePath
	 */
	private static void assertFolder(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
}
