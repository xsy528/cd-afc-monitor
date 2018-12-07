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
package com.insigma.commons.framework.view.search;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableColumn;

import com.insigma.commons.codec.ReflectionUtil;
import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.function.form.Form;
import com.insigma.commons.framework.function.search.SearchFunction;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.framework.function.search.TreeSearchFunction;
import com.insigma.commons.framework.function.table.TableGrid;
import com.insigma.commons.framework.view.FunctionComposite;
import com.insigma.commons.framework.view.form.FormEditor;
import com.insigma.commons.framework.view.table.ObjectKTableViewer;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.anotation.ColumnView;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.control.IPageChangedListener;
import com.insigma.commons.ui.control.PageNavigator;
import com.insigma.commons.ui.convert.ColumnConvertor;
import com.insigma.commons.ui.form.ObjectTableViewer;
import com.insigma.commons.ui.tree.ObjectTree;
import com.insigma.commons.ui.widgets.Button;
import com.insigma.commons.ui.widgets.CTabFolder;
import com.insigma.commons.ui.widgets.EnhanceComposite;
import com.insigma.commons.ui.widgets.Menu;
import com.insigma.commons.ui.widgets.MenuItem;
import com.insigma.commons.ui.widgets.ToolBar;
import com.insigma.commons.ui.widgets.ToolItem;
import com.insigma.commons.ui.widgets.ktable.KTableCellSelectionListener;
import com.swtdesigner.SWTResourceManager;

public class FormTableComposite<T> extends FunctionComposite {

	private SearchFunction<T> function;

	private FormEditor<T> formEditor;

	private ObjectTableViewer tableviewer;

	private Menu contextMenu;

	private PageNavigator pageNavigator;

	private ObjectTree leftTree;

	private int[] widths;

	private boolean useKTable = false;

	private ObjectKTableViewer ktable;

	public FormTableComposite(Composite parent, int style) {
		super(parent, style);
		setBackground(getParent().getBackground());
	}

	public Function getFunction() {
		return function;
	}

