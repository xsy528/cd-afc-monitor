/*
 * 项目: 监控运行图表组件
 * 版本: 1.0
 * 作者  姜旭锋
 * Email: jiangxufeng@zdwxjd.com

 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.graphic;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class TimerGraphic extends Canvas {

	private static final int PERCENTILE = 1;

	private PointQueue dataQueue;

	private long timeSpan = 60000;

	private int refreshInterval = 15000;

	private int grids = 20;

	private boolean showXGrid = true;

	private boolean showYGrid = true;

	private boolean showCoordinate = true;

	private boolean showMarginLine = true;

	private int yScale = 2;

	private int yMax = 20;

	private boolean autofit = true;

	private Color gridColor = getDisplay().getSystemColor(SWT.COLOR_CYAN);

	private Color defaultLineColor = getDisplay().getSystemColor(SWT.COLOR_GREEN);

	private Color backgroundColor = getDisplay().getSystemColor(SWT.COLOR_WHITE);

	/**
	 * margin line color
	 */
	private Color marginLineColor = getDisplay().getSystemColor(SWT.COLOR_GREEN);

	private Color[] lineColor = new Color[] { new Color(getDisplay(), new RGB(255, 110, 0)),
			new Color(getDisplay(), new RGB(203, 12, 41)), new Color(getDisplay(), new RGB(142, 12, 107)),
			new Color(getDisplay(), new RGB(93, 12, 200)) };

	private Rectangle[] listenerArea = new Rectangle[2];

	private int[] lineWidth;

	private int width;

	private long refreshTime;

	private int topMargin = 40;

	private int bottomMargin = 40;

	private int leftMargin = 60;

	private int rightMargin = 30;

	/**
	 * 左上角的title
	 */
	private String titleString;

	private int coordinationStyle = PERCENTILE;

	public String getTitleString() {
		return titleString;
	}

	public void setTitleString(String titleString) {
		this.titleString = titleString;
	}

	public TimerGraphic(Composite parent, int style) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		this.setBackground(backgroundColor);

		this.addPaintListener(new PaintListener() {

			public void paintControl(PaintEvent arg0) {

				if (dataQueue == null || dataQueue.getPointList().size() == 0) {
					return;
				}
				GC gc = arg0.gc;

				int width = getBounds().width;
				int height = getBounds().height;

				int graphicAreaWidth = width - leftMargin - rightMargin;

				int graphicAreaHeight = height - topMargin - bottomMargin;

				int gridgap = (int) timeSpan / grids;

				long gridx = refreshTime - refreshTime % gridgap;

				// draw margin line
				if (showMarginLine) {
					gc.setLineWidth(2);
					gc.setForeground(marginLineColor);
					gc.drawRectangle(0 + leftMargin, 0 + topMargin, width - leftMargin - rightMargin,
							height - topMargin - bottomMargin);
				}

				gc.setLineWidth(1);
				gc.setLineStyle(SWT.LINE_DASH);
				gc.setForeground(gridColor);

				// vertical grid line
				if (showXGrid) {
					while ((refreshTime - gridx) < timeSpan) {
						int xpos = width - (int) ((width * (refreshTime - gridx)) / timeSpan);
						if (xpos > leftMargin && xpos < width - rightMargin) {
							gc.drawLine(xpos, topMargin, xpos, height - bottomMargin);
						}
						gridx = gridx - gridgap;
					}
				}

				// horizontal grid line
				if (showYGrid) {
					int gap = (int) ((width * gridgap) / timeSpan);
					for (int i = 0; i < height - bottomMargin - topMargin; i += gap) {
						gc.drawLine(leftMargin, height - bottomMargin - i, width - rightMargin,
								height - bottomMargin - i);
					}
				}

				// draw coordinate

				drawCoordination(gc, width, height);

				gc.setLineStyle(SWT.LINE_SOLID);

				gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_DARK_MAGENTA));
				if (titleString != null) {
					gc.drawString(titleString, 20, 5);
				}

				// draw polyline
				drawPolyLine(gc, graphicAreaWidth, graphicAreaHeight);

				List<String> lineNames = dataQueue.getLineNames();
				if (lineNames == null || lineNames.isEmpty()) {
					return;
				}

				if (lineNames.size() != listenerArea.length) {
					listenerArea = new Rectangle[dataQueue.getLineNames().size()];
				}

				int gap = graphicAreaWidth / lineNames.size();
				int startX = leftMargin + gap / 2;

				int lineIndex = 0;
				for (String lineName : lineNames) {
					gc.setForeground(getLineColor(lineIndex));
					gc.setBackground(backgroundColor);
					gc.drawString(lineName, startX, topMargin + graphicAreaHeight + 10);

					int lableTextWidth = gc.textExtent(lineName).x;
					int lableTextHeight = gc.textExtent(lineName).y;
					gc.drawRectangle(startX + lableTextWidth + 5, topMargin + graphicAreaHeight + 10, 40,
							lableTextHeight);
					gc.setBackground(getLineColor(lineIndex));
					Rectangle showColorArea = new Rectangle(startX + lableTextWidth + 5,
							topMargin + graphicAreaHeight + 10, 40, lableTextHeight);
					gc.fillRectangle(showColorArea);
					listenerArea[lineIndex] = showColorArea;

					YPoints pois = dataQueue.getPointList().get(dataQueue.getPointList().size() - 1);
					gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
					int percent = pois.getY(lineIndex++) * 100 / getYMax();
					gc.drawString(String.valueOf(percent) + "%", startX + lableTextWidth + 5 + 40 + 5,
							topMargin + graphicAreaHeight + 10);

					startX += gap;
				}
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				super.mouseDoubleClick(e);
				int lineIndex = 0;
				for (Rectangle rec : listenerArea) {
					if (rec == null || e.x < rec.x || e.x > rec.x + rec.width || e.y < rec.y
							|| e.y > rec.y + rec.height) {
						lineIndex++;
						continue;
					}
					ColorDialog cd = new ColorDialog(getDisplay().getActiveShell());
					cd.setRGB(getLineColor(lineIndex).getRGB());
					RGB resetRGB = cd.open();
					if (resetRGB != null) {
						setLineColor(lineIndex, new Color(getDisplay(), resetRGB));
					}
					break;

				}

			}
		});
	}

	/**
	 * draw coordination of the rectangle
	 * 
	 * @param gc device to draw
	 * @param width width of the drawing area
	 * @param height height of the drawing area
	 */
	private void drawCoordination(GC gc, int width, int height) {
		if (showCoordinate) {
			gc.setLineStyle(SWT.LINE_SOLID);
			gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
			gc.drawLine(leftMargin, topMargin, leftMargin, height - bottomMargin);
			gc.drawLine(leftMargin, height - bottomMargin, width - rightMargin, height - bottomMargin);
		}
		if (coordinationStyle == PERCENTILE) {
			Point point100 = gc.stringExtent("100%");
			gc.drawString("100%", leftMargin - point100.x - 5, topMargin - point100.y / 2);
			Point point50 = gc.stringExtent("50%");
			gc.drawString("50%", leftMargin - point50.x - 5,
					topMargin + (height - bottomMargin - topMargin) / 2 - point50.y / 2);
			Point point0 = gc.stringExtent("0%");
			gc.drawString("0%", leftMargin - point0.x - 5, height - bottomMargin - point0.y / 2);
		}

	}

	private Color getLineColor(int lineIndex) {
		if (lineColor != null && lineIndex < lineColor.length && lineColor[lineIndex] != null) {
			return lineColor[lineIndex];
		}
		return defaultLineColor;
	}

	public PointQueue getDataQueue() {
		return dataQueue;
	}

	public void setDataQueue(PointQueue dataQueue) {
		this.dataQueue = dataQueue;
	}

	public Color[] getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color[] colors) {
		if (colors == null) {
			throw new NullPointerException("lineColor cannot be null");
		}
		Color[] colorsCopy = new Color[colors.length];
		for (int i = 0; i < colors.length; i++) {
			if (colors[i] != null) {
				colorsCopy[i] = new Color(colors[i].getDevice(), colors[i].getRGB());
			}
		}

		Color[] tempColors = this.lineColor;
		this.lineColor = colorsCopy;
		disposeColors(tempColors);
	}

	public int[] getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(int[] lineWidth) {
		this.lineWidth = lineWidth;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Color getGridColor() {
		return gridColor;
	}

	public void setGridColor(Color gridColor) {
		this.gridColor = gridColor;
	}

	public int getGrids() {
		return grids;
	}

	public void setGrids(int grids) {
		this.grids = grids;
	}

	public int getYScale() {
		return yScale;
	}

	public void setYScale(int scale) {
		yScale = scale;
	}

	public long getTimeSpan() {
		return timeSpan;
	}

	public void setTimeSpan(long timeSpan) {
		this.timeSpan = timeSpan;
	}

	public int getYMax() {
		return yMax;
	}

	public void setYMax(int max) {
		yMax = max;
	}

	public boolean isAutofit() {
		return autofit;
	}

	public void setAutofit(boolean autofit) {
		this.autofit = autofit;
	}

	public boolean isShowXGrid() {
		return showXGrid;
	}

	public void setShowXGrid(boolean showXGrid) {
		this.showXGrid = showXGrid;
	}

	public boolean isShowYGrid() {
		return showYGrid;
	}

	public void setShowYGrid(boolean showYGrid) {
		this.showYGrid = showYGrid;
	}

	public int getRefreshInterval() {
		return refreshInterval;
	}

	public boolean isShowMarginLine() {
		return showMarginLine;
	}

	public void setShowMarginLine(boolean showMarginLine) {
		this.showMarginLine = showMarginLine;
	}

	/**
	 * 刷新界面
	 * 
	 * @param imidiate
	 *            是否立即刷新
	 */
	public void refresh(boolean imidiate) {
		if (!isDisposed()) {
			long now = System.currentTimeMillis();
			if (!imidiate) {
				if ((now - refreshTime) < refreshInterval) {
					return;
				}
			}
			refreshTime = now;
			if (Thread.currentThread().getId() == getDisplay().getThread().getId()) {
				redraw();
			} else {
				getDisplay().asyncExec(new Runnable() {
					public void run() {
						redraw();
					}
				});
			}
		}
	}

	public void setRefreshInterval(int interval) {
		this.refreshInterval = interval;
		Display.getDefault().timerExec(refreshInterval, new Runnable() {
			public void run() {
				refresh(false);
				if (refreshInterval > 0) {
					Display.getDefault().timerExec(refreshInterval, this);
				}
			}
		});
	}

	/**
	 * draw the poly line in the graphic area
	 * 
	 * @param gc
	 *            the graphic device
	 * @param graphicAreaWidth
	 *            graphic area width
	 * @param graphicAreaHeight
	 *            graphic area height
	 */
	private void drawPolyLine(GC gc, int graphicAreaWidth, int graphicAreaHeight) {
		List<YPoints> points = dataQueue.getPointList();
		int pointcount = 0;
		for (int i = points.size() - 1; i >= 0; i--) {
			YPoints p = points.get(i);
			if (refreshTime - p.getXpos() > timeSpan) {
				pointcount++;
				break;
			}
			pointcount++;
		}
		pointcount -= 1;
		if (pointcount <= 0) {
			return;
		}

		for (int lineIndex = 0; lineIndex < dataQueue.getLines(); lineIndex++) {
			int[] pointsArray = new int[pointcount * 2];
			for (int i = 0; i < pointcount; i++) {
				YPoints point = points.get(points.size() - 1 - i);
				if (point != null) {
					pointsArray[i * 2] = leftMargin + graphicAreaWidth
							- (int) ((graphicAreaWidth * (refreshTime - point.getXpos())) / timeSpan);
					if (autofit) {
						pointsArray[i * 2 + 1] = topMargin + graphicAreaHeight
								- ((graphicAreaHeight * point.getY(lineIndex)) / yMax);
					} else {
						pointsArray[i * 2 + 1] = topMargin + (graphicAreaHeight - point.getY(lineIndex) * yScale);
					}
				}
			}
			if (lineWidth != null && lineWidth.length > 0) {
				if (lineIndex <= lineWidth.length - 1) {
					gc.setLineWidth(lineWidth[lineIndex]);
				} else {
					gc.setLineWidth(lineWidth[0]);
				}
			} else {
				gc.setLineWidth(1);
			}

			gc.setForeground(getLineColor(lineIndex));
			gc.drawPolyline(pointsArray);

		}
	}

	private void setLineColor(int lineIndex, Color color) {
		if (lineColor.length <= lineIndex) {
			Color[] colors = new Color[listenerArea.length];
			System.arraycopy(lineColor, 0, colors, 0, lineColor.length);
			lineColor = colors;
		}
		Color temp = lineColor[lineIndex];
		lineColor[lineIndex] = color;
		if (temp != null) {
			temp.dispose();
		}
	}

	@Override
	public void dispose() {
		disposeColors(this.lineColor);
		super.dispose();
	}

	private void disposeColors(Color[] colors) {
		for (Color color : colors) {
			if (color != null) {
				color.dispose();
			}
		}
	}

}
