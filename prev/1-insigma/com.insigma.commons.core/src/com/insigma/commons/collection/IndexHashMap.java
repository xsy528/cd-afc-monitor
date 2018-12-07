/* 
 * 项目:      AFC通信组件
 * 代码文件:   HashMap.java
 * 版本: 1.0
 * 日期: 2007-11-19 上午11:52:23
 * 作者  姜旭锋
 * Email: jiangxufeng@zdwxjd.com

 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.commons.collection;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author jiangxf
 *
 */
public class IndexHashMap<K, V> {

	private final LinkedList<K> keyList = new LinkedList<K>();

	/**
	 * @return the keyList
	 */
	public List<K> getKeyList() {
		return keyList;
	}

	private Map<K, V> map = new HashMap<K, V>();

	public V put(K key, V value, int index) {
		V rv = map.put(key, value);
		if (index == -1 || keyList.size() < index) {
			keyList.add(key);
		} else {
			keyList.add(index, key);
		}
		return rv;
	}

	public V get(K key) {
		return map.get(key);
	}

	public void clear() {
		keyList.clear();
		map.clear();
	}

	/**
	 * @param group
	 * @return
	 */
	public boolean containsKey(K key) {
		return map.containsKey(key);
	}

}
