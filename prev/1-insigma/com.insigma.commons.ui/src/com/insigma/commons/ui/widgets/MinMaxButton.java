package com.insigma.commons.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.swtdesigner.SWTResourceManager;

/**
 * 
 * @author DingLuofeng
 *
 */
public class MinMaxButton extends Composite {

	protected static Image restoreImage = SWTResourceManager.getImage(MinMaxButton.class,
			"/com/insigma/commons/ui/widgets/images/restore.png");

	protected static Image defaultImage = SWTResourceManager.getImage(MinMaxButton.class,
			"/com/insigma/commons/ui/widgets/images/default.png");

	private SelectionAdapter restoreItemSelectionListener;

	private SelectionAdapter imageItemSelectionListener;

	private ToolItem imageItem;

	private ToolItem restoreItem;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public MinMaxButton(Composite parent, int style) {
		super(parent, style);
		//		createButtin(defaultImage, "Test", SWT.VERTICAL);
	}

	/**
	 * 
	 * @param itemImage
	 * @param imageTipText
	 * @param style
	 *            SWT.VERTICAL|SWT.HORIZONTAL
	 */
	public void createButtin(Image itemImage, String imageTipText, int style) {

		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.verticalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 0;
		setLayout(gridLayout);
		CoolBar coolBar = new CoolBar(this, style | SWT.FLAT);
		GridData gd_coolBar = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_coolBar.heightHint = 60;
		coolBar.setLayoutData(gd_coolBar);

		CoolItem coolItem = new CoolItem(coolBar, SWT.NONE);

		ToolBar toolBar = new ToolBar(coolBar, style | SWT.FLAT | SWT.RIGHT);
		coolItem.setControl(toolBar);

		restoreItem = new ToolItem(toolBar, SWT.NONE);
		restoreItem.setToolTipText("还原");
		restoreItem.setImage(restoreImage);

		imageItem = new ToolItem(toolBar, SWT.NONE);
		if (itemImage != null) {
			imageItem.setImage(itemImage);
		} else {
			imageItem.setImage(defaultImage);
		}
		imageItem.setToolTipText(imageTipText);
		// imageItem.setImage(arg0);
		coolBar.packSize(false);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/**
	 * @return the restoreItemSelectionListener
	 */
	public SelectionAdapter getRestoreItemSelectionListener() {
		return restoreItemSelectionListener;
	}

	/**
	 * @param restoreItemSelectionListener
	 *            the restoreItemSelectionListener to set
	 */
	public void setRestoreItemSelectionListener(SelectionAdapter restoreItemSelectionListener) {
		this.restoreItemSelectionListener = restoreItemSelectionListener;
		restoreItem.addSelectionListener(restoreItemSelectionListener);
	}

	/**
	 * @return the imageItemSelectionListener
	 */
	public SelectionAdapter getImageItemSelectionListener() {
		return imageItemSelectionListener;
	}

	/**
	 * @param imageItemSelectionListener
	 *            the imageItemSelectionListener to set
	 */
	public void setImageItemSelectionListener(SelectionAdapter imageItemSelectionListener) {
		this.imageItemSelectionListener = imageItemSelectionListener;
		imageItem.addSelectionListener(imageItemSelectionListener);
	}

}
