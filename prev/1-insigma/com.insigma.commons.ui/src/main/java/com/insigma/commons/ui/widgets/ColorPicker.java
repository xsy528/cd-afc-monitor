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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.insigma.commons.ui.dialog.ColorDialog;

public class ColorPicker extends Composite implements IInputControl {

	private Color color;

	public ColorPicker(Composite parent, int style) {
		super(parent, style);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.numColumns = 2;
		setLayout(gridLayout);

		final Label label = new Label(this, SWT.BORDER);
		label.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Button button = new Button(this, SWT.ARROW | SWT.DOWN);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				ColorDialog dialog = new ColorDialog(getShell(), SWT.NORMAL);
				RGB rgb = dialog.open();
				if (rgb != null) {

					if (color != null) {
						color.dispose();
					}

					color = new Color(Display.getDefault(), rgb);
					label.setBackground(color);
				}
			}
		});
		button.setText("button");
		button.setLayoutData(new GridData(GridData.FILL_VERTICAL));
	}

	public void dispose() {
		color.dispose();
	}

	protected void checkSubclass() {
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Object getObjectValue() {
		return null;
	}

	public void setObjectValue(Object value) {
	}

	public void reset() {
	}
}
