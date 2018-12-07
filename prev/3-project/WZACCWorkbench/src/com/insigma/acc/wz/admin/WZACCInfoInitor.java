/* 
 * 日期：2011-2-23
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.afc.constant.ApplicationKey;
import com.insigma.afc.initor.AFCDaemonInitor;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.service.IMetroNodeService;
import com.insigma.afc.topology.service.TopologyInitial;
import com.insigma.commons.application.Application;

/**
 * Ticket:加载线路列表
 * 
 * @author Zhou-Xiaowei
 */
public class WZACCInfoInitor extends AFCDaemonInitor {

	private static final Log logger = LogFactory.getLog(WZACCInfoInitor.class);

	@Override
	public void init() {
		Map<Short, MetroLine> lineMap = new HashMap<Short, MetroLine>();
		List<Integer> accIDs = new ArrayList<Integer>();
		try {
			IMetroNodeService service = (IMetroNodeService) Application.getService(IMetroNodeService.class);
			List<MetroLine> lines = service.getAllMetroLine();
			if (lines != null) {
				for (MetroLine line : lines) {
					lineMap.put(line.getLineID(), line);
				}

				MetroLine line = new MetroLine();
				line.setLineID((short) 0);
				line.setLineName("ACC");
				lineMap.put(line.getLineID(), line);
			}
			//            List<MetroACC> acc = service.getMetroACC();
			//            for (MetroACC a : acc) {
			//                accIDs.add((int) a.getAccID());
			//            }

		} catch (Exception e) {
			logger.error("ACC路网信息获取失败", e);
		}
		Application.setData(ApplicationKey.LINE_LIST_KEY, lineMap);

		TopologyInitial topolotyInit = new TopologyInitial();
		topolotyInit.init(null, null);

		((Map<String, List<Integer>>) Application.getData(ApplicationKey.TOPLOGY_NODES)).put("ALL_ACC_ID", accIDs);
	}

	@Override
	public String getName() {
		return "加载WZACC路网信息列表";
	}

}
