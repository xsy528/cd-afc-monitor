package com.insigma.commons.framework.function.table;

import java.util.List;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.view.table.IRelateAction;
import com.insigma.commons.framework.view.tabletree.IEditListener;

/**
 * 创建时间 2010-11-10 下午03:11:01<br>
 * 描述：<br>
 * Ticket:
 * 
 * @author DingLuofeng
 */
public class PageNavigatorTableViewFunction extends Function {

	public enum ButtonLayout {
		TOP, BOTTOM, LEFT, RIGHT
	}

	private String[] columns;

	private int[] columnWidth;

	private boolean multipage;

	private Action pageChangedAction;

	private List<Action> tableContext;

	private Action tableDoubleClickAction;

	private Action loadAction;

	private IRelateAction relateAction;

	private ButtonLayout buttomLayout = ButtonLayout.TOP;

	private int[] editColumns;

	private IEditListener editListener;

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
		this.pageChangedAction = pageChangedAction;
	}

	public List<Action> getTableContext() {
		return tableContext;
	}

	public void setTableContext(List<Action> tableContext) {
		this.tableContext = tableContext;
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

	/**
	 * @param relateAction the relateAction to set
	 */
	public void setRelateAction(IRelateAction relateAction) {
		this.relateAction = relateAction;
	}

	/**
	 * @return the relateAction
	 */
	public IRelateAction getRelateAction() {
		return relateAction;
	}

	/**
	 * @param buttomLayout the buttomLayout to set
	 */
	public void setButtomLayout(ButtonLayout buttomLayout) {
		this.buttomLayout = buttomLayout;
	}

	/**
	 * @return the buttomLayout
	 */
	public ButtonLayout getButtomLayout() {
		return buttomLayout;
	}

	public void setEditColumns(int[] editColumns, IEditListener editListener) {
		this.editColumns = editColumns;
		this.editListener = editListener;
	}

	/**
	 * @return the editColumns
	 */
	public int[] getEditColumns() {
		return editColumns;
	}

	/**
	 * @return the editListener
	 */
	public IEditListener getEditListener() {
		return editListener;
	}

}
