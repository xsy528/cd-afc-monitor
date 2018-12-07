/**
 * 
 */
package com.insigma.commons.file.filter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author DLF
 *
 */
public class RegexFilenameFilter implements FilenameFilter {

	private String regexString;

	private int flags = Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;

	/**
	 * @param regexString
	 */
	public RegexFilenameFilter(String regexString) {
		this.regexString = regexString;
	}

	/**
	 * @param regexString
	 * @param flags eg: Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE|DOTALL and so on
	 */
	public RegexFilenameFilter(String regexString, int flags) {
		this.regexString = regexString;
		this.flags = flags;
	}

	/* (non-Javadoc)
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
	 */
	@Override
	public boolean accept(File dir, String name) {
		Pattern pattern = Pattern.compile(regexString, flags);
		Matcher matcher = pattern.matcher(name);
		return matcher.matches();
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public int getFlags() {
		return flags;
	}

}
