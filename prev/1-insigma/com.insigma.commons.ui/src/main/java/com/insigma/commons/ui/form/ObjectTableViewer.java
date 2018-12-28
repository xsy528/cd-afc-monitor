package com.insigma.commons.ui.form;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
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
import com.insigma.commons.ui.anotation.View;
import com.insigma.commons.ui.anotation.data.AnnotationData;
import com.insigma.commons.ui.anotation.data.AnnotationDataParse;
import com.insigma.commons.ui.anotation.data.AnnotationOverrideManager;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.ColumnConvertor;
import com.insigma.commons.ui.convert.RowColorConvertor;
import com.insigma.commons.ui.dialog.StandardDialog;
import com.insigma.commons.ui.form.util.ShowUtil;
import com.insigma.commons.ui.table.sortor.ITableColumnSortor;
import com.insigma.commons.ui.table.sortor.TableColumnSortor;
import com.insigma.commons.ui.widgets.TableView;
import com.swtdesigner.SWTResourceManager;

@SuppressWarnings("rawtypes")
public class ObjectTableViewer extends TableView {

	private ITableColumnSortor columnSortor = new TableColumnSortor();

	private Log logger = LogFactory.getLog(ObjectTableViewer.class);

	public static final int CELL_SELECTION = 1;

	protected Class<?> dataClass;

	protected List<BeanField> fields;

	protected List<Object> dataListValue;//传入的object集合

	protected final String LIST_VALUE = "...";

	protected boolean isSimpleTypeList;

	private boolean isOverride;

	private String overrideSuffix;//覆盖的json定义

	public ObjectTableViewer(Composite parent, int style) {
		super(parent, style | SWT.BORDER | SWT.MULTI);
		setHeaderVisible(true);
		setLinesVisible(true);
		fields = new ArrayList<BeanField>();
	}

	/**
	 * 设置数据，如果设置的数据对象和设置的表头class不一致会重新生成表头
	 * @param dataList
	 */
	public void setObjectList(List dataList) {
		this.dataListValue = dataList;

		if (dataList == null || dataList.size() == 0) {
			return;
		}

		Object data = dataList.get(0);
		// 生成表头
		if (BeanUtil.isUserDefinedClass(data.getClass()) && !data.getClass().equals(dataClass)) {
			newHeader(data.getClass());
		}

		fillObjectList(dataList, rowStyleListener);
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

	protected void addItemSelectionListener() {
		addListener(SWT.MouseDoubleClick, new Listener() {

			public void handleEvent(Event event) {
				final int selectionIndex = getSelectionIndex();
				if (selectionIndex < 0) {
					return;
				}
				TableItem item = getItems()[selectionIndex];
				Point pt = new Point(event.x, event.y);
				for (int i = 0; i < getColumnCount(); i++) {
					Rectangle rect = item.getBounds(i);
					if (rect.contains(pt)) {
						int column = i;
						final BeanField field = (BeanField) item.getData(ITEM_FIELD_KEY_PREFIX + column);
						if (field.field.getAnnotation(ListType.class) != null) {
							final Object itemObject = item.getData(ITEM_DATA_KEY_PREFIX + column);
							DetailsDialog detailsDialog = new DetailsDialog(Display.getDefault().getActiveShell(),
									itemObject, field);
							detailsDialog.open();
						}
						break;
					}
				}
			}
		});
	}

	/**
	 * 清空内容
	 */
	private void clearColumn() {
		removeAll();
		for (TableColumn column : this.getColumns()) {
			column.dispose();
		}
		fields.clear();
	}

	/**
	 * 收集表头所在类的所有字段，会自动收集父类的字段
	 * @param cls
	 * @param formMap 
	 */
	@SuppressWarnings("unchecked")
	private void collectFields(Class<?> cls, Map formMap) {
		if (cls.getSuperclass() != Object.class) {
			collectFields(cls.getSuperclass(), formMap);
		}
		if (!BeanUtil.isUserDefinedClass(cls)) {
			return;
		}

		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			BeanField beanField = new BeanField(field);
			initBeanField(beanField, formMap);
		}
	}

	private void collectFields(BeanField beanField, Map formMap) {
		Field[] fields = beanField.field.getType().getDeclaredFields();

		for (Field field : fields) {
			BeanField subBeanField = new BeanField(field, null, beanField);
			initBeanField(subBeanField, formMap);
		}
	}

	private void initBeanField(BeanField beanField, Map formMap) {
		Map fieldOverrideInfo = null;
		if (isOverride) {
			if (formMap == null) { // FIXME 覆盖信息为空
				return;
			}
			//获取覆盖信息
			fieldOverrideInfo = (Map) formMap.get(beanField.field.getName());
			Map<String, AnnotationData> parse = AnnotationDataParse.parseJson(fieldOverrideInfo, beanField.field);
			beanField.setFieldAnnotations(parse);
		} else {
			Map<String, AnnotationData> parse = AnnotationDataParse.parseAnnotations(beanField.field);
			beanField.setFieldAnnotations(parse);
		}
		// 
		ColumnViewData cellView = beanField.getColumnView();//只需要收集一层
		if (null != cellView) {
			if (BeanUtil.isUserDefinedClass(beanField.field.getType())) {
				collectFields(beanField, fieldOverrideInfo);
			} else {
				this.fields.add(beanField);
			}
		}
	}

