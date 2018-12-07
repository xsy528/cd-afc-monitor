/* 
 * 日期：2010-11-12
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.initor;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.topology.INodeIdStrategy;
import com.insigma.commons.application.Application;
import com.insigma.commons.application.exception.ServiceException;

/**
 * 字典定义初始化 Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class AFCApplicationInitor extends SystemInitor {

	INodeIdStrategy nodeIdStrategy;

	public AFCApplicationInitor(INodeIdStrategy nodeIdStrategy) {
		super();
		this.nodeIdStrategy = nodeIdStrategy;
	}

	@Override
	public String getName() {
		return "字典定义初始化";
	}

	@Override
	public boolean init() {
		logger.debug("开始初始化AFC应用环境...");
		try {
			if (nodeIdStrategy == null) {
				nodeIdStrategy = Application.getClassBean(INodeIdStrategy.class);
			}
			if (nodeIdStrategy == null) {
				return false;
			}
			AFCApplication.init(nodeIdStrategy);
		} catch (ServiceException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean isDaemon() {
		return false;
	}

	@Override
	public boolean nextIfFail() {
		return false;
	}

}
