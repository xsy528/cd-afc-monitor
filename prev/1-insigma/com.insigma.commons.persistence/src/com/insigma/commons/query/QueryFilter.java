package com.insigma.commons.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class QueryFilter {
	private String id;

	private final Map<Class<Object>, String> joinTables = new HashMap<Class<Object>, String>();

	private List<OrderBy> orderBys;

	private List<String> groupBys;

	protected List<QueryCondition> _extendContions;

	private int pageIndex = -1;

	private int pageSize = 20;

	public QueryFilter() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<QueryCondition> getQueryConditions() {
		if (this._extendContions == null) {
			this._extendContions = new ArrayList<QueryCondition>();
		}
		return this._extendContions;
	}

	public void addQueryConditoin(QueryCondition conditon) {
		if (conditon == null) {
			return;
		}
		if (!(getQueryConditions().contains(conditon)))
			getQueryConditions().add(conditon);
	}

	public List<String> getGroupbys() {
		if (this.groupBys == null) {
			this.groupBys = new ArrayList<String>();
		}
		return this.groupBys;
	}

	public void addGroupBy(String groupby) {
		if (groupby != null)
			if (groupby.indexOf(".") >= 0)
				getGroupbys().add(groupby);
			else
				getGroupbys().add(" ppt." + groupby);
	}

	public void addGroupBy(int index, String groupby) {
		if (groupby != null)
			if (groupby.indexOf(".") >= 0)
				getGroupbys().add(index, groupby);
			else
				getGroupbys().add(index, " ppt." + groupby);
	}

	public void addOrderBy(String orderby, boolean asc) {
		if (orderBys == null) {
			orderBys = new LinkedList<OrderBy>();
		}

		this.orderBys.add(new OrderBy(orderby, asc));

	}

	public List<OrderBy> getOrderBys() {
		if (this.orderBys == null) {
			this.orderBys = new ArrayList<OrderBy>();
		}
		return this.orderBys;
	}

	public void propertyFilter(String field, Object value) {
		propertyFilter(field, value, ConditionType.AND);
	}

	public void propertyFilter(String field, Object value, ConditionType type) {
		propertyFilter(field, value, type, "=");
	}

	public void propertyFilter(String field, Object value, String compareType) {
		propertyFilter(field, value, ConditionType.AND, compareType);
	}

	public void propertyFilter(String field, Object value, ConditionType type, String compareType) {
		QueryCondition conditon = new QueryCondition(field, value, type, compareType);
		addQueryConditoin(conditon);
	}

	public void addJoinTableFilter(Class<Object> cls, String field, Object value, ConditionType type,
			String compareType) {
		QueryCondition conditon = new QueryCondition(cls, cls.getSimpleName().toLowerCase(), field, value, type,
				compareType);
		addQueryConditoin(conditon);
	}

	public void addJoinTable(Class<Object> tbl, String fid) {
		if (!(this.joinTables.containsKey(tbl)))
			this.joinTables.put(tbl, fid);
	}

	public Map<Class<Object>, String> getJoinTables() {
		return this.joinTables;
	}

	public Map<String, Object> getParameters() {
		Map<String, Object> ps = new HashMap<String, Object>();
		for (QueryCondition con : this._extendContions) {
			Map<String, Object> m = con.getParameters();
			if (m != null) {
				ps.putAll(m);
			}
			List<QueryCondition> brs = con.getBrotherCons();
			if (brs != null) {
				for (QueryCondition subCon : brs) {
					Map<String, Object> subP = subCon.getParameters();
					if (subP != null) {
						ps.putAll(subP);
					}
				}
			}
		}
		return ps;
	}

	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex
	 *            the pageIndex to set
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public static enum ConditionType {
		AND, OR;
	}
}
