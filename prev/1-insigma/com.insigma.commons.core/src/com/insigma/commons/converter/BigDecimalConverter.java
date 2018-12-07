package com.insigma.commons.converter;

import java.math.BigDecimal;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

public final class BigDecimalConverter implements Converter {

	private Object defaultValue;

	private boolean useDefault;

	public BigDecimalConverter() {
		defaultValue = null;
		useDefault = true;
		defaultValue = null;
		useDefault = false;
	}

	public BigDecimalConverter(Object defaultValue) {
		this.defaultValue = null;
		useDefault = true;
		this.defaultValue = defaultValue;
		useDefault = true;
	}

	@SuppressWarnings("unchecked")
	public Object convert(Class type, Object value) {
		if (value == null || "".equals(value.toString().trim()))
			return defaultValue;
		if (value instanceof BigDecimal)
			return value;
		try {
			return new BigDecimal(value.toString().trim());
		} catch (Exception e) {
			if (useDefault)
				return defaultValue;
			else
				throw new ConversionException(e);
		}
	}
}
