/**
 * 
 */
package com.insigma.commons.ui.form;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.anotation.data.AnnotationData;
import com.insigma.commons.ui.anotation.data.AnnotationDataParse;
import com.insigma.commons.ui.anotation.data.AnnotationOverrideManager;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.anotation.data.ViewData;
import com.insigma.commons.ui.form.util.ShowUtil;
import com.insigma.commons.ui.widgets.ktable.FixedCellRenderer;
import com.insigma.commons.ui.widgets.ktable.KTableCellEditor;
import com.insigma.commons.ui.widgets.ktable.KTableCellRenderer;
import com.insigma.commons.ui.widgets.ktable.KTableDefaultModel;

/**
 * @author qcj(qiuchangjin@zdwxgd.com)
 *
 */
public class BeanViewerTableModel extends KTableDefaultModel {

	private KTableCellRenderer m_FixedRenderer = new FixedCellRenderer(
			FixedCellRenderer.STYLE_FLAT | FixedCellRenderer.INDICATION_FOCUS);

	Class<?> dataClass;
	/*private*/boolean isSimpleType;
	List<BeanTableCellValue> columns = new ArrayList<BeanViewerTableModel.BeanTableCellValue>();//已经展开的表头的定义
	List<BeanTableCellValue> columnsDefine = new ArrayList<BeanViewerTableModel.BeanTableCellValue>();//没有展开的表头的定义
	String[] headerString;
	int fixedRows;
	int fixedColumns;
	private List<List<BeanTableCellValue>> content = new ArrayList<List<BeanTableCellValue>>();

	private boolean isOverride;

	public BeanViewerTableModel() {

	}

	public BeanViewerTableModel(Class<?> headerClass) {
		this.dataClass = headerClass;
		newHeader(dataClass);
	}

	public BeanViewerTableModel(Field field, Class<?> headerClass) {
		isSimpleType = true;
		this.dataClass = headerClass;
		final BeanTableCellValue colDefine = new BeanTableCellValue(field);
		Map<String, AnnotationData> fieldAnnotations = AnnotationDataParse.parseAnnotations(field);
		colDefine.setFieldAnnotations(fieldAnnotations);
		columns.add(colDefine);
		content.add(columns);
		columnsDefine.add(colDefine);
	}

