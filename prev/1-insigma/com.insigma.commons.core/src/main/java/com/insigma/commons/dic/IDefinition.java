package com.insigma.commons.dic;

public interface IDefinition {

	String[] getNameList();

	Object[] getValueList();

	String getNameByValue(Object key);

	Object getValueByName(String key);
}
