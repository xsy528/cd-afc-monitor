/**
 * iFrameWork 框架
 * 
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description   
 * @date          2009-4-2
 * @copyright     浙江浙大网新众合轨道交通工程有限公司 
 * @history       
 */
package com.insigma.commons.framework.function.table;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.IActionHandler;
import com.insigma.commons.framework.Action.ActionModel;
import com.insigma.commons.framework.application.Function;

public class TableViewFunction extends Function {

	public enum TableViewType {
		MS, SINGLE, PROPERTY
	}

	private String showStyle;
	public int fixedRows = 1;
	public int fixedColumns;

	public boolean useKTable = false;

	private TableViewType tableViewType = TableViewType.SINGLE;

	private String[] columns;

	private int[] columnWidth;

	private boolean multipage;

	protected int pageSize;

	private Action pageChangedAction;

	private List<Action> tableContext;

	private Action tableDoubleClickAction;

	private Action loadAction;

	private Action activeAction;

	private int selectMode = SWT.MULTI;

	private Class<?> valueClass;//列表中值的类型
	private String overrideSuffix;//覆盖的json定义

	// 控制当页导出按钮是否显示
	protected boolean bShowCurrentPageExportButton = true;

	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public Action getLoadAction() {
		return loadAction;
	}

	public void setLoadAction(Action loadAction) {
		this.loadAction = loadAction;
	}

	public int[] getColumnWidth() {
		return columnWidth;
	}

	public void setColumnWidth(int[] columnWidth) {
		this.columnWidth = columnWidth;
	}

	public Action getPageChangedAction() {
		return pageChangedAction;
	}

	public void setPageChangedAction(Action pageChangedAction) {
		Action pAction = new Action(pageChangedAction.getText(), pageChangedAction.getActionHandler());
		if (pageChangedAction.getFunction() != null) {
			pAction.setFunction(pageChangedAction.getFunction());
		}
		pAction.setActionModel(ActionModel.PAGE_CHANGE);
		this.pageChangedAction = pAction;
		pAction.setShowProgress(true);
		setRefreshAction(pAction);
	}

	public List<Action> getTableContext() {
		return tableContext;
	}

	public void setTableContext(List<Action> tableContext) {
		this.tableContext = tableContext;
	}

	public void addContextAction(Action action) {
		if (tableContext == null) {
			tableContext = new ArrayList<Action>();
		}
		tableContext.add(action);
	}

	public void addContextAction(String text, IActionHandler actionHandler) {
		Action action = new Action();
		action.setText(text);
		action.setActionHandler(actionHandler);
		addContextAction(action);
	}

	public Action getTableDoubleClickAction() {
		return tableDoubleClickAction;
	}

	public void setTableDoubleClickAction(Action tableDoubleClickAction) {
		this.tableDoubleClickAction = tableDoubleClickAction;
	}

	public boolean isMultipage() {
		return multipage;
	}

	public void setMultipage(boolean multipage) {
		this.multipage = multipage;
	}

	public TableViewType getTableViewType() {
		return tableViewType;
	}

	public void setTableViewType(TableViewType tableViewType) {
		this.tableViewType = tableViewType;
	}

	public Action getActiveAction() {
		return activeAction;
	}

	public void setActiveAction(Action activeAction) {
		this.activeAction = activeAction;
	}

	public int getSelectMode() {
		return selectMode;
	}

	public void setSelectMode(int selectMode) {
		this.selectMode = selectMode;
	}

	/**
	 * 获取覆盖的json定义
	 * @return
	 */
	public String getOverrideSuffix() {
		return overrideSuffix;
	}

	public void setOverrideSuffix(String overrideSuffix) {
		this.overrideSuffix = overrideSuffix;
	}

	public Class<?> getValueClass() {
		return valueClass;
	}

	public void setValueClass(Class<?> valueClass) {
		this.valueClass = valueClass;
	}

	public boolean isbShowCurrentPageExportButton() {
		return bShowCurrentPageExportButton;
	}

	public void setbShowCurrentPageExportButton(boolean bShowCurrentPageExportButton) {
		this.bShowCurrentPageExportButton = bShowCurrentPageExportButton;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getShowStyle() {
		return showStyle;
	}

	public void setShowStyle(String showStyle) {
		this.showStyle = showStyle;
	}
}
