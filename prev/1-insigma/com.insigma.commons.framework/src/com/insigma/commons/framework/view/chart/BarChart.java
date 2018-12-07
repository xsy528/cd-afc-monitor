/*
 * $Source: $
 * $Revision: $
 * $Date: $
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.framework.view.chart;

import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.experimental.chart.swt.ChartComposite;

import com.insigma.commons.framework.view.chart.data.BarChartData;
import com.insigma.commons.framework.view.chart.data.BarChartData.ColumnData;

/**
 * 车站客流对比图(柱状图)
 *
 * @author 廖红自
 */
public class BarChart extends ChartComposite {

	private static final Log logger = LogFactory.getLog(BarChart.class);

	private final Composite parentComp;

	private int columns = 12;

	private final String msg = "无数据";

	private String chartTitle = "柱状图";

	private String valueAxisLabel = "人次/指定时段";

	private Number[] valueOfRows;

	// 默认最多支持8种颜色
	private Color[] colors = new Color[] { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.PINK, Color.CYAN,
			new Color(64, 128, 128), Color.ORANGE };

	private List<Boolean> showRows;

	private int rows = 0;

	/**
	 * 显示每个柱的数值，并修改该数值的字体属性
	 */
	private boolean barLableShow;

	public BarChart(Composite composite) {
		super(composite, SWT.NONE, null, ChartComposite.DEFAULT_WIDTH, ChartComposite.DEFAULT_HEIGHT,
				ChartComposite.DEFAULT_MINIMUM_DRAW_WIDTH, ChartComposite.DEFAULT_MINIMUM_DRAW_HEIGHT,
				ChartComposite.DEFAULT_MAXIMUM_DRAW_WIDTH, ChartComposite.DEFAULT_MAXIMUM_DRAW_HEIGHT, true, false,
				true, false, false, true);
		setChart(createChart(createDataset(null)));
		this.parentComp = composite;
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		parentComp.layout();
	}

	/**
	 * 刷新客流
	 *
	 * @param data
	 *            图表数据
	 */
	public void refreshChart(BarChartData data) {
		JFreeChart chart = createChart(createDataset(data));
		setChart(chart);
		forceRedraw();
		parentComp.layout();
	}

	/**
	 * 数据list中的Object[]格式是：Object[0]=列名，Object[i]=该列中各个柱状的值，包括：<br>
	 * 进站、出站、购票、充值
	 *
	 * @param data
	 *            图标数据
	 * @return
	 */
	private CategoryDataset createDataset(BarChartData data) {
		// create the dataset...
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		if (data == null || data.getColumnItems() == null || data.getColumnItems().isEmpty()) {
			logger.warn("柱状图数据为空");
			columns = 0;
			valueOfRows = null;
			return dataset;
		}

		barLableShow = data.isBarLableShow();

		columns = data.getColumnItems().size();

		showRows = data.getShowRows();
		List<String> rowNames = data.getRowNames();
		rows = rowNames.size();
		// lable 上需要显示的客流数值
		valueOfRows = new Number[rows];
		for (ColumnData columnData : data.getColumnItems()) {

			for (int index = 0; index < rows; index++) {

				String rowName = rowNames.get(index);
				Number value = 0.0;
				if (index < columnData.getValueOfRows().size()) {
					value = columnData.getValueOfRows().get(index);
					if (valueOfRows[index] == null) {
						valueOfRows[index] = value.doubleValue();
					} else {
						valueOfRows[index] = valueOfRows[index].doubleValue() + value.doubleValue();
					}
				}
				// 判断是否显示列中的各个行
				boolean isShow = true;
				if (showRows != null && showRows.size() >= rows) {
					if (showRows.get(index) != null) {
						isShow = showRows.get(index);
					}
				}

				if (isShow) {
					dataset.addValue(value, rowName, columnData.getColumnName());
				}

			}

		}
		return dataset;
	}

	private JFreeChart createChart(CategoryDataset dataset) {
		/**
		 * String title, String categoryAxisLabel, String valueAxisLabel, CategoryDataset dataset,
		 * PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls
		 */

		JFreeChart chart = ChartFactory.createBarChart(chartTitle, // chart
				// title
				"", // domain axis label客流[人次/指定时段]
				valueAxisLabel, // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips?
				false // URLs?
		);
		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		// set the background color for the chart...
		chart.setBackgroundPaint(Color.white);
		chart.getTitle().setFont(new Font("宋体", Font.PLAIN, 20));

		// get a reference to the plot for further customisation...
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setDomainGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.white);
		plot.setNoDataMessage(msg);

