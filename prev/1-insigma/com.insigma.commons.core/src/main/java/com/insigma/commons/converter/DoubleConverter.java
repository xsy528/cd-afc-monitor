package com.insigma.commons.converter;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

public final class DoubleConverter implements Converter {

	private Object defaultValue;

	private boolean useDefault;

	public DoubleConverter() {
		defaultValue = null;
		useDefault = true;
		defaultValue = null;
		useDefault = false;
	}

	public DoubleConverter(Object defaultValue) {
		this.defaultValue = null;
		useDefault = true;
		this.defaultValue = defaultValue;
		useDefault = true;
	}

	@SuppressWarnings("unchecked")
	public Object convert(Class type, Object value) {
		if (value == null || "".equals(value.toString().trim()))
			return defaultValue;
		if (value instanceof Double)
			return value;
		try {
			return new Double(value.toString());
		} catch (Exception e) {
			if (useDefault)
				return defaultValue;
			else
				throw new ConversionException(e);
		}
	}
}
