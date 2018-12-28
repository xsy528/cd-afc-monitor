/**
 * 
 */
package com.insigma.afc.monitor.map;

import com.insigma.afc.topology.MetroNode;
import com.insigma.commons.editorframework.ActionTreeNode;
import com.insigma.commons.editorframework.graphic.editor.MapItem;

/**
 * 只根据某种节点类型负责生成一个地图对象，<b>不做遍历</b>
 * @author Administrator
 *
 */
public interface IGraphicMapBuilder {

	/**
	 * 只根据某种节点类型负责生成一个地图对象
	 * @param node
	 * @return
	 */
	MapItem buildGraphicMap(MetroNode node);

	/**
	 * ActionTreeNode 创建完成时回调
	 * @param treeNode
	 */
	public void treeNodeCreated(ActionTreeNode treeNode);

}