		Font labelFont = new Font("宋体", Font.PLAIN, 12);
		// x轴设置
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLabelFont(labelFont);// 轴标题
		domainAxis.setTickLabelFont(labelFont);// 轴数值
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
		// 设置距离图片左端距离
		domainAxis.setLowerMargin(0.01);
		// 设置距离图片右端距离
		// domainAxis.setUpperMargin(0.1);
		domainAxis.setLabelFont(labelFont);
		domainAxis.setMaximumCategoryLabelWidthRatio(0.6f);// 横轴上的 Lable 是否完整显示
		plot.setDomainAxis(domainAxis);
		plot.setNoDataMessageFont(labelFont);
		// Y轴设置
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setLabelFont(labelFont);
		rangeAxis.setTickLabelFont(labelFont);
		// 设置最高的一个 Item 与图片顶端的距离
		// rangeAxis.setUpperMargin(0.05);
		// 设置最低的一个 Item 与图片底端的距离
		// rangeAxis.setLowerMargin(0.3);
		plot.setRangeAxis(rangeAxis);

		// BarRenderer3D renderer = new BarRenderer3D();
		BarRenderer renderer = new BarRenderer();
		// 设置柱子边框颜色
		// renderer.setBaseOutlinePaint(Color.BLACK);
		// 设置 柱子 的颜色
		// renderer.setWallPaint(Color.gray);

		// 显示每个柱的数值，并修改该数值的字体属性
		// renderer.setSeriesItemLabelGenerator(0,
		// new StandardCategoryItemLabelGenerator());
		// renderer.setSeriesItemLabelFont(0, labelFont);
		// renderer.setSeriesItemLabelsVisible(0, true);
		if (barLableShow) {
			renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setItemLabelFont(labelFont);
			renderer.setItemLabelsVisible(true);
		}

		// 设置 柱子 的颜色
		setSerierPain(renderer);

		// 设置每种水果代表的柱的 Outline 颜色
		renderer.setSeriesOutlinePaint(0, Color.BLACK);
		renderer.setSeriesOutlinePaint(1, Color.BLACK);
		renderer.setSeriesOutlinePaint(2, Color.BLACK);
		renderer.setSeriesOutlinePaint(3, Color.BLACK);
		renderer.setSeriesOutlinePaint(4, Color.BLACK);
		// renderer.setBaseItemLabelFont(lableFont);
		// 设置每个地区所包含的平行柱的之间距离 即组内柱子间隔为组宽的10%
		renderer.setItemMargin(0.0001);

		renderer.setIncludeBaseInRange(true);
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		// renderer.setBaseItemLabelsVisible(true);
		renderer.setBaseItemLabelFont(new Font("宋体", Font.PLAIN, 10));
		plot.setRenderer(renderer);
		// OPTIONAL CUSTOMISATION COMPLETED.

		// 设置chart的宽度，以达到美观
		//        int barChartWidth = 1068;
		if (rows * columns != 0 && rows * columns < 5) {
			if (rows * columns <= 2) {
				// 设置距离图片左端距离
				domainAxis.setLowerMargin(0.3);
				// 设置距离图片右端距离
				domainAxis.setUpperMargin(0.3);
				renderer.setItemMargin(0.1);
			} else {
				renderer.setItemMargin(0.3);
			}
		}
		//        else if (columns > baseCount) {
		//            barChartWidth = 1068 + (columns - baseCount) * 65;
		//        }
		//         int screenHeight =
		//         Toolkit.getDefaultToolkit().getScreenSize().height;
		//         parentComp.setSize(barChartWidth, screenHeight - 300);
		return chart;
	}

	private void setSerierPain(BarRenderer renderer) {
		// 进站，出站，购票，充值，合计客流是否显示参数
		if (showRows == null || showRows.isEmpty()) {
			for (int i = 0; i < rows; i++) {
				// 设置每种柱的颜色
				if (colors != null && i < colors.length && colors[i] != null) {
					renderer.setSeriesPaint(i, colors[i]);
				}
				renderer.setSeriesToolTipGenerator(i,
						new StandardCategoryToolTipGenerator("({0}, {1})={2}", NumberFormat.getInstance()));
			}
		} else {
			int painIndex = 0;
			int colorIndex = 0;
			for (boolean show : showRows) {
				if (show) {
					// 设置每种柱的颜色
					if (colors != null && colorIndex < colors.length && colors[colorIndex] != null) {
						renderer.setSeriesPaint(painIndex, colors[colorIndex]);
					}
					renderer.setSeriesToolTipGenerator(painIndex,
							new StandardCategoryToolTipGenerator("({0}, {1})={2}", NumberFormat.getInstance()));
					painIndex++;
				}
				colorIndex++;
			}
		}
	}

	public String getChartTitle() {
		return chartTitle;
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}

	public String getValueAxisLabel() {
		return valueAxisLabel;
	}

	public void setValueAxisLabel(String valueAxisLabel) {
		this.valueAxisLabel = valueAxisLabel;
	}

	public Color[] getColors() {
		return colors;
	}

	public void setColors(Color[] colors) {
		this.colors = colors;
	}

	public Number[] getValueOfRows() {
		return valueOfRows;
	}
}
