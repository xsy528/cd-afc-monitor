package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.constant.dic.AFCDeviceSubType;
import com.insigma.afc.monitor.constant.dic.AFCDeviceType;
import com.insigma.afc.monitor.model.entity.*;
import com.insigma.afc.monitor.model.entity.topology.AFCNodeLocation;
import com.insigma.afc.monitor.model.entity.topology.MetroDevice;
import com.insigma.afc.monitor.model.entity.topology.MetroDeviceModule;
import com.insigma.afc.monitor.model.entity.topology.MetroDeviceModuleId;
import com.insigma.afc.monitor.service.IDeviceModuleFactory;
import com.insigma.afc.monitor.service.IMetroEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ticket: 设备模块工厂实现类
 * @author xuzhemin
 */
@Service
public class DeviceModuleFactoryImpl implements IDeviceModuleFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(MetroNodeStatusServiceImpl.class);

	private IMetroEventService metroEventservice;

	@Autowired
	private DeviceModuleFactoryImpl(IMetroEventService iMetroEventService){
		this.metroEventservice = iMetroEventService;
	}

	@Override
	public List<MetroDeviceModule> getMetroDeviceModuleList(MetroDevice equipment) {
		List<MetroDeviceModule> result = new ArrayList<>();
		Short deviceType = equipment.getDeviceType();
		Short deviceSubType = equipment.getDeviceSubType();
		if (AFCDeviceType.GATE.equals(deviceType)) {
			List<Object[]> gateList = new ArrayList<>();
			gateList.add(new Object[] { 1L, "RDR", "维修门状态", 0, 110, 1, 1101 });

			if (AFCDeviceSubType.DEVICE_GATE_ENTRY.equals(deviceSubType)
					|| AFCDeviceSubType.DEVICE_GATE_BOTH.equals(deviceSubType)) {
				gateList.add(new Object[] { 2L, "CS1", "读写器1总体状态", 210, 230, 1, 600 });
			}
			if (AFCDeviceSubType.DEVICE_GATE_EXIT.equals(deviceSubType)
					|| AFCDeviceSubType.DEVICE_GATE_BOTH.equals(deviceSubType)) {
				gateList.add(new Object[] { 3L, "CTN", "票卡回收模块总体状态", 0, 150, 1, 1500 });
				gateList.add(new Object[] { 4L, "CS2", "读写器2总体状态", 0, 190, 1, 700 });
			}

			gateList.add(new Object[] { 23L, "DR1", "扇门状态", 100, 170, 1, 2103 });

			gateList.add(new Object[] { 5L, "EOD", "参数生效状态", 210, 150, 1, 300 });
			gateList.add(new Object[] { 6L, "ECU", "主控单元总体状态", 210, 190, 1, 500 });

			setResult(result, gateList, equipment);
		} else if (AFCDeviceType.POST.equals(deviceType)) {
			List<Object[]> postList = new ArrayList<>();
			postList.add(new Object[] { 7L, "RPU1", "打印机1总体状态", 13, 120, 1, 900 });
			postList.add(new Object[] { 8L, "TIM", "票卡发售模块总体状态", 13, 170, 1, 1400 });
			postList.add(new Object[] { 9L, "CS2", "读写器2总体状态", 13, 220, 1, 700 });
			postList.add(new Object[] { 10L, "ECU", "主控单元总体状态", 13, 270, 1, 500 });
			postList.add(new Object[] { 11L, "CS1", "读写器1总体状态", 260, 170, 1, 600 });
			postList.add(new Object[] { 12L, "EOD", "参数生效状态", 260, 220, 1, 300 });
			setResult(result, postList, equipment);
		} else if (AFCDeviceType.TVM.equals(deviceType)) {
			List<Object[]> tvmList = new ArrayList<>();
			tvmList.add(new Object[] { 13L, "EOD", "参数生效状态", 0, 60, 1, 300 });
			tvmList.add(new Object[] { 14L, "ECU", "主控单元总体状态", 0, 100, 1, 500 });

			tvmList.add(new Object[] { 15L, "BNC", "纸币找零模块总体状态", 0, 200, 1, 1600 });
			tvmList.add(new Object[] { 16L, "BNA", "纸币接收模块总体状态", 0, 240, 1, 1200 });
			tvmList.add(new Object[] { 16L, "CS1", "读写器1总体状态", 0, 280, 1, 600 });
			tvmList.add(new Object[] { 18L, "TIM", "票卡发售模块", 0, 320, 1, 1400 });

			tvmList.add(new Object[] { 19L, "RDR", "维修门状态", 240, 140, 1, 1101 });
			tvmList.add(new Object[] { 20L, "RPU1", "打印机1总体状态", 240, 180, 1, 900 });
			tvmList.add(new Object[] { 21L, "RPU2", "打印机2总体状态", 240, 220, 1, 1000 });

			tvmList.add(new Object[] { 22L, "CHS", "硬币模块总体状态", 120, 240, 1, 1300 });
			tvmList.add(new Object[] { 24L, "CS2", "读写器2总体状态", 120, 280, 1, 700 });
			setResult(result, tvmList, equipment);
		}

		return result;
	}

	private void setResult(List<MetroDeviceModule> result, List<Object[]> list, MetroDevice equipment) {

		for (Object[] obj : list) {
			long nodeId = equipment.getDeviceId();
			Short eventLevel = (short) 0;
			String moduleFlag = (String) obj[1];
			Short statusId = new Short(obj[6].toString());

			List<TmoEquStatusCur> eventList = metroEventservice.getEquStatusEntity(nodeId, statusId);
			if (eventList != null) {
				eventLevel = eventList.get(0).getStatusValue();
				//TODO : 此处从当前状态表中获取最新模块状态，或许存在脏数据
			}

			MetroDeviceModuleId id = new MetroDeviceModuleId(equipment.getStationId(),
					equipment.getDeviceId(), null);
			MetroDeviceModule deviceModule = new MetroDeviceModule(id, equipment.getLineId(),
					equipment.getLineName(), equipment.getStationName(), equipment.getDeviceName(), moduleFlag,
					eventLevel);
			deviceModule.setTextLocation(new AFCNodeLocation((Integer) obj[3], (Integer) obj[4], (Integer) obj[5]));
			deviceModule.setRemark((String) obj[2]);
			deviceModule.getId().setDeviceId(equipment.getDeviceId());
			deviceModule.setStatus(eventLevel);
			result.add(deviceModule);
		}
	}
}
