/**
 * 
 */
package com.insigma.afc.provider;

import java.util.ArrayList;
import java.util.List;

import com.insigma.afc.dic.DeviceStatus;
import com.insigma.commons.lang.PairValue;
import com.insigma.commons.ui.provider.IComboDataSource;

/**
 * 状态
 *
 */
public class DeviceStatusComboProvider implements IComboDataSource<Short> {

	private static List<PairValue<Object, String>> byGroup = new ArrayList<PairValue<Object, String>>();

	static {
		byGroup = DeviceStatus.getInstance().getByGroup("1");
	}

	public DeviceStatusComboProvider() {
		super();
	}

	@Override
	public String[] getText() {
		String[] names = new String[byGroup.size()];
		for (int i = 0; i < byGroup.size(); i++) {
			PairValue<Object, String> pairValue = byGroup.get(i);
			names[i] = pairValue.getValue();
		}
		return names;
	}

	@Override
	public Short[] getValue() {
		Short[] values = new Short[byGroup.size()];
		for (int i = 0; i < byGroup.size(); i++) {
			PairValue<Object, String> pairValue = byGroup.get(i);
			values[i] = ((Number) pairValue.getKey()).shortValue();
		}
		return values;
	}

}
