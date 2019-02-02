/**
 * 
 */
package com.insigma.afc.monitor.service;


import com.insigma.afc.monitor.model.entity.AFCResource;
import com.insigma.afc.monitor.model.entity.TsyResource;

import java.util.List;

/**
 * 资源服务
 * @author qcj(qiuchangjin@zdwxgd.com)
 *
 */
public interface ITsyResourceService {

	/**
	 * 同步所有资源文件到本地
	 */
	List<TsyResource> syncResouce();

	/**
	 * 新增或保存该资源文件
	 * @param resource
	 */
	void save(TsyResource resource);

	/**
	 * 获取所有资源文件
	 * @return
	 */
	List<AFCResource> getAFCResourceList();
}
