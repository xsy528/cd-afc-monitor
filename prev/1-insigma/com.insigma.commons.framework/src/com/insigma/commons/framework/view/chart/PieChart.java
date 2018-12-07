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
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.experimental.chart.swt.ChartComposite;

import com.insigma.commons.framework.view.chart.data.PieChartData;
import com.insigma.commons.framework.view.chart.data.PieChartData.PieData;

/**
 * 车站客流对比图(饼图)
 * 
 * @author 廖红自
 */
public class PieChart extends ChartComposite {
	private static final Log log = LogFactory.getLog(BarChart.class);

	private final Composite parentComp;

	private String chartTitle = "客流饼图";

	private String msg = "无数据";

	private double[] valueOfPart;

	public PieChart(Composite composite) {
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
	 * @param list
	 *            list
	 * @return inOutInfo
	 */
	public void refreshChart(PieChartData data) {
		JFreeChart chart = createChart(createDataset(data));
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		setChart(chart);
		forceRedraw();
		//        setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		parentComp.layout();
	}

	private PieDataset createDataset(PieChartData data) {
		DefaultPieDataset dataset = new DefaultPieDataset(); // 注意是DefaultPieDataset

		if (data == null || data.getPieItems() == null || data.getPieItems().isEmpty()) {
			log.warn("饼图数据为空");
			valueOfPart = null;
			return dataset;
		}
		valueOfPart = new double[data.getPieItems().get(0).getValueOfPies().size()];
		List<Boolean> showRows = data.getShowRows();
		for (PieData item : data.getPieItems()) {
			String name = item.getPieName();
			// 比如包括进站、出站等等
			List<Double> valueOfPies = item.getValueOfPies();
			double value = 0;

			// 判断是否显示列中的各个行
			if (showRows != null && showRows.size() >= valueOfPies.size()) {
				for (int index = 0; index < valueOfPies.size(); index++) {
					if (showRows.get(index) != null && showRows.get(index)) {
						value = value + valueOfPies.get(index);
					}
					valueOfPart[index] = valueOfPart[index] + valueOfPies.get(index);
				}
			} else {
				int valueIndex = 0;
				// 默认包括每个块中各个项的值
				for (double d : valueOfPies) {
					value = value + d;
					valueOfPart[valueIndex] = valueOfPart[valueIndex] + valueOfPies.get(valueIndex);
					valueIndex++;
				}
			}
			if (value > 0) {
				dataset.setValue(name, value);
			}
		}
		return dataset;
	}

	private JFreeChart createChart(PieDataset dataset) {
		// create the chart...
		JFreeChart chart = ChartFactory.createPieChart(chartTitle, // chart title
				dataset, // data
				true, // legend?图例
				true, // tooltips?
				false // URLs?
		);
		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		// set the background color for the chart...
		chart.setBackgroundPaint(Color.white);
		chart.getTitle().setFont(new Font("宋体", Font.PLAIN, 20));
		Font labelFont = new Font("宋体", Font.PLAIN, 12);
		if (chart.getLegend() != null) {
			chart.getLegend().setItemFont(labelFont);
		}

		// get a reference to the plot for further customisation...
		PiePlot plot = (PiePlot) chart.getPlot();

		plot.setNoDataMessage(msg); // 没有数据的时候显示的内容
		plot.setToolTipGenerator(new StandardPieToolTipGenerator("{0} = {1}({2})"));// 设置工具提示
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1} ({2})"));// 设置标签，同工具提示
		plot.setLabelFont(labelFont);
		plot.setNoDataMessageFont(labelFont);
		// 显示每个柱的数值，并修改该数值的字体属性
		// renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		// renderer.setItemLabelFont(new Font("黑体", Font.PLAIN, 12));
		// renderer.setItemLabelsVisible(true);

		// OPTIONAL CUSTOMISATION COMPLETED.
		return chart;
	}

	public String getChartTitle() {
		return chartTitle;
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}

	public double[] getValueOfPart() {
		return valueOfPart;
	}
}
