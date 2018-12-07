/* 
 * 日期：2010-12-22
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.monitor.handler;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.insigma.commons.ui.FormUtil;
import com.insigma.commons.ui.control.DialogHeaderComposite;
import com.swtdesigner.SWTResourceManager;

/**
 * Ticket: 模式广播信息确认对话框
 * 
 * @author Zhou-Xiaowei
 */
public class ModeBroadcastCheckDialog extends Dialog {

	private static final Log logger = LogFactory.getLog(ModeBroadcastCheckDialog.class);

	private Button btnOk;

	private Button btnCancel;

	private Object result = -1;

	private Shell shell;

	private Font font;

	private Display display;

	// private Composite composite;

	private List<String[]> validationList;

	protected SelectionAdapter validateAction = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent arg0) {
			result = 1;
			shell.close();

		}
	};

	protected SelectionAdapter exitAction = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent arg0) {
			result = 0;
			shell.close();
		}
	};

	public ModeBroadcastCheckDialog(Shell parent, int style) {
		super(parent, style);
	}

	public Object open() {
		result = -1;

		if (this.validationList == null || this.validationList.equals("")) {
			return null;
		}

		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}

	/**
	 * Create contents of the dialog
	 */
	protected void createContents() {
		shell = new Shell(display, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		int width = 550;
		int height = 400;
		shell.setSize(width, height);
		shell.setLocation(FormUtil.getCenterLocation(width, height));
		final GridLayout gridL = new GridLayout();
		gridL.verticalSpacing = 0;
		gridL.horizontalSpacing = 0;
		gridL.marginWidth = 0;
		gridL.marginHeight = 0;
		shell.setLayout(gridL);
		shell.setImage(SWTResourceManager.getImage(ModeBroadcastCheckDialog.class, "/com/insigma/afc/images/logo.png"));
		shell.setText("待发送模式广播列表");

		final DialogHeaderComposite insigmaLogoComposite = new DialogHeaderComposite(shell, SWT.NONE);
		final GridData gd_insigmaLogoComposite = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd_insigmaLogoComposite.heightHint = 60;
		insigmaLogoComposite.setLayoutData(gd_insigmaLogoComposite);
		insigmaLogoComposite.setTitle("待发送模式广播列表");
		insigmaLogoComposite.setDescription("需要确认的待发送模式广播列表");

		// font = SWTResourceManager.getFont("宋体", 9, SWT.SIMPLE);

		Composite composite = new Composite(shell, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 0;
		gridLayout.marginWidth = 5;
		gridLayout.marginHeight = 10;
		gridLayout.horizontalSpacing = 5;
		gridLayout.verticalSpacing = 15;
		gridLayout.numColumns = 1;
		composite.setLayout(gridLayout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		ScrolledComposite sc = new ScrolledComposite(composite, SWT.H_SCROLL | SWT.BORDER);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setLayout(new GridLayout());
		sc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite cp = new Composite(sc, SWT.NONE);
		cp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		final GridLayout gridLayoutX = new GridLayout();
		gridLayoutX.verticalSpacing = 0;
		gridLayoutX.marginWidth = 0;
		gridLayoutX.marginHeight = 0;
		gridLayoutX.horizontalSpacing = 0;
		gridLayoutX.numColumns = 1;
		composite.setLayout(gridLayoutX);
		cp.setLayout(gridLayoutX);

		Table table = new Table(cp, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		// table.setFont(font);

		table.addListener(SWT.MeasureItem, new Listener() {
			public void handleEvent(Event arg0) {
				arg0.height = 20;
			}
		});

		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		TableColumn sc1 = new TableColumn(table, SWT.FILL);
		TableColumn sc2 = new TableColumn(table, SWT.FILL);
		TableColumn tc1 = new TableColumn(table, SWT.FILL);
		TableColumn tc2 = new TableColumn(table, SWT.FILL);
		sc1.setText("模式上传源线路/编号");
		sc2.setText("模式上传源车站/编号");
		tc2.setText("模式广播目的线路/编号");
		tc1.setText("进入的模式名称/编号");
		sc1.setWidth(130);
		sc2.setWidth(130);
		tc1.setWidth(130);
		tc2.setWidth(150);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		for (String[] item : validationList) {
			TableItem item1 = new TableItem(table, SWT.NONE);
			item1.setText(3, item[0]);
			item1.setText(2, item[1]);
			item1.setText(0, item[2]);
			item1.setText(1, item[3]);
			logger.debug("validationList: " + "[" + item[2] + "]" + "[" + item[3] + "]" + "[" + item[1] + "]" + " ["
					+ item[0] + "]");
		}

		sc.setContent(cp);

		Composite bottomComposite = new Composite(composite, SWT.NONE);
		GridData gdb = new GridData(SWT.FILL, SWT.FILL, true, false);
		gdb.heightHint = 55;
		bottomComposite.setLayoutData(gdb);
		final GridLayout gridLayoutY = new GridLayout();
		gridLayoutY.verticalSpacing = 0;
		gridLayoutY.marginWidth = 10;
		gridLayoutY.marginHeight = 0;
		gridLayoutY.horizontalSpacing = 10;
		gridLayoutY.numColumns = 2;
		bottomComposite.setLayout(gridLayoutY);

		btnOk = new Button(bottomComposite, SWT.NONE);
		btnOk.addSelectionListener(validateAction);
		// btnOk.setFont(font);
		GridData gd4 = new GridData(SWT.RIGHT, SWT.CENTER, true, true);
		gd4.heightHint = 30;
		gd4.widthHint = 75;
		btnOk.setLayoutData(gd4);
		btnOk.setText("确定");

		btnCancel = new Button(bottomComposite, SWT.NONE);
		btnCancel.addSelectionListener(exitAction);
		// btnCancel.setFont(font);
		GridData gd5 = new GridData(SWT.LEFT, SWT.CENTER, false, true);
		gd5.heightHint = 30;
		gd5.widthHint = 75;
		btnCancel.setLayoutData(gd5);
		btnCancel.setText("关 闭");

	}

	/**
	 * @return the validationList
	 */
	public List<String[]> getValidationList() {
		return validationList;
	}

	/**
	 * @param validationList
	 *            the validationList to set
	 */
	public void setValidationList(List<String[]> validationList) {
		this.validationList = validationList;
	}

}
