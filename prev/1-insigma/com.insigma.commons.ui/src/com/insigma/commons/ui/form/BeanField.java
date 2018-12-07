package com.insigma.commons.ui.form;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.insigma.commons.ui.anotation.data.AnnotationData;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.anotation.data.DataSourceData;
import com.insigma.commons.ui.anotation.data.ViewData;

/**
 * 
 * @author DingLuofeng
 *
 */
@SuppressWarnings({ "unchecked" })
public class BeanField {
	/**
	 * 字段定义，该字段所在的class为 {@link #parent}.{@link #fieldValue}所在的class
	 */
	public Field field;

	/**
	 * {@link #field}所对应的值
	 */
	public Object fieldValue;

	/** 修改前内容 */
	public Object historyValue;

	/**
	 * 当前field所对应的Annotation信息
	 */
	/*public*/Map<String, AnnotationData> fieldAnnotations;

	/**
	 * 定义 {@link #field}所在的对象
	 */
	public BeanField parent;

	private List<BeanField> childs;

	public BeanField(Field field) {
		this.field = field;
	}

	public BeanField(Object value) {
		this.fieldValue = value;
	}

	public BeanField(Field myField, Object myValue, BeanField parent) {
		super();
		this.field = myField;
		this.fieldValue = myValue;
		this.parent = parent;
	}

	public BeanField(Field myField, Object myValue, Object historyValue, BeanField parent) {
		super();
		this.field = myField;
		this.fieldValue = myValue;
		this.parent = parent;
		this.historyValue = historyValue;
	}

	/**
	 * 获取AnnotationData 信息，不存在返回Null
	 * @param type
	 * @return
	 */
	public final <T extends AnnotationData> T getAnnotationData(Class<T> type) {
		return getAnnotationData(type.getName());
	}

	/**
	 * 获取AnnotationData 信息，不存在返回Null
	 * @param type
	 * @return
	 */
	public final AnnotationData getCustomAnnotationData(Class<?> type) {
		return getAnnotationData(type.getName());
	}

	/**
	 * 获取AnnotationData 信息，不存在返回Null
	 * @param type
	 * @return
	 */
	public final <T extends AnnotationData> T getAnnotationData(String viewName) {
		T fieldAnnotation = (T) fieldAnnotations.get(viewName);
		if (fieldAnnotation == null && viewName != null) {
			fieldAnnotation = (T) fieldAnnotations.get(viewName.toUpperCase());
		}
		return fieldAnnotation;
	}

	public final ViewData getView() {
		return getAnnotationData(ViewData.class);
	}

	public final ColumnViewData getColumnView() {
		return getAnnotationData(ColumnViewData.class);
	}

	public final DataSourceData DataSource() {
		return getAnnotationData(DataSourceData.class);
	}

	public final void setFieldAnnotations(Map<String, AnnotationData> fieldAnnotations) {
		this.fieldAnnotations = fieldAnnotations;
	}

	public String getName() {
		return field.getName();
	}

	public void setHistoryValue(Object historyValue) {
		this.historyValue = historyValue;
	}

	public List<BeanField> getChilds() {
		return childs;
	}

	public void addChild(BeanField child) {
		if (null == childs) {
			childs = new ArrayList<BeanField>();
		}
		this.childs.add(child);
	}

	public Object getParentValue() {
		Object result = null;
		if (null != parent) {
			result = parent.fieldValue;
		}

		return result;
	}
}