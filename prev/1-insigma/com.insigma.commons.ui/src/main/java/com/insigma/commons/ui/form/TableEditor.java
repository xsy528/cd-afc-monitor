package com.insigma.commons.ui.form;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.ValidationUtil;
import com.insigma.commons.ui.anotation.ListType;
import com.insigma.commons.ui.anotation.Validation;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.ui.form.util.ShowUtil;
import com.insigma.commons.ui.widgets.IInputControl;
import com.insigma.commons.ui.widgets.Label;
import com.swtdesigner.SWTResourceManager;

public class TableEditor extends ObjectTableViewer {

	private Log logger = LogFactory.getLog(TableEditor.class);

	private final static Color color_black = SWTResourceManager.getColor(SWT.COLOR_BLACK);

	public final static Color color_red = SWTResourceManager.getColor(SWT.COLOR_RED);

	/** 选中的Item */
	private TableItem selectedItem;

	/** 选中的列号 */
	private int selectedColumn;

	private ControlEditor controlEditor;

	/** list的Label监听 */
	private Listener listLabelListener;

	private IEditorChangedListener changedListener;

	public TableEditor(Composite parent, int style) {
		super(parent, style);
		addItemSelectionListener();
	}

	/**
	 * 设置table 单元格监听
	 */
	protected void addItemSelectionListener() {
		final TableCursor cursor = new TableCursor(this, SWT.NONE);

		controlEditor = new ControlEditor(cursor);
		controlEditor.grabHorizontal = true;
		controlEditor.grabVertical = true;
		// 设置单元格选中监听
		cursor.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent event) {
				widgetSelected(event);
			}

			public void widgetSelected(SelectionEvent event) {
				if (isSimpleTypeList) {
					simpleClassSelected();
				} else {
					userDefineClassSelected();

				}
			}

			public void userDefineClassSelected() {

				if (null != controlEditor.getEditor() && !controlEditor.getEditor().isDisposed()) {
					controlEditor.getEditor().dispose();
				}

				// 取得选择的item、列号、所在行、Field、view信息
				selectedItem = cursor.getRow();
				selectedColumn = cursor.getColumn();
				final Field field = (Field) selectedItem.getData(ITEM_FIELD_KEY_PREFIX + selectedColumn);
				final View view = field.getAnnotation(View.class);
				try {
					// 创建选中单元格显示的控件。List的显示特殊处理，用[...]表示。
					Control control = null;
					if (field.getAnnotation(ListType.class) != null) {
						Label label = new Label(cursor, SWT.CENTER);
						label.setBackground(SWTResourceManager.getColor(new RGB(165, 165, 238)));
						if (null != listLabelListener) {
							label.addListener(SWT.MouseDoubleClick, listLabelListener);
						}
						label.setText(LIST_VALUE);
						control = label;
					} else {
						control = simpleClassSelected();
					}
					control.forceFocus();
					controlEditor.setEditor(control);

				} catch (Exception e1) {
					e1.printStackTrace();
					logger.error("选中：" + "[" + view.label() + "]异常", e1);
					MessageForm.Message("错误提示", "[" + view.label() + "]异常", SWT.ICON_ERROR);
				}

			}

			public Control simpleClassSelected() {

				if (null != controlEditor.getEditor() && !controlEditor.getEditor().isDisposed()) {
					controlEditor.getEditor().dispose();
				}

				// 取得选择的item、列号、所在行、Field、view信息
				selectedItem = cursor.getRow();
				selectedColumn = cursor.getColumn();
				final Field field = (Field) selectedItem.getData(ITEM_FIELD_KEY_PREFIX + selectedColumn);
				final View view = field.getAnnotation(View.class);
				Control control = null;
				try {
					// 取得信息
					final Object value = selectedItem.getData(ITEM_DATA_KEY_PREFIX + selectedColumn);
					//
					// 创建显示控件
					final IInputControl inputControl = WidgetsFactory.getInstance().create(cursor, field, value);
					control = (Control) inputControl;
					if (null != ValidationUtil.validateMessage(field, value)) {
						control.setBackground(color_red);
					}

					final boolean isInput = (control instanceof Text || control instanceof Spinner);

					control.addListener(SWT.FocusOut, new Listener() {

						public void handleEvent(Event arg0) {
							doInput(inputControl, field, isInput);
						}
					});
					if (isInput) {// 如果是可输入的控件,任何按键都要验证
						control.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {
								boolean ok = doInput(inputControl, field, isInput);
								if (ok && e.character == SWT.CR) {
									((Control) inputControl).dispose();
								}
							}

						});
					}

