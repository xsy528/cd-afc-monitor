/**
 * iFrameWork 框架
 * 
 * @component     应用程序框架
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description   
 * @date          2009-4-2
 * @copyright     浙江浙大网新众合轨道交通工程有限公司 
 * @history       
 */
package com.insigma.commons.service;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

import com.insigma.commons.application.IService;
import com.insigma.commons.op.OPException;

@SuppressWarnings("unchecked")
public interface ICommonService extends IService {

	void updateObject(Object obj);

	public void removeObj(Class<?> c, Serializable id) throws OPException;

	public void removeObj(Object object) throws OPException;

	public Serializable saveObj(Object obj) throws OPException;

	public void saveObjs(Object objs[]) throws OPException;

	public void saveAndUpdateObjs(Object saveAndUpdate[]) throws OPException;

	public void updateObj(Object obj) throws OPException;

	public void updateObjs(Object objs[]) throws OPException;

	public Connection getConnection() throws OPException;

	void checkOnline() throws Exception;

	void saveOrUpdateObj(Object obj);

	void deleteDeviceList(Long nodeId);

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

	Long getCount(String hql, Object... objects) throws OPException;

	public List getEntityListSQL(String sql, Object... objects) throws OPException;

	/**
	 * 利用预编译HQL查询，如果查询不到，返回新的ArrayList，不返回NullPoint。
	 * 
	 * @param hql
	 *            HQL语句
	 * @param objects
	 *            变量绑定参数
	 * @return List 返回对象实体List
	 */
	public List getEntityListHQL(String hql, Object... objects) throws OPException;

}