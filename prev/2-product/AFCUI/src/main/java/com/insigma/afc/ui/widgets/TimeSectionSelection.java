/*
 * 日期：2007-12-4
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.ui.widgets;

import java.util.Calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Text;

import com.insigma.commons.ui.widgets.IInputControl;

/**
 * 选择时间段控件
 *
 * @author 廖红自
 */
public class TimeSectionSelection extends Composite implements IInputControl {

	private Object result;

	private Text txtTimePeriod;
	/* 时间间隔(分) */
	private int timePeriod = 1;
	/* 时间间隔个数 */
	private int timePeriodCount = 1;
	/* 最大时间间隔个数 */
	private int maxTimePeriod = 0;

	/**
	 * Create the composite
	 *
	 * @param parent
	 * @param style
	 */
	public TimeSectionSelection(Composite parent, int style) {
		super(parent, style);

		GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.marginLeft = 0;
		gridLayout.marginTop = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.numColumns = 2;
		super.setLayout(gridLayout);

		GridData griddata = new GridData(SWT.FILL, SWT.FILL, true, true);

		txtTimePeriod = new Text(this, SWT.BORDER);
		txtTimePeriod.setEditable(false);
		// txtTimePeriod.setBounds(0, 0, 90, 22);
		txtTimePeriod.setText("00:00-24:00");
		txtTimePeriod.setLayoutData(griddata);

		final Button button = new Button(this, SWT.ARROW | SWT.DOWN);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				TimePeriodDialog dialog = new TimePeriodDialog(getShell());
				dialog.setTimePeriod(timePeriod);
				dialog.setTimePeriodCount(timePeriodCount);
				dialog.setMaxTimePeriod(maxTimePeriod);
				result = dialog.open();
				if (result != null) {
					String[] period = (String[]) result;
					setTxtTimePeriod(period[0] + "-" + period[1]);
				}
			}
		});
		button.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		// button.setBounds(90, 0, 17, 22);
	}

	/**
	 * @param arg0
	 *            Layout
	 */
	public void setLayout(Layout arg0) {
	}

	/**
	 * @return 时间段
	 */
	public String getTxtTimePeriod() {
		return txtTimePeriod.getText();
	}

	/**
	 * @param txtTimePeriod
	 *            时间段
	 */
	public void setTxtTimePeriod(String txtTimePeriod) {
		this.txtTimePeriod.setText(txtTimePeriod);
	}

	/**
	 * 重新设置时间段
	 */
	public void resetTimeText() {
		if (timePeriod != 1 && maxTimePeriod != 0) {
			this.txtTimePeriod.setText(getCurrentTimePeriod(timePeriod, timePeriodCount));
		} else {
			this.txtTimePeriod.setText("00:00-24:00");
		}
	}

	public Object getObjectValue() {

		return getTxtTimePeriod();
	}

	public void setObjectValue(Object value) {
		setTxtTimePeriod(value.toString());
	}

	public void reset() {
		resetTimeText();
	}

	/**
	 *取得当前时间段
	 *
	 * @param timePeriod
	 * @param timePeriodCount
	 * @return
	 */
	public String getCurrentTimePeriod(int timePeriod, int timePeriodCount) {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minite = calendar.get(Calendar.MINUTE);
		minite = minite - minite % timePeriod;
		int period = timePeriod * timePeriodCount;
		if (minite >= period) {
			return String.format("%02d:%02d-%02d:%02d", hour, minite - period, hour, minite);
		} else {
			return String.format("%02d:%02d-%02d:%02d", hour == 0 ? 23 : (hour - 1), 60 - (period - minite), hour,
					minite);
		}

	}

	public int getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(int timePeriod) {
		this.timePeriod = timePeriod;
	}

	public int getTimePeriodCount() {
		return timePeriodCount;
	}

	public void setTimePeriodCount(int timePeriodCount) {
		this.timePeriodCount = timePeriodCount;
	}

	public void setMaxTimePeriod(int maxTimePeriod) {
		this.maxTimePeriod = maxTimePeriod;
	}

}