					control.forceFocus();
					controlEditor.setEditor(control);

				} catch (Exception e1) {
					e1.printStackTrace();
					logger.error("选中：" + "[" + view.label() + "]异常", e1);
					MessageForm.Message("错误提示", "[" + view.label() + "]异常", SWT.ICON_ERROR);
				}
				return control;
			}
		});

	}

	private boolean doInput(IInputControl inputControl, Field field, boolean isInput) {
		if (null == selectedItem || selectedItem.isDisposed()) {
			return true;
		}
		final View view = field.getAnnotation(View.class);
		Object inputValue = inputControl.getObjectValue();
		// 类型转化
		Object value = null;
		try {
			if (isSimpleTypeList) {
				value = BeanUtil.convert(inputValue, dataClass);
			} else {
				value = BeanUtil.convert(inputValue, field.getType());
			}
		} catch (Exception e) {
			logger.error("class:" + field.getType() + "value:" + inputValue + "类型转化失败", e);
			inputControl.setObjectValue(value);
			MessageForm.Message("错误提示", "[" + view.label() + "]输入错误", SWT.ICON_ERROR);
			Control mc = (Control) inputControl;
			if (isInput && !mc.isDisposed()) {
				mc.forceFocus();
				mc.setBackground(color_red);
			}
			return false;
		}

		Object data = selectedItem.getData();//取到该行的数据
		boolean isChange = false;
		String target = ShowUtil.getEditableItemText(field, inputValue, data, view);// 先显示到界面，使用非转换后的value
		selectedItem.setText(selectedColumn, target);
		if (isSimpleTypeList) {//如果是简单对象，直接修改list中的值
			int selectRowIndex = getSelectionIndex();
			Object date = dataListValue.get(selectRowIndex);
			if (null != value && !value.equals(date)) {
				isChange = true;
				dataListValue.set(selectRowIndex, value);
			}
		} else {//如果是复合对象，取到该行的数据，更新该单元格所在field的值
			if (null != value && !value.equals(BeanUtil.getValue(data, field))) {
				isChange = true;
				BeanUtil.setValue(data, field, value);// 给对象赋值，使用已经转换后的value
			}
		}
		selectedItem.setData(ITEM_DATA_KEY_PREFIX + String.valueOf(selectedColumn), value);//重新设置单元格的数据
		if (isChange && null != changedListener) {
			Event event = new Event();
			event.item = (Widget) inputControl;
			event.data = value;
			changedListener.editorChanged(event, true);
		}
		try {
			// 鼠标未移出单元格所在的行
			if (isChange) {
				return fieldValidate(selectedItem, selectedColumn, field, value);// 使用已经转换后的value
			}

		} catch (Exception e) {
			logger.error("验证异常", e);
		}
		return true;
	}

	private boolean fieldValidate(TableItem preSelectedItem, int index, Field field, Object obj) {
		if (null != field.getAnnotation(Validation.class)) {
			try {
				Object value = null;
				if (BeanUtil.isUserDefinedClass(field.getType())) {
					field.setAccessible(true);
					value = field.get(obj);
				} else {
					value = obj;
				}
				if (null != ValidationUtil.validateMessage(field, value)) {
					preSelectedItem.setForeground(index, color_red);
					preSelectedItem.setData("validation", false);
					return false;// 只要找到一个验证错误，就退出
				} else {
					preSelectedItem.setForeground(index, color_black);
				}

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}
		return true;
	}

	/**
	 * 设置列表label的
	 * 
	 * @param listLabelListener
	 */
	public void setListLabelDoubleClickListener(Listener listLabelListener) {
		this.listLabelListener = listLabelListener;
	}

	/**
	 * 添加行
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void addRow() throws InstantiationException, IllegalAccessException {
		Object addObject = null;
		if (!BeanUtil.isUserDefinedClass(dataClass)) {
			if (dataClass.isPrimitive()) {
				addObject = 0;
			} else if (Number.class.isAssignableFrom(dataClass)) {
				addObject = BeanUtil.convert(0, dataClass);
			} else {
				addObject = BeanUtil.newInstanceObject(dataClass);
			}
		} else {
			addObject = BeanUtil.newInstanceObject(dataClass);
			// 给添加的行附上初始有效值
			Field[] fields = addObject.getClass().getDeclaredFields();
			for (Field field : fields) {
				Validation validate = field.getAnnotation(Validation.class);
				if (null != validate && validate.rangeable() == true) {
					Object value = BeanUtil.convert(validate.minValue(), field.getType());
					BeanUtil.setValue(addObject, field, value);
				}
			}
		}
		TableItem rowItem = getRowItem(getItemCount());
		fillRowByObject(addObject, rowItem);
		if (dataListValue == null) {
			dataListValue = new ArrayList<Object>();
		}
		dataListValue.add(addObject);
	}

	/**
	 * 删除行
	 */
	public void removeRow() {
		if (null != selectedItem && !selectedItem.isDisposed()) {
			dataListValue.remove(selectedItem.getData());
			selectedItem.dispose();
		}
	}

	/**
	 * 取得选中的Item
	 * 
	 * @return
	 */
	public TableItem getSelectedItem() {
		return selectedItem;
	}

	/**
	 * 设置用户选中的Item
	 * 
	 * @param selectedItem
	 */
	public void setSelectedItem(TableItem selectedItem) {
		this.selectedItem = selectedItem;
	}

	/**
	 * 取得用户编辑界面信息
	 * 
	 * @return
	 */
	public ControlEditor getControlEditor() {
		return controlEditor;
	}

	public int getSelectedColumn() {
		return selectedColumn;
	}

	public IEditorChangedListener getChangedListener() {
		return changedListener;
	}

	public void setChangedListener(IEditorChangedListener changedListener) {
		this.changedListener = changedListener;
	}

}