	public void createTree() {

		TreeSearchFunction treeSearchFunction = (TreeSearchFunction) function;
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		setLayout(layout);

		if (treeSearchFunction.isHideTree()) {
			layout.horizontalSpacing = 0;
			setLayout(layout);

			EnhanceComposite composite = new EnhanceComposite(this, SWT.NONE);
			composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			composite.setLayout(new GridLayout());
			composite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
			GridData treeGridData = new GridData(GridData.FILL_VERTICAL);
			treeGridData.widthHint = 0;
			treeGridData.verticalSpan = 4;
			composite.setLayoutData(treeGridData);

			leftTree = new ObjectTree(composite, SWT.BORDER | SWT.CHECK);
			leftTree.setTreeProvider(treeSearchFunction.getTreeProvider());

			return;
		}

		CTabFolder folder = new CTabFolder(this, SWT.BORDER);
		GridData treeGridData = new GridData(GridData.FILL_VERTICAL);
		treeGridData.widthHint = 200;
		treeGridData.verticalSpan = 4;
		folder.setLayoutData(treeGridData);
		folder.setTabHeight(22);

		if (((TreeSearchFunction) function).isNavigatable()) {
			Composite toolbar = new Composite(folder, SWT.NONE);
			GridLayout barlayout = new GridLayout();
			barlayout.horizontalSpacing = 0;
			barlayout.verticalSpacing = 0;
			barlayout.marginBottom = 0;
			barlayout.marginTop = 0;
			barlayout.marginHeight = 0;

			barlayout.numColumns = 2;
			toolbar.setLayout(barlayout);
			Composite hintBar = new Composite(toolbar, SWT.NONE);
			hintBar.setLayoutData(new GridData(GridData.FILL_BOTH));
			ToolBar bar = new ToolBar(toolbar, SWT.NONE);
			folder.setTopRight(toolbar, SWT.FILL);

			final ToolItem leftItem = new ToolItem(bar, SWT.NONE);
			final ToolItem rightItem = new ToolItem(bar, SWT.NONE);
			leftItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					if (leftTree.getDepth() <= 1) {
						leftItem.setEnabled(false);
						return;
					}

					if (leftTree.getIsNodeType() != null
							&& leftTree.getIsNodeType().get(leftTree.getDepth() - 1) != null
							&& leftTree.getIsNodeType().get(leftTree.getDepth() - 1)) {
						leftTree.setDepth(leftTree.getDepth() - 2);
					} else {
						leftTree.setDepth(leftTree.getDepth() - 1);
					}

					if (leftTree.getDepth() <= 1) {
						leftItem.setEnabled(false);
					} else {
						leftItem.setEnabled(true);
						leftTree.setExpanded(2);
					}

					rightItem.setEnabled(true);
				}
			});
			leftItem.setImage(SWTResourceManager.getImage(getClass(), "/com/insigma/commons/ui/toolbar/left.png"));

			rightItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					if (leftTree.getDepth() >= leftTree.getMaxDepth()) {
						rightItem.setEnabled(false);
						return;
					}

					if (leftTree.getIsNodeType() != null
							&& leftTree.getIsNodeType().get(leftTree.getDepth() + 1) != null
							&& leftTree.getIsNodeType().get(leftTree.getDepth() + 1)) {
						leftTree.setDepth(leftTree.getDepth() + 2);
					} else {
						leftTree.setDepth(leftTree.getDepth() + 1);
					}

					leftTree.setExpanded(2);
					if (leftTree.getDepth() >= leftTree.getMaxDepth()) {
						rightItem.setEnabled(false);
					} else {
						rightItem.setEnabled(true);
					}

					leftItem.setEnabled(true);

				}
			});
			rightItem.setEnabled(false);
			rightItem.setImage(SWTResourceManager.getImage(getClass(), "/com/insigma/commons/ui/toolbar/right.png"));
			bar.layout();

			toolbar.layout();
		}

		CTabItem item = new CTabItem(folder, SWT.NONE);
		item.setText(treeSearchFunction.getTreeLabel());

		leftTree = new ObjectTree(folder, SWT.BORDER | SWT.CHECK);
		leftTree.setTreeProvider(treeSearchFunction.getTreeProvider());
		leftTree.setDepth(treeSearchFunction.getTreeDepth());
		leftTree.setExpanded(true);
		item.setControl(leftTree);

		folder.setSelection(0);

	}

	public void setFunction(SearchFunction<T> func) {

		if (this.function != null) {
			return;
		}
		context.setFunction(function);
		this.function = func;
		useKTable = func.useKTable;

		if (func.getStyle() == SearchFunction.TOOLBAR) {
			buildToolBar(this, func.getActions());
		}

		if (function instanceof TreeSearchFunction) {
			createTree();
		} else {
			GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			setLayout(layout);
		}

		if (function.getForm() != null || func.getStyle() == SearchFunction.ACTIONBAR) {
			EnhanceComposite composite = new EnhanceComposite(this, SWT.BORDER);
			composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			composite.setLayout(new GridLayout());
			composite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

			if (function.getForm() != null) {
				formEditor = new FormEditor(composite, SWT.NONE);
				GridData pdata = new GridData(GridData.FILL_HORIZONTAL);
				formEditor.setLayoutData(pdata);
				formEditor.setBackground(formEditor.getParent().getBackground());
				formEditor.setForm(function.getForm());
				formEditor.setSize(formEditor.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				formEditor.layout();
			}

			if (func.getStyle() == SearchFunction.ACTIONBAR) {
				buildButtonBar(composite, function.getActions());
			}

			composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			composite.layout();
		}
		if (useKTable) {
			ktable = new ObjectKTableViewer(this, SWT.BORDER);
			ktable.setFunction(func, func.fixedRows, func.fixedColumns);
			ktable.addCellSelectionListener(new KTableCellSelectionListener() {

				@Override
				public void fixedCellSelected(int col, int row, int statemask) {
					cellSelected(col, row, statemask);

				}

				@Override
				public void cellSelected(int col, int row, int statemask) {
					if (getFrameworkComposite() != null) {
						getFrameworkComposite().validate(getRequest());
					}
					validate(getRequest());

				}
			});
		} else {
			tableviewer = new ObjectTableViewer(this, SWT.BORDER);
			tableviewer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			tableviewer.setColumn(function.getColumns());
			widths = func.getColumnWidth();

			if (function.getOverrideSuffix() != null) {
				tableviewer.setOverrideSuffix(function.getOverrideSuffix());
			}

			if (function.getValueClass() != null) {
				tableviewer.setHeader(function.getValueClass());
			}

			if (widths != null && tableviewer.getColumn() != null) {
				tableviewer.setWidths(widths);
			}

			if (function.getTableContext() != null) {
				contextMenu = new Menu(tableviewer);
				tableviewer.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseDown(MouseEvent arg0) {
						if (arg0.button == 3) {
							tableviewer.setMenu(contextMenu);
						}
					}
				});

				for (Action action : function.getTableContext()) {
					MenuItem item = new MenuItem(contextMenu, SWT.NONE);
					item.setText(action.getText());
				}
			}

			tableviewer.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (getFrameworkComposite() != null) {
						getFrameworkComposite().validate(getRequest());
					}
					validate(getRequest());
				}
			});

			if (function.getTableDoubleClickAction() != null) {
				tableviewer.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						performAction(function.getTableDoubleClickAction());
					}
				});
			}
			if (func.isCellSelectionMode()) {
				tableviewer.setSelectionMode(tableviewer.CELL_SELECTION);
			}
		}

		/**
		 * revised by Zhou-Xiaowei 2010-12-18
		 */
		EnhanceComposite newcomposite = new EnhanceComposite(this, SWT.NONE);
		newcomposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		newcomposite.setLayout(layout);

		pageNavigator = new PageNavigator(newcomposite, SWT.NONE);
		pageNavigator.setPageSize(function.getPageSize());
		pageNavigator.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		pageNavigator.setCurrentPage(1);
		pageNavigator.setData(function);
		pageNavigator.setPageChangedListener(new IPageChangedListener() {
			public void pageChanged(int oldPage, int newPage) {
				performAction(function.getPageChangedAction());
			}
		});
