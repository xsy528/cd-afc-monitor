/* 
 * 日期：2010-11-22
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.commons.ui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.insigma.commons.ui.FormUtil;
import com.insigma.commons.ui.control.GifPlayer;
import com.insigma.commons.ui.widgets.Label;
import com.swtdesigner.SWTResourceManager;

/**
 * Ticket: 弹出滚动进度条对话框
 * 
 * @author Zhou-Xiaowei
 */
public class ProgressBarDialog extends Dialog {

	private Shell shell;

	private GifPlayer imageL;

	private Composite composite;

	private String fileName = "/pending4.gif";

	private String text = "正在加载，请稍候...";

	public ProgressBarDialog(Shell shell, int style) {
		super(shell, style);
	}

	/**
	 * Open the dialog
	 * 
	 * @return the result
	 */
	public void open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public void close() {
		if (null != shell && !shell.isDisposed()) {
			shell.close();
		}

		if (imageL != null && imageL.isDisposed()) {
			imageL.dispose();
		}
	}

	/**
	 * Create contents of the dialog
	 */
	protected void createContents() {
		shell = new Shell(getParent(), SWT.APPLICATION_MODAL);
		int width = 291;
		int height = 180;
		shell.setSize(width, height);
		shell.setLocation(FormUtil.getCenterLocation(width, height));
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);

		shell.setLayout(new FillLayout());
		composite = new Composite(shell, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(255, 255, 255));
		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.numColumns = 2;
		composite.setLayout(gridLayout);

		new Label(composite, SWT.NONE);

		final Label close = new Label(composite, SWT.NONE);
		// close.setImage(SWTResourceManager.getImage(ProgressBarDialog.class,
		// "/com/insigma/commons/ui/images/caca.jpg"));
		close.setText("关闭 ");
		close.setFont(SWTResourceManager.getFont("隶书", 12, SWT.SIMPLE));
		close.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		GridData gd0 = new GridData(SWT.RIGHT, SWT.TOP, true, true);
		close.setLayoutData(gd0);

		imageL = new GifPlayer(composite);
		GridData gd1 = new GridData(SWT.RIGHT, SWT.CENTER, true, true);
		imageL.setLayoutData(gd1);
		imageL.setImage(ProgressBarDialog.class.getResourceAsStream(fileName));

		Label middleLabel = new Label(composite, SWT.NONE);
		GridData gd2 = new GridData(SWT.LEFT, SWT.CENTER, true, true);
		middleLabel.setLayoutData(gd2);
		middleLabel.setText(text);

		new Label(composite, SWT.NONE);

		Label blank3 = new Label(composite, SWT.NONE);
		blank3.setText(" ");
		GridData gd3 = new GridData(SWT.RIGHT, SWT.DOWN, true, true);
		blank3.setLayoutData(gd3);

		close.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent arg0) {
			}

			public void mouseDown(MouseEvent arg0) {
				shell.close();
				if (imageL != null && imageL.isDisposed()) {
					imageL.dispose();
				}
			}

			public void mouseUp(MouseEvent arg0) {
			}

		});

		close.addMouseTrackListener(new MouseTrackListener() {

			public void mouseEnter(MouseEvent arg0) {
				close.setFont(SWTResourceManager.getFont("隶书", 12, SWT.BOLD));
				close.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
			}

			public void mouseExit(MouseEvent arg0) {
				close.setFont(SWTResourceManager.getFont("隶书", 12, SWT.SIMPLE));
				close.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
			}

			public void mouseHover(MouseEvent arg0) {
			}

		});

	}

	public void setBackgroundImage(String path) {
		composite.setBackgroundImage(SWTResourceManager.getImage(ProgressBarDialog.class, path));
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

}
