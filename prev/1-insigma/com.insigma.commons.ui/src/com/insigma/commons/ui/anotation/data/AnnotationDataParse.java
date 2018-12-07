/**
 * 
 */
package com.insigma.commons.ui.anotation.data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.anotation.ColumnView;
import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.util.AnnotationUtil;

/**
 * @author DLF
 *
 */
public class AnnotationDataParse {

	@SuppressWarnings("unchecked")
	public static final Map<String, AnnotationData> parseJson(Map<String, Object> fieldInfoMap, Field field) {
		Map<String, AnnotationData> fieldAnnotations = new HashMap<String, AnnotationData>();
		if (fieldInfoMap == null) {
			return fieldAnnotations;
		}
		if (BeanUtil.isUserDefinedClass(field.getType())) {//如果是复合对象，设置默认的View
			ViewData viewData = new ViewData();
			fieldAnnotations.put(ViewData.class.getName(), viewData);
			fieldAnnotations.put("VIEW", viewData);
		}
		Set<String> keySet = fieldInfoMap.keySet();
		for (String key : keySet) {
			if (key == null) {
				continue;
			}
			Map<String, Object> annotationMap = (Map<String, Object>) fieldInfoMap.get(key);
			if ("View".equalsIgnoreCase(key)) {
				ViewData viewData = new ViewData(annotationMap);
				fieldAnnotations.put(ViewData.class.getName(), viewData);
				fieldAnnotations.put("VIEW", viewData);
			} else if ("ColumnView".equalsIgnoreCase(key)) {
				ColumnViewData columnViewData = new ColumnViewData(annotationMap);
				fieldAnnotations.put(ColumnViewData.class.getName(), columnViewData);
				fieldAnnotations.put("COLUMNVIEW", columnViewData);
			} else if ("DataSource".equalsIgnoreCase(key)) {
				DataSourceData dataSourceData = new DataSourceData(annotationMap);
				fieldAnnotations.put(DataSourceData.class.getName(), dataSourceData);
				fieldAnnotations.put("DATASOURCE", dataSourceData);
			} else {//FIXME 待扩展
				fieldAnnotations.put(key.toUpperCase(), new AnnotationData(annotationMap));
			}
		}
		return fieldAnnotations;
	}

	public static final Map<String, AnnotationData> parseAnnotations(Field field) {
		Map<String, AnnotationData> fieldAnnotations = new HashMap<String, AnnotationData>();
		Annotation[] annotations = field.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof View) {
				ViewData viewData = new ViewData((View) annotation);
				fieldAnnotations.put(ViewData.class.getName(), viewData);
				fieldAnnotations.put("VIEW", viewData);
			} else if (annotation instanceof ColumnView) {
				ColumnViewData columnViewData = new ColumnViewData((ColumnView) annotation);
				fieldAnnotations.put(ColumnViewData.class.getName(), columnViewData);
				fieldAnnotations.put("COLUMNVIEW", columnViewData);
			} else if (annotation instanceof DataSource) {
				DataSourceData dataSourceData = new DataSourceData((DataSource) annotation);
				fieldAnnotations.put(DataSourceData.class.getName(), dataSourceData);
				fieldAnnotations.put("DATASOURCE", dataSourceData);
			} else {//FIXME 待扩展
				Map<String, Object> annotationMap = AnnotationUtil.parseAnnotation2Map(annotation);
				String annotationName = annotation.annotationType().getName();
				fieldAnnotations.put(annotationName, new AnnotationData(annotationMap));
			}
		}
		return fieldAnnotations;
	}

}
