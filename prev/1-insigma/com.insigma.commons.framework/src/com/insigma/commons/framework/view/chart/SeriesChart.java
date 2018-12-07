package com.insigma.commons.framework.view.chart;

import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.jfree.ui.RectangleInsets;

import com.insigma.commons.framework.view.chart.data.SeriesChartData;
import com.insigma.commons.framework.view.chart.data.SeriesChartData.SeriesData;
import com.insigma.commons.util.lang.DateTimeUtil;

/**
 * 曲线客流
 * 
 * @author 廖红自
 */
public class SeriesChart extends ChartComposite {

	private final Composite parentComp;

	private final String msg = "无数据";

	private String chartTitle = "客流曲线图";

	private String valueAxisLabel = "人次/指定时段";

	// 横坐标类型 1：时 2：日 3:月
	private int xaxisType = 1;

	private double[] valueOfPart;

	private Date startDate;

	private Date endDate;

	public SeriesChart(Composite parentComp) {
		super(parentComp, SWT.NONE, null, ChartComposite.DEFAULT_WIDTH, ChartComposite.DEFAULT_HEIGHT,
				ChartComposite.DEFAULT_MINIMUM_DRAW_WIDTH, ChartComposite.DEFAULT_MINIMUM_DRAW_HEIGHT,
				ChartComposite.DEFAULT_MAXIMUM_DRAW_WIDTH, ChartComposite.DEFAULT_MAXIMUM_DRAW_HEIGHT, true, false,
				true, false, false, true);

		setChart(createChart(createDataset(null)));
		this.parentComp = parentComp;
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		parentComp.layout();
	}

	public void refreshChart(SeriesChartData data) {
		JFreeChart chart = createChart(createDataset(data));
		chart.setBorderVisible(false);
		setChart(chart);
		forceRedraw();
		// setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		parentComp.layout();
	}

	/**
	 * Creates a sample dataset.
	 * 
	 * @param data
	 *            多个车站的连续时间点的数据.
	 * @return The dataset.
	 */
	private XYDataset createDataset(SeriesChartData data) {
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		if (data != null) {
			xaxisType = data.getXaxisType();
			startDate = data.getStartDate();
			endDate = data.getEndDate();
		}
		if (data == null || data.getSeriersItems() == null || data.getSeriersItems().isEmpty()) {
			valueOfPart = null;
			return dataset;
		}
		TimeSeries timeSeries = null;// new TimeSeries("", Minute.class);
		List<Boolean> includeParts = data.getShowRows();

		valueOfPart = new double[data.getPartNames().size()];

		Map<String, List<SeriesData>> seriersItems = data.getSeriersItems();

		// 遍历车站
		for (String seriesName : seriersItems.keySet()) {

			List<SeriesData> seriesDatas = seriersItems.get(seriesName);
			timeSeries = new TimeSeries(seriesName, Minute.class);
			// 遍历每个车站，将车站点分时数据生成TimeSeries
			if (seriesDatas == null || seriesDatas.size() <= 0) {
				dataset.addSeries(timeSeries);
				continue;
			}

			int pointIndex = 0;
			// 遍历该曲线上所有点的数据
			for (SeriesData timePoint : seriesDatas) {

				String str = DateTimeUtil.formatDateToString(timePoint.getDate(), "yyyyMMddHHmmss");
				int day = Integer.valueOf(str.substring(6, 8));
				int month = Integer.valueOf(str.substring(4, 6));
				int year = Integer.valueOf(str.substring(0, 4));
				int hour = Integer.valueOf(str.substring(8, 10));
				int minute = Integer.valueOf(str.substring(10, 12));

				long value = 0;
				int index = 0;
				for (long v : timePoint.getValueOfPart()) {
					// 每个点中包含的各项值，比如进站、出站，购票，充值，但不能包含合计
					if (includeParts == null || includeParts.size() < timePoint.getValueOfPart().length) {
						value = value + v;
					} else {
						if (includeParts.get(index)) {
							value = value + v;
						}
					}
					valueOfPart[index] = valueOfPart[index] + v;
					index++;
				}
				pointIndex++;
				timeSeries.addOrUpdate(new Minute(minute, hour, day, month, year), value);
			}
			if (timeSeries.getItemCount() > 0) {
				dataset.addSeries(timeSeries);
			}
		}

		return dataset;
	}

	/**
	 * Creates the demo chart.
	 * 
	 * @return The chart.
	 */
	@SuppressWarnings("deprecation")
	private JFreeChart createChart(XYDataset dataset) {
		JFreeChart chart = ChartFactory.createTimeSeriesChart(chartTitle, "", valueAxisLabel, dataset, true, true,
				false);
		chart.setBackgroundPaint(Color.white);
		chart.setBorderVisible(true);
		chart.setBorderPaint(Color.BLACK);
		chart.getTitle().setFont(new Font("宋体", Font.PLAIN, 20));
		Font labelFont = new Font("宋体", Font.PLAIN, 12);
		if (chart.getLegend() != null) {
			chart.getLegend().setItemFont(labelFont);
		}
		// TextTitle subtitle = new TextTitle("Four datasets and four range axes.");客流[人次/5分钟]
		// chart.addSubtitle(subtitle);
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.lightGray);
		plot.setRangeGridlinePaint(Color.lightGray);
		plot.setNoDataMessage(msg);

		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);
		// plot.setDomainGridlinesVisible(true);
		// DateAxis axis = (DateAxis) plot.getd;
		plot.getDomainAxis().setLabelFont(labelFont);
		plot.getRangeAxis().setLabelFont(labelFont);

		NumberAxis na = ((NumberAxis) plot.getRangeAxis());
		na.setAutoRange(true);
		na.setAutoTickUnitSelection(true);
		// NumberTickUnit n = new NumberTickUnit(20000d,NumberFormat.getIntegerInstance());
		// na.setTickUnit(n);
		NumberFormat nf = NumberFormat.getIntegerInstance();
		na.setNumberFormatOverride(nf);

		DateAxis dateaxis = (DateAxis) plot.getDomainAxis();
		if (startDate != null && endDate != null) {
			dateaxis.setRange(startDate, endDate);
		}
		if (xaxisType == 2) {
			dateaxis.setDateFormatOverride(new SimpleDateFormat("dd日"));
			dateaxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 1));
		} else if (xaxisType == 3) {
			dateaxis.setDateFormatOverride(new SimpleDateFormat("MM月"));
			dateaxis.setTickUnit(new DateTickUnit(DateTickUnit.MONTH, 1));
		} else {
			dateaxis.setDateFormatOverride(new SimpleDateFormat("HH:mm"));
		}

		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.getRangeAxis().setFixedDimension(15.0);
		plot.setNoDataMessageFont(labelFont);
		XYItemRenderer renderer = plot.getRenderer();

		// LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		renderer.setSeriesPaint(0, Color.black);

		// 设置提示信息
		StandardXYToolTipGenerator tipGenerator = new StandardXYToolTipGenerator(
				StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
				NumberFormat.getInstance());
		renderer.setToolTipGenerator(tipGenerator);
		return chart;
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

	public double[] getValueOfPart() {
		return valueOfPart;
	}

	/**
	 * @return the xaxisType
	 */
	public int getXaxisType() {
		return xaxisType;
	}

	/**
	 * @param xaxisType
	 *            the xaxisType to set
	 */
	public void setXaxisType(int xaxisType) {
		this.xaxisType = xaxisType;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
