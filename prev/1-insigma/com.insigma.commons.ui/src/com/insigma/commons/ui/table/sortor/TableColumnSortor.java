package com.insigma.commons.ui.table.sortor;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.widgets.TableView;
import com.swtdesigner.SWTResourceManager;

/**
 * 
 * @author DLF
 *
 */
@SuppressWarnings("rawtypes")
public class TableColumnSortor implements ITableColumnSortor {

	/* (non-Javadoc)
	 * @see com.insigma.commons.ui.provider.ITableColumnSortor#addSorter(com.insigma.commons.ui.widgets.TableView, org.eclipse.swt.widgets.TableColumn)
	 */
	@Override
	public void addSorter(final TableView table, final TableColumn column) {
		column.setImage(
				SWTResourceManager.getImage(TableColumnSortor.class, "/com/insigma/commons/ui/toolbar/sort.png"));
		column.addListener(SWT.Selection, new Listener() {
			boolean isAscend = true; // 按照升序排序

			public void handleEvent(Event e) {
				int columnIndex = table.indexOf(column);
				List<ItemData> dataList = getItemDataList(table, columnIndex);
				sort(table, dataList, columnIndex, isAscend);
				table.setSortColumn(column);
				table.setSortDirection((isAscend ? SWT.UP : SWT.DOWN));
				isAscend = !isAscend;
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.insigma.commons.ui.provider.ITableColumnSortor#addSorter(com.insigma.commons.ui.widgets.TableView, int)
	 */
	@Override
	public void addSorter(final TableView table, final int columnIndex) {
		final TableColumn column = table.getColumn(columnIndex);
		column.setImage(
				SWTResourceManager.getImage(TableColumnSortor.class, "/com/insigma/commons/ui/toolbar/sort.png"));
		column.addListener(SWT.Selection, new Listener() {
			boolean isAscend = true; // 按照升序排序

			public void handleEvent(Event e) {
				//				int columnIndex = table.indexOf(column);
				List<ItemData> dataList = getItemDataList(table, columnIndex);
				sort(table, dataList, columnIndex, isAscend);
				table.setSortColumn(column);
				//				table.setSortDirection((isAscend ? SWT.UP : SWT.DOWN));
				column.setImage(SWTResourceManager.getImage(TableColumnSortor.class,
						"/com/insigma/commons/ui/toolbar/sort" + (isAscend ? "_up" : "") + ".png"));
				isAscend = !isAscend;
			}
		});
	}

	protected List<ItemData> getItemDataList(TableView table, int columnIndex) {
		List<ItemData> result = new ArrayList<ItemData>();
		TableItem[] items = table.getItems();
		for (TableItem tableItem : items) {
			Object rowdata = tableItem.getData();
			Object cellData = tableItem.getData(TableView.ITEM_DATA_KEY_PREFIX + columnIndex);
			String cellText = tableItem.getText(columnIndex);
			//20141204 shenchao 空的时候也加入排序
			if (rowdata != null || cellData != null) {
				ItemData data = new BeanItemData(rowdata, cellData, cellText);
				if (rowdata.getClass().isArray()) {
					data = new ArrayItemData((Object[]) rowdata, cellData, cellText);
				}
				result.add(data);
			}
		}
		return result;
	}

	protected void sort(TableView table, List<ItemData> dataList, int columnIndex, final boolean isAscend) {
		Collections.sort(dataList, new Comparator<ItemData>() {
			@Override
			public int compare(ItemData o1, ItemData o2) {
				if (isAscend) {
					return compare_(o1, o2);
				} else {
					return compare_(o2, o1);
				}
			}

			private int compare_(ItemData o1, ItemData o2) {
				Object cellData1 = o1.cellData;
				Object cellData2 = o2.cellData;
				//20141204 shenchao 当为空时返回false
				if (cellData1 == null || cellData2 == null) {
					return 0;
				}
				if (Number.class.isAssignableFrom(cellData1.getClass())
						&& Number.class.isAssignableFrom(cellData2.getClass())) {
					Float numbervalue1 = BeanUtil.convert(cellData1, Float.class);
					Float numbervalue2 = BeanUtil.convert(cellData2, Float.class);
					int compare = Float.compare(numbervalue1, numbervalue2);
					return compare;
				} else if (Date.class.isAssignableFrom(cellData1.getClass())) {
					return ((Date) cellData1).compareTo((Date) cellData2);
				} else {
					Collator comparator = Collator.getInstance(Locale.getDefault());
					return comparator.compare(cellData1.toString(), cellData2.toString());
				}
			}
		});
		List<Object[]> resultArray = new ArrayList<Object[]>();
		List<Object> resultObject = new ArrayList<Object>();
		for (ItemData data : dataList) {
			Object rowData = data.rowData;
			if (data instanceof ArrayItemData) {
				resultArray.add((Object[]) rowData);
			} else {
				resultObject.add(rowData);
			}
		}
		if (!resultArray.isEmpty()) {
			table.fillArrayList(resultArray);
		}
		if (!resultObject.isEmpty()) {
			table.fillObjectList(resultObject);
		}
	}
}
