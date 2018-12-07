/**
 * 
 */
package com.insigma.afc.odmonitor.map;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.monitor.map.builder.AbstractGraphicMapBuilder;
import com.insigma.afc.odmonitor.listview.item.SectionOdFlowStatsView;
import com.insigma.afc.odmonitor.service.ISectionODFlowService;
import com.insigma.afc.topology.MetroACC;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroDeviceModule;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.editorframework.graphic.LineGraphicItem;
import com.insigma.commons.editorframework.graphic.editor.MapItem;

/**
 * @author qcj(qiuchangjin@zdwxgd.com)
 *
 */
public class SectionOdGraphicMapBuilder extends AbstractGraphicMapBuilder {

	private static final Log logger = LogFactory.getLog(SectionOdGraphicMapBuilder.class);

	private ISectionODFlowService sectionOdFlowService;
	private Integer[] timeIntervalIds;

	private Short direction;

	private Date date;

	private List<SectionOdFlowStatsView> sectionOdFlowStatsMap;

	@Override
	public MapItem buildGraphicMap(MetroNode node) {
		MapItem item = new MapItem(node.id());
		AFCNodeLevel type = node.level();
		switch (type) {
		case ACC:
			MetroACC acc = (MetroACC) node;
			item.setImage(acc.getPicName()); // 右侧监控布局底图
			item.setValue(node);
			break;
		case LC:
			item = getLine((MetroLine) node);
			break;
		case SC:
			item = getStation((MetroStation) node);
			break;
		case SLE:
			item = getDeviceItem((MetroDevice) node);
			break;
		default:
			item = getModule((MetroDeviceModule) node);
			break;
		}

		if (sectionOdFlowStatsMap == null) {
			Short[] lines = new Short[] { AFCApplication.getLineId() };
			sectionOdFlowStatsMap = sectionOdFlowService.getSectionODFlowStatsViewList(lines, timeIntervalIds,
					direction, date, -1, 0);
		}
		for (SectionOdFlowStatsView sectionMap : sectionOdFlowStatsMap) {
			addSectionItem(item, sectionMap);

		}
		return item;
	}

	private void addSectionItem(MapItem map, SectionOdFlowStatsView sectionMap) {
		int angle = 0;
		LineGraphicItem lineItem = new LineGraphicItem(map, sectionMap.getUpStationPosX().intValue(),
				sectionMap.getUpStationPosY().intValue(), sectionMap.getDownStationPosX().intValue(),
				sectionMap.getDownStationPosY().intValue(), angle);

		setColorAndHint(sectionMap, lineItem, map);

		map.addGraphicItem(lineItem);
		map.setValue(sectionMap);

	}

	private void setColorAndHint(SectionOdFlowStatsView sectionMap, LineGraphicItem lineItem, MapItem item) {
		int[] colorValues = new int[2];
		colorValues[0] = getColorValue(sectionMap.getOdLevelFlag());
		colorValues[1] = getColorValue(sectionMap.getDownOdLevelFlag());
		lineItem.setColorValues(colorValues);
		lineItem.setLineWidth(2);
		String tip = sectionMap.getUpstation().split("/")[0] + "→" + sectionMap.getDownstation().split("/")[0];
		//        if (direction==0) {
		//            tip += "\n上行客流：" + sectionMap.getUpcount();
		//        }else if(direction==1){
		//            tip = tip.replace("→", "←");
		//            tip += "\n下行客流：" + sectionMap.getDowncount();
		//        }else{
		tip += "\n上行客流：" + sectionMap.getUpcount() + "\n下行客流：" + sectionMap.getDowncount();
		//        }

		item.setHint(tip);
	}

	/**
	 * @param sectionMap
	 * @param lineItem
	 */
	private int getColorValue(short odLevel) {
		switch (odLevel) {
		case 0:
			return SWT.COLOR_GREEN;
		case 1:
			return SWT.COLOR_BLUE;
		case 2:
			return SWT.COLOR_RED;
		}
		return SWT.COLOR_GREEN;
	}

	public ISectionODFlowService getSectionOdFlowService() {
		return sectionOdFlowService;
	}

	public void setSectionOdFlowService(ISectionODFlowService sectionOdFlowService) {
		this.sectionOdFlowService = sectionOdFlowService;
	}

	public Integer[] getTimeIntervalIds() {
		return timeIntervalIds;
	}

	public void setTimeIntervalIds(Integer[] timeIntervalIds) {
		this.timeIntervalIds = timeIntervalIds;
	}

	public Short getDirection() {
		return direction;
	}

	public void setDirection(Short direction) {
		this.direction = direction;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
