package com.insigma.commons.dic;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.DicItem;

public class ModuleCode extends Definition {
	// 1000：第一位预留,第二位子系统编号，第三位和第四位为子系统中各模块逻辑编号
	// ------------后台处理子系统-----------------
	// 通信前置模块编号
	@DicItem
	public static Integer MODULE_COMM = 1400;

	// 数据处理 - 文件处理模块编号
	@DicItem
	public static Integer MODULE_FILE_HANDLE = 1401;

	// 数据处理 - 文件打包模块编号
	@DicItem
	public static Integer MODULE_FILE_PACK = 1402;

	// -------------------------------------------

	// BOM模块
	@DicItem
	public static Integer MODULE_BOM = 1100;

	// 其它模块
	@DicItem
	public final static Integer MODULE_OTHER = 1900;

	// 文件服务模块编号基数
	@DicItem
	public static Integer FILE_SERVER_BASIC = 1203;

	// ------------------------------
	// ------------计划任务管理子系统-----------------
	// 计划任务
	@DicItem
	public static Integer MODULE_TASK = 6108;
	// 计划任务加载
	@DicItem
	public static Integer MODULE_TASK_LOAD = 610801;
	// 计划任务明细
	@DicItem
	public static Integer MODULE_TASK_DETAIL = 610802;

	// 计划任务新增
	@DicItem
	public static Integer MODULE_TASK_NEW = 610803;

	// 计划任务新增
	@DicItem
	public static Integer MODULE_TASK_NEWNOW = 610804;
	// 计划任务删除
	@DicItem
	public static Integer MODULE_TASK_DEL = 610810;

	// 计划任务启动
	@DicItem
	public static Integer MODULE_TASK_START = 610820;

	// 计划任务停止
	@DicItem
	public static Integer MODULE_TASK_STOP = 610821;
}
