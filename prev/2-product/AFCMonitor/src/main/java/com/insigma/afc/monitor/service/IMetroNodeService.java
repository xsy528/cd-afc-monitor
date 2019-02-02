/* 
 * 日期：2010-4-30
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.dto.NodeData;
import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.topology.model.entity.MetroNode;
import org.springframework.web.multipart.MultipartFile;

/**
 * 节点管理服务
 * 
 * @author xuzhemin
 */
public interface IMetroNodeService {

	/**
	 * 创建节点
	 * @param nodeData
	 * @return
	 */
	Result<MetroNode> create(NodeData nodeData, MultipartFile file);

	/**
	 * 修改节点
	 * @param nodeData
	 * @param file
	 * @return
	 */
	Result<MetroNode> update(NodeData nodeData, MultipartFile file);

	/**
	 * 查询节点详情
	 * @param nodeId
	 * @return
	 */
	Result query(Long nodeId);

    /**
     * 删除节点
	 * @param nodeId
     * @return
     */
	Result delete(Long nodeId);
}
