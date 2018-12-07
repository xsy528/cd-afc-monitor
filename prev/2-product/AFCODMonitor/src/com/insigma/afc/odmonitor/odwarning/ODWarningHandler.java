package com.insigma.afc.odmonitor.odwarning;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;

public class ODWarningHandler {

	private static Log logger = LogFactory.getLog(ODWarningHandler.class);

	public static void writeEntity(ODObject oDObject) {
		XMLEncoder xmlEncoder;
		File file = null;

		try {

			file = new File(System.getProperty("user.dir") + "/odConfig.xml");

			if (!file.exists()) {
				logger.error("文件" + System.getProperty("user.dir") + "/odConfig.xml" + "不存在");
				if (!file.createNewFile()) {
					logger.error("创建文件odConfig.xml失败");
					return;
				} else {
					logger.info("创建文件" + System.getProperty("user.dir") + "/odConfig.xml" + "成功");
				}
			}

			xmlEncoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));
			xmlEncoder.writeObject(oDObject);
			xmlEncoder.close();
		} catch (FileNotFoundException e) {
			logger.warn("找不到文件odConfig.xml", e);
		} catch (IOException e) {
			logger.warn("写文件odConfig.xml失败", e);
		}
	}

	/**
	 * 获取
	 * 
	 * @return ODObject
	 */
	public static ODObject readODObject() {
		ODObject oDObject = ODWarningHandler.readEntity();
		List<TMoniODWarning> list = null;
		if (oDObject == null) {
			list = initOdConfig();
			oDObject = new ODObject();
			oDObject.setList(list);
		} else {
			list = oDObject.getList();
			final MetroNode afcNode = AFCApplication.getAFCNode();
			Map<Long, MetroStation> stationMap = null;
			if (afcNode instanceof MetroLine) {
				stationMap = ((MetroLine) afcNode).getStationMap();
			}
			if (stationMap == null) {
				throw new IllegalArgumentException("车站列表为空。");
			}
			if (!stationMap.isEmpty()) {
				// 检查odConfig.xml文件的车站参数是否与内存的车站参数是否一致，否则重新生成odConfig.xml文件
				for (TMoniODWarning tMoniOdWarning : list) {
					boolean finded = false;

					for (MetroStation station : stationMap.values()) {
						if (tMoniOdWarning.getStationNo().intValue() == station.getId().getStationId().intValue()
								&& tMoniOdWarning.getStationName().trim().equals(station.getStationName().trim())) {
							finded = true;
							break;
						}
					}
					if (!finded) {
						logger.warn(System.getProperty("user.dir") + "/odConfig.xml文件内容里的车站参数错误，重新生成文件odConfig.xml");
						list = initOdConfig();
						oDObject.setList(list);
						break;
					}
				}
			}
		}
		return oDObject;
	}

	/**
	 * 获取
	 * 
	 * @return ODObject
	 */
	public static ODObject readEntity() {
		XMLDecoder xmlDecoder = null;
		InputStream input = null;
		try {
			File file = new File(System.getProperty("user.dir") + "/odConfig.xml");

			if (!file.exists()) {
				logger.error("找不到文件" + System.getProperty("user.dir") + "/odConfig.xml");
				return null;
			}
			input = new FileInputStream(file);
			if (input == null) {
				return null;
			}
			xmlDecoder = new XMLDecoder(input);
			ODObject oDObject = (ODObject) xmlDecoder.readObject();
			xmlDecoder.close();
			return oDObject;
		} catch (Exception e) {
			logger.warn("解析文件odConfig.xml失败", e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error("关闭文件流失败", e);
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static List<TMoniODWarning> initOdConfig() {
		List<TMoniODWarning> list = new ArrayList<TMoniODWarning>();
		final MetroNode afcNode = AFCApplication.getAFCNode();
		Map<Long, MetroStation> stationMap = null;
		if (afcNode instanceof MetroLine) {
			stationMap = ((MetroLine) afcNode).getStationMap();
		}
		if (stationMap == null) {
			throw new IllegalArgumentException("车站列表为空。");
		}
		for (MetroStation station : stationMap.values()) {
			TMoniODWarning tMoniODWarning = new TMoniODWarning(station.getId().getLineId(),
					station.getId().getStationId(), station.getStationName().trim(), 10000, 10000, 18000);
			list.add(tMoniODWarning);
		}
		ODObject oDObject = new ODObject();
		oDObject.setList(list);
		ODWarningHandler.writeEntity(oDObject);
		return list;
	}
}
