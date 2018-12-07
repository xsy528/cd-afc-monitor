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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;

import com.insigma.commons.codec.md5.MD5Util;
import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.casecade.CasecadeEvent;
import com.insigma.commons.ui.casecade.CasecadeListener;
import com.insigma.commons.ui.casecade.ICasecadeControl;
import com.insigma.commons.ui.form.widget.format.ITextFormater;
import com.insigma.commons.util.lang.NumberUtil;

public class Text extends org.eclipse.swt.widgets.Text implements IInputControl, ICasecadeControl {

	private Control accessControl;

	private String defaultText = "";

	private String expression;

	private Class<?> fieldType = String.class;

	protected Object objectValue;

	private ITextFormater textFormater;

	private String fieldName;

	private Boolean md5Format = false;

	private String preData = "";

	private int formatLength = -1;

	private Boolean zero = false;

	private int radix = -1;

	/**
	 * @param arg0
	 * @param arg1
	 */
	public Text(Composite arg0, int arg1) {
		super(arg0, arg1);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				// 回车事件绑定
				if (e.character == SWT.CR && accessControl != null) {
					accessControl.notifyListeners(SWT.Selection, new Event());
					return;
				}
				// 验证正则表达式
				if (expression != null) {
					checkExpression(e, expression);
				}

			}

