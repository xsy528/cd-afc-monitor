/**
 * iFrameWork 框架
 * 
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description   
 * @date          2009-4-2
 * @history       
 */
package com.insigma.commons.framework.view;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;

import com.insigma.commons.application.Application;
import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.IResponseCode;
import com.insigma.commons.framework.IViewComponentBuilder;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.application.Module;
import com.insigma.commons.framework.application.UIPlatForm;
import com.insigma.commons.framework.extend.UserFunction;
import com.insigma.commons.framework.extend.UserModule;
import com.insigma.commons.ui.control.StatusBar;
import com.insigma.commons.ui.widgets.ToolBar;
import com.insigma.commons.ui.widgets.ToolItem;
import com.swtdesigner.SWTResourceManager;

public class ExpandBarFrameworkWindow extends FrameworkWindow {

	protected ExpandBar bar;

	protected ToolItemSelectionAdapter toolItemSelectionAdapter = new ToolItemSelectionAdapter();

	private CompositeFrameWork compsoite;

	private ToolBar topComposite;

	class ToolItemSelectionAdapter extends SelectionAdapter {
		public void widgetSelected(SelectionEvent arg0) {
			Object data = arg0.widget.getData();
			if (data == null || !(data instanceof Function)) {
				return;
			}
			Function function = (Function) data;
			compsoite.handleFunction(function);
		}
	}

	public ExpandBarFrameworkWindow(Display parent, int style) {
		super(parent, style);
		GridLayout gridLayout = new GridLayout();
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		setLayout(gridLayout);

		// DialogHeaderComposite header=new
		// DialogHeaderComposite(this,SWT.NONE);
		// final GridData gd_insigmaLogoComposite = new GridData(SWT.FILL,
		// SWT.FILL, true, false);
		// gd_insigmaLogoComposite.heightHint = 70;
		// header.setLayoutData(gd_insigmaLogoComposite);

		topComposite = new ToolBar(this, SWT.FLAT | SWT.WRAP | SWT.RIGHT);

		{
			final Label label = new Label(this, SWT.HORIZONTAL | SWT.SEPARATOR);
			label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
			label.setText("Label");
		}

		SashForm form = new SashForm(this, SWT.NONE);
		form.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite com = new Composite(form, SWT.NONE);
		com.setLayout(new FillLayout());
		bar = new ExpandBar(com, SWT.V_SCROLL);

		compsoite = new CompositeFrameWork(form, SWT.NONE);
		compsoite.createMainComposite();

		form.setWeights(new int[] { 20, 80 });

		{
			final Label label = new Label(this, SWT.HORIZONTAL | SWT.SEPARATOR);
			label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
			label.setText("Label");
		}
		statusBar = new StatusBar(this, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 32;
		statusBar.setLayoutData(gridData);
		statusBar.setLayout(new GridLayout());
	}

	public void setPlatForm(UIPlatForm platForm) {

		super.setPlatForm(platForm);

		if (platForm.getActions() != null) {
			for (final Action action : platForm.getActions()) {
				ToolItem item = new ToolItem(topComposite, SWT.FLAT | SWT.WRAP | SWT.RIGHT);
				item.setText(action.getText());
				item.addSelectionListener(new SelectionListener() {

					public void widgetSelected(SelectionEvent event) {
						ActionExecutor executor = new ActionExecutor(ExpandBarFrameworkWindow.this);
						Request request = new Request();
						request.setAction(action);
						executor.execute(action, request);
					}

					public void widgetDefaultSelected(SelectionEvent event) {
					}
				});
			}
		}

		for (final Module module : platForm.getModules()) {
			String mid = module.getFunctionID();
			if (mid == null) {
				logger.warn("Module:[" + module.getText() + "]没有定义functionId，默认初始化该module");
				// continue;
			} else {
				boolean hasAuth = Application.getUser().hasModule(mid);
				if (!hasAuth) {
					logger.debug(
							"用户：[" + Application.getUser().getUserName() + "]没有module:" + module + "的权限,忽略该module的初始化");
					continue;
				}
			}

			ExpandItem item0 = new ExpandItem(bar, SWT.NONE);
			if (module.getText() != null) {
				item0.setText(module.getText());
			}
			Composite composite = createExpandComposite();
			if (module instanceof UserModule) {
				// 模拟一个function
				UserFunction uf = new UserFunction();
				uf.setTitle(module.getText());
				uf.setImage(module.getImage());
				UserModule userModule = (UserModule) module;
				if (userModule.getViewComponentBuilder() != null) {
					uf.setViewComponentBuilder(userModule.getViewComponentBuilder());
				} else {
					final Class<?> compositeClass = userModule.getComposite();
					IViewComponentBuilder viewBuilder = new IViewComponentBuilder() {

						public Composite create(Composite parent) {
							try {
								Constructor<?> constructor = compositeClass.getConstructor(Composite.class, int.class);
								Object compositeObj = constructor.newInstance(parent, SWT.BORDER);
								return (Composite) compositeObj;
							} catch (Exception e) {
								e.printStackTrace();
								return null;
							}
						}
					};
					uf.setViewComponentBuilder(viewBuilder);

				}

				List<Function> fs = new ArrayList<Function>();
				fs.add(uf);
				module.setFunction(fs);
			}

			if (module.getFunction() != null) {
				for (Function function : module.getFunction()) {
					String fid = function.getFunctionID();
					if (fid == null) {
						logger.warn("function:[" + function.getTitle() + "]没有定义functionId,默认初始化该function");
					} else {
						boolean hasAuth = Application.getUser().hasFunction(fid);
						if (!hasAuth) {
							logger.debug("用户:[" + Application.getUser().getUserName() + "]没有function:" + function
									+ "的权限,忽略该function的初始化");
							continue;
						}
					}

					Label image = new Label(composite, SWT.NONE);
					image.setText(function.getTitle());
					image.setImage(SWTResourceManager.getImage(FrameworkComposite.class, function.getImage()));
					Link text = new Link(composite, SWT.NONE);
					text.setData(function);
					text.addSelectionListener(toolItemSelectionAdapter);
					text.setText("<a>" + function.getTitle() + "</a>");

				}
			}
			item0.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			item0.setControl(composite);
			// item0.setImage(image);

		}

		if (platForm.getStatusPages() != null) {
			createStatusBar();
		}
	}

	private Composite createExpandComposite() {
		Composite composite = new Composite(bar, SWT.NONE);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginLeft = layout.marginRight = 10;
		layout.marginBottom = layout.marginTop = 10;
		layout.verticalSpacing = 10;
		composite.setLayout(layout);
		return composite;
	}

	@Override
	public void dispose() {
		// if (Application.systemState == SystemState.LOGOUT) {
		super.dispose();
		// return;
		// }
		// if (MessageForm.Query("提示信息", "确定要退出工作台吗？") == SWT.YES) {
		// ThreadManager.getInstance().stopAll();
		// Application.systemState = SystemState.EXIT;
		// ThreadManager.getInstance().stopAll();
		// super.dispose();
		// }
	}

	public void setResponse(Response response) {
		if ((response.getCode() & IResponseCode.DIALOG_CLOSE) != 0) {
			this.close();
		}
	}
}
