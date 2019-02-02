/* 
 * 日期：2011-1-5
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.dic.loader.annotation;

import com.insigma.commons.dic.loader.IDicClassListProvider;
import com.insigma.commons.dic.annotation.Dic;

import java.util.List;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class ClasspathAnnotationScanProvider implements IDicClassListProvider {

	@Override
	public List<Class<?>> getDicClassList() {
		return new AnnotationReader().findAnnotations(Dic.class);
	}

}
