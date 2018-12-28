/*
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;

public class ButtonGroup extends EnhanceComposite implements IInputControl {

	private final int style;

	/**
	 * 返回唯一结果int值（即多个结果或值）；默认false，即返回List结果集
	 */
	private boolean uniqueIntResult;

	private Object objectValue;

	private Button[] buttons;

	private String[] labels;

	private Object[] values;

	private GridLayout layout;

	private List<String> checkedLabels;

	public GridLayout getLayout() {
		return layout;
	}

	public ButtonGroup(Composite parent, int numColumns, boolean uniqueIntResult, int style) {
		super(parent, style);
		this.style = style;
		layout = new GridLayout();
		layout.numColumns = numColumns;
		this.uniqueIntResult = uniqueIntResult;
		this.setLayout(layout);
		this.setBackground(parent.getBackground());
	}

	public ButtonGroup(Composite parent, int style) {
		super(parent, style);
		this.style = style;
		layout = new GridLayout();
		layout.numColumns = 4;
		this.setLayout(layout);
		this.setBackground(parent.getBackground());
	}

	public String[] getLabels() {
		return this.labels;
	}

	public void setLabels(String[] values) {
		if (values == null) {
			return;
		}
		buttons = new Button[values.length];
		for (int i = 0; i < values.length; i++) {
			buttons[i] = new Button(this, style);
			buttons[i].setText(values[i]);
			buttons[i].setBackground(this.getBackground());
		}
		this.labels = values;
		reset();
		this.layout();
	}

	public Object[] getValues() {
		return this.values;
	}

	public void setValues(Object[] values) {
		this.values = values;
		for (int i = 0; i < values.length; i++) {
			buttons[i].setData(values[i]);
		}
	}

	@Override
	public void addListener(int arg0, Listener arg1) {
		if ((arg0 == SWT.FocusOut) || (arg0 == SWT.Selection)) {
			for (Button button : buttons) {
				button.addListener(arg0, arg1);
			}
		} else {
			super.addListener(arg0, arg1);
		}
	}

	@Override
	public void reset() {
		if (null != buttons) {
			for (int i = 0; i < buttons.length; i++) {
				buttons[i].setSelection(false);
			}
			if ((this.style & SWT.RADIO) != 0) {
				buttons[0].setSelection(true);
			}
		}
	}

	public Object getObjectValue() {
		if (!isEnabled()) {
			return null;
		}
		checkedLabels = new ArrayList<String>();
		int intValueResult = 0;
		if (uniqueIntResult) {
			for (int i = 0; i < buttons.length; i++) {
				if (buttons[i].getSelection()) {
					if (labels != null && labels.length >= i) {
						checkedLabels.add(labels[i]);
					} else {
						checkedLabels.add(i + "");
					}
					int bitCount = i;
					int tempValue = (1 << bitCount);
					intValueResult = intValueResult | tempValue;
				}
			}
			return intValueResult;
		}

		List<Object> listResult = new ArrayList<Object>();
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].getSelection()) {
				Object data = buttons[i].getData();
				if (labels != null && labels.length >= i) {
					checkedLabels.add(labels[i]);
				} else {
					checkedLabels.add(i + "");
				}
				if (data == null) {
					listResult.add(i);
				} else {
					listResult.add(data);
				}
			}
		}
		this.objectValue = listResult;
		if ((this.style & SWT.RADIO) != 0) {
			// 如果是RadioButton，则返回索引值，默认索引值为其真实的数据值
			return listResult.get(0);
		}

		return this.objectValue;
	}

	private void setValue(Object value) {
		// reset();

		boolean isRadioGroup = (this.style & SWT.RADIO) != 0;//判断是否是单选

		if (uniqueIntResult && !isRadioGroup) {//intRelust 多选情况下返回单一int值 
			int intValue = Integer.decode(value + "");
			setUniqueIntResult(intValue);
			return;
		}

		if (isRadioGroup && isValuesEmpty()) {//单选模式下，且value是为空
			int v = 0;
			if (Number.class.isAssignableFrom(value.getClass())) {//如果是数字
				Number val = (Number) value;
				v = val.intValue();
			} else {//如果不是数字类型,因为 values 为空,直接返回
				return;
			}
			//设置按钮选中状态
			for (int i = 0; i < buttons.length; i++) {
				if (i == v) {
					buttons[i].setSelection(true);
				} else {
					buttons[i].setSelection(false);
				}
			}
			return;
		}

		for (int i = 0; i < buttons.length; i++) {
			Object data = buttons[i].getData();
			if ((data != null && data instanceof String && data.equals(String.valueOf(value)))
					|| (value.equals(data))) {
				buttons[i].setSelection(true);
			} else {
				// 单选时才把其他button设为false，多选时不用设
				if (isRadioGroup) {
					buttons[i].setSelection(false);
				}
			}
		}
	}

	/**
	 * @return
	 */
	public final boolean isValuesEmpty() {
		return this.values == null || this.values.length == 0;
	}

	/**
	 * 多选情况下返回单一int值 
	 * @param intValue
	 */
	private void setUniqueIntResult(int intValue) {
		for (int i = 0; i < buttons.length; i++) {
			int tempValue = 1 << i;
			if ((intValue & tempValue) != 0) {
				buttons[i].setSelection(true);
			} else {
				buttons[i].setSelection(false);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void setObjectValue(Object objectValue) {
		reset();
		if (null == objectValue) {
			return;
		}
		if (objectValue.getClass().isArray()) {
			Object[] values = (Object[]) objectValue;
			setArrayValue(values);
		} else if (objectValue instanceof List) {
			List values = (List) objectValue;
			setListValue(values);
		} else {
			setValue(objectValue);
		}
	}

	/**
	 * @param objectValue
	 */
	private final void setListValue(List<?> values) {
		for (Object value : values) {
			if (isValuesEmpty()) {
				if (Number.class.isAssignableFrom(value.getClass())) {//如果是数字
					Number val = (Number) value;
					buttons[val.intValue()].setSelection(true);
				} else {//如果不是数字类型,因为 values 为空,直接返回
					return;
				}
			} else
				setValue(value);
		}
	}

	/**
	 * @param values
	 */
	private final void setArrayValue(Object[] values) {
		for (Object value : values) {
			if (isValuesEmpty()) {
				if (Number.class.isAssignableFrom(value.getClass())) {//如果是数字
					Number val = (Number) value;
					buttons[val.intValue()].setSelection(true);
				} else {//如果不是数字类型,因为 values 为空,直接返回
					return;
				}
			} else
				setValue(value);
		}
	}

	public String[] getCheckedLabels() {
		String[] a = new String[checkedLabels.size()];
		return checkedLabels.toArray(a);
	}

}
