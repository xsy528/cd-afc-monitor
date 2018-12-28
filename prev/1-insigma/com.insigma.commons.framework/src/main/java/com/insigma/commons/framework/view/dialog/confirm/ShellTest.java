package com.insigma.commons.framework.view.dialog.confirm;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.insigma.commons.framework.application.WindowCloseListener;
import com.swtdesigner.SWTResourceManager;

public class ShellTest extends Shell {

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			ShellTest shell = new ShellTest(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public ShellTest(Display display) {
		super(display, SWT.SHELL_TRIM);

		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ExitConfirmDialog confirmDialog = new ExitConfirmDialog(getShell(), SWT.None);
				List<WindowCloseListener> dataList = new ArrayList<WindowCloseListener>();
				dataList.add(new WindowCloseListener() {
					@Override
					public boolean prepare() {
						return true;
					}

					@Override
					public void beforeClose() {
						// TODO Auto-generated method stub

					}

					@Override
					public void afterClose() {
						// TODO Auto-generated method stub

					}

					@Override
					public String getName() {
						return "111112222222222222222222222222222222222222222222222222222222";
					}

					@Override
					public Image getImage() {
						// TODO Auto-generated method stub
						return SWTResourceManager.getImage(ShellTest.class,
								"/com/insigma/commons/ui/images/green1.png");
					}
				});
				confirmDialog.setObjectList(dataList);
				Object open = confirmDialog.open();
				System.out.println(open);
			}
		});
		btnNewButton.setBounds(30, 37, 80, 27);
		btnNewButton.setText("New Button");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
