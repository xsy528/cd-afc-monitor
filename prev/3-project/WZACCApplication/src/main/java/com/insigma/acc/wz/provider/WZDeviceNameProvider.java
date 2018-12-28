/* 
 * 日期：2011-8-22
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.acc.wz.util.WZAppCommonUtil;
import com.insigma.afc.constant.ApplicationKey;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.commons.application.Application;
import com.insigma.commons.ui.provider.IComboDataSource;

/**
 * Ticket:增加设备名称
 * 
 * @author chenfuchun
 */
public class WZDeviceNameProvider implements IComboDataSource<Object> {

	private Log logger = LogFactory.getLog(WZDeviceNameProvider.class);

	private String[] deviceNames;

	private Long[] deviceIds;

	public WZDeviceNameProvider() {
		super();
		try {
			Map<Long, MetroDevice> deviceMap = (Map<Long, MetroDevice>) Application
					.getData(ApplicationKey.DEVICE_LIST_KEY);

			List<Long> ids = new ArrayList<Long>();
			List<String> names = new ArrayList<String>();

			if (deviceMap != null && !deviceMap.isEmpty()) {
				for (Long key : deviceMap.keySet()) {
					ids.add(key.longValue());
					names.add(deviceMap.get(key).getDeviceName() == null ? "未知"
							: deviceMap.get(key).getDeviceName() + "/" + String.format("0x%08x", key));
				}
			}

			deviceNames = (String[]) WZAppCommonUtil.convertListToArray(names);
			deviceIds = (Long[]) WZAppCommonUtil.convertListToArray(ids);

		} catch (Exception e) {
			logger.error("获取设备信息失败。", e);
		}
	}

	public String[] getText() {
		return deviceNames;
	}

	public Object[] getValue() {
		return deviceIds;
	}

}