			/* =================================== 以上部分为Text限制输入 =================================== */
			// 正则表达式限制输入
			private void checkExpression(KeyEvent e, String expression) {
				Pattern p = Pattern.compile(expression);
				String text = getText();
				int x = getSelection().x;
				int y = getSelection().y;
				String result = text.substring(0, x) + String.valueOf(e.character) + text.substring(y);
				Matcher m = p.matcher(result);
				if (m.matches()) {
					return;
				}
				if (checkKey(e)) {
					return;
				}
				e.doit = false;
			}

		});

		addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {

				if (!getEditable()) {
					return;
				}

				if (!isEnabled()) {
					return;
				}

				for (ICasecadeControl control : casecadeControls) {
					CasecadeEvent casecadeEvent = new CasecadeEvent(fieldName, Text.this, control);
					control.valueChanged(casecadeEvent);
				}
			}
		});

		addFocusListener(new FocusListener() {

			public void focusLost(FocusEvent e) {

				if (!isEnabled()) {
					return;
				}
				if (!getEditable()) {
					return;
				}

				// 验证正则表达式
				if (expression != null) {
					checkExpression(expression);
				}

				//hexingxing 
				if (formatLength != -1 && getText().length() < formatLength) {
					String addZeroForNum = addZeroForNum(getText(), formatLength, zero);
					objectValue = addZeroForNum;
					setText(objectValue.toString());
				}
				if (!md5Format) {
					Object convert = convert(getText());
					setObjectValue(convert);
					if (radix != -1 && objectValue != null) {
						setText(NumberUtil.transRadix(objectValue.toString(), fieldType, radix, 10));
						objectValue = getText();
					}
				} else {
					if (getText().equals("")) {
						setText(preData);
					}
					if (md5Format) {
						setText(MD5Util.MD5(getText()));
					}

				}
			}

			public void focusGained(FocusEvent e) {
				if (!isEnabled()) {
					return;
				}
				if (!getEditable()) {
					return;
				}
				if (md5Format) {
					preData = new String();
					if (objectValue != null) {
						preData = objectValue.toString();
					}
					setText("");
				} else if (radix != -1) {
					if (objectValue != null) {
						setText(NumberUtil.transRadix(objectValue.toString(), fieldType, 10, radix));
						objectValue = getText();
					} else {
						setText("0");
					}

				} else {
					if (objectValue == null) {
						setText("");
					} else {
						setText(objectValue.toString());
					}
				}
			}

			// 对输入结果进行正则校验
			private void checkExpression(String expression) {
				Pattern p = Pattern.compile(expression);
				String text = getText();
				Matcher m = p.matcher(text);
				if (m.matches()) {
					return;
				}
				setText(defaultText);
			}
		});
	}

	String getChangeText(KeyEvent e) {
		Point p = this.getSelection();
		String oldstr = getText();
		String newstr = oldstr.substring(0, p.x) + e.character + oldstr.substring(p.y);
		return newstr;
	}

	boolean characterCheck(KeyEvent e) {
		Pattern pattern = Pattern.compile("\\p{Punct}+");
		Matcher matcher = pattern.matcher(String.valueOf(e.character));
		return matcher.matches();
	}

	public static String addZeroForNum(String str, int strLength, boolean zero) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				if (zero) {
					sb.append("0").append(str);// 左补0
				} else {
					sb.append(str).append("0");//右补0
				}
				str = sb.toString();
				strLen = str.length();
			}
		}

		return str;
	}

	/**
	 * 验证特殊键
	 * 
	 * @param e
	 * @return
	 */
	protected boolean checkKey(KeyEvent e) {
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

	@Override
	protected void checkSubclass() {
	}

	public void setDigitalOnly(boolean digitalOnly) {
		if (digitalOnly) {
			this.expression = "\\d*";
		} else {
			this.expression = null;
		}
	}

	public void setDigitalOnly(boolean digitalOnly, int length) {
		if (digitalOnly) {
			this.expression = "\\d*{0," + length + "}";
		} else {
			this.expression = null;
		}
	}

	public Control getAccessControl() {
		return this.accessControl;
	}

	public void setAccessControl(Control accessControl) {
		this.accessControl = accessControl;
	}

	public void reset() {
		// 2012-8-14 重置text控件未清空数据问题
		this.objectValue = null;
		super.setText(defaultText);
	}

	public String getDefaultText() {
		return this.defaultText;
	}

	public void setDefaultText(String defaultText) {
		this.defaultText = defaultText;
	}

	public Object getObjectValue() {
		if (isDisposed()) {
			return null;
		}
		if (getText().trim().equals("")) {
			return null;
		}
		if (this.isFocusControl()) {
			return convert(getText());
		}
		return objectValue;
	}

	/**
	 * @param text
	 * @return
	 */
	private final Object convert(String text) {
		if (text == null || text.equals("")) {
			if (fieldType.isPrimitive()) {
				return 0;
			}
			return null;
		}
		return BeanUtil.convert(text, fieldType);
	}

	public void setObjectValue(Object objectValue) {
		this.objectValue = objectValue;
		if (objectValue != null) {
			if (textFormater != null) {
				setText(textFormater.format(objectValue));
			} else {
				setText(objectValue.toString());
			}
		} else {
			setText("");
		}
	}

	/**
	 * 当只允许输入数字时，得到编辑框对应的int数字
	 * 
	 * @return
	 * @return
	 */
	public int getNumber() {
		if (isEmpty()) {
			return 0;
		}
		return NumberUtil.parseNumber(getText(), Integer.class).intValue();
	}

	/**
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * @param expression
	 *            the expression to set
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}

	/**
	 * 判断text内容是否为空
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		String text = getText();
		if (text == null || text.trim().equals("")) {
			return true;
		} else
			return false;
	}

	public Class<?> getFieldType() {
		return fieldType;
	}

	public void setFieldType(Class<?> fieldType) {
		this.fieldType = fieldType;
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
		for (CasecadeListener casecadeListener : casecadeListeners) {
			casecadeListener.valueChanged(casecadeEvent);
		}
	}

	/**
	 * @param textFormater
	 *            the textFormater to set
	 */
	public void setTextFormater(ITextFormater textFormater) {
		this.textFormater = textFormater;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Boolean getMd5Format() {
		return md5Format;
	}

	public void setMd5Format(Boolean md5Format) {
		this.md5Format = md5Format;
	}

	public void setFormatLength(int formatLength) {
		this.formatLength = formatLength;
	}

	public void setZero(Boolean zero) {
		this.zero = zero;
	}

	/**
	 * @param radix the radix to set
	 */
	public void setRadix(int radix) {
		this.radix = radix;
	}

}
