/* 
 * 日期：2010-9-21
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.convert;

import org.eclipse.swt.graphics.Image;

import com.insigma.commons.ui.anotation.View;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class DefaultConvertor implements ValueConvertor {

	public String convertToUI(Object value, View view) {
		String result = view.nullvalue();
		if (null != value) {
			result = value.toString();
			if (' ' != view.passwordEchoChar()) {
				result = result.replaceAll("[\\w]", "*");
			}

			String format = view.format();
			if (null != format && !"".equals(format)) {
				// TODO:临时解决方法
				if (!value.getClass().isArray()) {
					result = String.format(format, value);
				} else {
					if (value.getClass().getComponentType().isAssignableFrom(byte.class)) {
						result = "";
						byte[] objects = (byte[]) value;
						for (byte object : objects) {
							result += String.format(format, object) + " ";
						}
					}
				}
			}
			if (view.postfix() != null && !"".equals(view.postfix())) {
				result += " " + view.postfix();
			}

		}
		return result;

	}

	public Image convertToImage(Object object, View view) {
		return null;
	}

}
