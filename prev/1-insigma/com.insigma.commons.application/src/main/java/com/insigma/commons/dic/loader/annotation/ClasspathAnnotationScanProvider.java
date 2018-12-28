/* 
 * 日期：2011-1-5
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.dic.loader.annotation;

import java.util.List;

import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.loader.IDicClassListProvider;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class ClasspathAnnotationScanProvider implements IDicClassListProvider {

	public List<Class<?>> getDicClassList() {
		AnnotationReader ar = new AnnotationReader(Dic.class);
		List<Class<?>> dicAnnos = ar.findAnnotations(Dic.class);
		return dicAnnos;
	}

}
