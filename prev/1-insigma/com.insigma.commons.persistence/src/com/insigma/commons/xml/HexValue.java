package com.insigma.commons.xml;

public class HexValue {

	private int intValue;

	private int length = 4;

	public HexValue() {
	}

	public HexValue(int value, int length) {
		this.intValue = value;
		this.length = length;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public void setValue(String value) {
		if (value != null) {
			intValue = Integer.valueOf(value, 16);
		}
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String toString() {
		return Integer.toHexString(intValue);
	}

	public static String numberToHexString(int value, int length) {
		String str = Integer.toHexString(value);
		if (str.length() > length) {
			str = str.substring(str.length() - length);
		} else {
			String addStr = "";
			for (int i = 0; i < length - str.length(); i++) {
				addStr = addStr + "0";
			}
			str = addStr + str;
		}
		return str;
	}

	public static String numberToHexString(long value, int length) {
		String str = Long.toHexString(value);
		if (str.length() > length) {
			str = str.substring(str.length() - length);
		} else {
			String addStr = "";
			for (int i = 0; i < length - str.length(); i++) {
				addStr = addStr + "0";
			}
			str = addStr + str;
		}
		return str;
	}
}
