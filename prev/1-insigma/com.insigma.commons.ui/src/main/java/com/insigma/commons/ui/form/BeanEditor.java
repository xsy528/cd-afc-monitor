/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.form;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.ValidationUtil;
import com.insigma.commons.ui.anotation.ListType;
import com.insigma.commons.ui.anotation.Validation;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.ui.form.util.ShowUtil;
import com.insigma.commons.ui.widgets.EnhanceComposite;
import com.insigma.commons.ui.widgets.IControl;
import com.insigma.commons.ui.widgets.IInputControl;
import com.insigma.commons.ui.widgets.Label;
import com.swtdesigner.SWTResourceManager;

public class BeanEditor extends ScrolledComposite implements IPropertyViewer, IControl {

	private final String ITEM_DATA_KEY_PREFIX = "DATA";

	private final String ITEM_COMPARE_KEY_PREFIX = "COMPARE_DATA";

	private final String ITEM_FIELD_KEY_PREFIX = "FIELD";

	protected Object compareData;

	protected Object data;

	protected Class<?> dataClass = null;

	protected HashMap<Field, IInputControl> hashMap;

	protected int mode;

	protected EnhanceComposite composite;

	protected GridLayout gridLayout;

	protected boolean isNeedSubGroup = false;

	protected boolean isNeedToolTipText = false;

	private GridData editorGridData = new GridData();

	private BeanEditorLayoutData beanEditorLayoutData = new BeanEditorLayoutData(260, 180, 1);

	protected IEditorChangedListener changedListener;

