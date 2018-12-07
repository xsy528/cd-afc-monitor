package com.insigma.commons.ui.table.sortor;

class ItemData<RowData> {
	RowData rowData;
	Object cellData;
	String cellText;

	public ItemData(RowData rowData, Object cellData, String cellText) {
		this.rowData = rowData;
		this.cellData = cellData;
		this.cellText = cellText;
	}

}