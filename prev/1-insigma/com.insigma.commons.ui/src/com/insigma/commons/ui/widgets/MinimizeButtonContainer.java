package com.insigma.commons.ui.widgets;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * 
 * @author DingLuofeng
 *
 */
public class MinimizeButtonContainer extends Composite {

	private Map<Integer, MinMaxButton> MinMaxButtonMap = new HashMap<Integer, MinMaxButton>();

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public MinimizeButtonContainer(Composite parent, int style) {
		super(parent, style);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.verticalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 0;
		setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		setLayout(gridLayout);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void createButton(final CTabFolder control) {
		if (MinMaxButtonMap.containsKey(control.hashCode())) {
			return;
		}
		MinMaxButton maxButton = new MinMaxButton(this, SWT.BORDER);
		GridData gd_maxButton = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		maxButton.setLayoutData(gd_maxButton);

		//TODO control.getToolTipText();
		Image image = null;
		String title = "";
		if (control.getItems().length > 0) {
			image = control.getItem(0).getImage();
			title = control.getItem(0).getText();
		}

		maxButton.createButtin(image, title, SWT.VERTICAL);
		maxButton.setRestoreItemSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				dispostButton(control);
				if (control instanceof CTabFolder) {
					CTabFolder ctab = (CTabFolder) control;
					ctab.restoreCTabFolder(ctab);
				}
			}

		});
		MinMaxButtonMap.put(control.hashCode(), maxButton);
	}

	public void dispostButton(Control control) {
		MinMaxButton maxButton = MinMaxButtonMap.remove(control.hashCode());
		if (maxButton != null && !maxButton.isDisposed()) {
			maxButton.dispose();
		}
	}

}
