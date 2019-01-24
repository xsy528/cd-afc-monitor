package com.insigma.commons.service;

import com.insigma.commons.collection.PageHolder;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.op.BaseHibernateDao;
import com.insigma.commons.op.OPException;
import com.insigma.commons.query.OrderBy;
import com.insigma.commons.query.QueryCondition;
import com.insigma.commons.query.QueryFilter;
import com.insigma.commons.query.QueryFilter.ConditionType;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Transactional(propagation = Propagation.REQUIRED)
@Service(value = "commonService")
@SuppressWarnings("unchecked")
public class CommonDAO extends BaseHibernateDao implements ICommonDAO {

	public void saveOrUpdateObj(Object element) {
		if (element == null) {
			throw new ApplicationException("保存失败，持久化对象为空。");
		}
		element = getHibernateTemplate().merge(element);
		this.getHibernateTemplate().saveOrUpdate(element);
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

	/**
	 * 按属性过滤条件列表查找对象列表.
	 * 
	 * @throws OPException
	 */
	public <T> List<T> fetchListByFilter(QueryFilter filter, Class<T> clazz) throws OPException {
		return fetchPageByFilter(filter, clazz).getDatas();
	}

	public <T> PageHolder<T> fetchPageByFilter(QueryFilter filter, Class<T> clazz) throws OPException {
		Class queryClazz = clazz;

		if (queryClazz == null) {
			throw new OPException("查询类不能为空。");
		}

		DetachedCriteria criteria = DetachedCriteria.forClass(queryClazz);
		List<QueryCondition> cons = filter.getQueryConditions();
		for (QueryCondition con : cons) {
			Criterion left = con.getCriterion();
			criteria = criteria.add(left);
			List<QueryCondition> brothers = con.getBrotherCons();
			for (QueryCondition brocon : brothers) {
				if (brocon.getType() == ConditionType.AND) {
					criteria.add(brocon.getCriterion());
				} else {
					criteria.add(brocon.getCriterion());
				}
			}
			List<QueryCondition> subCons = con.getSubCons();
			if (subCons != null && subCons.size() > 0) {
				QueryCondition firstSubCon = subCons.get(0);
				Criterion firstSubCri = firstSubCon.getCriterion();
				criteria.add(Expression.and(left, firstSubCri));
				for (int i = 1; i < subCons.size(); i++) {
					QueryCondition subcon = subCons.get(i);
					Criterion subconCri = subcon.getCriterion();
					if (subcon.getType() == ConditionType.AND) {
						Restrictions.and(firstSubCri, subconCri);
					} else {
						Restrictions.or(firstSubCri, subconCri);
					}
				}
			}
		}
		List<OrderBy> orders = filter.getOrderBys();
		int pageIndex = filter.getPageIndex();
		int pageSize = filter.getPageSize();
		if (pageIndex > -1) {
			// count操作
			criteria.setProjection(Projections.rowCount());
			List countResults = getHibernateTemplate().findByCriteria(criteria);
			int count = 0;
			if (countResults.size() > 0) {
				count = (Integer) countResults.get(0);
			}
			List list = null;
			if (count > 0) {
				criteria.setProjection(null);
				for (OrderBy key : orders) {
					if (key.isAsc()) {
						criteria.addOrder(Order.asc(key.getName()));
					} else {
						criteria.addOrder(Order.desc(key.getName()));
					}
				}
				// 查询数据
				list = getHibernateTemplate().findByCriteria(criteria, pageSize * (pageIndex - 1), pageSize);
			} else {
				list = new ArrayList();
			}
			PageHolder page = new PageHolder<T>(pageIndex, pageSize, count, list);
			return page;
		} else {
			for (OrderBy key : orders) {
				if (key.isAsc()) {
					criteria.addOrder(Order.asc(key.getName()));
				} else {
					criteria.addOrder(Order.desc(key.getName()));
				}
			}
			List list = getHibernateTemplate().findByCriteria(criteria);
			PageHolder page = new PageHolder<T>(-1, pageSize, list.size(), list);
			return page;
		}
	}

	//适用于oracle
	public Object callProcedureForObject4Oracle(String sql, int type, Object... objects) {
		try {
			Connection conn = getConnection();
			CallableStatement cs;
			String callString = "{call " + sql + " }";

			cs = conn.prepareCall(callString);
			int i = 1;
			for (Object object : objects) {
				cs.setObject(i, object);
				i++;
			}
			cs.registerOutParameter(i, type);
			cs.execute();
			return cs.getObject(i);
		} catch (Exception e) {
			throw new ApplicationException("更新实体失败。", e);
		}
	}
}