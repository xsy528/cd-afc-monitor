/* 
 * $Source: $
 * $Revision: $ 
 * $Date: $
 * 
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.commons.util.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * 用于数字类型的转换
 * 
 * @author 厉蒋
 */
public class NumberUtil {

	/**
	 * 无符号的byte 转short
	 * 
	 * @param value
	 * @return
	 */
	public static short unsignedByteToShort(byte value) {
		return (short) ((short) 0x00FF & value);
	}

	/**
	 * 无符号的byte 转int
	 * 
	 * @param value
	 * @return
	 */
	public static int unsignedByteToInt(byte value) {
		return 0x00FF & value;
	}

	/**
	 * 无符号的short 转int
	 * 
	 * @param value
	 * @return
	 */
	public static int unsigneShortToInt(short value) {
		return (0x0000FFFF & value);
	}

	/**
	 * 无符号的int 转long
	 * 
	 * @param value
	 * @return
	 */
	public static long unsigneIntToLong(long value) {
		return (long) ((long) (0x00000000FFFFFFFF) & value);
	}

	/**
	 * Convert the given number into an instance of the given target class.
	 * 
	 * @param number
	 *            the number to convert
	 * @param targetClass
	 *            the target class to convert to
	 * @return the converted number
	 * @throws IllegalArgumentException
	 *             if the target class is not supported (i.e. not a standard
	 *             Number subclass as included in the JDK)
	 * @see java.lang.Byte
	 * @see java.lang.Short
	 * @see java.lang.Integer
	 * @see java.lang.Long
	 * @see java.math.BigInteger
	 * @see java.lang.Float
	 * @see java.lang.Double
	 * @see java.math.BigDecimal
	 */
	public static Number convertNumberToTargetClass(Number number, Class targetClass) throws IllegalArgumentException {

		AssertUtil.notNull(number, "Number must not be null");
		AssertUtil.notNull(targetClass, "Target class must not be null");

		if (targetClass.isInstance(number)) {
			return number;
		} else if (targetClass.equals(Byte.class)) {
			long value = number.longValue();
			if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
				raiseOverflowException(number, targetClass);
			}
			return new Byte(number.byteValue());
		} else if (targetClass.equals(Short.class)) {
			long value = number.longValue();
			if (value < Short.MIN_VALUE || value > Short.MAX_VALUE) {
				raiseOverflowException(number, targetClass);
			}
			return new Short(number.shortValue());
		} else if (targetClass.equals(Integer.class)) {
			long value = number.longValue();
			if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
				raiseOverflowException(number, targetClass);
			}
			return new Integer(number.intValue());
		} else if (targetClass.equals(Long.class)) {
			return new Long(number.longValue());
		} else if (targetClass.equals(Float.class)) {
			return new Float(number.floatValue());
		} else if (targetClass.equals(Double.class)) {
			return new Double(number.doubleValue());
		} else if (targetClass.equals(BigInteger.class)) {
			return BigInteger.valueOf(number.longValue());
		} else if (targetClass.equals(BigDecimal.class)) {
			// using BigDecimal(String) here, to avoid unpredictability of
			// BigDecimal(double)
			// (see BigDecimal javadoc for details)
			return new BigDecimal(number.toString());
		} else {
			throw new IllegalArgumentException("Could not convert number [" + number + "] of type ["
					+ number.getClass().getName() + "] to unknown target class [" + targetClass.getName() + "]");
		}
	}

	/**
	 * Raise an overflow exception for the given number and target class.
	 * 
	 * @param number
	 *            the number we tried to convert
	 * @param targetClass
	 *            the target class we tried to convert to
	 */
	private static void raiseOverflowException(Number number, Class targetClass) {
		throw new IllegalArgumentException("Could not convert number [" + number + "] of type ["
				+ number.getClass().getName() + "] to target class [" + targetClass.getName() + "]: overflow");
	}

	/**
	 * Parse the given text into a number instance of the given target class,
	 * using the corresponding <code>decode</code> / <code>valueOf</code>
	 * methods.
	 * <p>
	 * Trims the input <code>String</code> before attempting to parse the
	 * number. Supports numbers in hex format (with leading "0x", "0X" or "#")
	 * as well.
	 * 
	 * @param text
	 *            the text to convert
	 * @param targetClass
	 *            the target class to parse into
	 * @return the parsed number
	 * @throws IllegalArgumentException
	 *             if the target class is not supported (i.e. not a standard
	 *             Number subclass as included in the JDK)
	 * @see java.lang.Byte#decode
	 * @see java.lang.Short#decode
	 * @see java.lang.Integer#decode
	 * @see java.lang.Long#decode
	 * @see #decodeBigInteger(String)
	 * @see java.lang.Float#valueOf
	 * @see java.lang.Double#valueOf
	 * @see java.math.BigDecimal#BigDecimal(String)
	 */
	public static Number parseNumber(String text, Class targetClass) {
		AssertUtil.notNull(text, "Text must not be null");
		AssertUtil.notNull(targetClass, "Target class must not be null");

		String trimmed = text.trim();

		try {
			if (targetClass.equals(Byte.class)) {
				return (isHexNumber(trimmed) ? Byte.decode(trimmed) : Byte.valueOf(trimmed));
			} else if (targetClass.equals(Short.class)) {
				return (isHexNumber(trimmed) ? Short.decode(trimmed) : Short.valueOf(trimmed));
			} else if (targetClass.equals(Integer.class)) {
				return (isHexNumber(trimmed) ? Integer.decode(trimmed) : Integer.valueOf(trimmed));
			} else if (targetClass.equals(Long.class)) {
				return (isHexNumber(trimmed) ? Long.decode(trimmed) : Long.valueOf(trimmed));
			} else if (targetClass.equals(BigInteger.class)) {
				return (isHexNumber(trimmed) ? decodeBigInteger(trimmed) : new BigInteger(trimmed));
			} else if (targetClass.equals(Float.class)) {
				return Float.valueOf(trimmed);
			} else if (targetClass.equals(Double.class)) {
				return Double.valueOf(trimmed);
			} else if (targetClass.equals(BigDecimal.class) || targetClass.equals(Number.class)) {
				return new BigDecimal(trimmed);
			} else {
				throw new IllegalArgumentException(
						"Cannot convert String [" + text + "] to target class [" + targetClass.getName() + "]");
			}
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 对Integer类型进行进制转换
	 * @param text
	 * @param targetClass
	 * @param fromRadix
	 * @param toRadix
	 * @return
	 */
	public static String transRadix(String text, Class targetClass, int fromRadix, int toRadix) {
		AssertUtil.notNull(text, "Text must not be null");
		AssertUtil.notNull(targetClass, "Target class must not be null");

		String trimmed = text.trim();
		try {
			if (targetClass.equals(Byte.class)) {
				int num = Byte.valueOf(trimmed, fromRadix);
				StringBuilder sb = new StringBuilder();
				while (num != 0) {
					sb.append(num % toRadix);
					num = num / toRadix;
				}
				return sb.reverse().toString();
			} else if (targetClass.equals(Short.class)) {

				int num = Short.valueOf(trimmed, fromRadix);
				StringBuilder sb = new StringBuilder();
				while (num != 0) {
					sb.append(num % toRadix);
					num = num / toRadix;
				}
				return sb.reverse().toString();

			} else if (targetClass.equals(Integer.class)) {
				int num = Integer.valueOf(trimmed, fromRadix);
				StringBuilder sb = new StringBuilder();
				while (num != 0) {
					sb.append(num % toRadix);
					num = num / toRadix;
				}
				return sb.reverse().toString();
			} else {
				throw new IllegalArgumentException(
						"Cannot convert String [" + text + "] to target class [" + targetClass.getName() + "]");
			}

		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * Parse the given text into a number instance of the given target class,
	 * using the given NumberFormat. Trims the input <code>String</code> before
	 * attempting to parse the number.
	 * 
	 * @param text
	 *            the text to convert
	 * @param targetClass
	 *            the target class to parse into
	 * @param numberFormat
	 *            the NumberFormat to use for parsing (if <code>null</code>,
	 *            this method falls back to
	 *            <code>parseNumber(String, Class)</code>)
	 * @return the parsed number
	 * @throws IllegalArgumentException
	 *             if the target class is not supported (i.e. not a standard
	 *             Number subclass as included in the JDK)
	 * @see java.text.NumberFormat#parse
	 * @see #convertNumberToTargetClass
	 * @see #parseNumber(String, Class)
	 */
	public static Number parseNumber(String text, Class targetClass, NumberFormat numberFormat) {
		if (numberFormat != null) {
			AssertUtil.notNull(text, "Text must not be null");
			AssertUtil.notNull(targetClass, "Target class must not be null");
			try {
				Number number = numberFormat.parse(text.trim());
				return convertNumberToTargetClass(number, targetClass);
			} catch (ParseException ex) {
				IllegalArgumentException iae = new IllegalArgumentException(
						"Could not parse number: " + ex.getMessage());
				iae.initCause(ex);
				throw iae;
			}
		} else {
			return parseNumber(text, targetClass);
		}
	}

	/**
	 * Determine whether the given value String indicates a hex number, i.e.
	 * needs to be passed into <code>Integer.decode</code> instead of
	 * <code>Integer.valueOf</code> (etc).
	 */
	private static boolean isHexNumber(String value) {
		int index = (value.startsWith("-") ? 1 : 0);
		return (value.startsWith("0x", index) || value.startsWith("0X", index) || value.startsWith("#", index));
	}

	/**
	 * Decode a {@link java.math.BigInteger} from a {@link String} value.
	 * Supports decimal, hex and octal notation.
	 * 
	 * @see BigInteger#BigInteger(String, int)
	 */
	private static BigInteger decodeBigInteger(String value) {
		int radix = 10;
		int index = 0;
		boolean negative = false;

		// Handle minus sign, if present.
		if (value.startsWith("-")) {
			negative = true;
			index++;
		}

		// Handle radix specifier, if present.
		if (value.startsWith("0x", index) || value.startsWith("0X", index)) {
			index += 2;
			radix = 16;
		} else if (value.startsWith("#", index)) {
			index++;
			radix = 16;
		} else if (value.startsWith("0", index) && value.length() > 1 + index) {
			index++;
			radix = 8;
		}

		BigInteger result = new BigInteger(value.substring(index), radix);
		return (negative ? result.negate() : result);
	}

}
