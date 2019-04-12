package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.constant.dic.AFCDeviceSubType;
import com.insigma.afc.monitor.constant.dic.AFCDeviceType;
import com.insigma.afc.monitor.model.entity.*;
import com.insigma.afc.monitor.service.IDeviceModuleFactory;
import com.insigma.afc.monitor.service.IMetroEventService;
import com.insigma.afc.monitor.service.rest.TopologyService;
import com.insigma.commons.util.NodeIdUtils;
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
		short nodeType = NodeIdUtils.nodeIdStrategy.getDeviceType(equipment.getDeviceId());
		if (nodeType == AFCDeviceType.ENG || nodeType == AFCDeviceType.EXG
				|| nodeType == AFCDeviceType.RG) {
			List<Object[]> gateList = new ArrayList<Object[]>();
			short deviceSubType = equipment.getDeviceSubType();

			if (AFCDeviceSubType.DEVICE_GATE_WIDE == deviceSubType
					|| AFCDeviceSubType.DEVICE_GATE_BOTH == deviceSubType
					|| AFCDeviceSubType.DEVICE_GATE_ENTRY == deviceSubType) {
				gateList.add(new Object[] { new Long(1), "CS1", "读写器1总体状态", 280, 110, 1 });
			}
			if (AFCDeviceSubType.DEVICE_GATE_WIDE == deviceSubType
					|| AFCDeviceSubType.DEVICE_GATE_BOTH == deviceSubType
					|| AFCDeviceSubType.DEVICE_GATE_EXIT == deviceSubType) {
				gateList.add(new Object[] { new Long(2), "CS2", "读写器2总体状态", 0, 110, 1 });
				gateList.add(new Object[] { new Long(3), "CTN", "票卡回收模块总体状态", 200, 180, 1 });
			}
			gateList.add(new Object[] { new Long(4), "EOD", "参数生效状态", 0, 70, 1 });
			gateList.add(new Object[] { new Long(5), "ECU", "主控单元总体状态", 190, 70, 1 });
			gateList.add(new Object[] { new Long(6), "RDR", "维修门状态", 90, 190, 1 });
			//20140514 新增
			gateList.add(new Object[] { new Long(23), "DR1", "扇门状态", 110, 105, 1 });
			setResult(result, gateList, equipment);
		} else if (AFCDeviceType.POST.shortValue() == nodeType) {
			List<Object[]> postList = new ArrayList<Object[]>();
			postList.add(new Object[] { new Long(7), "EOD", "参数生效状态", 5, 120, 1 });
			postList.add(new Object[] { new Long(8), "CS1", "读写器1总体状态", 30, 80, 1 });
			postList.add(new Object[] { new Long(9), "CS2", "读写器2总体状态", 5, 200, 1 });
			postList.add(new Object[] { new Long(10), "ECU", "主控单元总体状态", 75, 160, 1 });
			postList.add(new Object[] { new Long(11), "TIM", "票卡发售模块总体状态", 5, 240, 1 });
			postList.add(new Object[] { new Long(12), "RPU1", "打印机1总体状态", 270, 130, 1 });
			setResult(result, postList, equipment);
		} else if (AFCDeviceType.TVM.shortValue() == nodeType) {
			List<Object[]> tvmList = new ArrayList<Object[]>();
			tvmList.add(new Object[] { new Long(13), "RDR", "维修门状态", 250, 60, 1 });
			tvmList.add(new Object[] { new Long(14), "RPU2", "打印机2总体状态", 250, 150, 1 });
			tvmList.add(new Object[] { new Long(15), "RPU1", "打印机1总体状态", 250, 120, 1 });
			tvmList.add(new Object[] { new Long(16), "ECU", "主控单元总体状态", 0, 100, 1 });
			tvmList.add(new Object[] { new Long(17), "EOD", "参数生效状态", 0, 65, 1 });
			tvmList.add(new Object[] { new Long(18), "TIM", "票卡发售模块", 125, 255, 1 });
			tvmList.add(new Object[] { new Long(19), "BNA", "纸币接收器模块总体状态", 250, 90, 1 });
			tvmList.add(new Object[] { new Long(20), "CHS", "硬币接收器模块总体状态", 250, 180, 1 });
			tvmList.add(new Object[] { new Long(21), "CS1", "读写器1总体状态", 0, 205, 1 });
			tvmList.add(new Object[] { new Long(22), "CS2", "读写器2总体状态", 125, 225, 1 });
			//20140514 新增
			tvmList.add(new Object[] { new Long(24), "BNC", "纸币找零模块总体状态", 0, 240, 1 });
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
