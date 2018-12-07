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

	void removeObjs(String hql) throws OPException;

	void removeObjs(Collection objs) throws OPException;

	void updateObject(Object obj);

	Serializable saveObj(Object obj) throws OPException;

	void saveObjs(Object objs[]) throws OPException;

	void saveAndUpdateObjs(Object saveAndUpdate[]) throws OPException;

	void updateObj(Object obj) throws OPException;

	void updateObjs(Object objs[]) throws OPException;

	Connection getConnection() throws OPException;

	void saveOrUpdateObj(Object obj);

	void removeObj(Object o) throws OPException;

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
	 * 执行update 或者delete 等HQL语句
	 * 
	 * @param hql
	 *            HQL执行语句
	 */
	void executeHQLUpdates(List<String> hqls);

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
	public List retrieveEntityListSQL(String sql, int page, int pageSize, Object... objects) throws OPException;

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

	public <T> List<T> fetchList(Class<T> table, Object condition);

	/**
	 * 按属性过滤条件列表查找对象列表.
	 * 
	 * @throws OPException
	 */
	public <T> PageHolder<T> fetchPageByFilter(QueryFilter filters, Class<T> clazz) throws OPException;

	public <T> List<T> fetchListByFilter(QueryFilter filters, Class<T> clazz) throws OPException;

	/**
	 * 根据传入的object的annotation进行条件过滤,如果pageIndex>-1会自动进行count操作,totalCount会在pageHolder中保存
	 * 
	 * @param form
	 * @param pageSize
	 * @param pageIndex
	 * @return PageHolder
	 * @throws OPException
	 */
	public PageHolder fetchListByObject(Object form, int pageSize, int pageIndex, QueryCondition... cons)
			throws OPException;

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

	/**
	 * 调用无返回值的存储过程
	 * 
	 * @param sql
	 *            eg: afc_dproc(?,?)
	 * @param objects
	 * @return
	 */
	public boolean callProcedure(String sql, Object... objects);

	public Object callProcedureForObject4Oracle(String sql, int type, Object... objects);

	public Object callProcedureForObject(String sql, Object... objects);

	// 采用SQL进行批量更新
	public void execBathUpdate(String[] sqls);

	/**
	 * 批量更新一条语句，根据不同的条件 例如： sql ="update  table  set  flag = 1 where record=?" objList
	 * ={[1],[2]...[n]}
	 * 
	 * @param sql
	 *            跟新的条件语句
	 * @param objList
	 *            跟新条件
	 */
	public void execBathUpdate(String sql, List<Object[]> objList);
}