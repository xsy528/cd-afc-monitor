/* 
 * 日期：2010-9-25
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.ui.widgets.provider;

import java.lang.reflect.Field;
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
import com.insigma.commons.ui.anotation.data.ViewData;
import com.insigma.commons.ui.casecade.CasecadeEvent;
import com.insigma.commons.ui.casecade.CasecadeListener;
import com.insigma.commons.ui.casecade.ICasecadeControl;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.form.widget.IWidgetProvider;
import com.insigma.commons.ui.form.widget.format.ITextFormater;
import com.insigma.commons.ui.widgets.IInputControl;
import com.insigma.commons.ui.widgets.Text;
import com.insigma.commons.util.lang.NumberUtil;

public class ByteArrayTextProvider implements IWidgetProvider {

	@Override
	public IInputControl create(Composite parent, BeanField beanField) {
		Field field = beanField.field;
		Object value = beanField.fieldValue;
		ViewData view = beanField.getAnnotationData(ViewData.class);

		ByteArrayText text = new ByteArrayText(parent, SWT.BORDER);

		if (null != value && field.getType().isArray()
				&& field.getType().getComponentType().isAssignableFrom(byte.class)) {
			text.setObjectValue(value);
		}

		if (view.isEditable() == false) {
			text.setEditable(false);
		}

		if (!view.modify()) {
			text.setEnabled(view.modify());
		}

		return text;
	}

	public String getName() {
		return "ByteArrayText";
	}

	public class ByteArrayText extends org.eclipse.swt.widgets.Text implements IInputControl, ICasecadeControl {

		private Control accessControl;

		private String defaultText = "";

		private String expression;

		private Class<?> fieldType = String.class;

		protected Object objectValue;

		private String fieldName;

		public ByteArrayText(Composite arg0, int arg1) {
			super(arg0, arg1);
			addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {

					// 回车事件绑定
					if (e.character == SWT.CR && accessControl != null) {
						accessControl.notifyListeners(SWT.Selection, new Event());
						return;
					}
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
						CasecadeEvent casecadeEvent = new CasecadeEvent(fieldName, ByteArrayText.this, control);
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

					setObjectValue(getObjectValue());
				}

				public void focusGained(FocusEvent e) {
					if (!isEnabled()) {
						return;
					}
					if (!getEditable()) {
						return;
					}

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

		@Override
		public Object getObjectValue() {
			setText(getText().trim());
			if (getText().trim().equals("")) {
				return null;
			}
			String[] objects = getText().trim().split(" ");
			byte[] result = new byte[objects.length];
			for (int i = 0; i < objects.length; i++) {
				result[i] = Integer.valueOf(objects[i], 16).byteValue();
			}
			return result;
		}

		@Override
		public void setObjectValue(Object objectValue) {
			if (null == objectValue) {
				setText("");
			} else {
				String textValue = "";
				for (byte object : (byte[]) objectValue) {
					textValue += String.format("%02x", object).toUpperCase() + " ";
				}
				setText(textValue);
				setSelection(textValue.toString().length());
			}
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

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}

	}
}
