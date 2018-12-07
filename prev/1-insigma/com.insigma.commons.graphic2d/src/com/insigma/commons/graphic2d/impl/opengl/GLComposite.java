/*
 * 项目: 图形监控组件
 * 版本: 1.0
 * 作者  姜旭锋
 * Email: jiangxufeng@zdwxjd.com

 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.graphic2d.impl.opengl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Composite;
import org.lwjgl.opengl.GL11;

import com.insigma.commons.graphic2d.event.IPaintListener;
import com.insigma.commons.ui.widgets.EnhanceComposite;

public class GLComposite extends EnhanceComposite {

	protected IPaintListener paintListener;

	protected GLCanvas glCanvas;

	protected GLScaledGraphics graphic;

	protected final int viewWidth = 1200;

	protected final int viewHeight = 1600;

	protected int width = 0;

	protected int height = 0;

	protected int quadratic;

	protected float scale = 1.0f;

	/**
	 * @param parent
	 * @param style
	 */
	public GLComposite(Composite parent, int style) {
		super(parent, style);

		GridData gridData = new GridData();
		gridData.heightHint = viewWidth;
		gridData.widthHint = viewHeight;
		gridData.verticalAlignment = GridData.BEGINNING;
		GLData data = new GLData();
		data.doubleBuffer = true;
		data.stencilSize = 8;
		glCanvas = new GLCanvas(this, SWT.NO_BACKGROUND, data);
		glCanvas.setLayout(new GridLayout());
		glCanvas.setLayoutData(gridData);
		glCanvas.setSize(this.width, this.height); // needed for windows
		gridData = new GridData();
		gridData.verticalAlignment = GridData.BEGINNING;

		graphic = new GLScaledGraphics();

		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // 为色彩缓冲区指定用于清除的值

		glCanvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent arg0) {
				glCanvas.setCurrent();
				height = Math.max(viewHeight, 1);
				graphic.setViewPort(viewWidth, height);
				paintListener.paintGraphic(graphic);
				glCanvas.swapBuffers();
			}
		});

	}

	public void renderScene() { // 反复刷新图象
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // 用当前值清除缓冲区
		GL11.glLoadIdentity(); // 用恒等矩阵替换当前矩阵
		GL11.glTranslatef(0.0f, 0.0f, -10.0f); // 将变换矩阵与当前矩阵相乘
		GL11.glPushName(0); // 名字堆栈的压入和弹出操作
		GL11.glScalef(scale, scale, 1);
	}

	public IPaintListener getPaintListener() {
		return paintListener;
	}

	public void setPaintListener(IPaintListener paintListener) {
		this.paintListener = paintListener;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
