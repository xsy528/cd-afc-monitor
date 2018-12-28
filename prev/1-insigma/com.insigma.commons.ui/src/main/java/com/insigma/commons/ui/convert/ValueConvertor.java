/* 
 * 日期：2010-9-21
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.convert;

import org.eclipse.swt.graphics.Image;

import com.insigma.commons.ui.anotation.View;

/**
 * <b>UI显示和Form Bean的转换器</b><br>
 * 
 * <pre>
 * 举例说明：对应“性别”，假设后台用1表示男,0表示女.
 *       数据库查询出来是"1/0",但我们需要显示"男/女"
 *       另一方面，request后需要把“男/女”转换成"1/0"
 * </pre>
 * 
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public interface ValueConvertor {
	/**
	 * 把bean转换成UI显示
	 * 
	 * @param object
	 * @param view
	 * @return
	 */
	public String convertToUI(Object object, View view);

	public Image convertToImage(Object object, View view);
}
