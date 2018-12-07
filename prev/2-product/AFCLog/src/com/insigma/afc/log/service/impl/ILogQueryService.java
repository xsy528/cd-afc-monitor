package com.insigma.afc.log.service.impl;

import java.io.File;
import java.util.List;
import java.util.concurrent.Future;

import com.insigma.afc.log.entity.TsyOpLog;
import com.insigma.afc.log.form.LogInfo;
import com.insigma.commons.application.IService;
import com.insigma.commons.op.ImExportResult;

/**
 * 作用：封装了查询日志的数据库查询操作方法（包括日志数量查询、日志实体查询）
 * 
 * @author filefox
 * 
 */
public interface ILogQueryService extends IService {

	/**
	 * 获取日志总数
	 * 
	 * @param form
	 *            设置查询参数
	 * @return
	 */
	public long getOpLogCount(LogInfo form, List<Integer> moduleCode);

	/**
	 * 按页获取日志实体
	 * 
	 * @param form
	 *            设置查询参数
	 * @return
	 */
	public List<TsyOpLog> getOpLogs(LogInfo form, List<Integer> moduleCode, int page, int pageSize);

	/**
	 * 将数据库中的日志到处到CSV文件中
	 * 
	 * @param file
	 * @param form
	 * @param moduleList
	 * @return
	 */
	public Future<ImExportResult> logToCvs(File file, LogInfo form, List<Integer> moduleList);

}
