package com.insigma.afc.monitor.model.vo;


import com.insigma.afc.monitor.model.dto.BarPieChartDTO;
import com.insigma.afc.monitor.model.dto.TicketComparePieDTO;

import java.util.List;

/**
 * @Author by Xinshao,
 * @Email xingshaoya@unittec.com,
 * @Time on 2019/8/26 11:38.
 * @Ticket :
 */
public class TicketCompareVO {
    public TicketCompareVO() {
    }

    public TicketCompareVO(TicketComparePieDTO data, String pieChartNames) {
        this.data = data;
        this.PieChartNames = pieChartNames;
    }

    /**
     * 饼图数据
     */
    private TicketComparePieDTO data;

    /**
     * 对应饼图的名字
     */
    private String PieChartNames;

    public TicketComparePieDTO getData() {
        return data;
    }

    public void setData(TicketComparePieDTO data) {
        this.data = data;
    }

    public String getPieChartNames() {
        return PieChartNames;
    }

    public void setPieChartNames(String pieChartNames) {
        PieChartNames = pieChartNames;
    }
}
