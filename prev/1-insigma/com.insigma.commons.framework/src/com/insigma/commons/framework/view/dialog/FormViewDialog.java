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

import com.insigma.commons.framework.IRequestGenerator;
import com.insigma.commons.framework.IResponseDisplayer;
import com.insigma.commons.framework.IViewComponent;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.function.form.FormFunction;
import com.insigma.commons.framework.view.search.FormComposite;

public class FormViewDialog<T> extends FrameworkDialog implements IRequestGenerator, IResponseDisplayer {

	private FormComposite<T> formcomposite;

	public FormViewDialog(Shell parent, int style) {
		super(parent, style);
	}

	public FormViewDialog(Shell parent) {
		this(parent, SWT.NONE);
	}

	@Override
	protected IViewComponent createComposite() {
		try {
			formcomposite = new FormComposite<T>(shell, SWT.NONE);
			formcomposite.setFunction((FormFunction<T>) function.getFunction());
			return formcomposite;
		} catch (Exception e) {
			getLogger().error("组件构造错误", e);
		}
		return null;
	}

	public void reset() {
		formcomposite.reset();
	}

	public Request getRequest() {
		Request request = formcomposite.getRequest();
		request.setParent(openRequest);
		return request;
	}
}
