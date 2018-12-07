/* 
 * 日期：2011-6-8
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class BiTextBox extends EnhanceComposite implements IInputControl {

	private Text text1;

	private Text text2;

	public Text getText1() {
		return text1;
	}

	public void setText1(Text text1) {
		this.text1 = text1;
	}

	public Text getText2() {
		return text2;
	}

	public void setText2(Text text2) {
		this.text2 = text2;
	}

	public BiTextBox(Composite arg0, int arg1) {
		super(arg0, arg1);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = layout.marginWidth = layout.marginLeft = layout.marginRight = layout.marginTop = layout.marginBottom = 0;
		this.setLayout(layout);
		text1 = new Text(this, SWT.BORDER);
		text1.setLayoutData(new GridData(GridData.FILL_BOTH));
		// text1.setEchoChar('*');
		text2 = new Text(this, SWT.BORDER);
		text2.setLayoutData(new GridData(GridData.FILL_BOTH));
		// text2.setEchoChar('*');
	}

	public Object getObjectValue() {
		if (text1.getText() == null || text2.getText() == null || !text1.getText().equals(text2.getText())) {
			return null;
		}
		return text1.getText().trim();
	}

	public void setObjectValue(Object value) {

		if (value == null) {
			text1.setText("");
			text2.setText("");
		} else {
			text1.setText(value.toString());
			text2.setText(value.toString());
		}
	}

	public void reset() {
		text1.setText("");
		text2.setText("");
	}

}
