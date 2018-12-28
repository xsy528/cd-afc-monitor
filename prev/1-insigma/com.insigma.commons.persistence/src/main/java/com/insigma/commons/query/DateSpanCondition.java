/* 
 * 日期：2010-9-2
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.query;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.insigma.commons.lang.DateSpan;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class DateSpanCondition extends QueryCondition {
	private DateSpan dateSpan;

	/**
	 * @param field
	 * @param value
	 */
	public DateSpanCondition(String field, Object value) {
		super(field, value);
		if (value instanceof DateSpan) {
			dateSpan = (DateSpan) value;
		} else {
			throw new IllegalArgumentException("必须为DateSpan类型");
		}
	}

	public Criterion getCriterion() {
		return Restrictions.between(field, dateSpan.getStartDate(), dateSpan.getEndDate());
	}

}
