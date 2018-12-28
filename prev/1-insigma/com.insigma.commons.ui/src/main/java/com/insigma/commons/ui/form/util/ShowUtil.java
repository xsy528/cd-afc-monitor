/* 
 * 日期：2011-12-30
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.form.util;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang.StringUtils;

import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.anotation.ColumnView;
import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.anotation.data.ViewData;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.util.lang.DateTimeUtil;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class ShowUtil {

	/**
	 * 
	 * @param field 该字段的定义
	 * @param value 该字段的对应的值
	 * @param parentValue 该字段{@link field} 所在的对象
	 * @return
	 */
	public static String getItemText(BeanField field, Object value, Object parentValue) {
		ColumnViewData columnView = field.getColumnView();
		if (columnView == null) {
			return value == null ? "" : value.toString();
		}
		String el = columnView.el();
		if (StringUtils.isNotEmpty(el)) {
			try {
				Map<String, Object> context = new WeakHashMap<String, Object>();
				context.put("field", value);
				context.put("this", parentValue);
				// jfq,todo
				return "";
				//return ExpressionEngine.eval(el, context);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		ViewData view = field.getView();
		if (view != null && !"".equals(view.format()) && value != null) {
			return String.format(view.format(), value);
		}

		//2014-02-21 shenchao 
		DataSource annotation = field.field.getAnnotation(DataSource.class);
		if (annotation != null && value != null && view != null && view.type().equals("Combo")) {
			String[] list = annotation.list();
			String[] values = annotation.values();
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < list.length; i++) {
				if (values != null && values.length > 0) {
					map.put(values[i], list[i]);
				} else {
					map.put(i + "", list[i]);
				}
			}

			String string = map.get(value.toString());
			if (string != null) {
				value = string;
			}

		}

		if (value == null) {
			return columnView.nullvalue();
		} else {
			return value.toString();
		}

	}

	/**
	 * 
	 * @param field 该字段的定义
	 * @param value 该字段的对应的值
	 * @param parentValue 该字段{@link field} 所在的对象
	 * @return
	 */
	public static String getItemText(Field field, Object value, Object parentValue) {
		ColumnView columnView = field.getAnnotation(ColumnView.class);
		if (columnView == null) {
			return value == null ? "" : value.toString();
		}
		String el = columnView.el();
		if (StringUtils.isNotEmpty(el)) {
			Map<String, Object> context = new WeakHashMap<String, Object>();
			context.put("field", value);
			context.put("this", parentValue);
			// jfq
			return "";

			//return ExpressionEngine.eval(el, context);
		}

		View view = field.getAnnotation(View.class);
		if (view != null && !"".equals(view.format())) {
			return String.format(view.format(), value);
		}

		if (value == null) {
			if (columnView.nullvalue() != null) {
				return columnView.nullvalue();
			}
			return "";
		}
		if (value instanceof List) {
			return "...";
		} else {
			return value.toString();
		}

	}

	public static String getCompareItemText(Field field, Object value, Object parentValue, View view) {
		return getCompareItemText(field, value, parentValue, new ViewData(view));
	}

	public static String getCompareItemText(Field field, Object value, Object parentValue, ViewData view) {
		return "【" + getEditableItemText(field, value, parentValue, view) + "】";
	}

	public static String getEditableItemText(Field field, Object value, Object parentValue, View view) {
		return getEditableItemText(field, value, parentValue, new ViewData(view));
	}

	public static String getEditableItemText(Field field, Object value, Object parentValue, ViewData view) {
		String format = view.format();
		if (!"".equals(format)) {
			return String.format(format, value);
		} else if (StringUtils.isNotEmpty(view.type().trim())) {
			if ("date".equalsIgnoreCase(view.type())) {// date
				return DateTimeUtil.formatDate((Date) value, "yyyy-MM-dd");
			} else if ("dateTime".equalsIgnoreCase(view.type())) {// dateTime
				return DateTimeUtil.formatDate((Date) value, "yyyy-MM-dd HH:mm:ss");
			} else if ("Time".equalsIgnoreCase(view.type())) {// Time
				return DateTimeUtil.formatDate((Date) value, "HH:mm:ss");
			} else if ("RadioBox".equalsIgnoreCase(view.type()) || "Combo".equalsIgnoreCase(view.type())
					|| ("CheckBox".equalsIgnoreCase(view.type()))) {
				DataSource ds = field.getAnnotation(DataSource.class);
				if (ds == null) {
					return getItemText(field, value, parentValue);
				}
				String[] vs = ds.values();
				final String[] keylist = ds.list();
				if (vs == null || vs.length == 0) {
					if (keylist != null && keylist.length > 0) {
						try {
							Integer listIndex = (Integer) BeanUtil.convert(value, Integer.class);
							return keylist[listIndex];
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					for (int i = 0; i < vs.length; i++) {
						if (value.toString().equals(vs[i])) {
							return keylist[i];
						}
					}
				}
			}
		}
		return getItemText(field, value, parentValue);
	}

}
