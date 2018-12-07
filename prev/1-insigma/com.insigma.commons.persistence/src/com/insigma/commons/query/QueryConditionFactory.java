/* 
 * 日期：2010-9-6
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.query;

import java.util.Collection;

import com.insigma.commons.annotation.Condition;
import com.insigma.commons.lang.DateSpan;
import com.insigma.commons.query.QueryFilter.ConditionType;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class QueryConditionFactory {
	@SuppressWarnings("unchecked")
	public static QueryCondition buildCondition(Condition condition, String fieldName, Object fieldValue) {

		String name = fieldName;
		String compareType = "=";
		if (condition != null) {
			if (!"".equals(condition.column())) {
				name = condition.column();
			}
			if (!"".equals(condition.compareType())) {
				compareType = condition.compareType();
			}
		}

		Class fvClass = fieldValue.getClass();
		QueryCondition con = null;
		if ((fieldValue instanceof Collection) || fvClass.isArray()) {
			con = new InCondition(name, fieldValue);
		} else if (fieldValue instanceof DateSpan) {
			con = new DateSpanCondition(name, fieldValue);
		} else {
			con = new QueryCondition(name, fieldValue, ConditionType.AND, compareType);
		}

		return con;
	}
}
