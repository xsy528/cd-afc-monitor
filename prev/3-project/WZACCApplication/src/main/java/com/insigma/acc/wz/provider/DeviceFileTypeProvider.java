/* 
 * 日期：2017年4月17日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.provider;

import java.util.List;
import java.util.Map;

import com.insigma.afc.dic.AFCDeviceFileType;
import com.insigma.commons.dic.annotation.DicItem;
import com.insigma.commons.lang.PairValue;
import com.insigma.commons.ui.provider.IComboDataSource;

/**
 * Ticket: 文件头标签provider
 * @author  
 *
 */

public class DeviceFileTypeProvider implements IComboDataSource<Integer> {

	public DeviceFileTypeProvider() {
	}

	public String[] getText() {
		List<PairValue<Object, String>> list = AFCDeviceFileType.getInstance().getByGroup("DataFile");
		Map<Object, DicItem> dicItemMap = AFCDeviceFileType.getInstance().getDicItemMap();
		String[] names = new String[list.size()];
		int i = 0;
		for (PairValue<Object, String> pairValue : list) {
			//			names[i] = dicItemMap.get(pairValue.getKey()).desc() + "(" + pairValue.getValue().toString() + ")";
			names[i] = dicItemMap.get(pairValue.getKey()).desc();
			i++;
		}

		return names;
	}

	public Integer[] getValue() {
		Object[] valueListByGroup = AFCDeviceFileType.getInstance().getValueListByGroup("DataFile");
		Integer[] values = new Integer[valueListByGroup.length];
		for (int i = 0; i < valueListByGroup.length; i++) {
			values[i] = Integer.valueOf(valueListByGroup[i].toString());
		}
		return values;
	}
}
