package com.insigma.commons.dic;

public interface IDefinition {

	public String[] getNameList();

	public Object[] getValueList();

	public String getNameByValue(Object key);

	public Object getValueByName(String key);
}
