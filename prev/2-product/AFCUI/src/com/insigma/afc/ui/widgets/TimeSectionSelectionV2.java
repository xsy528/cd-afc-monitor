/*
 * 日期：2007-12-4
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.ui.widgets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

import com.insigma.commons.framework.view.form.FormEditor;
import com.insigma.commons.ui.widgets.Combo;
import com.insigma.commons.ui.widgets.IInputControl;

/**
 * Ticket: 选择时间段控件V2
 * 
 * @author Zhou-Xiaowei
 */
public class TimeSectionSelectionV2 extends Composite implements IInputControl {

	private final Log logger = LogFactory.getLog(TimeSectionSelectionV2.class);

	private Object result;

	/* 时间间隔(分) */
	private int timePeriod = 1;

	/* 时间间隔个数 */
	private int timePeriodCount = 1;

	/* 最大时间间隔个数 */
	private int maxTimePeriod = 0;

	private Combo combo;

	private ArrayList<String> recordList = new ArrayList<String>();

	private String parentName = "";

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public TimeSectionSelectionV2(Composite parent, int style) {
		super(parent, style);

		String obj = null;
		if (parent instanceof FormEditor) {
			obj = ((FormEditor) parent).getForm().getClass().getName();
		}
		if (obj == null) {
			obj = "";
		}
		parentName = obj;

		GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.marginLeft = 0;
		gridLayout.marginTop = 0;
		gridLayout.horizontalSpacing = 5;
		gridLayout.verticalSpacing = 0;
		gridLayout.numColumns = 2;
		super.setLayout(gridLayout);

		GridData griddata = new GridData(SWT.FILL, SWT.CENTER, true, true);
		combo = new Combo(this, SWT.READ_ONLY);
		combo.setLayoutData(griddata);

		readTimeRecord();
		if (!recordList.isEmpty()) {
			combo.setItems((String[]) convertListToArray(recordList));
			combo.select(recordList.size() - 1);
		} else {
			combo.setItems(new String[] { "00:00-24:00" });
			combo.select(0);
		}

		final Button button = new Button(this, SWT.NONE);
		button.setText("时段配置");

		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true));
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				TimePeriodDialogV2 dialog = new TimePeriodDialogV2(getShell());
				dialog.setTimePeriod(timePeriod);
				dialog.setTimePeriodCount(timePeriodCount);
				dialog.setMaxTimePeriod(maxTimePeriod);
				dialog.setParentName(parentName);
				dialog.setbDispose(false);
				result = dialog.open();
				if (result != null) {
					if (result instanceof String[]) {
						String[] period = (String[]) result;
						setComboTimePeriod(period[0] + "-" + period[1]);
					} else if (result instanceof String) {
						setComboTimePeriod((String) result);
					}
				} else {
					setComboTimePeriod(null);
				}
			}
		});

	}

	private Object convertListToArray(List<?> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}

		if (list.get(0) instanceof Short) {
			Short[] rst = new Short[list.size()];
			for (int i = 0; i < list.size(); i++) {
				rst[i] = (Short) list.get(i);
			}

			return rst;
		} else if (list.get(0) instanceof Integer) {
			Integer[] rst = new Integer[list.size()];
			for (int i = 0; i < list.size(); i++) {
				rst[i] = (Integer) list.get(i);
			}

			return rst;
		} else if (list.get(0) instanceof Long) {
			Long[] rst = new Long[list.size()];
			for (int i = 0; i < list.size(); i++) {
				rst[i] = (Long) list.get(i);
			}

			return rst;
		} else if (list.get(0) instanceof String) {
			String[] rst = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				rst[i] = (String) list.get(i);
			}

			return rst;
		}

		return null;
	}

	private void readTimeRecord() {
		File timePeriodRecord = null;
		BufferedReader bufferedReader = null;

		String fileName = new TimePeriodDialogV2(getShell()).getFileName();
		String suffix = new TimePeriodDialogV2(getShell()).getSuffix();

		try {
			timePeriodRecord = new File(System.getProperty("user.dir") + fileName + parentName + timePeriod + suffix);
			if (!timePeriodRecord.exists()) {
				logger.error("文件" + fileName + parentName + timePeriod + suffix + "不存在");
				return;
			}

			FileReader fileReader = new FileReader(timePeriodRecord);
			bufferedReader = new BufferedReader(fileReader);
			String timePeriod = "";

			recordList.clear();
			while ((timePeriod = bufferedReader.readLine()) != null) {
				if (timePeriod.equals("")) {
					continue;
				}
				recordList.add(timePeriod.replace(',', '-'));
			}

			bufferedReader.close();
		} catch (FileNotFoundException e) {
			logger.warn("找不到文件" + fileName + parentName + timePeriod + suffix + "。" + e, e);
		} catch (IOException e) {
			logger.warn("读取文件" + fileName + parentName + timePeriod + suffix + "失败。" + e, e);
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e) {
				logger.warn("关闭bufferedReader异常。" + e, e);
			}
		}
	}

	public void setLayout(Layout arg0) {
	}

	/**
	 * @return 时间段
	 */
	public String getComboTimePeriod() {
		return combo.getText();
	}

	/**
	 * @param comboTimePeriod
	 *            时间段
	 */
	public void setComboTimePeriod(String comboTimePeriod) {
		readTimeRecord();

		if (!recordList.isEmpty()) {
			combo.setItems((String[]) convertListToArray(recordList));

			int i;
			for (i = 0; i < recordList.size(); i++) {
				if (recordList.get(i).equals(comboTimePeriod)) {
					combo.select(i);
					break;
				}
			}

			if (i == recordList.size()) {
				combo.select(recordList.size() - 1);
			}
		} else {
			comboTimePeriod = (comboTimePeriod == null ? getCurrentTimePeriod(timePeriod, timePeriodCount)
					: comboTimePeriod);
			combo.setItems(new String[] { comboTimePeriod });
			combo.select(0);
		}
		// else if (comboTimePeriod != null) {
		// combo.setItems(new String[] {comboTimePeriod});
		// combo.select(0);
		// } else {
		// String txt = getCurrentTimePeriod(timePeriod, timePeriodCount);
		// if (txt != null) {
		// combo.setItems(new String[] {txt});
		// combo.select(0);
		// }
		// }

		// this.combo.setText(comboTimePeriod);
	}

	/**
	 * 重新设置时间段
	 */
	public void resetTimeCombo() {
		if (timePeriod != 1 && maxTimePeriod != 0) {
			this.combo.setText(getCurrentTimePeriod(timePeriod, timePeriodCount));
		} else {
			this.combo.setText("00:00-24:00");
		}
	}

	public Object getObjectValue() {
		return getComboTimePeriod();
	}

	public void setObjectValue(Object value) {
		setComboTimePeriod(value.toString());
	}

	public void reset() {
		resetTimeCombo();
	}

	/**
	 * 取得当前时间段
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
