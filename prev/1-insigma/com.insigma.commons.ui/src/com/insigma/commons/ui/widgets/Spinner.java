/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

public class Spinner extends org.eclipse.swt.widgets.Spinner implements IInputControl {

	private int defaultValue;

	private String expression;

	public Spinner(Composite arg0, int arg1) {
		super(arg0, arg1);
		addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}

			public void widgetSelected(SelectionEvent arg0) {
				// 验证正则表达式
				if (expression != null) {
					if (!checkExpression(getSelection() + "", expression)) {
						setSelection(getSelection() - 1);
					}
				}
			}

		});
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				// 验证正则表达式
				if (expression != null) {
					checkExpression(e, expression);
				}
			}
		});
	}

	protected void checkSubclass() {
	}

	public void reset() {
		setSelection(defaultValue);
	}

	public Object getObjectValue() {
		return getSelection();
	}

	public void setObjectValue(Object value) {

		if (value == null) {
			setSelection(0);
		}

		if (value instanceof Integer) {
			setSelection(((Integer) (value)));
		}
		if (value instanceof Short) {
			Short shortValue = (Short) value;
			setSelection(shortValue.intValue());
		}
		if (value instanceof Long) {
			Long longValue = (Long) value;
			setSelection(longValue.intValue());
		}
	}

	public int getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(int defaultValue) {
		this.defaultValue = defaultValue;
	}

	// 正则表达式限制输入
	private void checkExpression(KeyEvent e, String expression) {
		String result = getSelection() + String.valueOf(e.character);
		if (checkExpression(result, expression)) {
			return;
		}
		if (checkKey(e)) {
			return;
		}
		e.doit = false;
	}

	/**
	 * @param expression
	 * @param result
	 */
	private boolean checkExpression(String result, String expression) {
		Pattern p = Pattern.compile(expression);
		Matcher m = p.matcher(result);
		if (m.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 验证特殊键
	 * 
	 * @param e
	 * @return
	 */
	private boolean checkKey(KeyEvent e) {
		switch (e.keyCode) {
		case SWT.BS:
		case SWT.CR:
		case SWT.DEL:
		case SWT.LF:
		case SWT.ESC:
		case SWT.TAB:
		case 16777220:
		case 16777219:
		case 16777217:
		case 16777218:
			return true;
		}
		return false;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

}
