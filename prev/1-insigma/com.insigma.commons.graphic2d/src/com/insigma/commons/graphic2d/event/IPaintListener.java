/*
 * 项目: 图形监控组件
 * 版本: 1.0
 * 作者  姜旭锋
 * Email: jiangxufeng@zdwxjd.com

 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.graphic2d.event;

import com.insigma.commons.graphic2d.impl.opengl.GLScaledGraphics;

public interface IPaintListener {

	public void paintGraphic(GLScaledGraphics g);

}
