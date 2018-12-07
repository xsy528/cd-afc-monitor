/* 
 * 日期：2010-11-8
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.framework.view.tabletree;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.table.PageNavigatorTableViewFunction;
import com.insigma.commons.framework.function.table.PageNavigatorTableViewFunction.ButtonLayout;
import com.insigma.commons.framework.function.table.TableGrid;
import com.insigma.commons.framework.view.FunctionComposite;
import com.insigma.commons.ui.control.IPageChangedListener;
import com.insigma.commons.ui.control.PageNavigator;
import com.insigma.commons.ui.tree.TreeNode;
import com.insigma.commons.ui.widgets.EnhanceComposite;
import com.insigma.commons.ui.widgets.Label;
import com.insigma.commons.ui.widgets.Menu;
import com.insigma.commons.ui.widgets.MenuItem;
import com.swtdesigner.SWTResourceManager;

/**
 * 创建时间 2010-11-10 下午01:07:10<br>
 * 描述：<br>
 * Ticket:
 * 
 * @author DingLuofeng
 */
public class PageNavigatorTreeComposite extends FunctionComposite {
	public static final Font TITIL_FONT = SWTResourceManager.getFont("微软雅黑", 14, SWT.BOLD);

	protected PageNavigatorTableViewFunction function;

	protected TreeEdit tableView;

	// protected Composite compositeAction;

	protected PageNavigator pageNavigator;

	private Menu contextMenu;

	private int[] widths;

	private Composite compositeTop;

	private Composite compositeLeft;

	private Composite compositeRight;

	private Composite compositeBottom;

	private Composite buttomComposite;

	private PageNavigatorTreeComposite childTableComposite;

	private int buttonNumColumns = 0;

