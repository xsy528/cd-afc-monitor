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

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.ui.casecade.CasecadeEvent;
import com.insigma.commons.ui.casecade.CasecadeListener;
import com.insigma.commons.ui.casecade.ICasecadeControl;

public class Combo extends org.eclipse.swt.widgets.Combo implements IInputControl, ICasecadeControl {

	private int defaultValue = -1;

	//内存中的默认值，暂供级联修改时使用
	private Object value = null;

	private Object[] values;

	private String fieldName;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public Combo(Composite parent, int style) {
		super(parent, style);
		addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				for (ICasecadeControl control : casecadeControls) {
					CasecadeEvent casecadeEvent = new CasecadeEvent(fieldName, Combo.this, control);
					control.valueChanged(casecadeEvent);
				}
			}
		});
		// addSelectionListener(new SelectionAdapter() {
		// public void widgetSelected(SelectionEvent arg0) {
		// for (ICasecadeControl control : casecadeControls) {
		// CasecadeEvent casecadeEvent = new CasecadeEvent(fieldName, Combo.this, control);
		// control.valueChanged(casecadeEvent);
		// }
		// }
		// });
	}

	protected void checkSubclass() {
	}

	public void reset() {
		if (defaultValue == -1) {
			// 下拉列表不为空时，默认选择第一个数据
			if (values != null && values.length > 0) {
				defaultValue = 0;
				select(defaultValue);
			} else {
				deselectAll();
			}
		} else
			select(defaultValue);
	}

	public Object getObjectValue() {
		if (isDisposed()) {
			return null;
		}
		if (getText().trim().equals("")) {
			return null;
		}
		if (values == null) {
			return getSelectionIndex();
		} else {
			if (getSelectionIndex() >= 0 && getSelectionIndex() < values.length) {
				return values[getSelectionIndex()];
			}
		}
		return null;
	}

	public void setObjectValue(Object objectValue) {
		if (objectValue != null) {
			if (values != null) {
				select(getIndex(objectValue));
			} else {
				select(Integer.valueOf(objectValue.toString()));
				setValue(objectValue);
			}
		}
	}

	private int getIndex(Object val) {
		for (int i = 0; i < values.length; i++) {
			if (values[i].toString().equals(String.valueOf(val)) || values[i].equals(val)) {
				return i;
			}
		}
		return -1;
	}

	public int getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(int defaultValue) {
		this.defaultValue = defaultValue;
		reset();
	}

	public Object[] getValues() {
		return this.values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}

	public boolean isEmpty() {
		if ((getText().trim().equals("") || getSelectionIndex() == -1)) {
			return true;
		}
		return false;
	}

	private List<ICasecadeControl> casecadeControls = new ArrayList<ICasecadeControl>();

	private List<CasecadeListener> casecadeListeners = new ArrayList<CasecadeListener>();

	@Override
	public void addCasecadeListener(CasecadeListener casecadeListener) {
		if (casecadeListener == null) {
			return;
		}
		casecadeListeners.add(casecadeListener);
	}

	@Override
	public void addCasecadeControl(ICasecadeControl casecadeControl) {
		casecadeControls.add(casecadeControl);
		final Object objectValue = casecadeControl.getObjectValue();
		CasecadeEvent casecadeEvent = new CasecadeEvent(fieldName, this, casecadeControl);
		casecadeControl.valueChanged(casecadeEvent);
		casecadeControl.setObjectValue(objectValue);
	}

	@Override
	public void valueChanged(CasecadeEvent casecadeEvent) {
		for (CasecadeListener listener : casecadeListeners) {
			listener.valueChanged(casecadeEvent);
		}
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
