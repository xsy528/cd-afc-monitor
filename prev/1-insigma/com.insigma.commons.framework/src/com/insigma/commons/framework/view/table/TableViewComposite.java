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
package com.insigma.commons.framework.view.table;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.table.TableGrid;
import com.insigma.commons.framework.function.table.TableViewFunction;
import com.insigma.commons.framework.function.table.TableViewFunction.TableViewType;
import com.insigma.commons.framework.view.FunctionComposite;
import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.anotation.data.AnnotationData;
import com.insigma.commons.ui.anotation.data.AnnotationDataParse;
import com.insigma.commons.ui.control.IPageChangedListener;
import com.insigma.commons.ui.control.PageNavigator;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.form.ObjectTableViewer;
import com.insigma.commons.ui.form.PropertyViewer;
import com.insigma.commons.ui.widgets.Menu;
import com.insigma.commons.ui.widgets.MenuItem;

public class TableViewComposite extends FunctionComposite {

	protected TableViewFunction function;

	protected PropertyViewer tableViewMaster;

	protected ObjectTableViewer tableView;

	protected Composite compositeAction;

	protected PageNavigator pageNavigator;

	private Menu contextMenu;

	private int[] widths;

	public TableViewComposite(Composite parent, int style) {
		super(parent, style);
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(TableViewFunction func) {
		if (this.function != null) {
			return;
		}
		context.setFunction(func);
		this.function = func;
		setLayout(new GridLayout());

		if (func.getTableViewType() != TableViewType.SINGLE) {

			tableViewMaster = new PropertyViewer(this, SWT.BORDER);
			tableViewMaster.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		}

		if (func.getTableViewType() != TableViewType.PROPERTY) {

			if (function.getActions() != null && function.getActions().size() > 0) {
				buildButtonBar(this, function.getActions());
			}

			tableView = new ObjectTableViewer(this, SWT.BORDER | function.getSelectMode());
			tableView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			tableView.setColumn(func.getColumns());
			tableView.setSelectionMode(function.getSelectMode());//设置
			widths = func.getColumnWidth();

			if (function.getOverrideSuffix() != null) {
				tableView.setOverrideSuffix(function.getOverrideSuffix());
			}

			if (function.getValueClass() != null) {
				tableView.setHeader(function.getValueClass());
			}

			if (function.getTableContext() != null) {
				contextMenu = new Menu(tableView);
				tableView.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseDown(MouseEvent arg0) {
						if (arg0.button == 3) {
							tableView.setMenu(contextMenu);
						}
					}
				});
				for (final Action action : function.getTableContext()) {
					MenuItem item = new MenuItem(contextMenu, SWT.NONE);
					item.setText(action.getText());
					item.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							performAction(action);
						}
					});
				}
			}

			tableView.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (getFrameworkComposite() != null) {
						getFrameworkComposite().validate(getRequest());
					}
					validate(getRequest());
				}
			});

			if (function.getTableDoubleClickAction() != null) {
				tableView.addSelectionListener(new SelectionAdapter() {
					@Override
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
		}

		performAction(func.getLoadAction());

		validate(getRequest());
	}

	public Request getRequest() {
		SearchRequest request = new SearchRequest();
		request.setContext(context);
		if (pageNavigator != null) {
			request.setPage(pageNavigator.getCurrentPage());
			request.setPageSize(pageNavigator.getPageSize());
		}
		if (tableView != null) {
			request.setSelection(tableView.getSelectionIndicesObject());
		}
		return request;
	}

	public void setHeader(Class<?> cls) {
		if (tableView != null) {
			tableView.setHeader(cls);
		}
	}

	public void Reload(Action action) {
		performAction(action);
	}

	public void setResponse(Response response) {
		if (this.isDisposed()) {
			return;
		}
		if (tableViewMaster != null) {
			tableViewMaster.reset();
		}
		if (tableView != null) {
			tableView.reset();
		}
		if (getFrameworkComposite() != null) {
			getFrameworkComposite().validate(getRequest());
		}
		if (response.getValue() != null && response.getValue() instanceof TableGrid) {
			TableGrid datagrid = (TableGrid) response.getValue();
			if (pageNavigator != null) {
				pageNavigator.setTotalRecord(datagrid.getTotalSize());
				pageNavigator.setCurrentPage(datagrid.getCurrentPage());
				pageNavigator.setPageSize(datagrid.getPageSize());
				pageNavigator.refresh();
			}

			if (tableViewMaster != null && datagrid.getParent() != null) {
				tableViewMaster.setObject(datagrid.getParent());
			}

			if (null != tableView && null != datagrid.getContent() && datagrid.getContent().size() > 0) {
				Class<? extends Object> dataClazz = datagrid.getContent().get(0).getClass();
				if (dataClazz.isArray()) {
					tableView.fillArrayList((List<Object[]>) datagrid.getContent());
				} else if (BeanUtil.isUserDefinedClass(dataClazz)) {
					tableView.setObjectList(datagrid.getContent());
				} else {
					Field field = (Field) datagrid.getParent();
					BeanField beanField = new BeanField(field);
					Map<String, AnnotationData> fieldAnnotations = AnnotationDataParse.parseAnnotations(field);
					beanField.setFieldAnnotations(fieldAnnotations);
					tableView.setHeader(beanField, dataClazz);
					tableView.setObjectList(datagrid.getContent());
				}

				if (widths != null) {
					tableView.setWidths(widths);
				}
			}
		}
	}
}
