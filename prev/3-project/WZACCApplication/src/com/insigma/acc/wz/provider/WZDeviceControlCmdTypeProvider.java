/* 
 * 日期：2018年9月7日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.provider;

import com.insigma.acc.wz.define.WZDeviceCmdControlType;
import com.insigma.commons.ui.provider.IComboDataSource;

/**
 * Ticket: 设备控制命令
 * @author chenfuchun
 *
 */
public class WZDeviceControlCmdTypeProvider implements IComboDataSource<Integer> {

	/* (non-Javadoc)
	 * @see com.insigma.commons.ui.provider.IComboDataSource#getText()
	 */
	@Override
	public String[] getText() {
		String[] names = WZDeviceCmdControlType.getInstance().getNameList();
		return names;
	}

	/* (non-Javadoc)
	 * @see com.insigma.commons.ui.provider.IComboDataSource#getValue()
	 */
	@Override
	public Integer[] getValue() {
		Object[] objects = WZDeviceCmdControlType.getInstance().getValueList();
		Integer[] values = new Integer[objects.length];
		for (int i = 0; i < objects.length; i++) {
			values[i] = (Integer) objects[i];
		}
		return values;
	}

}
