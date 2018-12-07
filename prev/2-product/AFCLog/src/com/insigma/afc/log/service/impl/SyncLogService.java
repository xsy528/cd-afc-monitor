/* 
 * 日期：2010-6-2 下午04:12:20
 * 描述：预留
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * CHANGELOG
 */
package com.insigma.afc.log.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.insigma.afc.constant.LogDefines;
import com.insigma.afc.log.entity.TsyOpLog;
import com.insigma.commons.application.Application;
import com.insigma.commons.io.CSVWriter;
import com.insigma.commons.io.FileUtil;
import com.insigma.commons.service.ILogService;
import com.insigma.commons.service.Service;
import com.insigma.commons.util.lang.DateTimeUtil;

/**
 * 默认同步记录日志 Ticket #
 * 
 * @author Lin Yunzhi
 * @date 2010-6-2
 * @description
 */
public class SyncLogService extends Service implements ILogService {

	// 线路编号
	private int lineId;

	// 车站编号
	private int stationId;

	// 节点编号
	private long nodeId;

	// IP地址
	private String ipAddress;

	/**
	 * 模块：比如收益管理、参数管理、通信管理等
	 */
	private int module;

	public int getLineId() {
		return lineId;
	}

	public void setLineId(int lineId) {
		this.lineId = lineId;
	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public long getNodeId() {
		return nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public int getModule() {
		return module;
	}

	public void setModule(int module) {
		this.module = module;
	}

	/**
	 * 录入错误日志
	 * 
	 * @param message
	 */
	public void doBizErrorLog(String message) {
		logger.error(message);
		log(LogDefines.ERROR_LOG, message);
	}

	/**
	 * 录入错误日志
	 * 
	 * @param message
	 * @param exception
	 */
	public void doBizErrorLog(String message, Throwable exception) {
		logger.error(message, exception);
		log(LogDefines.ERROR_LOG, message);
	}

	/**
	 * 录入正常日志
	 * 
	 * @param message
	 */
	public void doBizLog(String message) {
		logger.info(message);
		log(LogDefines.NORMAL_LOG, message);
	}

	/**
	 * 录入警报日志
	 * 
	 * @param message
	 */
	public void doBizWarningLog(String message) {
		logger.warn(message);
		log(LogDefines.WARNING_LOG, message);
	}

	/**
	 * 录入警报日志
	 * 
	 * @param message
	 * @param exception
	 */
	public void doBizWarningLog(String message, Throwable exception) {
		logger.warn(message, exception);
		log(LogDefines.WARNING_LOG, message);
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		if (ipAddress.trim().equalsIgnoreCase("127.0.0.1")) {
			try {
				//只适合于windows。其他系统需要配置ip。
				ipAddress = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.ipAddress = ipAddress;
	}

	/**
	 * 打印log日志，并把相关的信息存入数据库
	 * 
	 * @param logClass
	 * @param message
	 */
	private void log(short logClass, String message) {
		TsyOpLog opLog = getOpLog(logClass, message);
		opLog = checkLog(opLog);
		if (opLog == null) {
			logger.debug("不期望的日志实体 ： null");
			return;
		}
		try {
			commonDAO.saveObj(opLog);
		} catch (Exception e) {
			logger.error("保存日志文件失败", e);
			logErrorToCSV(opLog);
		}
	}

	/**
	 * 组装log日志数据库实体类
	 * 
	 * @param logClass
	 * @param message
	 * @return
	 */
	private TsyOpLog getOpLog(short logClass, String message) {
		TsyOpLog opLog = new TsyOpLog();
		opLog.setLineId((short) lineId);
		opLog.setStationId(stationId);
		opLog.setNodeId(nodeId);
		opLog.setIpAddress(ipAddress);
		opLog.setLogClass(logClass);
		opLog.setLogType((short) 0);
		opLog.setModuleCode(module);
		opLog.setOccurTime(DateTimeUtil.getNow());
		opLog.setLogDesc(message);
		opLog.setOperatorId(Application.getUser() != null ? Application.getUser().getUserID() : "0");
		opLog.setUploadStatus(TsyOpLog.UNCREATEFILE);
		//        log.debug("发生时间为 : "
		//                + DateTimeUtil.formatDateToString(opLog.getOccurTime(),
		//                        "yyyy-MM-dd HH:mm:ss"));
		//      log.debug(ReflectionToStringBuilder.toString(opLog));
		return opLog;
	}

	/**
	 * 检查log日志描述是否超过250字，若超过则截断前250字保存数据库，超出部分保存到CSV文件
	 * 
	 * @param systemLog
	 * @return
	 */
	private TsyOpLog checkLog(TsyOpLog systemLog) {
		byte[] descBytes;
		String description = systemLog.getLogDesc();
		try {
			descBytes = description.getBytes("utf-8");
			if (descBytes.length >= 250) {
				logger.warn("描述过长，截断前250保存到数据库，其余保存在CSV文件中：" + description);
				byte[] subDescBytes = new byte[250];
				System.arraycopy(descBytes, 0, subDescBytes, 0, 250);
				String subDesc = new String(subDescBytes, "utf-8");
				logger.debug("描述过长，截断后：" + subDesc);
				systemLog.setLogDesc(subDesc);
				systemLog.setUploadStatus(TsyOpLog.CREATEFILE);
				logErrorToCSV(systemLog);
				return systemLog;
			}
		} catch (UnsupportedEncodingException e1) {
			logger.warn("转化字符个数时出错", e1);
		}

		if (!description.substring(description.length() - 1, description.length()).equals("。")
				&& !description.substring(description.length() - 1, description.length()).equals("\n")) {
			description += "。";
		}
		systemLog.setLogDesc(description);
		return systemLog;
	}

	/**
	 * 记录错误日志到CSV文件
	 * 
	 * @param msgtype
	 * @param description
	 * @param data
	 * @throws IOException
	 */
	public void logErrorToCSV(TsyOpLog systemLog) {
		Writer wr = null;
		CSVWriter writer = null;
		BufferedWriter br = null;
		try {
			String[] nextLine = new String[] { String.valueOf(systemLog.getLineId()),
					String.valueOf(systemLog.getStationId()), String.valueOf(systemLog.getNodeId()),
					String.valueOf(systemLog.getLogType()), DateTimeUtil.getCurrentDateInMinString(),
					String.valueOf(systemLog.getOperatorId()), systemLog.getLogDesc(),
					String.valueOf(systemLog.getLogClass()), String.valueOf(systemLog.getModuleCode()),
					String.valueOf(systemLog.getNodeId()) };
			String applicationPath = System.getProperty("user.dir");
			if (applicationPath == null) {
				return;
			}
			File dir = new File(applicationPath);
			if (!dir.exists() || !dir.isDirectory()) {
				FileUtil.createFolder(applicationPath);
			}
			File csvFile = new File(applicationPath, "ERRORFILE.CSV");
			logger.debug("CSV文件" + (csvFile.exists() ? "存在" : "不存在"));
			wr = new FileWriter(csvFile, true);
			br = new BufferedWriter(wr);
			writer = new CSVWriter(br);
			writer.writeNext(nextLine);
		} catch (Exception e) {
			logger.error("记录日志到CSV文件时错!记录到文件", e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error("关闭文件流异常。", e);
				}
			}

		}
	}

}
