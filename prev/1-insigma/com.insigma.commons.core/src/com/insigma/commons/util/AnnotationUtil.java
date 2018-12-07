/**
 * 
 */
package com.insigma.commons.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import sun.reflect.annotation.AnnotationType;

import com.insigma.commons.reflect.BeanUtil;

/**
 * 把Annotation转化为Map对象的工具类
 * @author DingLuofeng
 *
 */
public class AnnotationUtil {

	/**
	 * 把Annotation转化为Map对象
	 * @param annotation
	 * @return
	 */
	public static Map<String, Object> parseAnnotation2Map(Annotation annotation) {
		Map<String, Object> result = new HashMap<String, Object>();
		Class<? extends Annotation> annotationType = annotation.annotationType();
		AnnotationType instance = AnnotationType.getInstance(annotationType);
		Map<String, Method> members = instance.members();
		Collection<Method> values = members.values();
		for (Method method : values) {
			Object value = BeanUtil.getValue(method, annotation);
			result.put(method.getName(), value);
		}
		return result;
	}

}
