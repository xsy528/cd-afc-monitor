/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.control;

import org.eclipse.swt.graphics.Image;

public interface IStatusBar {

	public void setText(String text);

	public void setText(int Index, String text);

	public void setImage(int index, Image image);

	public void showProgress(String text, boolean value);

}
