/* 
 * 日期：2011-2-12
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.config;

import java.io.InputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SearchTableReader {
	public static void read(String fileName) {
		InputStream fileContent = ClassLoader.getSystemResourceAsStream(fileName);
		if (fileContent != null) {
			XStream xstream = new XStream(new DomDriver());
			xstream.processAnnotations(SearchTableConfig.class);
			SearchTableConfig table = (SearchTableConfig) xstream.fromXML(fileContent);
			SearchTableManager.setTable(table);
		}
	}

}
