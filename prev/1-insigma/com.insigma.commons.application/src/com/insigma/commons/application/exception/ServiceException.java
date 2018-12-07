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
package com.insigma.commons.application.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 7289504370200175561L;

	public ServiceException(String msg) {
		super(msg);
	}
}
