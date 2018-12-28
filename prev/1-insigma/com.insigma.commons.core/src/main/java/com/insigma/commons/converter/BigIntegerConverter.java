package com.insigma.commons.converter;

import java.math.BigInteger;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

public final class BigIntegerConverter implements Converter {

	private Object defaultValue;

	private boolean useDefault;

	public BigIntegerConverter() {
		defaultValue = null;
		useDefault = true;
		defaultValue = null;
		useDefault = false;
	}

	public BigIntegerConverter(Object defaultValue) {
		this.defaultValue = null;
		useDefault = true;
		this.defaultValue = defaultValue;
		useDefault = true;
	}

	@SuppressWarnings("unchecked")
	public Object convert(Class type, Object value) {
		if (value == null || "".equals(value.toString().trim()))
			return defaultValue;
		if (value instanceof BigInteger)
			return value;
		try {
			return new BigInteger(value.toString());
		} catch (Exception e) {
			if (useDefault)
				return defaultValue;
			else
				throw new ConversionException(e);
		}
	}
}
