/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.insigma.commons.ui.dialog.FontDialog;

public class FontPicker extends EnhanceComposite implements IInputControl {

	private Font font;

	private Label label;

	public FontPicker(Composite parent, int style) {
		super(parent, style);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.numColumns = 2;
		setLayout(gridLayout);

		label = new Label(this, SWT.HORIZONTAL | SWT.CENTER | SWT.BORDER);
		label.setAlignment(SWT.CENTER);
		label.setLayoutData(new GridData(GridData.FILL_BOTH));
		label.setText("字体");

		final Button button = new Button(this, SWT.ARROW | SWT.DOWN);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				FontDialog dialog = new FontDialog(getShell(), SWT.NORMAL);
				FontData fontdata = dialog.open();
				if (fontdata != null) {
					if (font != null) {
						font.dispose();
					}
					font = new Font(Display.getDefault(), fontdata);
					label.setFont(font);
				}
			}
		});
		button.setText("...");
		button.setLayoutData(new GridData(GridData.FILL_VERTICAL));
	}

	public void dispose() {
		font.dispose();
		super.dispose();
	}

	public Font getFont() {
		return this.font;
	}

	public void setFont(Font font) {
		this.font = font;
		label.setFont(font);
	}

	public void reset() {
	}

	public Object getObjectValue() {
		return getFont();
	}

	public void setObjectValue(Object value) {
		setFont((Font) value);
	}
}
