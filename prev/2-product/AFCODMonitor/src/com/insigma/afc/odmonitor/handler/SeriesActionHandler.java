package com.insigma.afc.odmonitor.handler;

import java.util.ArrayList;
import java.util.List;

import com.insigma.afc.odmonitor.form.SeriesForm;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.framework.view.chart.data.SeriesChartData;

/**
 * 曲线图客流刷新处理
 * 
 * @author 廖红自
 */
public class SeriesActionHandler extends ODActionHandler {

	@Override
	public void perform(SearchRequest request, SearchResponse response) {
		SeriesForm seriesForm = (SeriesForm) request.getForm().getEntity();
		seriesForm.setPartNames(SeriesForm.LEGEND);
		seriesForm.setIntervalCount(seriesForm.getIntervalCount());
		int trunsType = seriesForm.getTransType();

		if (trunsType == 0 || trunsType == 0xff) {
			response.addInformation("请至少选择一种交易类型。");
			return;
		}

		try {
			SeriesChartData data = chartService.getSeriesChartData(seriesForm);
			if (data != null) {
				List<Boolean> showRows = new ArrayList<Boolean>();
				for (int i = 0; i < SeriesForm.LEGEND.length; i++) {
					if ((trunsType & (0x1 << i)) != 0) {
						showRows.add(true);
					} else {
						showRows.add(false);
					}
				}
				data.setShowRows(showRows);
				response.setValue(data);
			}
		} catch (Exception e) {
			logger.error("查询车站客流异常", e);
			response.addError("数据库连接异常。");
			return;
		}
	}
}
