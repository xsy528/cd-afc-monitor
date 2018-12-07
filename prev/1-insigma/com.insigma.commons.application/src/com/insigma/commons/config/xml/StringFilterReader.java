/**
 * 
 */
package com.insigma.commons.config.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.CharBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author DLF
 *
 */
public class StringFilterReader extends StringReader {

	private Log logger = LogFactory.getLog(StringFilterReader.class);

	private StringReader stringReader;

	private final String patternString = "\\$\\{.*\\}";

	public StringFilterReader(File file) {
		super("");
		init(file);
	}

	public StringFilterReader(InputStream in) {
		super("");
		init(in);
	}

	private void init(InputStream in) {
		StringBuffer buffer = new StringBuffer();
		BufferedReader bufferedReader = null;
		InputStreamReader reader = null;
		String readLine = null;
		try {
			// in = new FileInputStream(file);
			reader = new InputStreamReader(in, "UTF-8");
			bufferedReader = new BufferedReader(reader);
			while ((readLine = bufferedReader.readLine()) != null) {
				String filterset = filterset(readLine);
				buffer.append(filterset);
			}
		} catch (IOException e) {
			logger.error("", e);
		} finally {
			if (null != bufferedReader) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
		stringReader = new StringReader(buffer.toString());
	}

	private void init(File file) {
		StringBuffer buffer = new StringBuffer();
		BufferedReader bufferedReader = null;
		FileInputStream in = null;
		InputStreamReader reader = null;
		String readLine = null;
		try {
			in = new FileInputStream(file);
			reader = new InputStreamReader(in, "UTF-8");
			bufferedReader = new BufferedReader(reader);
			while ((readLine = bufferedReader.readLine()) != null) {
				String filterset = filterset(readLine);
				buffer.append(filterset);
			}
		} catch (IOException e) {
			logger.error("", e);
		} finally {
			if (null != bufferedReader) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
		stringReader = new StringReader(buffer.toString());
	}

	/**
	 * 如果找不到变量名用变量名替换
	 * 
	 * @param filterString
	 * @return
	 */
	protected String filtersetWithNONEReplace(String filterString) {
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(filterString);
		while (matcher.find()) {
			filterString = replace(filterString);
			matcher = pattern.matcher(filterString);
		}
		return filterString;
	}

	/**
	 * 如果找不到变量不做任何替换
	 * 
	 * @param filterString
	 * @return
	 */
	protected String filterset(String filterString) {
		return replace(filterString, 0);
	}

	private String replace(String filterString, int fromIndex) {
		int start = filterString.indexOf("${", fromIndex);
		int end = filterString.indexOf("}", fromIndex);
		fromIndex = end + 1;
		if (start != -1 && end != -1) {
			String key = filterString.substring(start + 2, end);
			String replaceStr = filterString.substring(start, end + 1);
			logger.debug("start: " + start + " end: " + end + " Key: " + key + " replaceStr: " + replaceStr);
			String valueByKey = getValueByKey(key);
			logger.debug("value: " + valueByKey);
			if (valueByKey != null) {
				fromIndex = start + valueByKey.length();
				filterString = filterString.replace(replaceStr, valueByKey);
			}
			return replace(filterString, fromIndex);
		}
		return filterString;
	}

	private String replace(String filterString) {
		int start = filterString.lastIndexOf("${");
		int end = filterString.lastIndexOf("}");
		String key = filterString.substring(start + 2, end);
		String replaceStr = filterString.substring(start, end + 1);
		logger.debug("start: " + start + " end: " + end + " Key: " + key + " replaceStr: " + replaceStr);
		String valueByKey = getValueByKey(key);
		if (valueByKey == null) {
			valueByKey = key;
		}
		return filterString.replace(replaceStr, valueByKey);
	}

	private String getValueByKey(String key) {
		String property = System.getProperty(key);
		if (null == property) {
			property = System.getenv(key);
		}
		if (null == property) {
			logger.warn("环境变量中不存在变量：" + key + "的值，请检查是否已加载配置");
			return null;
		}
		return property;
	}

	@Override
	public void close() {
		stringReader.close();
	}

	@Override
	public void mark(int readAheadLimit) throws IOException {
		stringReader.mark(readAheadLimit);
	}

	@Override
	public boolean markSupported() {
		return stringReader.markSupported();
	}

	@Override
	public int read() throws IOException {
		return stringReader.read();
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		return stringReader.read(cbuf, off, len);
	}

	@Override
	public boolean ready() throws IOException {
		return stringReader.ready();
	}

	@Override
	public void reset() throws IOException {
		stringReader.reset();
	}

	@Override
	public long skip(long ns) throws IOException {
		return stringReader.skip(ns);
	}

	@Override
	public int read(char[] cbuf) throws IOException {
		return stringReader.read(cbuf);
	}

	@Override
	public int read(CharBuffer target) throws IOException {
		return stringReader.read(target);
	}

}
