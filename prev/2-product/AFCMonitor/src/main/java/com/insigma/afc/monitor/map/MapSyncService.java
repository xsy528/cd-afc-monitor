package com.insigma.afc.monitor.map;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.application.Application;
import com.insigma.commons.config.NetworkConfig;
import com.insigma.commons.service.Service;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class MapSyncService extends Service implements IMapSyncService {

	public MetroNode decode(InputStream in) {
		XStream xStream = new XStream(new DomDriver());
		NetworkConfig config = Application.getConfig(NetworkConfig.class);
		MetroNode metroNode = (MetroNode) xStream.fromXML(in);//解析出来的node
		AFCNodeLevel level = metroNode.level();
		if (level == AFCNodeLevel.ACC) {
			//TODO acc 不支持地图同步
			return null;
		} else if (level == AFCNodeLevel.LC) {
			MetroLine metroLine = (MetroLine) metroNode;
			logger.info("metroLine.getLineID() 是: " + metroLine.getLineID());
			logger.info("currentNode.getLineId() 是: " + config.getLineNo().shortValue());
			if (metroLine.getLineID() == config.getLineNo().shortValue()) {
				for (MetroStation station : metroLine.getStationList()) {
					logger.info("station.getId().getStationId().intValue() 是: "
							+ station.getId().getStationId().intValue());
					if (station.getId().getStationId().intValue() == config.getStationNo().intValue()) {
						return station;
					}
				}
			}
			return metroLine;
		}
		return null;

	}

	public void encode(MetroNode node, OutputStream out) throws Exception {
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out, "UTF-8");
		outputStreamWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
		XStream xStream = new XStream();
		xStream.toXML(node, outputStreamWriter);
	}

}
