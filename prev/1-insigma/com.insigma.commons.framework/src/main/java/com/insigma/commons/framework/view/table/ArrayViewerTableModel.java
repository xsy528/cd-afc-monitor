/**
 * 
 */
package com.insigma.commons.framework.view.table;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Color;

import com.insigma.commons.application.Application;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.ui.style.Column;
import com.insigma.commons.ui.style.IStyleFormat;
import com.insigma.commons.ui.style.StyleFormatAdapter;
import com.insigma.commons.ui.widgets.ktable.DefaultCellRenderer;
import com.insigma.commons.ui.widgets.ktable.FixedCellRenderer;
import com.insigma.commons.ui.widgets.ktable.KTableCellEditor;
import com.insigma.commons.ui.widgets.ktable.KTableCellRenderer;
import com.insigma.commons.ui.widgets.ktable.KTableDefaultModel;

/**
 * @author qcj(qiuchangjin@zdwxgd.com)
 *
 */
public class ArrayViewerTableModel extends KTableDefaultModel {

	private KTableCellRenderer m_FixedRenderer = new FixedCellRenderer(
			FixedCellRenderer.STYLE_FLAT | FixedCellRenderer.INDICATION_FOCUS);

	List<Column> columnsDefine;

	public List<IStyleFormat> columnStyleFormats = new ArrayList<IStyleFormat>();
	int fixedRows;
	int fixedColumns;
	private List<Object[]> content = new ArrayList<Object[]>();

	public ArrayViewerTableModel() {

	}

	public void addRow() {

	}

	public void newHeader(List<Column> columnsDef) {
		columnsDefine = columnsDef;
		//		if (columnsDef == null) {
		//			return;
		//		}
		for (Column column : columnsDef) {
			String format = column.getFormat();
			IStyleFormat styleFormat = (IStyleFormat) Application.getObject(format);
			if (styleFormat == null) {
				try {
					Class<?> forName = Class.forName(format);
					styleFormat = (IStyleFormat) forName.newInstance();
					Application.setObject(format, styleFormat);
				} catch (Exception e) {
					e.printStackTrace();
					styleFormat = new StyleFormatAdapter();
				}
			}

			columnStyleFormats.add(styleFormat);

		}
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
		if (columnsDefine.size() > column) {
			final Column view = columnsDefine.get(column);
			if (view != null) {
				return view.getText().length() * 12;
			}
		}
		return 80;
	}

	@Override
	public int getInitialRowHeight(int row) {
		return 25;
	}

	@Override
	public Object doGetContentAt(int col, int row) {
		if (row == 0) {
			if (columnsDefine != null) {
				if (col < columnsDefine.size()) {
					return columnsDefine.get(col).getText();
				}
				return "--";
			}
		}
		if (content.size() >= row) {
			final Column column = columnsDefine.get(col);
			IStyleFormat styleFormat = this.columnStyleFormats.get(col);
			Object itemValue = content.get(row - 1)[col];
			return styleFormat.format(content, column, itemValue);
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
		if (isFixedCell(col, row)) {
			return m_FixedRenderer;
		}
		if (columnsDefine != null) {
			if (col < columnsDefine.size()) {
				final Column column = columnsDefine.get(col);
				IStyleFormat styleFormat = this.columnStyleFormats.get(col);
				Object itemValue = content.get(row - 1)[col];
				Color itemColor = styleFormat.color(dataListValue, column, itemValue);
				DefaultCellRenderer.m_TextRenderer.setForeground(itemColor);
			}

		}
		return KTableCellRenderer.defaultRenderer;
	}

	@Override
	public int doGetRowCount() {
		return content.size() + 1;
	}

	public Object getRowContentAt(int row) {
		if (content == null) {
			return null;
		}
		if (row - 1 > content.size() || row < 0) {
			throw new ApplicationException("无效行号" + row + "。");
		}
		return content.get(row - 1);
	}

	@Override
	public int doGetColumnCount() {
		return columnsDefine.size();
	}

	@Override
	protected void addRow(Object object) {
		Object[] newObject = new Object[getColumnCount()];
		for (int i = 0; i < getColumnCount(); i++) {
			Integer index = columnsDefine.get(i).getIndex();
			newObject[i] = ((Object[]) object)[index + 1];
		}
		content.add(newObject);
		super.addRow(newObject);
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

	/**
	 * 
	 */
	public void clear() {
		content.clear();
	}

}
