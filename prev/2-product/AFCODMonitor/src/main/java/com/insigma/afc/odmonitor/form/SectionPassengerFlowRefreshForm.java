package com.insigma.afc.odmonitor.form;

import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.View;

/**
 * 创建时间 2010-10-12 下午08:43:58 <br>
 * 描述: 断面客流查询<br>
 * Ticket：
 *
 * @author wangxinhao<wangxinhao@zdwxjd.com>
 */
public class SectionPassengerFlowRefreshForm extends SectionPassengerFlowForm {

	@View(label = "刷新方式", type = "RadioBox")
	@DataSource(list = { "手动", "自动" }, values = { "0", "1" })
	protected Integer refreshType;

	public SectionPassengerFlowRefreshForm() {

	}

	public Integer getRefreshType() {
		return refreshType;
	}

	public void setRefreshType(Integer refreshType) {
		this.refreshType = refreshType;
	}
}