	public PageNavigatorTreeComposite(Composite parent, int style) {
		super(parent, style);
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(PageNavigatorTableViewFunction func) {
		if (this.function != null) {
			return;
		}
		context.setFunction(func);
		this.function = func;
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		setLayout(gridLayout);

		left(this, function.getButtomLayout());

		top(this, function.getButtomLayout());

		right(this, function.getButtomLayout());

		tableView = new TreeEdit(this, SWT.BORDER | SWT.MULTI);
		tableView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		tableView.setColumn(func.getColumns());
		widths = func.getColumnWidth();
		if (widths != null && widths.length == func.getColumns().length)
			tableView.setWeight(func.getColumnWidth());
		contextMenu = new Menu(tableView);
		tableView.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent arg0) {
				if (arg0.button == 3) {
					tableView.setMenu(contextMenu);
				}
			}
		});

		if (func.getEditColumns() != null) {
			tableView.setEditable(func.getEditColumns(), true);
		}
		tableView.addEditListener(func.getEditListener());

		tableView.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (getFrameworkComposite() != null) {
					getFrameworkComposite().validate(getRequest());
				}
				validate(getRequest());
				setChildResponse();
			}

		});
		if (function.getTableDoubleClickAction() != null) {
			tableView.addSelectionListener(new SelectionAdapter() {
				public void widgetDefaultSelected(SelectionEvent e) {
					performAction(function.getTableDoubleClickAction());
				}
			});
		}

		if (func.isMultipage()) {
			pageNavigator = new PageNavigator(this, SWT.NONE);
			pageNavigator.setPageSize(20);
			pageNavigator.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
			pageNavigator.setCurrentPage(1);
			pageNavigator.setData(func);
			pageNavigator.setPageChangedListener(new IPageChangedListener() {
				public void pageChanged(int oldPage, int newPage) {
					performAction(function.getPageChangedAction());
				}
			});
		}

		bottom(this, function.getButtomLayout());

		if (function.getTableContext() != null) {
			for (Action action : function.getTableContext()) {
				MenuItem item = new MenuItem(contextMenu, SWT.NONE);
				item.setText(action.getText());
			}
		}

		createButtons();

		performAction(func.getLoadAction());

		validate(getRequest());
	}

	private void createButtons() {
		if (function.getActions() != null && function.getActions().size() > 0) {
			if (function.getButtomLayout() == ButtonLayout.RIGHT || function.getButtomLayout() == ButtonLayout.LEFT) {
				buttonNumColumns = 1;
			} else {
				buttonNumColumns = function.getActions().size();
			}
			EnhanceComposite enhanceComposite = buildButtonBar(buttomComposite, function.getActions());
			GridLayout gridLayout_1 = new GridLayout();
			gridLayout_1.numColumns = buttonNumColumns;
			gridLayout_1.verticalSpacing = 20;
			gridLayout_1.marginHeight = 0;
			gridLayout_1.horizontalSpacing = 20;
			gridLayout_1.marginWidth = 0;
			gridLayout_1.marginTop = 5;
			enhanceComposite.setLayout(gridLayout_1);
			enhanceComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		}
	}

	private void bottom(Composite composite, ButtonLayout buttonLayout) {
		compositeBottom = new Composite(composite, SWT.NONE);
		compositeBottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compositeBottom.setLayout(new GridLayout());
		if (buttonLayout == ButtonLayout.BOTTOM) {
			buttomComposite = compositeBottom;
		}
	}

	private void right(Composite composite, ButtonLayout buttonLayout) {
		compositeRight = new Composite(composite, SWT.NONE);
		final GridData gd_composite_1 = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 4);
		compositeRight.setLayoutData(gd_composite_1);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		compositeRight.setLayout(gridLayout);
		if (buttonLayout == ButtonLayout.RIGHT) {
			buttomComposite = compositeRight;
		}
	}

	private void left(Composite composite, ButtonLayout buttonLayout) {
		compositeLeft = new Composite(composite, SWT.NONE);
		final GridData gd_composite_1 = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 4);
		compositeLeft.setLayoutData(gd_composite_1);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		compositeLeft.setLayout(gridLayout);
		if (buttonLayout == ButtonLayout.LEFT) {
			buttomComposite = compositeLeft;
		}
	}

	private void top(Composite composite, ButtonLayout buttonLayout) {
		compositeTop = new Composite(composite, SWT.NONE);
		compositeTop.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compositeTop.setLayout(new GridLayout());
		if (function.getTitle() != null) {
			Label label = new Label(compositeTop, SWT.CENTER);
			label.setFont(TITIL_FONT);
			label.setText(function.getTitle());
		}
		if (buttonLayout == ButtonLayout.TOP) {
			buttomComposite = compositeTop;
		}
	}

	public Request getRequest() {
		SearchRequest request = new SearchRequest();
		request.setContext(context);
		if (pageNavigator != null) {
			request.setPage(pageNavigator.getCurrentPage());
			request.setPageSize(pageNavigator.getPageSize());
		}
		request.setSelection(tableView.getSelectionIndicesObject());
		return request;
	}

	public void Reload(Action action) {
		performAction(action);
	}

	private void setChildResponse() {
		if (childTableComposite != null && function.getRelateAction() != null) {
			Response response = function.getRelateAction().executeAction(getRequest());
			childTableComposite.setResponse(response);
		}
	}

	@SuppressWarnings("unchecked")
	public void setResponse(Response response) {
		tableView.reset();
		if (response.getValue() != null && response.getValue() instanceof TableGrid) {
			TableGrid datagrid = (TableGrid) response.getValue();
			if (pageNavigator != null) {
				pageNavigator.setTotalRecord(datagrid.getTotalSize());
				pageNavigator.setCurrentPage(datagrid.getCurrentPage());
				pageNavigator.setPageSize(datagrid.getPageSize());
				pageNavigator.refresh();
			}

			if (null != datagrid.getContent() && datagrid.getContent().size() > 0) {
				if (datagrid.getContent().get(0) instanceof TreeNode) {
					tableView.fillTree((List<TreeNode>) datagrid.getContent());
				} else {
					if (datagrid.getContent().get(0).getClass().isArray()) {
						tableView.fillArrays(datagrid.getContent());
					} else {
						tableView.fillObjectList(datagrid.getContent());
					}
				}
				if (widths != null) {
					tableView.setWidths(widths);
				}
			}
		}
		setChildResponse();
	}

	/*
	 * (non-Javadoc)
	 * @see com.insigma.commons.framework.view.FunctionComposite#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		if (tableView != null && !tableView.isDisposed()) {
			tableView.reset();
		}
		if (pageNavigator != null && !pageNavigator.isDisposed()) {
			pageNavigator.refresh();
		}
	}

	/**
	 * @param childTableComposite
	 *            the childTableComposite to set
	 */
	public void setChildTableComposite(PageNavigatorTreeComposite childTableComposite) {
		this.childTableComposite = childTableComposite;
	}

	/**
	 * @return the childTableComposite
	 */
	public PageNavigatorTreeComposite getChildTableComposite() {
		return childTableComposite;
	}

}
