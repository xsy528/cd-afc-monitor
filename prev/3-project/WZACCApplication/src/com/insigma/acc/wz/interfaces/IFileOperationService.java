/* 
 * 日期：2012-4-1
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.interfaces;

import java.io.File;

import com.insigma.commons.application.IService;

/**
 * Ticket: 文件处理接口
 * 
 * @author Zhou-Xiaowei
 */
public interface IFileOperationService extends IService {

	String exportFile();

	boolean importFile(int fileType);

	boolean clearTempFile(int fileType);

	boolean parseFile(File file);

	String getMaxFileName(int fileType);

	String createAuthFile();

	/**
	 * 删除对应的文件
	 * 
	 * @param fileType
	 * @param fileName
	 * @return
	 */
	boolean delteleFile(int fileType, String fileName);

	/**
	 * 印刷卡号同步
	 * 
	 * @param fileType
	 * @param fileName
	 * @return
	 */
	boolean synTicketPrintId(int fileType, String fileName);

	/**
	 * 移动文件
	 * 
	 * @param fileName
	 * @param taskID
	 * @return
	 */
	boolean moveFile(String fileName, Long taskID);

	/**
	 * 清除本地的文件
	 * 
	 * @param fileType
	 * @param fileNameExtension
	 * @return
	 */
	boolean clearLocalFile(int fileType, String fileNameExtension);
	//
	//	/**
	//	 * 一卡通上传重传
	//	 * 
	//	 * @param fileAbsPath
	//	 * @return
	//	 */
	//	boolean yktUpLoadReSeed(String fileAbsPath);
	//
	//	/**
	//	 * 一卡通文件重新下载
	//	 * 
	//	 * @param fileAbsPath
	//	 * @return
	//	 */
	//	boolean yktDownLoadReSeed(String fileAbsPath);

}
