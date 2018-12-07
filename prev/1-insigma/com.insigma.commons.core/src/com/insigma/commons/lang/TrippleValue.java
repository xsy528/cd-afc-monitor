/**
 * iFrameWork 框架 公共基础库
 * 
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description   
 * @date          2009-4-4
 * @copyright     浙江浙大网新众合轨道交通工程有限公司 
 * @history       
 */
package com.insigma.commons.lang;

public class TrippleValue<K, V, E> {

	protected K key;

	protected V value;

	protected E ext;

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public E getExt() {
		return ext;
	}

	public void setExt(E ext) {
		this.ext = ext;
	}
}
