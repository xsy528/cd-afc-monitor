/**
 * iFrameWork 框架
 * 
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description   
 * @date          2009-4-6
 * @copyright     浙江浙大网新众合轨道交通工程有限公司 
 * @history       
 */
package com.insigma.commons.framework.view.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import com.insigma.commons.framework.IViewComponent;
import com.insigma.commons.framework.function.table.MultiTableViewFunction;
import com.insigma.commons.framework.view.table.MultiTableViewComposite;

public class MultiTableViewDialog extends FrameworkDialog {

	private MultiTableViewComposite tableviewer;

	public MultiTableViewDialog(Shell parent, int style) {
		super(parent, style);
	}

	public MultiTableViewDialog(Shell parent) {
		this(parent, SWT.NONE);
	}

	protected IViewComponent createComposite() {
		tableviewer = new MultiTableViewComposite(shell, SWT.NONE);
		tableviewer.setFunction((MultiTableViewFunction) function.getFunction());
		return tableviewer;
	}
}
