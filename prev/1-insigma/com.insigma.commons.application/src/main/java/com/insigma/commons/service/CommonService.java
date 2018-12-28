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

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.op.BaseHibernateDao;
import com.insigma.commons.op.OPException;

@Transactional(propagation = Propagation.REQUIRED)
@Service(value = "commonService")
public class CommonService extends BaseHibernateDao implements ICommonService {

	public Object getSingleObject(String sql) {
		return getSession().createSQLQuery(sql).uniqueResult();
	}

	public void updateObject(Object obj) {
		try {
			if (obj == null) {
				throw new ApplicationException("更新实体失败。");
			}
			this.updateObj(obj);
		} catch (OPException e) {
			throw new ApplicationException("更新实体失败。", e);
		}

	}

	public void checkOnline() throws Exception {
		getConnection().close();
	}

	public void saveOrUpdateObj(Object element) {
		if (element == null) {
			throw new ApplicationException("保存失败，持久化对象为空。");
		}
		this.getHibernateTemplate().saveOrUpdate(element);
	}

	/**
	 * 删除物理节点
	 * 
	 * @param nodeId
	 *            节点编号
	 */
	public void deleteDeviceList(Long nodeId) {
		String sql = "delete from TMT_DEVICE_LIST t where t.NODE_ID=?";
		try {
			this.execSqlUpdate(sql, nodeId);
		} catch (OPException e) {
			throw new ApplicationException("删除设备物理节点失败。", e);
		}
	}

	/**
	 * 执行update 或者delete 等HQL语句
	 * 
	 * @param hql
	 *            HQL执行语句
	 * @param objects
	 *            变量绑定参数
	 */
	public void executeHQLUpdate(String hql, Object... objects) {
		try {
			this.updateObjs(hql, objects);
		} catch (OPException e) {
			throw new ApplicationException("执行HQL语句<" + hql + ">失败。", e);
		}
	}

	public void executeHQLUpdates(List<String> hqls) {
		for (String hql : hqls) {
			executeHQLUpdate(hql);
		}
	}

}