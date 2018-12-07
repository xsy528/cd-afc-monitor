/**
 * 
 */
package com.insigma.commons.editorframework.graphic.editor;

import java.util.Stack;

import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.graphic.editor.action.MoveAction;
import com.insigma.commons.editorframework.graphic.editor.action.RotateAction;

/**
 * @author DingLuofeng
 *
 */
public class MapActionExecutor {

	public static final String ACTION_STATE_KEY_PRE = "ACTION_STATE_KEY_PRE";
	public static final String ACTION_STATE_KEY_NEXT = "ACTION_STATE_KEY_NEXT";

	public static final String P_ACTION_STATE_KEY_PRE = "P_ACTION_STATE_KEY_PRE";
	public static final String P_ACTION_STATE_KEY_NEXT = "P_ACTION_STATE_KEY_NEXT";

	private Stack<ExecuteRecord> undoList = new Stack<ExecuteRecord>();//历史命令列表
	Stack<ExecuteRecord> redoList = new Stack<ExecuteRecord>();//历史命令列表

	public MapActionExecutor() {
		super();
	}

	public Object perform(MapItem mapItem, ActionContext context, Action action) {
		{
			IState previous = mapItem.getDataState();
			context.setData(ACTION_STATE_KEY_PRE, previous);
			if (mapItem.getParent() != null) {
				context.setData(P_ACTION_STATE_KEY_PRE, mapItem.getParent().getDataState());
			}
		}

		if (action instanceof RotateAction) {

		} else if (action instanceof MoveAction) {

		} else {
			action.getHandler().perform(context);
		}

		{
			IState next = mapItem.getDataState();
			context.setData(ACTION_STATE_KEY_NEXT, next);

			if (mapItem.getParent() != null) {
				context.setData(P_ACTION_STATE_KEY_NEXT, mapItem.getParent().getDataState());
			}
		}
		if (action.isUndoable()) {
			getUndoList().push(new ExecuteRecord(context, action, mapItem));//添加到undoList
		}
		return null;
	}

	/**
	 * 撤销操作
	 */
	public void undo() {
		if (!canUndo()) {
			return;
		}
		final ExecuteRecord record = getUndoList().pop();//
		record.context.getAction().setUndoable(true);
		record.action.getHandler().unPerform(record.context);
		{ // 
			IState previous = (IState) record.context.getData(ACTION_STATE_KEY_PRE);
			record.mapItem.setDataState(previous);
		}
		{ // 
			IState previous = (IState) record.context.getData(P_ACTION_STATE_KEY_PRE);
			if (record.mapItem.getParent() != null) {
				record.mapItem.getParent().setDataState(previous);
			}
		}

		redoList.push(record);
	}

	/**
	 * @return
	 */
	public boolean canUndo() {
		return !getUndoList().isEmpty();
	}

	/**
	 * 还原操作
	 */
	public void redo() {
		if (!canRedo()) {
			return;
		}
		final ExecuteRecord record = redoList.pop();
		record.context.getAction().setUndoable(false);
		record.action.getHandler().perform(record.context);
		{ // 
			IState next = (IState) record.context.getData(ACTION_STATE_KEY_NEXT);
			record.mapItem.setDataState(next);
		}
		{ // 
			IState next = (IState) record.context.getData(P_ACTION_STATE_KEY_NEXT);
			if (record.mapItem.getParent() != null) {
				record.mapItem.getParent().setDataState(next);
			}
		}
		if (record.action.isUndoable()) {
			getUndoList().push(record);//添加到undoList
		}
	}

	/**
	 * @return
	 */
	public boolean canRedo() {
		return !redoList.isEmpty();
	}

	public void reset() {
		redoList.clear();
		getUndoList().clear();
	}

	public Stack<ExecuteRecord> getUndoList() {
		return undoList;
	}

	public void setUndoList(Stack<ExecuteRecord> undoList) {
		this.undoList = undoList;
	}

}
