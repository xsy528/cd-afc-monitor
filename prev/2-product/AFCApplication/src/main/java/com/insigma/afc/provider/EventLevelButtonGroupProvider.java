/**
 * 
 */
package com.insigma.afc.provider;

import java.util.ArrayList;
import java.util.List;

import com.insigma.afc.dic.AFCEventLevel;
import com.insigma.commons.lang.PairValue;
import com.insigma.commons.ui.provider.IButtonGroupDataSource;

/**
 * 状态
 *
 */
public class EventLevelButtonGroupProvider implements IButtonGroupDataSource {

	private static List<PairValue<Object, String>> byGroup = new ArrayList<PairValue<Object, String>>();

	static {
		byGroup = AFCEventLevel.getInstance().getByGroup("");
	}

	public EventLevelButtonGroupProvider() {
		super();

	}

	@Override
	public String[] getLabels() {
		String[] names = new String[byGroup.size()];
		for (int i = 0; i < byGroup.size(); i++) {
			PairValue<Object, String> pairValue = byGroup.get(i);
			names[i] = pairValue.getValue();
		}
		return names;
	}

	@Override
	public Short[] getValues() {
		Short[] values = new Short[byGroup.size()];
		for (int i = 0; i < byGroup.size(); i++) {
			PairValue<Object, String> pairValue = byGroup.get(i);
			values[i] = ((Number) pairValue.getKey()).shortValue();
		}
		return values;
	}

	@Override
	public int getDefault() {
		return 1;
	}

}
