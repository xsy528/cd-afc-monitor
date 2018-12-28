/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.control;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.insigma.commons.ui.widgets.EnhanceComposite;
import com.insigma.commons.ui.widgets.IControl;
import com.swtdesigner.SWTResourceManager;

public class DialogHeaderComposite extends Composite implements IControl {

	private Label lblTitle;

	private Label lblDescription;

	private String title;

	private String description;

	public DialogHeaderComposite(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(255, 255, 255));
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.verticalSpacing = 0;
		gridLayout_1.marginWidth = 0;
		gridLayout_1.marginHeight = 0;
		gridLayout_1.horizontalSpacing = 0;
		gridLayout_1.numColumns = 2;
		setLayout(gridLayout_1);

		final Composite composite_1 = new Composite(this, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.marginLeft = 5;
		gridLayout.verticalSpacing = 10;
		gridLayout.horizontalSpacing = 20;
		composite_1.setLayout(gridLayout);
		composite_1.setBackground(SWTResourceManager.getColor(255, 255, 255));
		composite_1.setLayoutData(new GridData(GridData.FILL_BOTH));

		lblTitle = new Label(composite_1, SWT.NONE);
		final GridData gd_lblTitle = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_lblTitle.widthHint = 200;
		lblTitle.setLayoutData(gd_lblTitle);
		lblTitle.setForeground(SWTResourceManager.getColor(0, 128, 192));
		lblTitle.setFont(SWTResourceManager.getFont("宋体", 10, SWT.BOLD));
		lblTitle.setBackground(SWTResourceManager.getColor(255, 255, 255));
		//        lblTitle.setText("标题：");

		lblDescription = new Label(composite_1, SWT.WRAP);
		final GridData gd_lblDescription = new GridData(SWT.FILL, SWT.FILL, true, true);
		lblDescription.setLayoutData(gd_lblDescription);
		lblDescription.setBackground(SWTResourceManager.getColor(255, 255, 255));
		lblDescription.setText("描述：");

		final EnhanceComposite enhanceComposite_1 = new EnhanceComposite(this, SWT.NONE);
		enhanceComposite_1.setImage(SWTResourceManager.getImage(DialogHeaderComposite.class, "/logo.png"));
		enhanceComposite_1.setBackground(SWTResourceManager.getColor(255, 255, 255));
		final GridData gd_enhanceComposite_1 = new GridData(SWT.LEFT, SWT.FILL, false, true);
		gd_enhanceComposite_1.heightHint = 372;
		gd_enhanceComposite_1.widthHint = 169;
		enhanceComposite_1.setLayoutData(gd_enhanceComposite_1);

		final Label label_1 = new Label(this, SWT.HORIZONTAL | SWT.SEPARATOR);
		label_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label_1.setText("Label");

	}

	@Override
	protected void checkSubclass() {
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
		//        if (title != null) {
		//            lblTitle.setText(title);
		//        }
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
		if (description != null) {
			lblDescription.setText(cutString(description));
		}
	}

	public void setDescription(String description, RGB strColor, FontData fd) {
		this.description = description;
		if (description != null) {
			lblDescription.setText(cutString(description));
			lblDescription.setForeground(SWTResourceManager.getColor(strColor));
			lblDescription.setFont(SWTResourceManager.getFont(fd.getName(), fd.getHeight(), fd.getStyle()));// "宋体", 13, SWT.BOLD
		}
	}

	private String cutString(String description) {
		String str = "";
		if (description.length() >= 20) {
			str += description.substring(0, 20) + "\n";
			description = description.substring(20);
			str += cutString(description);
		} else {
			str += description;
		}
		return str;
	}

	public void reset() {
	}

}
