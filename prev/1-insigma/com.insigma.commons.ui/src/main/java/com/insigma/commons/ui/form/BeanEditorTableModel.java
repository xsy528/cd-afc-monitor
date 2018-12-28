/**
 * 
 */
package com.insigma.commons.ui.form;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.widgets.Event;

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
import com.insigma.commons.ui.widgets.ktable.editors.InsigmaKTableCellEditor;

/**
 * @author qcj(qiuchangjin@zdwxgd.com)
 *
 */
public class BeanEditorTableModel extends KTableDefaultModel {

	Log logger = LogFactory.getLog(BeanEditorTableModel.class);
	//private int size = 999;

	private KTableCellRenderer m_FixedRenderer = new FixedCellRenderer(
			FixedCellRenderer.STYLE_FLAT | FixedCellRenderer.INDICATION_FOCUS);

	Class<?> dataClass;
	private boolean isSimpleType;
	List<BeanField> columns = new ArrayList<BeanField>();//已经展开的表头的定义
	List<BeanField> columnsDefine = new ArrayList<BeanField>();//没有展开的表头的定义
	private List<List<BeanField>> content = new ArrayList<List<BeanField>>();

	private List compareList;

	IEditorChangedListener changedListener;

	private boolean isOverride;

	public BeanEditorTableModel(Class<?> headerClass) {
		this.dataClass = headerClass;
		newHeader(dataClass);
	}

	public BeanEditorTableModel(Field field, Class<?> headerClass) {
		isSimpleType = true;
		this.dataClass = headerClass;
		final BeanField colDefine = new BeanField(field);
		Map<String, AnnotationData> fieldAnnotations = AnnotationDataParse.parseAnnotations(field);
		colDefine.setFieldAnnotations(fieldAnnotations);
		columns.add(colDefine);
		content.add(columns);
		columnsDefine.add(colDefine);
	}

	public Class getDataClass() {
		return this.dataClass;
	}

	public void setCampareList(List compareList) {
		this.compareList = compareList;
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

		// 判断比对的list类型是否与dataList一致
		if (null != compareList && compareList.size() > 0 && !compareList.get(0).getClass().equals(dataClass)) {
			compareList = null;
		}

		for (int i = 0; i < dataList.size(); i++) {
			Object object = dataList.get(i);

			Object compareObject = null;
			if (null != compareList && compareList.size() > i) {
				compareObject = compareList.get(i);
			}

			addTableContent(object, compareObject);
		}
	}

	protected void addRow(Object object) {
		addTableContent(object, null);
		dataListValue.add(object);

		fireChangeEvent(null);
	}

	protected void updateRow(int index, Object obj) throws Exception {
		Object historyObj = dataListValue.get(index - 1);
		if (historyObj.equals(obj)) {
			return;
		}

		if (isSimpleType) {
			dataListValue.set(index - 1, obj);
		} else {
			BeanUtil.copyObject(obj, historyObj);
		}

		updateTableContent(index, obj);

		fireChangeEvent(null);
	}

	private void updateTableContent(int index, Object obj) {
		List<BeanField> row = content.get(index);

		//TODO 临时处理 hexingyue
		if (columnsDefine.size() < row.size()) {
			isSimpleType = true;
		} else {
			isSimpleType = false;
		}

		for (int i = 0; i < row.size(); i++) {
			BeanField col = row.get(i);
			if (isSimpleType) {
				col.fieldValue = obj;
			} else {
				col.fieldValue = BeanUtil.getValue(obj, columnsDefine.get(i).field);
			}
		}
	}

	/**
	 * @param object
	 */
	private void addTableContent(Object object, Object compareObject) {
		List<BeanField> row = new ArrayList<BeanField>();
		for (BeanField col : columnsDefine) {
			if (isSimpleType) {
				BeanField e = new BeanField(col.field, object, null);
				e.setHistoryValue(compareObject);
				Map<String, AnnotationData> fieldAnnotations = AnnotationDataParse.parseAnnotations(col.field);
				e.setFieldAnnotations(fieldAnnotations);

				row.add(e);
			} else {
				BeanField parent = new BeanField(object);
				parent.setHistoryValue(compareObject);
				doGetCellValue(col, parent, row);
			}
		}
		content.add(row);
	}