//
//		Button exportButton = new Button(newcomposite, SWT.NONE);
//		exportButton.setText("当页导出");
//		exportButton.addSelectionListener(new ExportButtonSelectionAdapter());
//		if (!function.isbShowCurrentPageExportButton()) {
//			exportButton.setVisible(false);
//		}

		validate(getRequest());
	}

	public Request getRequest() {
		SearchRequest<T> request = new SearchRequest<T>();
		if (useKTable) {
			request.setSelectionData((List<T>) ktable.getSelectionDatas());
			request.setSelection(ktable.getSelectionDatas());
			request.setSelectionIndexs(ktable.getSelectIndexs());
		} else {
			request.setSelectionData((List<T>) tableviewer.getSelectionIndicesObject());
			request.setSelection(tableviewer.getSelectionIndicesObject());
			request.setSelectionIndexs(tableviewer.getSelectionIndices());
		}
		request.setContext(context);
		request.setPage(pageNavigator.getCurrentPage());
		request.setPageSize(pageNavigator.getPageSize());
		if (formEditor != null) {
			Form<T> form = formEditor.getForm();
			request.setForm(form);
		}
		if (leftTree != null) {
			request.setValue(leftTree.getChecked());
			request.setSelection(leftTree.getChecked());
		}
		return request;
	}

	@Override
	public void refresh() {
		if (formEditor != null) {
			formEditor.setForm(function.getForm());
		}
		if (useKTable) {

		} else {
			if (function.getColumns() != null && tableviewer.getColumn() != function.getColumns()) {
				tableviewer.setColumn(function.getColumns());
			}

			if (function.getColumnIndexes() != null) {
				int alignment = (function.getAlignment() == 0 ? SWT.LEFT : SWT.RIGHT);
				tableviewer.setAlignment(function.getColumnIndexes(), alignment);
			}
		}

		if (function instanceof TreeSearchFunction) {
			TreeSearchFunction treeSearchFunction = (TreeSearchFunction) function;
			if (leftTree.getTreeProvider() != treeSearchFunction.getTreeProvider()) {
				leftTree.removeAll();
				leftTree.setTreeProvider(treeSearchFunction.getTreeProvider());
			}
		}
		super.refresh();
	}

	public void setResponse(Response resp) {
		if (useKTable) {
			ktable.setResponse(resp);
		}
		if (resp instanceof SearchResponse) {
			SearchResponse response = (SearchResponse) resp;

			if (response.getData() != null) {
				TableGrid datagrid = response.getData();
				pageNavigator.setTotalRecord(datagrid.getTotalSize());
				pageNavigator.setCurrentPage(datagrid.getCurrentPage());
				pageNavigator.setPageSize(datagrid.getPageSize());
				pageNavigator.refresh();

				if (useKTable) {
					return;
				}

				if (datagrid.getContent() != null && datagrid.getContent().size() > 0) {

					if (datagrid.getContent().get(0).getClass().isArray()) {
						tableviewer.fillArrayList((List<Object[]>) datagrid.getContent());
					} else {
						tableviewer.setObjectList(datagrid.getContent());
					}

					if (widths != null) {
						tableviewer.setWidths(widths);
					}

					if (function.getColumnIndexes() != null) {
						int alignment = (function.getAlignment() == 0 ? SWT.LEFT : SWT.RIGHT);
						tableviewer.setAlignment(function.getColumnIndexes(), alignment);
					}
				} else {
					tableviewer.reset();
				}

				tableviewer.computeSize(SWT.DEFAULT, SWT.DEFAULT);

			}
		}
	}

	private List<Object[]> changeObjStyle(List<?> list) {
		//这里的list前台已经通过判断保证不为空
		List<Object[]> result = new ArrayList<Object[]>();

		int count = 0;
		for (Field fieldTemp : ReflectionUtil.getAllFields(list.get(0).getClass())) {
			for (Annotation annotation : fieldTemp.getAnnotations()) {
				if (annotation instanceof ColumnView) {
					count++;
				}
			}
		}

		for (int i = 0; i < list.size(); i++) {
			Object object = list.get(i);
			Object[] obj = new Object[count];
			int temp = 0;
			Field[] fields = ReflectionUtil.getAllFields(object.getClass());

			for (int j = 0; j < fields.length; j++) {
				if (count == temp)
					break;

				for (Annotation annotation : fields[j].getAnnotations()) {
					if (annotation instanceof ColumnView) {
						try {
							Method m = (Method) object.getClass().getMethod("get" + getMethodName(fields[j].getName()));

							ColumnViewData cv = new ColumnViewData((ColumnView) annotation);
							Class<? extends ColumnConvertor> convert = cv.convertor();
							ColumnConvertor convertIns = convert.newInstance();
							String text = convertIns.getText(object, m.invoke(object), i, temp, cv);
							if (text == null) {
								obj[temp] = m.invoke(object);
								temp++;
								continue;
							}
							obj[temp] = text;
							temp++;
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			result.add(obj);
		}
		return result;
	}

	// 第一个字母是小写的前提下，把一个字符串的第一个字母大写
	private String getMethodName(String fildeName) throws Exception {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}
}
