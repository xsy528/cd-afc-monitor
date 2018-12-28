package com.insigma.commons.service;

import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.insigma.commons.annotation.Condition;
import com.insigma.commons.annotation.NoCondition;
import com.insigma.commons.annotation.QueryClass;
import com.insigma.commons.collection.PageHolder;
import com.insigma.commons.database.StringHelper;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.op.BaseHibernateDao;
import com.insigma.commons.op.OPException;
import com.insigma.commons.op.SqlRestrictions;
import com.insigma.commons.query.OrderBy;
import com.insigma.commons.query.QueryCondition;
import com.insigma.commons.query.QueryConditionFactory;
import com.insigma.commons.query.QueryFilter;
import com.insigma.commons.query.QueryFilter.ConditionType;

@Transactional(propagation = Propagation.REQUIRED)
@Service(value = "commonService")
@SuppressWarnings("unchecked")
public class CommonDAO extends BaseHibernateDao implements ICommonDAO {

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

	public void executeHQLUpdates(List<String> hqls) {
		for (String hql : hqls) {
			executeHQLUpdate(hql);
		}
	}

	public <T> List<T> fetchList(Class<T> table, Object condition) {
		List<Object> args = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		try {
			sql.append("from " + table.getClass().getName() + " t where 1=1 ");
			for (Field field : condition.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				if (field.get(condition) != null) {
					if (field.getType().isArray()) {
						sql.append(SqlRestrictions.in("t." + field.getName(),
								StringHelper.array2StrOfCommaSplit((Object[]) field.get(condition))));
					} else {
						sql.append(" and t." + field.getName() + " = ? ");
						args.add(field.get(condition));
					}
				}
			}
			return getEntityListHQL(sql.toString(), args.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public PageHolder fetchListByObject(Object form, int pageSize, int pageIndex, QueryCondition... cons)
			throws OPException {
		Class formClass = form.getClass();
		QueryClass qc = form.getClass().getAnnotation(QueryClass.class);
		if (qc == null) {
			return null;
		}
		QueryFilter filter = new QueryFilter();
		Class queryClass = qc.queryClass();
		if (queryClass == null) {
			throw new NullPointerException("查询类不能为空。");
		}
		filter.setPageIndex(pageIndex);
		filter.setPageSize(pageSize);
		if (qc.orderby() != null) {
			String[] orderbys = qc.orderby();
			for (String orderbyString : orderbys) {
				String[] splits = orderbyString.split(" ");
				boolean asc = false;
				if (splits.length > 1) {
					asc = "asc".equalsIgnoreCase(splits[1]);
				}
				filter.addOrderBy(splits[0], asc);
			}
		}

		for (Field field : formClass.getDeclaredFields()) {
			field.setAccessible(true);
			if (field.getName().equals("serialVersionUID")) {
				continue;
			}

			NoCondition ignore = field.getAnnotation(NoCondition.class);
			if (ignore != null) {
				continue;
			}
			Condition condition = field.getAnnotation(Condition.class);
			Object fieldValue = null;
			try {
				fieldValue = field.get(form);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if (fieldValue == null) {
				continue;
			}
			QueryCondition con = QueryConditionFactory.buildCondition(condition, field.getName(), fieldValue);
			if (con == null) {
				continue;
			}
			filter.addQueryConditoin(con);
		}
		for (QueryCondition qcon : cons) {
			filter.addQueryConditoin(qcon);
		}
		return fetchPageByFilter(filter, queryClass);
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

	//适用于DB2
	public Object callProcedureForObject(String sql, Object... objects) {
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
			cs.execute();

			ResultSet rs = null;
			rs = cs.getResultSet();

			return rs;
		} catch (Exception e) {
			throw new ApplicationException("更新实体失败。", e);
		}
	}

	public void execBathUpdate(String[] sqls) {
		try {
			this.execSqlUpdate(sqls);
		} catch (Exception e) {
			throw new ApplicationException("进行批量更新SQL异常。", e);
		}
	}

	public void execBathUpdate(String sql, List<Object[]> objList) {
		// TODO Auto-generated method stub
		try {
			this.execSqlUpdate(sql, objList);
		} catch (Exception e) {
			throw new ApplicationException("进行批量更新SQL异常。", e);
		}
	}
}