	//	public void addRow() {
	//		try {
	//			Object newObj = BeanUtil.newInstanceObject(dataClass);
	//			this.addRow(newObj);
	//			dataListValue.add(newObj);
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}

	/**
	 * 设置数据，如果设置的数据对象和设置的表头class不一致会重新生成表头
	 * @param dataList
	 */
	public void setObjectList(List dataList) {
		this.dataListValue = dataList;

		if (dataList == null || dataList.size() == 0) {
			return;
		}
		if (headerString == null) {
			Object data = dataList.get(0);
			// 生成表头
			if (BeanUtil.isUserDefinedClass(data.getClass()) && !data.getClass().equals(dataClass)) {
				newHeader(data.getClass());
			}
		}
		for (Object object : dataList) {
			addTableContent(object);
		}
	}

	/**
	 * 收集表头所在类的所有字段，会自动收集父类的字段
	 * @param cls
	 * @param lookupOverride 
	 */
	private void collectFields(Class<?> cls, BeanTableCellValue parent, Map lookupOverride) {
		if (cls.getSuperclass() != Object.class) {
			collectFields(cls.getSuperclass(), parent, lookupOverride);
		}
		if (!BeanUtil.isUserDefinedClass(cls)) {
			return;
		}
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			Map<String, AnnotationData> fieldAnnotations = null;
			Map fieldInfo = null;
			if (isOverride) {
				if (lookupOverride == null) {//FIXME 覆盖信息为空
					continue;
				}
				fieldInfo = (Map) lookupOverride.get(field.getName());
				fieldAnnotations = AnnotationDataParse.parseJson(fieldInfo, field);
			} else {
				fieldAnnotations = AnnotationDataParse.parseAnnotations(field);
			}
			if (fieldAnnotations.containsKey(ColumnViewData.class.getName())) {
				BeanTableCellValue cellValue = new BeanTableCellValue(field);
				cellValue.setFieldAnnotations(fieldAnnotations);
				if (BeanUtil.isUserDefinedClass(field.getType())) {
					columnsDefine.add(cellValue);
					collectFields(field.getType(), cellValue, fieldInfo);
				} else {
					if (parent == null) {
						columnsDefine.add(cellValue);
					} else {
						parent.add(cellValue);
					}
					columns.add(cellValue);
				}
			}
		}
	}

	/**
	 * 构建表头
	 * 
	 * @param cls
	 */
	public void newHeader(Class<?> cls) {
		Map lookupOverride = AnnotationOverrideManager.lookupOverride(cls);
		isOverride = lookupOverride != null;
		collectFields(cls, null, lookupOverride);
		content.add(columns);
		if (!cls.equals(dataClass)) {
			dataClass = cls;
		}
	}

	public void newHeader(String[] columnsDef) {
		headerString = columnsDef;
	}

	@Override
	public int getFixedHeaderRowCount() {
		return fixedRows;
	}

	@Override
	public int getFixedHeaderColumnCount() {
		return fixedColumns;
	}

	@Override
	public int getFixedSelectableRowCount() {
		return 0;
	}

	@Override
	public int getFixedSelectableColumnCount() {
		return 0;
	}

	@Override
	public boolean isColumnResizable(int col) {
		return true;
	}

	@Override
	public boolean isRowResizable(int row) {
		return true;
	}

	@Override
	public int getRowHeightMinimum() {
		return 20;
	}

	@Override
	public int getInitialColumnWidth(int column) {
		if (columns.size() > column) {
			BeanTableCellValue cellValue = columns.get(column);
			if (cellValue == null) {
				return 80;
			}
			final ColumnViewData columnView = cellValue.getColumnView();
			if (columnView != null) {
				return columnView.name().length() * 12;
			}
			final ViewData view = cellValue.getView();
			if (view != null) {
				return view.label().length() * 12;
			}
		}
		return 80;
	}

	@Override
	public int getInitialRowHeight(int row) {
		return 25;
	}

	public BeanTableCellValue doGetContentDefine(int col, int row) {
		if (content.size() > row) {
			BeanTableCellValue cellValue = content.get(row).get(col);
			return cellValue;
		}
		return null;
	}

	@Override
	public Object doGetContentAt(int col, int row) {
		if (row == 0) {
			if (headerString != null) {
				if (col < headerString.length) {
					return headerString[col];
				}
				return "--";
			}
		}
		if (content.size() > row) {
			if (row == 0) {
				final BeanTableCellValue beanTableCellValue = columns.get(col);
				ColumnViewData view = beanTableCellValue.getColumnView();
				if (view != null) {
					return view.name();
				} else {
					return beanTableCellValue.getName();
				}
			}
			BeanTableCellValue cellValue = content.get(row).get(col);
			if (cellValue == null) {
				return "--";
			}
			return ShowUtil.getItemText(cellValue.field, cellValue.fieldValue, cellValue.parentValue);
		}
		return null;
	}

	@Override
	public KTableCellEditor doGetCellEditor(int col, int row) {
		return null;
	}

	@Override
	public void doSetContentAt(int col, int row, Object value) {

	}

	@Override
	public KTableCellRenderer doGetCellRenderer(int col, int row) {
		if (isFixedCell(col, row))
			return m_FixedRenderer;

		return KTableCellRenderer.defaultRenderer;
	}

	@Override
	public int doGetRowCount() {
		return content.size();
	}

	@Override
	public int doGetColumnCount() {
		return columns.size();
	}

	public boolean isSimpleType() {
		return isSimpleType;
	}

	@Override
	protected void addRow(Object object) {
		addTableContent(object);
		dataListValue.add(object);
	}

	protected void addTableContent(Object object) {
		List<BeanTableCellValue> row = new ArrayList<BeanTableCellValue>();
		for (BeanTableCellValue col : columnsDefine) {
			if (isSimpleType) {
				BeanTableCellValue e = new BeanTableCellValue(col.field, object, object);
				row.add(e);
			} else {
				doGetCellValue(col, object, row);
			}
		}
		content.add(row);
	}

	private void doGetCellValue(BeanTableCellValue cellDefine, Object object, List<BeanTableCellValue> row) {
		Field field = cellDefine.field;
		Object value = BeanUtil.getValue(object, field);
		List<BeanTableCellValue> cs = cellDefine.childs;
		if (cs.isEmpty()) {
			BeanTableCellValue cell = new BeanTableCellValue(field, value, object);
			row.add(cell);
			return;
		}
		for (BeanTableCellValue subCellDefine : cs) {
			doGetCellValue(subCellDefine, value, row);
		}

	}

	public int getFixedRows() {
		return fixedRows;
	}

	public void setFixedRows(int fixedRows) {
		this.fixedRows = fixedRows;
	}

	public int getFixedColumns() {
		return fixedColumns;
	}

	public void setFixedColumns(int fixedColumns) {
		this.fixedColumns = fixedColumns;
	}

	/*public*/static final class BeanTableCellValue extends BeanField {
		/**
		 * field所在的对象
		 */
		Object parentValue;

		List<BeanTableCellValue> childs = new ArrayList<BeanViewerTableModel.BeanTableCellValue>();

		/**
		 * @param field
		 */
		public BeanTableCellValue(Field field) {
			super(field);
		}

		public BeanTableCellValue(Field field, Object value, Object parentValue) {
			super(field);
			this.fieldValue = value;
			this.parentValue = parentValue;
		}

		/**
		 * @param e
		 */
		public final void add(BeanTableCellValue e) {
			childs.add(e);
		}

	}

	/**
	 * 
	 */
	public void clear() {
		if (content.size() > 0) {
			final List<BeanTableCellValue> fc = content.get(0);
			content.clear();
			content.add(fc);
		} else {
			content.clear();
		}
	}

}
