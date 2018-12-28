/**
 * 
 */
package com.insigma.commons.framework.view.form;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.commons.collection.IndexHashMap;
import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.reflect.ClassUtil;
import com.insigma.commons.ui.anotation.ViewExpansion;
import com.insigma.commons.ui.anotation.data.AnnotationData;
import com.insigma.commons.ui.anotation.data.AnnotationDataParse;
import com.insigma.commons.ui.anotation.data.AnnotationOverrideManager;
import com.insigma.commons.ui.anotation.data.ViewData;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.util.SystemPropertyUtil;

/**
 * @author DingLuofeng
 *
 */
public class FormFieldsCollector implements IFormFieldsCollector {

	protected Log logger = LogFactory.getLog(getClass());

	private boolean isOverride;

	public FormFieldsCollector() {
	}

	private final IndexHashMap<String, List<BeanField>> initGroupMap() {
		IndexHashMap<String, List<BeanField>> groupFieldsMap = new IndexHashMap<String, List<BeanField>>();//group的field
		groupFieldsMap.clear();
		groupFieldsMap.put(NONE_GROUP, new ArrayList<BeanField>(), -1);
		return groupFieldsMap;
	}

	/**
	 * 收集所有的字段，按group分组
	 * @param parentFormField 需收集的实例所在的字段
	 * @param clz 需收集的类
	 * @param groupName 组名
	 * @param groupFieldsMap
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected final void collectFields(BeanField parentFormField, Class<?> clz, String groupName,
			IndexHashMap<String, List<BeanField>> groupFieldsMap, Map formMap) {
		if (clz.getSuperclass() != Object.class) {
			collectFields(parentFormField, clz.getSuperclass(), groupName, groupFieldsMap, formMap);
		}
		Object data = parentFormField.fieldValue;
		Field[] fields = clz.getDeclaredFields();
		for (Field field : fields) {
			//覆盖字段信息，可能为Null
			//构建FormField
			BeanField child = new BeanField(field);
			Map fieldOverrideInfo = null;
			if (isOverride) {
				if (formMap == null) {//FIXME 覆盖信息为空
					continue;
				}
				//获取覆盖信息
				fieldOverrideInfo = (Map) formMap.get(field.getName());
				Map<String, AnnotationData> parse = AnnotationDataParse.parseJson(fieldOverrideInfo, field);
				child.setFieldAnnotations(parse);
			} else {
				Map<String, AnnotationData> parse = AnnotationDataParse.parseAnnotations(field);
				child.setFieldAnnotations(parse);
			}

			ViewData viewData = child.getAnnotationData(ViewData.class);

			if (viewData == null) {//该field没有View标签
				continue;
			}
			Object fieldValue = BeanUtil.getValue(data, field.getName());//字段的初始值
			child.fieldValue = fieldValue;
			child.parent = parentFormField;

			String group = viewData.group();//分组信息
			int index = viewData.index();//索引信息

			if (group != null && !"".equals(group)) {//有自定义的group，以自定义为主
				if (!groupFieldsMap.containsKey(group)) {//不存在初始化
					groupFieldsMap.put(group, new ArrayList<BeanField>(), index);
				}

				if (BeanUtil.isUserDefinedClass(field.getType())) {//是自定义对象，采用自定义的group递归收集
					collectUseBeanFields(group, groupFieldsMap, data, child, fieldOverrideInfo);
				} else { //简单对象，直接加到该group的列表中
					List<BeanField> list = groupFieldsMap.get(group);
					addGroupFields(list, index, child);
				}

			} else if (BeanUtil.isUserDefinedClass(field.getType())) {//没有自定义group，以传入的groupName为组
				collectUseBeanFields(groupName, groupFieldsMap, data, child, fieldOverrideInfo);
			} else {//简单对象，直接加到传入groupName的列表中
				List<BeanField> list = groupFieldsMap.get(groupName);
				addGroupFields(list, index, child);
			}
		}
	}

	/**
	 * @param groupName
	 * @param groupFieldsMap
	 * @param child
	 */
	private void addGroupFields(List<BeanField> list, int index, BeanField child) {
		if (index == -1 || list.size() < index) {
			list.add(child);
		} else {
			list.add(index, child);
		}

	}

	/**
	 * @param groupFieldsMap
	 * @param data
	 * @param field
	 * @param fieldValue
	 * @param child
	 * @param fieldOverrideInfo 
	 * @param group
	 * @param controlType
	 * @param list
	 */
	@SuppressWarnings("rawtypes")
	private void collectUseBeanFields(String groupName, IndexHashMap<String, List<BeanField>> groupFieldsMap,
			Object data, BeanField child, Map fieldOverrideInfo) {
		Object fieldValue = child.fieldValue;
		Field field = child.field;
		ViewData viewData = child.getAnnotationData(ViewData.class);
		String controlType = viewData.type();//控件类型
		List<BeanField> list = groupFieldsMap.get(groupName);
		//FIXME 先获取Wiew的扩展属性
		ViewExpansion viewExpansion = field.getAnnotation(ViewExpansion.class);
		if (viewExpansion != null && !viewExpansion.viewClass().equals("")) {
			Class<?> type = getViewType(viewExpansion);
			if (fieldValue == null) {
				try {
					fieldValue = BeanUtil.newInstanceObject(type);
					BeanUtil.setValue(data, field, fieldValue);
					fieldValue = BeanUtil.getValue(data, field);
				} catch (Exception e) {
					logger.error("", e);
				}
			}
			child.fieldValue = fieldValue;
			collectFields(child, type, groupName, groupFieldsMap, fieldOverrideInfo);

		} else {

			if (controlType.equalsIgnoreCase("Text")) {
				if (fieldValue == null) {
					try {
						fieldValue = BeanUtil.newInstanceObject(field.getType());
						BeanUtil.setValue(data, field, fieldValue);
						fieldValue = BeanUtil.getValue(data, field);
					} catch (Exception e) {
						logger.error("", e);
					}
				}
				child.fieldValue = fieldValue;
				collectFields(child, field.getType(), groupName, groupFieldsMap, fieldOverrideInfo);
			} else {
				addGroupFields(list, viewData.index(), child);
				//				list.add(child);
			}
		}
	}

	/**
	 * 
	 * @param viewExpansion
	 * @return
	 */
	private final Class<?> getViewType(ViewExpansion viewExpansion) {
		String viewClass = viewExpansion.viewClass();
		String className = SystemPropertyUtil.resolvePlaceholders(viewClass);
		return ClassUtil.forName(className);
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public IndexHashMap<String, List<BeanField>> collectFields(Object data, Class<?> dataClazz) {
		IndexHashMap<String, List<BeanField>> groupFields = initGroupMap();
		//查找是否有覆写的Form
		Map formMap = AnnotationOverrideManager.lookupOverride(data.getClass());
		isOverride = formMap != null;
		BeanField parent = new BeanField(null, data, null);
		collectFields(parent, dataClazz, NONE_GROUP, groupFields, formMap);
		return groupFields;
	}

}