	/**
	 * 构建表头
	 * 
	 * @param cls
	 */
	private void newHeader(Class<?> cls) {
		Map formMap = AnnotationOverrideManager.lookupOverride(cls, overrideSuffix);
		this.setHeader(cls, formMap);
	}

	/**
	 * 这里真正开始生成表头
	 * @param groupName 父亲的列表，该参数会自动加到复合对象的前面，并且用（）包含起来
	 */
	private void doCreateHeader() {
		for (int i = 0; i < this.fields.size(); i++) {
			BeanField field = fields.get(i);

			ColumnViewData columnView = field.getColumnView();
			if (columnView == null) {
				continue;
			}
			initColumnByField(field, columnView);
		}
	}

	private void initColumnByField(BeanField field, ColumnViewData columnView) {
		//		ColumnView columnView = field.getAnnotation(ColumnView.class);
		int align = columnView.alignment();

		TableColumn colum = new TableColumn(this, align);
		int length = columnView.name().length();
		if (length > 6) {
			colum.setWidth((length - 6) * 10 + 140);
		} else {
			colum.setWidth(columnView.width());
		}
		if (columnView.sortAble()) {
			columnSortor.addSorter(this, colum);
		}
		String title = columnView.name();
		colum.setText(title);
		colum.setData(field);
	}

	@Override
	protected void fillRowByObject(Object rowData, TableItem rowItem) {
		if (isSimpleTypeList) {
			rowItem.setText(rowData == null ? DEFAULT_NULL_VALUE : rowData.toString());//文字
			rowItem.setData(ITEM_DATA_KEY_PREFIX + String.valueOf(0), rowData);//单元格的数据
			rowItem.setData(ITEM_FIELD_KEY_PREFIX + String.valueOf(0), fields.get(0));
			rowItem.setData(rowData);//改行的数据
			return;
		}

		for (int i = 0; i < this.fields.size(); i++) {
			BeanField field = fields.get(i);
			ListType listType = field.field.getAnnotation(ListType.class);
			try {
				Object value = getValue(rowData, field);
				if (listType == null) {
					if (field.field.getType() == byte[].class) { // 添加byte[]数组显示
						String strValue = "";
						if (value != null) {
							byte[] bArray = (byte[]) value;
							for (byte b : bArray) {
								strValue += String.format("%02X ", b);
							}
							strValue = strValue.trim();
						}
						rowItem.setText(i, strValue);

					} else {
						rowItem.setText(i, ShowUtil.getItemText(field, value, rowData));//文字
					}
				} else {
					rowItem.setText(i, ShowUtil.getItemText(field, value, rowData));//文字
					//				rowItem.setText(i, LIST_VALUE);//LIST_VALUE
				}
				rowItem.setData(ITEM_DATA_KEY_PREFIX + String.valueOf(i), value);//单元格的数据
				rowItem.setData(ITEM_FIELD_KEY_PREFIX + String.valueOf(i), field);
				rowItem.setData(rowData);//改行的数据
			} catch (Exception e) {

			}
		}
	}

	private Object getValue(Object value, BeanField beanField) {
		List<BeanField> parentBeanFieldList = new ArrayList<BeanField>();
		Object parentValue = value;

		BeanField parentBeanField = beanField.parent;
		while (null != parentBeanField) {
			parentBeanFieldList.add(parentBeanField);
			parentBeanField = parentBeanField.parent;
		}

		for (int i = parentBeanFieldList.size() - 1; i >= 0; i--) {
			parentValue = BeanUtil.getValue(parentValue, parentBeanFieldList.get(i).field.getName());
			if (null == parentValue) {
				return null;
			}
		}

		return BeanUtil.getValue(parentValue, beanField.field.getName());

	}

	@Override
	protected void fillRowByArray(Object[] rowData, TableItem rowItem) {
		super.fillRowByArray(rowData, rowItem);
	}

	public List<?> getObjectList() {
		return this.dataListValue;
	}

	public List<?> getDataListValue() {
		return dataListValue;
	}

