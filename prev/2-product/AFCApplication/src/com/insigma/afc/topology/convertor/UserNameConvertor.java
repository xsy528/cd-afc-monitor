/**
 * 
 */
package com.insigma.afc.topology.convertor;

import com.insigma.afc.application.AFCApplication;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.ConvertorAdapter;

/**
 * @author shenchao
 *
 */
public class UserNameConvertor extends ConvertorAdapter {

	@Override
	public String getText(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {
		String userId = itemObject.toString();
		return AFCApplication.getUserNameByUserId(userId);
	}

}
