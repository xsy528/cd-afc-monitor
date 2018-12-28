/* 
 * 日期：2010-10-9
 * 描述（预留） 
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.ui.widgets.provider;

import com.insigma.afc.dic.AFCBLKType;
import com.insigma.commons.ui.provider.IComboDataSource;

/**
 * Ticket: 设备类型（数值+中文名）提供者
 * 
 * @author shenchao
 * @description
 */
public class AFCBLKLisTypeProvider implements IComboDataSource {

	public String[] getText() {
		String[] nameList = AFCBLKType.getInstance().getNameList();
		return nameList;
	}

	public Object[] getValue() {
		Object[] valueList = AFCBLKType.getInstance().getValueList();
		return valueList;
	}

}
