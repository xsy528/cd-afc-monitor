package com.insigma.commons.ui.form;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.FormUtil;
import com.insigma.commons.ui.anotation.ListType;
import com.insigma.commons.ui.anotation.Validation;
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.ui.convert.ValueConvertor;
import com.insigma.commons.ui.dialog.StandardDialog;
import com.insigma.commons.ui.form.util.ShowUtil;
import com.insigma.commons.ui.style.Column;
import com.insigma.commons.ui.style.IStyleFormat;
import com.insigma.commons.ui.style.TableStyle;
import com.insigma.commons.ui.widgets.Label;
import com.insigma.commons.ui.widgets.TableView;
import com.swtdesigner.SWTResourceManager;

public class TableViewer extends TableView {

	private Log logger = LogFactory.getLog(TableViewer.class);

	public static final int CELL_SELECTION = 1;

	public static final String ITEM_DATA_KEY_PREFIX = "DATA_";

	public static final String ITEM_FIELD_KEY_PREFIX = "FIELD_";

	protected Class<?> dataClass;

	protected List<Field> fields;

	protected List dataListValue;

	protected List<Field> keyFields;

	protected final String LIST_VALUE = "...";

	protected boolean isSimpleTypeList = false;

	protected TableStyle tableStyle;

	protected int itemIndex = 0;

	protected int fieldIndex = 0;

	public List<Field> getFields() {
		return fields;
	}

	public List<Field> getKeyFields() {
		return keyFields;
	}

	public void setKeyFields(List<Field> keyFields) {
		this.keyFields = keyFields;
	}

	public TableStyle getTableStyle() {
		return tableStyle;
	}

	public void setTableStyle(TableStyle tableStyle) {
		this.tableStyle = tableStyle;
	}

	public TableViewer(Composite parent, int style) {
		super(parent, style | SWT.BORDER | SWT.MULTI);
		setHeaderVisible(true);
		setLinesVisible(true);
		fields = new ArrayList<Field>();
		keyFields = new ArrayList<Field>();
	}

	/**
	 * 设置Table为选择方式
	 * 
	 * @param selectCellFlag
	 */
	public void setSelectionMode(int selectionMode) {
		if (CELL_SELECTION == selectionMode) {
			addItemSelectionListener();
		}
	}

