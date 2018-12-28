/* 
 * 日期：2010-7-6
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.framework.view.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.IResponseCode;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.framework.function.table.TableGrid;
import com.insigma.commons.framework.function.table.TableViewFunction;
import com.insigma.commons.framework.view.FunctionComposite;
import com.insigma.commons.ui.control.IPageChangedListener;
import com.insigma.commons.ui.control.PageNavigator;
import com.insigma.commons.ui.form.BeanViewerTableModel;
import com.insigma.commons.ui.style.Column;
import com.insigma.commons.ui.style.StyleManager;
import com.insigma.commons.ui.style.TableStyle;
import com.insigma.commons.ui.widgets.ktable.KTable;
import com.insigma.commons.ui.widgets.ktable.KTableCellDoubleClickListener;
import com.insigma.commons.ui.widgets.ktable.KTableCellSelectionListener;
import com.insigma.commons.ui.widgets.ktable.SWTX;
import com.insigma.commons.util.lang.StringUtil;

public class ObjectKTableViewer extends FunctionComposite {

	private Log logger = LogFactory.getLog(ObjectKTableViewer.class);

	private KTable ktable;

	protected PageNavigator pageNavigator;

	protected TableViewFunction function;

	private BeanViewerTableModel beanModel;
	private ArrayViewerTableModel arrayModel;

	private boolean isArray = false;

	public ObjectKTableViewer(final Composite parent, int style) {
		super(parent, style);
		setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

		//20140409 shenchao 调整Ktable显示样式
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = -12;
		gridLayout.marginLeft = 0;
		gridLayout.marginTop = 12;
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = 0;
		setLayout(gridLayout);
		setLayoutData(new GridData(GridData.FILL_BOTH));

		// table
		ktable = new KTable(this, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWTX.FILL_WITH_LASTCOL
				| SWTX.MARK_FOCUS_HEADERS | SWT.FULL_SELECTION);
		//		ktable = new KTable(this, SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWTX.FILL_WITH_LASTCOL
		//				| SWTX.EDIT_ON_KEY);
		ktable.setLayout(new GridLayout());
		ktable.setLayoutData(new GridData(GridData.FILL_BOTH));

	}

	public List<Object> getSelectionDatas() {
		int[] ps = ktable.getRowSelection();
		List<Object> ss = new ArrayList<Object>();
		if (ps != null && ps.length > 0) {
			for (int p : ps) {
				if (p == 0) {
					continue;
				}
				Object rowData = ktable.getModel().getRowContentAt(p);
				if (rowData != null) {
					ss.add(rowData);
				}
			}
		}

		return ss;
	}

	public int[] getSelectIndexs() {
		final int[] rowSelection = ktable.getRowSelection();
		return rowSelection;
	}

	/**
	 * 设置table的内容
	 * 
	 * @param dataList
	 */
	public void setObjectList(Object dataList) {
		if (dataList == null) {
			return;
		}
		List<Object> list = null;
		if (dataList.getClass().isArray()) {
			list = Arrays.asList((Object[]) dataList);
		} else if (dataList instanceof List) {
			list = (List<Object>) dataList;
		}
		beanModel.setObjectList(list);
		ktable.setModel(beanModel);
	}

	public TableViewFunction getFunction() {
		return function;
	}

	public void setFunction(final TableViewFunction function, int fixedRows, int fixedCols) {
		this.function = function;
		if (function.getValueClass() != null) {
			beanModel = new BeanViewerTableModel(function.getValueClass());
		} else {
			beanModel = new BeanViewerTableModel();
			if (function.getColumns() != null) {
				beanModel.newHeader(function.getColumns());
			}
		}
		final int[] columnWidths = function.getColumnWidth();
		if (columnWidths != null) {
			for (int i = 0; i < columnWidths.length; i++) {
				beanModel.setColumnWidth(i, columnWidths[i]);
			}
		}
		beanModel.setFixedRows(fixedRows);
		beanModel.setFixedColumns(fixedCols);
		ktable.setModel(beanModel);

		arrayModel = new ArrayViewerTableModel();
		List<Column> columns = null;
		String showStyle = function.getShowStyle();
		if (StringUtil.hasLength(showStyle)) {
			TableStyle tableStyle = StyleManager.getTableStyle(showStyle);
			if (tableStyle != null) {
				columns = tableStyle.getColumns();
			}
		} else {
			String[] cols = function.getColumns();
			if (cols != null) {
				columns = new ArrayList<Column>();
				for (int i = 0; i < cols.length; i++) {
					Column col = new Column();
					col.setIndex(i);
					col.setText(cols[i]);
					columns.add(col);
				}
			}
		}
		arrayModel.newHeader(columns);
		if (columnWidths != null) {
			for (int i = 0; i < columnWidths.length; i++) {
				arrayModel.setColumnWidth(i, columnWidths[i]);
			}
		}

		arrayModel.setFixedRows(fixedRows);
		arrayModel.setFixedColumns(fixedCols);
		arrayModel.newHeader(columns);

		final Action tableDoubleClickAction = function.getTableDoubleClickAction();
		if (tableDoubleClickAction != null) {
			ktable.addCellDoubleClickListener(new KTableCellDoubleClickListener() {

				@Override
				public void fixedCellDoubleClicked(int col, int row, int statemask) {
					performAction(tableDoubleClickAction);
				}

				@Override
				public void cellDoubleClicked(int col, int row, int statemask) {
					performAction(tableDoubleClickAction);
				}
			});
		}

		if (function.isMultipage()) {
			pageNavigator = new PageNavigator(this, SWT.NONE);
			pageNavigator.setPageSize(20);
			pageNavigator.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
			pageNavigator.setCurrentPage(1);
			pageNavigator.setData(function);
			pageNavigator.setPageChangedListener(new IPageChangedListener() {
				public void pageChanged(int oldPage, int newPage) {
					performAction(function.getPageChangedAction());
				}
			});
		}

	}

	@Override
	public Request getRequest() {
		SearchRequest request = new SearchRequest();
		request.setContext(context);
		if (pageNavigator != null) {
			request.setPage(pageNavigator.getCurrentPage());
			request.setPageSize(pageNavigator.getPageSize());
		}
		if (ktable != null) {
			request.setSelection(getSelectionDatas());
			request.setSelectionData(getSelectionDatas());
			request.setSelectionIndexs(getSelectIndexs());
		}
		return request;
	}

	@Override
	public void setResponse(Response response) {
		if (this.isDisposed()) {
			return;
		}

		if (response.getCode() == IResponseCode.RESET) {
			if (ktable != null && beanModel != null) {
				beanModel.clear();
			}
			if (arrayModel != null) {
				arrayModel.clear();
			}
			ktable.redraw();
			return;
		}

		if (getFrameworkComposite() != null) {
			getFrameworkComposite().validate(getRequest());
		}
		if (response instanceof SearchResponse) {
			SearchResponse sresp = (SearchResponse) response;
			if (sresp.getData() != null) {
				TableGrid datagrid = (TableGrid) sresp.getData();
				if (beanModel != null) {
					//					beanModel.clear();
				}
				if (arrayModel != null) {
					//					arrayModel.clear();
				}
				if (pageNavigator != null) {
					pageNavigator.setTotalRecord(datagrid.getTotalSize());
					pageNavigator.setCurrentPage(datagrid.getCurrentPage());
					pageNavigator.setPageSize(datagrid.getPageSize());
					pageNavigator.refresh();
				}
				if (null != datagrid.getContent() && datagrid.getContent().size() > 0) {
					Class<? extends Object> dataClazz = datagrid.getContent().get(0).getClass();
					if (dataClazz.isArray()) {
						isArray = true;
						ktable.setModel(arrayModel);
						arrayModel.clear();
						arrayModel.setObjectList(datagrid.getContent());
					} else {
						isArray = false;
						beanModel.setObjectList(datagrid.getContent());
					}

				}
			}
		}
		ktable.redraw();
	}

	@Override
	public void reset() {
		ktable.clearSelection();
		if (beanModel != null) {
			beanModel.clear();
		}
		if (arrayModel != null) {
			arrayModel.clear();
		}
		super.reset();
	}

	public void addCellSelectionListener(KTableCellSelectionListener listener) {
		ktable.addCellSelectionListener(listener);
	}

}
