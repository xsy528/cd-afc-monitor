/* 
 * 日期：2010-11-3
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.application;

import java.util.List;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class AnonymousUser implements IUser {

	public String getUserID() {
		return "0";
	}

	public String getUserName() {
		return "AnonymousUser";
	}

	public String getPassWord() {
		return null;
	}

	public List<Integer> getFunctionList() {
		return null;
	}

	public boolean hasFunction(String functionId) {
		return true;
	}

	public boolean hasModule(String moduleId) {
		return true;
	}

	public boolean hasSystem(String sid) {
		return true;
	}

}
