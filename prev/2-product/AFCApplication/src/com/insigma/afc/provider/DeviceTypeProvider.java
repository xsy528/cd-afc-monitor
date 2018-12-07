/**
 * 
 */
package com.insigma.afc.provider;

import java.util.List;

import com.insigma.afc.dic.AFCDeviceType;
import com.insigma.commons.lang.PairValue;
import com.insigma.commons.ui.provider.IComboDataSource;

/**
 * @author DLF
 *
 */
public class DeviceTypeProvider implements IComboDataSource<Short> {

	private static List<PairValue<Object, String>> byGroup;

	static {
		byGroup = AFCDeviceType.getInstance().getByGroup("SLE");
	}

	public DeviceTypeProvider() {
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
			values[i] = (Short) pairValue.getKey();
		}
		return values;
	}

}
