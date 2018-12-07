/* 
 * 日期：May 11, 2009
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.framework.view.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Shell;

import com.insigma.commons.framework.IViewComponent;
import com.insigma.commons.framework.function.search.SearchFunction;
import com.insigma.commons.framework.view.search.FormTableComposite;

public class SearchViewDialog extends FrameworkDialog {

	public SearchViewDialog(Shell parent) {
		super(parent);
	}

	public SearchViewDialog(Shell parent, int style) {
		super(parent, style);
	}

	protected IViewComponent createComposite() {
		FormTableComposite composite = new FormTableComposite(shell, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setFunction((SearchFunction) function.getFunction());
		return composite;
	}
}
