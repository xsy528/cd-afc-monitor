package com.insigma.commons.framework.view.chart;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.framework.view.chart.data.MultiPieData;
import com.insigma.commons.ui.widgets.EnhanceComposite;

public class MultiPieChartComposite extends EnhanceComposite {

	// private List<PieChart> charts = new ArrayList<PieChart>();

	public MultiPieChartComposite(Composite parent, int style) {
		super(parent, style);
		this.setLayout(new GridLayout());
	}

	public void refreshChart(MultiPieData data) {
		this.clear();
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		setLayout(gridLayout);
		if (data != null) {
			for (int i = 0; i < data.getData().size(); i++) {
				PieChart pie = new PieChart(this);
				pie.setChartTitle(data.getPieChartNames().get(i));
				pie.setLayoutData(new GridData(GridData.FILL_BOTH));
				pie.refreshChart(data.getData().get(i));

			}
		}

	}
}
