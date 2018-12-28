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

import java.lang.reflect.Constructor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.insigma.commons.framework.IViewComponent;
import com.insigma.commons.framework.function.dialog.CustomDialogFunction;

public class CustomViewDialog extends FrameworkDialog {

	public CustomViewDialog(Shell parent, int style) {
		super(parent, style);
	}

	public CustomViewDialog(Shell parent) {
		this(parent, SWT.NONE);
	}

	@Override
	protected IViewComponent createComposite() {
		CustomDialogFunction customDialogFunction = (CustomDialogFunction) function;

		if (customDialogFunction.getViewComponentBuilder() != null) {
			Composite composite = customDialogFunction.getViewComponentBuilder().create(shell);
			final GridData gd_compositeMain = new GridData(SWT.FILL, SWT.FILL, true, true);
			composite.setLayoutData(gd_compositeMain);
			if (composite instanceof IViewComponent) {
				return (IViewComponent) composite;
			}
		} else {
			Class<?> compositemain = customDialogFunction.getComposite();
			if (compositemain != null) {
				try {
					Constructor<?> constructor = compositemain.getConstructor(Composite.class, int.class);
					Object object = constructor.newInstance(shell, SWT.NONE);
					Composite composite = (Composite) object;
					final GridData gd_compositeMain = new GridData(SWT.FILL, SWT.FILL, true, true);
					composite.setLayoutData(gd_compositeMain);
					if (object instanceof IViewComponent) {
						return (IViewComponent) object;
					}
				} catch (Exception e) {
					getLogger().error("组件构造错误", e);
				}
			}
		}
		return null;
	}
}
