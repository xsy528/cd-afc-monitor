package com.insigma.commons.ui.form;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.anotation.ListType;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.ui.widgets.TableView;

public class PropertyViewer extends TableView {

	private Log logger = LogFactory.getLog(PropertyViewer.class);

	protected int mode;

	protected Class<?> dataClass;

	private boolean isNeedSubGroupName;

	public PropertyViewer(Composite parent, int style) {

		super(parent, style | SWT.BORDER);

		setHeaderVisible(true);

		setLinesVisible(true);

		TableColumn colum1 = new TableColumn(this, SWT.NONE);
		colum1.setWidth(200);
		colum1.setText("名称");

		TableColumn colum2 = new TableColumn(this, SWT.NONE);
		colum2.setWidth(300);
		colum2.setText("值");

		TableColumn colum3 = new TableColumn(this, SWT.NONE);
		colum3.setWidth(50);
		colum3.setText("");
	}

	public void setObject(Object data) {
		setObject(null, data);
	}

	public void setObject(String groupName, Object data) {
		Field[] fields = data.getClass().getDeclaredFields();

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
				setObject(view.label(), value);
			} else {

				TableItem item = new TableItem(this, SWT.NONE);
				String labelValue = view.label();
				if (isNeedSubGroupName && null != groupName && !"".equals(groupName)) {
					labelValue += "(" + groupName + ")";
				}
				item.setText(0, labelValue);
				String target = value == null ? "" : value.toString();
				item.setText(1, target);
				item.setText(2, view.postfix());
				item.setData(data);
			}
		}
		computeSize(SWT.DEFAULT, SWT.DEFAULT);
	}

	public boolean isNeedSubGroupName() {
		return isNeedSubGroupName;
	}

	public void setNeedSubGroupName(boolean isNeedGroupName) {
		this.isNeedSubGroupName = isNeedGroupName;
	}
}
