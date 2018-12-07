package com.insigma.commons.framework.view.table;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.function.table.MultiTableViewFunction;
import com.insigma.commons.framework.view.FunctionComposite;
import com.insigma.commons.framework.view.tabletree.PageNavigatorTreeComposite;

/**
 * 创建时间 2010-11-10 下午03:11:18<br>
 * 描述：<br>
 * Ticket:
 * 
 * @author DingLuofeng
 */
public class MultiTableViewComposite extends FunctionComposite {

	protected MultiTableViewFunction function;

	private PageNavigatorTreeComposite mastorComposite;

	private PageNavigatorTreeComposite childComposite;

	public MultiTableViewComposite(Composite parent, int style) {
		super(parent, style);
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(MultiTableViewFunction func) {
		if (this.function != null) {
			return;
		}
		context.setFunction(func);
		this.function = func;
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		setLayout(new GridLayout());

		final SashForm sashForm = new SashForm(this, function.getLayoutStyle());
		// sashForm.setWeights(new int[] {
		// 4, 6});
		sashForm.setLayout(new GridLayout());
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		mastorComposite = new PageNavigatorTreeComposite(sashForm, SWT.NONE);
		mastorComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		mastorComposite.setLayout(new GridLayout());
		mastorComposite.setFunction(func.getParentFunction());

		if (func.getChildFunction() != null) {
			childComposite = new PageNavigatorTreeComposite(sashForm, SWT.NONE);
			childComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			childComposite.setLayout(new GridLayout());
			childComposite.setFunction(func.getChildFunction());

			mastorComposite.setChildTableComposite(childComposite);
		}

	}

	public Request getRequest() {
		return mastorComposite.getRequest();
	}

	public void Reload(Action action) {
		performAction(action);
	}

	public void setResponse(Response response) {
		if (childComposite != null) {
			childComposite.refresh();
		}

		if (mastorComposite != null && !mastorComposite.isDisposed())
			mastorComposite.setResponse(response);
	}

}
