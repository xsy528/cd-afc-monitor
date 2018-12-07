/* 
 * 日期：2011-12-22
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.form.builder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.editorframework.form.listener.TableMouseDoubleClick;
import com.insigma.commons.editorframework.form.listener.ToolBarAddLisener;
import com.insigma.commons.editorframework.form.listener.ToolBarCopyLisener;
import com.insigma.commons.editorframework.form.listener.ToolBarDelLisener;
import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.framework.function.table.TableGrid;
import com.insigma.commons.framework.function.table.TableViewFunction;
import com.insigma.commons.framework.view.table.TableViewComposite;
import com.insigma.commons.ui.anotation.ListType;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.ui.form.BeanField;
import com.insigma.commons.ui.form.BeanTableEditor;
import com.insigma.commons.ui.form.IEditorChangedListener;
import com.insigma.commons.ui.form.ObjectTableViewer;
import com.insigma.commons.ui.form.listener.ITableEditorLisener;

/**
 * @author qiuchangjin@zdwxgd.com<br/>
 *         #Ticket
 */
public class ListCompositeBuilder {
	public static final Log logger = LogFactory.getLog(ListCompositeBuilder.class);

	public int pageSize = Integer.decode(System.getProperty("parameter.editor.pageSize", "20"));

	Composite parent;//
	BeanField beanField;

	public ListCompositeBuilder(Composite parent, BeanField beanField) {
		//		super();
		this.parent = parent;
		this.beanField = beanField;
	}

	/**
	 * 构造查看的List
	 * 
	 * @return
	 */
	public Composite builderViewListCompsite() {
		final ListType listType = beanField.field.getAnnotation(ListType.class);
		final TableViewComposite tableView = new TableViewComposite(parent, SWT.NONE);

		final PageChangeHandler handler = new PageChangeHandler(beanField.fieldValue, beanField.field);

		TableViewFunction func = new TableViewFunction();
		func.setSelectMode(ObjectTableViewer.CELL_SELECTION);//定义双击事件
		func.setMultipage(handler.hasMultiPage());
		func.setPageChangedAction(new Action(handler));
		func.setLoadAction(new Action(handler));

		tableView.setFunction(func);
		tableView.setHeader(listType.type());

		return tableView;
	}

	/**
	 * 构造可编辑的List
	 * 
	 * @return
	 */
	public Composite builderEditableListCompsite(IEditorChangedListener changedListener) {
		List<ITableEditorLisener> listener = new ArrayList<ITableEditorLisener>();

		BeanTableEditor propertyGrid = null;

		View annotation = beanField.field.getAnnotation(View.class);
		if (beanField.fieldValue.getClass().isArray() || !annotation.editable()) {
			propertyGrid = new BeanTableEditor(parent, SWT.NONE);
		} else {
			propertyGrid = new BeanTableEditor(parent, SWT.NONE, new ToolBarAddLisener(), new ToolBarCopyLisener(),
					new ToolBarDelLisener(), new TableMouseDoubleClick());
		}

		propertyGrid.setLayoutData(new GridData(GridData.FILL_BOTH));
		propertyGrid.setChangedListener(changedListener);
		propertyGrid.setHeader(beanField.field);
		propertyGrid.setCompareObjectList(beanField.historyValue);
		try {
			propertyGrid.setObjectList(beanField.fieldValue);
		} catch (Exception e) {
			logger.error("可编辑列表内容设置异常", e);
		}
		return propertyGrid;

	}
}

class PageChangeHandler extends ActionHandlerAdapter {

	public int pageSize = Integer.decode(System.getProperty("parameter.editor.pageSize", "20"));

	private List list;

	private Field field;

	private int totalSize;

	private int totalPage;

	private boolean hasMultiPage;

	public PageChangeHandler(Object object, Field field) {
		this.field = field;

		if (object.getClass().isArray()) {
			list = Arrays.asList((Object[]) object);
		} else {
			list = (List) object;
		}

		if (null != list) {
			totalSize = list.size();
			calculateTotalPage();
		}

		hasMultiPage = totalSize > pageSize;
	}

	public boolean hasMultiPage() {
		return hasMultiPage;
	}

	public void calculateTotalPage() {
		totalPage = totalSize / pageSize;
		if (totalSize % pageSize > 0) {
			totalPage++;
		}
	}

	@Override
	public Response perform(Request request) {
		SearchRequest sq = (SearchRequest) request;
		pageSize = sq.getPageSize();
		if (pageSize > 0) {
			calculateTotalPage();
		}
		return pageChange(sq.getPage(), list);
	}

	private SearchResponse pageChange(int pageIndex, List list) {
		final SearchResponse resp = new SearchResponse();

		List<?> newList = null;
		if (totalPage == 0 && pageIndex <= 1) {
			newList = list;
		} else {
			if (pageIndex <= 0 || pageIndex > totalPage) {
				resp.addInformation("非法的页数。");
				return resp;
			}

			int fromIndex = (pageIndex - 1) * pageSize;
			int toIndex = (pageIndex == totalPage ? totalSize : (fromIndex + pageSize));

			newList = list.subList(fromIndex, toIndex);
		}

		TableGrid data = new TableGrid();
		data.setTotalSize(totalSize);
		data.setCurrentPage(pageIndex);
		data.setPageSize(pageSize);
		data.setContent(newList);
		data.setParent(field);//如果是基本类型，需要使用到
		resp.setData(data);
		resp.setValue(data);
		return resp;
	}
}