	protected void addItemSelectionListener() {
		final TableCursor cursor = new TableCursor(this, SWT.NONE);

		final ControlEditor editor = new ControlEditor(cursor);
		editor.grabHorizontal = true;
		editor.grabVertical = true;

		cursor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (null != editor.getEditor() && !editor.getEditor().isDisposed()) {
					editor.getEditor().dispose();
				}
				if (null == fields || fields.size() < 1) {
					return;
				}
				TableItem item = cursor.getRow();
				int column = cursor.getColumn();
				final Field field = (Field) item.getData(ITEM_FIELD_KEY_PREFIX + column);

				final Object itemObject = item.getData(ITEM_DATA_KEY_PREFIX + column);
				Control control = null;
				if (field.getAnnotation(ListType.class) != null && !isSimpleTypeList) {
					Label label = new Label(cursor, SWT.CENTER);

					// 设置背景色
					label.setBackground(SWTResourceManager.getColor(new RGB(165, 165, 238)));

					// 设置鼠标双击事件
					label.addListener(SWT.MouseDoubleClick, new Listener() {
						public void handleEvent(Event arg0) {
							DetailsDialog detailsDialog = new DetailsDialog(Display.getDefault().getActiveShell(),
									itemObject, field);
							detailsDialog.open();
						}
					});
					label.setText(LIST_VALUE);
					control = label;
					editor.setEditor(control);
					control.setFocus();
				}
			}
		});
	}

	public List<Object> getSelectionIndicesObject() {
		int[] selectIndexs = getSelectionIndices();
		List<Object> result = new ArrayList<Object>();
		if (null != selectIndexs && selectIndexs.length > 0) {
			for (int index : selectIndexs) {
				Object object = getItem(index).getData();
				result.add(object);
			}
		}
		return result;
	}

	/**
	 * 清空内容
	 */
	public void clear() {
		removeAll();
		for (TableColumn column : this.getColumns()) {
			column.dispose();
		}
		fields.clear();
		keyFields.clear();
	}

	private void collectFields(Class<?> cls) {
		if (cls.getSuperclass() != Object.class) {
			collectFields(cls.getSuperclass());
		}
		if (!BeanUtil.isUserDefinedClass(cls)) {
			return;
		}
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			this.fields.add(field);
			View view = field.getAnnotation(View.class);
			if (BeanUtil.isUserDefinedClass(field.getType()) && null != view) {
				collectFields(field.getType());
			}
		}
	}

	private int collectFieldSize(Class<?> cls) {
		int size = 0;
		if (cls.getSuperclass() != Object.class) {
			size += collectFieldSize(cls.getSuperclass());
		}
		Field[] fields = cls.getDeclaredFields();
		size += fields.length;
		return size;
	}

	/**
	 * 构建表头
	 * 
	 * @param cls
	 */
	public void newHeader(Class<?> cls) {
		clear();
		collectFields(cls);
		setKeyFields(findKeyFieldList(cls));

		if (getTableStyle() != null) {
			newHeader(getTableStyle());
		} else {
			fieldIndex = 0;
			newHeader(null, getFields().size());
		}
		if (!cls.equals(dataClass)) {
			dataClass = cls;
		}
	}

	private List<Field> findKeyFieldList(Class<?> cls) {
		List<Field> keyFields = new ArrayList<Field>();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			Validation validation = field.getAnnotation(Validation.class);
			if (null != validation && validation.key() == true) {
				keyFields.add(field);
			}
		}
		return keyFields;

	}

	private void newHeader(TableStyle tableStyle) {
		for (Column c : tableStyle.getColumns()) {
			TableColumn colum = new TableColumn(this, SWT.NONE);
			int length = c.getText().length();
			if (length > 6) {
				colum.setWidth((length - 6) * 10 + 100);
			} else {
				colum.setWidth(100);
			}
			String title = c.getText();
			colum.setText(title);
			for (Field f : getFields()) {
				//                if (f.getName().equals(c.getKey())) {
				//                    colum.setData(f);
				//                    break;
				//                }
			}
			colum.setData("STYLE", c);

		}
	}

	private void newHeader(String groupName, int size) {

		for (int i = 0; i < size; i++) {

			Field field = getFields().get(fieldIndex);
			fieldIndex++;

			View view = field.getAnnotation(View.class);
			if (view == null) {
				continue;
			}

			if (BeanUtil.isUserDefinedClass(field.getType())) {
				newHeader("(" + view.label() + ")\n", field.getType().getDeclaredFields().length);
				i += field.getType().getDeclaredFields().length;
			} else {
				int align = SWT.NONE;
				if (null != field.getAnnotation(ListType.class)) {
					align = SWT.CENTER;
				}
				if (!BeanUtil.isUserDefinedClass(field.getType())) {
					TableColumn colum = new TableColumn(this, align);
					int length = view.label().length();
					if (length > 6) {
						colum.setWidth((length - 6) * 10 + 100);
					} else {
						colum.setWidth(100);
					}
					String title = view.label();
					if (null != groupName) {
						title += groupName;
					}
					colum.setText(title);
					colum.setData(field);

				}
			}
		}
	}

	protected void newRow(Object dataItem) {
		if (null == dataItem) {
			return;
		}
		TableItem item = new TableItem(this, SWT.NONE);
		item.setData("validation", true);
		item.setData("keyvalidation", true);
		item.setData(dataItem);
		// 设置颜色
		Color bgcolor = Display.getDefault().getSystemColor(SWT.COLOR_WHITE);
		if (dataItem instanceof IColorItem) {
			bgcolor = ((IColorItem) dataItem).getBackgound();
			if (bgcolor != null) {
				item.setBackground(bgcolor);
			}

			Color focolor = ((IColorItem) dataItem).getForeground();
			if (focolor != null) {
				item.setForeground(focolor);
			}
		}
		itemIndex = 0;
		fieldIndex = 0;
		if (this.getTableStyle() != null) {
			if (dataItem.getClass().isArray()) {
				setItemInfo(item, (Object[]) dataItem);
			} else {
				setItemInfo(item, dataItem);
			}
		} else {
			//setItemInfo(item, dataItem, collectFieldSize(dataItem.getClass()));
		}
		item.setData(dataItem);
	}

	private void setItemInfo(TableItem item, Object dataItem) {
		for (int i = 0; i < getColumnCount(); i++) {
			Field f = (Field) getColumns()[i].getData();
			f.setAccessible(true);
			try {
				item.setText(i, f.get(dataItem).toString());
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

	//    private void setItemInfo(TableItem item, Object[] dataItem) {
	//        for (int i = 0; i < getColumnCount(); i++) {
	//            Column c = (Column) getColumns()[i].getData("STYLE");
	//            if (c == null) {
	//                logger.error("样式信息为空");
	//            }
	//            try {
	//                if (c.getIndex() != null && c.getIndex() < dataItem.length) {
	//                    Object itemValue = dataItem[c.getIndex()];
	//
	//                    itemValue = formatValue(item, c, i, itemValue);
	//
	//                    item.setText(i, String.valueOf(itemValue));
	//                }
	//            } catch (Exception e) {
	//                logger.error("", e);
	//            }
	//        }
	//    }

	//    private Object formatValue(TableItem item, Column c, int index, Object itemValue) {
	//        try {
	//            String format = c.getFormat();
	//            if (format != null && !"".equals(format)) {
	//                IStyleFormat styleFormat = (IStyleFormat) Application.getObject(format);
	//                if (styleFormat == null) {
	//                    Class<?> forName = Class.forName(format);
	//                    styleFormat = (IStyleFormat) forName.newInstance();
	//                    Application.setObject(format, styleFormat);
	//                }
	//                String formatedValue = styleFormat.format(dataListValue, c, itemValue);
	//                Color itemColor = styleFormat.color(dataListValue, c, itemValue);
	//                itemValue = formatedValue;
	//                if (itemColor != null) {
	//                    item.setForeground(index, itemColor);
	//                }
	//            }
	//        } catch (Exception e) {
	//            logger.error("formatValue Error.", e);
	//        }
	//        return itemValue;
	//    }

	//    private void setItemInfo(TableItem item, Object dataItem, int size) {
	//        for (int i = 0; i < size; i++) {
	//            Field field = getFields().get(fieldIndex);
	//            fieldIndex++;
	//            View view = field.getAnnotation(View.class);
	//            if (view == null) {
	//                continue;
	//            }
	//            Object value = BeanUtil.getValue(dataItem, field.getName());
	//
	//            if (BeanUtil.isUserDefinedClass(field.getType())) {
	//                if (null == value) {
	//                    try {
	//                        value = field.getType().newInstance();
	//                        BeanUtil.setValue(dataItem, field, value);
	//                    } catch (InstantiationException e) {
	//                        logger.error("table显示，用户自定义对象实例化异常", e);
	//                    } catch (IllegalAccessException e) {
	//                        logger.error("table显示，方法不存在", e);
	//                    }
	//                }
	//                int fieldSize = field.getType().getDeclaredFields().length;
	//                if (field.getType().getSuperclass() != Object.class) {
	//                    fieldSize += field.getType().getSuperclass().getDeclaredFields().length;
	//                }
	//                setItemInfo(item, value, fieldSize);
	//            } else {
	//                if (field.getAnnotation(ListType.class) != null) {
	//                    item.setText(itemIndex, LIST_VALUE);
	//                } else {
	//                    ValueConvertor valueConvert = ValueConvertorFactory.getValueConvert(view
	//                            .convertor());
	//                    item.setText(itemIndex, ShowUtil.getItemText(field, value));
	//                    Image image = valueConvert.convertToImage(value, view);
	//                    if (image != null) {
	//                        item.setImage(itemIndex, image);
	//                    }
	//
	//                }
	//                item.setData(ITEM_DATA_KEY_PREFIX + String.valueOf(itemIndex), dataItem);
	//                item.setData(ITEM_FIELD_KEY_PREFIX + String.valueOf(itemIndex), field);
	//                itemIndex++;
	//            }
	//
	//        }
	//    }

	public List<?> getObjectList() {
		return this.dataListValue;
	}

	public List<?> getDataListValue() {
		return dataListValue;
	}

	@Override
	public void reset() {
		dataListValue = null;
		super.reset();
	}

	@Override
	public void remove(int arg0) {
		super.remove(arg0);
		if (dataListValue != null && dataListValue.size() > arg0) {
			dataListValue.remove(arg0);
		}
	}

	/**
	 * 设置列宽
	 * 
	 * @param widths
	 */
	public void setWidths(int[] widths) {
		TableColumn[] columns = getColumns();
		int size = columns.length;
		if (size >= widths.length) {
			size = widths.length;
		}
		for (int i = 0; i < size; i++) {
			columns[i].setWidth(widths[i]);
		}
	}

	public void setSimpleTypeList(List list, Field field) {
		isSimpleTypeList = true;
		getFields().add(0, field);

		this.dataListValue = list;

		View view = field.getAnnotation(View.class);
		ListType listType = field.getAnnotation(ListType.class);
		String title = view.label();

		this.dataClass = listType.type();
		// 设置标题
		TableColumn colum = new TableColumn(this, SWT.NONE);
		int length = title.length();
		if (length > 6) {
			colum.setWidth((length - 6) * 10 + 100);
		} else {
			colum.setWidth(100);
		}
		colum.setText(title);

		if (list == null || list.size() == 0) {
			return;
		}

		for (Object object : list) {
			addSimpleTypeRow(object, field);
		}
	}

	public void addSimpleTypeRow(Object object, Field field) {

		TableItem item = new TableItem(this, SWT.NONE);
		item.setData("validation", true);

		String value = ShowUtil.getItemText(field, object, null);

		if (null != object) {
			item.setText(0, value);
		} else {
			item.setText(0, "");
		}
		item.setData(object);
		item.setData(ITEM_DATA_KEY_PREFIX + String.valueOf(itemIndex), object);
		item.setData(ITEM_FIELD_KEY_PREFIX + String.valueOf(itemIndex), getFields().get(0));
	}

	public void setHeader(Class viewClass) {
		if (!viewClass.equals(dataClass)) {
			newHeader(viewClass);
		}
	}

	public void setObjectList(List dataList) {
		removeAll();
		this.dataListValue = dataList;

		if (dataList == null || dataList.size() == 0) {
			return;
		}

		Object data = dataList.get(0);
		// 生成表头
		if (!data.getClass().equals(dataClass)) {
			newHeader(data.getClass());
		}

		for (Object dataItem : dataList) {
			newRow(dataItem);
		}
	}

	/**
	 * 详细信息弹出框 Ticket:
	 * 
	 * @author 陈海锋
	 */
	private class DetailsDialog extends Dialog {

		protected Shell shell;

		/**
		 * Create the dialog
		 * 
		 * @param parent
		 * @param style
		 */
		public DetailsDialog(Shell parent, int style, Object itemData, Field field) {
			super(parent, style);
			shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
			shell.setText("详细信息");

			final GridLayout gridLayout = new GridLayout();
			gridLayout.verticalSpacing = 0;
			gridLayout.marginWidth = 0;
			gridLayout.marginHeight = 0;
			gridLayout.horizontalSpacing = 0;
			shell.setLayout(gridLayout);
			int width = 400;
			int height = 280;
			shell.setSize(700, 400);
			shell.setLocation(FormUtil.getCenterLocation(width, height));

			createContents(itemData, field);
		}

		/**
		 * Create the dialog
		 * 
		 * @param parent
		 */
		public DetailsDialog(Shell parent, Object itemData, Field field) {
			this(parent, SWT.NONE, itemData, field);
		}

		/**
		 * Open the dialog
		 * 
		 * @return the result
		 */
		public void open() {
			shell.setImage(SWTResourceManager.getImage(StandardDialog.class, "/com/insigma/afc/images/logo.png"));
			shell.open();
			shell.layout();
			Display display = getParent().getDisplay();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		}

		/**
		 * Create contents of the dialog
		 */
		protected void createContents(Object itemData, Field field) {
			TableViewer tableViewer = new TableViewer(shell, SWT.NONE);
			tableViewer.setLayoutData(new GridData(GridData.FILL_BOTH));
			Object value = BeanUtil.getValue(itemData, field.getName());
			ListType listType = field.getAnnotation(ListType.class);
			try {
				if (BeanUtil.isUserDefinedClass(listType.type())) {
					tableViewer.newHeader(listType.type());
					tableViewer.setObjectList((List) value);
				} else {
					if (List.class.isAssignableFrom(value.getClass())) {
						tableViewer.setSimpleTypeList((List) value, field);
					} else if (value.getClass().isArray()) {
						tableViewer.setSimpleTypeList(Arrays.asList((Object[]) value), field);
					}
				}
			} catch (Exception e) {
				logger.error("弹出框内容设置异常", e);
			}
		}
	}
}
