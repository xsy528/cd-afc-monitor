package com.insigma.afc.monitor.model.vo;

import java.util.*;

/**
 * Ticket:客流查询曲线图实体
 *
 * @author: xingshaoya
 * create time: 2019-03-22 10:37
 */
public class SeriesChartData {

    /**
     * 曲线图中的各条曲线 <曲线名,曲线对应的数据>
     */
    private Map<String, List<SeriesData>> seriersItems = new HashMap<String, List<SeriesData>>();

    /**
     * 图表每个点中包含的各项名,partNames的大小要与showRows大小一致，比如包含进站，出站等
     */
    private List<String> partNames = new ArrayList<String>();

    /**
     * 图表每个点中包含的各项是否显示，（showRows的大小要与valueOfSeriers大小一致，或者showRows为null,默认全部显示），<br>
     * 比如包含进站值，出站值等
     */
    private List<Boolean> showRows = new ArrayList<Boolean>();

    private int IntervalCount = 5;

    //横坐标类型 1：时 2：日 3:月
    private int xaxisType = 1;

    private Date startDate;

    private Date endDate;

    private Integer ticketType;

    public SeriesChartData() {

    }

    public SeriesChartData(Map<String, List<SeriesData>> seriersItems) {
        this.seriersItems = seriersItems;
    }

    public List<Boolean> getShowRows() {
        return showRows;
    }

    public void setShowRows(List<Boolean> showRows) {
        this.showRows = showRows;
    }

    /**
     * 曲线上点的数据
     *
     * @author lhz
     */
    public static class SeriesData {
        /**
         * 曲线名点的时间
         */
        private Date date;

        /**
         * 曲线图中各个点所包含的各项值，比如包含进站值，出站值等，valueOfPart的大小要与showRows大小一致
         */
        private long[] valueOfPart;

        public SeriesData() {

        }

        public SeriesData(Date date, long[] valueOfPart) {
            this.date = date;
            this.valueOfPart = valueOfPart;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public long[] getValueOfPart() {
            return valueOfPart;
        }

        public void setValueOfPart(long[] valueOfPart) {
            this.valueOfPart = valueOfPart;
        }

    }

    public Map<String, List<SeriesData>> getSeriersItems() {
        return seriersItems;
    }

    public void setSeriersItems(Map<String, List<SeriesData>> seriersItems) {
        this.seriersItems = seriersItems;
    }

    public List<String> getPartNames() {
        return partNames;
    }

    public void setPartNames(List<String> partNames) {
        this.partNames = partNames;
    }

    public int getIntervalCount() {
        return IntervalCount;
    }

    public void setIntervalCount(int intervalCount) {
        IntervalCount = intervalCount;
    }

    public Integer getTicketType() {
        return ticketType;
    }

    public void setTicketType(Integer ticketType) {
        this.ticketType = ticketType;
    }

    /**
     * @return the xaxisType
     */
    public int getXaxisType() {
        return xaxisType;
    }

    /**
     * @param xaxisType the xaxisType to set
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
     * @param startDate the startDate to set
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
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
