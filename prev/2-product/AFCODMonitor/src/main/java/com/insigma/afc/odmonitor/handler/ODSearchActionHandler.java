package com.insigma.afc.odmonitor.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.dic.AFCTicketFamily;
import com.insigma.afc.odmonitor.form.ConditionForm;
import com.insigma.afc.odmonitor.listview.item.ODSearchResultItem;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.framework.function.table.TableGrid;

/**
 * 创建时间 2010-10-13 下午10:14:59 <br>
 * 描述: 断面客流查询<br>
 * Ticket：
 *
 * @author wangxinhao<wangxinhao@zdwxjd.com>
 */
public class ODSearchActionHandler extends ODActionHandler {

	@Override
	public void perform(SearchRequest request, SearchResponse response) {
		ConditionForm conditionForm = (ConditionForm) request.getForm().getEntity();
		if (conditionForm != null) {
			int pageSize = request.getPageSize();
			int page = 0;
			if (!request.getAction().getText().equals("查询")) {
				page = request.getPage() - 1;
			}
			if (conditionForm.getStatType() == null) {
				response.addInformation("请选择统计类型。");
				return;
			}
			try {
				TableGrid grid = new TableGrid();
				response.setData(grid);

				Long totalSize = chartService.getODCount(conditionForm);
				if (totalSize == null || totalSize <= 0) {
					response.addInformation("查询结果为空。");
					return;
				}

				List<Object[]> data = chartService.getODList(conditionForm, page, pageSize);
				if (data == null || data.isEmpty()) {
					response.addInformation("查询结果为空。");
					return;
				}

				List<ODSearchResultItem> formData = new ArrayList<ODSearchResultItem>();

				if (AFCNodeLevel.LC.equals(AFCApplication.getAFCNodeType())) {
					// 总客流
					List<Object[]> totalDataList = chartService.getTotalODList(conditionForm);
					if (totalDataList != null && totalDataList.size() > 0) {
						for (Object[] totalData : totalDataList) {
							ODSearchResultItem t = new ODSearchResultItem();

							t.setId(-1);
							t.setStationName(totalData[0] + "号线总客流");

							Map<Object, String> ticketFamily = AFCTicketFamily.getInstance().getCodeMap();

							if (conditionForm.getStatType() == 1) {
								t.setTicketFamily(totalData[1].toString() + "/无");
							} else if (ticketFamily == null
									|| ticketFamily.get(((Short) totalData[1]).intValue()) == null) {
								t.setTicketFamily("票种未知/" + totalData[1]);
							} else {
								t.setTicketFamily(
										ticketFamily.get(((Short) totalData[1]).intValue()) + "/" + totalData[1]);
							}

							t.setOdIn((Long) totalData[2]);
							t.setOdOut((Long) totalData[3]);
							t.setOdBuy((Long) totalData[4]);
							t.setOdAdd((Long) totalData[5]);
							t.setOdTotal(t.getOdIn() + t.getOdOut() + t.getOdBuy() + t.getOdAdd());
							formData.add(t);
						}
					}
				}
				grid.setCurrentPage(page + 1);
				grid.setPageSize(pageSize);
				grid.setTotalSize(totalSize.intValue());

				int no = page * pageSize + 1;
				for (Object[] d : data) {
					ODSearchResultItem t = new ODSearchResultItem();

					t.setId(no++);
					t.setStationName(AFCApplication.getNodeName((Integer) d[0]));
					Map<Object, String> ticketFamily = AFCTicketFamily.getInstance().getCodeMap();

					if (conditionForm.getStatType() == 1) {
						t.setTicketFamily(d[1].toString() + "/无");
					} else if (ticketFamily == null || ticketFamily.get(((Short) d[1]).intValue()) == null) {
						t.setTicketFamily("票种未知/" + d[1]);
					} else {
						t.setTicketFamily(ticketFamily.get(((Short) d[1]).intValue()) + "/" + d[1]);
					}

					t.setOdIn((Long) d[2]);
					t.setOdOut((Long) d[3]);
					t.setOdBuy((Long) d[4]);
					t.setOdAdd((Long) d[5]);
					t.setOdTotal(t.getOdIn() + t.getOdOut() + t.getOdBuy() + t.getOdAdd());
					formData.add(t);
				}

				grid.setContent(formData);

			} catch (Exception e) {
				logger.error("查询车站客流异常", e);
				response.addInformation("查询车站客流失败。");
				return;
			}
		}
	}
}
