/* 
 * 日期：2010-12-17
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.ui.widgets.provider;

import java.util.List;

import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.service.IMetroNodeService;
import com.insigma.commons.application.Application;
import com.insigma.commons.application.exception.ServiceException;
import com.insigma.commons.ui.provider.IComboDataSource;

/**
 * Ticket:提供线路列表
 * 
 * @author Zhou-Xiaowei
 */
public class AFCMetroLinesProvider implements IComboDataSource<Integer> {

	public String[] getText() {

		List<MetroLine> lineList = getMetroLine();

		if ((lineList != null) && (lineList.size() > 0)) {
			String[] lines = new String[lineList.size()];

			for (int i = 0; i < lineList.size(); i++) {
				lines[i] = lineList.get(i).getLineName();
			}

			return lines;
		} else {
			return null;
		}
	}

	public Integer[] getValue() {

		List<MetroLine> lineList = getMetroLine();

		if ((lineList != null) && (lineList.size() > 0)) {
			Integer[] lines = new Integer[lineList.size()];

			for (int i = 0; i < lineList.size(); i++) {
				lines[i] = (int) lineList.get(i).getLineID();
			}

			return lines;
		} else {
			return null;
		}
	}

	private List<MetroLine> getMetroLine() {
		List<MetroLine> lineList = null;

		try {
			IMetroNodeService metroNodeService = (IMetroNodeService) Application.getService(IMetroNodeService.class);
			lineList = metroNodeService.getAllMetroLine();
		} catch (ServiceException e) {

			e.printStackTrace();
		}

		return lineList;
	}

}
