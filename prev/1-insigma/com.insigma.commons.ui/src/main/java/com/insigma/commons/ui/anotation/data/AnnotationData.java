package com.insigma.commons.ui.anotation.data;

import java.util.HashMap;
import java.util.Map;

import com.insigma.commons.reflect.BeanUtil;

public class AnnotationData {

	protected Map<String, Object> annotationItems = new HashMap<String, Object>();

	/**
	 * @param annotationItems
	 */
	public AnnotationData(Map<String, Object> annotationItems) {
		if (annotationItems != null) {
			this.annotationItems = annotationItems;
		}
	}

	/**
	 * @param annotationItems
	 */
	AnnotationData() {
	}

	public final Object get(String key) {
		return annotationItems.get(key);
	}

	public final <T> T get(String key, Class<T> type) {
		Object value = annotationItems.get(key);
		return convert(value, type, null);
	}

	public final <T> T get(String key, Class<T> type, T defaultValue) {
		Object value = annotationItems.get(key);
		return convert(value, type, defaultValue);
	}

	public final Object put(String key, Object value) {
		return annotationItems.put(key, value);
	}

	public final <T> T convert(Object object, Class<T> targetType, T defaultValue) {
		if (object == null) {
			return defaultValue;
		}
		return BeanUtil.convert(object, targetType);
	}

}