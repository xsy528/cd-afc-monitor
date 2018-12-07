/* 
 * 日期：2010-11-12
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.initor;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.service.ITsyResourceService;
import com.insigma.afc.ui.AFCUI;
import com.insigma.commons.application.exception.ServiceException;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class AFCUIInitor extends AFCInitor {

	@Override
	public void init() {
		AFCUI.initialize();

		try {
			ITsyResourceService resourceService = AFCApplication.getService(ITsyResourceService.class);
			resourceService.syncResouce();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public String getName() {
		return "AFCUI初始化";
	}

}
