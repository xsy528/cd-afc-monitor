/**
 * 
 */
package com.insigma.afc.service;

import java.util.List;

import com.insigma.afc.entity.TsyResource;
import com.insigma.commons.application.IService;

/**
 * @author qcj(qiuchangjin@zdwxgd.com)
 *
 */
public interface ITsyResourceService extends IService {
	/**
	 * 同步所有资源文件到指定目录
	 */
	public void syncResouce(String path);

	/**
	 * 同步指定资源文件到指定目录
	 */
	public void syncResouce(List<TsyResource> resources, String path);

	/**
	 * 同步所有资源文件到本地
	 */
	public void syncResouce();

	/**
	 * 新增或保存该资源文件
	 * @param resource
	 */
	public void save(TsyResource resource);

	/**
	 * 获取所有资源文件
	 * @return
	 */
	public List<TsyResource> getResourceList();
}
