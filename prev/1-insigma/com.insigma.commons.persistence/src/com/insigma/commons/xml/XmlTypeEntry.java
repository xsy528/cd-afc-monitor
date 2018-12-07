package com.insigma.commons.xml;

public @interface XmlTypeEntry {

	String xmlValue();

	@SuppressWarnings("unchecked")
	Class javaClass();

}
