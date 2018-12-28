/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.form.widget;

import java.lang.reflect.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.io.FileUtil;
import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.anotation.data.ViewData;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.widgets.FilePicker;
import com.insigma.commons.ui.widgets.IInputControl;
import com.insigma.commons.util.lang.StringUtil;

public class FileProvider implements IWidgetProvider {

	@Override
	public IInputControl create(Composite parent, final BeanField beanField) {
		Object value = beanField.fieldValue;

		ViewData view = beanField.getAnnotationData(ViewData.class);

		final FilePicker control = new FilePicker(parent, SWT.BORDER);

		if (view.fileSize() != -1) {
			control.setFileSize(view.fileSize());
		}
		if (value != null) {
			control.setObjectValue(value);
		}
		if (view.regexp() != null && !view.regexp().equals("")) {
			control.setExpression(view.regexp());
		}
		// 设置MD5信息
		if (null != view.description() && !"".equals(view.description().trim())) {
			final String[] field = view.description().trim().split("\\.");
			control.addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent arg0) {
				}

				@Override
				public void focusLost(FocusEvent arg0) {
					Object filePath = control.getObjectValue();
					if (null != filePath && !"".equals(filePath.toString().trim())
							&& !filePath.equals(beanField.fieldValue)) {
						if (null != beanField.parent) {
							setMd5(beanField.parent.fieldValue, filePath.toString(), field, 0);
						}
					}

				}

			});
		}

		return control;

	}

	public String getName() {
		return "File";
	}

	private void setMd5(Object data, String filePath, String[] md5Path, int depth) {
		if (null != data && md5Path.length > depth) {
			Field[] fields = data.getClass().getDeclaredFields();
			if (null != fields && fields.length > 0) {
				for (Field field : fields) {
					if (field.getName().equals(md5Path[depth])) {
						if (md5Path.length == (depth + 1)) {
							// 计算MD5
							byte[] md5 = FileUtil.calculateMd5(filePath);
							if (null != md5) {
								String strMd5 = StringUtil.formateByteArrayToString(md5);
								BeanUtil.setValue(data, field, strMd5);
							}
						} else {
							Object fieldValue = BeanUtil.getValue(data, field);
							if (null == fieldValue) {
								try {
									fieldValue = field.getType().newInstance();
									BeanUtil.setValue(data, field, fieldValue);
								} catch (Exception e) {
								}
							}
							setMd5(fieldValue, filePath, md5Path, ++depth);
						}
					}
				}
			}
		}
	}
}
