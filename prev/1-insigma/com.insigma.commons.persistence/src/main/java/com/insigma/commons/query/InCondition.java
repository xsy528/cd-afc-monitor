/* 
 * 日期：2010-9-2
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.query;

import java.util.Collection;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class InCondition extends QueryCondition {
	private Criterion criterion;

	/**
	 * @param field
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public InCondition(String field, Object value) {
		super(field, value);
		if (value instanceof Collection) {
			criterion = Restrictions.in(field, (Collection) value);
		} else if (value.getClass().isArray()) {
			criterion = Restrictions.in(field, (Object[]) value);
		} else {
			throw new IllegalArgumentException("必须为集合类型");
		}
	}

	public Criterion getCriterion() {
		return criterion;
	}

}
