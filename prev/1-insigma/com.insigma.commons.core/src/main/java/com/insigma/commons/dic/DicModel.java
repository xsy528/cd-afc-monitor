/* 
 * 日期：2010-10-28
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.dic;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典模型 Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class DicModel {
	private String name;

	private String type;

	private String desc;

	private List<DicItemModel<Object>> items;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the items
	 */
	public List<DicItemModel<Object>> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<DicItemModel<Object>> items) {
		this.items = items;
	}

	public void addItem(DicItemModel<Object> item) {
		if (items == null) {
			items = new ArrayList<DicItemModel<Object>>();
		}
		items.add(item);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(100);
		sb.append("名称:").append(name);
		sb.append("，类型:").append(type);
		sb.append("，描述：").append(desc);
		if (items != null && items.size() > 0) {
			sb.append("，字典项：{");
			for (DicItemModel<Object> item : items) {
				sb.append("[");
				sb.append(item);
				sb.append("]");
			}
			sb.append("}");
		}
		return sb.toString();
	}

}
