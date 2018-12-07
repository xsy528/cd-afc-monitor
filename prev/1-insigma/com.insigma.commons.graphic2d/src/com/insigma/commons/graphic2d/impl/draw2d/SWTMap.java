/*
 * 项目: 图形监控组件
 * 版本: 1.0
 * 作者  姜旭锋
 * Email: jiangxufeng@zdwxjd.com

 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.graphic2d.impl.draw2d;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class SWTMap extends FigureCanvas {

	protected Point startMousePos = new Point();

	protected Point endMousePos = new Point();

	protected Point selectedFigurePos = new Point();

	protected double scale = 1;

	protected SWTFigure selectedFigure;

	private final SWTScalableLayeredPane mainPanel;

	protected boolean canSelect;

	protected boolean canMove;

	protected boolean multiSelection;

	public boolean isCanSelect() {
		return canSelect;
	}

	public void setCanSelect(boolean canSelect) {
		this.canSelect = canSelect;
	}

	public SWTMap(Composite parent, int style) {

		super(parent, style | SWT.NO_BACKGROUND | SWT.DOUBLE_BUFFERED);
		setLayout(new FillLayout());
		setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

		mainPanel = new SWTScalableLayeredPane();
		mainPanel.setLayoutManager(new XYLayout());
		mainPanel.setScale(1);
		setContents(mainPanel);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(org.eclipse.swt.events.MouseEvent arg0) {

				startMousePos.x = arg0.x;
				startMousePos.y = arg0.y;

				endMousePos.x = arg0.x;
				endMousePos.y = arg0.y;

				mainPanel.UnSelectAll();

				IFigure figure = mainPanel.findFigureAt(startMousePos.x, startMousePos.y);

				if (figure != null && figure instanceof SWTFigure) {
					multiSelection = false;

					selectedFigure = (SWTFigure) figure;
					selectedFigurePos.x = figure.getBounds().x;
					selectedFigurePos.y = figure.getBounds().y;
					selectedFigure.setSelected(true);

				} else {
					multiSelection = true;
					selectedFigure = null;
				}

			}

			@Override
			public void mouseUp(org.eclipse.swt.events.MouseEvent arg0) {

				if (selectedFigure != null) {
					selectedFigure.setAlpha(255);
				} else {
					multiSelection = false;
					if ((endMousePos.x - startMousePos.x) != 0 && (endMousePos.y - startMousePos.y) != 0) {
						Rectangle rect = new Rectangle(startMousePos.x, startMousePos.y,
								endMousePos.x - startMousePos.x, endMousePos.y - startMousePos.y);
						mainPanel.getSelection(rect);
					} else {
						mainPanel.UnSelectAll();
					}
					SWTMap.this.redraw();
				}
			}
		});

		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent arg0) {
				//				if (multiSelection) {
				//					GC gc = arg0.gc;
				//					gc.setLineWidth(1);
				//					gc.setForeground(Display.getDefault().getSystemColor(
				//							SWT.COLOR_GREEN));
				//					gc.drawRectangle(startMousePos.x, startMousePos.y,
				//							endMousePos.x - startMousePos.x, endMousePos.y
				//									- startMousePos.y);
				//				}
			}
		});

		addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(org.eclipse.swt.events.MouseEvent arg0) {

				if ((arg0.stateMask & 0x80000) != 0) {

					endMousePos.x = arg0.x;
					endMousePos.y = arg0.y;

					if (canMove && startMousePos.x != 0) {
						if (selectedFigure != null) {
							Point offset = new Point((arg0.x - startMousePos.x) / scale,
									(arg0.y - startMousePos.y) / scale);
							selectedFigure.setAlpha(150);
							selectedFigure.setBounds(
									new Rectangle(selectedFigurePos.x + offset.x, selectedFigurePos.y + offset.y,
											selectedFigure.getBounds().width, selectedFigure.getBounds().height));
						}
					}
				}
			}
		});
	}

	public void add(final SWTFigure figure) {
		mainPanel.add(figure);
	}

	public void setScale(double scale) {
		this.scale = scale;
		mainPanel.setScale(scale);
	}

	public double getScale() {
		return mainPanel.getScale();
	}

	public SWTFigure getSelectedFigure() {
		return selectedFigure;
	}

	public void setSelectedFigure(SWTFigure selectedFigure) {
		this.selectedFigure = selectedFigure;
	}

	public boolean isCanMove() {
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
		if (canMove) {
			canSelect = true;
		}
	}

	public void move(int x, int y) {
		for (int i = 0; i < mainPanel.getChildren().size(); i++) {
			IFigure child = (IFigure) mainPanel.getChildren().get(i);
			child.getBounds().x += x;
			child.getBounds().y += y;
		}
		mainPanel.repaint();
	}

}
