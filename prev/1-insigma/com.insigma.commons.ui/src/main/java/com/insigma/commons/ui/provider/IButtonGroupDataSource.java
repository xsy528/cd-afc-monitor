package com.insigma.commons.ui.provider;

/**
 * ButtonGroup数据提供接口
 * @author DLF
 *
 */
public interface IButtonGroupDataSource {
	/**
	 * @return
	 * 标签文字数组
	 */
	String[] getLabels();

	/**
	 * @return
	 * 值数组
	 */
	Object[] getValues();

	/**
	 * @return
	 * 默认值
	 */
	int getDefault();
}