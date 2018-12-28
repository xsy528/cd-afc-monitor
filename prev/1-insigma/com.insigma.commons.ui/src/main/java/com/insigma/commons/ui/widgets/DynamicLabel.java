/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class DynamicLabel extends Label {

	protected String text;

	protected int pos = 0;

	protected boolean move;

	protected int width = 0;

	protected Color upColor;

	protected Color downColor;

	protected boolean ismouseDown;

	protected Refresh refresh = new Refresh();

	private class Refresh implements Runnable {
		public void run() {
			if (move && !isDisposed()) {
				pos -= 2;
				redraw();
				Display.getCurrent().timerExec(200, this);
			}
		}
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public DynamicLabel(Composite arg0, int arg1) {

		super(arg0, arg1);

		text = "";

		downColor = Display.getCurrent().getSystemColor(SWT.COLOR_RED);

		this.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent arg0) {
			}

			public void mouseDown(MouseEvent arg0) {
				ismouseDown = true;
			}

			public void mouseUp(MouseEvent arg0) {
				ismouseDown = false;
			}
		});

		this.addMouseTrackListener(new MouseTrackAdapter() {

			public void mouseEnter(MouseEvent arg0) {
				setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_HAND));
			}

			public void mouseExit(MouseEvent arg0) {
				setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
			}

		});

		this.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent arg0) {
				synchronized (text) {
					if (text != null) {
						GC gc = arg0.gc;

						if (width == 0) {
							for (int i = 0; i < text.length(); i++) {
								char c = text.charAt(i);
								width += gc.getAdvanceWidth(c);
							}
						}

						if (0 - pos > width) {
							pos = getSize().x;
						}

						gc.drawText(text, pos, 0, true);
					}
				}
			}
		});
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		synchronized (this.text) {
			this.text = text;
			pos = 0;
			width = 0;
			redraw();
		}
	}

	public boolean isMove() {
		return this.move;
	}

	public void setMove(boolean move) {
		this.move = move;
		if (move) {
			Display.getCurrent().syncExec(refresh);
		}
	}

	public Color getUpColor() {
		return upColor;
	}

	public void setUpColor(Color upColor) {
		this.upColor = upColor;
	}

	public Color getDownColor() {
		return downColor;
	}

	public void setDownColor(Color downColor) {
		this.downColor = downColor;
	}

}
