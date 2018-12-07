package com.insigma.commons.converter;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

public final class IntegerConverter implements Converter {

	private Object defaultValue;

	private boolean useDefault;

	public IntegerConverter() {
		defaultValue = null;
		useDefault = true;
		defaultValue = null;
		useDefault = false;
	}

	public IntegerConverter(Object defaultValue) {
		this.defaultValue = null;
		useDefault = true;
		this.defaultValue = defaultValue;
		useDefault = true;
	}

	@SuppressWarnings("unchecked")
	public Object convert(Class type, Object value) {
		if (value == null || "".equals(value.toString().trim()))
			return defaultValue;
		if (value instanceof Integer)
			return value;
		try {
			return new Integer(value.toString());
		} catch (Exception e) {
			if (useDefault)
				return defaultValue;
			else
				throw new ConversionException(e);
		}
	}
}
