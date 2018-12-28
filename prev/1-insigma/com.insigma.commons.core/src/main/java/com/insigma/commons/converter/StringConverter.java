package com.insigma.commons.converter;

import org.apache.commons.beanutils.Converter;

public final class StringConverter implements Converter {

	public StringConverter() {
	}

	@SuppressWarnings("unchecked")
	public Object convert(Class type, Object value) {
		if (value == null)
			return null;
		else
			return value.toString();
	}
}
