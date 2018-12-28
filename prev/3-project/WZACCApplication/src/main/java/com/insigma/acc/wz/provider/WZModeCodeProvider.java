/* 
 * 日期：2018年4月26日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.provider;

import java.util.List;

import com.insigma.acc.wz.define.WZModeCode;
import com.insigma.afc.dic.AFCModeCode;
import com.insigma.commons.lang.PairValue;
import com.insigma.commons.ui.provider.IComboDataSource;

/**
 * Ticket: 运营模式编码provider
 * @author chenfuchun
 *
 */
public class WZModeCodeProvider implements IComboDataSource<Integer> {

	String[] names;
	Integer[] values;

	public WZModeCodeProvider() {
		List<PairValue<Object, String>> list = AFCModeCode.getInstance().getByGroup("");
		String[] tempNames = new String[list.size()];
		Integer[] tempValues = new Integer[list.size()];
		int i = 0;
		for (PairValue<Object, String> pairValue : list) {
			//排除自定义类型
			if (!Integer.valueOf(pairValue.getKey().toString()).equals(WZModeCode.END_URGENCY_MODE_CODE)) {
				tempNames[i] = pairValue.getValue();
				tempValues[i] = Integer.valueOf(pairValue.getKey().toString());
				i++;
			}
		}

		names = new String[i];
		values = new Integer[i];
		for (int j = 0; j < i; j++) {
			names[j] = tempNames[j];
			values[j] = tempValues[j];
		}

	}

	/* (non-Javadoc)
	 * @see com.insigma.commons.ui.provider.IComboDataSource#getText()
	 */
	@Override
	public String[] getText() {
		return names;
	}

	/* (non-Javadoc)
	 * @see com.insigma.commons.ui.provider.IComboDataSource#getValue()
	 */
	@Override
	public Integer[] getValue() {
		return values;
	}

}
