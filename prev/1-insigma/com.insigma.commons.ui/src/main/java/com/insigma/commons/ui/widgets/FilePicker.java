/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;

import com.insigma.commons.ui.MessageForm;

public class FilePicker extends EnhanceComposite implements IInputControl {

	private Object objectValue;

	private org.eclipse.swt.widgets.Text filePathText;

	private String expression;

	private long fileSize = -1;

	public FilePicker(final Composite parent, int style) {

		super(parent, SWT.NONE);
		this.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		filePathText = new org.eclipse.swt.widgets.Text(this, style);
		filePathText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		filePathText.setEditable(false);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		this.setLayout(layout);
		final FilePicker filePicker = this;
		Button uploadButton = new Button(this, SWT.NONE);
		uploadButton.setText("...");
		uploadButton.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		uploadButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				com.insigma.commons.ui.dialog.FileDialog fileDialog = new com.insigma.commons.ui.dialog.FileDialog(
						parent.getShell(), SWT.NONE);
				fileDialog.setText("请选择文件：");
				String path = fileDialog.open();
				if (null != path) {
					if (null != expression && !"".equals(expression.trim())) {
						Pattern p = Pattern.compile(expression);
						Matcher m = p.matcher(path);
						if (!m.matches()) {
							MessageForm.Message("支持的文件类型正则为：" + expression);
							return;
						}
					}
					if (fileSize != -1) {
						File file = new File(path);
						if (file.length() > fileSize) {
							MessageForm.Message("导入文件不能大于" + fileSize / 1024 / 1024 + "MB。");
							return;
						}
					}
					int index = path.lastIndexOf(File.separatorChar);
					if (index < 0) {
						filePathText.setText(path);
					} else {
						String str = path.substring(index + 1);
						filePathText.setText(str);
					}
					objectValue = path;
					filePicker.setFocus();
				}
			}
		});

		Color bgcolor = getBackground();
		this.setBackground(bgcolor);
		filePathText.setBackground(bgcolor);
		uploadButton.setBackground(bgcolor);
	}

	public void addListener(int arg0, Listener arg1) {
		if (arg0 == SWT.FocusOut) {
			filePathText.addListener(arg0, arg1);
		} else {
			super.addListener(arg0, arg1);
		}
	}

	public void reset() {
		filePathText.setText("");
	}

	public Object getObjectValue() {
		return objectValue;
	}

	public void setObjectValue(Object objectValue) {
		this.objectValue = objectValue;

		int index = objectValue.toString().lastIndexOf(File.separatorChar);
		if (index < 0) {
		} else {
			objectValue = objectValue.toString().substring(index + 1);
		}
		filePathText.setText(objectValue.toString());

	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * @param expression the expression to set
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}

}
