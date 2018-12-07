/* 
 * 日期：2010-11-19
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.commons.framework.view.tabletree;

import java.lang.reflect.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TreeItem;

import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.anotation.ColumnView;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.ui.form.WidgetsFactory;
import com.insigma.commons.ui.widgets.IInputControl;
import com.insigma.commons.ui.widgets.Text;
import com.swtdesigner.SWTResourceManager;

/**
 * 创建时间 2010-11-19 上午11:02:12<br>
 * 描述：<br>
 * Ticket: 
 * @author  DingLuofeng
 *
 */
@SuppressWarnings("unchecked")
public class TreeEdit extends TreeView {

	private IEditListener editListener;

	public TreeEdit(Composite arg0, int arg1) {
		super(arg0, arg1);
	}

	@Override
	protected void checkSubclass() {
		// super.checkSubclass();
	}

	private boolean isContains(int x, int y, int columnNo) {
		Point point = new Point(x, y);
		TreeItem item = getItem(point);
		if (item != null) {
			Rectangle rectangle = item.getTextBounds(columnNo);
			if (rectangle.contains(point)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 创建时间 2010-11-19 下午01:13:14<br>
	 * 描述：创建显示控件
	 * 
	 * @param row
	 * @param columnNo
	 * @return
	 */
	private IInputControl createControl(View view, Field field, Object value) {
		if (view == null || field == null) {
			final Text text = new Text(TreeEdit.this, SWT.NONE);
			return text;
		}
		final IInputControl inputControl = WidgetsFactory.getInstance().create(TreeEdit.this, field, value);
		return inputControl;
	}

	private void edit(final int columnNo, final TreeEditor editor) {
		if (getSelectionCount() == 1) {
			// Determine the item to edit
			final TreeItem row = getSelection()[0];

			final Field field = (Field) row.getData(ITEM_FIELD_KEY_PREFIX + columnNo);
			final View view = field.getAnnotation(View.class);
			final ColumnView columnView = field.getAnnotation(ColumnView.class);
			Object value = row.getData(ITEM_DATA_KEY_PREFIX + columnNo);

			// 创建显示控件
			final IInputControl inputControl = createControl(view, field, value);
			final Control control = (Control) inputControl;
			// 设置控件的显示值
			if (value != null) {
				inputControl.setObjectValue(value);
			}
			// 失去焦点，设置修改后的显示内容
			control.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.character == SWT.CR && !control.isDisposed()) {
						Object newValue = inputControl.getObjectValue();
						updateListener(columnNo, row, columnView, field, newValue);
						control.dispose();
					}
					if (e.character == SWT.ESC && !control.isDisposed()) {
						control.dispose();
					}
				}
			});
			control.addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent e) {
					if (!control.isDisposed()) {
						Object newValue = inputControl.getObjectValue();
						updateListener(columnNo, row, columnView, field, newValue);
						control.dispose();
					}
				}

			});
			editor.setEditor(control, row, columnNo);
			control.setFocus();
		}
	}

	/**
	 * 创建时间 2010-11-19 下午03:17:47<br>
	 * 描述：更新内存对象域值
	 * 
	 * @param columnNo
	 * @param row
	 * @param view
	 * @param field
	 * @param newValue
	 */
	private void updateListener(int columnNo, TreeItem row, ColumnView view, Field field, Object newValue) {
		// row.setText(columnNo, inputControl.getObjectValue() + "");
		String text = formatValue(newValue, view);
		row.setText(columnNo, text);
		row.setData(ITEM_DATA_KEY_PREFIX + columnNo, newValue);
		Object itemData = row.getData();
		// 更新内存对象域值
		BeanUtil.setValue(itemData, field, newValue);
		if (editListener != null) {
			editListener.saveAction(itemData);
		}
	}

	/**
	 * 控制哪几列可以编辑
	 * 
	 * @param columnNos
	 *            编辑列号 第一列为0；
	 * @param editable
	 *            可编辑状态
	 */
	public void setEditable(int[] columnNos, boolean editable) {
		if (columnNos == null) {
			return;
		}
		for (int columnNo : columnNos) {
			setEditable(columnNo, editable);
		}
	}

	/**
	 * 控制第几列可以编辑
	 * 
	 * @param columnNo
	 *            编辑第几列 第一列为0；
	 * @param editable
	 *            可编辑状态
	 */
	public void setEditable(final int columnNo, final boolean editable) {
		if (editable) {
			setEditColumn(columnNo);
			final TreeEditor editor = new TreeEditor(this);

			editor.grabHorizontal = true;
			editor.grabVertical = true;

			addMouseListener(new MouseAdapter() {
				public void mouseDoubleClick(MouseEvent e) {
					if (isContains(e.x, e.y, columnNo)) {
						edit(columnNo, editor);
					}
				}
			});

			// addSelectionListener(new SelectionListener() {
			//
			// public void widgetDefaultSelected(SelectionEvent e) {
			// edit(columnNo, digitalOnly, editor);
			// }
			//
			// public void widgetSelected(SelectionEvent e) {
			// TreeEdit.this.setSelection(new TreeItem[] {editor.getItem()});
			// }
			// });

		}
	}

	public void setEditColumn(int col) {
		TreeItem[] items = getItems();
		for (TreeItem tableItem : items) {
			for (int i = 0; i < this.getColumnCount(); i++) {
				if (col == i) {
					tableItem.setBackground(i, SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
				}
			}
		}
	}

	/**
	 * @return the editListener
	 */
	public void addEditListener(IEditListener editListener) {
		this.editListener = editListener;
	}
}
