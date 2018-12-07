/**
 * iFrameWork 框架
 *
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description
 * @date          2009-4-2
 * @history
 */
package com.insigma.commons.framework.view.chart;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.function.chart.ChartFunction;
import com.insigma.commons.framework.function.chart.ChartFunction.ChartType;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.view.FunctionComposite;
import com.insigma.commons.framework.view.chart.data.BarChartData;
import com.insigma.commons.framework.view.chart.data.MultiPieData;
import com.insigma.commons.framework.view.chart.data.PieChartData;
import com.insigma.commons.framework.view.chart.data.SeriesChartData;
import com.insigma.commons.framework.view.form.FormEditor;
import com.insigma.commons.ui.tree.ObjectTree;
import com.insigma.commons.ui.widgets.CTabFolder;
import com.insigma.commons.ui.widgets.EnhanceComposite;
import com.insigma.commons.ui.widgets.ToolBar;
import com.insigma.commons.ui.widgets.ToolItem;
import com.swtdesigner.SWTResourceManager;

public class ChartComposite extends FunctionComposite {

	private ChartFunction function;

	private FormEditor formEditor;

	private ObjectTree leftTree;

	private BarChart barChart;

	private PieChart pieChart;

	private SeriesChart seriesChart;

	private MultiPieChartComposite multiPie;

	public ChartComposite(Composite parent, int style) {
		super(parent, style);
		setBackground(getParent().getBackground());
	}

	public Function getFunction() {
		return function;
	}

