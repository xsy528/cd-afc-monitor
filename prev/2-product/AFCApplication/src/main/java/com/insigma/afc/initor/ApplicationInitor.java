/* 
 * 日期：2010-11-12
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.initor;

import org.apache.commons.lang.StringUtils;

import com.insigma.commons.application.Application;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class ApplicationInitor extends SystemInitor {
	private String[] resourceLocations;

	public ApplicationInitor(String... locations) {
		this.resourceLocations = locations;
		if (resourceLocations == null || resourceLocations.length == 0) {
			resourceLocations = new String[] { "spring-config.xml" };
		}
	}

	public String getName() {
		return "应用初始化";
	}

	public boolean init() {
		String rs = StringUtils.join(resourceLocations, ",");
		logger.debug("读取配置文件：" + rs);
		Application.initialize(resourceLocations);
		logger.debug("读取配置文件[" + rs + "]完成，完成Spring初始化");
		return true;
	}

	public boolean nextIfFail() {
		return false;
	}

}
