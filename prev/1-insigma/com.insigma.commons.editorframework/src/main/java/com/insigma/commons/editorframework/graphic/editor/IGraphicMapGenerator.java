/* 
 * 项目:    Insigma 编辑器框架
 * 版本: 	1.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.graphic.editor;

import com.insigma.commons.editorframework.ActionTreeNode;

public interface IGraphicMapGenerator {
	/**
	 * 获取地图左边树的根节点
	 * 
	 * @return 
	 */
	ActionTreeNode getMapTreeRootNode(boolean force);

	/**
	 * 获取地图结构的根节点
	 * @return
	 */
	public MapItem getMapRootItem(boolean force);

	/**
	 * 根据MapItem 生成树节点ActionTreeNode
	 * @param mapItem
	 * @return
	 */
	ActionTreeNode buildTreeNode(MapItem mapItem);

	boolean isEditUI();
}