	protected void createTree() {
		CTabFolder folder = new CTabFolder(this, SWT.BORDER);
		GridData treeGridData = new GridData(GridData.FILL_VERTICAL);
		treeGridData.widthHint = 200;
		treeGridData.verticalSpan = 4;
		folder.setLayoutData(treeGridData);
		folder.setTabHeight(22);

		Composite toolbar = new Composite(folder, SWT.NONE);
		GridLayout barlayout = new GridLayout();
		barlayout.horizontalSpacing = 0;
		barlayout.verticalSpacing = 0;
		barlayout.marginBottom = 0;
		barlayout.marginTop = 0;
		barlayout.marginHeight = 0;
		barlayout.numColumns = 2;
		toolbar.setLayout(barlayout);
		Composite hintBar = new Composite(toolbar, SWT.NONE);
		hintBar.setLayoutData(new GridData(GridData.FILL_BOTH));
		ToolBar bar = new ToolBar(toolbar, SWT.NONE);
		folder.setTopRight(toolbar, SWT.FILL);

		final ToolItem subitem = new ToolItem(bar, SWT.NONE);
		final ToolItem additem = new ToolItem(bar, SWT.NONE);
		subitem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				if (leftTree.getDepth() <= 1) {
					subitem.setEnabled(false);
					return;
				}
				leftTree.setDepth(leftTree.getDepth() - 1);
				if (leftTree.getDepth() <= 1) {
					subitem.setEnabled(false);
				} else {
					subitem.setEnabled(true);
				}

				additem.setEnabled(true);
			}
		});
		subitem.setImage(SWTResourceManager.getImage(getClass(), "/com/insigma/commons/framework/images/left.png"));

		additem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				if (leftTree.getDepth() >= leftTree.getMaxDepth()) {
					additem.setEnabled(false);
					return;
				}
				leftTree.setDepth(leftTree.getDepth() + 1);
				if (leftTree.getDepth() >= leftTree.getMaxDepth()) {
					additem.setEnabled(false);
				} else {
					additem.setEnabled(true);
				}

				subitem.setEnabled(true);

			}
		});
		additem.setImage(SWTResourceManager.getImage(getClass(), "/com/insigma/commons/framework/images/right.png"));
		bar.layout();

		toolbar.layout();

		toolbar.setVisible(false);

		CTabItem item = new CTabItem(folder, SWT.NONE);
		item.setText("网络拓扑结构");

		leftTree = new ObjectTree(folder, SWT.BORDER | SWT.CHECK);
		leftTree.setTreeProvider(function.getTreeProvider());
		item.setControl(leftTree);

		folder.setSelection(0);
	}

	public void setFunction(ChartFunction func) {

		if (this.function != null) {
			return;
		}
		this.function = func;
		context.setFunction(function);

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		setLayout(layout);

		if (function.getTreeProvider() != null) {

			if (function.isHideTree()) {
				layout.numColumns = 2;
				layout.horizontalSpacing = 0;
				setLayout(layout);

				EnhanceComposite composite = new EnhanceComposite(this, SWT.NONE);
				composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				composite.setLayout(new GridLayout());
				composite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
				GridData treeGridData = new GridData(GridData.FILL_VERTICAL);
				treeGridData.widthHint = 0;
				treeGridData.verticalSpan = 4;
				composite.setLayoutData(treeGridData);

				leftTree = new ObjectTree(composite, SWT.BORDER | SWT.CHECK);
				leftTree.setTreeProvider(function.getTreeProvider());
			} else {
				layout.numColumns = 2;
				setLayout(layout);
				createTree();
			}

		}

		EnhanceComposite composite = new EnhanceComposite(this, SWT.BORDER);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		composite.setLayout(new GridLayout());
		composite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

		if (function.getForm() != null) {
			formEditor = new FormEditor(composite, SWT.NONE);
			GridData pdata = new GridData(GridData.FILL_HORIZONTAL);
			formEditor.setLayoutData(pdata);
			formEditor.setBackground(formEditor.getParent().getBackground());
			formEditor.setForm(function.getForm());
			formEditor.setSize(400, 400);
			formEditor.setSize(formEditor.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			formEditor.layout();
		}

		if (function.getActions() != null && function.getActions().size() > 0) {
			buildButtonBar(composite, function.getActions());
		}
		composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		composite.layout();

		if (function.getChartType() == ChartType.BAR) {
			barChart = new BarChart(this);
			barChart.setLayoutData(new GridData(GridData.FILL_BOTH));
			if (function.getChartTitle() != null) {
				barChart.setChartTitle(function.getChartTitle());
				barChart.refreshChart(null);
			}
		}

		if (function.getChartType() == ChartType.PIE) {
			pieChart = new PieChart(this);
			pieChart.setLayoutData(new GridData(GridData.FILL_BOTH));
			if (function.getChartTitle() != null) {
				pieChart.setChartTitle(function.getChartTitle());
				pieChart.refreshChart(null);
			}
		}

		if (function.getChartType() == ChartType.SERIES) {
			seriesChart = new SeriesChart(this);
			seriesChart.setLayoutData(new GridData(GridData.FILL_BOTH));
			if (function.getChartTitle() != null) {
				seriesChart.setChartTitle(function.getChartTitle());
				seriesChart.refreshChart(null);
			}
		}
		if (function.getChartType() == ChartType.MULTIPIE) {
			multiPie = new MultiPieChartComposite(this, SWT.BORDER);
			multiPie.setLayoutData(new GridData(GridData.FILL_BOTH));
			if (function.getChartTitle() != null) {
				// multiPie.setChartTitle(function.getChartTitle());
				// multiPie.refreshChart(null);
			}
		}

	}

	public Request getRequest() {

		SearchRequest request = new SearchRequest();
		if (formEditor != null) {
			request.setForm(formEditor.getForm());
		}
		if (leftTree != null) {
			request.setSelection(leftTree.getChecked());
		}
		return request;
	}

	public void refresh() {
		formEditor.setForm(function.getForm());
	}

	public void setResponse(Response resp) {
		if (function.getChartType() == ChartType.BAR) {
			BarChartData data = (BarChartData) resp.getValue();
			barChart.refreshChart(data);
		} else if (function.getChartType() == ChartType.PIE) {
			PieChartData data = (PieChartData) resp.getValue();
			pieChart.refreshChart(data);
		} else if (function.getChartType() == ChartType.SERIES) {
			SeriesChartData data = (SeriesChartData) resp.getValue();
			seriesChart.refreshChart(data);
		} else if (function.getChartType() == ChartType.MULTIPIE) {
			MultiPieData data = (MultiPieData) resp.getValue();
			multiPie.refreshChart(data);
		}
	}
}
