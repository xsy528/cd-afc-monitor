/* 
 * 日期：2009-8-6
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.config.xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

/**
 * Ticket:
 * 
 * @author DingLuofeng
 */
public class INIConfigFile {

	private static Logger logger = Logger.getLogger(INIConfigFile.class);

	private Map<String, Map<String, String>> sectionMap = new HashMap<String, Map<String, String>>();

	public static final String DEFAULT_SECTION = "default";;

	public static void main(String[] args) throws IOException {
		INIConfigFile configFile = new INIConfigFile();
		configFile.load(new File("D:/test.ini"));
		// String str = readINI(new File("D:/test.ini"), "section", "key");
		// System.out.println(str);
		// writeINI(new File("D:/test.ini"), "test", "key2", "value");

		// Map<String, String> map = configFile.getSectionConfig(new File("D:/aaa.ini"), "test1");
		Map<String, String> map = configFile.getSectionValues("section");
		for (Entry<String, String> entry : map.entrySet()) {
			System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
		}
	}

	public String getValue(String key) {

		Map<String, String> map = sectionMap.get(DEFAULT_SECTION);
		if (map == null)
			return null;
		else {
			return map.get(key);
		}
	}

	public String getValue(String section, String key) {
		Map<String, String> map = sectionMap.get(section);
		if (map == null)
			return null;
		else {
			return map.get(key);
		}
	}

	public Map<String, String> getSectionValues(String section) {
		Map<String, String> sectionValues = sectionMap.get(section);
		if (sectionValues == null) {
			logger.warn("不存在section：" + section + "的值,返回空的结果集。");
			return new HashMap<String, String>();
		}
		return sectionValues;
	}

	public void load(InputStream in) {
		String strLine = null;
		String fileSection = DEFAULT_SECTION;
		sectionMap.put(fileSection, new HashMap<String, String>());
		BufferedReader bufferedReader = null;
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(in, "UTF-8");
			bufferedReader = new BufferedReader(reader);
			while ((strLine = bufferedReader.readLine()) != null) {
				strLine = strLine.trim();
				int startIndex = strLine.indexOf("[", 0);
				int endIndex = strLine.indexOf("]", 0);
				boolean sectionMark = (startIndex != -1 && endIndex != -1 && endIndex > startIndex);
				if (strLine.startsWith("#")) {
					continue;
				} else if (sectionMark) {
					fileSection = strLine.substring(startIndex + 1, endIndex);
					sectionMap.put(fileSection, new HashMap<String, String>());
					continue;
				}

				String[] keyValues = strLine.split("=");
				String key = keyValues[0];
				if (keyValues.length == 1) {
					sectionMap.get(fileSection).put(key, "");
				} else if (keyValues.length == 2) {
					sectionMap.get(fileSection).put(key, keyValues[1]);
				} else {
					if (keyValues[0].trim().equalsIgnoreCase(key)) {
						String value = strLine.substring(strLine.indexOf("=") + 1).trim();
						sectionMap.get(fileSection).put(key, value);
					}
				}
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
	}

	public void load(File file) {
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			logger.error("", e);
		}
		load(in);
	}

	public static String readINI(File file, String key) throws IOException {
		return readINI(file, DEFAULT_SECTION, key);
	}

	/**
	 * 从INI配置文件中读取变量的值
	 * 
	 * @param file
	 *            配置文件的路径
	 * @param section
	 *            要获取的变量所在段名称
	 * @param key
	 *            要获取的变量名称
	 * @return 变量的值
	 * @throws IOException
	 *             抛出文件操作可能出现的IO异常
	 */
	public static String readINI(File file, String section, String key) throws IOException {
		String strLine = null;
		String fileSection = null;
		boolean inSection = false;
		BufferedReader bufferedReader = null;
		FileInputStream in = null;
		InputStreamReader reader = null;
		try {
			in = new FileInputStream(file);
			reader = new InputStreamReader(in, "UTF-8");
			bufferedReader = new BufferedReader(reader);
			while ((strLine = bufferedReader.readLine()) != null) {
				// 判断是否还在一个section内
				int startIndex = strLine.indexOf("[");
				int endIndex = strLine.indexOf("]");
				boolean sectionMark = (startIndex != -1 && endIndex != -1 && endIndex > startIndex);
				if (strLine.startsWith("#") && !sectionMark) {
					continue;
				} else if (sectionMark) {
					fileSection = strLine.substring(startIndex + 1, endIndex);
					if (fileSection.equalsIgnoreCase(section)) {
						inSection = true;
					} else {
						inSection = false;
					}
					continue;
				}
				// 如果在Section内
				if (inSection) {
					String[] keyValues = strLine.split("=");
					if (keyValues.length == 1) {
						if (keyValues[0].trim().equalsIgnoreCase(key)) {
							return "";
						}
					} else if (keyValues.length == 2) {
						if (keyValues[0].trim().equalsIgnoreCase(key)) {
							return keyValues[1].trim();
						}
					} else {
						if (keyValues[0].trim().equalsIgnoreCase(key)) {
							return strLine.substring(strLine.indexOf("=") + 1).trim();
						}
					}
				}

			}
		} catch (FileNotFoundException e) {
			logger.error("文件：" + file.getName() + " 缺失。");
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (in != null) {
				in.close();
			}
			if (reader != null) {
				reader.close();
			}
		}

		return null;
	}