	private void initBeanValue(BeanField parentBean, BeanField bean) {
		Object fieldValue = BeanUtil.getValue(parentBean.fieldValue, bean.field);

		if (null != parentBean.historyValue) {
			bean.historyValue = BeanUtil.getValue(parentBean.historyValue, bean.field);
		} else {
			bean.historyValue = null;
		}

		if (null == fieldValue && BeanUtil.isUserDefinedClass(bean.field.getType())) {
			try {
				fieldValue = bean.field.getType().newInstance();
				BeanUtil.setValue(parentBean.fieldValue, bean.field, fieldValue);
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		bean.fieldValue = fieldValue;
	}

	private void doGetCellValue(BeanField bean, BeanField parentBean, List<BeanField> row) {
		initBeanValue(parentBean, bean);

		List<BeanField> childs = bean.getChilds();

		if (null == childs || childs.isEmpty()) {

			//if (value != null && value instanceof String) {
			//	if (value.toString().length() > size) {
			//		value = value.toString().substring(0, size) + "...";
			//	}
			//}

			Field field = bean.field;
			Object fieldValue = bean.fieldValue;
			Object historyValue = bean.historyValue;

			BeanField cell = new BeanField(field, fieldValue, parentBean);
			cell.historyValue = historyValue;
			Map<String, AnnotationData> fieldAnnotations = AnnotationDataParse.parseAnnotations(field);
			cell.setFieldAnnotations(fieldAnnotations);

			row.add(cell);

		} else {

			// 子类遍历
			for (BeanField subBean : childs) {
				//if (value != null && value instanceof String) {
				//	if (value.toString().length() > 200) {
				//		value = value.toString().substring(0, 200) + "...";
				//	}
				//}
				doGetCellValue(subBean, bean, row);
			}
		}

	}

	public Object getRow(int index) {
		if (index > 0 && index < content.size()) {
			return dataListValue.get(index - 1);
		}
		return null;
	}

	public Object getCompareRow(int index) {
		if (null != compareList && index > 0 && index < compareList.size()) {
			return compareList.get(index - 1);
		}
		return null;
	}

	public Object copyRow(int index) {
		if (index > 0 && index < content.size()) {
			Object object = dataListValue.get(index - 1);
			try {
				Object result = BeanUtil.cloneObjectForNoId(object);
				return result;
			} catch (Exception e) {
				logger.error("选择对象复制失败", e);
				return null;
			}
		}

		return null;
	}

	public List<BeanField> remove(int index) {
		dataListValue.remove(index - 1);
		fireChangeEvent(null);

		return content.remove(index);
	}

	/**
	 * 收集表头所在类的所有字段，会自动收集父类的字段
	 * @param cls
	 * @param lookupOverride 
	 */
	private void collectFields(Class<?> cls, BeanField parent, Map lookupOverride) {
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
				BeanField cellValue = new BeanField(field);
				cellValue.setFieldAnnotations(fieldAnnotations);
				if (BeanUtil.isUserDefinedClass(field.getType())) {
					columnsDefine.add(cellValue);
					collectFields(field.getType(), cellValue, fieldInfo);
				} else {
					if (parent == null) {
						columnsDefine.add(cellValue);
					} else {
						parent.addChild(cellValue);
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
	private void newHeader(Class<?> cls) {
		Map lookupOverride = AnnotationOverrideManager.lookupOverride(cls);
		isOverride = lookupOverride != null;
		collectFields(cls, null, lookupOverride);
		content.add(columns);
		if (!cls.equals(dataClass)) {
			dataClass = cls;
		}
	}

	@Override
	public int getFixedHeaderRowCount() {
		return 1;
	}

	@Override
	public int getFixedHeaderColumnCount() {
		return 0;
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
			BeanField cellValue = columns.get(column);
			if (cellValue == null || cellValue.getView() == null) {
				return 80;
			}
			return cellValue.getView().label().length() * 12;
		}
		return 80;
	}

	@Override
	public int getInitialRowHeight(int row) {
		return 25;
	}

	public BeanField doGetContentDefine(int col, int row) {
		if (content.size() > row) {
			BeanField cellValue = content.get(row).get(col);
			return cellValue;
		}
		return null;
	}

	@Override
	public Object doGetContentAt(int col, int row) {
		if (content.size() > row) {
			BeanField cellValue = content.get(row).get(col);
			if (cellValue == null) {
				return "--";
			}
			ViewData view = columns.get(col).getView();
			if (row == 0) {
				return view.label();
			}
			String result = ShowUtil.getEditableItemText(cellValue.field, cellValue.fieldValue,
					cellValue.getParentValue(), view);
			if (null != cellValue.historyValue && !cellValue.historyValue.equals(cellValue.fieldValue)) {
				result += ShowUtil.getCompareItemText(cellValue.field, cellValue.historyValue,
						cellValue.getParentValue(), view);
			}
			return result;
		}
		return null;
	}

	@Override
	public KTableCellEditor doGetCellEditor(int col, int row) {
		if (content.size() > row && row > -1) {
			BeanField cellValue = content.get(row).get(col);
			if (cellValue == null) {
				return null;
			}

			return new InsigmaKTableCellEditor(columns.get(col).getView(), cellValue);
		}
		return null;
	}

	@Override
	public void doSetContentAt(int col, int row, Object value) {
		if (content.size() > row) {
			BeanField cellValue = content.get(row).get(col);
			if (cellValue == null) {
				return;
			}
			if (row == 0) {
				return;
			}
			Object newValue = null;
			if (isSimpleType) {
				newValue = BeanUtil.convert(value, dataClass);
				dataListValue.add(row - 1, newValue);
				dataListValue.remove(row);
			} else {
				Field field = cellValue.field;
				newValue = BeanUtil.convert(value, field.getType());
				BeanUtil.setValue(cellValue.getParentValue(), field, newValue);
			}
			Object oldValue = cellValue.fieldValue;
			cellValue.fieldValue = newValue;
			if (oldValue == null && newValue != null) {
				fireChangeEvent(cellValue);
			} else if (oldValue != null && !oldValue.equals(newValue)) {
				fireChangeEvent(cellValue);
			}
		}

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

	private void fireChangeEvent(BeanField cellValue) {
		if (this.changedListener == null) {
			return;
		}
		Event event = new Event();
		event.data = cellValue;
		changedListener.editorChanged(event, true);
	}

	public void setChangedListener(IEditorChangedListener changedListener) {
		this.changedListener = changedListener;
	}

}
