/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.insigma.commons.lang.DateSpan;

public class DateSpanPicker extends EnhanceComposite implements IInputControl {

	private DateSpan dateSpan = new DateSpan();

	private DatePicker beginDate;

	private DatePicker endDate;

	public DateSpanPicker(Composite parent, int style) {
		super(parent, style);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.numColumns = 3;
		setLayout(gridLayout);

		beginDate = new DatePicker(this, SWT.NONE);
		beginDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label label = new Label(this, SWT.NONE);
		label.setText("  -  ");

		endDate = new DatePicker(this, SWT.NONE);
		endDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
	}

	public Object getObjectValue() {
		dateSpan.setStartDate((Date) beginDate.getObjectValue());
		dateSpan.setEndDate((Date) endDate.getObjectValue());
		return dateSpan;
	}

	public void setObjectValue(Object value) {
		if (value != null && value instanceof DateSpan) {
			dateSpan = (DateSpan) value;
			beginDate.setDefaultDate(dateSpan.getStartDate());
			endDate.setDefaultDate(dateSpan.getEndDate());
		}
	}

	public void reset() {
		beginDate.setDefaultDate(new Date());
		endDate.setDefaultDate(new Date());
	}
}
