/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.manager;

public class FunctionManager {

	private static FunctionValidator validator;

	public static boolean validate(int functionid) {
		if (validator != null) {
			return validator.check(functionid);
		}
		return true;
	}

	public static boolean validateMenu(String functionid) {
		if (validator != null) {
			return validator.checkMenu(functionid);
		}
		return true;
	}

	public static FunctionValidator getValidator() {
		return validator;
	}

	public static void setValidator(FunctionValidator validator) {
		FunctionManager.validator = validator;
	}
}
