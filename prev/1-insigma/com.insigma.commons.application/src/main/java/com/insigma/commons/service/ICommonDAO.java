package com.insigma.commons.service;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import com.insigma.commons.application.IService;
import com.insigma.commons.collection.PageHolder;
import com.insigma.commons.io.ColumnValuePattern;
import com.insigma.commons.op.ImExportResult;
import com.insigma.commons.op.OPException;
import com.insigma.commons.query.QueryCondition;
import com.insigma.commons.query.QueryFilter;

@SuppressWarnings("unchecked")
public interface ICommonDAO extends IService {

	Serializable saveObj(Object obj) throws OPException;

	void saveObjs(Object objs[]) throws OPException;

	void saveAndUpdateObjs(Object saveAndUpdate[]) throws OPException;

	void updateObj(Object obj) throws OPException;

	void saveOrUpdateObj(Object obj);

	/**
	 * 执行update 或者delete 等HQL语句
	 * 
	 * @param hql
	 *            HQL执行语句
	 * @param objects
	 *            变量绑定参数
	 */
	void executeHQLUpdate(String hql, Object... objects);

	/**
	 * 执行SQL语句
	 * 
	 * @param sql
	 *            sql
	 * @param objects
	 *            变量绑定参数
	 * @return list Object[]对象列表
	 * @throws OPException
	 */
	int execSqlUpdate(String sql, Object... objects) throws OPException;

	Long getCount(String hql, Object... objects) throws OPException;

	List getEntityListSQL(String sql, Object... objects) throws OPException;

	/**
	 * 利用预编译HQL查询，如果查询不到，返回新的ArrayList，不返回NullPoint。
	 * 
	 * @param hql
	 *            HQL语句
	 * @param objects
	 *            变量绑定参数
	 * @return List 返回对象实体List
	 */
	List getEntityListHQL(String hql, Object... objects) throws OPException;

	/**
	 * 利用预编译SQL查询，如果查询不到，返回新的ArrayList，基本是返回List<Object[]>，不返回NullPoint。 *
	 * 
	 * @param sql
	 *            SQL语句
	 * @param page
	 *            第一页从0开始 ,小于0不分页
	 * @param pageSize
	 *            每页显示数量
	 * @param objects
	 *            变量绑定参数
	 * @return Query 返回Query
	 */
	List retrieveEntityListHQL(String sql, int page, int pageSize, Object... objects) throws OPException;

	/**
	 * 根据sql获取列表
	 * 
	 * @param sql
	 *            sql
	 * @param objects
	 *            变量绑定参数
	 * @return list Object[]对象列表
	 * @throws OPException
	 */
	public List execSqlQuery(String sql, Object... objects) throws OPException;

	/**
	 * 按属性过滤条件列表查找对象列表.
	 * 
	 * @throws OPException
	 */
	public <T> PageHolder<T> fetchPageByFilter(QueryFilter filters, Class<T> clazz) throws OPException;

	public <T> List<T> fetchListByFilter(QueryFilter filters, Class<T> clazz) throws OPException;

	/**
	 * @param preparedSql
	 *            preparedSQL
	 * @param exportFile
	 *            导出的文件
	 * @param append
	 *            是否加在文件末尾
	 * @param includeColumnNames
	 *            是否包含表结构
	 * @param colNameMap
	 *            列命名规则 列名必须大写
	 * @param colValPattern
	 *            列值规则 列名必须大写
	 * @param paras
	 *            prepared sql 的参数
	 * @return 导入导出的结果
	 * @throws OPException
	 *             描述：这个方法把数据库表导出到csv文件，目前支持mysql和oracle
	 */
	public Future<ImExportResult> exportTableToCSVFile(String preparedSql, File logExportFile, boolean append,
			boolean includeColumnNames, final Map<String, String> colNameMap,
			final Map<String, ColumnValuePattern> colValPattern, final Object... paras) throws OPException;

	public Object callProcedureForObject4Oracle(String sql, int type, Object... objects);

}