	/**
	 * @param table
	 * @param columnIndex
	 * @see com.insigma.commons.ui.table.sortor.ITableColumnSortor#addSorter(com.insigma.commons.ui.widgets.TableView, int)
	 */
	public void addSorter(int columnIndex) {
		columnSortor.addSorter(this, columnIndex);
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

	public String getOverrideSuffix() {
		return overrideSuffix;
	}

	public void setOverrideSuffix(String overrideSuffix) {
		this.overrideSuffix = overrideSuffix;
	}

	/**
	 * 手动设置表头的class
	 * @param viewClass
	 */
	public void setHeader(Class<?> viewClass) {
		if (!viewClass.equals(dataClass)) {
			newHeader(viewClass);
		}
	}

	public void setHeader(Class<?> cls, Map formMap) {
		clearColumn();
		isOverride = formMap != null;
		collectFields(cls, formMap);
		doCreateHeader();

		if (!cls.equals(dataClass)) {
			dataClass = cls;
		}
	}

	/**
	 * 手动设置表头的class
	 * @param viewClass
	 */
	public void setHeader(BeanField field, Class<?> simpleClass) {
		ColumnViewData columnView = field.getColumnView();
		if (columnView == null) {
			return;
		}
		isSimpleTypeList = true;
		this.dataClass = simpleClass;
		this.fields.add(field);
		initColumnByField(field, columnView);
	}

	public void fillArrayList(List<Object[]> dataList) {
		fillArrayList(dataList, rowStyleListener);
	}

	/**
	 * 填充 table
	 * 
	 * @param dataList
	 *            <code>List<?></code>如果dataList为空 或Null清空表内容
	 * @return
	 */
	public void fillObjectList(List<?> dataList) {
		fillObjectList(dataList, rowStyleListener);
	}

	public ITableColumnSortor getColumnSortor() {
		return columnSortor;
	}

	public void setColumnSortor(ITableColumnSortor columnSortor) {
		this.columnSortor = columnSortor;
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
		public DetailsDialog(Shell parent, Object value, BeanField field) {
			super(parent, SWT.NONE);
			shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
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
			View view = field.field.getAnnotation(View.class);
			if (view != null && view.label() != null) {
				shell.setText(view.label());
			}
			createContents(value, field);

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
		protected void createContents(Object itemData, BeanField field) {
			ObjectTableViewer tableViewer = new ObjectTableViewer(shell, SWT.NONE);
			tableViewer.setLayoutData(new GridData(GridData.FILL_BOTH));
			ListType listType = field.field.getAnnotation(ListType.class);
			try {
				if (BeanUtil.isUserDefinedClass(listType.type())) {
					tableViewer.setHeader(listType.type());
				} else {
					tableViewer.setHeader(field, listType.type());
				}

				if (List.class.isAssignableFrom(itemData.getClass())) {
					tableViewer.setObjectList((List<Object>) itemData);
				} else if (itemData.getClass().isArray()) {
					tableViewer.setObjectList(Arrays.asList((Object[]) itemData));
				}

			} catch (Exception e) {
				logger.error("弹出框内容设置异常", e);
			}
		}
	}

	public IRowStyleListener rowStyleListener = new IRowStyleListener() {

		@Override
		public void setStyle(List<?> objectList, Object row, TableItem rowItem) {
			com.insigma.commons.ui.anotation.TableView tableView = row.getClass()
					.getAnnotation(com.insigma.commons.ui.anotation.TableView.class);
			if (tableView != null) {
				Class<? extends RowColorConvertor> colorConvertor = tableView.colorConvertor();
				if (colorConvertor != null) {
					try {
						RowColorConvertor convertor = colorConvertor.newInstance();
						Color backgroundColor = convertor.getBackgroundColor(objectList, row, null);
						rowItem.setBackground(backgroundColor);
						Color frontColor = convertor.getFrontColor(objectList, row, null);
						rowItem.setForeground(frontColor);
					} catch (Exception e) {
						logger.error("设置行样式异常", e);
					}
				}
			}
			if (isSimpleTypeList) {
				return;
			}
			int rowNum = ObjectTableViewer.this.indexOf(rowItem);

			for (int i = 0; i < ObjectTableViewer.this.fields.size(); i++) {
				BeanField field = fields.get(i);
				final Object itemObject = rowItem.getData(ObjectTableViewer.ITEM_DATA_KEY_PREFIX + i);
				ColumnViewData cv = field.getColumnView();
				Class<? extends ColumnConvertor> convert = cv.convertor();
				if (convert == null) {
					return;
				}
				try {
					ColumnConvertor convertIns = convert.newInstance();
					Color backgroundColor = convertIns.getBackgroundColor(row, itemObject, rowNum, i, cv);
					if (backgroundColor != null) {
						rowItem.setBackground(i, backgroundColor);
					}
					Color frontColor = convertIns.getFrontColor(row, itemObject, rowNum, i, cv);
					if (frontColor != null) {
						rowItem.setForeground(i, frontColor);
					}
					Image image = convertIns.getImage(row, itemObject, rowNum, i, cv);
					if (image != null) {
						rowItem.setImage(i, image);
					}
					Font font = convertIns.getFont(row, itemObject, rowNum, i, cv);
					if (font != null) {
						rowItem.setFont(i, font);
					}
					String text = convertIns.getText(row, itemObject, rowNum, i, cv);
					if (text != null) {
						rowItem.setText(i, text);
					}
				} catch (Exception e) {
					logger.debug("设置单元格样式异常", e);
				}
			}
		}
	};
}
