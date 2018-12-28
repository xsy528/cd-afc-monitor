package com.insigma.commons.ui.convert;

import java.util.List;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import com.insigma.commons.ui.anotation.data.ColumnViewData;

public class ConvertorAdapter implements ColumnConvertor, RowColorConvertor {

	@Override
	public Color getFrontColor(List<?> rows, Object rowObject, ColumnViewData view) {
		return null;
	}

	@Override
	public Color getBackgroundColor(List<?> rows, Object rowObject, ColumnViewData view) {
		return null;
	}

	@Override
	public Color getFrontColor(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {
		return null;
	}

	@Override
	public Color getBackgroundColor(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {
		return null;
	}

	@Override
	public Image getImage(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {
		return null;
	}

	@Override
	public String getText(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {
		return null;
	}

	@Override
	public Font getFont(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {
		return null;
	}

}
