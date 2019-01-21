package com.insigma.commons.ui.form;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;

import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.anotation.ColumnView;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.ui.anotation.ViewExpansion;
import com.insigma.commons.ui.convert.ColumnConvertor;
import com.insigma.commons.ui.widgets.TableView;
import com.insigma.commons.util.SystemPropertyUtil;
import com.swtdesigner.SWTResourceManager;

public class BeanTableViewer extends TableView {

	protected Object beanData;

	protected int numColumns = 2;

	protected List<FieldBean> fieldBeans;

	private Class<?> dataClass;

	private static Log logger = LogFactory.getLog(BeanTableViewer.class);

	private Object beanDataPre;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public BeanTableViewer(Composite parent, int numColumns, int style) {
		super(parent, style);
		fieldBeans = new ArrayList<FieldBean>();
		this.numColumns = numColumns;
		DEFAULT_COLUMN_WIDTH = 150;
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void clear() {
		removeAll();
		fieldBeans.clear();
	}

	public void setBeanData(Object beanData) {
		this.beanData = beanData;
		if (beanData == null) {
			removeAll();
			return;
		}
		final Class<? extends Object> clazz = beanData.getClass();
		//20131226 shenchao 增加判断 对象 不同就改
		if (dataClass == null || !dataClass.equals(clazz) || !this.beanData.equals(this.beanDataPre)) {
			//第一次进入 ，或Class变化时才需要收集Fields
			clear();
			collectFields(beanData, null, clazz);
			newColunms();
		}
		//收集填充数据
		final List<Object[]> collectRows = collectRows(beanData);
		//填充数据
		fillArrayList(collectRows);
		dataClass = clazz;
		layout();
		this.beanDataPre = beanData;
	}

	/**
	 * 生成列
	 */
	private void newColunms() {
		final String[] column = getColumn();
		if (column == null || column.length != (numColumns * 2)) {
			String[] columns = new String[numColumns * 2];
			for (int i = 0; i < columns.length; i++) {
				columns[i] = "";
			}
			setColumn(columns);
		}
	}

	private List<Object[]> collectRows(Object beanData) {
		List<Object[]> rows = new ArrayList<Object[]>();
		int index = 0;
		int totalRows = (int) Math.ceil(fieldBeans.size() / (double) numColumns);
		for (int row = 0; row < totalRows; row++) {
			Object[] values = new Object[numColumns * 2];
			int count = 0;
			while (count < numColumns * 2) {
				if (index >= fieldBeans.size()) {
					break;
				}
				final FieldBean fieldBean = fieldBeans.get(index++);
				values[count++] = fieldBean.getText();
				values[count++] = fieldBean.getValue();
			}
			rows.add(values);
		}
		return rows;
	}

	private void collectFields(Object data, FieldBean parent, Class<?> cls) {

		if (cls.getSuperclass() != Object.class) {
			collectFields(data, parent, cls.getSuperclass());
		}
		Field[] declaredFields = cls.getDeclaredFields();
		for (Field field : declaredFields) {
			View view = field.getAnnotation(View.class);
			if (view == null) {
				continue;
			}
			FieldBean fieldBean = new FieldBean();
			fieldBean.setField(field);
			String text = view.label();
			fieldBean.setText(text);

			Object value = BeanUtil.getValue(data, field);
			if (view.format().length() > 0) {
				fieldBean.setValue(value == null ? "--" : String.format(view.format(), value));
			} else {
				fieldBean.setValue(value == null ? "--" : value);
			}

			ColumnView columnView = field.getAnnotation(ColumnView.class);
			if (columnView != null) {
				Class<? extends ColumnConvertor> convertor = columnView.convertor();
				try {
					if (convertor != null) {
						String convertorStr = convertor.newInstance().getText(null, value, 0, 0, null);
						fieldBean.setValue(convertorStr);
					}
				} catch (InstantiationException e) {

					e.printStackTrace();
				} catch (IllegalAccessException e) {

					e.printStackTrace();
				}
			} else {

			}

			fieldBean.setParent(parent);
			if (BeanUtil.isUserDefinedClass(field.getType()) && null != view) {
				//FIXME 先获取Wiew的扩展属性
				ViewExpansion viewExpansion = field.getAnnotation(ViewExpansion.class);
				if (viewExpansion != null && !viewExpansion.viewClass().equals("")) {
					Class<?> type = getViewType(viewExpansion);
					if (type == null) {
						continue;
					}
					if (value == null) {
						try {
							value = BeanUtil.newInstanceObject(type);
							BeanUtil.setValue(data, field, value);
							value = BeanUtil.getValue(data, field);
						} catch (Exception e) {
							logger.error(e);
						}
					}
					collectFields(value, fieldBean, type);

				} else {
					collectFields(value, fieldBean, field.getType());
				}
			} else {
				fieldBeans.add(fieldBean);
			}
		}
	}

	/**
	 * 
	 * @param viewExpansion
	 * @return
	 */
	private Class<?> getViewType(ViewExpansion viewExpansion) {
		String viewClass = viewExpansion.viewClass();
		String className = SystemPropertyUtil.resolvePlaceholders(viewClass);
		try {
			if (className.indexOf("${") != -1) {
				return null;
			}
			Class<?> clazz = Class.forName(className);
			return clazz;
		} catch (ClassNotFoundException e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * 填充 table
	 * 
	 * @param dataList
	 *            <code>List<Object[]></code>数组 ,如果dataList为空 或Null清空表内容
	 * @return
	 */
	public void fillArrayList(List<Object[]> dataList) {
		//		this.data = data;
		if (dataList == null) {
			removeAll();
			return;
		}
		startFill();
		for (int i = 0; i < dataList.size(); i++) {
			TableItem rowItem = getRowItem(i);
			Object[] rowDataArray = dataList.get(i);
			for (int j = 0; j < rowDataArray.length; j++) {
				if (rowDataArray[j] != null) {
					rowItem.setText(j, String.valueOf(rowDataArray[j]));
				} else {
					rowItem.setText(j, DEFAULT_NULL_VALUE);
				}
				if (j % 2 == 0) {
					//					rowItem.setFont(j, SWTResourceManager.getBoldFont(getFont()));
				} else {
					rowItem.setForeground(j, SWTResourceManager.getColor(SWT.COLOR_BLUE));
				}
			}
			rowIndex++;
		}
		endFill();
	}

	public class FieldBean {

		private Field field;

		private String text;

		private Object value;

		private FieldBean parent;

		public Field getField() {
			return field;
		}

		public Object getValue(Object data) {
			if (parent != null) {
				Object value = BeanUtil.getValue(data, parent.getField());
				return value == null ? null : BeanUtil.getValue(value, field);
			}
			return BeanUtil.getValue(data, field);
		}

		public void setField(Field field) {
			this.field = field;
		}

		public String getText() {
			if (parent != null) {
				return parent.getText() + text;
			}
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		/**
		 * @return the parent
		 */
		public FieldBean getParent() {
			return parent;
		}

		/**
		 * @param parent the parent to set
		 */
		public void setParent(FieldBean parent) {
			this.parent = parent;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

	}

}