	/**
	 * 获取一个Section中keys和values的Map对象
	 * 
	 * @param file
	 * @param section
	 * @return
	 * @throws IOException
	 */
	@Deprecated
	public static Map<String, String> getSectionConfig(File file, String section) throws IOException {
		HashMap<String, String> keyMap = new HashMap<String, String>();

		String strLine = null;
		String fileSection = null;
		boolean inSection = false;
		BufferedReader bufferedReader = null;
		FileInputStream in = null;
		InputStreamReader reader = null;
		try {
			in = new FileInputStream(file);
			reader = new InputStreamReader(in, "UTF-8");
			bufferedReader = new BufferedReader(reader);
			while ((strLine = bufferedReader.readLine()) != null) {
				// 判断是否还在一个section内
				int startIndex = strLine.indexOf("[");
				int endIndex = strLine.indexOf("]");
				boolean sectionMark = (startIndex != -1 && endIndex != -1 && endIndex > startIndex);
				if (strLine.startsWith("#") && !sectionMark) {
					continue;
				} else if (sectionMark) {
					// if (strLine.startsWith("#")) {
					// continue;
					// } else if (strLine.startsWith("[") && strLine.endsWith("]")) {
					fileSection = strLine.substring(startIndex + 1, endIndex);
					if (fileSection.equalsIgnoreCase(section)) {
						inSection = true;
					} else {
						inSection = false;
					}
					continue;
				}
				// 如果在Section内
				if (inSection) {
					String[] keyValues = strLine.split("=");
					if (keyValues.length == 1) {
						keyMap.put(keyValues[0], "");
					} else if (keyValues.length == 2) {
						keyMap.put(keyValues[0], keyValues[1]);
					} else {
						keyMap.put(keyValues[0], strLine.substring(strLine.indexOf("=") + 1).trim());
					}
				}

			}
		} catch (FileNotFoundException e) {
			logger.error("文件：" + file.getName() + " 缺失。");
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (in != null) {
				in.close();
			}
			if (reader != null) {
				reader.close();
			}
		}

		return keyMap;
	}

	public static void writeINI(File file, String key, String value) throws IOException {
		writeINI(file, DEFAULT_SECTION, key, value);
	}

	/**
	 * 新建、修改INI配置文件
	 * 
	 * @param file
	 * @param section
	 * @param key
	 * @param value
	 * @throws IOException
	 */
	public static void writeINI(File file, String section, String key, String value) throws IOException {
		boolean modifyed = false;
		String fileContent = "";
		String lineSeparator = getLineSeparator();
		// INI文件不存在
		if (!file.exists()) {
			logger.warn("文件：" + file.getName() + " 不存在，创建新文件。");
			file.createNewFile();
			fileContent += "[" + section + "]" + lineSeparator + key + "=" + value;

			bufferedWrite(file, fileContent);
			return;
		}

		String strLine = "";
		String fileSection = null;
		boolean inSection = false;
		BufferedReader bufferedReader = null;
		FileInputStream in = null;
		InputStreamReader reader = null;
		try {
			in = new FileInputStream(file);
			reader = new InputStreamReader(in, "UTF-8");
			bufferedReader = new BufferedReader(reader);
			while (strLine != null) {
				strLine = bufferedReader.readLine();
				if (strLine == null) {
					if (!inSection && !modifyed) {
						fileContent += "[" + section + "]" + lineSeparator + key + "=" + value + lineSeparator;
					} else if (inSection && !modifyed) {
						fileContent += key + "=" + value + lineSeparator;
					}
					break;
				}
				// 判断是否还在一个section内
				int startIndex = strLine.indexOf("[");
				int endIndex = strLine.indexOf("]");
				boolean sectionMark = (startIndex != -1 && endIndex != -1 && endIndex > startIndex);
				if (strLine.startsWith("#") && !sectionMark) {
					continue;
				} else if (sectionMark) {
					fileSection = strLine.substring(startIndex + 1, endIndex);
					if (fileSection.equalsIgnoreCase(section)) {
						inSection = true;
					} else {
						if (inSection && !modifyed) {
							modifyed = true;
							fileContent += key + "=" + value + lineSeparator;
						}
						inSection = false;
					}
					fileContent += strLine + lineSeparator;
					continue;
				}
				// 如果在Section内
				if (inSection) {
					String[] keyValues = strLine.split("=");
					if (keyValues.length >= 1) {
						if (keyValues[0].trim().equalsIgnoreCase(key)) {
							modifyed = true;
							fileContent += key + "=" + value + lineSeparator;
						} else {
							fileContent += strLine + lineSeparator;
						}
					}
				} else {
					fileContent += strLine + lineSeparator;
				}

			}
			// bufferedReader.close();
			bufferedWrite(file, fileContent);
		} catch (FileNotFoundException e) {
			logger.error("文件：" + file.getName() + " 缺失。");
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (in != null) {
				in.close();
			}
			if (reader != null) {
				reader.close();
			}
		}
	}

	private static void bufferedWrite(File file, String fileContent) throws IOException {
		BufferedWriter bufferedWriter = null;
		FileOutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		try {
			outputStream = new FileOutputStream(file);
			outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
			bufferedWriter = new BufferedWriter(outputStreamWriter);
			bufferedWriter.write(fileContent);
			bufferedWriter.flush();
		} finally {
			if (bufferedWriter != null) {
				bufferedWriter.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}
		}
	}

	/**
	 * 获取换行符
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getLineSeparator() {
		return (String) java.security.AccessController
				.doPrivileged(new sun.security.action.GetPropertyAction("line.separator"));
	}

}
