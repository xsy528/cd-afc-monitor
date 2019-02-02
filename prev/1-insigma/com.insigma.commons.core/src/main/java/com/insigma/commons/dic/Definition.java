package com.insigma.commons.dic;

import com.insigma.commons.dic.annotation.DicItem;
import com.insigma.commons.lang.PairValue;

import java.util.*;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class Definition implements IDefinition {
	private String[] nameList = new String[0];

	private Object[] codeList = new Object[0];

	public Map<String, DicitemEntry> dicItecEntryMap = new HashMap<>();

	public Definition() {

	}

	/**
	 * 根据值获取名称
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public String getNameByValue(Object key) {
		for (DicitemEntry v : dicItecEntryMap.values()) {
			if (v.value.equals(key)) {
				return v.dicitem.name();
			}
		}
		return "未知";
	}

	/**
	 * 根据名称获取值
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public Object getValueByName(String key) {
		for (DicitemEntry v : dicItecEntryMap.values()) {
			if (v.dicitem.name().equals(key)) {
				return v.value;
			}
		}
		return null;
	}

	public void setNameList(String[] nameList) {
		this.nameList = nameList;
	}

	public void setCodeList(Object[] codeList) {
		this.codeList = codeList;
	}

	@Override
	public String[] getNameList() {
		return nameList.clone();
	}

	@Override
	public Object[] getValueList() {
		return codeList.clone();
	}

	public List<PairValue<Object, String>> getByGroup(String group) {
		List<DicitemEntry> groups1 = new ArrayList<>();
		List<PairValue<Object, String>> groups = new ArrayList<>();
		for (DicitemEntry v : dicItecEntryMap.values()) {
			DicItem dicItem = v.dicitem;
			if ("".equals(group)) {
				groups1.add(v);
			} else if (dicItem.group().equals(group)) {
				groups1.add(v);
			}
		}
		Collections.sort(groups1, DicitemEntry.DICCMPR);
		for (DicitemEntry v : groups1) {
			PairValue<Object, String> value = new PairValue<>();
			value.setKey(v.value);
			value.setValue(v.dicitem.name());
			groups.add(value);
		}
		return groups;
	}

	public Object[] getNameListByGroup(String group) {
		List<String> groups = new ArrayList<>();
		for (DicitemEntry v : dicItecEntryMap.values()) {
			DicItem dicItem = v.dicitem;
			if (dicItem.group().equals(group)) {
				groups.add(dicItem.name());
			}
		}
		return groups.toArray();
	}

	public Object[] getValueListByGroup(String group) {
		List<Object> groups = new ArrayList<>();
		for (DicitemEntry v : dicItecEntryMap.values()) {
			DicItem dicItem = v.dicitem;
			if (dicItem.group().equals(group)) {
				groups.add(v.value);
			}
		}
		return groups.toArray();
	}

	/**
	 * @return the dicItemMap
	 */
	public DicItem getDicItemByName(Integer key) {
		for (DicitemEntry v : dicItecEntryMap.values()) {
			if (v.value.equals(key)) {
				return v.dicitem;
			}
		}
		return null;
	}

	/**
	 * 返回已经按@index 升序排序后的list
	 * 
	 * @return
	 */
	public List<DicitemEntry> getSortedDicitems() {
		ArrayList<DicitemEntry> values = new ArrayList<>(dicItecEntryMap.values());
		Collections.sort(values, DicitemEntry.DICCMPR);
		return values;
	}

	/**
	 * @return the dicItemMap
	 */
	public Map<Object, DicItem> getDicItemMap() {
		Map<Object, DicItem> map = new HashMap<>(dicItecEntryMap.size());
		for (DicitemEntry v : dicItecEntryMap.values()) {
			map.put(v.value, v.dicitem);
		}
		return map;
	}

	/**
	 * @return the dicItecEntryMap
	 */
	public Map<String, DicitemEntry> getDicItecEntryMap() {
		return dicItecEntryMap;
	}

	/**
	 * @return the codeMap
	 */
	public Map<Object, String> getCodeMap() {
		Map<Object, String> map = new HashMap<>(dicItecEntryMap.size());
		for (DicitemEntry v : dicItecEntryMap.values()) {
			map.put(v.value, v.dicitem.name());
		}
		return map;
	}

	/**
	 * @return the nameMap
	 */
	public Map<String, Object> getNameMap() {
		Map<String, Object> map = new HashMap<>(dicItecEntryMap.size());
		for (DicitemEntry v : dicItecEntryMap.values()) {
			map.put(v.dicitem.name(), v.value);
		}
		return map;
	}
}
