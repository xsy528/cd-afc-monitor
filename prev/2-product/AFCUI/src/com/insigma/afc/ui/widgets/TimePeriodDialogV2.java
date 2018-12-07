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
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.widgets.EnhanceComposite;
import com.insigma.commons.ui.widgets.TimePicker;
import com.insigma.commons.util.lang.DateTimeUtil;

/**
 * Ticket: 时段设置
 * 
 * @author Zhou-Xiaowei
 */
public class TimePeriodDialogV2 extends Dialog {

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

	private String parentName = "";

	private String suffix = ".dat";

	private static final Log logger = LogFactory.getLog(TimePeriodDialogV2.class);

	private boolean bDispose = true;

	private boolean bTableSelection = false;

	// private Button btnSure;

	private Button btnDelete;

	private Button btnCancel;

	private Button btnSure;

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 * @param style
	 */
	public TimePeriodDialogV2(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 */
	public TimePeriodDialogV2(Shell parent) {
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

		if (recordList != null && !recordList.isEmpty()) {
			result = recordList.get(recordList.size() - 1);
		} else {
			result = null;
		}

		return result;
	}

	/**
	 * Create contents of the window
	 */
	protected void createContents() {
		shell = new com.insigma.commons.ui.widgets.Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setLayout(new GridLayout());
		shell.setSize(480, 482);
		shell.setText("时段设置");
		shell.setImage(getParent().getImage());
		shell.posCenter();

		final Group grpNewPeriod = new Group(shell, SWT.NONE);
		grpNewPeriod.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		grpNewPeriod.setText("添加新时段");

		GridLayout layout = new GridLayout();
		layout.horizontalSpacing = 5;
		layout.numColumns = 5;
		grpNewPeriod.setLayout(layout);

		final Label lblStartTime = new Label(grpNewPeriod, SWT.NONE);
		{
			GridData gridData1 = new GridData(SWT.FILL, SWT.CENTER, true, false);
			lblStartTime.setLayoutData(gridData1);
		}
		lblStartTime.setText("开始时间：");

		final TimePicker startTimePicker = new TimePicker(grpNewPeriod, SWT.NONE);
		{
			GridData gridData1 = new GridData(SWT.FILL, SWT.CENTER, true, false);
			gridData1.widthHint = 115;
			startTimePicker.setLayoutData(gridData1);
		}
		startTimePicker.setTimePeriod(timePeriod);

		final Label lblEndTime = new Label(grpNewPeriod, SWT.NONE);
		{
			GridData gridData1 = new GridData(SWT.FILL, SWT.CENTER, true, false);
			lblEndTime.setLayoutData(gridData1);
		}
		lblEndTime.setText("结束时间：");

		final TimePicker endTimePicker = new TimePicker(grpNewPeriod, SWT.NONE);
		{
			GridData gridData1 = new GridData(SWT.FILL, SWT.CENTER, true, false);
			gridData1.widthHint = 115;
			endTimePicker.setLayoutData(gridData1);
		}
		endTimePicker.setTimePeriod(timePeriod);

		final Button btnEnsure = new Button(grpNewPeriod, SWT.NONE);
		{
			GridData gridData1 = new GridData(SWT.FILL, SWT.CENTER, true, false);
			btnEnsure.setLayoutData(gridData1);
		}
		btnEnsure.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(final SelectionEvent arg0) {
				Date start = (Date) startTimePicker.getObjectValue();
				start.setSeconds(0);
				Date end = (Date) endTimePicker.getObjectValue();
				end.setSeconds(0);

				if (!start.before(end)) {
					MessageForm.Message("提示信息", "开始时间不能大于或者等于结束时间。", SWT.ICON_INFORMATION);
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

				if (bDispose) {
					shell.close();
				} else {
					readTimeRecord();
				}
			}
		});
		btnEnsure.setText("添 加(&A)");
		btnEnsure.setBounds(180, 90, 70, 30);

		final Group group = new Group(shell, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		group.setText("历史时段");
		group.setLayout(new FillLayout());

		table = new Table(group, SWT.FULL_SELECTION | SWT.BORDER);

		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				TableItem[] selectedItem = table.getSelection();
				if (selectedItem.length > 0) {
					bTableSelection = true;
				} else {
					bTableSelection = false;
				}

				// btnSure.setEnabled(bTableSelection);
				btnDelete.setEnabled(bTableSelection);
			}
		});

		final TableColumn firstCol = new TableColumn(table, SWT.NONE);
		firstCol.setWidth(0);
		firstCol.setText("");

		final TableColumn tableColNo = new TableColumn(table, SWT.LEFT);
		// tableColNo.setAlignment(SWT.CENTER);
		tableColNo.setWidth(80);
		tableColNo.setText("序号");

		final TableColumn tableColStartTime = new TableColumn(table, SWT.LEFT);
		// tableColStartTime.setAlignment(SWT.CENTER);
		tableColStartTime.setWidth(100);
		tableColStartTime.setText("开始时间");

		final TableColumn tableColEndTime = new TableColumn(table, SWT.LEFT);
		// tableColEndTime.setAlignment(SWT.CENTER);
		tableColEndTime.setWidth(100);
		tableColEndTime.setText("结束时间");

		EnhanceComposite composite = new EnhanceComposite(shell, SWT.BORDER);
		{
			composite.setBackground(composite.getParent().getBackground());
			//			composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

			GridData gridData1 = new GridData(SWT.FILL, SWT.FILL, true, false);
			composite.setLayoutData(gridData1);

			GridLayout layout1 = new GridLayout(1, false);
			layout1.horizontalSpacing = 20;
			layout1.verticalSpacing = 0;
			layout1.marginWidth = 10;
			layout1.marginHeight = 10;
			layout1.numColumns = 3;// columns
			composite.setLayout(layout1);
		}

		//		btnSure = new Button(composite, SWT.NONE);
		//		btnSure.setEnabled(true);
		//		btnSure.setText("即时应用(&O)");
		//		{
		//			GridData gridData1 = new GridData(SWT.RIGHT, SWT.FILL, true, false);
		//			gridData1.widthHint = 80;
		//			gridData1.heightHint = 30;
		//			btnSure.setLayoutData(gridData1);
		//		}
		//		btnSure.addSelectionListener(new SelectionAdapter() {
		//			@Override
		//			public void widgetSelected(SelectionEvent arg0) {
		//				TableItem[] selectedItem = table.getSelection();
		//				if (selectedItem.length > 0) {
		//					String[] timePeriod = new String[2];
		//					timePeriod[0] = selectedItem[0].getText(2);
		//					timePeriod[1] = selectedItem[0].getText(3);
		//					result = timePeriod;
		//					shell.close();
		//				} else if (!recordList.isEmpty()) {
		//					result = recordList.get(recordList.size() - 1);
		//					shell.close();
		//					// MessageForm.Message("提示信息", "请选择一条记录作为开始时间段。");
		//				} else {
		//					result = null;
		//					shell.close();
		//				}
		//			}
		//		});

		btnDelete = new Button(composite, SWT.NONE);
		btnDelete.setEnabled(bTableSelection);
		btnDelete.setText("删 除 (&R)");
		{
			GridData gridData1 = new GridData(SWT.RIGHT, SWT.FILL, false, false);
			gridData1.widthHint = 70;
			gridData1.heightHint = 30;
			btnDelete.setLayoutData(gridData1);
		}
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				TableItem[] selectedItem = table.getSelection();

				if (selectedItem != null && selectedItem.length > 0) {
					for (TableItem item : selectedItem) {
						String section = item.getText(2) + ',' + item.getText(3);
						recordList.remove(section);
					}

					writeTimeRecord();
					readTimeRecord();
					MessageForm.Message("提示信息", "删除成功。", SWT.ICON_INFORMATION);
				} else {
					MessageForm.Message("提示信息", "您没有选择任何记录。");
				}

			}
		});

		btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setText("关闭 (&C)");
		{
			GridData gridData1 = new GridData(SWT.RIGHT, SWT.FILL, false, false);
			gridData1.widthHint = 70;
			gridData1.heightHint = 30;
			btnCancel.setLayoutData(gridData1);
		}
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				result = -1;
				shell.close();
			}
		});

		readTimeRecord();
	}

	/**
	 * 将文件的记录读到table上
	 */
	private void readTimeRecord() {
		File timePeriodRecord = null;
		BufferedReader bufferedReader = null;

		try {
			timePeriodRecord = new File(System.getProperty("user.dir") + fileName + parentName + timePeriod + suffix);
			if (!timePeriodRecord.exists()) {
				logger.error("文件" + fileName + parentName + timePeriod + suffix + "不存在");
				return;
			}
			FileReader fileReader = new FileReader(timePeriodRecord);
			bufferedReader = new BufferedReader(fileReader);
			String timePeriod = "";

			int no = 1;
			recordList.clear();
			table.removeAll();
			while ((timePeriod = bufferedReader.readLine()) != null) {
				if (timePeriod.equals("")) {
					continue;
				}
				recordList.add(timePeriod);
				TableItem item = new TableItem(table, SWT.NONE);

				item.setText(0, "");
				item.setText(1, "" + no);
				item.setText(2, timePeriod.substring(0, 5));
				item.setText(3, timePeriod.substring(6));
				no++;
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

				Event event = new Event();
				event.type = SWT.Selection;
				event.widget = table;
				table.notifyListeners(SWT.Selection, event);
			} catch (IOException e) {
				logger.warn("关闭bufferedReader异常。" + e, e);
			}
		}
	}

	private void writeTimeRecord() {
		File timePeriodRecord = null;
		BufferedWriter bufferedWriter = null;
		BufferedReader bufferedReader = null;
		try {
			timePeriodRecord = new File(System.getProperty("user.dir") + fileName + parentName + timePeriod + suffix);

			if (!timePeriodRecord.exists()) {
				logger.error("文件" + fileName + parentName + timePeriod + suffix + "不存在");
				if (!timePeriodRecord.createNewFile()) {
					logger.error("创建文件" + fileName + parentName + timePeriod + suffix + "失败");
					return;
				}
			}

			FileReader fileReader = new FileReader(timePeriodRecord);
			bufferedReader = new BufferedReader(fileReader);
			while ((bufferedReader.readLine()) != null) {
			}
			bufferedReader.close();

			FileWriter fileWriter = new FileWriter(timePeriodRecord);
			bufferedWriter = new BufferedWriter(fileWriter);

			for (String period : recordList) {
				bufferedWriter.newLine();
				bufferedWriter.write(period);
			}

			bufferedWriter.close();
		} catch (FileNotFoundException e) {
			logger.warn("找不到文件" + fileName + parentName + timePeriod + suffix + "。" + e, e);
		} catch (IOException e) {
			logger.warn("写入文件" + fileName + parentName + timePeriod + suffix + "失败。" + e, e);
		} finally {
			try {
				if (bufferedWriter != null) {
					bufferedWriter.close();
				}
			} catch (IOException e) {
				logger.warn("关闭bufferedReader异常。" + e, e);
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
			timePeriodRecord = new File(System.getProperty("user.dir") + fileName + parentName + timePeriod + suffix);

			if (!timePeriodRecord.exists()) {
				logger.error("文件" + fileName + parentName + timePeriod + suffix + "不存在");
				if (!timePeriodRecord.createNewFile()) {
					logger.error("创建文件" + fileName + parentName + timePeriod + suffix + "失败");
					return;
				}
			}

			FileWriter fileWriter = new FileWriter(timePeriodRecord);
			bufferedWriter = new BufferedWriter(fileWriter);

			if ((timePeriods != null) && !timePeriods.equals("")) {
				logger.info("写入时段：" + timePeriods);
				// bufferedWriter.write(timePeriods);
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
			logger.warn("找不到文件" + fileName + parentName + timePeriod + suffix + "。" + e, e);
		} catch (IOException e) {
			logger.warn("写入文件" + fileName + parentName + timePeriod + suffix + "失败。" + e, e);
		} finally {
			try {
				if (bufferedWriter != null) {
					bufferedWriter.close();
				}
			} catch (IOException e) {
				logger.warn("关闭bufferedReader异常。" + e, e);
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

	public String getFileName() {
		return fileName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setbDispose(boolean bDispose) {
		this.bDispose = bDispose;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}