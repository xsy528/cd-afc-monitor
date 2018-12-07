/* 
 * 日期：2016-7-7
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.layout;

import org.eclipse.swt.layout.GridLayout;

public class LayoutFactory {

	public static enum LayoutType {
		NO_BORDER
	}

	private static LayoutFactory instance = new LayoutFactory();

	private LayoutFactory() {

	}

	public static LayoutFactory getInstance() {
		return instance;
	}

	public GridLayout create(LayoutType type) {
		if (LayoutType.NO_BORDER.equals(type)) {

			GridLayout gridLayout = new GridLayout();
			gridLayout.marginWidth = 0;
			gridLayout.marginHeight = 0;
			gridLayout.marginLeft = 0;
			gridLayout.marginTop = 0;
			gridLayout.horizontalSpacing = 0;
			gridLayout.verticalSpacing = 0;

			return gridLayout;
		}

		return null;
	}
}
