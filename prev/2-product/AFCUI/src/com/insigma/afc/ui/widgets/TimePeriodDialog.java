/*
 * $Source: $
 * $Revision: $
 * $Date: $
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.ui.widgets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.widgets.TimePicker;
import com.insigma.commons.util.lang.DateTimeUtil;

/**
 * 选择时段
 *
 * @author 廖红自
 */
public class TimePeriodDialog extends Dialog {

	private Object result;

	/* 时间间隔(分) */
	private int timePeriod = 1;
	/* 时间间隔个数 */
	private int timePeriodCount = 1;
	/* 最大时间间隔个数 */
	private int maxTimePeriod = 0;

	private com.insigma.commons.ui.widgets.Shell shell;

	private Table table;

	private ArrayList<String> recordList = new ArrayList<String>();

	private String fileName = "/timePeriodRecord_";

	private String suffix = ".dat";

	private static final Log logger = LogFactory.getLog(TimePeriodDialog.class);

	/**
	 * Create the dialog
	 *
	 * @param parent
	 * @param style
	 */
	public TimePeriodDialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Create the dialog
	 *
	 * @param parent
	 */
	public TimePeriodDialog(Shell parent) {
		this(parent, SWT.NONE);
	}

	/**
	 * Open the dialog
	 *
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the window
	 */
	protected void createContents() {
		shell = new com.insigma.commons.ui.widgets.Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setLayout(new GridLayout());
		// shell.setImage(SWTResourceManager.getImage(StationSelectionDialog.class
		// ,
		// "/com/insigma/afc/monitor/images/red.png"));
		shell.setSize(480, 482);
		shell.setText("选择时段");
		shell.setImage(getParent().getImage());
		shell.posCenter();

		final Group grpNewPeriod = new Group(shell, SWT.NONE);
		grpNewPeriod.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		grpNewPeriod.setText("选择新时段");

		GridLayout layout = new GridLayout();
		layout.numColumns = 5;
		grpNewPeriod.setLayout(layout);

		final Label lblStartTime = new Label(grpNewPeriod, SWT.NONE);
		lblStartTime.setText("开始时间：");

		final TimePicker startTimePicker = new TimePicker(grpNewPeriod, SWT.NONE);
		GridData gridData = new GridData();
		gridData.widthHint = 115;
		startTimePicker.setLayoutData(gridData);
		startTimePicker.setTimePeriod(timePeriod);

		final Label lblEndTime = new Label(grpNewPeriod, SWT.NONE);
		lblEndTime.setText("结束时间：");

		final TimePicker endTimePicker = new TimePicker(grpNewPeriod, SWT.NONE);
		endTimePicker.setLayoutData(gridData);
		endTimePicker.setTimePeriod(timePeriod);

		final Button btnEnsure = new Button(grpNewPeriod, SWT.NONE);
		btnEnsure.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(final SelectionEvent arg0) {
				Date start = (Date) startTimePicker.getObjectValue();
				Date end = (Date) endTimePicker.getObjectValue();

				if (!start.before(end)) {
					MessageForm.Message("提示信息", "开始时间不能大于结束时间。", SWT.ICON_INFORMATION);
					return;
				}

				if (maxTimePeriod > 0) {
					int startIndex = DateTimeUtil.convertTimeToIndex(start, (short) timePeriod);
					int endIndex = DateTimeUtil.convertTimeToIndex(end, (short) timePeriod);
					if (end.getDate() - start.getDate() == 1) {
						endIndex = 289;
					}
					if (endIndex - startIndex > timePeriodCount) {
						MessageForm.Message("提示信息", "请选择时段小于" + maxTimePeriod + "分钟。", SWT.ICON_INFORMATION);
						return;
					} else if (start.getMinutes() % timePeriod > 0) {
						MessageForm.Message("提示信息", "请选择开始时间为" + timePeriod + "的倍数。", SWT.ICON_INFORMATION);
						return;
					} else if (end.getMinutes() % timePeriod > 0) {
						MessageForm.Message("提示信息", "请选择结束时间为" + timePeriod + "的倍数。", SWT.ICON_INFORMATION);
						return;
					}
				}

				String[] timePeriods = new String[2];

				timePeriods[0] = DateTimeUtil.formatDateToString(start, "HH:mm");
				timePeriods[1] = DateTimeUtil.formatDateToString(end, "HH:mm");
				if (end.getDate() - start.getDate() == 1) {
					timePeriods[1] = "24:00";
				}
				result = timePeriods;

				// 最后要把选择的时段写入文件
				writeTimeRecord(timePeriods[0] + "," + timePeriods[1]);
				shell.close();
			}
		});
		btnEnsure.setText("添 加(&O)");
		btnEnsure.setBounds(180, 90, 70, 30);

		final Group group = new Group(shell, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		group.setText("选择历史时段");
		group.setLayout(new FillLayout());
		//

		table = new Table(group, SWT.FULL_SELECTION | SWT.BORDER);

		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		final TableColumn tableColNo = new TableColumn(table, SWT.NONE);
		tableColNo.setWidth(82);
		tableColNo.setText("序号");

		final TableColumn tableColStartTime = new TableColumn(table, SWT.NONE);
		tableColStartTime.setAlignment(SWT.CENTER);
		tableColStartTime.setWidth(114);
		tableColStartTime.setText("开始时间");

		final TableColumn tableColEndTime = new TableColumn(table, SWT.NONE);
		tableColEndTime.setAlignment(SWT.CENTER);
		tableColEndTime.setWidth(104);
		tableColEndTime.setText("结束时间");

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		GridLayout compositeGridLayout = new GridLayout();
		compositeGridLayout.numColumns = 3;
		compositeGridLayout.marginRight = 30;
		composite.setLayout(compositeGridLayout);

		new Label(composite, SWT.NONE).setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Button btnSure = new Button(composite, SWT.NONE);

		btnSure.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				TableItem[] selectedItem = table.getSelection();
				if (selectedItem.length > 0) {
					String[] timePeriod = new String[2];
					timePeriod[0] = selectedItem[0].getText(1);
					timePeriod[1] = selectedItem[0].getText(2);
					result = timePeriod;
					shell.close();
				} else {
					MessageForm.Message("提示信息", "您没有选择任何一行记录。", SWT.ICON_INFORMATION);
				}
			}
		});
		btnSure.setText("确 定 (&O)");

		final Button btnCancel = new Button(composite, SWT.NONE);

		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				result = null;
				shell.close();
			}
		});
		btnCancel.setText("取 消 (&C)");

		readTimeRecord();
	}

	/**
	 * 将文件的记录读到table上
	 */
	private void readTimeRecord() {
		File timePeriodRecord = null;
		BufferedReader bufferedReader = null;

		try {
			timePeriodRecord = new File(System.getProperty("user.dir") + fileName + timePeriod + suffix);
			if (!timePeriodRecord.exists()) {
				logger.error("文件" + fileName + timePeriod + suffix + "不存在");
				return;
			}
			FileReader fileReader = new FileReader(timePeriodRecord);
			bufferedReader = new BufferedReader(fileReader);
			String timePeriod = "";

			int no = 1;
			recordList.clear();
			while ((timePeriod = bufferedReader.readLine()) != null) {
				if (timePeriod.equals("")) {
					continue;
				}
				recordList.add(timePeriod);
				TableItem item = new TableItem(table, SWT.NONE);

				item.setText(0, "" + no);
				item.setText(1, timePeriod.substring(0, 5));
				item.setText(2, timePeriod.substring(6));
				no++;
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			logger.warn("找不到文件" + fileName + timePeriod + suffix + "" + e, e);
		} catch (IOException e) {
			logger.warn("读取文件" + fileName + timePeriod + suffix + "失败" + e, e);
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e) {
				logger.warn("关闭bufferedReader异常" + e, e);
			}
		}
	}

	/**
	 * 将table的记录写到文件
	 */
	private void writeTimeRecord(String timePeriods) {
		File timePeriodRecord = null;
		BufferedWriter bufferedWriter = null;
		try {
			timePeriodRecord = new File(System.getProperty("user.dir") + fileName + timePeriod + suffix);

			if (!timePeriodRecord.exists()) {
				logger.error("文件" + fileName + timePeriod + suffix + "不存在");
				if (!timePeriodRecord.createNewFile()) {
					logger.error("创建文件" + fileName + timePeriod + suffix + "失败");
					return;
				}
			}

			FileWriter fileWriter = new FileWriter(timePeriodRecord);
			bufferedWriter = new BufferedWriter(fileWriter);

			if ((timePeriods != null) && !timePeriods.equals("")) {
				logger.info("写入时段：" + timePeriods);
				//                bufferedWriter.write(timePeriods);
				Date dateStart = DateTimeUtil.parseStringToDate(timePeriods.split(",")[0], "HH:mm");
				Date dateEnd = DateTimeUtil.parseStringToDate(timePeriods.split(",")[1], "HH:mm");
				boolean isWrited = false;
				int i = 1;
				for (String period : recordList) {
					if (i > 100) {
						break;
					}
					Date date = DateTimeUtil.parseStringToDate(period.split(",")[0], "HH:mm");
					if (!isWrited) {
						if (timePeriods.equals(period)) {
							continue;
						}
						if (dateStart.before(date)) {
							bufferedWriter.newLine();
							bufferedWriter.write(timePeriods);
							isWrited = true;
						} else if ((dateStart.compareTo(date) == 0)) {
							date = DateTimeUtil.parseStringToDate(period.split(",")[1], "HH:mm");
							if (dateEnd.before(date)) {
								bufferedWriter.newLine();
								bufferedWriter.write(timePeriods);
								isWrited = true;
							}
						}
					}
					bufferedWriter.newLine();
					bufferedWriter.append(period);
					i++;
				}
				if (!isWrited) {
					bufferedWriter.newLine();
					bufferedWriter.write(timePeriods);
				}
			}

			bufferedWriter.close();
		} catch (FileNotFoundException e) {
			logger.warn("找不到文件" + fileName + timePeriod + suffix + "" + e, e);
		} catch (IOException e) {
			logger.warn("写入文件" + fileName + timePeriod + suffix + "失败" + e, e);
		} finally {
			try {
				if (bufferedWriter != null) {
					bufferedWriter.close();
				}
			} catch (IOException e) {
				logger.warn("关闭bufferedReader异常" + e, e);
			}
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
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

	public int getMaxTimePeriod() {
		return maxTimePeriod;
	}

	public void setMaxTimePeriod(int maxTimePeriod) {
		this.maxTimePeriod = maxTimePeriod;
	}

}