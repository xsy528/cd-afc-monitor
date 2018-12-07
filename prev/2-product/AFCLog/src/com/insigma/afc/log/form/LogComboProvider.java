/*
 * 日期：2010-10-9
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 *
 */
package com.insigma.afc.log.form;

import com.insigma.afc.constant.LogDefines;
import com.insigma.commons.ui.provider.IComboDataSource;

/**
 * Ticket #
 *
 * @author wangxinhao
 * @date 2011-7-6
 * @description
 */
public class LogComboProvider implements IComboDataSource<Short> {

	public String[] getText() {
		return LogDefines.logClassName;
	}

	public Short[] getValue() {
		return null;
	}

}
