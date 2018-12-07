/**
 * iFrameWork 框架
 * 
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description   
 * @date          2009-4-2
 * @copyright     浙江浙大网新众合轨道交通工程有限公司 
 * @history       
 */
package com.insigma.commons.spring;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.commons.application.Application;

public class ServiceWireHelper {

	protected Log logger = LogFactory.getLog(ServiceWireHelper.class);

	public void wire(Object data) {
		wire(data, data.getClass());
	}

	private void wire(Object data, Class<?> clz) {

		if (clz.getSuperclass() != Object.class) {
			wire(data, clz.getSuperclass());
		}

		Field[] fields = clz.getDeclaredFields();
		if (fields != null) {
			for (Field field : fields) {
				Autowire service = field.getAnnotation(Autowire.class);
				if (service != null) {
					try {
						Object object = Application.getClassBean(field.getType());
						field.setAccessible(true);
						field.set(data, object);
					} catch (Exception e) {
						logger.error("自动组装错误", e);
					}
				}
			}
		}
	}
}
