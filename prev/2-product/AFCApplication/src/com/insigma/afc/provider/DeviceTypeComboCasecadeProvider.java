/* 
 * 日期：2012-11-16
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.provider;

import java.util.HashMap;
import java.util.Map;

import com.insigma.afc.dic.AFCDeviceSubType;
import com.insigma.commons.ui.casecade.CasecadeEvent;
import com.insigma.commons.ui.casecade.CasecadeListener;
import com.insigma.commons.ui.widgets.Combo;

public class DeviceTypeComboCasecadeProvider implements CasecadeListener {

	public final static Map<Short, String> subType = new HashMap();

	static {

		subType.put((short) 0, "双向窄闸机");//进出次序免检模式

		subType.put((short) 1, "双向宽闸机");//时间免检模式
	}

	public String[] names(Object parent) {
		Number s = (Number) parent;

		//yanyang for cd4
		if (AFCDeviceSubType.DEVICE_GATE_BOTH != null && AFCDeviceSubType.DEVICE_GATE_BOTH == s.shortValue()) {
			String[] nameList = new String[2];
			int index = 0;
			for (Short key : subType.keySet()) {
				nameList[index] = subType.get(key);
				index++;
			}
			return nameList;
		}
		//		if (AFCDeviceType.GATE != null && AFCDeviceType.GATE == s.shortValue()) {
		//			return AFCDeviceSubType.getInstance().getNameList();
		//		} else if (AFCDeviceType.GATE == null
		//				&& AFCDeviceSubType.getInstance().getDicItemMap().containsKey(s.shortValue())) {
		//			return new String[] { AFCDeviceSubType.getInstance().getNameByValue(s.shortValue()) };
		//		}
		return null;
	}

	public Short[] values(Object parent) {
		Number s = (Number) parent;
		//yanyang for cd4
		if (AFCDeviceSubType.DEVICE_GATE_BOTH != null && AFCDeviceSubType.DEVICE_GATE_BOTH == s.shortValue()) {
			Short[] valueList = new Short[2];
			int index = 0;
			for (Short key : subType.keySet()) {
				valueList[index] = key;
				index++;
			}
			return valueList;
		}
		//		if (AFCDeviceType.GATE != null && AFCDeviceType.GATE == s.shortValue()) {
		//			Object[] valueList = AFCDeviceSubType.getInstance().getValueList();
		//			Short[] values = new Short[valueList.length];
		//			int i = 0;
		//			for (Object integer : valueList) {
		//				values[i++] = ((Number) integer).shortValue();
		//			}
		//			return values;
		//		} else if (AFCDeviceType.GATE == null
		//				&& AFCDeviceSubType.getInstance().getDicItemMap().containsKey(s.shortValue())) {
		//			return new Short[] { s.shortValue() };
		//		}
		return null;
	}

	@Override
	public void valueChanged(CasecadeEvent casecadeEvent) {
		Object objectValue = casecadeEvent.changedControl.getObjectValue();
		if (objectValue != null && casecadeEvent.listenerControl instanceof Combo) {
			Combo combo = (Combo) casecadeEvent.listenerControl;
			String[] names = names(objectValue);
			Short[] values = values(objectValue);
			if (names == null) {
				combo.removeAll();
				combo.setEnabled(false);
			} else {
				combo.setEnabled(true);
				combo.setItems(names);
				combo.setValues(values == null ? names : values);
				Object value = combo.getValue();
				if (value != null) {
					combo.setObjectValue(value);
				} else {
					combo.setDefaultValue(0);
				}
			}

		}
	}
}