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

import com.insigma.commons.framework.function.table.TableViewFunction;
import com.insigma.commons.framework.view.table.TableViewComposite;

public class TableViewDialog extends FrameworkDialog {

	private TableViewComposite tableviewer;

	public TableViewDialog(Shell parent, int style) {
		super(parent, style);
	}

	public TableViewDialog(Shell parent) {
		this(parent, SWT.NONE);
	}

	protected IViewComponent createComposite() {
		tableviewer = new TableViewComposite(shell, SWT.NONE);
		tableviewer.setFunction((TableViewFunction) function.getFunction());
		return tableviewer;
	}
}
