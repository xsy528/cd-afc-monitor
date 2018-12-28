/**
 * iFrameWork 框架
 * 
 * @component     应用程序框架
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description   
 * @date          2009-4-2
 * @copyright     浙江浙大网新众合轨道交通工程有限公司 
 * @history       
 */
package com.insigma.commons.application;

import java.util.List;

public interface IUser {

	public String getUserID();

	public String getUserName();

	public String getPassWord();

	public List<Integer> getFunctionList();

	public boolean hasFunction(String functionId);

	public boolean hasModule(String moduleId);

	public boolean hasSystem(String sid);
}
