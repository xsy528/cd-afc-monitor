/* 
 * $Source: $
 * $Revision: $ 
 * $Date: $
 * 
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.application;

import java.util.List;

import com.insigma.commons.application.IUser;

public class User implements IUser {

	private String userId;

	private String passWord;

	private String userName;

	private List<Integer> functionList;

	public User(String userId, String passWord) {
		this(userId, passWord, "匿名");
	}

	public User(String userId, String passWord, String userName) {
		this.userId = userId;
		this.passWord = passWord;
		this.userName = userName;
	}

	public List<Integer> getFunctionList() {
		return functionList;
	}

	public String getPassWord() {
		return this.passWord;
	}

	public String getUserID() {
		return this.userId;
	}

	public String getUserName() {
		if (userName == null) {
			return "匿名";
		}
		return this.userName;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public boolean hasFunction(String functionId) {
		if (functionList == null) {
			return true;
		}
		try {
			Integer fid = Integer.parseInt(functionId);
			return functionList.contains(fid);
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * @param functionList
	 *            the functionList to set
	 */
	public void setFunctionList(List<Integer> functionList) {
		this.functionList = functionList;
	}

	public boolean hasModule(String moduleId) {
		if (functionList == null) {
			return true;
		}
		try {
			for (Integer fun : functionList) {
				if (((fun / 100) + "").equals(moduleId)) {
					return true;
				}
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return false;
	}

	public boolean hasSystem(String sid) {
		if (functionList == null) {
			return true;
		}
		try {
			for (Integer fun : functionList) {
				if (((fun / 10000)) == Integer.valueOf(sid)) {
					return true;
				}
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return false;
	}
}
