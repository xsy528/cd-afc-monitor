package com.insigma.commons.ui.style;

public class StyleManager {

	private static Style style;

	public static Style getStyle() {
		return style;
	}

	public static void setStyle(Style style) {
		StyleManager.style = style;
	}

	public static TableStyle getTableStyle(String name) {
		for (TableStyle t : style.getTable()) {
			if (t.getName().equals(name)) {
				return t;
			}
		}
		return null;
	}
}
