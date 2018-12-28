/* 
 * 日期：2014-12-19
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.config.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.Charset;

import com.insigma.commons.config.ConfigHelper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlUtil {
	public static Object readXML(String relativePath, Class... bean) {
		XStream xStream = new XStream(new DomDriver("utf-8"));
		xStream.autodetectAnnotations(true);
		if (bean.length > 0) {
			for (int i = 0; i < bean.length; i++) {
				int length = bean[i].getName().split("\\.").length;
				xStream.alias(bean[i].getName().split("\\.")[length - 1], bean[i]);
			}
		}
		InputStream inputStream = ConfigHelper.getUserResourceAsStream(relativePath);

		Object o = xStream.fromXML(inputStream);

		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return o;
	}

	public static void writeXML(Object o, String relativePath, Class... bean) throws IOException {
		XStream xStream = new XStream(new DomDriver("utf-8"));
		xStream.autodetectAnnotations(true);
		if (bean.length > 0) {
			for (int i = 0; i < bean.length; i++) {
				int length = bean[i].getName().split("\\.").length;
				xStream.alias(bean[i].getName().split("\\.")[length - 1], bean[i]);
			}
		}
		//		URL url = ClassLoader.getSystemResource(relativePath);
		//		File file = new File(url.getFile());
		File file = new File(relativePath);
		FileOutputStream fileOutputStream;

		try {
			fileOutputStream = new FileOutputStream(file);

			OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream, Charset.forName("UTF-8"));
			// xml文件头必须手工加上
			writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");

			xStream.toXML(o, writer);
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