	public BeanEditor(Composite parent, int style) {

		super(parent, style | SWT.H_SCROLL | SWT.V_SCROLL);

		hashMap = new HashMap<Field, IInputControl>();
		composite = new EnhanceComposite(this, SWT.NONE);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		composite.setLayout(gridLayout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		setContent(composite);
		setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		composite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
	}

	public void setCompareData(Object compareData) {
		this.compareData = compareData;
	}

	@Override
	public Object getData() {
		return data;
	}

	/**
	 * 取得数据信息
	 */
	public Object getObject() {
		return data;
	}

	public void updateObject() {
		updateObject(data);
	}

	/**
	 * 根据界面信息更新数据
	 * 
	 * @param data 数据
	 */
	private void updateObject(Object data) {

		if (null != compareData && !compareData.getClass().equals(data.getClass())) {
			compareData = null;
		}

		Field[] fields = data.getClass().getDeclaredFields();

		for (Field field : fields) {
			if (field.getAnnotation(ListType.class) != null) {
				continue;
			}

			View view = field.getAnnotation(View.class);
			if (view == null) {
				continue;
			}

			if (BeanUtil.isUserDefinedClass(field.getType())) {
				Object value = BeanUtil.getValue(data, field.getName());
				updateObject(value);
			} else {
				IInputControl control = hashMap.get(field);
				Object inputValue = control.getObjectValue();
				if (null != inputValue) {
					Object value = BeanUtil.convert(inputValue, field.getType());
					// 记录修改状态
					if (null != changedListener && !value.equals(BeanUtil.getValue(data, field))) {
						Event event = new Event();
						event.item = (Widget) control;
						event.data = inputValue;
						changedListener.editorChanged(event, true);
					}
					BeanUtil.setValue(data, field, value);
				}
			}
		}
	}

	/**
	 * 清空界面信息
	 */
	public void clear() {
		hashMap.clear();
		composite.clear();
	}

	/**
	 * 设置界面信息
	 */
	public void setObject(Object data) {

		this.data = data;

		if ((dataClass == null) || !dataClass.equals(data.getClass())) {

			clear();

			dataClass = ((BeanField) data).fieldValue.getClass();

			if (null != compareData && !compareData.getClass().equals(dataClass)) {
				compareData = null;
			}

			createControl(composite, "基本信息", data, dataClass, compareData);

			composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			composite.layout();
			layout();
		}

	}

	public void setSimpleTypeObject(Field field, Object obj) {
		this.data = obj;
		clear();

		View view = field.getAnnotation(View.class);
		final Class classType = data.getClass();
		if (null != view) {
			Label label = new Label(composite, SWT.NONE);
			label.setText(view.label().trim() + ": ");
			label.setBackground(this.getBackground());

			// 创建控件
			final IInputControl inputControl = WidgetsFactory.getInstance().create(composite, field, data);
			final Control control = (Control) inputControl;

			initEditorGridData();

			// 设置控件长度
			control.setLayoutData(editorGridData);
			control.setData(ITEM_FIELD_KEY_PREFIX, field);

			control.addListener(SWT.KeyUp, new Listener() {
				public void handleEvent(Event arg0) {

					Field field = (Field) control.getData(ITEM_FIELD_KEY_PREFIX);

					Object value = getValueByInputControl(inputControl, classType);

					checkValidation(inputControl, field, value);

					notifyChangeListener((Widget) inputControl, value);

					data = value;
				}
			});

			// 显示单位Label
			Label postlabel = createLabel(composite, view.postfix());
		}

		composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		composite.layout();
		layout();
	}

	private void initEditorGridData() {
		editorGridData.horizontalAlignment = GridData.FILL;
		editorGridData.widthHint = beanEditorLayoutData.inputControlWidth;
	}

	private void createControl(Composite composite, String groupName, Object data, Class<?> dataclass,
			Object compareData) {
		Field[] fields = dataclass.getDeclaredFields();

		// 构建GROUP
		if (isNeedSubGroup) {
			boolean needViewGroup = needViewGroup(dataclass);

			// 如果view的个数少于一个，不显示
			if (needViewGroup) {
				composite = createGroup(groupName, this.composite);
			} else {
				composite = this.composite;
			}
		}

		initEditorGridData();

		for (Field field : fields) {

			final View view = field.getAnnotation(View.class);
			if (view == null) {
				continue;
			}

			if (field.getAnnotation(ListType.class) != null && !view.checkable()) {
				continue;
			}

			// 取得对应值
			Object value = BeanUtil.getValue(((BeanField) data).fieldValue, field.getName());
			BeanField subData = new BeanField(value);

			// 获取比较信息
			Object compareValue = null;
			if (null != compareData) {
				compareValue = BeanUtil.getValue(compareData, field.getName());
			}

			if (BeanUtil.isUserDefinedClass(field.getType())) {
				// 初始化
				BeanUtil.initValue(((BeanField) data).fieldValue, field, value);
				createControl(composite, view.label(), subData, field.getType(), compareValue);
			} else {

				// 显示的label信息
				Label label = createLabel(composite, view.label().trim() + ": ");
				if (isNeedToolTipText) {
					label.setToolTipText(label.getText());
				}

				// 创建控件
				final IInputControl inputControl = WidgetsFactory.getInstance().create(composite, field, data);
				final Control control = (Control) inputControl;
				GridData controlGridData = new GridData();
				controlGridData.horizontalAlignment = GridData.FILL;
				controlGridData.widthHint = beanEditorLayoutData.inputControlWidth;
				control.setLayoutData(controlGridData);

				control.setData(ITEM_DATA_KEY_PREFIX, ((BeanField) data).fieldValue);
				control.setData(ITEM_COMPARE_KEY_PREFIX, compareValue);
				control.setData(ITEM_FIELD_KEY_PREFIX, field);

				// 显示单位Label
				Label postlabel = createLabel(composite, view.postfix());

				// 比对内容控件
				final Label compareLabel = createLabel(composite, "");
				if (null != compareData) {
					compareLabel.setLayoutData(editorGridData);
					if (null != compareValue && !compareValue.equals(value)) {
						compareLabel.setText(
								ShowUtil.getCompareItemText(field, compareValue, ((BeanField) data).fieldValue, view));
					}
				}

				// 设置控件的hashMap
				hashMap.put(field, inputControl);

				control.addListener(SWT.FocusOut, new Listener() {
					public void handleEvent(Event arg0) {
						final Field field = (Field) control.getData(ITEM_FIELD_KEY_PREFIX);
						final Object data = control.getData(ITEM_DATA_KEY_PREFIX);
						final Object compareData = control.getData(ITEM_COMPARE_KEY_PREFIX);

						Object value = getValueByInputControl(inputControl, field.getType());

						checkValidation(inputControl, field, value);

						resetValueToParent(inputControl, field, value, data);

						compareValue(compareLabel, field, compareData, value);
					}
				});
			}
		}
		//刷新布局，否则布局无法生效-yangy
		layout();
	}

	private Group createGroup(String groupName, Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		// 设置group组名
		if (null == groupName) {
			groupName = "基本信息";
		}
		group.setToolTipText(groupName);
		group.setText(groupName);
		final GridData groupGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		groupGridData.horizontalSpan = gridLayout.numColumns;
		group.setLayoutData(groupGridData);
		final GridLayout groupGridLayout = new GridLayout();
		// 此GridLayout的列数目.
		groupGridLayout.numColumns = gridLayout.numColumns;
		// 控制一列中两个网络间组件的宽度,像素为单位.
		groupGridLayout.verticalSpacing = 5;
		// 控制左边和右边组件离边缘的距离空间,以像素为单位.
		groupGridLayout.marginWidth = 2;
		//				groupGridLayout.makeColumnsEqualWidth = true;
		group.setLayout(groupGridLayout);
		// 设置背景色
		group.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

		return group;
	}

	private boolean needViewGroup(Class dataClass) {
		boolean result = false;

		if (BeanUtil.isUserDefinedClass(dataClass)) {
			Field[] fields = dataClass.getDeclaredFields();
			for (Field field : fields) {
				View view = field.getAnnotation(View.class);
				// 打view、非ListType且非用户自定义类型才显示group
				if (view != null && field.getAnnotation(ListType.class) == null
						&& !BeanUtil.isUserDefinedClass(field.getType())) {
					result = true;
					break;
				}
			}
		}

		return result;
	}

	private Label createLabel(Composite parent, String value) {
		Label result = new Label(parent, SWT.NONE);
		result.setBackground(this.getBackground());
		result.setText(value);
		return result;
	}

	private void compareValue(final Label compareLabel, final Field field, final Object compareData, Object value) {
		if (null != compareData && !compareData.equals(value)) {
			final View view = field.getAnnotation(View.class);
			Display.getCurrent().asyncExec(new Runnable() {
				@Override
				public void run() {
					if (!compareLabel.isDisposed()) {
						compareLabel.setText(ShowUtil.getCompareItemText(field, compareData, data, view));
					}
				}
			});
		}
	}

	private void resetValueToParent(IInputControl inputControl, Field field, Object value, Object data) {

		//		Object inputValue = inputControl.getObjectValue();
		if (null != value) {
			// 记录修改状态
			if (!value.equals(BeanUtil.getValue(data, field))) {
				BeanUtil.setValue(data, field, value);
				notifyChangeListener((Widget) inputControl, value);
			}
		} else {
			// String字段编辑内容全删后为null 2013-04-09
			if (BeanUtil.getValue(data, field) != null) {
				BeanUtil.setValue(data, field, value);
				notifyChangeListener((Widget) inputControl, value);
			}
		}
	}

	private void checkValidation(IInputControl inputControl, Field field, Object value) {
		// 检验数据有效性
		if (null != field.getAnnotation(Validation.class)) {
			// 修改提示
			if (inputControl instanceof Control) {
				Control control = (Control) inputControl;
				if (null != ValidationUtil.validateMessage(field, value)) {
					control.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
					control.setToolTipText(ValidationUtil.validateMessage(field, value));
				} else {
					control.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
					control.setToolTipText("");
				}
			}
			// 提示错误信息
			ValidationUtil.doValidate(inputControl, field, value);
		}
	}

	private Object getValueByInputControl(IInputControl inputControl, Class<?> classType) {
		Object inputValue = inputControl.getObjectValue();

		// 类型转化
		Object result = null;
		try {
			if (null != inputValue) {
				result = BeanUtil.convert(inputValue, classType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private void notifyChangeListener(Widget w, Object value) {
		if (null != changedListener) {
			Event event = new Event();
			event.item = w;
			event.data = value;
			changedListener.editorChanged(event, true);
		}
	}

	public void reset() {
	}

	/**
	 * 设置是否需要根据类对象分组显示
	 * 
	 * @param isNeedSubGroup
	 */
	public void setNeedSubGroup(boolean isNeedSubGroup) {
		this.isNeedSubGroup = isNeedSubGroup;
	}

	/**
	 * 设置单行显示的列数
	 * 
	 * @param numColumns
	 */
	public void setCompositeNumColumns(int columns) {
		gridLayout.numColumns = columns * 3;
	}

	public void setNeedToolTipText(boolean isNeedToolTipText) {
		this.isNeedToolTipText = isNeedToolTipText;
	}

	public IEditorChangedListener getChangedListener() {
		return changedListener;
	}

	public void setChangedListener(IEditorChangedListener changedListener) {
		this.changedListener = changedListener;
	}

	/**
	 * @return the beanEditorLayoutData
	 */
	public BeanEditorLayoutData getBeanEditorLayoutData() {
		return beanEditorLayoutData;
	}

	/**
	 * @param beanEditorLayoutData the beanEditorLayoutData to set
	 */
	public void setBeanEditorLayoutData(BeanEditorLayoutData beanEditorLayoutData) {
		if (beanEditorLayoutData != null) {
			this.beanEditorLayoutData = beanEditorLayoutData;
			gridLayout.numColumns = 3 * beanEditorLayoutData.numColumn;
		}
	}

}
