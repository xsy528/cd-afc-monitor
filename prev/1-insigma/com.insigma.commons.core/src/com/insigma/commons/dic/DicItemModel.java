/* 
 * 日期：2010-10-28
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.dic;

import com.insigma.commons.lang.PairValue;

/**
 * 字典项模型 Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class DicItemModel<V> extends PairValue<String, V> {

	private String name;

	private String desc;

	private String ref;

	private String relation;

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
	 * @return the ref
	 */
	public String getRef() {
		return ref;
	}

	/**
	 * @param ref
	 *            the ref to set
	 */
	public void setRef(String ref) {
		this.ref = ref;
	}

	/**
	 * @return the relation
	 */
	public String getRelation() {
		return relation;
	}

	/**
	 * @param relation
	 *            the relation to set
	 */
	public void setRelation(String relation) {
		this.relation = relation;
	}

	public int getInt() {
		return Integer.valueOf(getValue().toString());
	}

	public long getLong() {
		return Long.valueOf(getValue().toString());
	}

	public long getShort() {
		return Short.valueOf(getValue().toString());
	}

	public float getFloat() {
		return Float.valueOf(getValue().toString());
	}

	public double getDouble() {
		return Double.valueOf(getValue().toString());
	}

	public char getChar() {
		return Character.valueOf(getValue().toString().charAt(0));
	}

	public byte getByte() {
		return Byte.valueOf(getValue().toString());
	}

	public boolean getBoolean() {
		return Boolean.valueOf(getValue().toString());
	}

	public String getString() {
		return getValue().toString();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(100);
		sb.append("Key:").append(getKey());
		sb.append("，名称：").append(getName());
		sb.append("，值：").append(getValue());
		sb.append("，引用：").append(getRef());
		sb.append("，关系：").append(getRelation());
		sb.append("，描述：").append(getDesc());
		return sb.toString();
	}
}
