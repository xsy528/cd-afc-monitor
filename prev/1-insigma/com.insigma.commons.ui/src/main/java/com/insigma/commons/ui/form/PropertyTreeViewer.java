/* 
 * 日期：2010-7-21
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.form;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.ListType;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.ui.form.util.ShowUtil;
import com.insigma.commons.ui.widgets.Tree;

@SuppressWarnings("unchecked")
public class PropertyTreeViewer extends Tree implements IPropertyViewer {

	private Log logger = LogFactory.getLog(PropertyTreeViewer.class);

	protected Object data;

	protected List<?> dataList;

	protected Class dataClass;

	protected int mode;

	public Class getDataClass() {
		return dataClass;
	}

	public void setDataClass(Class dataClass) {
		this.dataClass = dataClass;
	}

	public PropertyTreeViewer(Composite parent, int style) {
		super(parent, style | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);

		setHeaderVisible(true);
		setLinesVisible(true);

		TreeColumn colum1 = new TreeColumn(this, SWT.NONE);
		colum1.setWidth(300);
		colum1.setText("名称");

		TreeColumn colum2 = new TreeColumn(this, SWT.NONE);
		colum2.setWidth(200);
		colum2.setText("值");

		// TreeColumn colum3 = new TreeColumn(this, SWT.NONE);
		// colum3.setWidth(50);
		// colum3.setText("");
	}

	public Object getObject() {
		return data;
	}

	public void setObject(Object obj) {
		dataClass = obj.getClass();
		setObject(null, obj);
		this.data = obj;
	}

	public void setObject(TreeItem parent, Object data) {
		Field[] fields = data.getClass().getDeclaredFields();
		boolean needViewGroup = false;
		if (BeanUtil.isUserDefinedClass(data.getClass())) {
			for (Field field : fields) {
				View view = field.getAnnotation(View.class);
				// 打view、非ListType且非用户自定义类型才显示group
				if (view != null && field.getAnnotation(ListType.class) == null
						&& !BeanUtil.isUserDefinedClass(field.getType())) {
					needViewGroup = true;
					break;
				}
			}
		}

		for (Field field : fields) {
			View view = field.getAnnotation(View.class);
			if (view == null) {
				continue;
			}

			ListType listype = field.getAnnotation(ListType.class);
			if (listype != null) {
				continue;
			}
			Object value = BeanUtil.getValue(data, field.getName());

			//2014-02-21 shenchao
			DataSource annotation = field.getAnnotation(DataSource.class);
			if (annotation != null && view != null && view.type().equals("Combo")) {
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

			TreeItem childItem = null;
			if (needViewGroup) {
				if (null == parent) {
					childItem = new TreeItem(this, SWT.NONE);
				} else {
					childItem = new TreeItem(parent, SWT.NONE);
				}
			} else {
				if (null == parent) {
					childItem = new TreeItem(this, SWT.NONE);
				} else
					childItem = parent;
			}

			if (BeanUtil.isUserDefinedClass(field.getType())) {
				// 初始化
				if (null == value) {
					try {
						value = field.getType().newInstance();
						BeanUtil.setValue(data, field, value);
					} catch (InstantiationException e) {
						logger.error("用户自定义对象实例化异常", e);
					} catch (IllegalAccessException e) {
						logger.error("方法不存在", e);
					}
				}
				childItem.setText(0, view.label());
				childItem.setText(1, "");
				// childItem.setText(2, "");
				setObject(childItem, value);
			} else if (field.getType() == byte[].class && !"".equals(view.format())) { // 添加byte[]数组显示
				String strValue = "";
				if (value != null) {
					byte[] bArray = (byte[]) value;
					for (byte b : bArray) {
						strValue += String.format(view.format(), b) + " ";
					}
					strValue = strValue.trim();
				}
				childItem.setText(0, view.label());
				childItem.setText(1, strValue);
				childItem.setData(data);
			} else {
				childItem.setText(0, view.label());
				childItem.setText(1, ShowUtil.getItemText(field, value, data));
				// childItem.setText(2, view.postfix());
				childItem.setData(data);
			}
		}
		computeSize(SWT.DEFAULT, SWT.DEFAULT);
	}

}
