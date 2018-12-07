package com.insigma.commons.converter;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

public final class BooleanConverter implements Converter {

	private Object defaultValue;

	private boolean useDefault;

	public BooleanConverter() {
		defaultValue = null;
		useDefault = true;
		defaultValue = null;
		useDefault = false;
	}

	public BooleanConverter(Object defaultValue) {
		this.defaultValue = null;
		useDefault = true;
		this.defaultValue = defaultValue;
		useDefault = true;
	}

	@SuppressWarnings("unchecked")
	public Object convert(Class type, Object value) {
		if (value == null || "".equals(value.toString().trim()))
			throw new ConversionException("No value specified");
		if (value instanceof Boolean)
			return value;
		try {
			String stringValue = value.toString();
			if (stringValue.equalsIgnoreCase("yes") || stringValue.equalsIgnoreCase("y")
					|| stringValue.equalsIgnoreCase("true") || stringValue.equalsIgnoreCase("on")
					|| stringValue.equalsIgnoreCase("1"))
				return Boolean.TRUE;
			if (stringValue.equalsIgnoreCase("no") || stringValue.equalsIgnoreCase("n")
					|| stringValue.equalsIgnoreCase("false") || stringValue.equalsIgnoreCase("off")
					|| stringValue.equalsIgnoreCase("0"))
				return Boolean.FALSE;
			if (useDefault)
				return defaultValue;
			else
				throw new ConversionException(stringValue);
		} catch (ClassCastException e) {
			if (useDefault)
				return defaultValue;
			else
				throw new ConversionException(e);
		}
	}
}
