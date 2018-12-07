package com.insigma.commons.database;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class PatternFactory {

	static Map<String, Pattern> patternMap = new HashMap<String, Pattern>();

	public static Pattern getPattern(String regExp) {
		Pattern pattern = null;
		if (patternMap.containsKey(regExp)) {
			pattern = patternMap.get(regExp);
		} else {
			pattern = Pattern.compile(regExp);
			patternMap.put(regExp, pattern);
		}
		return pattern;
	}

}
