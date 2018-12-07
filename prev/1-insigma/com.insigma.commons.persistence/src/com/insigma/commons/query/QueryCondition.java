package com.insigma.commons.query;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class QueryCondition {
	protected Class<Object> clazz;

	protected String clsAlias;

	protected String field;

	protected Object value;

	private boolean isFieldFunction;

	private boolean useQuote;

	protected QueryFilter.ConditionType type;

	protected String compareType;

	protected QueryCondition parent;

	private LinkedList<QueryCondition> brothers;

	private LinkedList<QueryCondition> subCons;

	public QueryCondition(String field, Object value) {
		this(field, value, QueryFilter.ConditionType.AND);
	}

	public QueryCondition(String field, Object value, QueryFilter.ConditionType type) {
		this.clsAlias = "ppt.";

		this.isFieldFunction = false;
		this.useQuote = true;

		this.compareType = "=";

		this.field = field;
		this.value = value;
		if (value == null) {
			this.compareType = "";
		}
		this.type = type;
	}

	public QueryCondition(String field, Object value, QueryFilter.ConditionType type, boolean useQuote) {
		this(field, value, type);
		this.useQuote = useQuote;
	}

	public QueryCondition(String field, Object value, QueryFilter.ConditionType type, String compareType) {
		this(field, value, type);
		if (value != null)
			this.compareType = compareType;
	}

	public QueryCondition(String field, Object value, QueryFilter.ConditionType type, String compareType,
			boolean useQuote) {
		this(field, value, type, useQuote);
		if (value != null)
			this.compareType = compareType;
	}

	public QueryCondition(boolean fieldIsFunction, String field, Object value, QueryFilter.ConditionType type,
			String compareType, boolean useQuote) {
		this(field, value, type, compareType, useQuote);
		this.isFieldFunction = fieldIsFunction;
	}

	public QueryCondition(Class<Object> clazz, String clsAlias, String field, Object value,
			QueryFilter.ConditionType type, String compareType) {
		this(field, value, type, compareType);
		this.clazz = clazz;
		this.clsAlias = clsAlias;
	}

	public QueryCondition(Class<Object> clazz, String clsAlias) {
		this.clsAlias = "ppt.";

		this.isFieldFunction = false;
		this.useQuote = true;

		this.compareType = "=";

		this.clazz = clazz;
		this.clsAlias = clsAlias;
	}

	public void setClazz(Class<Object> clazz) {
		this.clazz = clazz;
		this.clsAlias = clazz.getSimpleName().toLowerCase() + ".";
	}

	public QueryCondition(Class<Object> clazz) {
		this(clazz, clazz.getSimpleName().toLowerCase());
	}

	public void addSubCondition(QueryCondition subCon, int index) {
		if (this.subCons == null) {
			this.subCons = new LinkedList<QueryCondition>();
		}
		this.subCons.add(index, subCon);
		subCon.setParent(this);
	}

	public void addSubCondition(QueryCondition subCon) {
		if (this.subCons == null) {
			this.subCons = new LinkedList<QueryCondition>();
		}
		this.subCons.add(subCon);
		subCon.parent = this;
	}

	public void addCondition(QueryCondition con) {
		if (this.brothers == null) {
			this.brothers = new LinkedList<QueryCondition>();
		}
		this.brothers.add(con);
	}

	public Object getValue() {
		return this.value;
	}

	public Class<Object> getClazz() {
		return this.clazz;
	}

	public String getClsAlias() {
		return this.clsAlias;
	}

	public String getField() {
		return this.field;
	}

	public String getCompareType() {
		return this.compareType;
	}

	public QueryFilter.ConditionType getType() {
		return this.type;
	}

	public String generateHql() {
		return " " + this.type + toString();
	}

	public Criterion getCriterion() {
		Criterion criter = null;
		String trimCompare = this.compareType.trim();
		if (trimCompare.equals("=")) {
			criter = Restrictions.eq(field, value);
		} else if (trimCompare.equalsIgnoreCase("like")) {
			if (value != null) {
				criter = Restrictions.like(field, value.toString(), MatchMode.ANYWHERE);
			}
		} else if (trimCompare.equalsIgnoreCase("in")) {
			criter = Restrictions.in(field, (Object[]) value);
		} else if (trimCompare.equals(">")) {
			criter = Restrictions.gt(field, value);
		} else if (trimCompare.equals(">=")) {
			criter = Restrictions.ge(field, value);
		} else if (trimCompare.equals("<")) {
			criter = Restrictions.lt(field, value);
		} else if (trimCompare.equals("<=")) {
			criter = Restrictions.le(field, value);
		} else if (trimCompare.equals("<>") || (trimCompare.equals("!="))) {
			criter = Restrictions.ne(field, value);
		}

		return criter;
	}

	public String toString() {
		QueryCondition con;
		Iterator<QueryCondition> localIterator;
		StringBuffer hql = new StringBuffer(100);
		boolean hasSub = !(getSubCons().isEmpty());
		boolean hasBrother = !(getBrotherCons().isEmpty());
		boolean isMutipleCon = (hasSub) || (hasBrother);
		if (isMutipleCon) {
			hql.append("(");
		}
		if (this.isFieldFunction) {
			this.clsAlias = "";
		}
		hql.append(" ").append(this.clsAlias).append(this.field).append(" ");
		if (this.compareType.equalsIgnoreCase("like")) {
			hql.append(" like '%").append(this.value).append("%'");
		} else if (this.value == null) {
			hql.append(" is ").append(this.compareType).append(" null ");
		} else {
			hql.append(this.compareType);
			if ((this.value.getClass() == Integer.TYPE) || (this.value instanceof Number)
					|| (this.value instanceof Boolean) || (!(this.useQuote)))
				hql.append(this.value);
			else if (this.value instanceof Date)
				hql.append("'").append(DateFormatUtils.format((Date) this.value, "yyyy-MM-dd HH:mm:ss")).append("' ");
			else if ((this.compareType.indexOf("in") > -1) || (this.compareType.indexOf("IN") > -1)) {
				hql.append(" (").append(this.value).append(")");
			} else {
				hql.append("'").append(this.value).append("'");
			}

		}

		if (hasBrother) {
			for (localIterator = getBrotherCons().iterator(); localIterator.hasNext();) {
				con = (QueryCondition) localIterator.next();
				hql.append(" ").append(con.type).append(" ").append(con.generateHql());
			}
		}

		if (hasSub) {
			for (localIterator = getSubCons().iterator(); localIterator.hasNext();) {
				con = (QueryCondition) localIterator.next();
				hql.append(" ").append(con.type).append(" (").append(con.generateHql()).append(")");
			}
		}

		if (isMutipleCon) {
			hql.append(")");
		}
		return hql.toString();
	}

	public QueryCondition getParent() {
		return this.parent;
	}

	public void setParent(QueryCondition parent) {
		this.parent = parent;
		if (!(parent.getSubCons().contains(this)))
			parent.addSubCondition(this);
	}

	@SuppressWarnings("unchecked")
	public List<QueryCondition> getSubCons() {
		if (this.subCons == null) {
			return Collections.EMPTY_LIST;
		}
		return this.subCons;
	}

	@SuppressWarnings("unchecked")
	public List<QueryCondition> getBrotherCons() {
		if (this.brothers == null) {
			return Collections.EMPTY_LIST;
		}
		return this.brothers;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof QueryCondition)) {
			return false;
		}
		QueryCondition con = (QueryCondition) obj;
		if (this.clazz != null && con.clazz != null) {
			if ((this.clazz.equals(con.clazz)) && (this.field.equals(con.field))) {
				if (this.value == null)
					if (con.value != null)
						return false;
				if ((this.value.equals(con.value)) && (this.isFieldFunction == con.isFieldFunction)
						&& (this.useQuote == con.useQuote) && (this.compareType.equals(con.compareType))
						&& (this.type.equals(con.type)))
					return true;
			}
		}
		return false;
	}

	public Map<String, Object> getParameters() {
		return null;
	}
}
