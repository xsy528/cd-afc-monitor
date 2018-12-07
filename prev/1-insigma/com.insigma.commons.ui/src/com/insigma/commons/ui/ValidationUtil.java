package com.insigma.commons.ui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.anotation.ListType;
import com.insigma.commons.ui.anotation.Validation;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.ui.widgets.IInputControl;
import com.swtdesigner.SWTResourceManager;

public class ValidationUtil {
	private final static Log logger = LogFactory.getLog(ValidationUtil.class);

	private static List<Field> findKeyFieldList(Class<?> cls) {
		List<Field> keyFields = new ArrayList<Field>();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			Validation validation = field.getAnnotation(Validation.class);
			if (null != validation && validation.key() == true) {
				keyFields.add(field);
			}
		}
		return keyFields;

	}

	/**
	 * 判断对象有效性，返回错误信息。
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String objectValidateMessage(Object obj) {
		if (obj == null) {
			return null;
			// return "传入对象为空";
		}

		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Object fieldObj = null;
			fieldObj = BeanUtil.getValue(obj, field);
			if (null != fieldObj) {
				ListType listType = field.getAnnotation(ListType.class);
				if (null != listType) {// 如果是list
					List<?> list = new ArrayList();

					Object listObj = fieldObj;
					if (listObj.getClass().isArray()) {
						Object[] objArr = (Object[]) listObj;
						list = Arrays.asList(objArr);

					} else if (BeanUtil.isUserDefinedClass(fieldObj.getClass())) {
						String objValidateMessage = objectValidateMessage(fieldObj);
						if (null != objValidateMessage)
							return objValidateMessage;
					} else
						list = (List<?>) listObj;

					for (Object object : list) {
						if (!BeanUtil.isUserDefinedClass(object.getClass())) {
							String fieldValidateMessage = validateMessage(field, object);
							if (null != fieldValidateMessage)
								return fieldValidateMessage;
						} else {
							String objValidateMessage = objectValidateMessage(object);
							if (null != objValidateMessage)
								return objValidateMessage;
						}
					}

					if (list.size() != 0 && findKeyFieldList(list.get(0).getClass()).size() != 0) {
						List<Field> keyFieldList = findKeyFieldList(list.get(0).getClass());
						String keyVldMessage = keyValidateMessage(list, keyFieldList, null);
						if (null != keyVldMessage)
							return keyVldMessage;
					}

				} else if (BeanUtil.isUserDefinedClass(fieldObj.getClass())) {
					String objValidateMessage = objectValidateMessage(fieldObj);
					if (null != objValidateMessage)
						return objValidateMessage;
				} else {
					if (null != field.getAnnotation(Validation.class)) {
						String fieldValidateMessage = validateMessage(field, fieldObj);
						if (null != fieldValidateMessage)
							return fieldValidateMessage;
					}
				}
			}

		}
		return null;
	}

	// 判断对象是否有效
	public static boolean isValidate(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Object fieldObj = null;
			fieldObj = BeanUtil.getValue(obj, field);
			if (null != fieldObj) {
				if (null != field.getAnnotation(ListType.class)) {// 如果是list
					List<?> list = null;

					Object listObj = fieldObj;
					if (listObj instanceof Arrays) {
						list = Arrays.asList((Arrays) listObj);
					} else
						list = (List<?>) listObj;

					for (Object object : list) {
						if (!isValidate(object))
							return false;
					}
					if (list.size() != 0 && findKeyFieldList(list.get(0).getClass()).size() != 0) {
						if (null != keyValidateMessage(list, findKeyFieldList(list.get(0).getClass()), null))
							return false;
					}

				} else if (BeanUtil.isUserDefinedClass(fieldObj.getClass())) {
					if (!isValidate(fieldObj))
						return false;
				} else {
					if (null != field.getAnnotation(Validation.class)) {
						if (null != validateMessage(field, fieldObj))
							return false;
					}
				}
			}

		}
		return true;
	}

	// 判断是否有Validation annotation
	// public static boolean isValidatable(Object obj) {
	// Field[] fields = obj.getClass().getDeclaredFields();
	// for (Field field : fields) {
	// if (null != field.getAnnotation(Validation.class))
	// return true;
	// }
	// return false;
	// }

	/**
	 * 检查冲突
	 */
	public static String keyValidateMessage(List<?> dataList, List<Field> keyFieldList, Object data) {
		if (keyFieldList == null)
			return null;
		String messageString = null;
		for (Object obj : dataList) {
			try {
				if (null == data) {
					for (Object object : dataList) {
						return keyValidateMessage(dataList, keyFieldList, object);
					}

				} else {
					if (obj != data) {// 自己检查冲突
						int count = 0;
						for (Field field : keyFieldList) {
							field.setAccessible(true);
							if (isEquals(BeanUtil.getValue(obj, field), BeanUtil.getValue(data, field))) {
								count++;
							}
						}
						if (count != 0 && count == keyFieldList.size()) {
							View table = data.getClass().getAnnotation(View.class);
							return table.label() + "数据:key值冲突";
						}
					}
				}

			} catch (Exception e) {
				logger.equals("类型验证出错" + e);
			}

		}
		return messageString;
	}

	// 简单相等，如果key值是对象则应该重写相等函数。
	private static boolean isEquals(Object o, Object d) {

		return o.equals(d);
	}

	public static String validateMessage(Field field, Object value) {
		Validation validate = field.getAnnotation(Validation.class);
		if (null == value || null == validate)
			return null;
		String messageString = null;
		try {
			if (validate.rangeable()) {
				if (Integer.valueOf((value + "").trim()) > validate.maxValue()) {
					messageString = ":最大值不能超过" + validate.maxValue();
					return field.getAnnotation(View.class).label() + messageString;
				}
			}
			if (validate.rangeable()) {
				if (Integer.valueOf((value + "").trim()) < validate.minValue()) {

					messageString = ":最小值不能低于" + validate.minValue();
					return field.getAnnotation(View.class).label() + messageString;
				}
			}
			if ("" != validate.regexp()) {
				Pattern pattern = Pattern.compile(validate.regexp());
				Matcher m = pattern.matcher(value.toString());
				String describ = validate.describ();
				if (!m.find()) {
					if (describ.trim().equals("")) {
						messageString = "输入内容不符合对应字段的格式" + validate.regexp();
					} else {
						messageString = describ;
					}
				}

			}

		} catch (Exception e) {
			logger.equals("类型验证出错" + e);
		}

		return messageString;

	}

	public static boolean doValidate(IInputControl inputControl, Field field, Object value) {
		String describ = ValidationUtil.validateMessage(field, value);
		View view = field.getAnnotation(View.class);
		if (null != describ) {
			MessageForm.Message("警告信息", "[" + view.label() + "]输入错误：" + describ, SWT.ICON_WARNING);
			return false;
		}
		return true;
	}

	public final static Color color_red = SWTResourceManager.getColor(SWT.COLOR_RED);
}
