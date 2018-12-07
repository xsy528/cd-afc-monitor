/**
 * iFrameWork 框架
 * 
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description   
 * @date          2009-4-3
 * @copyright     浙江浙大网新众合轨道交通工程有限公司 
 * @history       
 */
package com.insigma.commons.ui.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

	private Object key;

	private String text;

	private boolean checked = false;

	private List<TreeNode> childs;

	public TreeNode() {
		childs = new ArrayList<TreeNode>();
	}

	public List<TreeNode> getChilds() {
		return childs;
	}

	public void setChilds(List<TreeNode> childs) {
		this.childs = childs;
	}

	public void addNode(TreeNode node) {
		if (childs == null) {
			childs = new ArrayList<TreeNode>();
		}
		childs.add(node);
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the checked
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * @param checked
	 *            the checked to set
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
