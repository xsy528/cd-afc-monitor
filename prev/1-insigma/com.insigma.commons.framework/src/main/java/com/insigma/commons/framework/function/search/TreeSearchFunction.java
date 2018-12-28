/**
 * iFrameWork 框架
 * 
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description   
 * @date          2009-4-2
 * @copyright     浙江浙大网新众合轨道交通工程有限公司 
 * @history       
 */
package com.insigma.commons.framework.function.search;

import com.insigma.commons.ui.tree.ITreeProvider;

public class TreeSearchFunction<V, T> extends SearchFunction<T> {

	private ITreeProvider treeProvider;

	private String treeLabel = "网络拓扑结构";

	private int treeDepth;

	private boolean navigatable;

	private boolean hideTree = false;

	public ITreeProvider getTreeProvider() {
		return treeProvider;
	}

	public void setTreeProvider(ITreeProvider treeProvider) {
		this.treeProvider = treeProvider;
	}

	public String getTreeLabel() {
		return treeLabel;
	}

	public void setTreeLabel(String treeLabel) {
		this.treeLabel = treeLabel;
	}

	public int getTreeDepth() {
		return treeDepth;
	}

	public void setTreeDepth(int treeDepth) {
		this.treeDepth = treeDepth;
	}

	public boolean isNavigatable() {
		return navigatable;
	}

	public void setNavigatable(boolean navigatable) {
		this.navigatable = navigatable;
	}

	/**
	 * @return the hideTree
	 */
	public boolean isHideTree() {
		return hideTree;
	}

	/**
	 * @param hideTree
	 *            the hideTree to set
	 */
	public void setHideTree(boolean hideTree) {
		this.hideTree = hideTree;
	}

}
